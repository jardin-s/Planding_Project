package action.user.qna;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.qna.SearchQnaListService;
import vo.ActionForward;
import vo.PageInfo;
import vo.QnaBean;

public class SearchQnaListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		
		//처음 요청할 경우 조회하는 페이지넘버 기본값 1
		int page = 1;
		int limit = 10;//한 페이지 당 최대 글 개수 10개
		
		//페이지 넘버 클릭하여 조회하는 경우
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		/*- 검색어 -------------------------------------------------*/
		String search_title = request.getParameter("q_title"); 
		request.setAttribute("searchKeyword", search_title);
		
		/* qna 테이블에서 글을 가져옴 */
		SearchQnaListService SearchQnaListService = new SearchQnaListService();
		
		int listCount = SearchQnaListService.getSearchListCount(search_title);//검색글 개수를 얻어옴
		System.out.println("[SearchQnaListAction] qna_tbl 총 검색글 개수 = "+listCount);
		
		ArrayList<QnaBean> qnaList = SearchQnaListService.getSearchQnaList(search_title, page, limit);//원하는 페이지넘버의 원하는개수만큼 글을 가져옴
		
		
		/* 페이지네이션 */
		int maxPage = (int) ((double)listCount/limit + 0.95); //최대 페이지 수
		//(0.95를 더해 올림 -> 나눗셈 결과가 0 또는 1이 아니면 무조건 올림효과 발생)
		
		int startPage = (( (int) ((double)page/10 + 0.9)) -1 )*10 +1; //페이지네이션 중 첫 번째 수
		//(예) 2페이지라면 2/10 = 0.2			16페이지라면
		//0.2+0.9 = 1.1 -> (int)1.1 = 1		1.6+0.9 = 2.5 -> (int)2.5 = 2
		//1-1 = 0							2-1 = 1
		//0*10+1 = 1						1*10+1 = 11
		
		int endPage = startPage+10 -1; //페이지네이션 중 마지막 수 10 = 1+10-1
		
		if(endPage > maxPage) {//만약 페이지네이션 마지막수가 최대페이지수보다 크다면
			endPage = maxPage;//페이지네이션 마지막수를 최대페이지수로 표시
		}
		
		/*
		 * int itemsPerPage = 15; // 페이지당 항목 수
			int firstNumber = 1;  // 원하는 첫 번째 숫자
			int lastNumber = 15;  // 원하는 마지막 숫자
			
			int currentPage = 56; // 현재 보고자 하는 페이지
			int startPage = ((currentPage - 1) / itemsPerPage) * itemsPerPage + firstNumber;
			endPage = 46+15 = 51 51-50 50이랑 15
			
			int endPage = Math.min(startPage + itemsPerPage - 1, lastNumber);
			
			System.out.println("Start Page: " + startPage);
			System.out.println("End Page: " + endPage);
		 * 
		 * */
		
		
		//페이지네이션 정보를 PageInfo객체에 담아 전달
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPage(page);
		pageInfo.setListCount(listCount);
		pageInfo.setMaxPage(maxPage);
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);
		
		//페이지네이션 정보와 해당 페이지 글목록을 request속성으로 저장
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("qnaList", qnaList);
		
		request.setAttribute("showPage", "user/qna/searchQnaList.jsp");//문의사항 페이지로 디스패치 포워딩
		forward = new ActionForward("userTemplate.jsp", false);
		
		return forward;
	}

}
