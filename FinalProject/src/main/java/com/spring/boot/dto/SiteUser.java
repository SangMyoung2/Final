package com.spring.boot.dto;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
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
	private String tel;
	private String created;

	@PrePersist
	protected void onCreate() {
		if (created == null || created.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
            created = LocalDateTime.now().format(formatter);
}
}
}
