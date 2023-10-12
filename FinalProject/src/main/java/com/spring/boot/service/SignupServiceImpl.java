package com.spring.boot.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.spring.boot.dto.SignupDTO;
import com.spring.boot.mapper.SignupMapper;

@Service
public class SignupServiceImpl implements SignupService {


    @Autowired
    private SignupMapper signupMapper;

    @Override
    public void deleteData(String email) throws Exception {
        signupMapper.deleteData(email);
        
    }

    @Override
    public void insertData(SignupDTO dto) throws Exception {
        signupMapper.insertData(dto);
        
    }

    @Override
    public void updateData(SignupDTO dto) throws Exception {
        signupMapper.updateData(dto);
        
    }

    @Override
    public SignupDTO findID(SignupDTO dto) throws Exception {
        return signupMapper.findID(dto);
       
    }

    @Override
    public SignupDTO findPWD(SignupDTO dto) throws Exception {
        return signupMapper.findPWD(dto);
       
    }


    

}