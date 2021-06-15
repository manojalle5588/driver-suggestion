package com.walmart.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.client.RestTemplate;

import com.walmart.entity.DriverLocation;
import com.walmart.entity.Store;
import com.walmart.model.DriverPayload;
import com.walmart.repository.DriverLocationRepository;
import com.walmart.repository.StoreRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SuggestionControllerTest {
	@LocalServerPort
	int randomServerPort;
	
	@Autowired
	private StoreRepository storeRepo;
	
	@Autowired
	private DriverLocationRepository driverLocationRepository;
	
	@BeforeEach
	public void init() {
		storeRepo.deleteAll();
	}

	@Test
	public void testNoDriversAvailble() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		storeRepo.save(new Store(Long.valueOf(1),Double.valueOf(1),Double.valueOf(1)));
		final String baseUrl = "http://localhost:" + randomServerPort + "/api/nearest/drivers/list?storeID=1&size=2";
		URI uri = new URI(baseUrl);

		ResponseEntity<List<DriverPayload>> result = restTemplate.exchange(uri, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<DriverPayload>>() {
				});

		// Verify request succeed
		assertEquals(200, result.getStatusCodeValue());
		assertTrue(result.getBody().isEmpty());
	}
	
	@Test
	public void testNearByDrivers() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		storeRepo.save(new Store(Long.valueOf(1),Double.valueOf(1),Double.valueOf(1)));
		driverLocationRepository.save(new DriverLocation("abc@1",Double.valueOf(3),Double.valueOf(3),Double.valueOf(0)));
		driverLocationRepository.save(new DriverLocation("abc@2",Double.valueOf(2),Double.valueOf(2),Double.valueOf(0)));
		driverLocationRepository.save(new DriverLocation("abc@3",Double.valueOf(4),Double.valueOf(4),Double.valueOf(0)));
		final String baseUrl = "http://localhost:" + randomServerPort + "/api/nearest/drivers/list?storeID=1&size=2";
		URI uri = new URI(baseUrl);

		ResponseEntity<List<DriverPayload>> result = restTemplate.exchange(uri, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<DriverPayload>>() {
				});

		// Verify request succeed
		assertEquals(200, result.getStatusCodeValue());
		assertEquals(2,result.getBody().size());
		assertEquals("abc@2",result.getBody().get(0).getDriverID());
	}


	@Test
	public void testNearByDriversFailure() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/api/nearest/drivers/list?storeID=12&size=2";
		URI uri = new URI(baseUrl);

		assertThrows(InternalServerError.class, () -> {
			restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<DriverPayload>>() {
			});
		});

	}
}