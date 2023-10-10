package com.spring.boot.mapper;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.collection.ChatContentCollection;

@Repository
public interface ChatContentRepository extends MongoRepository<ChatContentCollection, String>{
    
    public ChatContentCollection findByRoomIdIn(String roomId);
    public List<ChatContentCollection> findAllByRoomIdIn(String roomId);
    public ChatContentCollection findByRoomIdAndChatsReadCountGreaterThan(String roomId, int readCount);
    
}
