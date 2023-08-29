package action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.admin.AdminJoinIdCheckService;
import vo.ActionForward;

public class AdminJoinIdCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward forward = null;
		
		String a_id = request.getParameter("id");
		System.out.println("[AdminJoinIdCheckAction] 파라미터 id : "+a_id);
		
		AdminJoinIdCheckService adminJoinIdCheckService = new AdminJoinIdCheckService();
		boolean isIdUsable = adminJoinIdCheckService.adminIdCheck(a_id);
		System.out.println("[AdminJoinIdCheckAction]");
		System.out.println("isIdUsable = "+isIdUsable);
		
		request.setAttribute("isIdUsable", isIdUsable);
		
		forward = new ActionForward("idCheck.jsp", false);
		
		return forward;
		
	}

}
