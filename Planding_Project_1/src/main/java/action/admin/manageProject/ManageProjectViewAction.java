package action.admin.manageProject;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.project.ManageProjectViewService;
import svc.project.ProjectPageViewService;
import vo.ActionForward;
import vo.MemberBean;
import vo.PlannerBean;
import vo.ProjectBean;
import vo.RewardBean;

public class ManageProjectViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		//파라미터값들
		int project_id = Integer.parseInt(request.getParameter("project_id"));
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}			
				
		
		System.out.println("[ManageProjectViewAction] 파라미터값 확인");
		System.out.println("project_id = "+project_id);
		System.out.println("page = "+page);
		
		//ManageProjectViewService manageProjectViewService = new ManageProjectViewService();
		
		ProjectPageViewService projectPageViewService = new ProjectPageViewService();
		
		//프로젝트 정보를 얻어옴 (달성률 세팅된 상태)
		ProjectBean projectInfo = projectPageViewService.getProjectInfo(project_id);
		request.setAttribute("projectInfo", projectInfo);
		//프로젝트 남은일수는 관리자모드에서는 계산하지 않음
		System.out.println("[ManageProjectViewAction] projectInfo = "+projectInfo);
		
		//프로젝트 기획자 정보를 얻어옴
		PlannerBean plannerInfo = projectPageViewService.getPlannerInfo(project_id);
		request.setAttribute("plannerInfo", plannerInfo);
		System.out.println("[ManageProjectViewAction] plannerInfo = "+plannerInfo);
		
		ArrayList<RewardBean> rewardList = null;
		if(projectInfo.getKind().equals("donate")) {//기부 프로젝트라면
			
			//기본리워드 정보만 얻어와서 저장
			rewardList = new ArrayList<>();
			rewardList.add(projectPageViewService.getRewardInfo("default"));
			System.out.println("[ManageProjectViewAction] rewardList = "+rewardList);
			
		}else if(projectInfo.getKind().equals("fund")) {//펀딩 프로젝트라면
			
			//프로젝트-리워드 매핑 테이블에서 리워드ID리스트를 얻어옴
			ArrayList<String> rewardIdList = projectPageViewService.getAllRewardIdList(project_id);
			if(rewardIdList == null) {
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('리워드ID 불러오기 요청이 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}else {
				
				System.out.println("[ManageProjectViewAction] rewardIdList = "+rewardIdList);
				
				//리워드ID리스트로 리워드리스트를 얻어옴
				rewardList = projectPageViewService.getRewardList(rewardIdList);
				System.out.println("[ManageProjectViewAction] rewardList = "+rewardList);
				
			}
		}
		request.setAttribute("rewardList", rewardList);
		//projectInfo, plannerInfo, rewardList가 넘어온 상태
		
		//페이지넘버를 request속성으로 넘김
		request.setAttribute("page", page);
		
		request.setAttribute("showAdmin", "admin/manageProject/manageProjectView.jsp");
		forward = new ActionForward("adminTemplate.jsp", false);
				
		return forward;
	}

}
