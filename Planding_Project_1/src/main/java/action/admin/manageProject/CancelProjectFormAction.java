package action.admin.manageProject;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import vo.ActionForward;

public class CancelProjectFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		int project_id = Integer.parseInt(request.getParameter("project_id"));
		String status = request.getParameter("status");
		
		HttpSession session = request.getSession();
		String a_id = (String) session.getAttribute("a_id");
		
		if(a_id == null) {//로그인이 풀린 상태라면
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('관리자 로그인이 필요합니다.');");
			out.println("adminLoginForm.adm;");
			out.println("</script>");
		}else {
			
			//폼에 히든값으로 세팅해 다시 넘겨주기 위해
			request.setAttribute("project_id", project_id);
			request.setAttribute("status", status);
			
			request.setAttribute("showAdmin", "cancelProjectForm.jsp");
			forward = new ActionForward("adminTemplate.jsp", false);
			
		}
		return forward;
	}

}
