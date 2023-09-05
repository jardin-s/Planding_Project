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

		String ckid = request.getParameter("ckid");
		System.out.println("[AdminIdCheckAction] 파라미터 ckid : "+ckid);
		
		AdminIdCheckService adminJoinIdCheckService = new AdminIdCheckService();
		boolean isIdUsable = adminJoinIdCheckService.adminIdCheck(ckid);
		System.out.println("[AdminIdCheckAction]");
		System.out.println("isIdUsable = "+isIdUsable);
		
		request.setAttribute("isIdUsable", isIdUsable);
		request.setAttribute("check_id", ckid);
		
		forward = new ActionForward("idCheck.jsp", false);
		
		return forward;
		
	}

}
