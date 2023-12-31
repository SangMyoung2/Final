package com.spring.boot.controller;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.spring.boot.collection.ChatRoomCollection;
import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.GatchiLikeDTO;
import com.spring.boot.dto.MapDTO;
import com.spring.boot.dto.MeetInfoDTO;
import com.spring.boot.dto.SessionUser;
import com.spring.boot.model.Users;
import com.spring.boot.service.ChatRoomService;
import com.spring.boot.service.GatchiLikeService;
import com.spring.boot.service.GatchiService;
import com.spring.boot.service.MapService;
import com.spring.boot.service.MeetServiceYj;

import org.springframework.ui.Model;


@RestController //는 return을 텍스트로 인식하지만 ModelAndView는 ResponseBody를 작성하지 않아도 주소로 인식한다. 
public class MeetmateController {
	
	@Autowired
	private GatchiService gatchiService;

	@Autowired
	private GatchiLikeService gatchiLikeService;

	@Autowired
	private MapService mapService;

	@Autowired
	private MeetServiceYj meetServiceYj;

	@Autowired
	private ChatRoomService chatRoomService;
 	//여기서 호출 하면 BoardService -> BoardServiceImpl -> BoardMapper -> boardMapper.xml에서 데이터 반환을 BoardController로 해준다.

	@GetMapping("/gatchiChoice.action")
	public ModelAndView gatchiChoice() throws Exception{
		
		ModelAndView mav = new ModelAndView();

		mav.setViewName("meetmate/gatchiChoice");
		
		return mav;		
	}


	@PostMapping("/gatchiChoice.action")
	public ModelAndView gatchiChoice_ok(@RequestParam("meetCheck") String meetCheck, HttpServletRequest request, GatchiDTO dto) throws Exception{

		ModelAndView mav = new ModelAndView();

		mav.addObject("dto", dto);
		mav.addObject("lat", request.getParameter("lat"));
		mav.addObject("lng", request.getParameter("lng"));
		
		if (dto.getMeetCheck() == 1) {

			dto.setMeetName(""); // 모임명을 ""로 설정
			mav.setViewName("meetmate/meetMateCreate");
			return mav;

		} else if (dto.getMeetCheck() == 2) {
			mav.setViewName("meetmate/communiFindCreate");
			return mav;
		}	
		return mav;
	}

