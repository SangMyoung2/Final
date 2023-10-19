package com.spring.boot.dto;

import lombok.Data;

@Data
public class MeetInfoDTO {
    private int meetListNum; //gatchi참조
    private String email; //Users참조
    private int meetMemStatus; //0승인대기중 1방장 2회원 3블랙
}
