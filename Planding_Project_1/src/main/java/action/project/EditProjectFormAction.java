package action.project;

import java.io.File;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.project.ProjectPageViewService;
import vo.ActionForward;
import vo.PlannerBean;
import vo.ProjectBean;

public class EditProjectFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		int project_id = Integer.parseInt(request.getParameter("project_id"));
		ProjectPageViewService projectPageViewService = new ProjectPageViewService();
		ProjectBean projectInfo = projectPageViewService.getProjectInfo(project_id);
		PlannerBean plannerInfo = projectPageViewService.getPlannerInfo(project_id);
		

		
        String startDateStr = projectInfo.getStartdate();
        String endDateStr = projectInfo.getEnddate();	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        

		// 예외 처리를 추가
		try {
			java.sql.Date startDate = new java.sql.Date(sdf.parse(startDateStr).getTime());
			java.sql.Date endDate = new java.sql.Date(sdf.parse(endDateStr).getTime());

		    // 파싱한 날짜를 request에 설정
		    request.setAttribute("startDate", startDate);
		    request.setAttribute("endDate", endDate);
		} catch (ParseException e) {
		    // 예외 처리: 파싱 오류 발생 시, 오류 메시지를 로그에 기록하고 예외를 다시 던질 수 있습니다.
		    e.printStackTrace();
		    throw e; // 예외 다시 던지기 또는 다른 적절한 처리를 수행하세요.
		}

		request.setAttribute("projectInfo", projectInfo);
		request.setAttribute("plannerInfo", plannerInfo);
		
		
		
		request.setAttribute("showPage", "project/editProjectForm.jsp");
		forward = new ActionForward("userTemplate.jsp", false);
		
		return forward;
	}

}