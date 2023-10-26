package com.spring.boot.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeAuthDTO {
    private int challengeListNum;
    private int challengeAuthListNum; //인증 리스트 번호
    private String email;
    private String challengeAuthContent;
    private String challengeAuthCreateDate;
    private String challengeAuthImage;
    private int challengeAuthStatus;
    
    private String name;
    private String picture;
}
