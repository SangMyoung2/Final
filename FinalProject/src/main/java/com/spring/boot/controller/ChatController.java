package com.spring.boot.controller;

import java.lang.management.MemoryType;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.spring.boot.chat.Chat;
import com.spring.boot.chat.ChatDAO;
import com.spring.boot.collection.ChatContentCollection;
import com.spring.boot.collection.ChatRoomCollection;
import com.spring.boot.collection.ChatContentCollection.ChatMessage;
import com.spring.boot.dto.ChatDTO;
import com.spring.boot.dto.ChatImageDTO;
import com.spring.boot.dto.ChatDTO.MessageType;
import com.spring.boot.model.Users;
import com.spring.boot.service.ChatContentService;
import com.spring.boot.service.ChatRoomService;
import com.spring.boot.service.UserService;
import com.spring.boot.util.ChatUtil;

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

    @Autowired
    private final ChatRoomService chatRoomService;

    @Autowired
    private final UserService userService;

    @Autowired
    private ChatUtil chatUtil;

    private String userUUID;

    // 채팅방 별 데이터 저장할 공간
    private Map<String, ChatContentCollection> chatMaps = new ConcurrentHashMap<>();
    private List<String> roomInUserId = new ArrayList<>();
    private int roomInUser = 0;
    private int userCount = 0;
    private int cnt = 0;
    private String day;

    @RequestMapping("/chat/newUser.action")
    public ModelAndView newUser(@RequestParam("roomId") String roomId,
    @RequestParam("useremail") String useremail,
    @RequestParam("meetListNum") int meetListNum) {

        // 채팅방 읽어오기
        Optional<ChatRoomCollection> room = chatRoomService.getReadDate(roomId);
        ChatRoomCollection rooms = (ChatRoomCollection)room.get();
        
        // 여기는 신규유저 인지 아닌지 확인 하는곳
        if(!rooms.getUsers().contains(useremail)){
            System.out.println("신규 유저 입장!");

            rooms.getUsers().add(useremail);
            String entryDate = chatUtil.todayYMDAndTime();
            String newUser = chatUtil.emailSubString(useremail);
            rooms.setEntryDate(newUser, entryDate);
            int userCnt = rooms.getUserCount();
            rooms.setUserCount(userCnt + 1);
            chatRoomService.updateChatRoom(rooms);
        }
        return new ModelAndView("redirect:/meetManager.action?meetListNum=" + meetListNum);
    }


    // MessageMapping 을 통해 webSocket 로 들어오는 메시지를 발신 처리한다.
    // 이때 클라이언트에서는 /pub/chat/message 로 요청하게 되고 이것을 controller 가 받아서 처리한다.
    // 처리가 완료되면 /sub/chat/room/roomId 로 메시지가 전송된다.
    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload ChatDTO chat, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("chat/enterUser 들어옴....");

        // 여기부터 읽었는지 확인 하는 코드
        String today = chatUtil.todayYearMonthDay();

        // 채팅방 읽어오기
        Optional<ChatRoomCollection> room = chatRoomService.getReadDate(chat.getRoomId());
        ChatRoomCollection rooms = (ChatRoomCollection)room.get();
        
        // 여기는 신규유저 인지 아닌지 확인 하는곳
        if(!rooms.getUsers().contains(chat.getUserId())){
            System.out.println("신규 유저 입장!");
            rooms.getUsers().add(chat.getUserId());
            String entryDate = chatUtil.todayYMDAndTime();
            rooms.setEntryDate(chat.getUserId(), entryDate);
            int userCnt = rooms.getUserCount();
            rooms.setUserCount(userCnt + 1);
            chat.setMessage(chat.getSender() + " 님 환영합니다!!");
            chat.setType(MessageType.ENTER);
            template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
        }
        userCount = rooms.getUserCount();
        //여기까지 신규유저 확인
        //DB에 채팅내역 가져오기
        List<ChatContentCollection> roomChats = chatContentService.findAllByRoomIdIn(chat.getRoomId() + today);

        if(roomChats != null){
            for (ChatContentCollection roomChat : roomChats){

                List<ChatMessage> chatMessage = roomChat.getChats();
                //System.out.println("챗 메세지 : " + chatMessage);
                
                    // 채팅 내역에 해당유저가 없으면 추가 해주면서 카운트 -1
                for(int i=0; i<chatMessage.size(); i++){
                    if(!chatMessage.get(i).getReadUser().contains(chat.getUserId())){
                        System.out.println("유저 있나? : " + chatMessage.get(i).getReadUser());
                        System.out.println("카운트 내려주는 곳 " + i);
                        
                        System.out.println("메세지 : " + chatMessage.get(i));

                        int rCnt = chatMessage.get(i).getReadCount();
                        
                        System.out.println("rCnt : " + rCnt);

                        chatMessage.get(i).setReadCount(rCnt - 1);
                        chatMessage.get(i).getReadUser().add(chat.getUserId());

                        System.out.println("변경된 값 : " + chatMessage.get(i).getReadCount());

                    }
                }
                //System.out.println("chatMessage : " + chatMessage);
                roomChat.setChats(chatMessage);
                chatContentService.updateChat(roomChat);
                chatMaps.put(chat.getRoomId(), roomChat);
            }
        }
        // 요까지 채팅방 들어오면 읽음 -1

        // 채팅방 신규유저 업데이트
        chatRoomService.updateChatRoom(rooms);

        roomInUserId.add(chat.getUserId());
        roomInUser++;
        System.out.println("현재 채팅치는 인원수(in) : " + roomInUser);
        
        System.out.println("chat.getRoomId : " + chat.getRoomId());
    }

    // 해당 유저
    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDTO chat,SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("chat sendMessage 들어옴 ----------------------------------------- 구분선");
        //log.info("CHAT {}", chat);
        chat.setMessage(chat.getMessage());
        System.out.println("보낸이 : " + chat.getSender());
        System.out.println("보낸아이디 : " + chat.getUserId());
        //System.out.println("타입 : " + chat.getType().toString());
        // 현재시간 가져오기
        String today = chatUtil.todayYearMonthDay();
        String todayAndTime = chatUtil.todayYMDAndTime();
        if(day == null) {
            day = today;
        }
        System.out.println("day : " + day);
        System.out.println("today : " + today);
        if(!chatMaps.containsKey(chat.getRoomId()) || !day.equals(today)){
            // 채팅내역이 없으면(Map에 안만들어져 있으면)
            System.out.println("채팅방이 없으면 들어오는곳");
            ChatContentCollection chatContentCollection = new ChatContentCollection(chat.getRoomId()+today);
            day = today;
            chatMaps.put(chat.getRoomId(),chatContentCollection);
        }
        
        //System.out.println(chat.getRoomId());
        //System.out.println(chat.getSender());
        System.out.println(chat.getMessage());
        //System.out.println(todayAndTime);
        
        // 유저 사진 넣는 코드
        Users user = userService.getUser(chat.getUserId());
        chat.setPicture(user.getPicture());

        // 채팅내역 입력
        ChatMessage c = new ChatMessage();
        c.setUserId(chat.getUserId());
        c.setSender(chat.getSender());
        c.setMessage(chat.getMessage());
        c.setTime(todayAndTime);
        c.setType(chat.getType().toString());
        c.setReadCount(userCount - roomInUser);
        c.setPicture(user.getPicture());
        
        //System.out.println("현재 유저 (send) : " + roomInUserId);
        // List<String> readUserList = c.getReadUser();
        // readUserList.add(chat.getSender());
        System.out.println("방에 있는 유저 : " + roomInUserId);
        
        c.setReadUser(new ArrayList<>(roomInUserId));

        System.out.println("새로운 채팅 내역 : " + c);
        chat.setReadCount(c.getReadCount());

        String chatTime = chatUtil.todayChatContent();
        chat.setTime(chatTime);
        // 채팅내역 추가
        chatMaps.get(chat.getRoomId()).addLists(c);
        // 채팅내역 추가해서 새로고침
        // ChatContentCollection chatContentCollection = chatContentService.findList(chat.getRoomId());
        // chatContentCollection.addLists(c);
        // chatMaps.replace(chat.getRoomId(), chatContentCollection);

        //System.out.println("chatMaps 데이터 : " + chatMaps.get(chat.getRoomId()));

        for (ChatMessage c1 : chatMaps.get(chat.getRoomId()).getChats()) {
            System.out.println("채팅 : " + c1.getMessage() + "  카운트 : " + c1.getReadCount() + " 읽은유저 : " + c1.getReadUser());
        }

        chatContentService.insertChat(chatMaps.get(chat.getRoomId()));

        

        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }

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
        
        // 어제 날짜
        //chatUtil.todayMinusDay(1);

        String today = chatUtil.todayYearMonthDay();
        
        if(!chatMaps.containsKey(chat.getRoomId())){
            // 채팅내역이 없으면(Map에 안만들어져 있으면 만들어줌)
            System.out.println("채팅방이 없으면 들어오는곳");
            chatContentCollection = new ChatContentCollection(chat.getRoomId()+today);
            chatMaps.put(chat.getRoomId(),chatContentCollection);
        }
        
        // List<ChatMessage> chatLists = chatMaps.get(chat.getRoomId()).getChats();
        Optional<ChatRoomCollection> room = chatRoomService.getReadDate(chat.getRoomId());
        ChatRoomCollection rooms = (ChatRoomCollection)room.get();
        String user = chatUtil.emailSubString(chat.getUserId());
        String entryTime = rooms.getEntryDate().get(user);

        // List<ChatMessage> chatLists = new ArrayList<>();
        List<ChatMessage> chatLists = chatContentService.findAllByRoomIdInTime(chat.getRoomId(),entryTime);
        if(chatLists != null){
            for (ChatMessage chatMessage : chatLists) {
                chatMessage.setTime(chatUtil.changeChatContent(chatMessage.getTime()));
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("data", chatLists);

        return data;
    }

    // @MessageMapping("/chat/sendImage")
    // @SendTo("/topic/receiveImage")
    @PostMapping("/chat/sendImage")
    public int receiveImage(@RequestParam("file") MultipartFile file,
    @RequestParam("roomId") String roomId, 
    @RequestParam("sender") String sender,
    @RequestParam("userId") String userId,
    HttpServletRequest req){
       
        System.out.println("file" + file.getOriginalFilename());
        
        try {

            String saveFileName = UUID.randomUUID() + file.getOriginalFilename();

            Resource resource = new ClassPathResource("static");
            String resourcePath = resource.getFile().getAbsolutePath() + "/uploadFile";
            System.out.println(resourcePath);
            Path filePath = Paths.get(resourcePath, saveFileName);
            // 파일 저장
            Files.write(filePath, file.getBytes());
            
            String todayAndTime = chatUtil.todayYMDAndTime();

            String src = "/uploadFile/" + saveFileName;

            // 유저 사진 넣는 코드
            Users user = userService.getUser(userId);

            ChatMessage c = new ChatMessage();
            c.setUserId(userId);
            c.setSender(sender);
            c.setMessage(src);
            c.setTime(todayAndTime);
            c.setType("IMAGE");
            c.setReadCount(userCount - roomInUser);
            // 채팅내역 추가
            System.out.println("메세지 : " + c.getMessage());
            System.out.println("보낸이 : " + c.getSender());
            System.out.println("시스템");
            c.setReadUser(new ArrayList<>(roomInUserId));
            c.setPicture(user.getPicture());
            chatMaps.get(roomId).addLists(c);
            chatContentService.insertChat(chatMaps.get(roomId));

            ChatDTO chat = new ChatDTO();
            chat.setMessage(src);
            chat.setRoomId(roomId);
            chat.setUserId(userId);
            chat.setSender(sender);
            chat.setType(MessageType.IMAGE);
            chat.setReadCount(c.getReadCount());
            chat.setPicture(user.getPicture());

            String chatTime = chatUtil.todayChatContent();
            chat.setTime(chatTime);
            template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);

        } catch (Exception e) {
            System.out.println(e.toString() + "이미지 전송 실패");
        }
        return 1;
    }

    @PostMapping("/chat/getReadCount")
    public Map<String,Object> readCount(@RequestBody Map<String, String> requestMap){
        //System.out.println("chat GetReaddCount에 들어옴");
        
        String today = chatUtil.todayYearMonthDay();
        //System.out.println(today);
        String roomId = requestMap.get("roomId") + today;
        Map<String, Object> data = new HashMap<>();
        ChatContentCollection roomChat = chatContentService.getChatContentWithReadCountGreaterThanZero(roomId);
        if(roomChat == null) return data;
        //System.out.println("채팅 : " + roomChat);
        List<ChatMessage> chatMessage = roomChat.getChats();
        // System.out.println(chatMessage);
        data.put("data", chatMessage);
        //chatContentService.updateReadCount(roomId);
        return data;
    }

    @MessageMapping("/chat/logout")
    public void logout(@Payload ChatDTO chat){
        roomInUser--;
        System.out.println("로그아웃");
        System.out.println("chat user : " + chat.getSender());
        roomInUserId.remove(chat.getSender());
        System.out.println("방에 있는 사람 : " + roomInUserId);
    }

    @GetMapping("/service.action")
    public ModelAndView service(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/chat/service");
        return mav;
    }


}