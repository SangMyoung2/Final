package com.spring.boot.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentInfoDTO {
    private String email; // 로그인 된 유저의 이메일
    private int paid_amount; // 결제 금액
    private String paid_at; // 결제 날짜와 시간
    private String pay_method; // 결제 방법 (카드)
    private String card_name; // 카드 이름
    private String card_number; // 카드 번호
    private String apply_num; // 승인 번호
    private String name; // 구입한 상품 이름
    private String status; // 결제 상태
}
