package com.spring.boot.mapper;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.collection.ChatRoomCollection;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoomCollection, String>{
    // In은 or 개념 All 은 And 개념으로 
    // A,B가 있을 때 In(A,B)는 A또는 B가 속한 리스트 뽑아오고
    // All은 A와 B 둘다 속한 리스트만 뽑아다줌
    public List<ChatRoomCollection> findByUsersIn(String users);
    public ChatRoomCollection findByRoomId(String roomId);

}
