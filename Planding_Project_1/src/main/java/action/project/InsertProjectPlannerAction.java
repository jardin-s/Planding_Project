package action.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import vo.ActionForward;

public class InsertProjectPlannerAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		
		//기획자 이름, 기획자 소개글을 session에 저장
		session.setAttribute("planner_name", request.getParameter("planner_name"));
		session.setAttribute("introduce", request.getParameter("introduce").trim());
		
		//은행값 session에 저장
		if(request.getParameter("bank") != null) {//만약 은행값이 선택되어있다면
			
			session.setAttribute("bank", request.getParameter("bank"));//선택한 은행으로 bank값 세션에 저장
			
		}else if(request.getParameter("otherBankName") != null) {//만약 select옵션 은행이 아니라 직접 입력한 은행이라면 
			
			session.setAttribute("otherBankName", request.getParameter("otherBankName"));//입력한 은행으로 bank값 세션에 저장
			
		}
		
		//계좌번호값 session에 저장
		session.setAttribute("account", request.getParameter("account"));
		
		/* <여기까지 세션에 저장된 값>
		 * kind(프로젝트 종류)
		 * planner_name(기획자 이름), introduce(기획자 소개글), bank(기획자 은행), account(기획자 계좌번호)
		 *  */
		
		

		//프로젝트 내용입력 폼으로 이동
		request.setAttribute("showPage", "project/insertProjectContentForm.jsp");
		forward=new ActionForward("userTemplate.jsp", false);
		
		
		return forward;
	}

}
