package com.spring.boot.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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
