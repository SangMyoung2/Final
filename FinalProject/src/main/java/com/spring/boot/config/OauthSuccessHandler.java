package com.spring.boot.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.spring.boot.dto.SessionUser;

public class OauthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        
        HttpSession session = request.getSession();
        Object principal = authentication.getPrincipal();

        if (principal instanceof SessionUser) {
            SessionUser userDetails = (SessionUser) principal;
            SessionUser sessionUser = new SessionUser(userDetails);
            session.setAttribute("user", sessionUser);

        }

        // 리다이렉트 수행
        response.sendRedirect("/");
    }
    
}
