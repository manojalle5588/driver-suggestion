package com.walmart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walmart.entity.DriverLocation;
import com.walmart.entity.Store;
import com.walmart.exception.ApplicationException;
import com.walmart.model.DriverPayload;
import com.walmart.model.StorePayload;
import com.walmart.repository.DriverLocationRepository;
import com.walmart.repository.StoreRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SuggestionService {

	@Autowired
	private DriverLocationRepository driverRepo;
	
	@Autowired
	private StoreRepository storeRepo;

	public void save(DriverPayload driverPayload) {
		ObjectMapper mapper = new ObjectMapper();
		DriverLocation target = mapper.convertValue(driverPayload, DriverLocation.class);
		driverRepo.save(target);
	}
	
	public void save(StorePayload storePayload) {
		ObjectMapper mapper = new ObjectMapper();
		Store target = mapper.convertValue(storePayload, Store.class);
		storeRepo.save(target);
	}

	public List<DriverPayload> nearByDrivers(long storeID, int size) {
		List<DriverPayload> drivers=new ArrayList<>();
		try {
		Optional<Store> store=storeRepo.findById(storeID);
		if(!store.isPresent()|| store.get().getStoreID() <= 0) {
			throw new Exception("Not a Valid Store");
		}
		List<DriverLocation> driverLocations=driverRepo.nearByDrivers( store.get().getLatitude(), store.get().getLongitude(),size);
		ObjectMapper mapper = new ObjectMapper();
		driverLocations.stream().forEach(driver->{
			drivers.add(mapper.convertValue(driver, DriverPayload.class));
			});
		}catch(Exception e) {
			throw new ApplicationException(e.getMessage());
		}
		return drivers;
	}
}
