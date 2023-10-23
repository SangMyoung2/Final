package com.spring.boot.controller;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.internal.runners.statements.ExpectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.dto.ChallengeDTO;
import com.spring.boot.dto.ChallengeInfoDTO;
import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.MapDTO;
import com.spring.boot.dto.MeetInfoDTO;
import com.spring.boot.dto.MeetReviewDTO;
import com.spring.boot.dto.SessionUser;
import com.spring.boot.model.Users;
import com.spring.boot.service.ChallengeService;



@RestController
public class ChallengeController {
	
	@Autowired
    private ChallengeService challengeService;


	@GetMapping("/challengeCreate.action")
	public ModelAndView challengeCreate() throws Exception{

		ModelAndView mav = new ModelAndView();

		mav.setViewName("challenge/ChallengeCreate");
		
		return mav;	
	}

    @PostMapping("/challengeCreate.action")
	public ModelAndView challengeCreatedOk(ChallengeDTO dto,
    ChallengeInfoDTO infoDTO,
    @RequestParam("imageMain") MultipartFile imageMain, 
    @RequestParam("imageSuccess") MultipartFile imageSuccess, 
    @RequestParam("imageFail") MultipartFile imageFail,
    HttpServletRequest request
    ) throws Exception{

		ModelAndView mav = new ModelAndView();

        HttpSession session = request.getSession();
		SessionUser social = (SessionUser)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");

		if (social != null) {
			infoDTO.setEmail(social.getEmail()); 
		} else if (user1 != null) {
			infoDTO.setEmail(user1.getEmail()); 
		}

        // Resource resource = new ClassPathResource("static");
        // String resourcePath = resource.getFile().getAbsolutePath() + "/image/challenge";
        String resourcePath ="C:\\VSCode\\Final\\FinalProject\\src\\main\\resources\\static\\image\\challenge";

		if (!imageMain.isEmpty()) {
			String originalFileName = imageMain.getOriginalFilename();
			String saveFileName = UUID.randomUUID() + originalFileName;
		    
			Path filePath = Paths.get(resourcePath, saveFileName);

            Files.write(filePath, imageMain.getBytes());
            //challenge dto
            dto.setChallengeImageMain(saveFileName);

            //challengeInfo dto

            infoDTO.setChallengeMemberStatus(1); //방장 설정

		}

        if (!imageSuccess.isEmpty()) {
			String originalFileName = imageSuccess.getOriginalFilename();
			String saveFileName = UUID.randomUUID() + originalFileName;
		    
			Path filePath = Paths.get(resourcePath, saveFileName);

            Files.write(filePath, imageSuccess.getBytes());
            //challenge dto
            dto.setChallengeImageSuccess(saveFileName);

            //challengeInfo dto
            infoDTO.setChallengeMemberStatus(1); //방장 설정

		}
        
        if (!imageFail.isEmpty()) {
			String originalFileName = imageFail.getOriginalFilename();
			String saveFileName = UUID.randomUUID() + originalFileName;
		    
			Path filePath = Paths.get(resourcePath, saveFileName);

            Files.write(filePath, imageFail.getBytes());
            //challenge dto
            dto.setChallengeImageFail(saveFileName);

            //challengeInfo dto
            infoDTO.setChallengeMemberStatus(1); //방장 설정

		}

        int maxNum = challengeService.maxNum();
        infoDTO.setChallengeListNum(maxNum+1);
        dto.setChallengeListNum(maxNum+1);

        challengeService.createChallenge(dto);
        challengeService.insertChallengeInfo(infoDTO);

		mav.setViewName("redirect:/challengeList.action");
		
		return mav;	
	}



    @GetMapping("/challengeArticle.action")
	public ModelAndView challengeArticle(HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView();
		ChallengeInfoDTO challengeInfoDTO = new ChallengeInfoDTO();

        //게시글 번호로 1개의 게시글 불러옴
        int challengeListNum = Integer.parseInt( request.getParameter("challengeListNum"));
		ChallengeDTO challengeDTO = challengeService.getReadData(challengeListNum);
        ChallengeInfoDTO masterInfoDTO = new ChallengeInfoDTO();

        //user session정보 가져오기
        HttpSession session = request.getSession();
		SessionUser social = (SessionUser)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");

        // int memStatus=0;
        // int memLike=0;
        
        //접속한 user 정보 데이터 담기  
        if (social != null) { //소셜유저의 정보
            
            challengeInfoDTO = challengeService.getUserEmailData(social.getEmail(),challengeListNum);

            mav.addObject("challengeInfoDTO",challengeInfoDTO);

            // memStatus = challengeInfoDTO.getChallengeMemberStatus();
            // memLike = challengeInfoDTO.getChallengeLike();

            if(challengeInfoDTO==null){
                System.out.println("일치하는 유저 정보 없음");
            }
            

		} else if (user1 != null) { //홈페이지 가입 정보

			challengeInfoDTO = challengeService.getUserEmailData(user1.getEmail(),challengeListNum);

            mav.addObject("challengeInfoDTO",challengeInfoDTO);

            // memStatus = challengeInfoDTO.getChallengeMemberStatus();
            // memLike = challengeInfoDTO.getChallengeLike();

            
            if(challengeInfoDTO==null){
                System.out.println("일치하는 유저 정보 없음");
            }
		}

        
        masterInfoDTO = challengeService.getMasterData(challengeListNum);
      

        // mav.addObject("memStatus", memStatus);

        mav.addObject("masterInfoDTO",masterInfoDTO);
        mav.addObject("challengeDTO", challengeDTO);
        
		mav.setViewName("challenge/ChallengeArticle");
		
		return mav;
	}







