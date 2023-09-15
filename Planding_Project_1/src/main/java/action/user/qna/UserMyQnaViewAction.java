package action.user.qna;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.qna.QnaViewService;
import vo.ActionForward;
import vo.QnaBean;

public class UserMyQnaViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		int qna_id = Integer.parseInt(request.getParameter("qna_id"));
		int page = 1;//상세보기에서 목록보기 클릭 시 전체 문의사항 리스트의 첫 페이지를 로딩
		
		System.out.println("[UserMyQnaViewAction] 파라미터값");
		System.out.println("qna_id = "+qna_id);
		
		//qna_id로 해당 글의 정보를 얻어옴
		QnaViewService qnaViewService = new QnaViewService();
		QnaBean qna = qnaViewService.getQnaInfo(qna_id);
		
		if(qna == null) {//리스트에서 글 보고 있는 사이, 관리자가 해당 글을 삭제했을 경우
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제된 글입니다.');");
			out.println("history.back();");
			out.println("</script>");
			
		}else {
			
			request.setAttribute("page", page);
			request.setAttribute("qnaInfo", qna);
			
			//나의 문의글 보기에서 넘어온 경우, 목록 클릭 시 다시 내 문의글 목록으로 돌아가야 함
			request.setAttribute("myqna", "myqna");
			
			request.setAttribute("showPage", "user/qna/qnaView.jsp");
			forward = new ActionForward("userTemplate.jsp", false);
			
		}
		
		return forward;
	}

}
