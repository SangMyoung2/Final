package com.spring.boot.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Users  implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqname")
	private Long id;
	
	@Column(unique = true)
	private String email;
	private String picture;
	@Column(unique = true)
	private String userName;
	private String password;
	private String tel;
	private String created;
	private String name;

	@PrePersist
	protected void onCreate() {
		if (created == null || created.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
            created = LocalDateTime.now().format(formatter);
}
}
	
	// JPA로 DB에 저장할 때 enum 자료형 설정(기본값은 int (안써주면))
	@Enumerated(EnumType.STRING)
	
	private BaseAuthRole role;
	
	@Builder
	public Users(String name, String email, String picture, BaseAuthRole role) {
		this.name = name;
		this.email = email;
		this.picture = picture;
		this.role = role;
	}
	
	//회원정보 수정
	public Users update(String name, String picture) {
		this.name = name;
		this.picture = picture;
		
		return this;
	}
	
	//유저인지 손님인지 값을 받아오는거
	public String getRoleKey() {
		return this.role.getKey();
	}
	
	
	
}
