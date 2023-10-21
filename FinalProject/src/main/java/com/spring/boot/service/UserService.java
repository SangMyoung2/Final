package com.spring.boot.service;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.spring.boot.dao.UserRepository;
import com.spring.boot.dto.SessionUser;

import com.spring.boot.model.Users;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;

	private final SaveData saveData;
	
	private final PasswordEncoder passwordEncoder;
	@Transactional
	public Users create(String userName,String name, String password,String tel,String picture) {
		
		Users user = new Users();
		user.setUserName(userName);
		user.setName(name);
		user.setTel(tel);
		user.setPicture(picture);
		
		user.setPassword(passwordEncoder.encode(password));
		
		userRepository.save(user);
		saveData.saveData(userName);
		
		return user;
	}
	
	public Users getUser(String userName) {
		
		Optional<Users> BaseAuthUser = userRepository.findByUserName(userName);
		
		if(BaseAuthUser.isPresent()) {
			return BaseAuthUser.get();
		}
		else {
			throw new DataNotFoundException("User Not Found");
		}
	}

	public void changePassword(String email, String password) {

        Optional<Users> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user); 
        } 
    }


	public void userupDate(String email, String password,String name,String saveFileName) {

        Optional<Users> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
			user.setName(name);

			if (saveFileName.contains(".")) {
            user.setPicture(saveFileName);
        }
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user); 
        } 
    }
	
    
   
}
	

