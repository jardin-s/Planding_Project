package action.admin.manageMember;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.admin.manageMember.DeleteMemberService;
import vo.ActionForward;

public class DeleteMemberListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("a_id") == null) {//로그인 풀린상태라면
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('세션이 만료되었습니다. 메인으로 돌아갑니다.');");
			out.println("location.href='userMain.usr';");
			out.println("</script>");
		}else {
			
			String[] memberArr = request.getParameterValues("remove");
			
			DeleteMemberService deleteMemberService = new DeleteMemberService();
			boolean isDeleteMemberListSuccess = deleteMemberService.deleteMemberList(memberArr);
			
			if(!isDeleteMemberListSuccess) {
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('회원 삭제가 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}else {
				//회원 삭제 후 탈퇴회원 목록보기
				forward = new ActionForward("deletedMemberList.mngm",true);
			}
			
		}	
		
		
		return forward;
	}

}
