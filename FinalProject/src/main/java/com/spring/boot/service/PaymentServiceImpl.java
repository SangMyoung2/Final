package com.spring.boot.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.mapper.PaymentMapper;
import com.google.gson.JsonObject;
import com.spring.boot.dto.PaymentInfoDTO;
import com.spring.boot.dto.userPointDTO;


@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    
    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public void processPaymentInfo(PaymentInfoDTO paymentInfoDTO){

        System.out.println("Paid Amount: " + paymentInfoDTO.getPaid_amount());

        try {
            paymentMapper.insertPaymentInfo(paymentInfoDTO);
            
            // 결제 정보 처리 후, UserPoint 업데이트
            // 임의의 이메일 주소 가져오기 (이 부분은 단순화된 예시입니다. 실제로는 이메일 주소를 DB에서 조회해야 합니다.)
            String userEmail = "test@email.com"; 
            userPointDTO userPointDTO = new userPointDTO();
            userPointDTO.setUser_email(userEmail); 
            userPointDTO.setPoint_balance(paymentInfoDTO.getPaid_amount());
            updateUserPoint(userPointDTO);
            
        } catch (Exception e) {
            e.printStackTrace(); // 이렇게 해서 로그에 어떤 예외가 출력 되는지 확인
            throw e;
        }
    }

    @Override
    public void updateUserPoint(userPointDTO userPointDTO) {
        paymentMapper.updateUserPoint(userPointDTO);
    }

    public void joinGroupAndDeductPoint(String userEmail, int meetListNum) {

        
        String actualEmail = extractEmail(userEmail);
        System.out.println("Extracted User email: " + actualEmail);

        System.out.println("Parsed User email: " + actualEmail);


        try {

            int meetMoney = paymentMapper.getMeetMoney(meetListNum);
            int currentUserPoint = paymentMapper.getUserPoint(actualEmail);

            System.out.println("Mapper에서 반환된 meetMoney 값: " + meetMoney);
            System.out.println("Mapper에서 반환된 currentUserPoint 값: " + currentUserPoint);

            if(currentUserPoint < meetMoney) {
                
                
                throw new RuntimeException("Insufficient points!"); // 혹은 다른 적절한 예외 처리 방식을 사용
                
            }

            System.out.println("포인트 확인: " + (currentUserPoint < meetMoney ? "포인트 부족" : "포인트 충분"));

            userPointDTO userPointDTO = new userPointDTO();
            userPointDTO.setUser_email(actualEmail);
            userPointDTO.setPoint_balance(-meetMoney);

            updateUserPoint(userPointDTO);


            // 여기서 모임에 가입시키는 로직을 추가하면 됩니다.
            // 예: groupService.joinGroup(userEmail, meetListNum);

        } catch(Exception e) {
            e.printStackTrace();
        }

    }


        public String extractEmail(String input) {
        String emailRegex = "\"userEmail\":\"([^\"]+)\"";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }



}