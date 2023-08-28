package action.admin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.admin.AdminIdCheckService;
import vo.ActionForward;

public class AdminIdCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String id = request.getParameter("id");
		
		AdminIdCheckService adminIdCheckService = new AdminIdCheckService();
		boolean isIdExits = adminIdCheckService.checkId(id);
		
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
