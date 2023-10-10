package com.spring.boot.service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.collection.ChatRoomCollection;
import com.spring.boot.mapper.ChatRoomRepository;

@Service
public class ChatRoomService {
    
    @Autowired
    private final ChatRoomRepository chatRoomRepository;
    
    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public ChatRoomCollection createChat(ChatRoomCollection chatRoomCollection){
        return chatRoomRepository.save(chatRoomCollection);
    }

    public void updateChatRoom(ChatRoomCollection chatRoomCollection){
        chatRoomRepository.save(chatRoomCollection);
    }

    public List<ChatRoomCollection> getLists(){
        List<ChatRoomCollection> lists = chatRoomRepository.findAll();
        Collections.reverse(lists);
        return lists;
    }

    public List<ChatRoomCollection> getFindNameInUsers(String users){
        List<ChatRoomCollection> lists = chatRoomRepository.findByUsersIn(users);
        Collections.reverse(lists);
        return lists;
    }

    public Optional<ChatRoomCollection> getReadDate(String roomId){
        return chatRoomRepository.findById(roomId);
    }

    



}
