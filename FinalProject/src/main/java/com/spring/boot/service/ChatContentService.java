package com.spring.boot.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.spring.boot.collection.ChatContentCollection;
import com.spring.boot.collection.ChatContentCollection.ChatMessage;
import com.spring.boot.collection.ChatRoomCollection;
import com.spring.boot.mapper.ChatContentRepository;
import com.spring.boot.util.ChatUtil;

@Service
public class ChatContentService {

    @Autowired
    private final ChatContentRepository chatContentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ChatUtil chatUtil;

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

    public List<ChatMessage> findAllByRoomIdInTime(String roomId, String entryTime){

        List<ChatContentCollection> chats = chatContentRepository.findAllByRoomIdIn(roomId);
        if(chats == null) return null;

        LocalDateTime entry = chatUtil.formatterDateTime(entryTime);

        List<ChatMessage> result = new ArrayList<>();

        for(ChatContentCollection chatContent : chats){
            List<ChatMessage> chat = chatContent.getChats();
            for(ChatMessage chatMessage : chat){
                LocalDateTime t = chatUtil.formatterDateTime(chatMessage.getTime());
                if(t.isAfter(entry)){
                    result.add(chatMessage);
                }
            }
        }

        return result;
    }

    public void updateChat(ChatContentCollection lists){
        chatContentRepository.save(lists);
    }

    public ChatContentCollection getChatContentWithReadCountGreaterThanZero(String roomId) {
        ChatContentCollection chatContentCollection = chatContentRepository.findByRoomIdIn(roomId);
        if(chatContentCollection == null){
            return null;
        }
        List<ChatMessage> chatMessage = chatContentCollection.getChats();
        List<ChatMessage> result = new ArrayList<>();
        for(ChatMessage c : chatMessage){
            //System.out.println("ReadCount : " + c.getReadCount());
            if(c.getReadCount() > -1){
                result.add(c);
            }
        }

        chatContentCollection.setChats(result);

        //System.out.println("Result 들어간 chatContent : " + chatContentCollection);

        return chatContentCollection;
    }

    public void updateReadCount(String roomId){
        ChatContentCollection chatContentCollection = chatContentRepository.findByRoomIdIn(roomId);
        if(chatContentCollection == null){
            return;
        }
        List<ChatMessage> chatMessage = chatContentCollection.getChats();
        List<ChatMessage> result = new ArrayList<>();
        for(ChatMessage c : chatMessage){
            if(c.getReadCount() <= 0){
                c.setReadCount(-1);
            }
            result.add(c);
        }
        chatContentCollection.setChats(result);
        chatContentRepository.save(chatContentCollection);
    }

    public Map<String, Integer> checkNotReadMessage(List<ChatRoomCollection> chats, String username){
        // System.out.println("checkNotReadMessage 들어온 유저 : " + username);
        Map<String, Integer> notReadCount = new HashMap<>();
        
        outerLoop:
        for(int i=0; i<chats.size(); i++){
            int result = 0;
            // System.out.println("룸 : " + roomIds.get(i));
            String roomIds = chats.get(i).getRoomId();
            String userEntry = chats.get(i).getEntryDate().get(username);
            LocalDateTime userEntryDate = chatUtil.formatterDateTime(userEntry);

            for(int j=0; j<100; j++){
                String day = chatUtil.todayMinusDay(j);
                ChatContentCollection chatContentCollection = chatContentRepository.findByRoomIdIn(roomIds + day);
                if(chatContentCollection == null){
                    notReadCount.put(roomIds, result);
                    continue outerLoop;
                }
                List<ChatMessage> chatMessage = chatContentCollection.getChats();
                int cnt = 0;
                for(int k=0; k<chatMessage.size(); k++){
                    LocalDateTime chatDateTime = chatUtil.formatterDateTime(chatMessage.get(k).getTime());
                    if(chatDateTime.isAfter(userEntryDate) && !chatMessage.get(k).getReadUser().contains(username)){
                        cnt++;
                    }
                }

                if(cnt == 0) {
                    notReadCount.put(roomIds, result);
                    continue outerLoop;
                }
                result += cnt;
            }
            notReadCount.put(roomIds, result);
        }

        return notReadCount;
    }

}

