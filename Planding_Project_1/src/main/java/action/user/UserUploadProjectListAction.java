package action.user;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.UserUploadProjectListService;
import vo.ActionForward;
import vo.ProjectBean;

public class UserUploadProjectListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		
		//혹시나 프로젝트 수정 실패시 남아있을 수 있는 프로젝트 아이디 삭제
		session.removeAttribute("project_id");
		
		if(u_id == null) {//만약 로그인 풀린 상태라면
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 후 이용가능한 서비스입니다.');");
			out.println("location.href='userLogin.usr'");
			out.println("</script>");
		
		}else {
			
			UserUploadProjectListService uploadProjectListService = new UserUploadProjectListService();
			//[순서-1] 사용자ID로 기획한 프로젝트 ID를 가져와서
			ArrayList<Integer> uploadProjectIdList = uploadProjectListService.getProjectIdList(u_id);
			
			//[순서-2] 프로젝트ID로 프로젝트 정보를 가져옴
			ArrayList<ProjectBean> uploadProjectList = null;
			if(uploadProjectIdList != null) {
				uploadProjectList = uploadProjectListService.getProjectList(uploadProjectIdList);
			}					
			
			request.setAttribute("uploadProjectList", uploadProjectList);
			
			request.setAttribute("showPage", "user/myPage/userUploadProjectList.jsp");
			forward = new ActionForward("userTemplate.jsp", false);		
			
		}		
		
		return forward;
	}

}
