package com.spring.boot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInfoDTO {
    private int paid_amount;
    private int paid_at;
    private String card_name;
    private String card_number;
    private String apply_num;
    private String name;
    private String status;
}
