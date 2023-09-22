package com.spring.boot.dto;

import lombok.Data;

@Data
public class MeetDTOYj {
    private int meet_ctgnum;
	private String meet_ctgname;
    
    private int meet_listnum;
    private String meet_master;
    private String meet_title;
    private String meet_content;
    private String meet_place;
    private int meet_memcnt;
    private int meet_max_memcnt;
    private int meet_hitcount;
    private int meet_like;
    private String meet_date;
    private String meet_list_image;

}
