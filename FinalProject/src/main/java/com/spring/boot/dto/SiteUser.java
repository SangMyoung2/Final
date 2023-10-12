package com.spring.boot.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {
	
	@Id 
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ") 
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
	private Long id ;
	@Column(unique = true)
	private String userName;
	private String password;
	private String email;
	private String tel;
	private String created;
}
