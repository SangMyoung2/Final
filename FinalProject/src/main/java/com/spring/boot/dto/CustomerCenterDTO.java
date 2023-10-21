package com.spring.boot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCenterDTO {
    private String centerSubject; // 질문 주제 (ex:소셜링, 회원가입 등)
    private String centerQuestion; // 질문 (ex:소셜링이 무엇인가요?)
    private String centerAnswer; // 답변 (ex:소셜링은 ~입니다)
}
