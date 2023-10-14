package com.spring.boot.controller;

import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.spring.boot.dao.UserRepository;
import com.spring.boot.model.Users;
import com.spring.boot.service.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BaseAuthController {

	@Resource
	private UserService userService;

   @Autowired
    private UserRepository userRepository;

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
	public ModelAndView findID_ok(@RequestParam("name") String name) {
		ModelAndView mav= new ModelAndView();
	
		// 사용자 이름을 기반으로 사용자 찾기
		Optional<Users> userOptional = userRepository.findByName(name);
	
		if (userOptional.isPresent()) {
			Users user = userOptional.get();

			
			String email = user.getEmail();
			String name1 = user.getName();

			mav.addObject("name1", name1);
			mav.addObject("email", email);
			
			mav.setViewName("login/findID");
		} else {
			mav.addObject("error", "해당 사용자 이름에 해당하는 사용자를 찾을 수 없습니다.");
			mav.setViewName("login/findID"); 
		}
	
		return mav;
	}
	
	@GetMapping("/findPWD.action")
	public ModelAndView findPWD() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login/findPWD");
		
		return mav;
		
	}

	@PostMapping("/findPWD.action")
	public ModelAndView findPWD_ok(@RequestParam("email") String email) throws Exception {
		
		ModelAndView mav = new ModelAndView();
			// 사용자 이름을 기반으로 사용자 찾기
		Optional<Users> userOptional = userRepository.findByEmail(email);
	
		if (userOptional.isPresent()) {
			Users user = userOptional.get();

			String email1 = user.getEmail();
			
			mav.addObject("email", email1);
			
			
			mav.setViewName("login/rePWD");
		} else {
			mav.addObject("error", "해당 사용자 이메일에 해당하는 사용자를 찾을 수 없습니다.");
			mav.setViewName("login/findPWD"); 
		}
	
		return mav;
	}


	@PostMapping("/rePWD.action")
	public ModelAndView rePWD(@RequestParam("password") String password,@RequestParam("email") String email ) throws Exception {
		
		ModelAndView mav = new ModelAndView();

		userService.changePassword(email, password);

		mav.setViewName("login/rePWD_ok");
		
		return mav;
		
	}

	@GetMapping("/mypage.action")
	public ModelAndView mypage() {
		ModelAndView mav = new ModelAndView();
		
	mav.setViewName("login/mypage");
			
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
	
