package com.spring.boot.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.MeetDTOYj;
import com.spring.boot.dto.PointHistoryDTO;
import com.spring.boot.dto.userPointDTO;
import com.spring.boot.model.Users;
import com.spring.boot.service.GatchiService;
import com.spring.boot.dto.MeetCategoryDTO;
import com.spring.boot.dto.MeetInfoDTO;
import com.spring.boot.dto.MeetReviewDTO;
import com.spring.boot.service.MeetServiceYj;
import com.spring.boot.service.PaymentService;
import com.spring.boot.service.PointHistoryService;
import com.spring.boot.util.ChatUtil;

@RestController
public class MeetControllerYj {
    
	@Autowired
	private MeetServiceYj meetServiceYj;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private PointHistoryService pointHistoryService;

	@Autowired
	private GatchiService gatchiService;

	@GetMapping("/listYj.action")
	public ModelAndView listYj() throws Exception {
		List<MeetCategoryDTO> listYj = meetServiceYj.getAllCategories();

		ModelAndView mav = new ModelAndView();
		if(listYj != null){
			listYj = new ArrayList<>();
		}
		mav.addObject("listYj", listYj);
		mav.setViewName("bbs/listYj");

		return mav;
	}

	@GetMapping("/articleYj.action")
	public ModelAndView articleYj(HttpServletRequest request) throws Exception {

		// 날짜 종료된 모임이면 meetStatus( 1 => 2로 변경 )
		GatchiDTO GatchiDTO = new GatchiDTO();
		GatchiDTO.setMeetListNum(Integer.parseInt(request.getParameter("meetListNum")));
        meetServiceYj.meetStatusCompletion(GatchiDTO);

		GatchiDTO meetListInfo = meetServiceYj.getMeetListInfo(Integer.parseInt(request.getParameter("meetListNum")));
		List<String> meetMembers = meetServiceYj.getMeetMembers(Integer.parseInt(request.getParameter("meetListNum")));
		List<MeetReviewDTO> meetReview = meetServiceYj.getReview(Integer.parseInt(request.getParameter("meetListNum")));
		int meetStatus = meetServiceYj.getMeetStatus(Integer.parseInt(request.getParameter("meetListNum")));
		
		ModelAndView mav = new ModelAndView();
		MeetInfoDTO MeetInfoDTO = new MeetInfoDTO();
		
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user1");
		String useremail = user.getEmail();

		mav.addObject("loginEmail", useremail);  // TODO : 로그인된 email

		MeetInfoDTO.setMeetListNum(Integer.parseInt(request.getParameter("meetListNum")));
		MeetInfoDTO.setEmail(useremail); // TODO : 세션에서 email 가져와야됨

		// 떠돌이 유저
		int memberStatus = -1;
		Integer ret = meetServiceYj.getMemberStatus(MeetInfoDTO);
		if (ret != null) memberStatus = ret.intValue();
		
		// 방장 자신은 강퇴하면 안 되니까 비교하려고
		if (memberStatus == 1) {
			String masterEmail = meetServiceYj.getMeetMasterEmail(Integer.parseInt(request.getParameter("meetListNum")));
			mav.addObject("masterEmail", masterEmail);
		}

		// 방장 이메일 띄움
		String masterEmail2 = meetServiceYj.getMeetMasterEmail(Integer.parseInt(request.getParameter("meetListNum")));
		mav.addObject("masterEmail2", masterEmail2);

        mav.addObject("meetStatus", meetStatus);
		mav.addObject("meetListNum", request.getParameter("meetListNum"));
		mav.addObject("meetListInfo", meetListInfo);
		mav.addObject("meetMembers", meetMembers);
		mav.addObject("meetReview", meetReview);
		mav.addObject("meetMemStatus", memberStatus);
		mav.addObject("dto", MeetInfoDTO);
		mav.setViewName("meetmate/articleYj");
		
		return mav;
	}

