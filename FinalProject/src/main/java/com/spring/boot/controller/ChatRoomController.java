package com.spring.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.boot.chat.ChatDAO;
import com.spring.boot.dto.ChatRoom;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ChatRoomController {

	@Autowired
    private ChatDAO chatDAO;

    //채팅 리스트 화면
    @RequestMapping("/chatlist.action")
    public ModelAndView chatRooms(ModelAndView mav){
        System.out.println("ChatList 화면 입니다.");
        // ModelAndView mav = new ModelAndView();
        mav.addObject("list", chatDAO.findAllChatRoom());

        if(chatDAO.findAllChatRoom() != null){
            System.out.println("SHOW ALL ChatList {}" +  chatDAO.findAllChatRoom());
        }

        mav.setViewName("chat/chatlist");
        return mav;
    }

    // 채팅방 생성
    @PostMapping("/chat/createroom.action")
    public ModelAndView createRoom(@RequestParam("name") String name, @RequestParam("master") String master){
        System.out.println("Chat Create Room 화면 입니다.");

        System.out.println("채팅방 이름 : " + name);
        System.out.println("방장 이름 : " + master);

        ChatRoom room = chatDAO.createChatRoom(name, master);

        ModelAndView mav = new ModelAndView();
        mav.addObject("roomName", room);
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

        mav.addObject("room", chatDAO.findRoomById(roomId));
        mav.setViewName("chat/chatroom");

        return mav;
    }

}