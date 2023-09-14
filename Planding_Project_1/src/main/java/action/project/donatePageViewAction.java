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

public class donatePageViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession();
		
		int project_id=Integer.parseInt((String)session.getAttribute("project_id"));
		
		
		ProjectPageViewService donatePageViewService = new ProjectPageViewService();
		ProjectBean pj=new ProjectBean();
		pj=donatePageViewService.projectPageViewService(project_id);
		
		String member_id=(String)session.getAttribute("u_id");
		
		ProjectPageViewService2 donatePageViewService2 = new ProjectPageViewService2();
		PlannerBean planner=new PlannerBean();
		planner=donatePageViewService2.projectPageViewService2(project_id, member_id);
		
		int reward_id=1;
		String r_name="donate";
		
		ProjectPageViewService3 projectPageViewService3 = new ProjectPageViewService3();
		RewardBean reward=new RewardBean();
		reward=projectPageViewService3.projectPageViewService3(reward_id, r_name);
		
		request.setAttribute("pj", pj);
		request.setAttribute("planner", planner);
		request.setAttribute("reward", reward);
		
		request.setAttribute("showPage", "project/insertProjectSuccess.jsp");
        forward = new ActionForward("userTemplate.jsp", false);

		
		
		return forward;
	}

}