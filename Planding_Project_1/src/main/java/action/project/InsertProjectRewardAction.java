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
		String[] r_price=new String[r_prices.length];
		for(int i=0;i<r_prices.length;i++) {
			r_price[i]=r_prices[i].replace(",", "");
		}
		
		
			session.setAttribute("rewardName", rewardNames);
			session.setAttribute("r_content", r_contents);
			session.setAttribute("r_price", r_price);

		

		
		request.setAttribute("showPage", "project/projectViewSessionTemp.jsp");
		forward=new ActionForward("userTemplate.jsp", false);
		
		
		return forward;
	}

}