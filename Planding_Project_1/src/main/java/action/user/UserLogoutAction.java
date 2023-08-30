package action.user;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import vo.ActionForward;

public class UserLogoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		response.setContentType("text/html; charset=utf-8");
				
		/**
		 * session 영역의 속성들을 제거
		 */
		//session을 request로 가져오는 이유? sessionID가 클라이언트영역의 쿠키영역에 저장되어 있으므로
		HttpSession session = request.getSession();
		//session.invalidate();//세션의 모든 속성들 삭제	
		
		session.removeAttribute("u_id");
		session.removeAttribute("u_password");
		session.removeAttribute("u_grade");		
		session.removeAttribute("u_name");			
		session.removeAttribute("u_email");
		
		System.out.println("[UserLogoutAction] 세션의 모든 사용자 정보 삭제 완료");
				
		forward = new ActionForward("userMain.usr", true);
		return forward;
		
	}

}
