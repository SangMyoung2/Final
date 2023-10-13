package com.spring.boot.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GatchiDTO {
    private int meetListNum; //index
    private int meetCheck; //1소셜링 2클럽
    private int meetCtgNum; //1문화예술 2맛집 3취미 4여행 5운동
    private String meetName; //모임명
    private String meetTitle; //게시글제목
    private String meetContent; //게시글내용
    private String meetImage; //게시글 메인사진
    private String meetDate; //게시글 작성일자
    private String meetDday; //모임일자
    private int meetMemCnt; //현재참석인원수
    private int meetMaxMemCnt; //최대참석인원수
    private int meetHitCount; //게시글 조회수
    private int meetLikeCount; //게시글 좋아요수
    private int meetHow; //1선착순 2승인제
    private int meetEntryfee; //1기본참가비있음 2없음
    public int meetMoney; //참가비금액
    private String meetPlace; //모임장소
}
