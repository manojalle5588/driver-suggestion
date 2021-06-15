package com.walmart.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walmart.model.DriverPayload;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Producer {

    
    @Value("${spring.kafka.topic}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishDriverLocation(DriverPayload driverPayload) throws JsonProcessingException {
        log.info(String.format(" Driver Location -> %s", driverPayload.toString()));
        this.kafkaTemplate.send(topicName,  new ObjectMapper().writeValueAsString(driverPayload));
    }
}
