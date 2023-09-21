package com.spring.boot.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.dto.BoardDTO;
import com.spring.boot.service.BoardService;
import com.spring.boot.util.MyUtil;

@RestController //는 return을 텍스트로 인식하지만 ModelAndView는 ResponseBody를 작성하지 않아도 주소로 인식한다. 
public class BoardController {

	@Resource
	private BoardService boardService;
	
	@Autowired
	MyUtil myUtil;
	
	
	@RequestMapping(value = "/")
	public ModelAndView index() throws Exception{ 
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("index");
		
		return mav;
		
	}
	
//	@RequestMapping(value = "/created.action", method = RequestMethod.GET)
	@GetMapping("/created.action")
	public ModelAndView created() throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("bbs/created");
		
		return mav;
		
	}
	
	//action을 지워도 문제없다.
//	@RequestMapping(value = "/created.action", method = RequestMethod.POST)
	@PostMapping("/created.action")
	public ModelAndView created_ok(BoardDTO dto, HttpServletRequest request) throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		//여기서 호출 하면 BoardService -> BoardServiceImpl -> BoardMapper -> boardMapper.xml에서 데이터 반환을 BoardController로 해준다.
		int maxNum = boardService.maxNum();
		
		dto.setNum(maxNum+1);
		dto.setIpAddr(request.getRemoteAddr());
		
		boardService.insertData(dto);
		
		mav.setViewName("redirect:/list.action");
		
		return mav;
		
	}
	
//	@RequestMapping(value = "/list.action", method = {RequestMethod.POST, RequestMethod.GET}) //검색은 post방식으로 넘어가기 때문에 있어야한다.
	@GetMapping("/list.action")
	public ModelAndView list(HttpServletRequest request) throws Exception{
		
		String pageNum = request.getParameter("pageNum");
		
		int currentPage = 1;
		
		if(pageNum!=null) {
			currentPage = Integer.parseInt(pageNum);
		}
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue==null) {
			searchKey = "subject";
			searchValue = "";
		}else {
			if(request.getMethod().equalsIgnoreCase("GET")) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
		}
		
		int dataCount = boardService.getDataCount(searchKey, searchValue);
		
		int numPerPage = 5;
		int totalPage = myUtil.getPageCount(numPerPage, dataCount);
		
		if(currentPage>totalPage) {
			currentPage = totalPage;
		}
		
		int start = (currentPage-1)*numPerPage+1;
		int end = currentPage*numPerPage;
		
		List<BoardDTO> lists = boardService.getLists(start, end, searchKey, searchValue);
		
		String param = "";
		if(searchValue!=null&&!searchValue.equals("")) {
			param = "searchKey=" + searchKey;
			param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		String listUrl = "/list.action";
		
		if(!param.equals("")) {
			listUrl = listUrl + "?" + param;
		}
		
		String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);
		
		String articleUrl = "/article.action?pageNum=" + currentPage;
		
		if(!param.equals("")) {
			articleUrl = articleUrl + "&" + param;
		}
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("bbs/list");

		mav.addObject("lists", lists);
		mav.addObject("pageIndexList", pageIndexList);
		mav.addObject("dataCount", dataCount);
		mav.addObject("articleUrl", articleUrl);
		
//		mav.addObject("pageNum", currentPage);
		
		return mav;
		
	}
	
	
//	@RequestMapping(value = "/article.action", method = {RequestMethod.POST, RequestMethod.GET})
	@GetMapping("/article.action")
	public ModelAndView article(HttpServletRequest request) throws Exception{
		
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue!=null) {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");	
		}else {
			searchKey = "subject";
			searchValue = "";
		}
		
		boardService.updateHitCount(num);
		
		BoardDTO dto = boardService.getReadData(num);
		

		if(dto==null) {

			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/list.action?pageNum=" + pageNum);
			
			return mav;
		}
		
		int lineSu = dto.getContent().split("\n").length;
		
//		dto.setContent(dto.getContent().replaceAll("\n", "<br/>"));
		
		String param = "pageNum=" + pageNum;
		
		if(searchValue!=null&&!searchValue.equals("")) {
			param+= "&searchKey=" + searchKey;
			param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		//ModelAndView 방식
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("dto",dto);
		mav.addObject("lineSu",lineSu);
		mav.addObject("params",param);
		mav.addObject("pageNum",pageNum);
		
		mav.setViewName("bbs/article");
		
		return mav;
		
	}
	
//	@RequestMapping(value = "/updated.action", method = {RequestMethod.POST, RequestMethod.GET})
	@GetMapping("/updated.action")
	public ModelAndView updated(HttpServletRequest request) throws Exception{
		
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue!=null) {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");	
		}
		
		BoardDTO dto = boardService.getReadData(num);
		
		if(dto==null) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/list.action?pageNum=" + pageNum);
			
			return mav;
		}
		
		String param = "pageNum=" + pageNum;
	
		if(searchValue!=null&&!searchValue.equals("")) {
			param+= "&searchKey=" + searchKey;
			param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("bbs/updated");
		
		mav.addObject("dto", dto);
		mav.addObject("pageNum", pageNum);
		mav.addObject("params", param);
		mav.addObject("searchKey", searchKey);
		mav.addObject("searchValue", searchValue);
		
		return mav;
		
	}
	
//	@RequestMapping(value = "/updated_ok.action", method = {RequestMethod.POST, RequestMethod.GET})
	@PostMapping("/updated_ok.action")
	public ModelAndView updated_ok(BoardDTO dto, HttpServletRequest request) throws Exception{
		
		String pageNum = request.getParameter("pageNum");
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");

		
		boardService.updateData(dto);
		
		String param = "pageNum=" + pageNum;
		
		if(searchValue!=null && !searchValue.equals("")) {
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/list.action?" + param);
		
		return mav;
		
	}
	
//	@RequestMapping(value = "/deleted_ok.action", method = {RequestMethod.POST, RequestMethod.GET})
	@GetMapping("/deleted_ok.action")
	public ModelAndView deleted_ok(HttpServletRequest request) throws Exception{
		
		String pageNum = request.getParameter("pageNum");
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");

		int num = Integer.parseInt(request.getParameter("num"));
		
		boardService.deleteData(num);
		
		String param = "pageNum=" + pageNum;
		
		if(searchValue!=null && !searchValue.equals("")) {
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/list.action?" + param);
		
		return mav;
		
	}
	
	


	@GetMapping("/meetmate.action")
	public ModelAndView meetmate() throws Exception{
		System.out.println("meetmate.action 들어옴");
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/meetmate/list");
		
		return mav;
		
	}
	
	
}
