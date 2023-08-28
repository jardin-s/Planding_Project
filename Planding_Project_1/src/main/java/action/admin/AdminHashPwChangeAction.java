package action.admin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.admin.AdminHashPwChangeService;
import vo.ActionForward;
import vo.MemberPwChangeBean;

public class AdminHashPwChangeAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		//방법-1 : 파라미터로 id값 얻어오기
		//String a_id = request.getParameter("a_id");
		
		//방법-2 : Session에서 id값 가져오기
		HttpSession session = request.getSession();
		String a_id = (String) session.getAttribute("a_id");
				
		String pre_password = request.getParameter("pre_password");
		String new_password = request.getParameter("new_password");
		//MemberBean에는 비밀번호가 1개만 있으므로 VO를 새로 생성
		
		//파라미터로 넘어온 값들을 확인
		System.out.println("[AdminHashPwChangeAction]");
		System.out.println("a_id : "+a_id);
		System.out.println("pre_password : "+pre_password);
		System.out.println("new_password : "+new_password);
		
		//비밀번호 암호화된 객체
		MemberPwChangeBean memberPwChangeBean = new MemberPwChangeBean(a_id, pre_password, new_password);
		
		AdminHashPwChangeService adminHashPwChangeService = new AdminHashPwChangeService();
		
		boolean isChangeHashPwSuccess = adminHashPwChangeService.changeHashPw(memberPwChangeBean);
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
			out.println("location.href='adminMain.adm';");
			out.println("</script>");
			
			//방법-2
			//forward = new ActionForward("adminMain.adm", true);
		}
		
		return forward;
	}

}