	@PostMapping("/meetMateCreate.action")
	public ModelAndView meetMateCreate_ok(HttpServletRequest request, 
		@RequestParam("meetImage1") MultipartFile meetImage, GatchiDTO dto, MeetInfoDTO infoDTO) throws Exception{

		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();

		
		SessionUser social = (SessionUser)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");

		if (social != null) {
			infoDTO.setEmail(social.getEmail()); 
		} else if (user1 != null) {
			infoDTO.setEmail(user1.getEmail()); 
		}

		String absolutePath = new File("").getAbsolutePath() + "\\";
		String path = "FinalProject/src/main/resources/static/image/gatchiImage";
        File file = new File(path);
		System.out.println("앱솔루트패스 : " + absolutePath);
		if (!meetImage.isEmpty()) {
			String originalFileName = meetImage.getOriginalFilename();

			// 폴더가 없다면 생성
			if (!file.exists()) {
				file.mkdirs();
			}

			String saveFileName = UUID.randomUUID() + originalFileName;
			file = new File(absolutePath + path + "/" + saveFileName);
			meetImage.transferTo(file);

			int maxNum = gatchiService.maxNum();
			dto.setMeetListNum(maxNum + 1);
			dto.setMeetImage(saveFileName);
			gatchiService.createGatchi(dto);

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
		mav.addObject("listNum", dto.getMeetListNum());
		mav.addObject("createType", 1);

		//mav.setViewName("redirect:/meetMateList.action");
		mav.setViewName("redirect:/createroom.action");
		Thread.sleep(2000);
		return mav;
	}


	@PostMapping("/communiFindCreate.action")
	public ModelAndView communiFindCreate_ok(HttpServletRequest request, 
		@RequestParam("meetImage1") MultipartFile meetImage, GatchiDTO dto, MeetInfoDTO infoDTO) throws Exception{

		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		SessionUser social = (SessionUser)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");

		if (social != null) {
			infoDTO.setEmail(social.getEmail()); 
		} else if (user1 != null) {
			infoDTO.setEmail(user1.getEmail()); 
		}
	
		String absolutePath = new File("").getAbsolutePath() + "\\";
		String path = "FinalProject/src/main/resources/static/image/gatchiImage";
        File file = new File(path);
		System.out.println("앱솔루트패스 : " + absolutePath);

		if (!meetImage.isEmpty()) {
			String originalFileName = meetImage.getOriginalFilename();

			// 폴더가 없다면 생성
			if (!file.exists()) {
				file.mkdirs();
			}

			String saveFileName = UUID.randomUUID() + originalFileName;
			file = new File(absolutePath + path + "/" + saveFileName);
			meetImage.transferTo(file);
			int maxNum = gatchiService.maxNum();
			dto.setMeetListNum(maxNum + 1);
			dto.setMeetImage(saveFileName);
			gatchiService.createCommuni(dto);

			infoDTO.setMeetListNum(maxNum + 1);
			gatchiService.createMeetInfo(infoDTO);

			MapDTO mapDTO = new MapDTO();
			mapDTO.setLat(Double.parseDouble(request.getParameter("lat")));
			mapDTO.setLng(Double.parseDouble(request.getParameter("lng")));
			mapDTO.setMeetListNum(maxNum + 1);

			mapService.insertMapData(mapDTO);
		}

		mav.addObject("roomName", dto.getMeetTitle());
		mav.addObject("roomType", "CUMMUNITY");
		mav.addObject("listNum", dto.getMeetListNum());
		mav.addObject("createType", 2);

		// mav.setViewName("redirect:/communiFindList.action");
		mav.setViewName("redirect:/createroom.action");
		Thread.sleep(2000);
		return mav;
	}
		
	@RequestMapping("/meetMateList.action")
	public ModelAndView meetMateList(
		@RequestParam(name = "searchKey", required = false, defaultValue = "meetTitle") String searchKey,
		@RequestParam(name = "searchValue", required = false) String searchValue, 
		@RequestParam(name = "sortOrder", required = false) String sortOrder,
		MeetInfoDTO meetInfoDTO, HttpServletRequest request) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		 // 방장 프로필 사진 불러오기
		 int meetListNum = meetInfoDTO.getMeetListNum();
		 String masterProfile = gatchiService.getProfileByUsers(meetListNum);

		List<GatchiDTO> meetMateLists = new ArrayList<>();
		List<GatchiDTO> meetMateSlideLists = new ArrayList<>();

		meetMateSlideLists = gatchiService.getMeetMateRandomList(9); // 9개의 랜덤 모임을 가져옴
		meetMateLists = gatchiService.getMeetMateLists();

		if (searchValue != null) {	
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		} else {
			searchValue = "";
		}

		List<GatchiDTO> searchMeetMateList = gatchiService.searchMeetMateList(searchKey, searchValue);
		List<GatchiDTO> searchMeetMateList2 = gatchiService.searchMeetMateList2(searchKey, searchValue);

		//정렬 버튼 클릭 시
		if (sortOrder != null) {
			List<GatchiDTO> sortLists = new ArrayList<>();
			
			if ("meetHitCount".equals(sortOrder)){
				sortLists = gatchiService.sortByHitCountMeet();
				// System.out.println(sortLists);
			} else if ("meetLikeCount".equals(sortOrder)) {
				sortLists = gatchiService.sortByLikeCountMeet();
				// System.out.println(sortLists);
			} else if ("meetDday".equals(sortOrder)) {
				sortLists = gatchiService.sortByDdayMeet();
				// System.out.println(sortLists);
			}
			mav.addObject("searchMeetMateList2", searchMeetMateList2);		
			mav.addObject("sortLists", sortLists);
			mav.setViewName("meetmate/meetMateList");
	  
			return mav;
			}
	  

 		//여기서부터 meetStatus 값 변경 위한 작업
		Date currentDate = new Date();//현재 날짜, 시간 가져오기
		
		List<GatchiDTO> meetMateLists2 = gatchiService.getMeetMateLists();//meetMateLists로 GatchiDTO 가져오기

		// meetMateLists를 하나씩 꺼내면서 날짜 비교 및 업데이트
		for (GatchiDTO meetMateList : meetMateLists2) {			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date meetDday = dateFormat.parse(meetMateList.getMeetDday());

			if (meetMateList.getMeetCheck() == 1 && meetDday.before(currentDate)) {// meetCheck가 1이고 meetDday 지나면
				meetMateList.setMeetStatus(2);//meetStatus를 2로 업데이트				
				gatchiService.updateMeetStatusMate(meetMateList);//업데이트된 GatchiDTO 저장
				ChatRoomCollection chatRoom = chatRoomService.findByRoomId(meetMateList.getChatRoomNum());
				chatRoom.setRoomType(2);
				chatRoomService.updateChatRoom(chatRoom);
			}
		}

		//mav.addObject("picture", picture);********************
		mav.addObject("searchMeetMateList", searchMeetMateList);
		mav.addObject("searchMeetMateList2", searchMeetMateList2);
		mav.addObject("meetMateSlideLists", meetMateSlideLists);		
		mav.addObject("meetLists", meetMateLists);	
		mav.addObject("masterProfile", masterProfile);
		mav.setViewName("meetmate/meetMateList");
		
		return mav;		
	}


