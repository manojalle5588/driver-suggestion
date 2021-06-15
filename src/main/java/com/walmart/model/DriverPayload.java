package com.walmart.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverPayload {
	@NotBlank
	private String driverID;
	@NotNull
	@Digits(integer = 10, fraction = 10)
	private Double longitude;
	@NotNull
	@Digits(integer = 10, fraction = 10)
	private Double latitude;

}
