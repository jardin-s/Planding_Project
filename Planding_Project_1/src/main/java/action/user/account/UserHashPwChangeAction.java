package action.user.account;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.account.UserHashPwChangeService;
import vo.ActionForward;
import vo.MemberPwChangeBean;

public class UserHashPwChangeAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		//방법-1 : 파라미터로 id값 얻어오기
		//String u_id = request.getParameter("u_id");
		
		//방법-2 : Session에서 id값 가져오기
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
				
		String pre_password = request.getParameter("pre_password");
		String new_password = request.getParameter("new_password");
		//MemberBean에는 비밀번호가 1개만 있으므로 VO를 새로 생성
		
		//파라미터로 넘어온 값들을 확인
		System.out.println("[UserHashPwChangeAction]");
		System.out.println("u_id : "+u_id);
		System.out.println("pre_password : "+pre_password);
		System.out.println("new_password : "+new_password);
		
		//비밀번호 암호화된 객체
		MemberPwChangeBean memberPwChangeBean = new MemberPwChangeBean(u_id, pre_password, new_password);
		
		UserHashPwChangeService userHashPwChangeService = new UserHashPwChangeService();
		
		boolean isChangeHashPwSuccess = userHashPwChangeService.changeHashPw(memberPwChangeBean);
		if(!isChangeHashPwSuccess) { //비밀번호 재설정에 실패할 경우
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호 변경에 실패했습니다. 다시 시도해주세요.');");
			out.println("history.back();");
			out.println("</script>");
		
		}else { //비밀번호 재설정에 성공할 경우
			
			//방법-1
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호 변경에 성공했습니다.');");
			out.println("location.href='userMain.usr';");
			out.println("</script>");
			
			//방법-2
			//forward = new ActionForward("userMain.usr", true);
		}
		
		return forward;
	}

}
