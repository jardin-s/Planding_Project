package action.user.notice;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.notice.NoticeViewService;
import vo.ActionForward;
import vo.NoticeBean;

public class UserNoticeViewtAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		int notice_id = Integer.parseInt(request.getParameter("notice_id"));
		int page = Integer.parseInt(request.getParameter("page"));
		
		System.out.println("[AdminNoticeViewAction] 파라미터값");
		System.out.println("notice_id = "+notice_id);
		System.out.println("page = "+page);
				
		NoticeViewService noticeViewService = new NoticeViewService();
		NoticeBean notice = noticeViewService.getNoticeInfo(notice_id);
		
		if(notice == null) {//리스트에서 글 보고 있는 사이, 작성자가 해당 글을 삭제했을 경우
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제된 글입니다.');");
			out.println("history.back();");
			out.println("</script>");
			
		}else {
			
			request.setAttribute("page", page);
			request.setAttribute("noticeInfo", notice);
						
			request.setAttribute("showPage", "user/notice/noticeView.jsp");
			forward = new ActionForward("userTemplate.jsp", false);
			
		}
		
		return forward;
	}

}
