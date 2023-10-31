package com.spring.boot.service;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.spring.boot.dao.BaseAuthUserRepository;
import com.spring.boot.dto.OAuthAttributes;
import com.spring.boot.dto.SessionUser;
import com.spring.boot.dto.userPointDTO;
import com.spring.boot.model.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BaseCustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
	
	@Autowired
	private final BaseAuthUserRepository baseAuthUserRepository;
	
	@Autowired
	private final HttpSession httpSession;

	@Autowired
	private final PaymentService paymentService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2UserService<OAuth2UserRequest, OAuth2User> oauthUserService = 
				new DefaultOAuth2UserService();
		
		OAuth2User oauth2User = oauthUserService.loadUser(userRequest);
		
		// 간편 로그인을 진행하는 플랫폼(google, naver, kakoa...)
		
		String registrationId = 
				userRequest.getClientRegistration().getRegistrationId();
		
		//oauth2 로그인 진행시 key가 되는 필드값(primary key역할)
		//구글:sub, 네이버:response, 카카오:id
		
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
										.getUserInfoEndpoint().getUserNameAttributeName();
		
		System.out.println(userNameAttributeName); // sub, id, response
		
		//로그인을 통해 가져온 OAuth2User의 속성을 담아두는 메소드
		OAuthAttributes attributes = 
				OAuthAttributes.of(registrationId, userNameAttributeName, oauth2User.getAttributes());
		
		System.out.println(attributes.getAttributes()); //JSON형태
		
		//응답받은 속성을 authUser 객체에 넣음
		Users authUser = saveOrUpdate(attributes);
		
		System.out.println(authUser.getName() + "1111");
		
		//SessionUser : 세션에 사용자 정보를 저장하기 위한 DTO 클래스
		httpSession.setAttribute("user", new SessionUser(authUser));

		
		try {
			userPointDTO dto = paymentService.getReadUserPoint(authUser.getEmail());
			if(dto == null){
                paymentService.insertUserAfterSignUp(authUser.getEmail());
            }
		} catch (Exception e) {
			// TODO: handle exception
		}


		
		return new DefaultOAuth2User(
				Collections.singleton(
					new SimpleGrantedAuthority(authUser.getRoleKey())),
					attributes.getAttributes(),
					attributes.getNameAttributeKey());
	}
	
	//구글 사용자 정보가 업데이트 되었을 때 반영하는 메서드
	private Users saveOrUpdate(OAuthAttributes attributes) {
		
		Users authUser = 
				baseAuthUserRepository.findByEmail(attributes.getEmail())
				.map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
				.orElse(attributes.toEntity());
		
		return baseAuthUserRepository.save(authUser);
		
	}
	
}
