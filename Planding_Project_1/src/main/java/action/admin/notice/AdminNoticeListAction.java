package action.admin.notice;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.notice.NoticeListService;
import vo.ActionForward;
import vo.NoticeBean;
import vo.PageInfo;

public class AdminNoticeListAction implements Action {

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
		
		
		/* notice 테이블에서 글을 가져옴 */
		NoticeListService noticeListService = new NoticeListService();
		
		int listCount = noticeListService.getListCount();//전체 글 개수를 얻어옴
		System.out.println("[AdminNoticeListAction] notice_tbl의 전체 공지글 개수 = "+listCount);
		
		//중요공지사항 글의 개수를 알아냄
		int importantCount = noticeListService.getImportantCount();
		System.out.println("[UserNoticeListAction] notice_tbl의 중요 공지글 개수 = "+importantCount);
		
		//중요공지글을 제외하고 한 페이지에 출력할 글의 개수 (10 - 중요글개수)
		limit = limit - importantCount;
				
		//중요공지글을 얻어옴
		ArrayList<NoticeBean> importantList = noticeListService.getImportantList();
		
		//원하는 페이지의 넘버의 원하는 개수만큼 글을 얻어옴
		ArrayList<NoticeBean> noticeList = noticeListService.getNoticeList(page, limit);
		
		
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
		
		//페이지네이션 정보를 PageInfo객체에 담아 전달
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPage(page);
		pageInfo.setListCount(listCount);
		pageInfo.setMaxPage(maxPage);
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);
		
		//페이지네이션 정보와 해당 페이지 글목록을 request속성으로 저장
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("importantList", importantList);
		request.setAttribute("noticeList", noticeList);
		
		request.setAttribute("showPage", "admin/notice/noticeList.jsp");
		forward = new ActionForward("userTemplate.jsp", false);
		
		return forward;
	}

}
