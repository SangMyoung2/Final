package com.spring.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PayController {

    @GetMapping("/pay.action")
	public ModelAndView pay(){
		// System.out.println("pay controller 들어옴");
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("pay/pay");
		
		return mav;
	}


    
}
