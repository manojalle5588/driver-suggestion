package com.walmart.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.walmart.entity.Store;
import com.walmart.repository.DriverLocationRepository;
import com.walmart.repository.StoreRepository;

@SpringBootTest
public class SuggestionServiceTest {

    @Mock
	private DriverLocationRepository driverRepo;
	
    @Mock
	private StoreRepository storeRepo;

    @InjectMocks 
    private SuggestionService suggestionService = new SuggestionService();

    @BeforeEach
    void setMockOutput() {
        when(storeRepo.findById(1L)).thenReturn(Optional.of(new Store(Long.valueOf(1),Double.valueOf(1),Double.valueOf(1))));
        when(driverRepo.nearByDrivers(1, 1, 1)).thenReturn(new ArrayList<>());
    }

    @DisplayName("Test Mock suggestionService + StoreRepository + DriverLocationRepository")
    @Test
    void testNearByDriversList() {
    	assertNotNull( suggestionService.nearByDrivers(1, 1));
    }

}