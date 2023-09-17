package action.user.qna;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.qna.UserDeleteQnaListService;
import vo.ActionForward;

public class UserDeleteQnaListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		//조회를 위해 u_id를 가져옴
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		
		if(u_id == null) {//만약 로그인 안 된 상태라면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 후 이용가능한 서비스입니다.');");
			out.println("location.href='userLogin.usr'");
			out.println("</script>");
			
		}else {
			
			//파라미터로 넘어온 삭제할 문의글 ID 리스트
			String[] removeQnaList = request.getParameterValues("remove");
			
			UserDeleteQnaListService userDeleteQnaListService = new UserDeleteQnaListService();
			boolean isDeleteUserQnaListSuccess = userDeleteQnaListService.deleteUserQnaList(removeQnaList);
			
			if(!isDeleteUserQnaListSuccess) {//실패 시
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('문의글 삭제가 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}else {
				
				//성공시, 다시 관심프로젝트 목록보기 요청
				forward = new ActionForward("userMyQnaList.usr", true);
				
			}
		}
		
		return forward;
		
	}

}
