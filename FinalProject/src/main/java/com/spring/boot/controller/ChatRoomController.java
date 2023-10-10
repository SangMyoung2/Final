package com.spring.boot.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.chat.ChatDAO;
import com.spring.boot.collection.ChatRoomCollection;
import com.spring.boot.dto.ChatRoom;
import com.spring.boot.mapper.ChatRoomRepository;
import com.spring.boot.service.ChatContentService;
import com.spring.boot.service.ChatRoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ChatRoomController {

	@Autowired
    private ChatDAO chatDAO;

    @Autowired
    private ChatRoomService chatRoomService;
    
    @Autowired
    private ChatContentService chatContentService;

    @RequestMapping("/chatbutton.action")
    public ModelAndView chatButton(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("chat/chatbutton");
        
        return mav;
    }

    //채팅 리스트 화면
    @RequestMapping("/chatlist.action")
    public ModelAndView chatRooms(ModelAndView mav){
        System.out.println("ChatList 화면 입니다.");
        // ModelAndView mav = new ModelAndView();
        // mav.addObject("list", chatDAO.findAllChatRoom());
        
        // 로그인 된 유저 방만 찾기
        // 세션에 로그인된 아이디 받아와서 찾아주고
        List<ChatRoomCollection> lists = chatRoomService.getFindNameInUsers("김밥");
        System.out.println("lists : " + lists);

        mav.addObject("list", lists);

        // if(chatDAO.findAllChatRoom() != null){
        //     System.out.println("SHOW ALL ChatList {}" +  chatDAO.findAllChatRoom());
        // }

        mav.setViewName("chat/chatlist");
        return mav;
    }

    // 채팅방 생성
    @RequestMapping("/createroom.action")
    public ModelAndView createRoom(@RequestParam("roomName") String roomName, @RequestParam("roomMaster") String roomMaster){

        //chatDAO.createChatRoom(roomName, roomMaster);

        System.out.println("Chat Create Room 화면 입니다.");

        System.out.println("채팅방 이름 : " + roomName);
        System.out.println("방장 이름 : " + roomMaster);

        LocalDateTime currentDateTime = LocalDateTime.now();
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);


        ChatRoomCollection chatRoomCollection = new ChatRoomCollection();

        chatRoomCollection.setRoomName(roomName);
        chatRoomCollection.setRoomId(UUID.randomUUID().toString());
        chatRoomCollection.setRoomMaster(roomMaster);
        chatRoomCollection.setCreateDate(formattedDateTime);
        chatRoomCollection.setType("만남");
        chatRoomCollection.setUserCount(0);

        chatRoomCollection.setLists(roomMaster);

        chatRoomService.createChat(chatRoomCollection);
        System.out.println("DB저장 완료");

        //ChatRoom room = chatDAO.createChatRoom(roomName, roomMaster);
        
        ModelAndView mav = new ModelAndView();
        //mav.addObject("roomName", room);
        mav.setViewName("redirect:/chatlist.action");
        return mav;
    }

    // 채팅방 입장 화면
    // 파라미터로 넘어오는 roomId 를 확인후 해당 roomId 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.
    @GetMapping("/chat/room")
    public ModelAndView roomDetail(ModelAndView mav, String roomId){
        
        System.out.println("Room Detail 화면 입니다.");

        log.info("roomId {}", roomId);
        
        // mav.addObject("room", chatDAO.findRoomById(roomId));

        System.out.println("room정보 : " + chatRoomService.getReadDate(roomId));

        Optional<ChatRoomCollection> room = chatRoomService.getReadDate(roomId);
        System.out.println(room.isPresent());
        System.out.println("room.get()은 : " + room.get());

        ChatRoomCollection rooms = (ChatRoomCollection)room.get();

        System.out.println("rooms : " + rooms);

        mav.addObject("room", rooms);

        mav.setViewName("chat/chatroom");
        return mav;
    }

}