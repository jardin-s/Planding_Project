package action.admin.qna;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.qna.QnaViewService;
import vo.ActionForward;
import vo.QnaBean;

public class AdminInsertQnaAFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		String a_id = (String) session.getAttribute("a_id");
		
		if(a_id == null) {//만약 로그인 풀린 상태라면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");			
			out.println("alert('해당 서비스는 로그인이 필요합니다.');");
			out.println("location.href='adminLoginForm.adm';");			
			out.println("</script>");		
		
		}else {//로그인 된 상태면 답변쓰기 폼으로 이동
		
			int qna_id = Integer.parseInt(request.getParameter("qna_id"));
			int page = Integer.parseInt(request.getParameter("page"));
			
			System.out.println("[AdminInsertQnaAFormAction] 파라미터값");
			System.out.println("qna_id = "+qna_id);
			System.out.println("page = "+page);
			
			//qna_id로 문의글을 가져옴
			QnaViewService qnaViewService = new QnaViewService();
			QnaBean qnaInfo = qnaViewService.getQnaInfo(qna_id);
			
			request.setAttribute("qnaInfo", qnaInfo);
			request.setAttribute("page", page);
			
			request.setAttribute("showAdmin", "admin/qna/insertQnaAForm.jsp");
			forward = new ActionForward("adminTemplate.jsp", false);
			
		}
		
		
		return forward;
	}

}
