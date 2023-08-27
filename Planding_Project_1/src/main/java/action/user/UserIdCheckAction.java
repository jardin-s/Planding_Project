package action.user;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.user.UserIdCheckService;
import vo.ActionForward;

public class UserIdCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String id = request.getParameter("id");
		
		UserIdCheckService userIdCheckService = new UserIdCheckService();
		boolean isIdExits = userIdCheckService.checkId(id);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if(isIdExits) {
			out.println("0");
		}else {
			out.println("1");
		}
		
		out.close();
		
		return forward;
	}

}
