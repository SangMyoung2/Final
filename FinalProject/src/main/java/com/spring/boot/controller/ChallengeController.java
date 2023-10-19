package com.spring.boot.controller;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.MapDTO;
import com.spring.boot.service.GatchiService;
import com.spring.boot.service.MapService;


@RestController
public class ChallengeController {
	
	@Autowired
	private GatchiService gatchiService;

	
	// @GetMapping("/")
	// public ModelAndView gatchiChoice() throws Exception{
		
	// 	ModelAndView mav = new ModelAndView();

	// 	mav.setViewName("/meetmate/gatchiChoice");
		
	// 	return mav;		
	// }


	// @PostMapping("/")
	// public ModelAndView gatchiChoice_ok(@RequestParam("meetCheck") String meetCheck, HttpServletRequest request, GatchiDTO dto) throws Exception{

	// 	ModelAndView mav = new ModelAndView();



	// 	// if (dto.getMeetCheck() == 1) {
	// 	// 	dto.setMeetName(""); // 모임명을 ""로 설정
	// 	// }             ///////이거 주석처리돼있으면 아래에서 설정하는게 먹힌다는거니까 지우기
				
	// 	//System.out.println("설정한 meetCheck 1: " + dto.getMeetCheck());
	// 	//System.out.println("설정한 meetName 2: " + dto.getMeetName());
	// 	mav.addObject("dto", dto);
	// 	mav.addObject("lat", request.getParameter("lat"));
	// 	mav.addObject("lng", request.getParameter("lng"));
		

	// 	//이거 왜 안되냐구........................
	// 	if (dto.getMeetCheck() == 1) {
	// 		System.out.println("들어옴");
	// 		dto.setMeetName(""); // 모임명을 ""로 설정
	// 		mav.setViewName("meetmate/meetMateCreate");
	// 		return mav;

	// 	} else if (dto.getMeetCheck() == 2) {
	// 		mav.setViewName("meetmate/communiFindCreate");
	// 		return mav;
	// 	}	

	// 	return mav;
	// }


    
	@GetMapping("/ChallengeCreate.action")
	public ModelAndView challengeCreate() throws Exception{

		ModelAndView mav = new ModelAndView();

		mav.setViewName("challenge/ChallengeCreate");
		
		return mav;	
	}

    @PostMapping("/ChallengeCreate.action")
	public ModelAndView challengeCreatedOk(HttpServletRequest request) throws Exception{

		ModelAndView mav = new ModelAndView();

        String weekCheck = request.getParameter("weekCheck");
        String dateCheck = request.getParameter("dateCheck");

        System.out.println(weekCheck);
        System.out.println(dateCheck);



		mav.setViewName("redirect:/ChallengeList.action");
		
		return mav;	
	}


		
	@GetMapping("/ChallengeList.action")
	public ModelAndView meetMateList() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		

	
		mav.setViewName("challenge/ChallengeList");
		
		return mav;		
	}

	@PostMapping("/meetMateList")
	public ModelAndView meetMateList(@RequestParam(name = "searchKey", required = false) String searchKey,
        @RequestParam(name = "searchValue", required = false) String searchValue) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		List<GatchiDTO> meetMateLists = new ArrayList<>();
		List<GatchiDTO> meetMateSlideLists = new ArrayList<>();
		
		// // String searchKey = request.getParameter("searchKey");
		// // String searchValue = request.getParameter("searchValue");
		// if (searchValue == null) {
		// 	searchKey = "meetTitle";
		// 	searchValue = "";
		
		// } else {
		// 	if (request.getMethod().equalsIgnoreCase("GET")) {
		// 		searchValue = URLDecoder.decode(searchValue, "UTF-8");
		// 	}
		// }   ******************************************
		
		System.out.println("searchKey 내용 : " + searchKey);
		System.out.println("searchValue 내용 : " + searchValue);

		meetMateLists = gatchiService.searchMeetMateList(searchKey, searchValue);
		meetMateSlideLists = gatchiService.getMeetMateRandomList(3); // 5개의 랜덤 모임을 가져옴

		//System.out.println("모임 DB 가져온 내용 : " + meetLists);

		mav.addObject("meetMateSlideLists", meetMateSlideLists);
		
		mav.addObject("meetLists", meetMateLists);
		
		mav.setViewName("/meetmate/meetMateList");
		
		return mav;
	}


/* 페이징처리 폭망하면 이거 살리고 하던거 지워

	@GetMapping("/meetMateList")
	public ModelAndView meetMateList() throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		List<GatchiDTO> meetMateLists = new ArrayList<>();
		List<GatchiDTO> meetMateSlideLists = new ArrayList<>();
		
		//int meetListNum = Integer.parseInt(request.getParameter("meetListNum"));//추가한거
		//GatchiDTO readData = gatchiService.getReadData(meetListNum);//추가한거
		
		meetMateLists = gatchiService.getMeetMateLists();
		meetMateSlideLists = gatchiService.getMeetMateRandomList(5); // 5개의 랜덤 모임을 가져옴

		//System.out.println("모임 DB 가져온 내용 : " + meetLists);

		mav.addObject("meetLists", meetMateLists);
		mav.addObject("meetMateSlideLists", meetMateSlideLists);
		//mav.addObject("readData", readData);//추가한거
		mav.setViewName("/meetmate/meetMateList");
		
		return mav;		
	}
 */

	@GetMapping("/communiFindList")
	public ModelAndView communiFindList() throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		List<GatchiDTO> communiFindLists = new ArrayList<>();
		List<GatchiDTO> communiFindSlideLists = new ArrayList<>();

		communiFindLists = gatchiService.getCommuniFindLists();
		communiFindSlideLists = gatchiService.getCommuniFindRandomList(9); // 5개의 랜덤 모임을 가져옴

		//System.out.println("모임 DB 가져온 내용 : " + meetLists);

		mav.addObject("communiFindSlideLists", communiFindSlideLists);
		
		mav.addObject("communiLists", communiFindLists);
		mav.setViewName("/meetmate/communiFindList");
		
		return mav;		
	}

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
}
