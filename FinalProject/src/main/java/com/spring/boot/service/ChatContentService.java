package com.spring.boot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

    public ChatContentCollection getChatContentWithReadCountGreaterThanZero(String roomId) {
        ChatContentCollection chatContentCollection = chatContentRepository.findByRoomIdIn(roomId);

        List<ChatMessage> chatMessage = chatContentCollection.getChats();
        List<ChatMessage> result = new ArrayList<>();
        for(ChatMessage c : chatMessage){
            System.out.println("ReadCount : " + c.getReadCount());
            if(c.getReadCount() > 0){
                result.add(c);
            }
        }

        chatContentCollection.setChats(result);

        System.out.println("Result 들어간 chatContent : " + chatContentCollection);

        return chatContentCollection;
    }

}
