package com.spring.boot.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private int roomType; // 1 모임중 , 2 모임끝 또는 방삭제
    private List<String> users;

    private Map<String, String> entryDate;

    public void setLists(String user){
        if(users == null || users.isEmpty()){
            users = new ArrayList<String>();
        }
        users.add(user);
    }
    
    public void setEntryDate(String user, String date){
        if(entryDate == null){
            entryDate = new HashMap<>();
        }
        entryDate.put(user, date);
    }
}
