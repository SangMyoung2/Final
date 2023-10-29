package com.spring.boot.controller;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.internal.runners.statements.ExpectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.dto.ChallengeAuthDTO;
import com.spring.boot.dto.ChallengeDTO;
import com.spring.boot.dto.ChallengeInfoDTO;
import com.spring.boot.dto.ChallengeLikeDTO;
import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.GatchiLikeDTO;
import com.spring.boot.dto.MapDTO;
import com.spring.boot.dto.MeetInfoDTO;
import com.spring.boot.dto.MeetReviewDTO;
import com.spring.boot.dto.SessionUser;
import com.spring.boot.model.Users;
import com.spring.boot.service.ChallengeLikeService;
import com.spring.boot.service.ChallengeService;



@RestController
public class ChallengeController {
	
	@Autowired
    private ChallengeService challengeService;

	@Autowired
	private ChallengeLikeService challengeLikeService;

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

        String absolutePath = new File("").getAbsolutePath() + "\\";
		String path = "FinalProject/src/main/resources/static/image/challenge/challengeList";
        File file = new File(path);
		// 폴더가 없다면 생성
		if (!file.exists()) {
			file.mkdirs();
		}

		if (!imageMain.isEmpty()) {
			String originalFileName = imageMain.getOriginalFilename();
			String saveFileName = UUID.randomUUID() + originalFileName;
		    
			file = new File(absolutePath + path + "/" + saveFileName);
			imageMain.transferTo(file);
            //challenge dto
            dto.setChallengeImageMain(saveFileName);

            //challengeInfo dto

            infoDTO.setChallengeMemberStatus(1); //방장 설정

		}

        if (!imageSuccess.isEmpty()) {
			String originalFileName = imageSuccess.getOriginalFilename();
			String saveFileName = UUID.randomUUID() + originalFileName;
		    
			file = new File(absolutePath + path + "/" + saveFileName);
			imageSuccess.transferTo(file);
            //challenge dto
            dto.setChallengeImageSuccess(saveFileName);

            //challengeInfo dto
            infoDTO.setChallengeMemberStatus(1); //방장 설정

		}
        
        if (!imageFail.isEmpty()) {
			String originalFileName = imageFail.getOriginalFilename();
			String saveFileName = UUID.randomUUID() + originalFileName;
		    
			file = new File(absolutePath + path + "/" + saveFileName);
			imageFail.transferTo(file);
            //challenge dto
            dto.setChallengeImageFail(saveFileName);

            //challengeInfo dto
            infoDTO.setChallengeMemberStatus(1); //방장 설정

		}

        int maxNum = challengeService.maxNum();
        System.out.println(maxNum);
        infoDTO.setChallengeListNum(maxNum+1);
		dto.setChallengeContent(dto.getChallengeContent().replace("\r\n", "<br/>"));
        dto.setChallengeListNum(maxNum+1);

        challengeService.createChallenge(dto);
        challengeService.insertChallengeInfo(infoDTO);

		Thread.sleep(2000);
		mav.setViewName("redirect:/challengeList.action");
		
