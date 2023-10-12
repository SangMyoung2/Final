package com.spring.boot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

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

	@GetMapping("/gatchiChoice")
	public ModelAndView meetmateChoice() throws Exception{
		
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

		mav.setViewName("/meetmate/meetmateCreate");
		
		return mav;
	}


	@PostMapping("/meetmateCreate")
	public ModelAndView meetmateCreate_ok(@RequestParam("meetImage") MultipartFile meetImage, GatchiDTO dto) throws Exception{

		ModelAndView mav = new ModelAndView();
		
		// 업로드된 이미지 처리
		if (!meetImage.isEmpty()) {
			try {
				byte[] bytes = meetImage.getBytes();
				// 여기서 이미지를 저장하거나 다른 처리를 수행할 수 있습니다.
			} catch (IOException e) {
				// 파일 처리 중 예외 발생 시 예외 처리 로직 추가
			}
		}

		
		int maxNum = gatchiService.maxNum();
		
		//System.out.println("설정이름 : "+ dto.getMeetName());
		//System.out.println("설정한 meetCheck : "+ dto.getMeetCheck());
		dto.setMeetListNum(maxNum + 1);
		
		gatchiService.createMeetmate(dto);
	
		mav.setViewName("redirect:/meetmateList");
		
		return mav;

	}
		

	@GetMapping("/meetmateList")
	public ModelAndView meetmateList() throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		List<GatchiDTO> meetLists = new ArrayList<>();

		meetLists = gatchiService.getMeetLists();

		//System.out.println("모임 DB 가져온 내용 : " + meetLists);

		mav.addObject("meetLists", meetLists);
		mav.setViewName("/meetmate/list");
		
		return mav;		
	}


}
