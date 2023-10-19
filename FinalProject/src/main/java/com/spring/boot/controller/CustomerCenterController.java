package com.spring.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.dto.CustomerCenterDTO;
import com.spring.boot.service.CustomerCenterService;

@Controller
public class CustomerCenterController {
    
    @Autowired
    private CustomerCenterService customerCenterService;

    @GetMapping("/customercenter.action")
    public ModelAndView getCustomerCenterPage() {
        ModelAndView mav = new ModelAndView();
        
        List<CustomerCenterDTO> customerCenterData = customerCenterService.getAllList();
        mav.addObject("customerCenterData", customerCenterData);
        
        mav.setViewName("customercenter/customercenter");
        return mav;
    }

}
