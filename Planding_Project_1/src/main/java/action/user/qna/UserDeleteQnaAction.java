package action.user.qna;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.qna.DeleteQnaService;
import vo.ActionForward;
import vo.QnaBean;

public class UserDeleteQnaAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		//파라미터값
		int page = Integer.parseInt(request.getParameter("page"));
		int qna_id =  Integer.parseInt(request.getParameter("qna_id"));
		String q_writer =  request.getParameter("q_writer");
		
		System.out.println("[DeleteQnaAction] 파라미터값");
		System.out.println("page="+page);
		System.out.println("qna_id="+qna_id);
		System.out.println("q_writer="+q_writer);
		
		HttpSession session = request.getSession();
		String u_id= (String) session.getAttribute("u_id");
		
		if(!u_id.equals(q_writer)) {
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('해당 글을 삭제할 권한이 없습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			
			DeleteQnaService deleteQnaService = new DeleteQnaService();
			boolean isDeleteQnaSuccess = deleteQnaService.deleteQna(qna_id);
			
			
			if(!isDeleteQnaSuccess) {//글 삭제 실패
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('문의글 삭제가 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");			
			}else {
				
				forward = new ActionForward("qnaList.qna?page="+page, true);
				
			}
		}
				
		return forward;
	}

}
