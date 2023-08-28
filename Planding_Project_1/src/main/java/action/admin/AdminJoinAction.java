package action.admin;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
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
		
		String a_id = request.getParameter("id");
		String a_grade = request.getParameter("grade");//admin
		String a_password = request.getParameter("password");		
		String a_name = request.getParameter("name");
		String a_email = request.getParameter("email");
		String a_phone = request.getParameter("phone");
		
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
		MemberBean admin = new MemberBean(a_id, a_grade, a_password, a_name, a_email, a_phone);
		
		int postcode = Integer.parseInt(request.getParameter("postcode"));
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		
		AddressBean addr = new AddressBean(a_id, postcode, address1, address2);
		
		AdminJoinService adminJoinService = new AdminJoinService();
		boolean isAdminJoinSuccess = adminJoinService.adminJoin(admin, addr);
		
		if(!isAdminJoinSuccess) { //관리자 등록 실패
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('관리자 등록에 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else { //회원가입 성공
			/*
			 * 1개의 PC를 1사람의 관리자 id로 사용하므로
			 * 관리자 등록 후 그전 사용자 흔적이 있는 id쿠키객체와 checkbox 쿠키객체를 삭제할 필요가 없다.
			 * 다시 회원가입한 사용자가 새롭게 입력할 수 있도록 하기
			 */
			/*
			Cookie cookieA_id = new Cookie("a_id", "");
			cookieA_id.setMaxAge(0);
			
			Cookie cookieCheckbox = new Cookie("checkbox","");
			cookieCheckbox.setMaxAge(0);
			
			response.addCookie(cookieA_id);
			response.addCookie(cookieCheckbox);
			*/
			
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
