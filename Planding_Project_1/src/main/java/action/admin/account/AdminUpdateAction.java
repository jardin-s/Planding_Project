package action.admin.account;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.admin.account.AdminUpdateService;
import vo.ActionForward;
import vo.AddressBean;
import vo.MemberBean;

public class AdminUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String u_id = request.getParameter("member_id");	
		String u_name = request.getParameter("name");
		String u_email = request.getParameter("email");
		String u_phone = request.getParameter("phone");
		
		int postcode = Integer.parseInt(request.getParameter("postcode"));
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String address_id = request.getParameter("address_id");
		
		System.out.println("[UserUpdateAction] 파라미터로 받은 값");
		System.out.println("member_id = "+ u_id);
		System.out.println("name = "+ u_name);
		System.out.println("email = "+ u_email);
		System.out.println("phone = "+ u_phone);
		System.out.println("postcode = "+ postcode);
		System.out.println("address1 = "+ address1);
		System.out.println("address2 = "+ address2);
		System.out.println("address_id = "+ address_id);
		
		
		MemberBean adminInfo = new MemberBean();
		adminInfo.setMember_id(u_id);
		adminInfo.setName(u_name);
		adminInfo.setEmail(u_email);
		adminInfo.setPhone(u_phone);
		
		AddressBean addrInfo = new AddressBean(address_id, u_id, u_name, u_phone, postcode, address1, address2);
		
		AdminUpdateService adminUpdateService = new AdminUpdateService();
		
		boolean isAdminUpdateSuccess = adminUpdateService.adminUpdate(adminInfo, addrInfo);

		if(!isAdminUpdateSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원 수정이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			
			//세션에 저장되었던 값 변경
			HttpSession session = request.getSession();
			session.setAttribute("u_name", u_name);
			session.setAttribute("u_email", u_email);
			session.setAttribute("u_phone", u_phone);
			
			forward = new ActionForward("adminUpdateForm.adm", true);
		}
		
		return forward;
	}

}
