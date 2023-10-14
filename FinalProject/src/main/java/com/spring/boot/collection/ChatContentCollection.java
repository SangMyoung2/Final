package com.spring.boot.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.spring.boot.chat.Chat;

import lombok.Data;

@Data
@Document(collection = "chatcontent")
public class ChatContentCollection {
    
    @Id
    private String roomId;
    private List<ChatMessage> chats;

    public ChatContentCollection(String roomId){
        this.roomId = roomId;
        this.chats = new ArrayList<>();
    }

    public void addLists(ChatMessage c){
        chats.add(c);
    }

    @Data
    public static class ChatMessage{
        private String sender;
        private String message;
        private String time;
        private String type;
        private int readCount;
        private List<String> readUser;
    }
}
