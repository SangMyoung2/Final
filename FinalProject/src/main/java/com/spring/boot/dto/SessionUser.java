package com.spring.boot.dto;

import java.io.Serializable;

import org.springframework.security.core.userdetails.UserDetails;

import com.spring.boot.model.BaseAuthUser;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import lombok.Getter;


@Getter

public class SessionUser implements UserDetails, Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(BaseAuthUser user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

	public SessionUser(UserDetails userDetails) {
        this.name = userDetails.getUsername();
     
    }

    // UserDetails 인터페이스의 메서드들을 구현
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한 정보를 반환 (필요에 따라 구현)
        return null;
    }

    @Override
    public String getPassword() {
        // 패스워드 정보를 반환 (필요에 따라 구현)
        return null;
    }

    @Override
    public String getUsername() {
        return this.name; // 사용자 이름을 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정이 만료되지 않았음을 반환
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정이 잠기지 않았음을 반환
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명이 만료되지 않았음을 반환
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정이 활성화되었음을 반환
    }
}