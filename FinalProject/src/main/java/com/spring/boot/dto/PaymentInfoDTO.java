package com.spring.boot.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentInfoDTO {
    private String email;
    private int paid_amount;
    private int paid_at;
    private String pay_method;
    private String card_name;
    private String card_number;
    private String apply_num;
    private String name;
    private String status;
}
