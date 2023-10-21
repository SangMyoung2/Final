package com.spring.boot.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
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
import com.spring.boot.dto.MeetCategoryDTO;
import com.spring.boot.dto.MeetInfoDTO;
import com.spring.boot.dto.MeetReviewDTO;
import com.spring.boot.dto.SessionUser;
import com.spring.boot.model.Users;
import com.spring.boot.service.MeetServiceYj;

@RestController
public class MeetControllerYj {
    
	@Autowired
	private MeetServiceYj meetServiceYj;

	@GetMapping("/listYj.action")
	public ModelAndView listYj() throws Exception {
		List<MeetCategoryDTO> meetLists = meetServiceYj.getAllCategories();

		ModelAndView mav = new ModelAndView();
		if (meetLists == null) {
			meetLists = new ArrayList<>();
		}
		
		mav.addObject("meetLists", meetLists);
		mav.setViewName("bbs/listYj");

		return mav;
	}

	@GetMapping("/meetArticle.action")
	public ModelAndView meetArticle(HttpServletRequest request, @RequestParam("meetListNum") int meetListNum) throws Exception {

		// 날짜 종료된 모임이면 meetStatus( 1 => 2로 변경 )
		GatchiDTO GatchiDTO = new GatchiDTO();
		GatchiDTO.setMeetListNum(Integer.parseInt(request.getParameter("meetListNum")));
        meetServiceYj.meetStatusCompletion(GatchiDTO);

		GatchiDTO meetListInfo = meetServiceYj.getMeetListInfo(Integer.parseInt(request.getParameter("meetListNum")));
		List<MeetInfoDTO> meetMembers = meetServiceYj.getMeetMembers(Integer.parseInt(request.getParameter("meetListNum")));
		List<MeetReviewDTO> meetReview = meetServiceYj.getReview(Integer.parseInt(request.getParameter("meetListNum")));
		int meetStatus = meetServiceYj.getMeetStatus(Integer.parseInt(request.getParameter("meetListNum")));
		MeetInfoDTO meetMaster = meetServiceYj.getMeetMaster(Integer.parseInt(request.getParameter("meetListNum")));
		
		ModelAndView mav = new ModelAndView();
		MeetInfoDTO MeetInfoDTO = new MeetInfoDTO();

		HttpSession session = request.getSession();
		// Users social = (Users)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");

		if (sessionUser != null) {
			MeetInfoDTO.setEmail(sessionUser.getEmail()); 
		} else if (user1 != null) {
			MeetInfoDTO.setEmail(user1.getEmail()); 
		}
		
		// mav.addObject("loginEmail", "kim");  // TODO : 로그인된 email

		MeetInfoDTO.setMeetListNum(Integer.parseInt(request.getParameter("meetListNum")));
		// MeetInfoDTO.setEmail("kim"); // TODO : 세션에서 email 가져와야됨

		// 떠돌이 유저
		int memberStatus = -1;
		Integer ret = meetServiceYj.getMemberStatus(MeetInfoDTO);
		if (ret != null) memberStatus = ret.intValue();
		
		// // 방장 자신은 강퇴하면 안 되니까 비교하려고
		// if (memberStatus == 1) {
		// 	String masterEmail = meetServiceYj.getMeetMaster(Integer.parseInt(request.getParameter("meetListNum")));
		// 	mav.addObject("masterEmail", masterEmail);
		// }

		mav.addObject("meetMaster", meetMaster);
        mav.addObject("meetStatus", meetStatus);
		mav.addObject("meetListNum", request.getParameter("meetListNum"));
		mav.addObject("meetListInfo", meetListInfo);
		mav.addObject("meetMembers", meetMembers);
		mav.addObject("meetReview", meetReview);
		mav.addObject("meetMemStatus", memberStatus);
		mav.addObject("dto", MeetInfoDTO);
		mav.setViewName("meetmate/article");
		
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
		MeetInfoDTO MeetInfoDTO = new MeetInfoDTO();

		HttpSession session = request.getSession();
		// Users social = (Users)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");

		if (sessionUser != null) {
			MeetInfoDTO.setEmail(sessionUser.getEmail()); 
			MeetReviewDTO.setEmail(sessionUser.getEmail());
		} else if (user1 != null) {
			MeetInfoDTO.setEmail(user1.getEmail()); 
			MeetReviewDTO.setEmail(user1.getEmail());
		}
		
		MeetReviewDTO.setMeetListNum(meetListNum);
		// MeetReviewDTO.setEmail("kim"); // TODO : 세션에서 email 가져와야됨

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

				MeetReviewDTO.setMeetReviewImg(saveFileName);
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

		return new ModelAndView("redirect:/meetArticle.action?meetListNum=" + meetListNum);
	}

	
	// 방장이 관리할 수 있는 곳
	@GetMapping("/meetManager.action")
	public ModelAndView meetManager(HttpServletRequest request) throws Exception {

		int meetListNum = Integer.parseInt(request.getParameter("meetListNum"));
		GatchiDTO meetListInfo = meetServiceYj.getMeetListInfo(Integer.parseInt(request.getParameter("meetListNum")));

		List<MeetInfoDTO> meetMembers = meetServiceYj.getMeetMembers(meetListNum);
		List<MeetInfoDTO> meetBlack = meetServiceYj.getMeetBlack(meetListNum);
		List<MeetInfoDTO> meetWait = meetServiceYj.getMeetWait(meetListNum);
		MeetInfoDTO meetMaster = meetServiceYj.getMeetMaster(meetListNum);

		ModelAndView mav = new ModelAndView();


		mav.addObject("meetListNum", meetListNum);
		mav.addObject("meetMembers", meetMembers);
		mav.addObject("meetWait", meetWait);
		mav.addObject("meetBlack", meetBlack);
		mav.addObject("meetListInfo", meetListInfo);
		mav.addObject("meetMaster", meetMaster);

		mav.setViewName("meetmate/manager");
		
		return mav;
	}

