package action.admin.qna;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.admin.qna.AdminModifyQnaAService;
import vo.ActionForward;
import vo.QnaBean;

public class AdminModifyQnaAAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		int page = Integer.parseInt(request.getParameter("page"));
		int qna_id = Integer.parseInt(request.getParameter("qna_id"));
		String a_content = request.getParameter("a_content");
		
		QnaBean qna = new QnaBean();
		qna.setQna_id(qna_id);
		qna.setA_content(a_content);
		
		AdminModifyQnaAService adminModifyQnaAService = new AdminModifyQnaAService();
		boolean isQnaAModifySuccess = adminModifyQnaAService.updateAnswer(qna);
		
		if(!isQnaAModifySuccess) {
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('문의글 수정이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			
			forward = new ActionForward("adminQnaView.adm?page="+page+"&qna_id="+qna_id, true);			
			
		}
		
		
		
		
		
		//QnaNewQuestionService qnaNewQuestionService = new QnaNewQuestionService();
		//boolean isWriteSuccess = qnaNewQuestionService.insertNewQuestion(qna);
		
//		if(!isWriteSuccess) {//글 등록 실패 시
//			response.setContentType("text/html; charset=utf-8");
//			PrintWriter out = response.getWriter();
//			out.println("<script>");
//			out.println("alert('문의글 등록이 실패했습니다.');");
//			out.println("history.back();");
//			out.println("</script>");
//		}else {//글 등록 성공 시
//						
//			request.setAttribute("showPage", "qna/qnaList.jsp");
//			forward = new ActionForward("userTemplage.jsp", false);
//		}		
		
		return forward;
		
	}

}