		return mav;	
	}



    @GetMapping("/challengeArticle.action")
	public ModelAndView challengeArticle(HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView();
		ChallengeInfoDTO challengeInfoDTO = new ChallengeInfoDTO();
		ChallengeInfoDTO masterInfoDTO = new ChallengeInfoDTO();

        //게시글 번호로 1개의 게시글 불러옴
        int challengeListNum = Integer.parseInt( request.getParameter("challengeListNum"));
    
        List<ChallengeAuthDTO> allReviewList = challengeService.getAllReviewList(challengeListNum);
        List<ChallengeInfoDTO> lists = challengeService.getUserListData(challengeListNum);
		ChallengeDTO challengeDTO = challengeService.getReadData(challengeListNum);

        //user session정보 가져오기
        HttpSession session = request.getSession();
		SessionUser social = (SessionUser)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");
        String email ="";

        //접속한 user 정보 데이터 담기  
        if (social != null) { //소셜유저의 정보
            email = social.getEmail();
		} else if (user1 != null) { //홈페이지 가입 정보
            email = user1.getEmail();
		}



        challengeInfoDTO.setChallengeListNum(challengeListNum);
        challengeInfoDTO.setEmail(email);
        masterInfoDTO = challengeService.getMasterData(challengeListNum);
        
        int ChallengeMemberStatus = -1;

		Integer ret = challengeService.getMemberStatus(challengeInfoDTO);
     
		if (ret != null) {
          
            ChallengeMemberStatus = ret.intValue();
        }

		//잔디심기
		List<ChallengeAuthDTO> userAuthList = challengeService.getUserReview(challengeListNum,email);

		int challengeDay = challengeService.getChallengeDay(challengeListNum); //8일

		// System.out.println(challengeDay);
		// System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
		// System.out.println(userAuthList.get(0).getChallengeAuthCreateDate());
		// System.out.println(userAuthList.get(1).getChallengeAuthCreateDate());
		// System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		if (!userAuthList.isEmpty()) {

			LocalDate dummy = LocalDate.of(2023, 10, 7);
			if(challengeDay!=userAuthList.size()){
				ChallengeAuthDTO newAuthData = new ChallengeAuthDTO();
				while (challengeDay > userAuthList.size()) {
					
					
					newAuthData.setChallengeAuthCreateDate(Date.valueOf(dummy));
					userAuthList.add(newAuthData);
				}
			}

			// System.out.println(userAuthList.get(0).getChallengeAuthCreateDate());
			// System.out.println(userAuthList.get(1).getChallengeAuthCreateDate());
			// System.out.println(userAuthList.get(2).getChallengeAuthCreateDate());
			// System.out.println(userAuthList.get(3).getChallengeAuthCreateDate());
			// System.out.println(userAuthList.get(4).getChallengeAuthCreateDate());
			// System.out.println(userAuthList.get(5).getChallengeAuthCreateDate());
			
			int[] authStatus = new int[challengeDay];
			for (int i = 0; i < challengeDay; i++) {

				LocalDate startDate = challengeDTO.getChallengeStartDate().toLocalDate(); // 시작 날짜를 LocalDate로 변환
				boolean flag = false;
				startDate = startDate.plusDays(i); // i일만큼 증가
				
				for(int j = 0; j < challengeDay; j++){
					System.out.println("====================================================");
					System.out.println("2번 for문 돈다");
					System.out.println("startDate = i" + i + "식 증가했다" + startDate);



					Date authDate = userAuthList.get(j).getChallengeAuthCreateDate();
					System.out.println("authDate = j" + j + "식 증가한다." + authDate);
					
					System.out.println(authDate);
					if (authDate.toLocalDate().isEqual(startDate)) {
						System.out.println("true @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						flag = true;
						break;
					}else{
						System.out.println("false");
					}
				}

				if(flag==true){
					authStatus[i] = 1;
				}else{
					authStatus[i] = 0;
				}
				flag = false;
			}
			mav.addObject("authStatus", authStatus);
		}else if(userAuthList.isEmpty()){
			int[] authStatus = new int[challengeDay];
				for (int i = 0; i < challengeDay; i++) {
					authStatus[i] = 0;
				}
			mav.addObject("authStatus", authStatus);
		}

		int userAuthCnt = userAuthList.size();
		mav.addObject("userAuthCnt",userAuthCnt);
        mav.addObject("challengeDay",challengeDay);
        mav.addObject("challengeInfoDTO",challengeInfoDTO);
        mav.addObject("ChallengeMemberStatus", ChallengeMemberStatus);
        mav.addObject("allReviewList", allReviewList);
        mav.addObject("lists", lists);
        mav.addObject("masterInfoDTO",masterInfoDTO);
        mav.addObject("challengeDTO", challengeDTO);
        
		mav.setViewName("challenge/ChallengeArticle");
		
		return mav;
	}






	// 리뷰 올리기
	@RequestMapping("/uploadAuth")
    public String uploadAuth(HttpServletRequest request,
            @RequestParam("challengeListNum") int challengeListNum,
            @RequestParam("challengeAuthContent") String challengeAuthContent,
            @RequestParam("challengeAuthImage") MultipartFile challengeAuthImage) throws Exception {
                  

        ChallengeAuthDTO authDTO = new ChallengeAuthDTO();
		HttpSession session = request.getSession();
		Users user1 = (Users)session.getAttribute("user1");
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        int maxNum = challengeService.authMaxNum();

		if (sessionUser != null) {
			authDTO.setEmail(sessionUser.getEmail());
		} else if (user1 != null) {
			authDTO.setEmail(user1.getEmail());
		}

		String email = authDTO.getEmail();

        authDTO.setChallengeListNum(challengeListNum);
        authDTO.setChallengeAuthListNum(maxNum+1);
        authDTO.setEmail(email);
        authDTO.setChallengeAuthContent(challengeAuthContent.replace("\r\n", "<br/>"));
		// 중복 리뷰 작성 여부 확인
		ChallengeAuthDTO hasReviewed = challengeService.getNoneAuthReview(authDTO);
		String response = "";
        
		if (hasReviewed == null) { // 리뷰는 한 이메일당 하루에 하나만 작성 가능 인증안되었으면 다시 작성가능
            
			if (!challengeAuthImage.isEmpty()) {

                String absolutePath = new File("").getAbsolutePath() + "\\";
				String path = "FinalProject/src/main/resources/static/image/challenge/challengeCheck";
				File file = new File(path);
				// 폴더가 없다면 생성
				if (!file.exists()) {
					file.mkdirs();
				}
				String originalFileName = challengeAuthImage.getOriginalFilename();
				String saveFileName = UUID.randomUUID() + originalFileName;
				
				file = new File(absolutePath + path + "/" + saveFileName);
				challengeAuthImage.transferTo(file);

                authDTO.setChallengeAuthImage(saveFileName);
				

                authDTO.setChallengeAuthStatus(0);
				
				challengeService.insertAuthReview(authDTO);
				response = "success";
				return response; // 리뷰 작성 성공 시 success 페이지로 리다이렉트
        } 

		}else{
			response = "already-reviewed";
            return response; // 이미 리뷰를 작성한 경우 already-reviewed 페이지로 리다이렉트
		}
        response = "already-reviewed";
		return response;
    }


	// 리뷰 삭제
	@PostMapping("/deleteChallengeReview")
	public ModelAndView deleteReview(
			@RequestParam("email") String email,
			@RequestParam("challengeListNum") int challengeListNum,
			@RequestParam("challengeAuthListNum") int challengeAuthListNum,
			@RequestParam("challengeAuthImage") String challengeAuthImage
			) throws Exception {

		ChallengeAuthDTO challengeAuthDTO = new ChallengeAuthDTO();
		
		challengeAuthDTO.setChallengeAuthImage(challengeAuthImage);

		System.out.println(challengeAuthImage + "여기왔야!!!!!!!!!!!!!!!!");
		String srcFileName = null;

        // try{
        //     srcFileName = URLDecoder.decode(challengeAuthImage,"UTF-8");
        //     //UUID가 포함된 파일이름을 디코딩해줍니다.
        //     File file = new File(uploadPath +File.separator + srcFileName);
        //     boolean result = file.delete();

        //     File thumbnail = new File(file.getParent(),"s_"+file.getName());
        //     //getParent() - 현재 File 객체가 나태내는 파일의 디렉토리의 부모 디렉토리의 이름 을 String으로 리턴해준다.
        //     result = thumbnail.delete();
            
        // }catch (UnsupportedEncodingException e){
        //     e.printStackTrace();
        // }


		challengeAuthDTO.setChallengeListNum(challengeListNum);
		challengeAuthDTO.setChallengeAuthListNum(challengeAuthListNum);
		challengeAuthDTO.setEmail(email);

		challengeService.deleteChallengeReview(challengeAuthDTO);		

		return new ModelAndView("redirect:/meetArticle.action?meetListNum=" + challengeListNum);
	}


	//리뷰인증
	@RequestMapping("/confirmReview.action")
    public ModelAndView confirmReview(HttpServletRequest request,
			@RequestParam("challengeListNum") int challengeListNum,
            @RequestParam("challengeAuthImage") String challengeAuthImage) throws Exception {

			
			challengeService.successChallengeAuth(challengeAuthImage);

			
		

			return new ModelAndView("redirect:/meetArticle.action?meetListNum=" + challengeListNum);
				
	}

	//리뷰인증 실패
	@RequestMapping("/failReview.action")
    public ModelAndView failReview(HttpServletRequest request,
			@RequestParam("challengeListNum") int challengeListNum,
            @RequestParam("challengeAuthImage") String challengeAuthImage) throws Exception {

			
			challengeService.failChallengeAuth(challengeAuthImage);

				

			return new ModelAndView("redirect:/meetArticle.action?meetListNum=" + challengeListNum);
				
	}












	//챌린지 가입
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
        
        challengeService.updateChallengeMemCnt(challengeListNum);
        infoDTO.setChallengeMemberStatus(2); //회원 설정
        infoDTO.setChallengeListNum(challengeListNum);

        challengeService.insertChallengeInfo(infoDTO);


		mav.setViewName("redirect:/challengeArticle.action?challengeListNum=" + challengeListNum);
		
		return mav;		
	}

	//챌린지 삭제
    @PostMapping("/deleteChallenge.action")
	public ModelAndView deleteChallenge(HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView();
	
        int challengeListNum =  Integer.parseInt(request.getParameter("challengeListNum"));

        challengeService.deleteChallengeStatus(challengeListNum);

        mav.setViewName("redirect:/challengeList.action");

		return mav;
	}

    //챌린지 포기
    @PostMapping("/giveUpChallenge.action")
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
       
      	challengeService.downChallengeMemCnt(challengeListNum);

        challengeService.deleteChallengeInfo(challengeListNum,email);
       

        mav.setViewName("redirect:/challengeArticle.action?challengeListNum=" + challengeListNum);

		return mav;
	}






		
	@RequestMapping("/challengeList.action")
	public ModelAndView challengeList(
		@RequestParam(name = "searchValue", required = false) String searchValue, 
		HttpServletRequest request) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		if(searchValue == null || searchValue.equals(null)){
			searchValue = "";
		}

        List<ChallengeDTO> challengeLists = challengeService.getListsSerchValue(searchValue);
		
		for (ChallengeDTO challengeDTO : challengeLists) {
			System.out.println("챌린지 : " + challengeDTO.getChallengeTitle());
		}

		mav.addObject("challengeLists", challengeLists);

		mav.setViewName("challenge/ChallengeList");
		
		return mav;		
	}


    //테스트용
    @GetMapping("/test1.action")
    public ModelAndView test() throws Exception {
        ModelAndView mav = new ModelAndView();
		int meetListNum = 1;
		MapDTO dto = challengeService.getlatlng(meetListNum);
mav.addObject("dto", dto);
		System.out.println(dto+"duddddddddddddddddddddddddddddd");


		

		mav.setViewName("challenge/test");
		
		return mav;	
    }


    @PostMapping("/test1.action")
    public ModelAndView testResult() throws Exception {
        ModelAndView mav = new ModelAndView();


        System.out.println("여기?");
        System.out.println("여기?");

        mav.setViewName("redirect:/challengeList.action");
        return mav;	
    }

	@PostMapping("/challenge/plusLike")
	public String plusLike(@RequestBody Map<String, String> data,HttpServletRequest req) throws Exception {

		System.out.println("좋아요 버튼을 누르셨군요? 챌린지");

		HttpSession session = req.getSession();
		SessionUser social = (SessionUser)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");

		String useremail = "";

		if (social != null) {
			useremail = social.getEmail();
		} else if (user1 != null) {
			useremail = user1.getEmail(); 
		}
		

		System.out.println("유저이메일 : " + useremail);

		System.out.println(data.get("challengeListNum"));
		int listNum = Integer.parseInt(data.get("challengeListNum"));

		ChallengeLikeDTO dto = new ChallengeLikeDTO();
		dto.setChallengeListNum(listNum);
		dto.setUseremail(useremail);

		ChallengeLikeDTO isDto = challengeLikeService.getReadDataInChallengeLikeDTO(dto);
		if(isDto != null) return null;

		challengeService.plusChallengeCount(listNum);
		challengeLikeService.insertChallengeLike(dto);

		ChallengeDTO readData = challengeService.getReadData(listNum);
		System.out.println(readData.getChallengeTitle() + "모임의 좋아요 수는 : " + readData.getChallengeLikeCount());

		return "SUCCESS";
	}
	
	@PostMapping("/challenge/minusLike")
	public String minusLike(@RequestBody Map<String, String> data,HttpServletRequest req) throws Exception {
		
		System.out.println("좋아요 버튼을 취소했다.");

		HttpSession session = req.getSession();
		SessionUser social = (SessionUser)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");

		String useremail = "";
		
		if (social != null) {
			useremail = social.getEmail();
		} else if (user1 != null) {
			useremail = user1.getEmail(); 
		}

		System.out.println("유저이메일 : " + useremail);

		System.out.println(data.get("challengeListNum"));
		int listNum = Integer.parseInt(data.get("challengeListNum"));

		challengeService.minusChallengeCount(listNum);
		//gatchiService.deleteGatchiLike(listNum, useremail);

		ChallengeLikeDTO dto = new ChallengeLikeDTO();
		dto.setChallengeListNum(listNum);
		dto.setUseremail(useremail);

		challengeLikeService.deleteChallengeLike(dto);

		ChallengeDTO readData = challengeService.getReadData(listNum);
		System.out.println(readData.getChallengeTitle() + "모임의 좋아요 수는 : " + readData.getChallengeLikeCount());

		return "SUCCESS";
	}

	@PostMapping("/challenge/loadLikeData")
	public List<Integer> loadLikeData(HttpServletRequest req) throws Exception {

		HttpSession session = req.getSession();
		SessionUser social = (SessionUser)session.getAttribute("user");
		Users user1 = (Users)session.getAttribute("user1");

		String useremail = "";
		
		if (social != null) {
			useremail = social.getEmail();
		} else if (user1 != null) {
			useremail = user1.getEmail(); 
		}
		System.out.println("??????");
		List<ChallengeLikeDTO> lists = challengeLikeService.getReadDataChallengeLike(useremail);
		System.out.println("null??? : " + lists);
		if(lists == null) return null;

		List<Integer> listNum = new ArrayList<>();
		System.out.println("123123123123");
		for(ChallengeLikeDTO g : lists){
			listNum.add(g.getChallengeListNum());
		}
		System.out.println("좋아요 누른 방들 : " + listNum);
		return listNum;
	}

}
