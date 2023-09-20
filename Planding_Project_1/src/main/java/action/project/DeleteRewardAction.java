package action.project;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.project.DeleteRewardService;
import svc.project.InsertProjectService;
import vo.ActionForward;
import vo.RewardBean;

public class DeleteRewardAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		//얻어온 새로운 reward값들
		String reward_id = request.getParameter("reward_id");
		int project_id = Integer.parseInt(request.getParameter("project_id"));
		request.setAttribute("project_id", project_id);
		//파라미터값 확인
		System.out.println("[DeleteRewardAction] 파라미터 값 확인");
		System.out.println("reward_id = "+reward_id);
		System.out.println("project_id = "+project_id);
		
		

		
		DeleteRewardService deleteRewardService = new DeleteRewardService();
        
		//donation_tbl에 후원기록이 있을 경우 삭제할 수 없음
		//donation_tbl에서 검색하기
		int donationCount=deleteRewardService.seleteDonation_Reward(reward_id);
		if(donationCount!=0) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('후원기록이 있는 리워드는 삭제할 수 없습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			//프로젝트-리워드 맵핑 지우기
			boolean deleteMapReward=deleteRewardService.deleteMapReward(project_id, reward_id);
			if(!deleteMapReward) {
				System.out.println("deleteMapReward error : 프로젝트-리워드 맵핑 삭제 실패");
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('리워드 삭제에 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}else {
				//리워드 지우기
				boolean deleteReward=deleteRewardService.deleteReward(reward_id);	
				if(!deleteReward) {
					System.out.println("deleteReward error : 리워드 삭제 실패");
					response.sendRedirect(request.getHeader("referer"));
				}else {
					response.setContentType("text/html; charset=utf-8");
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('리워드 삭제에 성공했습니다.');");
					out.println("history.back();");
					out.println("</script>");	
					
					
					
					request.setAttribute("showPage", "project/selectEditReward.jsp");
					forward = new ActionForward("userTemplate.jsp", false);
				}//deleteReward
			}//deleteMapReward
		}//seleteDonation_Reward
		
		//if 그럴리는 없지만 잘못빠져나왔을때
		request.setAttribute("showPage", "project/selectEditReward.jsp");
		forward = new ActionForward("userTemplate.jsp", false);		
        return forward;
    }
    
}