package com.spring.boot.controller;

import java.lang.management.MemoryType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.spring.boot.chat.Chat;
import com.spring.boot.chat.ChatDAO;
import com.spring.boot.collection.ChatContentCollection;
import com.spring.boot.collection.ChatContentCollection.ChatMessage;
import com.spring.boot.dto.ChatDTO;
import com.spring.boot.dto.ChatImageDTO;
import com.spring.boot.dto.ChatDTO.MessageType;
import com.spring.boot.service.ChatContentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final SimpMessageSendingOperations template;

    @Autowired
    private ChatDAO repository;

    @Autowired
    private final ChatContentService chatContentService;

    private String userUUID;

    // 채팅방 별 데이터 저장할 공간
    private Map<String, ChatContentCollection> chatMaps = new ConcurrentHashMap<>();

    int cnt = 0;


    // MessageMapping 을 통해 webSocket 로 들어오는 메시지를 발신 처리한다.
    // 이때 클라이언트에서는 /pub/chat/message 로 요청하게 되고 이것을 controller 가 받아서 처리한다.
    // 처리가 완료되면 /sub/chat/room/roomId 로 메시지가 전송된다.
    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload ChatDTO chat, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("chat/enterUser 들어옴....");
        // 채팅방 유저+1
        // repository.plusUserRoom(chat.getRoomId());

        // 채팅방에 유저 추가 및 UserUUID 반환
        //userUUID = repository.addUser(chat.getRoomId(), chat.getSender());

        // 반환 결과를 socket session 에 userUUID 로 저장
        // headerAccessor.getSessionAttributes().put("userUUID", userUUID);
        // headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());
        
        chat.setMessage(chat.getSender() + " 님 환영합니다!! 메롱");
        
        System.out.println("chat.getRoomId : " + chat.getRoomId());

        // DB데이터 가져오던 줄

        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);

    }

    // 해당 유저
    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDTO chat,SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("chat sendMessage 들어옴...");
        log.info("CHAT {}", chat);
        chat.setMessage(chat.getMessage());

        // 현재시간 가져오기
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = currentDateTime.format(formatter);
        String formattedCollection = currentDateTime.format(formatter2);
        if(!chatMaps.containsKey(chat.getRoomId())){
            // 채팅내역이 없으면(Map에 안만들어져 있으면)
            System.out.println("채팅방이 없으면 들어오는곳");
            ChatContentCollection chatContentCollection = new ChatContentCollection(chat.getRoomId()+formattedCollection);
            chatMaps.put(chat.getRoomId(),chatContentCollection);
        }

        System.out.println(chat.getRoomId());
        System.out.println(chat.getSender());
        System.out.println(chat.getMessage());
        System.out.println(formattedDateTime);
        
        // 채팅내역 입력
        ChatMessage c = new ChatMessage();
        c.setSender(chat.getSender());
        c.setMessage(chat.getMessage());
        c.setTime(formattedDateTime);

        // 채팅내역 추가
        chatMaps.get(chat.getRoomId()).addLists(c);

        // 채팅내역 추가해서 새로고침
        // ChatContentCollection chatContentCollection = chatContentService.findList(chat.getRoomId());
        // chatContentCollection.addLists(c);
        // chatMaps.replace(chat.getRoomId(), chatContentCollection);

        //System.out.println("chatMaps 데이터 : " + chatMaps.get(chat.getRoomId()));
        
        if(cnt >= 5){
            System.out.println("DB 인서트");
            chatContentService.insertChat(chatMaps.get(chat.getRoomId()));
            cnt = 0;
        }else{
            System.out.println("cnt ++ -----------------------------------");
            cnt++;
        }

        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }

    // 유저 퇴장 시에는 EventListener 을 통해서 유저 퇴장을 확인
    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("DisConnEvent {}", event);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // stomp 세션에 있던 uuid 와 roomId 를 확인해서 채팅방 유저 리스트와 room 에서 해당 유저를 삭제
        String userUUID = (String) headerAccessor.getSessionAttributes().get("userUUID");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");

        log.info("headAccessor {}", headerAccessor);

        // // 채팅방 유저 -1
        // repository.minusUserRoom(roomId);

        // // 채팅방 유저 리스트에서 UUID 유저 닉네임 조회 및 리스트에서 유저 삭제
        // String username = repository.getUserName(roomId, userUUID);
        // repository.delUser(roomId, userUUID);

        // if (username != null) {
        //     log.info("User Disconnected : " + username);

        //     // builder 어노테이션 활용
        //     ChatDTO chat = ChatDTO.builder()
        //             .type(ChatDTO.MessageType.LEAVE)
        //             .sender(username)
        //             .message(username + " 님 퇴장!!")
        //             .build();

        //     template.convertAndSend("/sub/chat/room/" + roomId, chat);
        // }
    }

    // 채팅에 참여한 유저 리스트 반환
    // @GetMapping("/chat/userlist")
    // @ResponseBody
    // public ArrayList<String> userList(String roomId) {

    //     return repository.getUserList(roomId);
    // }

    // 채팅에 참여한 유저 닉네임 중복 확인
    @GetMapping("/chat/duplicateName")
    @ResponseBody
    public String isDuplicateName(@RequestParam("roomId") String roomId, @RequestParam("username") String username) {

        // 유저 이름 확인

        System.out.println("chatController에서의 룸아이디 : " + roomId);

        String userName = repository.isDuplicateName(roomId, username);
        log.info("동작확인 {}", userName);

        return userName;
    }

    //@SendTo("/chat/receiveData") // 응답을 클라이언트로 보낼 주소
    //@MessageMapping("/chat/sendData") // 클라이언트 요청 주소와 일치
    @GetMapping("/chat/getPrevChatList")
    public Map<String, Object> sendData(@Payload ChatDTO chat){
        System.out.println("sendData 들어옴~");
        System.out.println("chat Room Id : " + chat.getRoomId());
        ChatContentCollection chatContentCollection;
        LocalDateTime currentDateTime = LocalDateTime.now();
        
        // 어제 날짜
        currentDateTime = currentDateTime.minusDays(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = currentDateTime.format(formatter);
        if(!chatMaps.containsKey(chat.getRoomId())){
            // 채팅내역이 없으면(Map에 안만들어져 있으면)
            System.out.println("지효근 바보 채팅방이 없으면 들어오는곳");
            
            chatContentCollection = new ChatContentCollection(chat.getRoomId()+formattedDateTime);

            chatMaps.put(chat.getRoomId(),chatContentCollection);
        }
        
        // List<ChatMessage> chatLists = chatMaps.get(chat.getRoomId()).getChats();
        List<ChatMessage> chatLists = new ArrayList<>();
        List<ChatContentCollection> ccc = chatContentService.findAllByRoomIdIn(chat.getRoomId()+formattedDateTime);
        
        if(chatMaps.get(chat.getRoomId()).getChats().isEmpty()){
            for(ChatContentCollection c : ccc){
                   chatMaps.put(chat.getRoomId(), c);
            }
        }

        System.out.println("DB에서 가져온 chat Size : " + ccc.size());
        for(int i=0; i<ccc.size(); i++){
            for(ChatMessage chatMessage : ccc.get(i).getChats()){
                
                chatLists.add(chatMessage);
                System.out.println(i + "번째 " + "chat 내용 : " + chatMessage);
            }
        }
        
        Map<String, Object> data = new HashMap<>();
        data.put("data", chatLists);

        return data;
    }

    // @MessageMapping("/chat/sendImage")
    // @SendTo("/topic/receiveImage")
    @PostMapping("/chat/sendImage")
    public String receiveImage(){
        System.out.println("이미지ㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣ");
        // String imageData = image.getImage().getOriginalFilename();
        // System.out.println("오리지널 : " + imageData);
        // image.setType(MessageType.IMAGE);

        // System.out.println("이미지 룸 아이디 : " + image.getRoomId());
        // template.convertAndSend("/sub/chat/room/" + image.getRoomId(), image);

        return null;
    }



}