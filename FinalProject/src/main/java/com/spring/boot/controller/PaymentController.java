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
