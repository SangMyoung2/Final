package com.spring.boot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.boot.dao.UserRepository;
import com.spring.boot.dto.SessionUser;
import com.spring.boot.model.Users;
import com.spring.boot.model.UserRole;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService{
	
    @Autowired
        private final HttpSession httpSession;

    @Autowired
	private final UserRepository userRepository;

	//사용자명으로 비밀번호를 조회해서 리턴하는 메서드
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        Optional<Users> searchUser = userRepository.findByUserName(username);

        if (!searchUser.isPresent()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        Users baseAuthUser = searchUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 사용자명이 "admin"인 경우 사용자 권한을 부여
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else { // 일반 사용자
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        httpSession.setAttribute("user1", new SessionUser(baseAuthUser));
       
        return new User(baseAuthUser.getUserName(), baseAuthUser.getPassword(), authorities);
    }
}


	

