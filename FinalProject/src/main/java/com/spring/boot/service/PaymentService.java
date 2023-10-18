package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.PaymentInfoDTO;
import com.spring.boot.dto.userPointDTO;

public interface PaymentService {
    
    // 결제 정보를 삽입합니다.
    public void insertPaymentInfo(PaymentInfoDTO paymentInfoDTO) throws Exception;

    // 사용자의 포인트를 업데이트합니다.
    public void updateUserPoint(userPointDTO userPointDTO) throws Exception;

    // 주어진 모임 번호의 meetMoney를 조회합니다.
    public int getMeetMoney(int meetListNum) throws Exception;

    // 주어진 이메일의 사용자의 포인트 잔액을 조회합니다.
    public int getUserPoint(String email) throws Exception;

    // 주어진 이메일의 사용자의 결제 내역을 조회합니다.
    public List<PaymentInfoDTO> findByEmail(String email) throws Exception; 


    // 이후 필요한 추가적인 비즈니스 로직 메서드를 여기에 추가할 수 있습니다.
}