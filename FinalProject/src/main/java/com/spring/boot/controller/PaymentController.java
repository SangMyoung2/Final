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
import com.spring.boot.dto.SessionUser;
import com.spring.boot.model.Users;
import com.spring.boot.service.PaymentService;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/pay.action") // 결제 페이지
	public ModelAndView pay() {
        ModelAndView mav = new ModelAndView();
		mav.setViewName("pay/pay");
		return mav;
	}

    @PostMapping("/payment-info")
    public ResponseEntity<String> receivePaymentInfo(@RequestBody PaymentInfoDTO paymentInfoDTO, HttpServletRequest req) {

        HttpSession session = req.getSession();
        Users social = (Users) session.getAttribute("user");
        Users user1 = (Users) session.getAttribute("user1");

        String userEmail;
        if (social != null) {
            userEmail = social.getEmail();
        } else if (user1 != null) {
            userEmail = user1.getEmail();
        } else {
            return new ResponseEntity<>("No user session found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("현재 세션에 있는 유저 이메일 : " + userEmail);
        paymentInfoDTO.setEmail(userEmail);

        try {
            paymentService.insertPaymentInfo(paymentInfoDTO);

            Map<String, Object> params = new HashMap<>();
            params.put("email", userEmail);
            params.put("paid_amount", paymentInfoDTO.getPaid_amount());

            paymentService.updateOrInsertUserPointWithMap(params);
            return new ResponseEntity<>("Payment data received successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while processing payment data", HttpStatus.INTERNAL_SERVER_ERROR);
        }   
    }

    @GetMapping("/paymentInfo.action") 
    public ModelAndView paymentInfo(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = req.getSession();
        Object sessionUserObject = session.getAttribute("user");

        String userEmail;

        if(sessionUserObject instanceof Users) {
            Users user1 = (Users) sessionUserObject;
            userEmail = user1.getEmail();
        } else if(sessionUserObject instanceof SessionUser) {
            SessionUser social = (SessionUser) sessionUserObject;
            userEmail = social.getEmail();
        } else {
            mav.setViewName("pay/paymentErrorPage");
            return mav;
        }

        if(userEmail == null || userEmail.isEmpty()){
            mav.setViewName("pay/paymentErrorPage");
            return mav;
        }

        try {
            List<PaymentInfoDTO> paymentInfo = paymentService.findByEmail(userEmail);
            mav.addObject("paymentInfo", paymentInfo);
            mav.setViewName("pay/paymentInfo");
        } catch (Exception e) {
            mav.setViewName("pay/paymentErrorPage");
        }
        return mav;
    }


    @GetMapping("/paySuccessPage")
    public String paySuccessPage() {
        return "pay/paySuccessPage"; 
    }

    @GetMapping("/payFailurePage")
    public String payFailurePage() {
        return "pay/payFailurePage"; 
    }
}
