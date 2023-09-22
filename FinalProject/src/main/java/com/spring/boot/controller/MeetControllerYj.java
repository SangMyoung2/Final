package com.spring.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.dto.MeetDTOYj;
import com.spring.boot.service.MeetServiceYj;

@RestController
public class MeetControllerYj {
    
@Autowired
    private MeetServiceYj meetServiceYj;

		@GetMapping("/listYj.action")
		public ModelAndView listYj() throws Exception {
			
			List<MeetDTOYj> listYj = meetServiceYj.getLists();
			
			ModelAndView mav = new ModelAndView();
			
			mav.addObject("listYj", listYj);
			mav.setViewName("bbs/listYj");
			
			return mav;
			
		}

		

}
