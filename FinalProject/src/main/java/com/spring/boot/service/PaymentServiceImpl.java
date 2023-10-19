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

    private String getCurrentUserEmail() {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user == null) {
            System.out.println("세션 정보 없음");
            throw new RuntimeException("User is not logged in or session has expired.");
        }
        System.out.println("세션에서 가져온 이메일: " + user.getEmail());
        return user.getEmail();
    }

    @Override
    public void processPaymentInfo(PaymentInfoDTO paymentInfoDTO){

        try {
        paymentMapper.insertPaymentInfo(paymentInfoDTO);
        
        // 세션에서 로그인된 사용자의 이메일 주소 가져오기
        String email = getCurrentUserEmail(); 
        
        
        // 해당 이메일 주소를 사용하여 userPointDTO를 업데이트하기
        int currentBalance = paymentMapper.getUserPoint(email);
        userPointDTO userPoint = new userPointDTO();
        userPoint.setUseremail(email);
        userPoint.setPointBalance(currentBalance + paymentInfoDTO.getPaid_amount());
        
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
            userPointDTO.setUseremail(emailFromSession);
            userPointDTO.setPointBalance(-meetMoney);

            updateUserPoint(userPointDTO);


            // 여기서 모임에 가입시키는 로직을 추가하면 됩니다.
            // 예: groupService.joinGroup(userEmail, meetListNum);

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getUserPoint(String useremail){
        return paymentMapper.getUserPoint(useremail);
    }

    @Override
    public void updateUserUsePoint(userPointDTO userPointDTO){
        paymentMapper.updateUserUsePoint(userPointDTO);
    }
}