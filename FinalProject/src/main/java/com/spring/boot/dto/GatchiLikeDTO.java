package com.spring.boot.dto;

import lombok.Data;

@Data
public class GatchiLikeDTO {

	private int meetListNum; //글번호
	private int likeCount; //좋아요 수
	private String userEmail; //회원Email

}
