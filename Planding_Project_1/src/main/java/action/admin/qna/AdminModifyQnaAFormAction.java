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
		String a_writer = request.getParameter("a_writer");
				
		HttpSession session = request.getSession();
		String a_id = (String) session.getAttribute("a_id");
		
		if(a_id == null) {//로그인이 풀린 상태라면
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('해당 서비스는 로그인이 필요합니다.');");
			out.println("location.href='adminLoginForm.adm';");
			out.println("</script>");
			
		}else if(!a_id.equals(a_writer)){//답변을 등록한 관리자가 아닌 경우
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('본인이 작성한 답변만 수정가능합니다.');");
			out.println("location.href='adminLoginForm.adm';");
			out.println("</script>");
		
		}else {//본인 답변이라면
			
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
