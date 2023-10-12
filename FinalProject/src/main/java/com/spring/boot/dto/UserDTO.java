package com.spring.boot.dto;




import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {
	
	
	private String userName;
	private String password;
	private String password2;
	private String email;
	private String tel;
	private String created;
}