	// 방 가입
	@PostMapping("/join-meet")
	public ModelAndView  joinMeet(HttpServletRequest request,
			@RequestParam("meetListNum") int meetListNum) throws Exception {

		ModelAndView mav = new ModelAndView("redirect:/meetArticle.action?meetListNum=" + meetListNum);

		//String email = (String) request.getSession().getAttribute("email"); //세션
	
		MeetInfoDTO MeetInfoDTO = new MeetInfoDTO();

		HttpSession session = request.getSession();
		// Users social = (Users)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");

		if (sessionUser != null) {
			MeetInfoDTO.setEmail(sessionUser.getEmail()); 
		} else if (user1 != null) {
			MeetInfoDTO.setEmail(user1.getEmail()); 
		}

		MeetInfoDTO.setMeetListNum(meetListNum);
		// MeetInfoDTO.setEmail("kim"); // TODO : 세션에서 email 가져와야됨
		
		// meetHow 값에 따라 meetMemStatus 설정
		int meetHow = meetServiceYj.getMeetHow(meetListNum);
		if (meetHow == 1) {
			MeetInfoDTO.setMeetMemStatus(2); // 선착순
			meetServiceYj.incrementMeetMemCnt(meetListNum);
		} else if (meetHow == 2) {
			MeetInfoDTO.setMeetMemStatus(0); // 승인대기
		}
			meetServiceYj.insertMeetJoinOk(MeetInfoDTO);
		
			return mav;
		}

	// 방 나가기
	@PostMapping("/out-meet")
	public ModelAndView outMeet(HttpServletRequest request,
			@RequestParam("meetListNum") int meetListNum,
			@RequestParam("email") String email) throws Exception {

		ModelAndView mav = new ModelAndView("redirect:/meetArticle.action?meetListNum=" + meetListNum);
		MeetInfoDTO MeetInfoDTO = new MeetInfoDTO();

		HttpSession session = request.getSession();
		// Users social = (Users)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");

		if (sessionUser != null) {
			MeetInfoDTO.setEmail(sessionUser.getEmail()); 
		} else if (user1 != null) {
			MeetInfoDTO.setEmail(user1.getEmail()); 
		}

		//String email = (String) request.getSession().getAttribute("email"); //세션
	
		MeetInfoDTO.setMeetListNum(meetListNum);
		MeetInfoDTO.setEmail(email);

		meetServiceYj.deleteMeetOut(MeetInfoDTO);
		meetServiceYj.decrementMeetMemCnt(meetListNum);
	
		return mav;
	}

	// 방 삭제( 1 => 0으로 변경 )
	@PostMapping("/delete-meet")
	public ModelAndView deleteMeet(HttpServletRequest request,
			@RequestParam("meetListNum") int meetListNum) throws Exception {

				
		ModelAndView mav = new ModelAndView("redirect:/meetMateList.action");

		GatchiDTO GatchiDTO = new GatchiDTO();
		GatchiDTO.setMeetListNum(meetListNum);

		meetServiceYj.updateMeetStatus(GatchiDTO);
	
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
		
		return new ModelAndView("redirect:/meetManager.action?meetListNum=" + meetListNum);
	}

	// 승인대기 거절
	@PostMapping("/reject-from-waitlist")
	public ModelAndView rejectFromWaitlist(
			@RequestParam("meetListNum") int meetListNum,
			@RequestParam("email") String email) throws Exception {

		MeetInfoDTO MeetInfoDTO = new MeetInfoDTO();

		MeetInfoDTO.setMeetListNum(meetListNum);
		MeetInfoDTO.setEmail(email);
		meetServiceYj.rejectFromWaitlist(MeetInfoDTO);

		return new ModelAndView("redirect:/meetManager.action?meetListNum=" + meetListNum);
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

		return new ModelAndView("redirect:/meetManager.action?meetListNum=" + meetListNum);
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

		return new ModelAndView("redirect:/meetManager.action?meetListNum=" + meetListNum);
	}

}
