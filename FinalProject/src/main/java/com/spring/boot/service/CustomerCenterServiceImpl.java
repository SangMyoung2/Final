package com.spring.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.CustomerCenterDTO;
import com.spring.boot.mapper.CustomerCenterMapper;

@Service
public class CustomerCenterServiceImpl implements CustomerCenterService {
    @Autowired
    private CustomerCenterMapper customerCenterMapper;

    @Override
    public List<CustomerCenterDTO> getAllList() {
        return customerCenterMapper.getAllList();
    }
}
