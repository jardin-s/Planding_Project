package action.admin.notice;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.admin.notice.AdminDeleteNoticeService;
import vo.ActionForward;

public class AdminDeleteNoticeAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		//파라미터값
		int page = Integer.parseInt(request.getParameter("page"));
		int notice_id =  Integer.parseInt(request.getParameter("notice_id"));
		
		System.out.println("[AdminDeleteNoticeAction] 파라미터값");
		System.out.println("page="+page);
		System.out.println("notice_id="+notice_id);
		
		HttpSession session = request.getSession();
		String a_id= (String) session.getAttribute("a_id");
		
		if(a_id == null) {//관리자 로그인이 풀린 상태라면
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('관리자 로그인이 필요합니다.');");
			out.println("adminLoginForm.adm;");
			out.println("</script>");
		}else {
			
			//notice_id로 글 삭제
			AdminDeleteNoticeService deleteNoticeService = new AdminDeleteNoticeService();
			boolean isDeleteNoticeSuccess = deleteNoticeService.deleteNotice(notice_id);
						
			if(!isDeleteNoticeSuccess) {//글 삭제 실패
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('공지글 삭제가 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");			
			}else {
				
				forward = new ActionForward("adminNoticeList.adm?page="+page, true);
				
			}
		}
				
		return forward;
	}

}
