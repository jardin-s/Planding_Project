package action.admin.manageProject;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.admin.manageProject.AuthorizeProjectService;
import vo.ActionForward;

public class AuthorizeProjectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		int project_id =  Integer.parseInt(request.getParameter("project_id"));
		
		HttpSession session =  request.getSession();
		
		String a_id = (String) session.getAttribute("a_id");
		
		if(a_id == null) {//로그인이 풀린 상태라면
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('관리자 로그인이 필요합니다.');");
			out.println("adminLoginForm.adm;");
			out.println("</script>");
		}else {
			AuthorizeProjectService authorizeProjectService = new AuthorizeProjectService();
			boolean isAuthorizeSuccess = authorizeProjectService.autorizeProject(project_id);
			
			if(!isAuthorizeSuccess) {
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('프로젝트 승인이 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}else {
				//다시 해당 프로젝트 상세보기 요청
				forward = new ActionForward("manageProjectView.mngp?project_id="+project_id, true);
			}
		}		
		
		return forward;
	}

}
