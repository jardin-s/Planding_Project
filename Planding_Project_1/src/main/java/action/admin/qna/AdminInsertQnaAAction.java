package action.admin.qna;

import java.io.File;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import svc.admin.qna.QnaNewAnswerService;
import vo.ActionForward;
import vo.QnaBean;

public class AdminInsertQnaAAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		int page = Integer.parseInt(request.getParameter("page"));
		int qna_id = Integer.parseInt(request.getParameter("qna_id"));
		String a_content = request.getParameter("a_content");
		
		QnaBean qna = new QnaBean();
		qna.setQna_id(qna_id);
		qna.setA_content(a_content);
		
		QnaNewAnswerService qnaNewAnswerService = new QnaNewAnswerService();
		boolean isAnswerSuccess = qnaNewAnswerService.insertNewAnswer(qna);
		
		if(!isAnswerSuccess) {//답변 등록 실패 시
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('답변 등록이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {//답변 등록 성공 시
			
			forward = new ActionForward("adminQnaView.adm?qna_id="+qna_id+"&page="+page, true);
		}
		
		return forward;
	}

}
