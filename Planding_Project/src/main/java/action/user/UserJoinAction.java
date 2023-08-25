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
		
		String u_id = request.getParameter("id");
		String u_grade = request.getParameter("grade");
		String u_password = request.getParameter("password");		
		String u_name = request.getParameter("name");
		String u_email = request.getParameter("email");
		String u_phone = request.getParameter("phone");
		
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
		MemberBean user = new MemberBean(u_id, u_grade, u_password, u_name, u_email, u_phone);
		
		int postcode = Integer.parseInt(request.getParameter("postcode"));
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		
		AddressBean addr = new AddressBean(u_id, postcode, address1, address2);
		
		UserJoinService userJoinService = new UserJoinService();
		boolean isUserJoinSuccess = userJoinService.userJoin(user, addr);
		
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
			forward = new ActionForward("userLogin.usr", true);
		}
		
		return forward;
	}

}
