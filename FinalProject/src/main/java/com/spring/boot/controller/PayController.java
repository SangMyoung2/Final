package com.spring.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.dto.PayDTO;
import com.spring.boot.service.PayService;

@Controller
public class PayController {

	@Autowired
	private PayService payService;

    @GetMapping("/pay.action")
	public ModelAndView pay(){
		// System.out.println("pay controller 들어옴");
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("pay/pay");
		
		return mav;
	}

    @PostMapping("/processPayment")
    public String processPayment(PayDTO payDTO) {
        // 결제 처리 로직을 수행하고 성공 시
        if (payService.processPayment(payDTO)) {
            // 결제 성공 시 리다이렉트할 페이지 경로를 반환
            return "redirect:/paySuccessPage";
        } else {
            // 결제 실패 시 리다이렉트할 페이지 경로를 반환
            return "redirect:/payFailurePage";
        }
    }
    

}

