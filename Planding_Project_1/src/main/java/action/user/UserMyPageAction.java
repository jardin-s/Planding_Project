package action.user;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.UserMyPageService;
import vo.ActionForward;
import vo.MemberBean;

public class UserMyPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		
		String u_id = (String) session.getAttribute("u_id");
		
		if(u_id == null) {//세션 만료
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 후 이용가능한 서비스입니다.');");
			out.println("location.href='userLogin.usr'");
			out.println("</script>");
		}else{
			
			//사용자의 정보를 가져옴
			UserMyPageService userMyPageService = new UserMyPageService();
			MemberBean userInfo = userMyPageService.getUserInfo(u_id);
			
			//사용자 현재 잔액 표시
			request.setAttribute("userMoney", userInfo.getMoney());
			
			request.setAttribute("showPage", "user/myPage/userMyPage.jsp");
			forward = new ActionForward("userTemplate.jsp", false);
			
		}
		
		return forward;
	}

}
