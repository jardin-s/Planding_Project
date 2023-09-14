package action.user.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.user.account.UserIdCheckService;
import vo.ActionForward;

public class UserIdCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward forward = null;
		
		String check_id = request.getParameter("ckid");
		System.out.println("[UserJoinIdCheckAction] 파라미터 ckid : "+check_id);
		
		UserIdCheckService userJoinIdCheckService = new UserIdCheckService();
		boolean isIdUsable = userJoinIdCheckService.userIdCheck(check_id);
		System.out.println("[UserJoinIdCheckAction]");
		System.out.println("isIdUsable = "+isIdUsable);
		
		request.setAttribute("isIdUsable", isIdUsable);
		request.setAttribute("check_id", check_id);
		
		forward = new ActionForward("idCheck.jsp", false);
		
		return forward;
		
	}

}
