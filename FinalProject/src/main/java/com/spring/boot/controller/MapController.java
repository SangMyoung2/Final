package com.spring.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MapController {

    @RequestMapping(value = "/mainMap.action")
    public ModelAndView mainMap() throws Exception{

        ModelAndView mav = new ModelAndView();

        mav.setViewName("map/Map");

        return mav;
    }

}
