package action.admin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.admin.AdminJoinService;
import util.SHA256;
import vo.ActionForward;
import vo.AddressBean;
import vo.MemberBean;

public class AdminJoinAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		String a_id = request.getParameter("member_id");
		String a_grade = request.getParameter("grade");
		String a_password = request.getParameter("password");		
		String a_name = request.getParameter("name");
		String a_email = request.getParameter("email");
		int a_account = Integer.parseInt(request.getParameter("account"));
		boolean isAdmin = Boolean.parseBoolean(request.getParameter("isAdmin"));
		
		/*
		MemberBean admin = new MemberBean();
		admin.setId(a_id);
		admin.setGrade(a_grade);
		//admin.setPassword(SHA256.encodeSHA256(a_password));
		admin.setPasswordSHA256(a_password);
		admin.setName(a_name);
		admin.setEmail(a_email);
		admin.setPhone(a_phone);
		*/
		
		//비밀번호 암호화 방법-2 (매개변수가 있는 생성자)
		MemberBean admin = new MemberBean(a_id, a_password, a_name, a_email, a_account, isAdmin);
		
		AdminJoinService adminJoinService = new AdminJoinService();
		boolean isAdminJoinSuccess = adminJoinService.adminJoin(admin);
		
		if(!isAdminJoinSuccess) { //회원가입 실패
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원가입에 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else { //회원가입 성공
			
			//방법-1 : 알림창을 띄움
			/*
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원가입에 성공했습니다.');");
			out.println("location.href='adminLogin.adm'");
			out.println("</script>");
			*/
			
			//방법-2 : 알림창을 띄우지 않음
			forward = new ActionForward("adminLogin.adm", true);
		}
		
		return forward;
	}

}
