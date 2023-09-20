package action.project;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import vo.ActionForward;

public class DeleteTempProjectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		
		//세션에 담아뒀던 임시저장용 정보들을 모두 삭제
		session.removeAttribute("plannerInfo");
		session.removeAttribute("otherBankName");
		session.removeAttribute("projectInfo");
		session.removeAttribute("rewardInfo");
		session.removeAttribute("r_name");
		session.removeAttribute("r_content");
		session.removeAttribute("r_price");
		session.removeAttribute("rewardList");
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.println("<script>");
		out.println("alert('등록이 취소되었습니다.');");
		out.println("location.href='userMain.usr';");
		out.println("</script>");
		
		return forward;
	}

}
