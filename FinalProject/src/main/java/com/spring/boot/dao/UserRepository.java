package com.spring.boot.dao;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.boot.model.Users;

public interface UserRepository extends JpaRepository<Users, Long>{
	
	Optional<Users> findByUserName(String username);
	Optional<Users> findByName(String name);
	Optional<Users> findByEmail(String email);
}
