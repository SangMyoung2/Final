package com.spring.boot.service;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.spring.boot.dao.UserRepository;
import com.spring.boot.dto.SessionUser;
import com.spring.boot.dto.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	public SiteUser create(String userName, String email, String password,String tel) {
		
		SiteUser user = new SiteUser();
		user.setUserName(userName);
		user.setEmail(email);
		user.setTel(tel);
		// 패스워드 암호화(BCrypt해싱함수)
		//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		user.setPassword(passwordEncoder.encode(password));
		
		userRepository.save(user);
		
		return user;
	}
	
	public SiteUser getUser(String userName) {
		
		Optional<SiteUser> siteUser = userRepository.findByUserName(userName);
		
		if(siteUser.isPresent()) {
			return siteUser.get();
		}
		else {
			throw new DataNotFoundException("User Not Found");
		}
	}


	
    
   
}
	

