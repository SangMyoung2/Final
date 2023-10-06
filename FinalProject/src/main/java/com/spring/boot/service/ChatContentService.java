package com.spring.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.collection.ChatContentCollection;
import com.spring.boot.collection.ChatContentCollection.ChatMessage;
import com.spring.boot.mapper.ChatContentRepository;

@Service
public class ChatContentService {

    @Autowired
    private final ChatContentRepository chatContentRepository;

    public ChatContentService(ChatContentRepository chatContentRepository){
        this.chatContentRepository = chatContentRepository;
    }

    public void insertChat(ChatContentCollection lists){
        chatContentRepository.save(lists);
    }

    public ChatContentCollection findByRoomIdIn(String roomId){
        return chatContentRepository.findByRoomIdIn(roomId);
    }

    public List<ChatContentCollection> findAllByRoomIdIn(String roomId){
        return chatContentRepository.findAllByRoomIdIn(roomId);
    }

    public void updateChat(ChatContentCollection lists){
        chatContentRepository.save(lists);
    }
}
