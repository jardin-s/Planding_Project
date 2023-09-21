package action.project.manage;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.project.InsertProjectService;
import vo.ActionForward;
import vo.RewardBean;

public class EditRewardAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		//얻어온 새로운 reward값들
		String reward_id = request.getParameter("reward_id");
		String r_name = request.getParameter("r_name");
		String r_content = request.getParameter("r_content");
		int r_price = Integer.parseInt(request.getParameter("r_price"));
		int project_id = Integer.parseInt(request.getParameter("project_id"));
		
		//파라미터값 확인
		System.out.println("[EditRewardAction] 파라미터 값 확인");
		System.out.println("reward_id = "+reward_id);
		System.out.println("r_name = "+r_name);
		System.out.println("r_content = "+r_content);
		System.out.println("r_price = "+r_price);
		
		
		//리워드 객체에 저장
		RewardBean rewardInfo = new RewardBean(reward_id,r_name,r_content,r_price);
		
		InsertProjectService insertProjectService = new InsertProjectService();
        
		boolean editReward = insertProjectService.editReward(rewardInfo);
        
        

		if(!editReward) {
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('리워드 수정에 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			//업데이트 성공시 수정 할 리워드 고르는 페이지로 돌아감
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('리워드 수정에 성공했습니다.');");
			out.println("</script>");
			forward = new ActionForward("editProjectRewardList.pj?project_id="+project_id,true);
		}
			
		
        return forward;
	}

}
