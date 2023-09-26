package com.spring.boot.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.BaseAuthUser;

public interface BaseAuthUserRepository extends JpaRepository<BaseAuthUser, Long>{
	
	Optional<BaseAuthUser> findByEmail(String email);
	
	
}
