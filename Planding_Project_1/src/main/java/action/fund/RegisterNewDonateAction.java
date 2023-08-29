package action.fund;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.fund.FundRegisterDonateProjectService;
import vo.ActionForward;

public class RegisterNewDonateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String kind = request.getParameter("kind");//donate
		
		String title = request.getParameter("title");
		String summary = request.getParameter("summary");
		String content = request.getParameter("content");
		String thumbnail = request.getParameter("thumbnail");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		int goal_amount = Integer.parseInt(request.getParameter("goal_amount"));
		int curr_amount = Integer.parseInt(request.getParameter("curr_amount"));
		String category = request.getParameter("category");
		//String status = "ready"; //default값
		//int likes = 0; //default값
		
		FundRegisterDonateProjectService fundRegisterDonateProjectService = new FundRegisterDonateProjectService();
		
		
		
		return forward;
	}

}
