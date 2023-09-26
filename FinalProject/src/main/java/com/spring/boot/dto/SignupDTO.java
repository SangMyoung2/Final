package com.spring.boot.dto;

import lombok.Data;

@Data
public class SignupDTO {
    
	private String name;
	private String password;
	private String password2;
	private String email;
	private String created;
}
