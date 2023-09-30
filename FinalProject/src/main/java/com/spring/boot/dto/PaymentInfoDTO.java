package com.spring.boot.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class PaymentInfoDTO {
    private String user_email;
    private int paid_amount;
    private Date paid_at;
    private String card_name;
    private String card_number;
    private String apply_num;
    private String product_name;
    private String status;
}
