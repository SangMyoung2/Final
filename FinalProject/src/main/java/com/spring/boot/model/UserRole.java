package com.spring.boot.model;

import lombok.Getter;

@Getter
public enum UserRole {
	
	// 시큐어리티 권한 코드는 접두사 Role_ 로 시작함
	
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	
	private String value;
	
	UserRole(String value) {
		this.value = value;
	}
	
}