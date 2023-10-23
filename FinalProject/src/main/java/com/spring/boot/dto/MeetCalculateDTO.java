package com.spring.boot.dto;

import lombok.Data;

@Data
public class MeetCalculateDTO {
    private int meetListNum;
    private String senderUserEmail;
    private String targetUserEmail;
    private int status;
}
