package com.spring.boot.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetReviewDTO {
    private int meetListNum;
    private int meetReviewNum;
    private String email;
    private String meetReviewContent;
    private String meetReviewDate;
    private String meetReviewImg;
}
