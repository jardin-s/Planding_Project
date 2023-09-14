package action.project;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.project.GetProjectIDService;
import svc.project.InsertPlannerSevice;
import svc.project.InsertProjectService;
import vo.ActionForward;
import vo.MemberBean;
import vo.PlannerBean;
import vo.ProjectBean;

public class InsertProjectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		
		String kind=(String)session.getAttribute("kind");
		String title=(String)session.getAttribute("title");
		String summary=(String)session.getAttribute("summary");
		String thumbnail=(String)session.getAttribute("thumbnail");
		String content=(String)session.getAttribute("content");
		String image=(String)session.getAttribute("contentImgSysNames");
		String startdate=(String)session.getAttribute("startdate");
		String enddate=(String)session.getAttribute("enddate");
		int goal_amount=Integer.parseInt((String)session.getAttribute("goal_amount"));
		
		ProjectBean pj = new ProjectBean();
		pj.setKind(kind);
		pj.setTitle(title);
		pj.setSummary(summary);
		pj.setThumbnail(thumbnail);
		pj.setContent(content);
		pj.setImage(image);
		pj.setStartdate(startdate);
		pj.setEnddate(enddate);
		pj.setGoal_amount(goal_amount);
		
		InsertProjectService insertProjectService=new InsertProjectService();
		boolean isInsertProjectSuccess = insertProjectService.insertProjectService(pj);
		
		
		if(!isInsertProjectSuccess) { //실패
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('프로젝트 등록에 실패하였습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else { //성공-플래너 등록
			GetProjectIDService getProjectIDService=new GetProjectIDService();
			int project_id=getProjectIDService.getProjectID(pj);
			if(project_id<=0) {
				response.setContentType("text/html; charset=UTF-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('프로젝트 등록에 실패하였습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}else {
				String member_id="";
				if((String)session.getAttribute("u_id")!=null) {
					member_id=(String)session.getAttribute("u_id");
				}else {
					
				}
				String planner_name=(String)session.getAttribute("planner_name");
				String introduce=(String)session.getAttribute("introduce");
				String bank=(String)session.getAttribute("bank");
				String account=(String)session.getAttribute("account");
	
				
				PlannerBean planner = new PlannerBean();
				planner.setProject_id(project_id);
				planner.setMember_id(member_id);
				planner.setPlanner_name(planner_name);
				planner.setIntroduce(introduce);
				planner.setBank(bank);
				planner.setAccount(account);
				
				InsertPlannerSevice insertPlannerSevice=new InsertPlannerSevice();
				boolean isInsertPlannerSuccess=insertPlannerSevice.insertPlanner(planner);
				
				if(!isInsertPlannerSuccess) {
					response.setContentType("text/html; charset=UTF-8");
					
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('프로젝트 등록에 실패하였습니다.');");
					out.println("history.back();");
					out.println("</script>");
				}else {
					response.setContentType("text/html; charset=UTF-8");
					
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('저장되었습니다.');");
					out.println("window.location.href='projectView.pj';");
					out.println("</script>");
				}
			}
			
			
		}
		return forward;
	}

}
