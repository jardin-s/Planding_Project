package action.admin.notice;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import vo.ActionForward;

public class AdminInsertNoticeFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		String a_id = (String) session.getAttribute("a_id");
		
		if(a_id == null) {//로그인이 풀린 상태라면
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");			
			out.println("alert('관리자 로그인이 필요합니다.');");
			out.println("location.href='adminLoginForm.adm';");			
			out.println("</script>");	
		
		}else {//로그인 된 상태라면 공지글 작성 폼으로 이동
			
			request.setAttribute("showAdmin", "admin/notice/insertNoticeForm.jsp");
			forward = new ActionForward("adminTemplate.jsp", false);
		}
		
		
		return forward;
	}

}
