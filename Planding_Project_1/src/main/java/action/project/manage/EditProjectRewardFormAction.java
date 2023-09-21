package action.project.manage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.project.ProjectPageViewService;
import vo.ActionForward;
import vo.RewardBean;

public class EditProjectRewardFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String reward_id = request.getParameter("reward_id");
		int project_id = Integer.parseInt(request.getParameter("project_id"));
		
		ProjectPageViewService projectPageViewService = new ProjectPageViewService();
		
		RewardBean rewardInfo = projectPageViewService.getRewardInfo(reward_id);
		
		request.setAttribute("rewardInfo", rewardInfo);
		request.setAttribute("project_id", project_id);
		
		request.setAttribute("showPage", "project/manage/editProjectRewardForm.jsp");
		forward = new ActionForward("userTemplate.jsp", false);
		
		return forward;
	}

}
