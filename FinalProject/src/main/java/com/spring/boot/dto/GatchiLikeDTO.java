package com.spring.boot.dto;

import lombok.Data;

@Data
public class GatchiLikeDTO {

	private int meetListNum; //글번호 li_boardidx
	private int likeClick; // 1좋아요 2안좋아요 li_idx
	//private int meetLikeCount; //좋아요 수
	private String userEmail; //회원Email li_userid

}