	//리뷰 올리기
	@PostMapping("/upload-review")
    public String uploadReview(HttpServletRequest request,
            @RequestParam("meetListNum") int meetListNum,
            @RequestParam("meetReviewContent") String meetReviewContent,
            @RequestParam("meetReviewImg") MultipartFile meetReviewImg) throws Exception {
        
		Resource resource = new ClassPathResource("static");
      	String resourcePath = resource.getFile().getAbsolutePath() + "/image/reviewImage";

        MeetReviewDTO MeetReviewDTO = new MeetReviewDTO();
		
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user1");
		String useremail = user.getEmail();

		MeetReviewDTO.setMeetListNum(meetListNum);
		MeetReviewDTO.setEmail(useremail); // TODO : 세션에서 email 가져와야됨

		// 중복 리뷰 작성 여부 확인
		int hasReviewed = meetServiceYj.hasUserReviewed(MeetReviewDTO);
		String response = "";

		if (hasReviewed<=0) { // 리뷰는 한 이메일당 하나만 작성 가능

			if (!meetReviewImg.isEmpty()) {
				String originalFilename = meetReviewImg.getOriginalFilename();
				String saveFileName = originalFilename + UUID.randomUUID();
				System.out.println(saveFileName);
				Path filePath = Paths.get(resourcePath, saveFileName);
            	// 파일 저장
            	Files.write(filePath, meetReviewImg.getBytes());
				// File destFile = new File(resourcePath, saveFileName);
				// meetReviewImg.transferTo(destFile);

				MeetReviewDTO.setMeetReviewImg(saveFileName); // 원본 이미지 파일 이름을 저장
				MeetReviewDTO.setMeetReviewContent(meetReviewContent);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				MeetReviewDTO.setMeetReviewDate(sdf.format(new Date()));

				int meetReviewNum = meetServiceYj.getReviewNum(meetListNum);
				MeetReviewDTO.setMeetReviewNum(meetReviewNum);

				meetServiceYj.insertMeetReview(MeetReviewDTO);
				response = "success";
				return response; // 리뷰 작성 성공 시 success 페이지로 리다이렉트
        } 

		}else{
			response = "already-reviewed";
            return response; // 이미 리뷰를 작성한 경우 already-reviewed 페이지로 리다이렉트
		}
		return "already-reviewed";
    }

	// 리뷰 삭제
	@PostMapping("/delete-review")
	public ModelAndView deleteReview(HttpServletRequest request,
			@RequestParam("meetListNum") int meetListNum,
			@RequestParam("meetReviewNum") int meetReviewNum,
			@RequestParam("email") String email) throws Exception {

		MeetReviewDTO MeetReviewDTO = new MeetReviewDTO();
		
		MeetReviewDTO.setMeetListNum(meetListNum);
		MeetReviewDTO.setEmail(email);
		MeetReviewDTO.setMeetReviewNum(meetReviewNum);

		meetServiceYj.deleteMeetReview(MeetReviewDTO);		

		return new ModelAndView("redirect:/articleYj.action?meetListNum=" + meetListNum);
	}

	
	// 방장이 관리할 수 있는 곳
	@GetMapping("/managerYj.action")
	public ModelAndView manageYj(HttpServletRequest request) throws Exception {

		int meetListNum = Integer.parseInt(request.getParameter("meetListNum"));
		GatchiDTO meetListInfo = meetServiceYj.getMeetListInfo(Integer.parseInt(request.getParameter("meetListNum")));

		List<String> meetMembers = meetServiceYj.getMeetMembers(meetListNum);
		List<String> meetBlack = meetServiceYj.getMeetBlack(meetListNum);
		List<String> meetWait = meetServiceYj.getMeetWait(meetListNum);

		ModelAndView mav = new ModelAndView();
		mav.addObject("meetListNum", meetListNum);
		mav.addObject("meetMembers", meetMembers);
		mav.addObject("meetWait", meetWait);
		mav.addObject("meetBlack", meetBlack);
		mav.addObject("meetListInfo", meetListInfo);

		String masterEmail = meetServiceYj.getMeetMasterEmail(meetListNum);
		mav.addObject("masterEmail", masterEmail);

		mav.setViewName("meetmate/managerYj");
		
		return mav;
	}

