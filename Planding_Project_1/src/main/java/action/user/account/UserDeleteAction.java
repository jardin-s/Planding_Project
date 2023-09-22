package action.user.account;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.account.UserDeleteService;
import vo.ActionForward;
import vo.MemberBean;

public class UserDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		String u_id = request.getParameter("member_id");
		String u_password = request.getParameter("password");
		
		//탈퇴사유가 탈퇴회원테이블에 있을 경우
		//String reason = request.getParameter("reason");
		
		//1. 본인인증 확인
		UserDeleteService userDeleteService = new UserDeleteService();
		
		MemberBean user = new MemberBean();
		user.setMember_id(u_id);
		user.setSHA256Password(u_password);
		
		String userIdToDelete = userDeleteService.checkUserSelf(user);
		
		if(userIdToDelete == null) { //본인인증 실패시
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디 또는 비밀번호가 일치하지 않습니다.');");
			out.println("history.back();");
			out.println("</script>");
			
		}else { //본인인증 성공시
			
			//2. 회원탈퇴 (회원 아이디, 관리자여부만 남기고 모든 회원개인정보 삭제)
			boolean isUserDeleteSuccess = userDeleteService.userDelete(user);
			//주소는 이벤트스케줄러 -> 회원탈퇴일 6개월 이후 삭제 (프로젝트 배송시 필요할 수 있으므로)
			
			if(isUserDeleteSuccess == false) { //회원탈퇴 실패 시
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('회원탈퇴에 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}else { //회원탈퇴 성공 시
				//session 영역의 모든 속성 제거
				HttpSession session = request.getSession();
				session.removeAttribute("u_id");
				session.removeAttribute("u_password");
				session.removeAttribute("u_name");
				session.removeAttribute("u_email");
				session.removeAttribute("u_admin_status");
				
				
				//로그인 시 아이디 저장 체크했던 쿠키도 삭제
				/* 1개의 PC를 여러 사용자가 사용하므로 회원탈퇴 후 그 전 사용자의 흔적이 있는 id쿠키객체와 checkbox 쿠키객체를 삭제 */
				Cookie cookieU_id = new Cookie("u_id","");
				cookieU_id.setMaxAge(0);
				response.addCookie(cookieU_id);
				
				Cookie cookieCheckbox = new Cookie("u_checkbox", "");
				cookieCheckbox.setMaxAge(0);
				response.addCookie(cookieCheckbox);
				
				/*
				Cookie[] cookies = request.getCookies();
				for(Cookie c:cookies) {
					if(c.getName().equals("u_id")) {
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
				out.println("location.href='userMain.usr'");//userMain.jsp로 해도 됨
				out.println("</script>");
				
			}
		}
		
		return forward;
	}

}
