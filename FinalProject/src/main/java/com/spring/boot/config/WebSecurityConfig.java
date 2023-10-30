package com.spring.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.spring.boot.model.BaseAuthRole;
import com.spring.boot.service.BaseCustomOAuth2UserService;
import com.spring.boot.service.UserSecurityService;
import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	//Service등록
	@Autowired
	private final BaseCustomOAuth2UserService baseCustomOAuth2UserService;
	
	
	
	private final UserSecurityService userSecurityService;
	

	@Bean
    public SuccessHandler SuccessHandler() {
        return new SuccessHandler();
    }

	 @Bean
    public OauthSuccessHandler OauthSuccessHandler() {
        return new OauthSuccessHandler();
	}
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception{
		
			http
			.csrf().disable().headers().frameOptions().disable()
		
			.and()
			.authorizeRequests()
			.antMatchers
			("/","/rePWD.action","/mainReview.action","/mainMap.action","/challengeList.action","/communiFindList.action","/meetMateList.action","/customercenter.action","/image/**","/sendSMS","/css/**","/js/**","/signup.action","/signup_ok.action","/login_ok.action","/findID.action","/findPWD.action","/login.action","/reFindList")
			.permitAll()
			
		
			.antMatchers("/api/vi/**")
			.hasRole(BaseAuthRole.USER.name())
			.anyRequest().authenticated()
			.and()
			
			
			

			.formLogin()
			.loginPage("/login.action")
			.permitAll()
			.successHandler(SuccessHandler()) 
			.and()
			
			.logout()
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

			http.oauth2Login()
			.successHandler(OauthSuccessHandler())
			.userInfoEndpoint()
			.userService(baseCustomOAuth2UserService);
		
		  

       return http.build();
   }

   @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		}
	
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration)
	throws Exception{
		
		//AuthenticationManager 는 스프링의 security인증을 담당함
		
		//AuthenticationManager Bean생성시 스프링의 내부 동작으로
		//위에서 작성한 UserSecurityService와 PasswordEncoder가
		//자동으로 설정 된다
		
		return authenticationConfiguration.getAuthenticationManager();
	}
}