	@RequestMapping("/communiFindList.action")
	public ModelAndView communiFindList(
		@RequestParam(name = "searchKey", required = false, defaultValue = "meetTitle") String searchKey,
		@RequestParam(name = "searchValue", required = false) String searchValue, 
		@RequestParam(name = "sortOrder", required = false) String sortOrder,
		MeetInfoDTO meetInfoDTO, HttpServletRequest request) throws Exception {
		
		ModelAndView mav = new ModelAndView();

		 // 방장 프로필 사진 불러오기
		 int meetListNum = meetInfoDTO.getMeetListNum();
		 String masterProfile = gatchiService.getProfileByUsers(meetListNum);

		List<GatchiDTO> communiFindLists = new ArrayList<>();
		List<GatchiDTO> communiFindSlideLists = new ArrayList<>();

		communiFindSlideLists = gatchiService.getCommuniFindRandomList(9); // 9개의 랜덤 모임을 가져옴
		communiFindLists = gatchiService.getCommuniFindLists();

		if (searchValue != null) {	
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		} else {
			searchValue = "";
		}

		List<GatchiDTO> searchCommuniFindList = gatchiService.searchCommuniFindList(searchKey, searchValue);

		//정렬 버튼 클릭 시
		if (sortOrder != null) {
			List<GatchiDTO> sortLists = new ArrayList<>();

			if ("meetHitCount".equals(sortOrder)){
			sortLists = gatchiService.sortByHitCountMeet();
			} else if ("meetLikeCount".equals(sortOrder)) {
			sortLists = gatchiService.sortByLikeCountMeet();
			} else if ("meetDday".equals(sortOrder)) {
			sortLists = gatchiService.sortByDdayMeet();
			}
			mav.addObject("sortLists", sortLists);
			mav.setViewName("meetmate/communiFindList");

			return mav;
		}


		//여기서부터 meetStatus 값 변경 위한 작업		
		Date currentDate = new Date();//현재 날짜, 시간 가져오기
		
		List<GatchiDTO> getCommuniFindLists2 = gatchiService.getCommuniFindLists();//meetMateLists로 GatchiDTO 가져오기

		//meetMateLists를 하나씩 꺼내면서 날짜 비교 및 업데이트
		// for (GatchiDTO communiFindList : getCommuniFindLists2) {			
		// 	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// 	Date meetDday = dateFormat.parse(communiFindList.getMeetDday());

		// 	if (communiFindList.getMeetCheck() == 2 && meetDday.before(currentDate)) {// meetCheck가 1이고 meetDday 지나면
		// 		communiFindList.setMeetStatus(2);//meetStatus를 2로 업데이트				
		// 		gatchiService.updateMeetStatusFind(communiFindList);//업데이트된 GatchiDTO 저장
		// 	}
		// }

		//mav.addObject("picture", picture);********************
		mav.addObject("searchCommuniFindList", searchCommuniFindList);		
		mav.addObject("communiFindSlideLists", communiFindSlideLists);		
		mav.addObject("communiLists", communiFindLists);
		mav.addObject("masterProfile", masterProfile);
		mav.setViewName("/meetmate/communiFindList");
		
		return mav;		
	}
	
/*
	@GetMapping("/selectSort") //meetMateList
	public String selectSort(@RequestParam("selectBox") String selectBox, Model model) throws Exception{

		Gson gson = new Gson();
		List<GatchiDTO> lists;
        
		if (selectBox.equals("meetHitCount")) { //조회수순
			//System.out.println(selectBox);
			lists = gatchiService.sortByHitCountMeet();
			//String searchData = gson.toJson(lists);
			//return searchData;

		} else if (selectBox.equals("meetLikeCount")) { //좋아요순
			lists = gatchiService.sortByLikeCountMeet();
			//String searchData = gson.toJson(lists);
			//System.out.println(lists);
			//return searchData;

		} else if (selectBox.equals("meetDday")) { //모임 가장 가까운순
			lists = gatchiService.sortByDdayMeet();
			//String searchData = gson.toJson(lists);
			//return searchData;
		}

		String searchData = gson.toJson(lists);
   		model.addAttribute("meetSortedLists", lists); // 변경된 이름으로 데이터를 모델에 추가

    	return "meetmate/meetMateList :: meetCardContainer"; // 변경된 데이터를 반환
	}


	@GetMapping("/selectSort1") //communiFind
	public String selectSort1(@RequestParam("selectBox1") String selectBox) throws Exception{

		Gson gson = new Gson();
        
		if (selectBox.equals("meetHitCount")) { //조회수순
			System.out.println(selectBox);
			List<GatchiDTO> lists = gatchiService.sortByHitCountFind();
			String searchData = gson.toJson(lists);
			return searchData;

		} else if (selectBox.equals("meetLikeCount")) { //좋아요순
			List<GatchiDTO> lists = gatchiService.sortByLikeCountFind();
			String searchData = gson.toJson(lists);
			System.out.println(lists);
			return searchData;

		} else if (selectBox.equals( "meetDday")) { //모임 가장 가까운순
			List<GatchiDTO> lists = gatchiService.sortByDdayFind();
			String searchData = gson.toJson(lists);
			return searchData;
		}
		return null;
	}

*/

