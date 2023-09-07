package action.admin.qna;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.qna.ModifyQnaQFormService;
import vo.ActionForward;
import vo.QnaBean;

public class AdminModifyQnaAFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		int page = Integer.parseInt(request.getParameter("page"));
		int qna_id = Integer.parseInt(request.getParameter("qna_id"));
				
		HttpSession session = request.getSession();
		String a_id = (String) session.getAttribute("a_id");
		
		if(a_id == null) {//로그인이 풀린 상태라면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('해당 서비스는 로그인이 필요합니다.');");
			out.println("location.href='adminLoginForm.adm';");
			out.println("</script>");
		}else {
			
			//qna_id로 문의글 정보를 가져옴 (수정글에서 세팅을 위해)
			ModifyQnaQFormService modifyQnaQFormService = new ModifyQnaQFormService();
			QnaBean qnaInfo = modifyQnaQFormService.getQnaInfo(qna_id);
			
			request.setAttribute("page", page);
			request.setAttribute("qnaInfo", qnaInfo);
			
			request.setAttribute("showAdmin", "admin/qna/modifyQnaAForm.jsp");
			forward = new ActionForward("adminTemplate.jsp", false);
		}
		
		return forward;
	}

}
