package action.admin.manageMember;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.admin.manageMember.DeleteMemberService;
import vo.ActionForward;

public class DeleteMemberAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String[] memberArr = request.getParameterValues("remove");
		
		DeleteMemberService deleteMemberService = new DeleteMemberService();
		boolean isDeleteMemberSuccess = deleteMemberService.deleteMember(memberArr);
		
		if(!isDeleteMemberSuccess) {
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원 삭제가 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			//회원 삭제 후, 다시 회원관리 페이지를 로딩
			forward = new ActionForward("manageMemberList.mngm",true);
		}
		
		return forward;
	}

}
