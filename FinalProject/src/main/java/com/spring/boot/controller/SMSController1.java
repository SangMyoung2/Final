package com.spring.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.spring.boot.service.smsService1;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

 @Controller
public class SMSController1 {
    @Autowired
    private final smsService1 smsService;

    
    public SMSController1(smsService1 smsService) {
        this.smsService = smsService;
    }

   
    
// coolSMS 구현 로직 연결  
@GetMapping("/sendSMS")
public @ResponseBody String sendSMS(@RequestParam(value="tel") String tel) throws CoolsmsException {  	
    System.out.println(tel);
	return smsService.PhoneNumberCheck(tel);
}
}