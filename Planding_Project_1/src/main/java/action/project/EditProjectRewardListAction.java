package action.project;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.project.ProjectPageViewService;
import vo.ActionForward;
import vo.RewardBean;


public class EditProjectRewardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		int project_id = Integer.parseInt(request.getParameter("project_id"));
		ProjectPageViewService projectPageViewService = new ProjectPageViewService();
		
		
		
		ArrayList<String> reward_id_list = new ArrayList<>();
		ArrayList<RewardBean> rewardList = new ArrayList<>();
		//리워드 아이디 리스트 얻어오기
		reward_id_list = projectPageViewService.getRewardIdList(project_id);
		
		
		//리워드 아이디로 리스트객체 얻어오기
		for(int i=0;i<reward_id_list.size();i++) {
			System.out.println("getRewardIdList 확인 : "+ reward_id_list.get(i));
			
			rewardList.add(projectPageViewService.getRewardInfo(reward_id_list.get(i)));
		}
		System.out.println("rewardList 확인 : "+ rewardList.toString());
		request.setAttribute("rewardList", rewardList);
		request.setAttribute("project_id", project_id);
		
		
		request.setAttribute("showPage", "project/selectEditRewardForm.jsp");
		forward = new ActionForward("userTemplate.jsp", false);
		
		return forward;
	}

}