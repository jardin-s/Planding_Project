package action.admin.account;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.admin.account.AdminDeleteService;
import svc.user.account.UserDeleteService;
import vo.ActionForward;
import vo.MemberBean;

public class AdminDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		String a_id = request.getParameter("member_id");
		String a_password = request.getParameter("password");
		
		//탈퇴사유가 탈퇴회원테이블에 있을 경우
		//String reason = request.getParameter("reason");
		
		//1. 본인인증 확인
		AdminDeleteService adminDeleteService = new AdminDeleteService();
		
		MemberBean user = new MemberBean();
		user.setMember_id(a_id);
		user.setSHA256Password(a_password);
		
		String userIdToDelete = adminDeleteService.checkAdminSelf(user);
		
		if(userIdToDelete == null) { //본인인증 실패시
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디 또는 비밀번호가 일치하지 않습니다.');");
			out.println("history.back();");
			out.println("</script>");
			
		}else { //본인인증 성공시
			
			boolean isAdminDeleteSuccess = adminDeleteService.adminDelete(userIdToDelete);
			
			if(isAdminDeleteSuccess == false) { //회원탈퇴 실패 시
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('회원탈퇴에 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}else { //회원탈퇴 성공 시
				//session 영역의 모든 속성 제거
				HttpSession session = request.getSession();
				session.removeAttribute("a_id");
				session.removeAttribute("a_password");
				session.removeAttribute("a_name");
				session.removeAttribute("a_email");
				session.removeAttribute("a_admin_status");
				
				
				//로그인 시 아이디 저장 체크했던 쿠키도 삭제
				/* 1개의 PC를 여러 사용자가 사용하므로 회원탈퇴 후 그 전 사용자의 흔적이 있는 id쿠키객체와 checkbox 쿠키객체를 삭제 */
				Cookie cookieA_id = new Cookie("a_id","");
				cookieA_id.setMaxAge(0);
				response.addCookie(cookieA_id);
				
				Cookie cookieCheckbox = new Cookie("a_checkbox", "");
				cookieCheckbox.setMaxAge(0);
				response.addCookie(cookieCheckbox);
				
				/*
				Cookie[] cookies = request.getCookies();
				for(Cookie c:cookies) {
					if(c.getName().equals("a_id")) {
						c.setMaxAge(0);
					}
					if(c.getName().equals("checkbox")) {
						c.setMaxAge(0);
					}
				}
				*/
				
				//회원탈퇴 성공 알림창 -> '메인으로 돌아가기' 요청
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('회원탈퇴 처리되었습니다.');");
				out.println("location.href='userMain.usr'");
				out.println("</script>");
				
			}
		}
		
		
		
		
		return forward;
	}

}
