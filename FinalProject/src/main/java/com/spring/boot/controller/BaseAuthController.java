package com.spring.boot.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.dto.SessionUser;

import com.spring.boot.dto.SignupDTO;

import com.spring.boot.model.Users;
import com.spring.boot.service.LoginUser;
import com.spring.boot.service.SaveData;
import com.spring.boot.service.SignupService;
import com.spring.boot.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BaseAuthController {

    
	

	@Resource
	private UserService userService;

	@Resource
	private SignupService signupService;
	



	@GetMapping("/")
	public ModelAndView main() throws Exception {

	ModelAndView mav = new ModelAndView();

	mav.setViewName("index");
		
	return mav;
    }

	@GetMapping("/login.action")
	public String login() {
	
		return "login/login";
		
	}

	

	
	
	

	
	@GetMapping("/signup.action")
	public ModelAndView signup() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login/signup");
		
		return mav;
		
	}
	
	@PostMapping("/signup.action")
	public String signup(Users dto, BindingResult bindResult,Model model,@RequestParam("userName") String userName) {
		

		try {
			
			userService.create(dto.getUserName(), dto.getName(),
					dto.getPassword(),dto.getTel());

					
			return "login/signup_ok";

		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			model.addAttribute("error", "이미 등록된 사용자입니다.");
			return "login/signup";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
			return "login/signup";
		}

		
	}


	@GetMapping("/findID.action")
	public ModelAndView findID() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login/findID");
		
		return mav;
		
	}

	@PostMapping("/findID.action")
	public ModelAndView findID_ok(SignupDTO dto) throws Exception {
		
		ModelAndView mav = new ModelAndView();

		  SignupDTO dto2 = signupService.findID(dto);

		  if (dto2 != null) {
			mav.addObject("dto", dto2);
		} else {
			mav.addObject("error", "회원 정보를 찾지 못했습니다"); 
		}
		mav.setViewName("login/findID");
		return mav;
	}
	
	@GetMapping("/findPWD.action")
	public ModelAndView findPWD() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login/findPWD");
		
		return mav;
		
	}

	@PostMapping("/findPWD.action")
	public ModelAndView findPWD_ok(SignupDTO dto) throws Exception {
		
		ModelAndView mav = new ModelAndView();

		  SignupDTO dto2 = signupService.findPWD(dto); 

		  if (dto2 != null) {
			mav.addObject("dto", dto2); 
		} else {
			mav.addObject("error", "회원 정보를 찾지 못했습니다"); 
		}
		mav.setViewName("login/findPWD");
		return mav;
		
	}
 @GetMapping("/test.action")
 public ModelAndView test() {
	ModelAndView mav = new ModelAndView();
    
   mav.setViewName("login/test");
		
		return mav;
}



@GetMapping("/temp.action")
	public ModelAndView temp() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("temp");
		
		return mav;
		
	}
}
	
