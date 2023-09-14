package action.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.project.ProjectPageViewService;
import svc.project.ProjectPageViewService2;
import svc.project.reward.ProjectPageViewService3;
import vo.ActionForward;
import vo.PlannerBean;
import vo.ProjectBean;
import vo.RewardBean;

public class fundPageViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession();
		int project_id = (int) session.getAttribute("project_id"); // 기본값 설정

		ProjectPageViewService donatePageViewService = new ProjectPageViewService();
		ProjectBean pj=new ProjectBean();
		pj=donatePageViewService.projectPageViewService(project_id);
		
		
		String member_id=(String)session.getAttribute("u_id");
		
		ProjectPageViewService2 donatePageViewService2 = new ProjectPageViewService2();
		PlannerBean planner=new PlannerBean();
		planner=donatePageViewService2.projectPageViewService2(project_id, member_id);
		

		request.setAttribute("pj", pj);
		request.setAttribute("planner", planner);
		
		request.setAttribute("showPage", "project/insertProjectView.jsp");//어느 폼 보기인지 showPage이름 속성으로 저장
		forward = new ActionForward("userTemplate.jsp",false);//반드시 디스패치 (request를 공유)
	

		
		
		return forward;
	}

}