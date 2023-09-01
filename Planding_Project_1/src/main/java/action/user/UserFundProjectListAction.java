package action.user;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.UserFundProjectListService;
import svc.user.UserBookmarkListService;
import vo.ActionForward;
import vo.ProjectBean;

public class UserFundProjectListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		
		UserFundProjectListService fundProjectListService = new UserFundProjectListService();
		//[방법-1] 프로젝트 ID만 가지고와서 id로 프로젝트 정보 가져옴
		ArrayList<Integer> fundProjectIdList = fundProjectListService.getProjectIdList(u_id);
		ArrayList<ProjectBean> fundProjectList = fundProjectListService.getProjectList(fundProjectIdList);
		
		//[방법-2] BookmarkBean(프로젝트 ID, 멤버 ID, 추가일 모두 얻어옴)
		//ArrayList<BookmarkBean> bookmarkList =  userBookmarkListService.getBookmarkList(u_id);
		//ArrayList<ProjectBean> projectList userBookmarkListService.getProjectList(bookmarkList);
		
		request.setAttribute("fundProjectList", fundProjectList);
		
		request.setAttribute("showPage", "user/myPage/userFundProjectList.jsp");
		forward = new ActionForward("userTemplate.jsp", false);		
		
		return forward;
	}

}