	@RequestMapping(value = "/reFindList", method = RequestMethod.POST, consumes = "application/json")
	public Map<String,Object> reFindList(@RequestBody Map<String, String> requestMap) throws Exception {

		System.out.println(requestMap);

		List<GatchiDTO> lists = new ArrayList<>();
		
		int endList = Integer.parseInt(requestMap.get("endList"));

		lists = gatchiService.getRownumList(endList);

		Map<String, Object> data = new HashMap<>();
		data.put("meetLists", lists);

		return data;
	}
	
	@PostMapping("/meet/plusLike")
	public String plusLike(@RequestBody Map<String, String> data,HttpServletRequest req) throws Exception {
		
		System.out.println("좋아요 버튼을 누르셨군요?");

		HttpSession session = req.getSession();
		SessionUser social = (SessionUser)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");

		String useremail = "";

		if (social != null) {
			useremail = social.getEmail();
		} else if (user1 != null) {
			useremail = user1.getEmail(); 
		}
		

		System.out.println("유저이메일 : " + useremail);

		System.out.println(data.get("meetListNum"));
		int listNum = Integer.parseInt(data.get("meetListNum"));

		GatchiLikeDTO dto = new GatchiLikeDTO();
		dto.setMeetListNum(listNum);
		dto.setUseremail(useremail);

		GatchiLikeDTO isDto = gatchiLikeService.getReadDataInLike(dto);
		if(isDto != null) return "";

		gatchiService.plusMeetCount(listNum);
		gatchiLikeService.insertGatchiLike(dto);

		GatchiDTO readData = meetServiceYj.getMeetListInfo(listNum);
		System.out.println(readData.getMeetTitle() + "모임의 좋아요 수는 : " + readData.getMeetLikeCount());

		return "SUCCESS";
	}
	
	@PostMapping("/meet/minusLike")
	public String minusLike(@RequestBody Map<String, String> data,HttpServletRequest req) throws Exception {
		
		System.out.println("좋아요 버튼을 취소했다.");

		HttpSession session = req.getSession();
		SessionUser social = (SessionUser)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");

		String useremail = "";
		
		if (social != null) {
			useremail = social.getEmail();
		} else if (user1 != null) {
			useremail = user1.getEmail(); 
		}

		System.out.println("유저이메일 : " + useremail);

		System.out.println(data.get("meetListNum"));
		int listNum = Integer.parseInt(data.get("meetListNum"));

		gatchiService.minusMeetCount(listNum);
		//gatchiService.deleteGatchiLike(listNum, useremail);

		GatchiLikeDTO dto = new GatchiLikeDTO();
		dto.setMeetListNum(listNum);
		dto.setUseremail(useremail);

		gatchiLikeService.deleteGatchiLike(dto);

		GatchiDTO readData = meetServiceYj.getMeetListInfo(listNum);
		System.out.println(readData.getMeetTitle() + "모임의 좋아요 수는 : " + readData.getMeetLikeCount());

		return "SUCCESS";
	}

	@PostMapping("/meet/loadLikeData")
	public List<Integer> loadLikeData(HttpServletRequest req) throws Exception {

		HttpSession session = req.getSession();
		SessionUser social = (SessionUser)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");

		String useremail = "";
		
		if (social != null) {
			useremail = social.getEmail();
		} else if (user1 != null) {
			useremail = user1.getEmail(); 
		}

		List<GatchiLikeDTO> lists = gatchiLikeService.getReadDataGatchiLike(useremail);
		if(lists == null) return null;

		List<Integer> listNum = new ArrayList<>();

		for(GatchiLikeDTO g : lists){
			listNum.add(g.getMeetListNum());
		}
		System.out.println("좋아요 누른 방들 : " + listNum);
		return listNum;
	} 

}
