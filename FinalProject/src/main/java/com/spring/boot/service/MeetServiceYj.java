package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.MeetCategoryDTO;
import com.spring.boot.dto.MeetInfoDTO;
import com.spring.boot.dto.MeetReviewDTO;

public interface MeetServiceYj {

    public MeetInfoDTO getMeetMaster(int meetListNum) throws Exception;

    public List<MeetCategoryDTO> getAllCategories() throws Exception;

    public GatchiDTO getMeetListInfo(int meetListNum) throws Exception;

    public List<MeetInfoDTO> getMeetMembers(int meetListNum) throws Exception;
    
    public List<MeetReviewDTO> getReview(int meetListNum) throws Exception;

    public void insertMeetReview(MeetReviewDTO dto) throws Exception;

    public int getReviewNum(int meetListNum) throws Exception;

    public int hasUserReviewed(MeetReviewDTO dto) throws Exception;

    public void deleteMeetReview(MeetReviewDTO dto) throws Exception;
    
    public Integer getMemberStatus(MeetInfoDTO dto) throws Exception;

    public int getMeetHow(int meetListNum) throws Exception;

    public void insertMeetJoinOk(MeetInfoDTO dto) throws Exception;

    public void deleteMeetOut(MeetInfoDTO dto) throws Exception;
    
    public void updateMeetStatus(GatchiDTO dto) throws Exception;

    public void meetStatusCompletion(GatchiDTO dto) throws Exception;

    public int getMeetStatus(int meetListNum) throws Exception;

    public List<MeetInfoDTO> getMeetWait(int meetListNum) throws Exception;

    public int acceptToWaitlist(MeetInfoDTO dto) throws Exception;

    public void incrementMeetMemCnt(int meetListNum) throws Exception;

    public void rejectFromWaitlist(MeetInfoDTO dto) throws Exception;

    public List<MeetInfoDTO> getMeetBlack(int meetListNum) throws Exception;
    
    public void addToBlacklist(MeetInfoDTO dto) throws Exception;

    public void decrementMeetMemCnt(int meetListNum) throws Exception;

    public void releaseFromBlacklist(MeetInfoDTO dto) throws Exception;

    public List<MeetInfoDTO> getMeetInfo(int meetListNum) throws Exception;

    public List<MeetReviewDTO> getAllMeetReviews() throws Exception;
}
