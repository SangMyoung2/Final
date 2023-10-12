package com.spring.boot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class BaseAuthUser {
	
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqname")
private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String email;
	
	@Column
	private String picture;
	
	// JPA로 DB에 저장할 때 enum 자료형 설정(기본값은 int (안써주면))
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BaseAuthRole role;
	
	@Builder
	public BaseAuthUser(String name, String email, String picture, BaseAuthRole role) {
		this.name = name;
		this.email = email;
		this.picture = picture;
		this.role = role;
	}
	
	//회원정보 수정
	public BaseAuthUser update(String name, String picture) {
		this.name = name;
		this.picture = picture;
		
		return this;
	}
	
	//유저인지 손님인지 값을 받아오는거
	public String getRoleKey() {
		return this.role.getKey();
	}
	
	
	
}
