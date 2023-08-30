package action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.admin.AdminIdCheckService;
import vo.ActionForward;

public class AdminIdCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward forward = null;
		
		String a_id = request.getParameter("id");
		System.out.println("[AdminJoinIdCheckAction] 파라미터 id : "+a_id);
		
		AdminIdCheckService adminJoinIdCheckService = new AdminIdCheckService();
		boolean isIdUsable = adminJoinIdCheckService.adminIdCheck(a_id);
		System.out.println("[AdminJoinIdCheckAction]");
		System.out.println("isIdUsable = "+isIdUsable);
		
		request.setAttribute("isIdUsable", isIdUsable);
		
		forward = new ActionForward("idCheck.jsp", false);
		
		return forward;
		
	}

}
