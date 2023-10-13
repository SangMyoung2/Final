package com.spring.boot.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.Users;

public interface BaseAuthUserRepository extends JpaRepository<Users, Long>{
	
	Optional<Users> findByEmail(String email);
	
	
}
