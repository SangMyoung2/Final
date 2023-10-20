package com.spring.boot.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetInfoDTO {
    private int meetListNum; 
    private String email;
    private int meetMemStatus; //0승인대기중 1방장 2회원 3블랙
    
    private String name;
}
