package com.spring.boot.dto;


import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeDTO {
    private int challengeListNum;
    private String challengeTitle;
    private String challengeContent;
    private String challengeImageMain;
    private String challengeWeekCheck;
    private String challengeDateCheck;
    private Date challengeStartDate;
    private Date challengeEndDate;
    private String challengeImageSuccess;
    private String challengeImageFail;
    private int challengeLikeCount;
    private int challengeMemberCount;
    private String challengeCreateDate;
    private int challengeStatus;
    private String challengeChatRoomNum;
}
