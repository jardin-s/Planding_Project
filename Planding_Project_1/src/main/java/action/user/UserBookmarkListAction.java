package action.user;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.UserBookmarkListService;
import vo.ActionForward;
import vo.BookmarkBean;
import vo.ProjectBean;

public class UserBookmarkListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		String u_id = (String)session.getAttribute("u_id");
		
		
		if(u_id == null) {//만약 로그인 안 된 상태라면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 후 이용가능한 서비스입니다.');");
			out.println("location.href='userLogin.usr'");
			out.println("</script>");
		}else {
			
						
			UserBookmarkListService userBookmarkListService = new UserBookmarkListService();
			
			//[순서-1] 프로젝트 ID만 가지고와서 
			ArrayList<Integer> bookmarkIdList = userBookmarkListService.getBookmarkIdList(u_id);
			
			//[순서-2] 프로젝트ID로 프로젝트 정보가 담긴 리스트를 가져옴
			ArrayList<ProjectBean> bookmarkList = null;
			if(bookmarkIdList != null) {//ID리스트가 있으면
				bookmarkList = userBookmarkListService.getProjectList(bookmarkIdList);
			}
						
			request.setAttribute("bookmarkList", bookmarkList);
			
			request.setAttribute("showPage", "user/myPage/userBookmarkList.jsp");
			forward = new ActionForward("userTemplate.jsp", false);		
		}
		
		return forward;
		
	}

}
