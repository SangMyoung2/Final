package com.spring.boot.controller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.spring.boot.dao.UserRepository;
import com.spring.boot.dto.ChallengeDTO;
import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.MeetReviewDTO;
import com.spring.boot.dto.SessionUser;
import com.spring.boot.model.Users;
import com.spring.boot.service.ChallengeLikeService;
import com.spring.boot.service.ChallengeService;
import com.spring.boot.service.GatchiService;
import com.spring.boot.service.MeetServiceYj;
import com.spring.boot.service.PaymentService;
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

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private MeetServiceYj meetServiceYj;

	@Autowired
	private ChallengeService challengeService;

	@Autowired
	private ChallengeLikeService challengeLikeService;

	@GetMapping("/")
	public ModelAndView main() throws Exception {

	ModelAndView mav = new ModelAndView();

	List<GatchiDTO> meetMateLists = new ArrayList<>();
	List<GatchiDTO> communiLists = new ArrayList<>();
	List<ChallengeDTO> challengeList = new ArrayList<>();
	List<MeetReviewDTO> allreview = new ArrayList<>();
	challengeList = challengeService.getChallengeLists();
	meetMateLists = gatchiService.getMeetMateLists();
	communiLists = gatchiService.getCommuniFindLists();
	allreview = meetServiceYj.getAllMeetReviews();
	

	mav.addObject("meetLists", meetMateLists);	
	mav.addObject("communiLists", communiLists);	
	mav.addObject("challengeList", challengeList);	
	mav.addObject("allreview", allreview);	
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
		public String signup(Users dto,BindingResult bindResult,Model model,
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
	public ModelAndView mypage(HttpServletRequest req) throws Exception {
		ModelAndView mav = new ModelAndView();

		HttpSession session = req.getSession();
		Users user1 = (Users) session.getAttribute("user1"); // 일반 로그인
		SessionUser sessionUser = (SessionUser)session.getAttribute("user"); // 소셜 로그인

		
		String email = null;

		if (sessionUser != null) {
            email = sessionUser.getEmail();
        } else if (user1 != null) {
            email = user1.getEmail();
        }
		
		if (email != null) {
			try {
				// 포인트 잔액 조회
				int pointBalance = paymentService.getUserPoint(email);
				mav.addObject("pointBalance", pointBalance);
				
				mav.setViewName("login/mypage");

			} catch (Exception e) {
				mav.setViewName("login/errorPage"); // 적절한 에러 페이지로 변경해야 합니다.
			}
			
		} 

    List<Integer> userMeetList = gatchiService.getMeetListNumByUserEmail(email);
	List<Integer> userMeetLike = gatchiService.getMeetLikeNumByUserEmail(email);
	List<Integer> userChallengeLike = challengeLikeService.getChallengeLikeNumByUserEmail(email);
	List<Integer> userChallengeList = challengeService.getChallengeListNumByUserEmail(email);
	

	 if (userChallengeList == null || userChallengeList.isEmpty()) {
		
		mav.setViewName("login/mypage");
	} else {
		List<ChallengeDTO> challengeList = challengeService.getChallengeByChallengeListNums(userChallengeList);
	
		mav.addObject("challengeList", challengeList);
	}

    if (userMeetList == null || userMeetList.isEmpty()) {
		
		mav.setViewName("login/mypage");
	} else {
		List<GatchiDTO> gatchiList = gatchiService.getGatchiByMeetMateListNums(userMeetList);
		List<GatchiDTO> gatchicommuList = gatchiService.getGatchiByMeetcommuListNums(userMeetList);
	
		mav.addObject("gatchiList", gatchiList);
		mav.addObject("gatchicommuList", gatchicommuList);
	}


	if (userMeetLike == null || userMeetLike.isEmpty()) {
		mav.setViewName("login/mypage");
	} else {
		List<GatchiDTO> gatchlike = gatchiService.getGatchiByLikeNums(userMeetLike);

	 mav.addObject("gatchlike", gatchlike);
	}

	if (userChallengeLike == null || userChallengeLike.isEmpty()) {
		mav.setViewName("login/mypage");
	} else {
		List<ChallengeDTO> challengelike = challengeLikeService.getChallengeLikeNums(userChallengeList);

	 mav.addObject("challengelike", challengelike);
	}
	
	
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

			String absolutePath = new File("").getAbsolutePath() + "\\";
			String path = "FinalProject/src/main/resources/static/image/login";
			File file = new File(path);
		

			System.out.println(absolutePath);
			String originalFileName = userImg.getOriginalFilename();

		// 폴더가 없다면 생성
		if (!file.exists()) {
		   file.mkdirs();
		}

		String saveFileName = UUID.randomUUID() + originalFileName;
		file = new File(absolutePath + path + "/" + saveFileName);
		userImg.transferTo(file);
				
			userService.userupDate(email,password,name,saveFileName);
		
		mav.setViewName("login/userupdate_ok");
				
		return mav;
		}

		@GetMapping("/mainReview.action")
		public ModelAndView mainReview() throws Exception {
			ModelAndView mav = new ModelAndView();

		List<MeetReviewDTO> allreview = new ArrayList<>();
		List<ChallengeDTO> challengeList = new ArrayList<>();
		challengeList = challengeService.getChallengeLists();
		allreview = meetServiceYj.getAllMeetReviews();

		mav.addObject("challengeList", challengeList);	
		mav.addObject("allreview", allreview);	
			
		mav.setViewName("login/mainReview");
				
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
	
