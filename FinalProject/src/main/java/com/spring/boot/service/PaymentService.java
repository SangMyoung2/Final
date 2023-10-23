package com.spring.boot.service;

import java.util.List;
import java.util.Map;

import com.spring.boot.dto.PaymentInfoDTO;
import com.spring.boot.dto.userPointDTO;

public interface PaymentService {
    
    // 결제 정보를 삽입합니다.
    public void insertPaymentInfo(PaymentInfoDTO paymentInfoDTO) throws Exception;

    // 사용자의 포인트를 업데이트합니다.
    public void updateOrInsertUserPointWithMap(Map<String, Object> params) throws Exception;

    // 주어진 이메일의 사용자의 포인트 잔액을 조회합니다.
    public int getUserPoint(String email) throws Exception;

    // 주어진 이메일의 사용자의 결제 내역을 조회합니다.
    public List<PaymentInfoDTO> findByEmail(String email) throws Exception;

    // 시작날짜와 종료날짜를 받아서 사용자의 결제 내역을 조회합니다.
    List<PaymentInfoDTO> findByEmailBetweenDates(String email, String startDate, String endDate) throws Exception;


    public void updateUserPoint(userPointDTO userPointDTO);
    public void updateUserUsePoint(userPointDTO userPointDTO);
    // 이후 필요한 추가적인 비즈니스 로직 메서드를 여기에 추가할 수 있습니다.
}