	// 방 가입
	@GetMapping("/join-meet")
	public ModelAndView  joinMeet(HttpServletRequest request,
			@RequestParam("meetListNum") int meetListNum) throws Exception {

		ModelAndView mav = new ModelAndView("redirect:/articleYj.action?meetListNum=" + meetListNum);

		//String email = (String) request.getSession().getAttribute("email"); //세션
		System.out.println("join-meet 들어옴");

		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user1");
		String useremail = user.getEmail();
		
		paymentPoint(useremail, meetListNum);
	
		MeetInfoDTO MeetInfoDTO = new MeetInfoDTO();
		MeetInfoDTO.setMeetListNum(meetListNum);
		MeetInfoDTO.setEmail(useremail); // TODO : 세션에서 email 가져와야됨
		
		// meetHow 값에 따라 meetMemStatus 설정
		int meetHow = meetServiceYj.getMeetHow(meetListNum);
		if (meetHow == 1) {
			MeetInfoDTO.setMeetMemStatus(2); // 선착순
			meetServiceYj.incrementMeetMemCnt(meetListNum);
			//채팅방 가입


		} else if (meetHow == 2) {
			MeetInfoDTO.setMeetMemStatus(0); // 승인대기
		}
			meetServiceYj.insertMeetJoinOk(MeetInfoDTO);
		
			return mav;
		}

	// 방 나가기
	@GetMapping("/out-meet")
	public ModelAndView outMeet(HttpServletRequest request,
			@RequestParam("meetListNum") int meetListNum) throws Exception {

		ModelAndView mav = new ModelAndView("redirect:/articleYj.action?meetListNum=" + meetListNum);

		//String email = (String) request.getSession().getAttribute("email"); //세션
	
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user1");
		String useremail = user.getEmail();

		MeetInfoDTO MeetInfoDTO = new MeetInfoDTO();
		MeetInfoDTO.setMeetListNum(meetListNum);
		MeetInfoDTO.setEmail(useremail);

		meetServiceYj.deleteMeetOut(MeetInfoDTO);
		meetServiceYj.decrementMeetMemCnt(meetListNum);
	
		refundPoint(useremail, meetListNum);

		return mav;
	}

	// 방 삭제( 1 => 0으로 변경 )
	@GetMapping("/delete-meet")
	public ModelAndView deleteMeet(HttpServletRequest request,
			@RequestParam("meetListNum") int meetListNum) throws Exception {

		ModelAndView mav = new ModelAndView("redirect:/meetMateList");

		GatchiDTO GatchiDTO = new GatchiDTO();
		GatchiDTO.setMeetListNum(meetListNum);

		meetServiceYj.updateMeetStatus(GatchiDTO);
		
		// TODO : 방 인원들 싹다 불러와서 금액 환불
		// 1. 방인원 싹 불러오기(리스트로)
		// 2. 리스트 돌면서 환불해주기 하면 끗
		List<MeetInfoDTO> lists = meetServiceYj.getMeetInfo(meetListNum);
		
		if(lists == null || lists.isEmpty()) return mav;

		for (MeetInfoDTO m : lists) {
			refundPoint(m.getEmail(), meetListNum);
		}

		//채팅 삭제
		
		return mav;
	}

