package com.spring.boot.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.spring.boot.model.Users;
import com.spring.boot.service.UserService;

@Component
public class SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
       
       
        HttpSession session = request.getSession();
        //  Object principal = authentication.getPrincipal();


          String userName = authentication.getName();
         Users user = userService.getUser(userName);
              
          session.setAttribute("user1", user);

          
        
        // if (principal instanceof UserDetails) {
        //     UserDetails userDetails = (UserDetails) principal;
        //     session.setAttribute("user1", userDetails);


        //     System.out.println("현재 세션에 저장된 사용자 정보: " + userDetails.getUsername());
        // } else {
        //     System.out.println("세션에 사용자 정보가 없습니다.");
        // }

        
        response.sendRedirect("/"); 
    }
}