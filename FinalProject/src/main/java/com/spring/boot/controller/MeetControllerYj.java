package com.spring.boot.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

import com.spring.boot.collection.ChatRoomCollection;
import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.MapDTO;
import com.spring.boot.dto.PointHistoryDTO;
import com.spring.boot.dto.userPointDTO;
import com.spring.boot.model.Users;
import com.spring.boot.service.ChatRoomService;
import com.spring.boot.service.GatchiService;
import com.spring.boot.service.MapService;
import com.spring.boot.dto.MeetCategoryDTO;
import com.spring.boot.dto.MeetInfoDTO;
import com.spring.boot.dto.MeetReviewDTO;
import com.spring.boot.dto.SessionUser;
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

	@Autowired
	private MapService mapService;

	@Autowired
    private ChatRoomService chatRoomService;

	@Autowired
    private ChatUtil chatUtil;

	// meetMate 아티클
	@GetMapping("/meetArticle.action")
	public ModelAndView meetArticle(HttpServletRequest request) throws Exception {

		return article(Integer.parseInt(request.getParameter("meetListNum")), request.getSession(), "meetmate/article");
	}

	// communiFind 아티클
	@GetMapping("/communiArticle.action")
	public ModelAndView communiArticle(HttpServletRequest request) throws Exception {

		return article(Integer.parseInt(request.getParameter("meetListNum")), request.getSession(), "meetmate/communiFindArticle");
	}

	// 아티클
	private ModelAndView article(int meetListNum, HttpSession session, String rurl) throws Exception {
	
	GatchiDTO gatchiDTO = new GatchiDTO();
	gatchiDTO.setMeetListNum(meetListNum);
	
	// 날짜 종료된 모임이면 meetStatus( 1 => 2로 변경 )
	meetServiceYj.meetStatusCompletion(gatchiDTO);

	GatchiDTO meetListInfo = meetServiceYj.getMeetListInfo(meetListNum);
	List<GatchiDTO> onlyMeetListInfo = meetServiceYj.getOnlyMeetListInfo(meetListNum);
	List<MeetInfoDTO> meetMembers = meetServiceYj.getMeetMembers(meetListNum);
	List<MeetInfoDTO> membersExMaster = meetServiceYj.getMembersExMaster(meetListNum);
	List<MeetReviewDTO> meetReview = meetServiceYj.getReview(meetListNum);
	int meetStatus = meetServiceYj.getMeetStatus(meetListNum);
	MeetInfoDTO meetMaster = meetServiceYj.getMeetMaster(meetListNum);
	List<MeetCategoryDTO> meetCategory = meetServiceYj.getAllCategories();
	gatchiService.updateHitCount(meetListNum); //조회수 증가

	ModelAndView mav = new ModelAndView();
	MeetInfoDTO meetInfoDTO = new MeetInfoDTO();

	// 세션
	Users user1 = (Users)session.getAttribute("user1");
	SessionUser sessionUser = (SessionUser) session.getAttribute("user");

	if (sessionUser != null) {
		meetInfoDTO.setEmail(sessionUser.getEmail()); 
	} else if (user1 != null) {
		meetInfoDTO.setEmail(user1.getEmail()); 
	}

	mav.addObject("loginEmail", meetInfoDTO.getEmail()); // 로그인된 email

	meetInfoDTO.setMeetListNum(meetListNum);

	// 떠돌이 유저(meetMemStatus)
	int memberStatus = -1;
	Integer ret = meetServiceYj.getMemberStatus(meetInfoDTO);
	if (ret != null) memberStatus = ret.intValue();

	// 떠돌이 유저(approvalStatus)
	int approvalStatus = -1;
	Integer ret2 = meetServiceYj.getApprovalStatus(meetInfoDTO);
	if (ret2 != null) approvalStatus = ret2.intValue();

	MapDTO mapDto = meetServiceYj.getlatlng(meetListNum);
	mav.addObject("mapDto", mapDto);

	mav.addObject("meetCategory", meetCategory);
	mav.addObject("meetMaster", meetMaster);
	mav.addObject("meetStatus", meetStatus);
	mav.addObject("meetListNum", meetListNum);
	mav.addObject("meetListInfo", meetListInfo);
	mav.addObject("onlyMeetListInfo", onlyMeetListInfo);
	mav.addObject("meetMembers", meetMembers);
	mav.addObject("membersExMaster", membersExMaster);
	mav.addObject("meetReview", meetReview);
	mav.addObject("meetMemStatus", memberStatus);
	mav.addObject("approvalStatus", approvalStatus);
	mav.addObject("dto", meetInfoDTO);

	mav.setViewName(rurl);
	return mav;

	}

	// communiFind 내에 방 생성은 meetMate만 만들어야됨, meetMate 생성하기 버튼 누르면 이거 타야됨
	@GetMapping("/communiFindGatchiChoice.action")
	public ModelAndView communiFindGatchiChoice(
			@RequestParam("meetListNum") int meetListNum) throws Exception{

		ModelAndView mav = new ModelAndView();

		mav.addObject("meetListNum", meetListNum);

		mav.setViewName("meetmate/communiFindGatchiChoice");
		
		return mav;		
	}

	@PostMapping("/communiFindGatchiChoice.action")
	public ModelAndView communiFindGatchiChoice_ok(HttpServletRequest request, GatchiDTO dto) throws Exception{

		ModelAndView mav = new ModelAndView();

		int meetListNum = Integer.parseInt(request.getParameter("meetListNum"));

		mav.addObject("dto", dto);
		mav.addObject("lat", request.getParameter("lat"));
		mav.addObject("lng", request.getParameter("lng"));
		mav.addObject("meetListNum", meetListNum);

		if (dto.getMeetCheck() == 1) {

			dto.setMeetName(""); // 모임명을 ""로 설정
			mav.setViewName("meetmate/meetInCommuniCreate");
			return mav;
		}

		return mav;
	}

	@PostMapping("/meetInCommuniCreate.action")
	public ModelAndView meetInCommuniCreate_ok(HttpServletRequest request, 
			GatchiDTO dto, MeetInfoDTO infoDTO,
			@RequestParam("meetImage1") MultipartFile meetImage,
			@RequestParam("meetListNum") int meetListNum) throws Exception{

		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		Users social = (Users)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");
		
		if (social != null) {
			infoDTO.setEmail(social.getEmail()); 
		} else if (user1 != null) {
			infoDTO.setEmail(user1.getEmail()); 
		}

		// // 암호에 communiFind 방번호 넣어줌
		// dto.setCode(meetListNum);
		// meetServiceYj.updateCode(dto.getCode());
		
		String absolutePath = new File("").getAbsolutePath() + "\\";
		String path = "FinalProject/src/main/resources/static/image/gatchiImage";
        File file = new File(path);
		int maxNum = 0;
		if (!meetImage.isEmpty()) {
			String originalFileName = meetImage.getOriginalFilename();

			// 폴더가 없다면 생성
			if (!file.exists()) {
				file.mkdirs();
			}

			String saveFileName = UUID.randomUUID() + originalFileName;
			file = new File(absolutePath + path + "/" + saveFileName);
			meetImage.transferTo(file);

			maxNum = gatchiService.maxNum();
			dto.setMeetListNum(maxNum + 1);
			dto.setMeetImage(originalFileName);
			dto.setCode(meetListNum);
			meetServiceYj.createMeetInCommuni(dto);

			infoDTO.setMeetListNum(maxNum + 1);			
			gatchiService.createMeetInfo(infoDTO);
			
			MapDTO mapDTO = new MapDTO();
			mapDTO.setLat(Double.parseDouble(request.getParameter("lat")));
			mapDTO.setLng(Double.parseDouble(request.getParameter("lng")));
			mapDTO.setMeetListNum(maxNum + 1);
			
			mapService.insertMapData(mapDTO);
		}
		mav.addObject("roomName", dto.getMeetTitle());
		mav.addObject("roomType", "MEET");
		mav.addObject("meetListNum", (maxNum + 1));
		//mav.setViewName("redirect:/meetMateList.action");
		mav.addObject("createType", 3);
		mav.addObject("redirectNum", meetListNum);
		mav.setViewName("redirect:/createroom.action");
		return mav;
	}

	//리뷰 올리기
	@PostMapping("/upload-review")
    public String uploadReview(HttpServletRequest request,
            @RequestParam("meetListNum") int meetListNum,
            @RequestParam("meetReviewContent") String meetReviewContent,
            @RequestParam("meetReviewImg") MultipartFile meetReviewImg) throws Exception {
        
		//  String resourcePath = "C:\\VSCode\\Final\\FinalProject\\src\\main\\resources\\static\\image\\reviewImage";

		

		// Resource resource = new ClassPathResource("static");
      	// String resourcePath = resource.getFile().getAbsolutePath() + "/image/reviewImage";

        MeetReviewDTO meetReviewDTO = new MeetReviewDTO();
		MeetInfoDTO meetInfoDTO = new MeetInfoDTO();

		HttpSession session = request.getSession();
		// Users social = (Users)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");

		if (sessionUser != null) {
			meetInfoDTO.setEmail(sessionUser.getEmail()); 
			meetReviewDTO.setEmail(sessionUser.getEmail());
		} else if (user1 != null) {
			meetInfoDTO.setEmail(user1.getEmail()); 
			meetReviewDTO.setEmail(user1.getEmail());
		}
		
		String useremail = user1.getEmail();

		meetReviewDTO.setMeetListNum(meetListNum);
		meetReviewDTO.setEmail(useremail);

		// 중복 리뷰 작성 여부 확인
		int hasReviewed = meetServiceYj.hasUserReviewed(meetReviewDTO);
		String response = "";

		if (hasReviewed<=0) { // 리뷰는 한 이메일당 하나만 작성 가능

			if (!meetReviewImg.isEmpty()) {

				String absolutePath = new File("").getAbsolutePath() + "\\";
				String path = "FinalProject/src/main/resources/static/image/reviewImage";
				File file = new File(path);
				System.out.println("앱솔루트패스 : " + absolutePath);

				String originalFileName = meetReviewImg.getOriginalFilename();

				 // 폴더가 없다면 생성
				 if (!file.exists()) {
				    file.mkdirs();
				 }

				 String saveFileName = UUID.randomUUID() + originalFileName;
				 file = new File(absolutePath + path + "/" + saveFileName);
				 meetReviewImg.transferTo(file);

				// String originalFilename = meetReviewImg.getOriginalFilename();
				// String saveFileName = UUID.randomUUID() + originalFilename;
				// System.out.println(saveFileName);
				// Path filePath = Paths.get(resourcePath, saveFileName);
            	// // 파일 저장
            	// Files.write(filePath, meetReviewImg.getBytes());
				// File destFile = new File(resourcePath, saveFileName);
				// meetReviewImg.transferTo(destFile);
				// Resource resource = new ClassPathResource("static");

				meetReviewDTO.setMeetReviewImg(saveFileName);
				meetReviewDTO.setMeetReviewContent(meetReviewContent);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				meetReviewDTO.setMeetReviewDate(sdf.format(new Date()));

				int meetReviewNum = meetServiceYj.getReviewNum(meetListNum);
				meetReviewDTO.setMeetReviewNum(meetReviewNum);
				
				
				meetServiceYj.insertMeetReview(meetReviewDTO);
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

		MeetReviewDTO meetReviewDTO = new MeetReviewDTO();
		ModelAndView mav = new ModelAndView();
		GatchiDTO meetListInfo = meetServiceYj.getMeetListInfo(meetListNum);
		int meetCheck = meetListInfo.getMeetCheck();
		
		meetReviewDTO.setMeetListNum(meetListNum);
		meetReviewDTO.setEmail(email);
		meetReviewDTO.setMeetReviewNum(meetReviewNum);

		meetServiceYj.deleteMeetReview(meetReviewDTO);

		if (meetCheck == 1) {
			return new ModelAndView("redirect:/meetArticle.action?meetListNum=" + meetListNum);
		} else if (meetCheck == 2) {
			return new ModelAndView("redirect:/communiArticle.action?meetListNum=" + meetListNum);
		}
		return mav;
		
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
	@GetMapping("/join-meet")
	public ModelAndView  joinMeet(HttpServletRequest request,
			@RequestParam("meetListNum") int meetListNum) throws Exception {

		ModelAndView mav = new ModelAndView();
		GatchiDTO meetListInfo = meetServiceYj.getMeetListInfo(meetListNum);
		int meetCheck = meetListInfo.getMeetCheck();

		//String email = (String) request.getSession().getAttribute("email"); //세션
		System.out.println("join-meet 들어옴");

		HttpSession session = request.getSession();
		Users user1 = (Users)session.getAttribute("user1");
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		String useremail = "";
		if(user1 != null){
			useremail = user1.getEmail();
		}else if(sessionUser != null){
			useremail = sessionUser.getEmail();
		}
		
		paymentPoint(useremail, meetListNum);
	
		MeetInfoDTO meetInfoDTO = new MeetInfoDTO();

		meetInfoDTO.setMeetListNum(meetListNum);
		meetInfoDTO.setEmail(useremail);
		
		// meetHow 값에 따라 meetMemStatus 설정
		int meetHow = meetServiceYj.getMeetHow(meetListNum);
		if (meetHow == 1) {
			meetInfoDTO.setMeetMemStatus(2); // 선착순
			meetServiceYj.incrementMeetMemCnt(meetListNum);
			meetInfoDTO.setApprovalStatus(-1);
			
			//채팅방 가입
			Optional<ChatRoomCollection> room = chatRoomService.getReadDate(meetListInfo.getChatRoomNum());
        	ChatRoomCollection rooms = (ChatRoomCollection)room.get();
        
			// 여기는 신규유저 인지 아닌지 확인 하는곳
			if(!rooms.getUsers().contains(useremail)){
				System.out.println("신규 유저 입장!");

				rooms.getUsers().add(useremail);
				String entryDate = chatUtil.todayYMDAndTime();
				String newUser = chatUtil.emailSubString(useremail);
				rooms.setEntryDate(newUser, entryDate);
				int userCnt = rooms.getUserCount();
				rooms.setUserCount(userCnt + 1);
				chatRoomService.updateChatRoom(rooms);
			}

		} else if (meetHow == 2) {
			meetInfoDTO.setMeetMemStatus(0); // 승인대기
			meetInfoDTO.setApprovalStatus(-1);
		}
		System.out.println("예외 ================================================================");

		meetServiceYj.insertMeetJoinOk(meetInfoDTO);
		System.out.println("예외 ================================================================");

		if (meetCheck == 1) {
			return new ModelAndView("redirect:/meetArticle.action?meetListNum=" + meetListNum);
		} else if (meetCheck == 2) {
			return new ModelAndView("redirect:/communiArticle.action?meetListNum=" + meetListNum);
		}
		
		return mav;
	}

	// 방 나가기
	@GetMapping("/out-meet")
	public ModelAndView outMeet(HttpServletRequest request,
			@RequestParam("meetListNum") int meetListNum) throws Exception {
		
		System.out.println("유저가 방나가기 누른곳");

		ModelAndView mav = new ModelAndView();
		GatchiDTO meetListInfo = meetServiceYj.getMeetListInfo(meetListNum);
		int meetCheck = meetListInfo.getMeetCheck();
		MeetInfoDTO meetInfoDTO = new MeetInfoDTO();

		HttpSession session = request.getSession();
		// Users social = (Users)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");

		if (sessionUser != null) {
			meetInfoDTO.setEmail(sessionUser.getEmail()); 
		} else if (user1 != null) {
			meetInfoDTO.setEmail(user1.getEmail()); 
		}

		//String email = (String) request.getSession().getAttribute("email"); //세션
		
		String useremail = meetInfoDTO.getEmail();

		meetInfoDTO.setMeetListNum(meetListNum);
		meetInfoDTO.setEmail(useremail);

		meetServiceYj.deleteMeetOut(meetInfoDTO);
		meetServiceYj.decrementMeetMemCnt(meetListNum);
		
		refundPoint(useremail, meetListNum);

		// 채팅방 나가기
		String chatRoomNum = meetListInfo.getChatRoomNum();
		ChatRoomCollection chatRoom = chatRoomService.findByRoomId(chatRoomNum);
		
		List<String> users = chatRoom.getUsers();
		for(int i=0; i<users.size(); i++){
			if(users.get(i).equals(useremail) || users.get(i) == useremail){
				System.out.println("같은 유저 찾아서 삭제");
				users.remove(i);
				break;
			}
		}
		chatRoom.setUsers(users);
		chatRoom.setUserCount(chatRoom.getUserCount() - 1);
		chatRoomService.updateChatRoom(chatRoom);

		if (meetCheck == 1) {
			return new ModelAndView("redirect:/meetArticle.action?meetListNum=" + meetListNum);
		} else if (meetCheck == 2) {
			return new ModelAndView("redirect:/communiArticle.action?meetListNum=" + meetListNum);
		}

		return mav;
	}

	// 방 삭제( 1 => 0으로 변경 )
	@GetMapping("/delete-meet")
	public ModelAndView deleteMeet(HttpServletRequest request,
			@RequestParam("meetListNum") int meetListNum) throws Exception {

		ModelAndView mav = new ModelAndView("redirect:/meetMateList.action");

		GatchiDTO gatchiDTO = new GatchiDTO();
		gatchiDTO.setMeetListNum(meetListNum);

		meetServiceYj.updateMeetStatus(gatchiDTO);
		meetServiceYj.updateCode(gatchiDTO); // 커뮤니방 삭제되면 그 속에 미트방도 삭제
		
		//채팅 삭제 (타입 2로 변경해서 안나오게)
		gatchiDTO = meetServiceYj.getMeetListInfo(meetListNum);
		String chatRoomNum = gatchiDTO.getChatRoomNum();
		System.out.println("chatRoomNum : " + chatRoomNum);
		ChatRoomCollection chatRoom = chatRoomService.findByRoomId(chatRoomNum);
		System.out.println("룸 타입 : " + chatRoom.getRoomType());
		chatRoom.setRoomType(2);
		chatRoomService.updateChatRoom(chatRoom);

		// TODO : 방 인원들 싹다 불러와서 금액 환불 MeetMate만
		// 1. 방인원 싹 불러오기(리스트로)
		// 2. 리스트 돌면서 환불해주기 하면 끗
		if(gatchiDTO.getMeetCheck() == 1){
			List<MeetInfoDTO> lists = meetServiceYj.getMeetInfo(meetListNum);
			if(lists == null || lists.isEmpty()) return mav;
			
			for (MeetInfoDTO m : lists) {
				refundPoint(m.getEmail(), meetListNum);
			}
		}
		

		return mav;
	}

	// 승인대기 수락
	@PostMapping("/accept-to-waitlist")
	public ModelAndView acceptToWaitlist(
			@RequestParam("meetListNum") int meetListNum,
			@RequestParam("email") String email) throws Exception {

		MeetInfoDTO meetInfoDTO = new MeetInfoDTO();

		meetInfoDTO.setMeetListNum(meetListNum);
		meetInfoDTO.setEmail(email);

		meetServiceYj.acceptToWaitlist(meetInfoDTO);
		meetServiceYj.incrementMeetMemCnt(meetListNum);
		
		GatchiDTO gatchiDTO = meetServiceYj.getMeetListInfo(meetListNum);

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

		MeetInfoDTO meetInfoDTO = new MeetInfoDTO();

		meetInfoDTO.setMeetListNum(meetListNum);
		meetInfoDTO.setEmail(email);
		meetServiceYj.rejectFromWaitlist(meetInfoDTO);
		
		refundPoint(email, meetListNum);

		return new ModelAndView("redirect:/meetManager.action?meetListNum=" + meetListNum);
	}
		
	// 블랙리스트에 추가
	@PostMapping("/add-to-blacklist")
	public ModelAndView addToBlacklist(
			@RequestParam("meetListNum") int meetListNum,
			@RequestParam("email") String email) throws Exception {

		MeetInfoDTO meetInfoDTO = new MeetInfoDTO();

		meetInfoDTO.setMeetListNum(meetListNum);
		meetInfoDTO.setEmail(email);

		meetServiceYj.addToBlacklist(meetInfoDTO);
		meetServiceYj.decrementMeetMemCnt(meetListNum);
		
		refundPoint(email, meetListNum);

		return new ModelAndView("redirect:/meetManager.action?meetListNum=" + meetListNum);
	}

	// 블랙리스트 해제
	@PostMapping("/release-from-blacklist")
	public ModelAndView releaseFromBlacklist(
			@RequestParam("meetListNum") int meetListNum,
			@RequestParam("email") String email) throws Exception {

		MeetInfoDTO meetInfoDTO = new MeetInfoDTO();
		meetInfoDTO.setMeetListNum(meetListNum);
		meetInfoDTO.setEmail(email);
		meetServiceYj.releaseFromBlacklist(meetInfoDTO);

		return new ModelAndView("redirect:/meetManager.action?meetListNum=" + meetListNum);
	}

	// 방장 -> 선택한 회원에게 승인요청
	@PostMapping("/send-request")
	public ModelAndView sendRequest(
			@RequestParam("meetListNum") int meetListNum,
			@RequestParam("emails") List<String> emails) throws Exception {

		MeetInfoDTO meetInfoDTO = new MeetInfoDTO();

		meetInfoDTO.setMeetListNum(meetListNum);

		for (String email : emails) {
			meetInfoDTO.setEmail(email.replace("[","").replace("]","").replace("\"",""));

			meetServiceYj.updateApprovalReq(meetInfoDTO);
		}

		return new ModelAndView("redirect:/meetManager.action?meetListNum=" + meetListNum);
	}

	// 선택받은 회원이 승인 누름
	@PostMapping("/req-approval")
	public ModelAndView reqApproval(
			@RequestParam("meetListNum") int meetListNum,
			@RequestParam("email") String email) throws Exception {

		MeetInfoDTO meetInfoDTO = new MeetInfoDTO();

		meetInfoDTO.setMeetListNum(meetListNum);
		meetInfoDTO.setEmail(email);

		meetServiceYj.updateApprovalOk(meetInfoDTO);

		int meetMemberCount = meetServiceYj.getMeetInfoCount(meetListNum);
		int meetApprovalCount = meetServiceYj.getMeetInfoApprovalstatusCount(meetListNum);

		if((meetMemberCount-1) == meetApprovalCount){
			// 모두 승인했을 경우
			GatchiDTO dto = meetServiceYj.getMeetListInfo(meetListNum);
			int money = dto.getMeetMoney();
			money = money * meetApprovalCount;

			MeetInfoDTO masterDto = meetServiceYj.getMeetMaster(meetListNum);
			String master = masterDto.getEmail();
			masterPoint(master,meetListNum,money);
		}

		return new ModelAndView("redirect:/meetManager.action?meetListNum=" + meetListNum);
	}

	// 선택받은 회원이 기각 누름
	@PostMapping("/req-reject")
	public ModelAndView reqReject(
			@RequestParam("meetListNum") int meetListNum,
			@RequestParam("email") String email) throws Exception {

		MeetInfoDTO meetInfoDTO = new MeetInfoDTO();

		meetInfoDTO.setMeetListNum(meetListNum);
		meetInfoDTO.setEmail(email);

		meetServiceYj.updateReject(meetInfoDTO);

		return new ModelAndView("redirect:/meetManager.action?meetListNum=" + meetListNum);
	}

	// 포인트 있는지 확인
	@PostMapping("/checkuserpoint")
	public Boolean checkUserMoney(HttpServletRequest req, @RequestParam("meetListNum") int meetListNum) throws Exception{
		HttpSession session = req.getSession();
		
		Users user1 = (Users)session.getAttribute("user1");
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		String useremail = "";
		if(user1 != null){
            useremail = user1.getEmail();
        }else if(sessionUser != null){
            useremail = sessionUser.getEmail();
        }

		int userPoint = paymentService.getUserPoint(useremail);

		GatchiDTO dto = meetServiceYj.getMeetListInfo(meetListNum);
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
		Users user1 = (Users)session.getAttribute("user1");
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		String useremail = "";
		if(user1 != null){
            useremail = user1.getEmail();
        }else if(sessionUser != null){
            useremail = sessionUser.getEmail();
        }

		GatchiDTO gatchiDTO = meetServiceYj.getMeetListInfo(meetListNum);
		int meetMoney = gatchiDTO.getMeetMoney();

		// PointHistoryDTO pointDTO = new PointHistoryDTO();
		// pointDTO.setUseremail(useremail);
		// pointDTO.setMeetListNum(meetListNum);

		// pointDTO = pointHistoryService.getUseReadData(pointDTO);

		// if(meetMoney != pointDTO.getUsePoint()){
		// 	return false;
		// }

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

		GatchiDTO gatchiDTO = meetServiceYj.getMeetListInfo(meetListNum);

		LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		LocalDateTime meetDateTime = LocalDateTime.parse(gatchiDTO.getMeetDday(), formatter);

		if(currentDateTime.isAfter(meetDateTime)){
			// 이미 모임시간 지남
			return 2;
		}

		return 1;
	}

	// @PostMapping("/sendCalculate")
	// public Boolean sendCalculate(HttpServletRequest req, @RequestParam("meetListNum") int meetListNum) throws Exception{
	// 	// 정산 메세지 보내는 곳
	// 	System.out.println("정산 하러 들어 왔음");

	// 	List<MeetInfoDTO> lists = meetServiceYj.getMeetInfo(meetListNum);
		
	// 	HttpSession session = req.getSession();
	// 	Users user = (Users) session.getAttribute("user1");
	// 	String useremail = user.getEmail();

	// 	if(lists == null || lists.isEmpty() || lists.size() <= 1) return false;

	// 	for (MeetInfoDTO m : lists) {
	// 		if(m.getEmail() == useremail || m.getEmail().equals(useremail)) continue;

	// 		//방장이 아니라면 실행할 코드
	// 		MeetCalculateDTO calculateDTO = new MeetCalculateDTO();
	// 		calculateDTO.setMeetListNum(meetListNum);
	// 		calculateDTO.setSenderUserEmail(useremail);
	// 		calculateDTO.setTargetUserEmail(m.getEmail());
	// 		calculateDTO.setStatus(0);
	// 		meetCalculateService.insertData(calculateDTO);
	// 	}

	// 	return true;
	// }

	// 포인트 감소(입장) 메서드
	private void paymentPoint(String useremail, int meetListNum) throws Exception{
		int userPoint = paymentService.getUserPoint(useremail);
		
		GatchiDTO gatchiDto = meetServiceYj.getMeetListInfo(meetListNum);
		System.out.println("유저 포인트 전");
		// 유저 포인트 감소
		userPointDTO userpointDTO = new userPointDTO();
		userpointDTO.setEmail(useremail);
		userpointDTO.setPointBalance(gatchiDto.getMeetMoney());
		paymentService.updateUserUsePoint(userpointDTO);
		System.out.println("유저 포인트 후 히스토리 전");
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
		System.out.println("히스토리 후");
	}


	//환불 메서드
	private void refundPoint(String useremail, int meetListNum) throws Exception{
		//여기부터 환불 코드
		// 해당 방 정보 가져오기(금액 확인용)
		GatchiDTO gatchiDTO = meetServiceYj.getMeetListInfo(meetListNum);
		int userPoint = paymentService.getUserPoint(useremail);
		// 유저 포인트 증가
		userPointDTO userpointDTO = new userPointDTO();
		userpointDTO.setEmail(useremail);
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

	//방장에게 돈주기
	private void masterPoint(String useremail, int meetListNum, int money) throws Exception{
		// 해당 방 정보 가져오기
		GatchiDTO gatchiDTO = meetServiceYj.getMeetListInfo(meetListNum);

		//방장 포인트 추가
		int userPoint = paymentService.getUserPoint(useremail);
		userPointDTO userpointDTO = new userPointDTO();
		userpointDTO.setEmail(useremail);
		userpointDTO.setPointBalance(money);
		System.out.println(userpointDTO.getPointBalance());
		paymentService.updateUserPoint(userpointDTO);

		System.out.println("정산 포인트 : " + money);
		// 히스토리 업데이트(모임끝 4번으로 추가)
		PointHistoryDTO pointDto = new PointHistoryDTO();
		pointDto.setUseremail(useremail);
		pointDto.setUseType(4); // 1:사용 2:충전 3:환불 4:모임끝정산
		pointDto.setUsePoint(money);
		pointDto.setPointUseHistory(gatchiDTO.getMeetTitle());
		pointDto.setAfterPoint(userPoint + money);
		pointDto.setBeforPoint(userPoint);
		pointDto.setMeetListNum(meetListNum);
		pointHistoryService.insertPointHistory(pointDto);
		
	}
}
