package com.spring.boot.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.spring.boot.dto.MapDTO;
import com.spring.boot.service.MapService;

@RestController
public class MapController {

    @Resource
    private MapService mapService;

    @RequestMapping(value = "/mainMap.action")
    public ModelAndView mainMap() throws Exception{
        
        ModelAndView mav = new ModelAndView();
        
        List<MapDTO> lists = mapService.getLists();
        
        Gson gson = new Gson();
        String jsonArray = gson.toJson(lists);

        // System.out.println(jsonArray);
        
        mav.addObject("jsonArray", jsonArray);
        
        mav.setViewName("map/fullsize");

        return mav;
    }

}
