package com.spring.boot.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeDTO {
    private int challengeListNum;
    private String challengeImageMain;
    private String challengeWeekCheck;
    private String challengeDateCheck;
    private String challengeStartDate;
    private String challengeEndDate;
    private String challengeImageSuccess;
    private String challengeImageFail;
    private int challengeLikeCount;
    private String challengeContent;
    private int challengeMemberCount;
    private String challengeCreateDate;
}
