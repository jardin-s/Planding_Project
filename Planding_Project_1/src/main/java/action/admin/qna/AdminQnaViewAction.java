package action.admin.qna;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.qna.QnaViewService;
import vo.ActionForward;
import vo.QnaBean;

public class AdminQnaViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		int qna_id = Integer.parseInt(request.getParameter("qna_id"));
		int page = Integer.parseInt(request.getParameter("page"));
		
		System.out.println("[QnaViewAction] 파라미터값");
		System.out.println("qna_id = "+qna_id);
		System.out.println("page = "+page);
				
		QnaViewService qnaViewService = new QnaViewService();
		QnaBean qna = qnaViewService.getQnaInfo(qna_id);
		
		if(qna == null) {//리스트에서 글 보고 있는 사이, 작성자가 해당 글을 삭제했을 경우
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제된 글입니다.');");
			out.println("history.back();");
			out.println("</script>");
			
		}else {
			
			request.setAttribute("page", page);
			request.setAttribute("qnaInfo", qna);
			
			request.setAttribute("showAdmin", "admin/qna/qnaView.jsp");
			forward = new ActionForward("adminTemplate.jsp", false);
			
		}
		
		return forward;
	}

}
