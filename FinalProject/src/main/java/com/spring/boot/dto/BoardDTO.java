package com.spring.boot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {
	
	private int num;
	private String name;
	private String pwd;
	private String email;
	private String subject;
	private String content;
	private String ipAddr;
	private String created;
	private int hitCount;
	
}
