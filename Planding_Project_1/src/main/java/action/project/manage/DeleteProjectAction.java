package action.project.manage;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.project.DeleteRewardService;
import svc.project.EditProjectService;
import svc.project.ProjectPageViewService;
import vo.ActionForward;

public class DeleteProjectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		
		
		int project_id = Integer.parseInt(request.getParameter("project_id"));
		request.setAttribute("project_id", project_id);
		
		String member_id = (String)session.getAttribute("u_id");
		if(member_id == null) {
			member_id = request.getParameter("member_id");
		}
		
		//파라미터값 확인
		System.out.println("[DeleteProjectAction] 파라미터 값 확인");
		System.out.println("project_id = "+project_id);
		
		//리워드 정보 가져오기
		ProjectPageViewService projectPageViewService = new ProjectPageViewService();
		EditProjectService editProjectService = new EditProjectService();
		DeleteRewardService deleteRewardService = new DeleteRewardService();
		
		//리워드 아이디 리스트
		ArrayList<String> reward_id_list = 	projectPageViewService.getRewardIdList(project_id);
		
		if(reward_id_list != null) {//리워드가 있다면
		
			//donation_tbl에 후원기록이 있을 경우 리워드를 삭제할 수 없음
			//donation_tbl에서 검색하기
			int donationCount = 0;
			boolean isDonation = false;
			
			for (String reward_id : reward_id_list) {
				
				donationCount = deleteRewardService.seleteDonation_Reward(reward_id);
				
				if(donationCount > 0) {
					isDonation = true; break;//후원목록이 하나라도 있을 경우 삭제 실패하도록
				}
			}
		
			if(isDonation) {//후원기록이 있다면
				
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('후원기록이 있는 프로젝트는 삭제할 수 없습니다.');");
				out.println("history.back();");
				out.println("</script>");
			
			}else {
				
				//후원기록이 없을 때 프로젝트-리워드 맵핑 삭제
				boolean deleteMapReward = false;
				
				for (String reward_id : reward_id_list) {
					
					deleteMapReward = deleteRewardService.deleteMapReward(project_id, reward_id);
					
					if(!deleteMapReward) {//프로젝트-리워드 맵핑 삭제 실패
						System.out.println("[DeleteProjectAction] deleteMapReward error : 프로젝트-리워드 맵핑 삭제 실패");
						System.out.println("project_id : "+project_id+"reward_id : "+reward_id);
						
						response.setContentType("text/html; charset=utf-8");
						
						PrintWriter out = response.getWriter();
						out.println("<script>");
						out.println("alert('프로젝트 삭제에 실패했습니다.');");
						out.println("history.back();");
						out.println("</script>");
						break;
					}
				}
				
				//매핑 삭제 이후 리워드 삭제
				for (String reward_id : reward_id_list) {
					deleteMapReward=deleteRewardService.deleteReward(reward_id);
					if(!deleteMapReward) {
						System.out.println("[DeleteProjectAction] deleteReward error : 리워드 "+reward_id+" 삭제 실패");
						System.out.println("project_id : "+project_id+"reward_id : "+reward_id);
						
						response.setContentType("text/html; charset=utf-8");
						
						PrintWriter out = response.getWriter();
						out.println("<script>");
						out.println("alert('리워드 삭제에 실패했습니다.');");
						out.println("history.back();");
						out.println("</script>");
						break;					
					}
						
					
				}
			}
		
		}
		
		
		
		//리워드 삭제 성공 시 플래너 삭제
		boolean isDeletePlanner = editProjectService.deletePlanner(project_id, member_id);
		if(!isDeletePlanner) {
			System.out.println("[DeleteProjectAction] deletePlanner error : Planner 삭제 실패");
			
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('플래너 삭제에 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		
		}else {//플래너 삭제 후 프로젝트 삭제
			
			boolean isDeleteProject = editProjectService.deleteProject(project_id);
			if(!isDeleteProject) {
				System.out.println("[DeleteProjectAction] deletePlanner error : Project 삭제 실패");
				
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('프로젝트 삭제에 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			
			}else {//최종 프로젝트 삭제 완료
				
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('프로젝트 삭제에 성공하였습니다.');");
				out.println("window.location.href = 'userUploadProjectList.usr'");
				out.println("</script>");
			}
		}
		
		return forward;
	}

}
