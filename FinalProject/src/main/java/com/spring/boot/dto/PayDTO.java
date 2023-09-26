package com.spring.boot.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class PayDTO {
    private int paymentsId;
    private int productId;
    private String paymentsStatus;
    private Date paymentsDate;
}
