package com.spring.boot.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.spring.boot.dto.MeetCategoryDTO;
import com.spring.boot.dto.GatchiDTO;
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
        
        mav.setViewName("map/memo");

        return mav;
    }

    @RequestMapping("/getDataFromDB")
    public GatchiDTO getDataFromDB(@RequestParam("meetListNum") int meetListNum) throws Exception {

        
        GatchiDTO data = mapService.getOneData(meetListNum);
        
        // System.out.println(listNum);
        // System.out.println(data);

        return data;
    }

    
    @RequestMapping("/getDataFromDB1")
    public GatchiDTO getDataFromDB1(@RequestParam("meetListNum") int meetListNum) throws Exception {

        
        GatchiDTO data1 = mapService.getOneData(meetListNum);
        
        // System.out.println(listNum);
        // System.out.println(data);

        return data1;
    }



    @RequestMapping("/getSearchDataFromDB")
    public String getSearchDataFromDB() throws Exception {

        List<GatchiDTO> lists = mapService.getData();
        
        Gson gson = new Gson();
        String searchData = gson.toJson(lists);

        // System.out.println(searchData);

        return searchData;
    }

    @RequestMapping("/getSearchTitleDataFromDB")
    public String getSearchTitleDataFromDB(@RequestParam("meetTitle") String meetTitle) throws Exception {

        List<GatchiDTO> lists = mapService.getTitleData(meetTitle);
        
        Gson gson = new Gson();
        String searchTitleData = gson.toJson(lists);

        // System.out.println(searchTitleData);

        return searchTitleData;
    }

    @RequestMapping("/getCategoryFromDB")
    public MeetCategoryDTO getCategoryFromDB(@RequestParam("meetCtgNum") int meetCtgNum) throws Exception {

        MeetCategoryDTO data = mapService.getCategory(meetCtgNum);
        // System.out.println(meetCtgNum);
        // System.out.println(data.getMeetCtgName());
        return data;
    }

}
