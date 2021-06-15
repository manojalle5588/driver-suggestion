package com.walmart.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.walmart.kafka.Producer;
import com.walmart.model.DriverPayload;
import com.walmart.model.StorePayload;
import com.walmart.service.SuggestionService;

@RestController
@RequestMapping("/api")
public class SuggestionController {

	@Autowired
	private SuggestionService service;

	@Autowired
	private Producer producer;

	@PostMapping("/driver/add")
	public ResponseEntity<String> addDriver(@Valid  @RequestBody DriverPayload driver) {
		service.save(driver);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/store/add")
	public ResponseEntity<String>  addStore(@Valid @RequestBody StorePayload storePayload) {
		service.save(storePayload);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping(value = "/publish/driver/location")
	public ResponseEntity<String> sendMessageToKafkaTopic(@Valid  @RequestBody DriverPayload driverPayload) {
		try {
			this.producer.publishDriverLocation(driverPayload);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/nearest/drivers/list")
	public ResponseEntity<List<DriverPayload>> nearByDrivers(@RequestParam("storeID") long storeID,@RequestParam("size") int size ) {
		List<DriverPayload> drivers=service.nearByDrivers(storeID,size);
		
		return new ResponseEntity<>(drivers,HttpStatus.OK);
	}
}
