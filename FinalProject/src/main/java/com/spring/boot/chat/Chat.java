package com.spring.boot.chat;

import lombok.Data;

@Data
public class Chat {
    private String roomId;
    private String sendDate;
    private String sender;
    //채팅내역
    private String content;
}
