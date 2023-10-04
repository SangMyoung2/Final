package com.spring.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.dto.PaymentInfoDTO;
import com.spring.boot.service.PaymentService;

@Controller
@RequestMapping(value = "/")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/pay.action")
	public ModelAndView pay(){
		// System.out.println("pay controller 들어옴");
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("pay/pay");
		
		return mav;
	}


    @PostMapping("/payment-info") 
    // 이 메서드는 클라이언트로부터 '/payment-info'로 들어오는 POST 요청을 처리하고,
    // 요청 본문에 포함 된 결제 정보를 서비스 레이어에 전달하여 처리한다.
    // 처리가 성공하면 성공 메세지와 함께 HTTP 200 상태 코드를, 실패하면 에러 메세지와 함께 HTTP 500 상태 코드를 반환.
    public ResponseEntity<String> receivePaymentInfo(@RequestBody PaymentInfoDTO paymentInfoDTO) {
        try {
            paymentService.processPaymentInfo(paymentInfoDTO);
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
