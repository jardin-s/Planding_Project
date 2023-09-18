package action.project;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.project.ProjectPageViewService;
import vo.ActionForward;
import vo.PlannerBean;
import vo.ProjectBean;
import vo.RewardBean;

public class UserProjectViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		//파라미터값들
		int project_id = Integer.parseInt(request.getParameter("project_id"));
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}		
		
		System.out.println("[UserProjectViewAction] 파라미터값 확인");
		System.out.println("project_id = "+project_id);
		System.out.println("page = "+page);
		
		
		//ManageProjectViewService manageProjectViewService = new ManageProjectViewService();
		
		ProjectPageViewService projectPageViewService = new ProjectPageViewService();
		
		//프로젝트 정보를 얻어옴 (달성률 세팅된 상태)
		//ProjectBean projectInfo = projectPageViewService.getProjectInfo(project_id);
		ProjectBean projectInfo = projectPageViewService.getProjectInfoDate(project_id);
		System.out.println("[UserProjectViewAction] 시작일 : "+projectInfo.getStartdate().replace(".", "-"));
		System.out.println("[UserProjectViewAction] 시작일 : "+projectInfo.getEnddate().replace(".", "-"));
		
		//★★[사용자모드] 남은일수 계산해서 세팅
		LocalDate today = LocalDate.now();
		//공개예정, 미승인 : 오늘로부터 시작일까지 남은 일자를 계산
		if(projectInfo.getKind().equalsIgnoreCase("ready")||projectInfo.getKind().equalsIgnoreCase("unauthorized")) {
        	LocalDate startdate_date = LocalDate.parse(projectInfo.getStartdate().replace(".", "-"));
			long deadline = ChronoUnit.DAYS.between(today, startdate_date);//두 날짜 사이 일수차이를 구함
			
			System.out.println("[UserProjectViewAction] 오늘로부터 시작일까지 일 수 차이 : "+deadline);
	        projectInfo.setDeadline((int)deadline);
	        
        }else {//진행중 : 오늘로부터 종료일까지 남은 일자를 계산
        	LocalDate enddate_date = LocalDate.parse(projectInfo.getEnddate().replace(".", "-"));
        	long deadline = ChronoUnit.DAYS.between(today, enddate_date);//두 날짜 사이 일수차이를 구함
        	
        	System.out.println("[UserProjectViewAction] 오늘로부터 종료일까지 일 수 차이 : "+deadline);
            projectInfo.setDeadline((int)deadline);
        }
		
		        
        //달성률, 남은일수까지 다 계산된 상태에서 request에 저장
		request.setAttribute("projectInfo", projectInfo);
				
		//프로젝트 기획자 정보를 얻어옴
		PlannerBean plannerInfo = projectPageViewService.getPlannerInfo(project_id);
		request.setAttribute("plannerInfo", plannerInfo);
		
		ArrayList<RewardBean> rewardList = null;
		if(projectInfo.getKind().equals("kind")) {//펀딩 프로젝트라면 (기부 프로젝트는 아예 뷰페이지에서 기본리워드만 세팅해둠)
			
			//프로젝트-리워드 매핑 테이블에서 리워드ID리스트를 얻어옴
			ArrayList<String> rewardIdList = projectPageViewService.getRewardIdList(project_id);
			if(rewardIdList == null) {
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('리워드ID 불러오기 요청이 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}else {
				
				//리워드ID리스트로 리워드리스트를 얻어옴
				rewardList = projectPageViewService.getRewardList(rewardIdList);
				
			}
		}
		request.setAttribute("rewardList", rewardList);
		//projectInfo, plannerInfo, rewardList가 넘어온 상태
		
		//페이지넘버를 request속성으로 넘김
		request.setAttribute("page", page);
		
		request.setAttribute("showPage", "project/projectView.jsp");
		forward = new ActionForward("userTemplate.jsp", false);
				
		return forward;
		
	}

}
