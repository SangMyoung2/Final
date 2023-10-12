package com.spring.boot.mapper;




import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.SignupDTO;

@Mapper
public interface SignupMapper {
   
	public void insertData(SignupDTO dto) throws Exception;
	public void updateData(SignupDTO dto) throws Exception;
	public void deleteData(String email) throws Exception;
	public SignupDTO findID(SignupDTO dto) throws Exception;
	public SignupDTO findPWD(SignupDTO dto) throws Exception;
}
