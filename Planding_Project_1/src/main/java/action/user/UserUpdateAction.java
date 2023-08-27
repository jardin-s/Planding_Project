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
		String u_grade = request.getParameter("grade");		
		String u_password = request.getParameter("password");
		String u_name = request.getParameter("name");
		String u_email = request.getParameter("email");
		String u_phone = request.getParameter("phone");
		
		int u_postcode = Integer.parseInt(request.getParameter("postcode"));
		String u_address1 = request.getParameter("address1");
		String u_address2 = request.getParameter("address2");
		
		MemberBean userInfo = new MemberBean(u_id, u_grade, u_password, u_name, u_email, u_phone);
		AddressBean userAddrInfo = new AddressBean(u_id, u_postcode, u_address1, u_address2);
		
		UserUpdateService userUpdateService = new UserUpdateService();
		
		boolean isUserUpdateSuccess = false;
		
		isUserUpdateSuccess = userUpdateService.userUpdate(userInfo, userAddrInfo);

		if(!isUserUpdateSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원 수정이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			forward = new ActionForward("userView.usr", true);
		}
		
		return forward;
	}

}
