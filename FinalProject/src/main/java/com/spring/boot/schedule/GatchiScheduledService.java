package com.spring.boot.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.service.GatchiService;

@Service
public class GatchiScheduledService {
    
    @Autowired
	private GatchiService gatchiService;

    public GatchiScheduledService(GatchiService gatchiService){
        this.gatchiService = gatchiService;
    }

    // 5분단위로 모임 만들어서 1,6,11... 1분지난시간에 스케쥴러 돌게 만듬
    @Scheduled(cron = "0 1,6,11,16,21,26,31,36,41,46,51,56 * * * *")
    public void meetMateDateCheck() throws Exception{
        //여기서부터 meetStatus 값 변경 위한 작업
		Date currentDate = new Date();//현재 날짜, 시간 가져오기
		
		List<GatchiDTO> meetMateLists = gatchiService.getMeetMateLists();//meetMateLists로 GatchiDTO 가져오기

		// meetMateLists를 하나씩 꺼내면서 날짜 비교 및 업데이트
		for (GatchiDTO dto : meetMateLists) {			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date meetDday = dateFormat.parse(dto.getMeetDday());

			if (dto.getMeetCheck() == 1 && meetDday.before(currentDate)) {// meetCheck가 1이고 meetDday 지나면
				dto.setMeetStatus(2);//meetStatus를 2로 업데이트				
				gatchiService.updateMeetStatusMate(dto);//업데이트된 GatchiDTO 저장
			}
		}
    }

}
