package com.spring.boot.controller;

import java.io.File;
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
import com.spring.boot.service.GatchiService;


@RestController //는 return을 텍스트로 인식하지만 ModelAndView는 ResponseBody를 작성하지 않아도 주소로 인식한다. 
public class MeetmateController {
	
	@Autowired
	private GatchiService gatchiService;

	//여기서 호출 하면 BoardService -> BoardServiceImpl -> BoardMapper -> boardMapper.xml에서 데이터 반환을 BoardController로 해준다.

	@GetMapping("/search3")
	public ModelAndView search3() throws Exception{
		
		ModelAndView mav = new ModelAndView();

		mav.setViewName("/meetmate/search3");
		
		return mav;		
	}
/*
	@GetMapping("/slide")
	public ModelAndView slide() throws Exception{
		
		ModelAndView mav = new ModelAndView();

		mav.setViewName("/meetmate/slide");
		
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

		// if (dto.getMeetCheck() == 1) {
		// 	dto.setMeetName(""); // 모임명을 ""로 설정
		// }             ///////이거 주석처리돼있으면 아래에서 설정하는게 먹힌다는거니까 지우기
				
		//System.out.println("설정한 meetCheck 1: " + dto.getMeetCheck());
		//System.out.println("설정한 meetName 2: " + dto.getMeetName());

		mav.setViewName("/meetmate/meetMateCreate");
		
		//이거 왜 안되냐구........................
		if (dto.getMeetCheck() == 1) {
			dto.setMeetName(""); // 모임명을 ""로 설정
			mav.setViewName("/meetmate/meetMateCreate");

		} else if (dto.getMeetCheck() == 2) {
			mav.setViewName("/meetmate/communiFindCreate");
		}	

		return mav;
	}


	@PostMapping("/meetMateCreate")
	public ModelAndView meetMateCreate_ok(HttpServletRequest request, 
		@RequestParam("meetImage1") MultipartFile meetImage, 
		GatchiDTO dto) throws Exception{

		ModelAndView mav = new ModelAndView();
		try{
			//String uploadDir = "C:\\VSCode\\work\\Final\\FinalProject\\src\\main\\resources\\static\\image\\gatchiImage";
			Resource resource = new ClassPathResource("static");
			String resourcePath = resource.getFile().getAbsolutePath() + "/image/gatchiImage";
			if (!meetImage.isEmpty()) {
				String originalFileName = meetImage.getOriginalFilename();
				File destFile = new File(resourcePath, originalFileName);
				
				//System.out.print("이거야 이름 이거이거거거ㅓ"+ originalFileName);
				meetImage.transferTo(destFile);
				int maxNum = gatchiService.maxNum();
				dto.setMeetListNum(maxNum + 1);
				dto.setMeetImage(originalFileName);
				gatchiService.createGatchi(dto);
			}
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		mav.setViewName("redirect:/meetMateList");
		return mav;
	}


	@PostMapping("/communiFindCreate")
	public ModelAndView communiFindCreate_ok(HttpServletRequest request, 
		@RequestParam("meetImage1") MultipartFile meetImage, 
		GatchiDTO dto) throws Exception{

		ModelAndView mav = new ModelAndView();
		
		// String uploadDir = "C:\\VSCode\\work\\Final\\FinalProject\\src\\main\\resources\\static\\image\\gatchiImage";
		Resource resource = new ClassPathResource("static");
		String resourcePath = resource.getFile().getAbsolutePath() + "/image/gatchiImage";
		if (!meetImage.isEmpty()) {
			String originalFileName = meetImage.getOriginalFilename();
			File destFile = new File(resourcePath, originalFileName);
			
			//System.out.print("이거야 이름 이거이거거거ㅓ"+ originalFileName);

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
	public ModelAndView meetMateList(HttpServletRequest request) throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		List<GatchiDTO> meetMateLists = new ArrayList<>();
		List<GatchiDTO> meetMateSlideLists = new ArrayList<>();
		
		//int meetListNum = Integer.parseInt(request.getParameter("meetListNum"));//추가한거
		//GatchiDTO readData = gatchiService.getReadData(meetListNum);//추가한거

		meetMateLists = gatchiService.getMeetMateLists();
		meetMateSlideLists = gatchiService.getMeetMateRandomList(9); // 5개의 랜덤 모임을 가져옴

		//System.out.println("모임 DB 가져온 내용 : " + meetLists);

		mav.addObject("meetMateSlideLists", meetMateSlideLists);
		
		mav.addObject("meetLists", meetMateLists);
		//mav.addObject("readData", readData);//추가한거
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
