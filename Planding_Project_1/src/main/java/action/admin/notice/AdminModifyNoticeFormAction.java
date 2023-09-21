package action.admin.notice;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.notice.NoticeViewService;
import vo.ActionForward;
import vo.NoticeBean;

public class AdminModifyNoticeFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		int page = Integer.parseInt(request.getParameter("page"));
		int notice_id = Integer.parseInt(request.getParameter("notice_id"));
				
		HttpSession session = request.getSession();
		String a_id = (String) session.getAttribute("a_id");
		
		if(a_id == null) {//로그인이 풀린 상태라면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('해당 서비스는 로그인이 필요합니다.');");
			out.println("location.href='adminLoginForm.adm';");
			out.println("</script>");
		}else {
			
			//notice_id로 공지글 정보를 가져옴 (수정글에서 세팅을 위해)
			NoticeViewService noticeViewService = new NoticeViewService();
			NoticeBean noticeInfo = noticeViewService.getNoticeInfo(notice_id);
			System.out.println("[AdminModifyNoticeFormAction] noticeInfo의 importance값 = "+noticeInfo.getImportance());
			
			request.setAttribute("page", page);
			request.setAttribute("noticeInfo", noticeInfo);
			
			request.setAttribute("showAdmin", "admin/notice/modifyNoticeForm.jsp");
			forward = new ActionForward("adminTemplate.jsp", false);
		}
		
		return forward;
		
	}

}
