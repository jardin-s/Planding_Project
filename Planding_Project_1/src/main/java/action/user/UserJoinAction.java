package action.user;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.user.UserJoinService;
import util.SHA256;
import vo.ActionForward;
import vo.AddressBean;
import vo.MemberBean;

public class UserJoinAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		String u_id = request.getParameter("member_id");
		String u_password = request.getParameter("password");		
		String u_name = request.getParameter("name");
		String u_email = request.getParameter("email");
		String u_phone = request.getParameter("phone");
		int u_account = Integer.parseInt(request.getParameter("account"));
		String u_admin = request.getParameter("admin_status");
		
		System.out.println("[UserJoinAction] 회원가입 폼에서 전송받은 값");
		System.out.println("member_id="+u_id);
		System.out.println("password="+u_password);
		System.out.println("name="+u_name);
		System.out.println("email="+u_email);
		System.out.println("phone="+u_phone);
		System.out.println("account="+u_account);
		System.out.println("admin_status="+u_admin);
		
		/*
		MemberBean user = new MemberBean();
		user.setId(u_id);
		user.setGrade(u_grade);
		//user.setPassword(SHA256.encodeSHA256(u_password));
		user.setPasswordSHA256(u_password);
		user.setName(u_name);
		user.setEmail(u_email);
		user.setPhone(u_phone);
		*/
		
		//비밀번호 암호화 방법-2 (매개변수가 있는 생성자)
		MemberBean user = new MemberBean(u_id, u_password, u_name, u_email, u_phone, u_account, u_admin);
		
		UserJoinService userJoinService = new UserJoinService();
		boolean isUserJoinSuccess = userJoinService.userJoin(user);
		
		if(!isUserJoinSuccess) { //회원가입 실패
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
			out.println("location.href='userLogin.usr'");
			out.println("</script>");
			*/
			
			//방법-2 : 알림창을 띄우지 않음
			forward = new ActionForward("userLoginForm.usr", true);
		}
		
		return forward;
	}

}
