package com.spring.boot.mapper;




import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spring.boot.dto.SignupDTO;

@Mapper
public interface UserMapper {
   
	public SignupDTO getEmail(@Param("email") String email);

	public int countUserByEmail(String email);

	
}
