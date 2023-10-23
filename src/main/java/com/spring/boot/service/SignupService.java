package com.spring.boot.service;


import com.spring.boot.dto.SignupDTO;

    public interface SignupService {
	
	public void insertData(SignupDTO dto) throws Exception;	
	public void updateData(SignupDTO dto) throws Exception;
	public void deleteData(String email) throws Exception;
	public SignupDTO findID(SignupDTO dto) throws Exception;
	public SignupDTO findPWD(SignupDTO dto) throws Exception;
}