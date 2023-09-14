package action.user.qna;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.qna.ModifyQnaQFormService;
import vo.ActionForward;
import vo.QnaBean;

public class UserModifyQnaQFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		int qna_id = Integer.parseInt(request.getParameter("qna_id"));
		String q_writer = request.getParameter("q_writer");
		
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		
		if(!u_id.equals(q_writer)) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('해당 글을 수정할 권한이 없습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			
			ModifyQnaQFormService qnaEditFormService = new ModifyQnaQFormService();
			QnaBean qnaInfo = qnaEditFormService.getQnaInfo(qna_id);
			
			request.setAttribute("qnaInfo", qnaInfo);
			request.setAttribute("showPage", "user/qna/modifyQnaQForm.jsp");
			
			forward = new ActionForward("userTemplate.jsp", false);
		}
		
		return forward;
	}

}
