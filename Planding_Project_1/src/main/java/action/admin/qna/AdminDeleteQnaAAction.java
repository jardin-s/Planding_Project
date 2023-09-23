package action.admin.qna;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.qna.DeleteQnaService;
import vo.ActionForward;

public class AdminDeleteQnaAAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		//파라미터값
		int page = Integer.parseInt(request.getParameter("page"));
		int qna_id =  Integer.parseInt(request.getParameter("qna_id"));
		String a_writer =  request.getParameter("a_writer");
		
		System.out.println("[AdminDeleteQnaAAction] 파라미터값");
		System.out.println("page="+page);
		System.out.println("qna_id="+qna_id);
		
		HttpSession session = request.getSession();
		String a_id= (String) session.getAttribute("a_id");
		
		if(a_id == null) {//관리자 로그인이 풀린 상태라면
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('해당 서비스는 로그인이 필요합니다.');");
			out.println("adminLoginForm.adm;");
			out.println("</script>");
			
		}else if(!a_id.equals(a_writer)){//답변을 등록한 관리자가 아닌 경우
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('본인이 작성한 답변만 삭제가능합니다.');");
			out.println("history.back();");
			out.println("</script>");
		
		}else {
			
			//qna_id로 답변 삭제
			DeleteQnaService deleteQnaService = new DeleteQnaService();
			boolean isDeleteQnaASuccess = deleteQnaService.deleteQnaA(qna_id);
			
			
			if(!isDeleteQnaASuccess) {//답변 삭제 실패
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('답변 삭제가 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");			
			}else {
				
				//답변 삭제 후 다시 해당 답변 보기 요청
				forward = new ActionForward("adminQnaView.adm?qna_id="+qna_id+"&page="+page, true);
				
			}
		}
				
		return forward;
	}

}