	// 승인대기 수락
	@PostMapping("/accept-to-waitlist")
	public ModelAndView acceptToWaitlist(
			@RequestParam("meetListNum") int meetListNum,
			@RequestParam("email") String email) throws Exception {

		MeetInfoDTO MeetInfoDTO = new MeetInfoDTO();

		MeetInfoDTO.setMeetListNum(meetListNum);
		MeetInfoDTO.setEmail(email);

		meetServiceYj.acceptToWaitlist(MeetInfoDTO);
		meetServiceYj.incrementMeetMemCnt(meetListNum);
		
		GatchiDTO gatchiDTO = gatchiService.getReadData(meetListNum);

		//채팅방 가입으로 보냄
		ModelAndView mav = new ModelAndView();
		mav.addObject("meetListNum", meetListNum);
		mav.addObject("useremail",email);
		mav.addObject("roomId",gatchiDTO.getChatRoomNum());
		mav.setViewName("redirect:/chat/newUser.action");
		return mav;
	}

	// 승인대기 거절
	@PostMapping("/reject-from-waitlist")
	public ModelAndView rejectFromWaitlist(
			@RequestParam("meetListNum") int meetListNum,
			@RequestParam("email") String email,
			HttpServletRequest req) throws Exception {

		MeetInfoDTO MeetInfoDTO = new MeetInfoDTO();

		MeetInfoDTO.setMeetListNum(meetListNum);
		MeetInfoDTO.setEmail(email);
		meetServiceYj.rejectFromWaitlist(MeetInfoDTO);
		
		refundPoint(email, meetListNum);

		return new ModelAndView("redirect:/managerYj.action?meetListNum=" + meetListNum);
	}
		
	// 블랙리스트에 추가
	@PostMapping("/add-to-blacklist")
	public ModelAndView addToBlacklist(
			@RequestParam("meetListNum") int meetListNum,
			@RequestParam("email") String email) throws Exception {

		MeetInfoDTO MeetInfoDTO = new MeetInfoDTO();

		MeetInfoDTO.setMeetListNum(meetListNum);
		MeetInfoDTO.setEmail(email);

		meetServiceYj.addToBlacklist(MeetInfoDTO);
		meetServiceYj.decrementMeetMemCnt(meetListNum);

		return new ModelAndView("redirect:/managerYj.action?meetListNum=" + meetListNum);
	}

	// 블랙리스트 해제
	@PostMapping("/release-from-blacklist")
	public ModelAndView releaseFromBlacklist(
			@RequestParam("meetListNum") int meetListNum,
			@RequestParam("email") String email) throws Exception {

		MeetInfoDTO MeetInfoDTO = new MeetInfoDTO();
		MeetInfoDTO.setMeetListNum(meetListNum);
		MeetInfoDTO.setEmail(email);
		meetServiceYj.releaseFromBlacklist(MeetInfoDTO);

		return new ModelAndView("redirect:/managerYj.action?meetListNum=" + meetListNum);
	}

	// 포인트 있는지 확인
	@PostMapping("/checkuserpoint")
	public Boolean checkUserMoney(HttpServletRequest req, @RequestParam("meetListNum") int meetListNum) throws Exception{
		HttpSession session = req.getSession();
		Users user = (Users) session.getAttribute("user1");
		String useremail = user.getEmail();
		System.out.println("방 넘버 : " + meetListNum);
		System.out.println("포인트 체크 하는 곳");
		int userPoint = paymentService.getUserPoint(useremail);

		GatchiDTO dto = gatchiService.getReadData(meetListNum);
		int meetMoney = dto.getMeetMoney();

		System.out.println("유저 포인트 : " + userPoint);
		System.out.println("meetMoney = " + meetMoney);

		if(userPoint < meetMoney){
			return false;
		}
		return true;
	}

