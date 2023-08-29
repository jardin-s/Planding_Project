package action.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.user.UserJoinIdCheckService;
import vo.ActionForward;

public class UserJoinIdCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward forward = null;
		
		String check_id = request.getParameter("ckid");
		System.out.println("[UserJoinIdCheckAction] 파라미터 ckid : "+check_id);
		
		UserJoinIdCheckService userJoinIdCheckService = new UserJoinIdCheckService();
		boolean isIdUsable = userJoinIdCheckService.userIdCheck(check_id);
		System.out.println("[UserJoinIdCheckAction]");
		System.out.println("isIdUsable = "+isIdUsable);
		
		request.setAttribute("isIdUsable", isIdUsable);
		request.setAttribute("check_id", check_id);
		
		forward = new ActionForward("idCheck.jsp", false);
		
		return forward;
		
	}

}
