package action.user;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.user.UserUpdateService;
import vo.ActionForward;
import vo.AddressBean;
import vo.MemberBean;

public class UserUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String u_id = request.getParameter("id");	
		String u_name = request.getParameter("name");
		String u_email = request.getParameter("email");
		String u_phone = request.getParameter("phone");
						
		MemberBean userInfo = new MemberBean();
		userInfo.setMember_id(u_id);
		userInfo.setName(u_name);
		userInfo.setEmail(u_email);
		userInfo.setPhone(u_phone);
				
		UserUpdateService userUpdateService = new UserUpdateService();
		
		boolean isUserUpdateSuccess = false;
		
		isUserUpdateSuccess = userUpdateService.userUpdate(userInfo);

		if(!isUserUpdateSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원 수정이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원 수정이 완료되었습니다.');");
			out.println("</script>");
			
			forward = new ActionForward("userView.usr", true);
		}
		
		return forward;
	}

}
