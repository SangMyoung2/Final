package com.spring.boot.util;

import org.springframework.stereotype.Service;

@Service //객체를 생성하게끔 만들어준다.
public class MyUtil {
	/**
	 * 전체 데이터를 가지고 페이지수 구하기
	 * @param numPerPage
	 * @param dataCount
	 * @return
	 */
	public int getPageCount(int numPerPage,int dataCount) {
		
		int pageCount = 0;
		pageCount = dataCount / numPerPage;
		
		
		if(dataCount%numPerPage !=0) {
			pageCount++;
		}
		
		return pageCount;
	
	}
	
	//페이지 처리 메소드
	public String pageIndexList(int currentPage, int totalPage, String listUrl) {
		
		int numPerBlock = 5;
		int currentPageSetup; //이전페이지(표시할 첫페이지 - 1)
		int page;
		
		StringBuffer sb = new StringBuffer();
		
		if(currentPage==0 || totalPage==0) {
			return "";
		}
		
		//listUrl에 오는 2가지 데이터
		//list.jsp ?pageNum=2
		//list.jsp?searchKey=subject&searchValue=suzi &pageNum=2 검색했을때 데이터
		//이때는 페이징 처리된 데이터와 검색한 데이터 정보도 가지고 나가야한다.
		if(listUrl.indexOf("?")!=-1) {
			listUrl += "&"; //(검색을 했을때) ?가 붙어있으면 &만 붙이고 
		}else {
			listUrl += "?"; //(검색을 안했을때) 아무것도 없으면 ?를 붙여라
		}
		
		//◀이전 6 7 8 9 10 다음▶ 현재페이지 / 나눌페이지수
		currentPageSetup = (currentPage/numPerBlock)*numPerBlock;
		
		//나눠서 0으로 떨어지면 계속 동일한 numperblock이 된다.
		//나눠서 0으로 떨어지는 페이지는 그냥 numperblock을 빼주면 된다.
		if(currentPage % numPerBlock == 0) {
			currentPageSetup = currentPageSetup - numPerBlock;
		}
		
		//◀이전 만들기
		if(totalPage > numPerBlock && currentPageSetup > 0) {
			
			sb.append("<a href=\"" + listUrl + "pageNum=" + currentPageSetup + "\">◀이전</a>&nbsp;");
			
			
			//<a href="list.jsp?pageNum=5">◀이전</a>&nbsp;
			
		}
		
		// 6 7 8 9 10 
		//바로가기 페이지 만들기
		page = currentPageSetup + 1;
     //최대페이지 보다 크면안되 && numperblock만큼 찍어
		while(page <= totalPage && page <= (currentPageSetup+numPerBlock)) {
			
			if(page==currentPage) {
				sb.append("<font color=\"Fuchsia\">" + page + "</font>&nbsp;");
				//<font color="Fuchsia">9</font>&nbsp;
			}else {
				sb.append("<a href=\"" + listUrl + "pageNum=" + page + "\">" + page + "</a>&nbsp;");
				//<a href="list.jsp?pageNum=10">10</a>&nbsp;
			}
			page++;
		}
		
		//다음▶ 만들기
		//◀이전 11 12       다음▶
		if(totalPage - currentPageSetup > numPerBlock) {
			
			sb.append("<a href=\"" + listUrl + "pageNum=" + page + "\">다음▶</a>&nbsp;");
			//<a href="list.jsp?pageNum=11">다음▶</a>&nbsp;	
		}
		return sb.toString();
	}
	
}
