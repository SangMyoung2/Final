package com.spring.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.dto.SessionUser;
import com.spring.boot.service.LoginUser;

@Controller

public class BaseAuthController {

	

	
	@GetMapping("/")
	public String index() {
		
		
		
		
		
		return "index";
	}
	
	@GetMapping("/login.action")
	public ModelAndView login() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login");
		
		return mav;
		
	}
	
	@GetMapping("/signup.action")
	public ModelAndView signup() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("signup");
		
		return mav;
		
	}


	@GetMapping("/signup_ok.action")
	public String signup_ok(@RequestParam("isAuthenticated") boolean isAuthenticated) {
        if (isAuthenticated) {
            // isAuthenticated가 true일 때의 로직을 수행
            return "success-page"; // 성공 페이지로 이동
        } else {
            // isAuthenticated가 false일 때의 로직을 수행
            return "failure-page"; // 실패 페이지로 이동
        }
    }

	
	@GetMapping("/main.action")
	public String main(Model model, @LoginUser SessionUser user) throws Exception {
		
		if(user!=null) {
			model.addAttribute("email", user.getEmail());
			model.addAttribute("name", user.getName());
			model.addAttribute("picture", user.getPicture());
		}
		
		return "main";
	}
	
	@GetMapping("/test.action")
	public ModelAndView test() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("test");
		
		return mav;
		
	}

	

	
	}
	
	
	

