package action.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.project.ProjectPageViewService;
import vo.ActionForward;
import vo.PlannerBean;
import vo.ProjectBean;
import vo.RewardBean;

public class DonateTempPageViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		//세션에 저장된 project_id를 가지고 옴
		HttpSession session = request.getSession();
		int project_id = (int) session.getAttribute("project_id"); // 기본값 설정

		//project_id로 프로젝트 정보를 얻어옴
		ProjectPageViewService projectPageViewService = new ProjectPageViewService();
		ProjectBean projectInfo = projectPageViewService.getProjectInfo(project_id);
		
		//세션에 저장된 사용자 id를 가지고 옴
		String member_id = (String) session.getAttribute("u_id");
		
		//프로젝트id, 사용자id로 기획자 정보를 얻어옴
		PlannerBean plannerInfo = projectPageViewService.getPlannerInfo(project_id, member_id);
		
		//리워드 ID (기본리워드 : 후원금액 1000원 기본세팅) (기부프로젝트 기본후원금액 1000원부터 시작)
		/* ★★리워드ID를 varchar로 수정하고, "pj"+프로젝트ID+"rwd"+"리워드 index"로 세팅해야 함 */
		//int reward_id = 1;
		//String r_name = "donate";//기부용 리워드로 donate로 이름 고정
		RewardBean rewardInfo = projectPageViewService.getRewardInfo(1, "donate");
		
		//프로젝트 정보, 기획자 정보, 리워드 정보를 request 속성값으로 저장
		request.setAttribute("projectInfo", projectInfo);
		request.setAttribute("plannerInfo", plannerInfo);
		request.setAttribute("rewardInfo", rewardInfo);
		
		//미리보기 페이지로 이동
		request.setAttribute("showPage", "project/insertProjectTempView.jsp");//어느 폼 보기인지 showPage이름 속성으로 저장
		forward = new ActionForward("userTemplate.jsp",false);//반드시 디스패치 (request를 공유)
	
		return forward;
	}

}