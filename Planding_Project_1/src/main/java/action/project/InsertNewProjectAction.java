package action.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import vo.ActionForward;

public class InsertNewProjectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession();
		session.removeAttribute("planner_name");
		session.removeAttribute("introduce");
		session.removeAttribute("bank");
		session.removeAttribute("otherBankName");
		session.removeAttribute("account");
		session.removeAttribute("kind");
		session.removeAttribute("title");
		session.removeAttribute("summary");
		session.removeAttribute("contentImg");
		session.removeAttribute("content");
		session.removeAttribute("thumbnail");
		session.removeAttribute("startdate");
		session.removeAttribute("enddate");
		session.removeAttribute("goal_amount");
		

		
		request.setAttribute("showPage", "project/insertNewProject.jsp");//어느 폼 보기인지 showPage이름 속성으로 저장
		forward = new ActionForward("userTemplate.jsp",false);//반드시 디스패치 (request를 공유)
		
		return forward;
	}

}
