package com.spring.boot.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GatchiDTO {
    private int meetListNum;
    private int meetCheck;
    private int meetCtgNum;
    private String meetName;
    private String meetTitle;
    private String meetContent;
    private String meetImage;
    private Date meetDate;
    private Date meetDday;
    private int meetMemCnt;
    private int meetMaxMemCnt;
    private int meetHitCount;
    private int meetLike;
    private int meetHow;
    private int meetEntryFee;
    private int meetMoney;
    private String meetPlace;
}
