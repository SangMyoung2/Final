package com.spring.boot.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    public enum MessageType{
        ENTER, TALK, MYTALK, LEAVE, IMAGE;
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private String time;
    private MultipartFile image;
}