    @PostMapping("/joinChallenge.action")
	public ModelAndView joinChallenge(HttpServletRequest request, ChallengeInfoDTO infoDTO) throws Exception {
		
		ModelAndView mav = new ModelAndView();

        HttpSession session = request.getSession();
		SessionUser social = (SessionUser)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");

		if (social != null) {
			infoDTO.setEmail(social.getEmail()); 
		} else if (user1 != null) {
			infoDTO.setEmail(user1.getEmail()); 
		}

		int challengeListNum =  Integer.parseInt(request.getParameter("challengeListNum"));
        
         
        infoDTO.setChallengeMemberStatus(2); //회원 설정
        infoDTO.setChallengeListNum(challengeListNum);

        challengeService.insertChallengeInfo(infoDTO);


		mav.setViewName("redirect:/challengeArticle.action?challengeListNum=" + challengeListNum);
		
		return mav;		
	}


    @PostMapping("/deleteChallenge.action")
	public ModelAndView deleteChallenge(HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView();
	
        int challengeListNum =  Integer.parseInt(request.getParameter("challengeListNum"));

        challengeService.deleteChallengeStatus(challengeListNum);

        mav.setViewName("redirect:/challengeList.action");

		return mav;
	}

    
    @PostMapping("/giveUpChallenge")
	public ModelAndView giveUpChallenge(HttpServletRequest request,ChallengeInfoDTO challengeInfoDTO) throws Exception {

        ModelAndView mav = new ModelAndView();
	
        int challengeListNum =  Integer.parseInt(request.getParameter("challengeListNum"));



        HttpSession session = request.getSession();
		SessionUser social = (SessionUser)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");
        String email = "";

		if (social != null) {
			email = social.getEmail();
		} else if (user1 != null) {
			email = user1.getEmail(); 
		}
       
      

        challengeService.deleteChallengeInfo(challengeListNum,email);
       

        mav.setViewName("redirect:/challengeArticle.action?challengeListNum=" + challengeListNum);

		return mav;
	}






		
	@GetMapping("/challengeList.action")
	public ModelAndView challengeList() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
        // for (GatchiDTO meetMateList : meetMateLists2) {			
		// 	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// 	Date meetDday = dateFormat.parse(meetMateList.getMeetDday());

		// 	if (meetMateList.getMeetCheck() == 1 && meetDday.before(currentDate)) {// meetCheck가 1이고 meetDday 지나면
		// 		meetMateList.setMeetStatus(2);//meetStatus를 2로 업데이트				
		// 		gatchiService.updateMeetStatusMate(meetMateList);//업데이트된 GatchiDTO 저장
		// 	}
		// }


		mav.setViewName("challenge/ChallengeList");
		
		return mav;		
	}


    //테스트용
    @GetMapping("/test1.action")
    public ModelAndView test(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();

       int challengeListNum =  Integer.parseInt(request.getParameter("challengeListNum"));



        HttpSession session = request.getSession();
		SessionUser social = (SessionUser)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");
        String email = "";

		if (social != null) {
			email = social.getEmail();
		} else if (user1 != null) {
			email = user1.getEmail(); 
		}


        challengeService.deleteChallengeInfo(challengeListNum,email);
		mav.setViewName("challenge/test");
		
		return mav;	
    }


    @PostMapping("/test1.action")
    public ModelAndView testResult(ChallengeDTO dto) throws Exception {
        ModelAndView mav = new ModelAndView();

        challengeService.test(dto);

        System.out.println(dto.getChallengeStartDate()+"여기?");
        System.out.println(dto.getChallengeEndDate()+"여기?");


        mav.setViewName("redirect:/challengeList.action");
        return mav;	
    }


}
