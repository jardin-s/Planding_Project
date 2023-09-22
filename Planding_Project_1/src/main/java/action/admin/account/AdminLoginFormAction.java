package action.admin.account;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import vo.ActionForward;

public class AdminLoginFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		
		if(u_id != null) {//사용자 로그인 된 상태면 접근제한
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('접근 권한이 없습니다.');");
			out.println("location.href='userMain.usr'");
			out.println("</script>");
		}else {
			
			request.setAttribute("showPage", "admin/account/loginForm.jsp");//어느 폼 보기인지 showAdmin이름 속성으로 저장
			forward = new ActionForward("adminLoginTemplate.jsp",false);
			
		}
		
		return forward;
		
	}

}
