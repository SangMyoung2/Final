package com.spring.boot.dto;

import java.util.HashMap;
import java.util.UUID;

import lombok.Data;

@Data
public class ChatRoom {
    private String roomId;
    private String roomName;
    private String roomMaster;
    private int userCount;

    private HashMap<String, String> userlist = new HashMap<String, String>();

    public ChatRoom create(String roomName,String roomMaster){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.roomName = roomName;
        chatRoom.roomMaster = roomMaster;

        return chatRoom;
    }

}