	// 가입 취소시
	@PostMapping("/joincancel")
	public Boolean joinCancel(HttpServletRequest req, @RequestParam("meetListNum") int meetListNum) throws Exception {
		System.out.println("가입 취소 들어옴");

		HttpSession session = req.getSession();
		Users user = (Users) session.getAttribute("user1");
		String useremail = user.getEmail();

		GatchiDTO gatchiDTO = gatchiService.getReadData(meetListNum);
		int meetMoney = gatchiDTO.getMeetMoney();

		PointHistoryDTO pointDTO = new PointHistoryDTO();
		pointDTO.setUseremail(useremail);
		pointDTO.setMeetListNum(meetListNum);

		pointDTO = pointHistoryService.getUseReadData(pointDTO);

		if(meetMoney != pointDTO.getUsePoint()){
			return false;
		}

		// 위에 조건에서 리턴 안되면 환불
		refundPoint(useremail, meetListNum);

		MeetInfoDTO meetinfoDTO = new MeetInfoDTO();
		meetinfoDTO.setEmail(useremail);
		meetinfoDTO.setMeetListNum(meetListNum);

		meetServiceYj.deleteMeetOut(meetinfoDTO);
		return true;
	}

	@PostMapping("/meettimecheck")
	public int meetTiemCheck(HttpServletRequest req, @RequestParam("meetListNum") int meetListNum) throws Exception{
		// 1 = 취소가능 2 = 취소 불가능(마감시간 끝)

		GatchiDTO gatchiDTO = gatchiService.getReadData(meetListNum);

		LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		LocalDateTime meetDateTime = LocalDateTime.parse(gatchiDTO.getMeetDday(), formatter);

		if(currentDateTime.isAfter(meetDateTime)){
			// 이미 모임시간 지남
			return 2;
		}

		return 1;
	}

	// 포인트 감소(입장) 메서드
	private void paymentPoint(String useremail, int meetListNum) throws Exception{
		int userPoint = paymentService.getUserPoint(useremail);
		
		GatchiDTO gatchiDto = gatchiService.getReadData(meetListNum);
		
		// 유저 포인트 감소
		userPointDTO userpointDTO = new userPointDTO();
		userpointDTO.setUseremail(useremail);
		userpointDTO.setPointBalance(gatchiDto.getMeetMoney());
		paymentService.updateUserUsePoint(userpointDTO);

		// 히스토리 업데이트(추가)
		PointHistoryDTO pointDto = new PointHistoryDTO();
		pointDto.setUseremail(useremail);
		pointDto.setUseType(1); // 1:사용 2:충전 3:환불
		pointDto.setUsePoint(gatchiDto.getMeetMoney());
		pointDto.setPointUseHistory(gatchiDto.getMeetTitle());
		pointDto.setAfterPoint(userPoint - gatchiDto.getMeetMoney());
		pointDto.setBeforPoint(userPoint);
		pointDto.setMeetListNum(meetListNum);
		pointHistoryService.insertPointHistory(pointDto);
	}


	//환불 메서드
	private void refundPoint(String useremail, int meetListNum) throws Exception{
		//여기부터 환불 코드
		// 해당 방 정보 가져오기(금액 확인용)
		GatchiDTO gatchiDTO = gatchiService.getReadData(meetListNum);
		int userPoint = paymentService.getUserPoint(useremail);
		// 유저 포인트 증가
		userPointDTO userpointDTO = new userPointDTO();
		userpointDTO.setUseremail(useremail);
		userpointDTO.setPointBalance(gatchiDTO.getMeetMoney());
		System.out.println(userpointDTO.getPointBalance());
		paymentService.updateUserPoint(userpointDTO);

		System.out.println("유저 포인트 : " + userPoint);
		// 히스토리 업데이트(환불 3번으로 추가)
		PointHistoryDTO pointDto = new PointHistoryDTO();
		pointDto.setUseremail(useremail);
		pointDto.setUseType(3); // 1:사용 2:충전 3:환불
		pointDto.setUsePoint(gatchiDTO.getMeetMoney());
		pointDto.setPointUseHistory(gatchiDTO.getMeetTitle());
		pointDto.setAfterPoint(userPoint + gatchiDTO.getMeetMoney());
		pointDto.setBeforPoint(userPoint);
		pointDto.setMeetListNum(meetListNum);
		pointHistoryService.insertPointHistory(pointDto);
		//여기까지 환불 코드
	}


}
