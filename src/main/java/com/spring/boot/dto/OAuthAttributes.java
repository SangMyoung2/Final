package com.spring.boot.dto;

import java.util.Map;

import com.spring.boot.model.BaseAuthRole;
import com.spring.boot.model.Users;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
	
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;
	private String picture;
	
	@Builder
	public OAuthAttributes(Map<String, Object> attributes,
				String nameAttributeKey, String name, String email, String picture) {
		
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
		this.picture = picture;
		
	}
	
	//구글 카카오 네이버 구분
	public static OAuthAttributes of(String registrationId,
				String userNameAttributeName, Map<String, Object> attributes) {
		//userNameAttributeName : sub
		
		if(registrationId.equals("kakao")) { // id
			return ofKakao(userNameAttributeName, attributes);
		}
		if(registrationId.equals("naver")) { //response
			return ofNaver("id", attributes);
		}
		// userNameAttributeName : sub
		return ofGoogle(userNameAttributeName,attributes);
	}
	
	/*
	카카오 데이터 넘어오는 방식
			{
				id=2340739652, 
				connected_at=2022-12-14T03:58:53Z, 
				properties=
			{
				nickname=세상은 , 
				profile_image=http://k.kakaocdn.net/dn/Kfxs8/btr1111lT/fkZPX2K3ebIYjAHunGTYK0/img_640x640.jpg, 
				thumbnail_image=http://k.kakaocdn.net/dn/Kfxs8/btr111b1lT/fkZPX2K3ebIYjAHunGTYK0/img_110x110.jpg
			}, 
			kakao_account=
			{
				profile_nickname_needs_agreement=false, 
				profile_image_needs_agreement=false, 
				profile=
				{
					nickname=세상은 , 
					thumbnail_image_url=http://k.kakaocdn.net/dn/Kfxs8/btr111rb1lT/fkZPX2K3ebIYjAHunGTYK0/img_110x110.jpg, 
					profile_image_url=http://k.kakaocdn.net/dn/Kfxs8/btrII111lT/fkZPX2K3ebIYjAHunGTYK0/img_640x640.jpg,
				}, 
				has_email=true, 
				email_needs_agreement=false, 
				is_email_valid=true, 
				is_email_verified=true, 
				email=htaeyong@naver.com
			}
		}
	*/
	@SuppressWarnings("unchecked")
	private static OAuthAttributes ofKakao(String userNameAttributeName,
				Map<String, Object> attributes) {
		
		//kakao_account에 사용자 정보 (email)이 있음
		Map<String, Object> kakaoAccount = 
				(Map<String, Object>)attributes.get("kakao_account");
		
		Map<String, Object> kakaoProfile = 
				(Map<String, Object>)kakaoAccount.get("profile");
		
		return OAuthAttributes.builder()
				.name((String)kakaoProfile.get("nickname"))
				.email((String)kakaoAccount.get("email"))
				.picture((String)kakaoProfile.get("profile_image_url"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
	}
	
	/*
		구글 데이터 넘어오는 방식
		[Google Attribute] - sub

		{
			sub=107123782853678978866,
			name=배수지, 
			given_name=수지, 
			family_name=배, 
			picture=https://lh3.googleusercontent.com/a-/AFdZucppLJTanskdjfbksjdhfoisdfXSA=s96-c, 
			email=digulx2@gmail.com, 
			email_verified=true, 
			locale=ko
		}
	*/
	private static OAuthAttributes ofGoogle(String userNameAttributeName,
				Map<String, Object> attributes) {
		
		return OAuthAttributes.builder()
				.name((String)attributes.get("name"))
				.email((String)attributes.get("email"))
				.picture((String)attributes.get("picture"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
	}
	
	
	/*
		네이버 넘어올 때
		{
			id=ApWwLB5hGFlNvLcBpAU8LNpjbkZ2UyFt-PLmSlpWJt8, 
			profile_image=https://phinf.pstatic.net/contact/20220715_138/1657872143804gFYAa_PNG/avatar_profile.png, 
			email=htaeyong@naver.com, 
			name=홍길동
		}
	
	*/
	@SuppressWarnings("unused")
	private static OAuthAttributes ofNaver(String userNameAttributeName,
			Map<String, Object> attributes) {
	
	Map<String, Object> response = 
			(Map<String, Object>)attributes.get("response");
	
	return OAuthAttributes.builder()
			.name((String)response.get("name"))
			.email((String)response.get("email"))
			.picture((String)response.get("profile_image"))
			.attributes(response)
			.nameAttributeKey(userNameAttributeName)
			.build();
}
	
	
	public Users toEntity() {
		return Users.builder()
				.name(name)
				.email(email)
				.picture(picture)
				.role(BaseAuthRole.GUEST)
				.build();
	}
	
}
