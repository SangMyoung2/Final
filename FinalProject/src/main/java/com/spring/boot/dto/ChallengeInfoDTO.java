package com.spring.boot.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeInfoDTO {
    private int challengeListNum;
    private String email;
    private int challengeLike;
    private int challengeMemberStatus;

    private String name;
    private String picture;
}
