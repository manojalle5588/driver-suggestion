package com.walmart.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StorePayload {
	@NotNull
	private Long storeID;
	@NotNull
	@Digits(integer = 10, fraction = 10)
	private Double longitude;
	@NotNull
	@Digits(integer = 10, fraction = 10)
	private Double latitude;
	
}
