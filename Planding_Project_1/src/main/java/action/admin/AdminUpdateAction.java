package action.admin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.admin.AdminUpdateService;
import vo.ActionForward;
import vo.MemberBean;

public class AdminUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String a_id = request.getParameter("member_id");	
		String a_password = request.getParameter("password");
		String a_name = request.getParameter("name");
		String a_email = request.getParameter("email");
		String a_phone = request.getParameter("phone");
		int a_account = Integer.parseInt(request.getParameter("account"));
		String a_admin_status = request.getParameter("admin_status");
				
		MemberBean adminInfo = new MemberBean(a_id, a_password, a_name, a_email, a_phone, a_account, a_admin_status);
		
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
			forward = new ActionForward("adminUpdateForm.adm", true);
		}
		
		return forward;
	}

}
