package action.admin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.admin.AdminUpdateService;
import vo.ActionForward;
import vo.AddressBean;
import vo.MemberBean;

public class AdminUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String a_id = request.getParameter("id");	
		String a_password = request.getParameter("password");
		String a_name = request.getParameter("name");
		String a_email = request.getParameter("email");
		int a_account = Integer.parseInt(request.getParameter("account"));
		Boolean isAdmin = Boolean.parseBoolean(request.getParameter("isAdmin"));
				
		MemberBean adminInfo = new MemberBean(a_id, a_password, a_name, a_email, a_account, isAdmin);
		
		AdminUpdateService adminUpdateService = new AdminUpdateService();
		
		boolean isAdminUpdateSuccess = false;
		
		isAdminUpdateSuccess = adminUpdateService.adminUpdate(adminInfo);

		if(!isAdminUpdateSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원 수정이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			forward = new ActionForward("adminView.usr", true);
		}
		
		return forward;
	}

}
