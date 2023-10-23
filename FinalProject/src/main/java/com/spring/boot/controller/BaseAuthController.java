package com.spring.boot.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.spring.boot.dao.UserRepository;
import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.MeetInfoDTO;
import com.spring.boot.model.Users;
import com.spring.boot.service.GatchiService;
import com.spring.boot.service.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BaseAuthController {

	@Autowired
	private UserService userService;

   @Autowired
    private UserRepository userRepository;

	@Autowired
	private GatchiService gatchiService;

	@GetMapping("/")
	public ModelAndView main() throws Exception {

	ModelAndView mav = new ModelAndView();

	List<GatchiDTO> meetMateLists = new ArrayList<>();

	meetMateLists = gatchiService.getMeetMateLists();

	

	mav.addObject("meetLists", meetMateLists);	

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
	public String signup(Users dto, BindingResult bindResult,Model model,
	@RequestParam("userName") String userName,
	@RequestParam("picture") String picture) {
		

		try {
			
			userService.create(dto.getUserName(), dto.getName(),
					dto.getPassword(),dto.getTel(),dto.getPicture());

					
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
	public ModelAndView mypage(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
		ModelAndView mav = new ModelAndView();

		
		String userEmail = userDetails.getUsername();
		
		List<Integer> userMeetList = gatchiService.getMeetListNumByUserEmail(userEmail);
		
		List<MeetInfoDTO> meetInfoList = new ArrayList<>();

		List<GatchiDTO> gatchiList = gatchiService.getGatchiByMeetListNums(userMeetList);
		
		meetInfoList = gatchiService.getMeetInfo();
			
		mav.addObject("meetinfolist", meetInfoList);	
		mav.addObject("gatchiList", gatchiList);
		mav.setViewName("login/mypage");
			
	return mav;
	}


	@GetMapping("/userupdate.action")
	public ModelAndView userupdate() {
		ModelAndView mav = new ModelAndView();
		
	mav.setViewName("login/userupdate");
			
	return mav;
	}


	@PostMapping("/userupdate.action")
	public ModelAndView userupdate_ok(
		@RequestParam("email") String email,
		@RequestParam("name") String name,
		@RequestParam("password") String password,
		 @RequestParam("userImg") MultipartFile userImg) throws IOException {


			ModelAndView mav = new ModelAndView();

		// Resource resource = new ClassPathResource("static");
      	// String resourcePath = resource.getFile().getAbsolutePath() + "\\image\\login";
		  String resourcePath = "C:\\VSCode\\Final\\FinalProject\\src\\main\\resources\\static\\image\\login";
		
		

		String originalFilename = userImg.getOriginalFilename();
				String saveFileName = originalFilename + UUID.randomUUID();
				Path filePath = Paths.get(resourcePath, saveFileName);
            	
				
            	Files.write(filePath, userImg.getBytes());
				
			userService.userupDate(email,password,name,saveFileName);
		
		mav.setViewName("login/userupdate_ok");
				
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
	
