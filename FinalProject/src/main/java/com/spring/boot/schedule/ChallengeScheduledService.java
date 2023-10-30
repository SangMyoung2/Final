package com.spring.boot.schedule;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import com.spring.boot.service.ChallengeService;

@Service
public class ChallengeScheduledService {
    
    @Autowired
    private final ChallengeService challengeService;

    public ChallengeScheduledService(ChallengeService challengeService){
        this.challengeService = challengeService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void challengeEndDateCheck() throws Exception {
        
        System.out.println("++++++++++++++++++++챌린지 스케쥴 시작++++++++++++++++++++");
        challengeService.updateChallengeStatus();
    }

}
