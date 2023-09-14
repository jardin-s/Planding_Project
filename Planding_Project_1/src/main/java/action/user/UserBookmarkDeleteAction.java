package action.user;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.UserBookmarkDeleteService;
import vo.ActionForward;

public class UserBookmarkDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		//북마크에서 삭제할 프로젝트ID
		String[] removeProjectId = request.getParameterValues("remove");
		
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		
		if(u_id == null) {//로그인이 풀린 상태라면
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인이 필요합니다.');");
			out.println("userLoginForm.usr;");
			out.println("</script>");
		}else {
			
			//프로젝트ID로 북마크 삭제
			UserBookmarkDeleteService userBookmarkDeleteService = new UserBookmarkDeleteService();
			boolean isDeleteBookmarkListSuccess = userBookmarkDeleteService.deleteBookmarkList(removeProjectId);
			
			if(!isDeleteBookmarkListSuccess) {//실패 시
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('관심프로젝트 삭제가 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}else {
				
				//성공시, 다시 관심프로젝트 목록보기 요청
				forward = new ActionForward("userBookmarkList.usr", true);
				
			}
			
		}	
		
		return forward;
	}

}
