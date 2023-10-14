package com.spring.boot.dto;

import com.spring.boot.dto.ChatDTO.MessageType;

import lombok.Data;

@Data
public class ChatImageDTO {
    private String roomId;
    private String message;
    private MessageType type;
}
