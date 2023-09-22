package action.project.manage;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.project.EditProjectService;
import vo.ActionForward;
import vo.PlannerBean;
import vo.ProjectBean;

/** 미완 */
public class EditProjectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;

		int project_id = Integer.parseInt(request.getParameter("project_id"));
		String member_id = request.getParameter("member_id");
		String planner_name = request.getParameter("planner_name");
		String introduce = request.getParameter("introduce");
		String bank="";
		if(request.getParameter("bank") != null) {
			bank= request.getParameter("bank");
			}else {
				bank= request.getParameter("otherBankName");
			}
		String account = request.getParameter("account");
		String title = request.getParameter("title");
		String summary = request.getParameter("summary");
		String content = request.getParameter("content");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		int goal_amount = Integer.parseInt(request.getParameter("goal_amount").replace(",", ""));
		
		//파라미터값 확인
		System.out.println("[EditProjectAction] 파라미터 값 확인");
		System.out.println("member_id : "+ member_id);
		System.out.println("project_id : " + project_id);
		System.out.println("planner_name : " + planner_name);
		System.out.println("introduce : " + introduce);
		System.out.println("bank : " + bank);
		System.out.println("account : " + account);
		System.out.println("title : " + title);
		System.out.println("summary : " + summary);
		System.out.println("content : " + content);
		System.out.println("startdate : " + startdate);
		System.out.println("enddate : " + enddate);
		System.out.println("goal_amount : " + goal_amount);
		
		//프로젝트, 플래너 객체에 저장

		ProjectBean pj = new ProjectBean();
		pj.setProject_id(project_id);
		pj.setTitle(title);
		pj.setSummary(summary);
		pj.setContent(content);
		pj.setStartdate(startdate);
		pj.setEnddate(enddate);
		pj.setGoal_amount(goal_amount);
		PlannerBean planner = new PlannerBean();
		planner.setProject_id(project_id);
		planner.setPlanner_name(planner_name);
		planner.setMember_id(member_id);
		planner.setIntroduce(introduce);
		planner.setBank(bank);
		planner.setAccount_num(account);

		request.setAttribute("pj", pj);
		request.setAttribute("planner", planner);
		
		EditProjectService editProjectService = new EditProjectService();
		boolean editProject=editProjectService.editProject(pj);
		boolean editPlanner=editProjectService.editPlanner(planner);
		System.out.println("planner.getPlanner_name()"+planner.getPlanner_name());
		
		
		
		if(editProject && editPlanner) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('프로젝트 수정에 성공하였습니다.');");
			out.println("window.location.href = 'userUploadProjectList.usr'");
			out.println("</script>");

		
		}else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('프로젝트 수정에 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}
		
        return forward;
	}

}
