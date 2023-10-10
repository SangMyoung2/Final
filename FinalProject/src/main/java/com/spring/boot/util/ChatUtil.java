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
    
    public String todayMinusDay(int day){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        currentDateTime.minusDays(day);
        String today = currentDateTime.format(formatter);
        return today;
    }
}
