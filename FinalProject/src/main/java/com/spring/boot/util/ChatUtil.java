package com.spring.boot.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

@Service
public class ChatUtil {
    
    public String todayYearMonthDay(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = currentDateTime.format(formatter);
        return today;
    }
    
    public String todayYMDAndTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String today = currentDateTime.format(formatter);
        return today;
    }
    
    public String todayChatContent(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a h:mm");
        String today = currentDateTime.format(formatter);
        return today;
    }

    public String changeChatContent(String time){
        LocalDateTime currentDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a h:mm");
        String today = currentDateTime.format(formatter);
        return today;
    }

    public String todayMinusDay(int day){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime minusday = currentDateTime.minusDays(day);
        String today = minusday.format(formatter);
        return today;
    }

    public LocalDateTime formatterDateTime(String day){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(day, formatter);
    }

    public String emailSubString(String useremail){
        int dot = useremail.lastIndexOf('.');
        return useremail.substring(0, dot);
    }
}
