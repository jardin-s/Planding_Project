package action.user.qna;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.qna.QnaViewService;
import vo.ActionForward;
import vo.QnaBean;

public class UserQnaViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		
		
		int qna_id = Integer.parseInt(request.getParameter("qna_id"));
		int page = Integer.parseInt(request.getParameter("page"));
		String q_private = request.getParameter("q_private");
		
		System.out.println("[QnaViewAction] 파라미터값");
		System.out.println("qna_id = "+qna_id);
		System.out.println("page = "+page);
		System.out.println("q_private = "+q_private);
		
		
		//비밀글인 경우, 현재 회원이 작성자가 아니면 제한해야하므로
		HttpSession session =  request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		
		QnaViewService qnaViewService = new QnaViewService();
		QnaBean qna = qnaViewService.getQnaInfo(qna_id);
		
		if(qna == null) {//리스트에서 글 보고 있는 사이, 작성자가 해당 글을 삭제했을 경우
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제된 글입니다.');");
			out.println("history.back();");
			out.println("</script>");
			
		}else if(q_private.equalsIgnoreCase("Y") && !u_id.equals(qna.getMember_id())) {//비밀글이고, 현재 회원이 작성자가 아니라면
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('해당 글을 볼 수 있는 권한이 없습니다.');");
			out.println("history.back();");
			out.println("</script>");
			
		}else if((q_private.equalsIgnoreCase("Y") && u_id.equals(qna.getMember_id()))//비밀글의 작성자이거나 비밀글이 아니면
				|| q_private.equalsIgnoreCase("N")) {
			
			request.setAttribute("page", page);
			request.setAttribute("qnaInfo", qna);
			
			request.setAttribute("showPage", "user/qna/qnaView.jsp");
			forward = new ActionForward("userTemplate.jsp", false);
			
		}
		
		return forward;
	}

}
