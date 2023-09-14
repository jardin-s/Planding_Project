package action.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import vo.ActionForward;

public class InsertProjectRewardAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession();
		
		String[] rewardNames=request.getParameterValues("rewardName");
		String[] r_contents=request.getParameterValues("r_content");
		String[] r_prices=request.getParameterValues("r_price");
		
		
		for(int i=0;i<rewardNames.length;i++) {
			session.setAttribute(("rewardName"+(i+1)), rewardNames[i]);
			session.setAttribute(("r_content"+(i+1)), r_contents[i]);
			session.setAttribute(("r_price"+(i+1)), r_prices[i]);
//			System.out.println(rewardNames[i]);
//			System.out.println(r_contents[i]);
//			System.out.println(r_prices[i]);
		}
		

		
		request.setAttribute("showPage", "project/projectViewSessionTemp.jsp");
		forward=new ActionForward("userTemplate.jsp", false);
		
		
		return forward;
	}

}