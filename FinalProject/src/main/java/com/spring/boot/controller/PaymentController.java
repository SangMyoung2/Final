package com.spring.boot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.dto.PaymentInfoDTO;
import com.spring.boot.dto.userPointDTO;
import com.spring.boot.model.Users;
import com.spring.boot.service.PaymentService;

@Controller // RestController를 사용하면 결제 성공 후 페이지가 안 뜨고, Controller 쓰면 뜬다
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @GetMapping("/pay.action") // 결제 페이지
	public ModelAndView pay(){

        ModelAndView mav = new ModelAndView();
		
		mav.setViewName("pay/pay");
		
		return mav;
	}

    @GetMapping("/paymentInfo.action") // 결제 내역 페이지
    public ModelAndView paymentInfo(HttpServletRequest req){
        ModelAndView mav = new ModelAndView();

        // 세션에서 email 값을 가져온다.
        HttpSession session = req.getSession();
        Users user = (Users)session.getAttribute("user1");
        String email = user.getEmail();

        // email 값 체크 (null이거나 비어 있는지)
        if(email == null || email.isEmpty()){
            mav.setViewName("pay/paymentErrorPage");
            return mav;
        }

        try {
            List<PaymentInfoDTO> paymentInfo = paymentService.findByEmail(email);
            mav.addObject("paymentInfo", paymentInfo);
            mav.setViewName("pay/paymentInfo");
        } catch (Exception e) {
            mav.setViewName("pay/paymentErrorPage");
        }
        return mav;
    }


    
    // 이 메서드는 클라이언트로부터 '/payment-info'로 들어오는 POST 요청을 처리하고,
    // 요청 본문에 포함 된 결제 정보를 서비스 레이어에 전달하여 처리한다.
    // 처리가 성공하면 성공 메세지와 함께 HTTP 200 상태 코드를, 실패하면 에러 메세지와 함께 HTTP 500 상태 코드를 반환.
    @PostMapping("/payment-info")
    public ResponseEntity<String> receivePaymentInfo(@RequestBody PaymentInfoDTO paymentInfoDTO, HttpServletRequest req) {
        
        //포인트 잔액 데이터 넣기 이메일 같이 넣기

        HttpSession session = req.getSession();
        Users user = (Users)session.getAttribute("user1");
        String userEmail = user.getEmail();

        System.out.println("현재 세션에 있는 유저 이메일 : " + userEmail);

        paymentInfoDTO.setEmail(userEmail);

        try {
            paymentService.insertPaymentInfo(paymentInfoDTO);

            // 결제 정보가 성공적으로 삽입된 후, 포인트 증가 로직 추가
            // 여기에서 Map을 사용합니다.
            Map<String, Object> params = new HashMap<>();
            params.put("email", userEmail);
            params.put("paid_amount", paymentInfoDTO.getPaid_amount());
        
        // 서비스 메서드에 Map을 전달
        paymentService.updateOrInsertUserPointWithMap(params);

        return new ResponseEntity<>("Payment data received successfully!", HttpStatus.OK);
        } catch (Exception e) {
        return new ResponseEntity<>("Error while processing payment data", HttpStatus.INTERNAL_SERVER_ERROR);
        }   
    }

    @GetMapping("/paySuccessPage")
    public String paySuccessPage() {
        return "pay/paySuccessPage"; // "pay/paySuccessPage"는 뷰 템플릿의 경로입니다.
    }

    @GetMapping("/payFailurePage")
    public String payFailurePage() {
        return "pay/payFailurePage"; 
    }



}
