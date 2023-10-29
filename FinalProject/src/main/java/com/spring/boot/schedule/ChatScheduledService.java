package com.spring.boot.schedule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.spring.boot.collection.ChatContentCollection;
import com.spring.boot.collection.ChatContentCollection.ChatMessage;
import com.spring.boot.mapper.ChatContentRepository;

@Service
public class ChatScheduledService {
    
    @Autowired
    private final ChatContentRepository chatContentRepository;
    public ChatScheduledService(ChatContentRepository chatContentRepository){
        this.chatContentRepository = chatContentRepository;
    }

    @Scheduled(fixedRate = 10000) // 10초마다 실행
    public void chatReadCountCheckScheduled() {
        // System.out.println("스케쥴 시작--------------------------------------------------");
        List<ChatContentCollection> chatContentCollection = chatContentRepository.findAll();
        if(chatContentCollection == null){
            return;
        }

        Iterator it = chatContentCollection.iterator();
        while(it.hasNext()){
            ChatContentCollection chats = (ChatContentCollection) it.next();
            List<ChatMessage> chatMessage = chats.getChats();
            List<ChatMessage> result = new ArrayList<>();

            for(ChatMessage c : chatMessage){
                if(c.getReadCount() <= 0){
                    c.setReadCount(-1);
                }
                result.add(c);
            }
            chats.setChats(result);
            chatContentRepository.save(chats);
        }
    }













}
