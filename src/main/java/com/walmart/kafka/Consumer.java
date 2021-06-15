package com.walmart.kafka;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walmart.model.DriverPayload;
import com.walmart.service.SuggestionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Consumer {

	@Autowired
	private SuggestionService service;

	@KafkaListener(topics = "driver_location", groupId = "group_id")
	public void consume(String message) throws IOException {
		log.info(String.format("Driver Location KAFKA Event -> %s", message));
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			DriverPayload payload = mapper.readValue(message, DriverPayload.class);
			service.save(payload);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
