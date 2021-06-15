package com.walmart.exception;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = -4125695343051639274L;

	public ApplicationException() {
		super();
	}
	public ApplicationException(String message){
		super(message);
	}
}
