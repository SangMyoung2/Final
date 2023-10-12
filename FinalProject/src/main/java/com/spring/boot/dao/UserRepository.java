package com.spring.boot.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.dto.SiteUser;

public interface UserRepository extends JpaRepository<SiteUser, Long>{
	
	Optional<SiteUser> findByUserName(String username);
	
}
