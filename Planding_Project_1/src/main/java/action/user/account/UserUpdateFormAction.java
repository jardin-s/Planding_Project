package action.user.account;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.account.UserViewService;
import vo.ActionForward;
import vo.AddressBean;
import vo.MemberBean;

public class UserUpdateFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		HttpSession session =  request.getSession();
		String viewId = (String) session.getAttribute("u_id");
		
		//로그인된 상태에서만 회원정보관리를 볼 수 있음
		if(viewId == null) {//로그인X 상태 
			response.setContentType("text/html");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인이 필요한 서비스입니다.');");
			out.println("location.href='userLoginForm.usr'"); //로그인 폼 보기 요청
			out.println("</script>");
			
		}else {//로그인 상태
			UserViewService userViewService = new UserViewService();
			
			MemberBean userInfo = userViewService.getUserInfo(viewId);
			AddressBean userAddrInfo = userViewService.getUserAddrInfo(viewId);
						
			request.setAttribute("user", userInfo);
			request.setAttribute("addr", userAddrInfo);
			
			
			
			request.setAttribute("showPage", "user/account/userUpdateForm.jsp");//어느 폼 보기인지 showPage이름 속성으로 저장
			
			forward = new ActionForward("userTemplate.jsp",false);//반드시 디스패치 (request를 공유)
		}
				
		return forward;
	}

}
