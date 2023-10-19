package com.spring.boot.dto;

import lombok.Data;

@Data
public class PointHistoryDTO {
    
    private String useremail;
    private String pointUseHistory;
    private String useDate;
    private int usePoint;
    private int afterPoint;
    private int beforPoint;
    private int useType;
}
