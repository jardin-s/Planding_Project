package action.project;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.project.InsertProjectService;
import vo.ActionForward;
import vo.RewardBean;

public class EditProjectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;

		int project_id = Integer.parseInt(request.getParameter("project_id"));
		
		//파라미터값 확인
		System.out.println("[EditRewardAction] 파라미터 값 확인");

		
		
		//리워드 객체에 저장


        
        

			
		
        return forward;
    }
    
}
