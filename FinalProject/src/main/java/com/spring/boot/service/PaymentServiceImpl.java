package com.spring.boot.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.mapper.PaymentMapper;
import com.spring.boot.dto.PaymentInfoDTO;
import com.spring.boot.dto.SessionUser;
import com.spring.boot.dto.userPointDTO;


@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    
    @Autowired
    private HttpSession httpSession;

    @Autowired
    private PaymentMapper paymentMapper;



    @Override
    public void processPaymentInfo(PaymentInfoDTO paymentInfoDTO){

        try {
        paymentMapper.insertPaymentInfo(paymentInfoDTO);
        
        // 세션에서 로그인된 사용자의 이메일 주소 가져오기
        String email = getCurrentUserEmail(); 

        if (email == null) {
            throw new RuntimeException("User not found in session.");
        }
        
        // 해당 이메일 주소를 사용하여 userPointDTO를 업데이트하기
        int currentBalance = paymentMapper.getUserPoint(email);
        userPointDTO userPoint = new userPointDTO();
        userPoint.setEmail(email);
        userPoint.setPoint_balance(currentBalance + paymentInfoDTO.getPaid_amount());
        
        paymentMapper.updateUserPoint(userPoint);
        
    } catch (Exception e) {
        e.printStackTrace(); // 이렇게 해서 로그에 어떤 예외가 출력 되는지 확인
        throw e;
    }
    }

    @Override
    public void updateUserPoint(userPointDTO userPointDTO) {
        paymentMapper.updateUserPoint(userPointDTO);
    }

    public void joinGroupAndDeductPoint(String email, int meetListNum) {

        String emailFromSession = getCurrentUserEmail();

        try {
            int meetMoney = paymentMapper.getMeetMoney(meetListNum);
            int currentUserPoint = paymentMapper.getUserPoint(emailFromSession);

            if(currentUserPoint < meetMoney) {
                throw new RuntimeException("Insufficient points!"); // 혹은 다른 적절한 예외 처리 방식을 사용
            }

            userPointDTO userPointDTO = new userPointDTO();
            userPointDTO.setEmail(emailFromSession);
            userPointDTO.setPoint_balance(-meetMoney);

            updateUserPoint(userPointDTO);


            // 여기서 모임에 가입시키는 로직을 추가하면 됩니다.
            // 예: groupService.joinGroup(userEmail, meetListNum);

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

        private String getCurrentUserEmail() {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

        if (sessionUser == null) {
            System.out.println("세션에 'user' 속성이 없습니다.");
            return null;  // or throw an exception if this is an unexpected case
        } else {
            System.out.println("세션에 'user' 속성이 존재합니다.");
            return sessionUser.getEmail();
        }

    }



}