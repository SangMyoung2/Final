package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.CustomerCenterDTO;

public interface CustomerCenterService {
    public List<CustomerCenterDTO> getAllList() throws Exception;
}
