package action.admin;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.admin.AdminDeleteService;
import vo.ActionForward;

public class AdminDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		String id = request.getParameter("id");
		
		AdminDeleteService adminDeleteService = new AdminDeleteService();
		boolean isAdminDeleteSuccess = adminDeleteService.adminDelete(id);
		
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
			session.removeAttribute("a_grade");
			session.removeAttribute("a_name");
			session.removeAttribute("a_email");
			
			//+추가적으로 제거 (추후에 구현할 내용들)
			session.removeAttribute("cartList");
			session.removeAttribute("totalMoney");
			session.removeAttribute("saleTotalMoney");
			session.removeAttribute("latestOrder");//가장최근주문건
			
			//로그인 시 아이디 저장 체크했던 쿠키도 삭제
			/* 1개의 PC를 여러 사용자가 사용하므로 회원탈퇴 후 그 전 사용자의 흔적이 있는 id쿠키객체와 checkbox 쿠키객체를 삭제 */
			Cookie cookieA_id = new Cookie("a_id","");
			cookieA_id.setMaxAge(0);
			response.addCookie(cookieA_id);
			
			Cookie cookieCheckbox = new Cookie("checkbox", "");
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
			out.println("location.href='adminMain.adm'");//adminMain.jsp로 해도 됨
			out.println("</script>");
			
		}
		
		
		return forward;
	}

}
