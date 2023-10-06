package com.spring.boot.chat;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.spring.boot.dto.ChatRoom;

import lombok.extern.slf4j.Slf4j;


@Repository
@Slf4j
public class ChatDAO {
    
    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init(){
        chatRoomMap = new LinkedHashMap<>();
    }

    //전체 채팅방 조회
    public List<ChatRoom> findAllChatRoom(){
        //채팅방 생성 순서를 최근순으로 반환
        List chatRooms = new ArrayList<ChatRoom>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    // roomId 기준 채팅방 찾기
    public ChatRoom findRoomById(String roomId){
        return chatRoomMap.get(roomId);
    }

    // 채팅방 만들기
    public ChatRoom createChatRoom(String roomName,String roomMaster){
        ChatRoom chatRoom = new ChatRoom().create(roomName, roomMaster);

        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);

        return chatRoom;
    }

    //채팅방 인원 +1
    public void plusUserRoom(String roomId){
        ChatRoom room = chatRoomMap.get(roomId);
        room.setUserCount(room.getUserCount() + 1);
    }

    //채팅방 인원 -1
    public void minusUserRoom(String roomId){
        ChatRoom room = chatRoomMap.get(roomId);
        room.setUserCount(room.getUserCount() - 1);
    }

    // 채팅방 유저 리스트에 유저 추가
    public String addUser(String roomId, String userName){
        ChatRoom room = chatRoomMap.get(roomId);
        String userUUID = UUID.randomUUID().toString();

        //아이디 중복확인 후 추가
        room.getUserlist().put(userUUID,userName);

        return userUUID;
    }

    // 채팅방 유저 이름 중복 확인
    public String isDuplicateName(String roomId, String userName){
        
        System.out.println("룸아이디 : " + roomId);

        ChatRoom room = chatRoomMap.get(roomId);
        System.out.println("챗 룸 맵 : " + chatRoomMap);
        System.out.println("룸 : " + room);

        String tmp = userName;

        System.out.println("userName = " + tmp);

        // while(room.getUserlist().containsValue(tmp)){
        //     int randomNum = (int)(Math.random() * 100) + 1;
        //     tmp = userName + randomNum;
        // }
        return tmp;
    }

    // 채팅방 유저 리스트 삭제
    public void delUser(String roomId, String userUUID){
        ChatRoom room = chatRoomMap.get(roomId);

        room.getUserlist().remove(userUUID);
    }

    // 채팅방 유저이름 조회
    public String getUserName(String roomId, String userUUID){
        ChatRoom room = chatRoomMap.get(roomId);
        return room.getUserlist().get(userUUID);
    }

    // 채팅방 전체 userlist 조회
    public ArrayList<String> getUserList(String roomId){
        ArrayList<String> list = new ArrayList<>();
        
        ChatRoom room = chatRoomMap.get(roomId);

        // hashmap 을 for 문을 돌린 후
        // value 값만 뽑아내서 list 에 저장 후 reutrn
        room.getUserlist().forEach((key, value) -> list.add(value));
        return list;
    }     




}
