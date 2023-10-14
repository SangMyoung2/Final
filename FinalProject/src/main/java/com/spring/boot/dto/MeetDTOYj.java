package com.spring.boot.dto;

import lombok.Data;

@Data
public class MeetDTOYj {
    
    private int meetCtgNum; //index
	private String meetCtgName; //문화예술 맛집 취미 여행 운동
    
    private int meetListNum; //index
    private int meetCheck; //1소셜링 2클럽
    private String meetName; //모임명
    private String meetTitle; //게시글제목
    private String meetContent; //게시글내용
    private String meetImage; //게시글 메인사진
    private String meetDate; //게시글 작성일자
    private String meetDday; //모임일자
    private int meetMemCnt; //현재참석인원수
    private int meetMaxMemCnt; //최대참석인원수
    private int meetHitCount; //게시글 조회수
    private int meetLike; //게시글 좋아요수
    private int meetHow; //1선착순 2승인제
    private int meetEntryfee; //1기본참가비있음 2없음
    public int meetMoney; //참가비금액
    private String meetPlace; //모임장소

    private String email; //회원 이메일
    private int meetMemStatus; //0승인대기중 1방장 2회원 3블랙

    private int meetReviewNum; //리뷰 넘
    private String meetReviewContent; //리뷰 내용
    private String meetReviewDate; //리뷰 작성날짜
    private String meetReviewImg; //리뷰 이미지

}
