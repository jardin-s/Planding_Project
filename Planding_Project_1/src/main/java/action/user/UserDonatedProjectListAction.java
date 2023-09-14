package action.user;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.UserDonatedProjectListService;
import svc.user.UserBookmarkListService;
import vo.ActionForward;
import vo.PageInfo;
import vo.ProjectBean;

public class UserDonatedProjectListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
			
		if(u_id == null) {//만약 로그인 풀린 상태라면
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 후 이용가능한 서비스입니다.');");
			out.println("location.href='userLogin.usr'");
			out.println("</script>");
		
		}else {
			
			UserDonatedProjectListService userDonatedProjectListService = new UserDonatedProjectListService();
			
			//[순서-1] 사용자가 기획한 프로젝트ID만 가지고와서
			ArrayList<Integer> fundProjectIdList = userDonatedProjectListService.getProjectIdList(u_id);
						
			//[순서-2] 프로젝트ID로 프로젝트 정보 가져옴
			ArrayList<ProjectBean> donatedProjectList = null;
			if(fundProjectIdList != null) {//ID리스트가 null이 아니면
				donatedProjectList = userDonatedProjectListService.getProjectList(fundProjectIdList);
			}
			
			request.setAttribute("donatedProjectList", donatedProjectList);
			
			request.setAttribute("showPage", "user/myPage/userDonatedProjectList.jsp");
			forward = new ActionForward("userTemplate.jsp", false);		
			
		}
			
		return forward;
	}

}
