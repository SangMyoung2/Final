package com.spring.boot.service;


import com.spring.boot.dto.SignupDTO;

    public interface SignupService {
	
	public void insertData(SignupDTO dto) throws Exception;	
	public void updateData(SignupDTO dto) throws Exception;
	public void deleteData(String email) throws Exception;
}

    

