package com.spring.boot.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.service.GatchiService;


@RestController //는 return을 텍스트로 인식하지만 ModelAndView는 ResponseBody를 작성하지 않아도 주소로 인식한다. 
public class MeetmateController {
	
	@Resource
	private GatchiService gatchiService;

	//여기서 호출 하면 BoardService -> BoardServiceImpl -> BoardMapper -> boardMapper.xml에서 데이터 반환을 BoardController로 해준다.
/*
	@GetMapping("/slide")
	public ModelAndView slide() throws Exception{
		
		ModelAndView mav = new ModelAndView();

		mav.setViewName("/meetmate/slide");
		
		return mav;		
	}

	@GetMapping("/giormeet")
	public ModelAndView giormeet() throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		List<GatchiDTO> meetMateLists = new ArrayList<>();

		meetMateLists = gatchiService.getMeetMateLists();

		//System.out.println("모임 DB 가져온 내용 : " + meetLists);

		mav.addObject("meetLists", meetMateLists);
		mav.setViewName("/meetmate/giormeet");
		
		return mav;		
	}
*/




	
	@GetMapping("/gatchiChoice")
	public ModelAndView gatchiChoice() throws Exception{
		
		ModelAndView mav = new ModelAndView();

		mav.setViewName("/meetmate/gatchiChoice");
		
		return mav;		
	}


	@PostMapping("/gatchiChoice")
	public ModelAndView gatchiChoice_ok(@RequestParam("meetcheck") String meetCheck, GatchiDTO dto) throws Exception{

		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", dto);
		
		//meetCheck 값을 int로 파싱
		dto.setMeetCheck(Integer.parseInt(meetCheck));

		if (dto.getMeetCheck() == 1) {
			dto.setMeetName(""); // 모임명을 ""로 설정
		}
				
		//System.out.println("설정한 meetCheck 1: " + dto.getMeetCheck());
		//System.out.println("설정한 meetName 2: " + dto.getMeetName());

		mav.setViewName("/meetmate/meetMateCreate");
		
		// 이거 왜 안되냐구........................
		// if (dto.getMeetCheck() == 1) {
		// 	dto.setMeetName(""); // 모임명을 ""로 설정
		// 	mav.setViewName("/meetmate/meetMateCreate");

		// } else if (dto.getMeetCheck() == 2) {
		// 	mav.setViewName("/meetmate/communiFindCreate");
		// }	

		return mav;
	}


	@PostMapping("/meetMateCreate")
	public ModelAndView meetMateCreate_ok(HttpServletRequest request, 
		@RequestParam("meetImage1") MultipartFile meetImage, 
		GatchiDTO dto) throws Exception{

		ModelAndView mav = new ModelAndView();
		
		String uploadDir = "C:\\VSCode\\work\\Final\\FinalProject\\src\\main\\resources\\static\\image\\gatchiImage";

		if (!meetImage.isEmpty()) {
			String originalFileName = meetImage.getOriginalFilename();
			File destFile = new File(uploadDir, originalFileName);
			
			System.out.print("이거야 이름 이거이거거거ㅓ"+ originalFileName);

			meetImage.transferTo(destFile);
			int maxNum = gatchiService.maxNum();
			dto.setMeetListNum(maxNum + 1);
			dto.setMeetImage(originalFileName);
			gatchiService.createGatchi(dto);
		}
		mav.setViewName("redirect:/meetMateList");
		return mav;
	}


	@PostMapping("/communiFindCreate")
	public ModelAndView communiFindCreate_ok(HttpServletRequest request, 
		@RequestParam("meetImage1") MultipartFile meetImage, 
		GatchiDTO dto) throws Exception{

		ModelAndView mav = new ModelAndView();
		
		String uploadDir = "C:\\VSCode\\work\\Final\\FinalProject\\src\\main\\resources\\static\\image\\gatchiImage";

		if (!meetImage.isEmpty()) {
			String originalFileName = meetImage.getOriginalFilename();
			File destFile = new File(uploadDir, originalFileName);
			
			System.out.print("이거야 이름 이거이거거거ㅓ"+ originalFileName);

			meetImage.transferTo(destFile);
			int maxNum = gatchiService.maxNum();
			dto.setMeetListNum(maxNum + 1);
			dto.setMeetImage(originalFileName);
			gatchiService.createGatchi(dto);
		}
		mav.setViewName("redirect:/communiFindList");
		return mav;
	}
		

	@GetMapping("/meetMateList")
	public ModelAndView meetMateList() throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		List<GatchiDTO> meetMateLists = new ArrayList<>();

		meetMateLists = gatchiService.getMeetMateLists();

		//System.out.println("모임 DB 가져온 내용 : " + meetLists);

		mav.addObject("meetLists", meetMateLists);
		mav.setViewName("/meetmate/meetMateList");
		
		return mav;		
	}



	@GetMapping("/communiFindList")
	public ModelAndView communiFindList() throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		List<GatchiDTO> communiFindLists = new ArrayList<>();

		communiFindLists = gatchiService.getCommuniFindLists();

		//System.out.println("모임 DB 가져온 내용 : " + meetLists);

		mav.addObject("communiFindLists", communiFindLists);
		mav.setViewName("/meetmate/communiFindList");
		
		return mav;		
	}


}
