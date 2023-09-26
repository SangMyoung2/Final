package com.spring.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.spring.boot.model.BaseAuthRole;
import com.spring.boot.service.BaseCustomOAuth2UserService;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	//Service등록
	@Autowired
	private final BaseCustomOAuth2UserService baseCustomOAuth2UserService;
	
	
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception{
		
		http
		.csrf().disable().headers().frameOptions().disable()
		.and()
		.authorizeRequests()
		.antMatchers("/","/sendSMS","/css/**","/images/**","/js/**","/signup.action","/login.action","/profile").permitAll()
		
		//"/api/vi/**"변경 불가 구글꺼 풀어주는 거
		.antMatchers("/api/vi/**").hasRole(BaseAuthRole.USER.name())
		.anyRequest().authenticated()
		.and()
		.logout().logoutSuccessUrl("/").invalidateHttpSession(true)
		.and()
		.oauth2Login().defaultSuccessUrl("/main.action").userInfoEndpoint().
		userService(baseCustomOAuth2UserService);
		
		   http
           .sessionManagement()
           .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

       return http.build();
   }
}
