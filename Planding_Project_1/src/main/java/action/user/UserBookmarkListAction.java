package action.user;

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
		
		UserBookmarkListService userBookmarkListService = new UserBookmarkListService();
		ArrayList<BookmarkBean> bookmarkList =  userBookmarkListService.getBookmarkList(u_id);
		//ArrayList<ProjectBean> projectList userBookmarkListService.getProjectList(bookmarkList);
		
		return forward;
	}

}
