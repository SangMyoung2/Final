package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.MeetDTOYj;

public interface MeetServiceYj {

    public List<MeetDTOYj> getAllCategories() throws Exception;

    public List<MeetDTOYj> getLists() throws Exception;

    //public void insertData(MeetDTOYj dto) throws Exception;

}
