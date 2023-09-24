package action.project.manage;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.project.ProjectPageViewService;
import vo.ActionForward;
import vo.PlannerBean;
import vo.ProjectBean;

public class EditProjectFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
				
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		
		if(u_id == null) {//로그인이 풀린 상태라면
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('세션이 만료되었습니다. 로그인 후 다시 시도해주세요.');");
			out.println("location.href='userLoginform.usr';");
			out.println("</script>");
		
		}else {
			
			//파라미터 값
			int project_id = Integer.parseInt(request.getParameter("project_id"));
			
			ProjectPageViewService projectPageViewService = new ProjectPageViewService();
			
			//프로젝트 정보를 얻어옴
			ProjectBean projectInfo = projectPageViewService.getProjectInfoDate(project_id);
			
			/*--- 프로젝트 시작일 설정 시, 시작일을 내일부터 설정가능하도록 minDate 설정 ---*/
			Calendar mindateCal = Calendar.getInstance();
			SimpleDateFormat dd_format = new SimpleDateFormat("dd");
			int min_startday = Integer.parseInt(dd_format.format(mindateCal.getTime()));//오늘로 세팅
			int min_endday = Integer.parseInt(dd_format.format(mindateCal.getTime()))+1;//내일로 세팅 (종료일과 시작일 최소 하루 차이는 나도록)
			
			SimpleDateFormat yyyymm_format = new SimpleDateFormat("yyyy-MM");
			String minStartdate = yyyymm_format.format(mindateCal.getTime()) + "-"+ min_startday;
			String minEnddate = yyyymm_format.format(mindateCal.getTime()) + "-"+ min_endday;
			
			System.out.println("[EditProjectFormAction] 최소시작일 = "+ minStartdate);
			System.out.println("[EditProjectFormAction] 최소종료일 = "+ minEnddate);
			request.setAttribute("minStartdate", minStartdate);
			request.setAttribute("minEnddate", minEnddate);
			
			//지정한 시작일과 종료일로 세팅되도록 설정 (yyyy-MM-dd 형식으로 세팅)
			SimpleDateFormat date_org = new SimpleDateFormat("yyyy.MM.dd");
			SimpleDateFormat date_chng = new SimpleDateFormat("yyyy-MM-dd");
			
			projectInfo.setStartdate(date_chng.format(date_org.parse(projectInfo.getStartdate())));
			projectInfo.setEnddate(date_chng.format(date_org.parse(projectInfo.getEnddate())));
			
			//목표 모금액 천단위 구분자 세팅
			projectInfo.setGoal_amount_df_exc(projectInfo.getGoal_amount());
			
			//기존 이미지 수를 카운팅
			String[] images = projectInfo.getImage().split(";");
			request.setAttribute("originImgCount", images.length);
			
			//request에 프로젝트 정보저장
			request.setAttribute("projectInfo", projectInfo);
			
			//플래너 정보를 얻어옴
			PlannerBean plannerInfo = projectPageViewService.getPlannerInfo(project_id);
			request.setAttribute("plannerInfo", plannerInfo);
			
			//EditProjectAction에서 사용하기 위해 session에 project_id 저장
			session.setAttribute("project_id", project_id);
			
			request.setAttribute("showPage", "project/manage/editProjectForm.jsp");
			forward = new ActionForward("userTemplate.jsp", false);
			
			
		}
		
		return forward;
	}

}
