package com.spring.boot.service;

public class DataNotFoundException  extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String message) {
		super(message);
	}
	
}
