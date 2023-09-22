package action.project.manage;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
		
		String contentImg = projectInfo.getImage();
		String[] contentImgs = contentImg.split(";");
		for(int i=0;i<contentImgs.length;i++) {
		System.out.println("contentImgs 이미지"+i+" 확인: " + contentImgs[i]);
		}
		int imgCount = contentImgs.length;
		
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
		    e.printStackTrace();
		    throw e;
		}
		
		request.setAttribute("contentImgs", contentImgs);
		request.setAttribute("imgCount", imgCount);
		request.setAttribute("projectInfo", projectInfo);
		request.setAttribute("plannerInfo", plannerInfo);
		
		
		
		request.setAttribute("showPage", "project/manage/editProjectForm.jsp");
		forward = new ActionForward("userTemplate.jsp", false);
		
		return forward;
	}

}
