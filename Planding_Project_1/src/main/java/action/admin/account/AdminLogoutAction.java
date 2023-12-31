package action.admin.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import vo.ActionForward;

public class AdminLogoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		/**
		 * session 영역의 속성들을 제거
		 */
		//session을 request로 가져오는 이유? sessionID가 클라이언트영역의 쿠키영역에 저장되어 있으므로
		HttpSession session = request.getSession();
		//session.invalidate();//세션의 모든 속성들 삭제	
		
		session.removeAttribute("a_id");
		session.removeAttribute("a_password");
		session.removeAttribute("a_grade");		
		session.removeAttribute("a_name");			
		session.removeAttribute("a_email");
		
		//관리자 페이지 로그아웃시 기존 사용자모드 홈페이지로 이동
		request.setAttribute("showPage", "user/userMain.jsp");
		forward = new ActionForward("userTemplate.jsp", false);
		return forward;
		
	}

}
