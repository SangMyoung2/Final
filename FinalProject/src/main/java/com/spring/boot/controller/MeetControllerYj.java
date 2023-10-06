package com.spring.boot.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.dto.MeetDTOYj;
import com.spring.boot.service.MeetServiceYj;

@RestController
public class MeetControllerYj {
    
	@Autowired
	private MeetServiceYj meetServiceYj;

	@GetMapping("/listYj.action")
	public ModelAndView listYj() throws Exception {
		List<MeetDTOYj> listYj = meetServiceYj.getAllCategories();

		ModelAndView mav = new ModelAndView();
		mav.addObject("listYj", listYj);
		mav.setViewName("bbs/listYj");

		return mav;
	}

	@GetMapping("/articleYj.action")
	public ModelAndView articleYj(HttpServletRequest request) throws Exception {
		
		List<MeetDTOYj> meetList = meetServiceYj.getLists(Integer.parseInt(request.getParameter("meet_listnum")));
		List<String> meetMembers = meetServiceYj.getMeetMembers(Integer.parseInt(request.getParameter("meet_listnum")));
		List<MeetDTOYj> meetReview = meetServiceYj.getReview();
		ModelAndView mav = new ModelAndView();

		mav.addObject("meetList", meetList);
		mav.addObject("meetMembers", meetMembers);
		mav.addObject("meetReview", meetReview);
		mav.setViewName("bbs/articleYj");
		
		return mav;
		
	}
	
	 @PostMapping("/upload-review")
    public ModelAndView uploadReview(HttpServletRequest request,
            @RequestParam("meet_listnum") int meet_listnum,
            @RequestParam("meet_memid") String meet_memid,
            @RequestParam("meet_review_content") String meet_review_content,
            @RequestParam("meet_review_img") MultipartFile meet_review_img) throws IOException {
        
        // 이미지 업로드 처리
        String uploadDir = "경로를_지정하세요"; // 이미지를 저장할 디렉토리 경로를 지정해야 합니다.

        if (!meet_review_img.isEmpty()) {
            String originalFilename = meet_review_img.getOriginalFilename();
            String uniqueFilename = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + originalFilename;

            // 이미지 파일을 지정된 경로에 저장
            File destFile = new File(uploadDir, uniqueFilename);
            meet_review_img.transferTo(destFile);

            // MEET_REVIEW 테이블에 리뷰 정보 저장
            MeetDTOYj dto = new MeetDTOYj();
            dto.setMeet_listnum(meet_listnum);
            dto.setMeet_memid(meet_memid);
            dto.setMeet_review_content(meet_review_content);
            dto.setMeet_review_date(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            dto.setMeet_review_img(uniqueFilename); // 저장된 이미지 파일 이름을 저장

			meetServiceYj.insertMeetReview(dto);

        }

        // 기존 articleYj 화면으로 리다이렉트
        return new ModelAndView("redirect:/articleYj.action?meet_listnum=" + meet_listnum);
    }
	
	@GetMapping("/managerYj.action")
		public ModelAndView manageYj(HttpServletRequest request) throws Exception {
	
			List<MeetDTOYj> meetList = meetServiceYj.getLists(Integer.parseInt(request.getParameter("meet_listnum")));
			List<String> meetMembers = meetServiceYj.getMeetMembers(Integer.parseInt(request.getParameter("meet_listnum")));

			ModelAndView mav = new ModelAndView();

			mav.addObject("meetList", meetList);
			mav.addObject("meetMembers", meetMembers);
			mav.setViewName("bbs/managerYj");
	
			return mav;
		}

		

}
