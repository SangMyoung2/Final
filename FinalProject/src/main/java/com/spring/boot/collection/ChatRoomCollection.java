package com.spring.boot.collection;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "chatroom")
public class ChatRoomCollection {
    
    @Id
    private String roomId;

    private String roomName;
    private String roomMaster;
    private String type;
    private int userCount;
    private String createDate;

    private List<String> users;

    public void setLists(String user){
        if(users == null || users.isEmpty()){
            users = new ArrayList<String>();
        }
        users.add(user);
    }
}
