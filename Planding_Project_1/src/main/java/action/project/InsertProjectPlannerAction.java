package action.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.project.RegisterNewDonateService;
import vo.ActionForward;
import vo.PlannerBean;

public class InsertProjectPlannerAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession();
		session.setAttribute("planner_name", request.getParameter("planner_name"));
		session.setAttribute("introduce", request.getParameter("introduce").trim());
		if(request.getParameter("bank")!=null) {
			session.setAttribute("bank", request.getParameter("bank"));
		}else if(request.getParameter("otherBankName")!=null) {
			session.setAttribute("otherBankName", request.getParameter("otherBankName"));
		}
		session.setAttribute("account", request.getParameter("account"));
		

		
		request.setAttribute("showPage", "project/insertProjectContents.jsp");
		forward=new ActionForward("userTemplate.jsp", false);
		
		
		return forward;
	}

}
