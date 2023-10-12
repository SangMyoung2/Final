package com.spring.boot.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
	
}
//@Target(ElementType.PARAMETER)
//어노테이션이 생성될수있는 위치를 지정
//parameter : 메소드의 파라미터로 선언된 객체에서 사용
//@interface : 어노테이션 클래스로 지정