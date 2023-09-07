package action.user.qna;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import vo.ActionForward;

public class UserInsertQnaQFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		
		if(u_id == null) {//만약 로그인 안 된 상태라면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");			
			out.println("alert('해당 서비스는 로그인이 필요합니다.');");
			out.println("history.back();");			
			out.println("</script>");			
		
		}else {//로그인 된 상태면 글쓰기 폼으로 이동
		
			request.setAttribute("showPage", "user/qna/insertQnaQForm.jsp");
			forward = new ActionForward("userTemplate.jsp", false);
			
		}
		
		
		return forward;
	}

}
