package com.spring.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.MeetDTOYj;
import com.spring.boot.mapper.MeetMapperYj;

@Service
public class MeetServiceImplYj implements MeetServiceYj {
    
    @Autowired
    private MeetMapperYj meetMapperYj;

    @Override
    public List<MeetDTOYj> getAllCategories() throws Exception {
    
        return meetMapperYj.getAllCategories();
    
    }

    @Override
    public List<MeetDTOYj> getLists() throws Exception {
        
        return meetMapperYj.getLists();
    }

    // @Override
	// public void insertData(MeetDTOYj dto) throws Exception {

	// 	meetMapperYj.insertData(dto);
		
	// }

}
