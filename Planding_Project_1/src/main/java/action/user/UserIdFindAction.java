package action.user;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.user.UserIdFindService;
import vo.ActionForward;
import vo.MemberBean;

public class UserIdFindAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String email = request.getParameter("email");
		System.out.println("action에서 받은 email값 : "+email);
		
		UserIdFindService userIdFindService = new UserIdFindService();
		MemberBean userInfo = userIdFindService.findId(email);
		
		System.out.println("service에서 돌아옴");
		
		if(userInfo == null) { //해당 email을 가지는 회원이 없다면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('존재하지 않는 이메일주소입니다. 다시 시도해주세요.');");
			out.println("history.back();");
			out.println("</script>");
		
		}else { //해당 email을 가지는 회원이 있다면
			String u_id = userInfo.getMember_id();
			request.setAttribute("u_id", u_id);
			
			System.out.println("받아온 id 값 : "+u_id);
			
			request.setAttribute("showPage", "user/account/findIdComplete.jsp");//'회원님의 아이디는~입니다' 보여줄 페이지로 이동
			forward = new ActionForward("userTemplate.jsp",false);//request에 id값을 저장하였으므로 디스패치 포워딩
			
		}
		
		return forward;
	}

}

