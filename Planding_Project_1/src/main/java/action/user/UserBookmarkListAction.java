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
			
			//[방법-1] 프로젝트 ID만 가지고와서 id로 프로젝트 정보 가져옴
			UserBookmarkListService userBookmarkListService = new UserBookmarkListService();
			ArrayList<Integer> bookmarkIdList = userBookmarkListService.getBookmarkIdList(u_id);
			ArrayList<ProjectBean> bookmarkList = userBookmarkListService.getProjectList(bookmarkIdList);
			
			//[방법-2] BookmarkBean(프로젝트 ID, 멤버 ID, 추가일 모두 얻어옴)
			//ArrayList<BookmarkBean> bookmarkList =  userBookmarkListService.getBookmarkList(u_id);
			//ArrayList<ProjectBean> projectList userBookmarkListService.getProjectList(bookmarkList);
			
			request.setAttribute("bookmarkList", bookmarkList);
			
			request.setAttribute("showPage", "user/myPage/userBookmarkList.jsp");
			forward = new ActionForward("userTemplate.jsp", false);		
		}
		
		return forward;
		
	}

}
