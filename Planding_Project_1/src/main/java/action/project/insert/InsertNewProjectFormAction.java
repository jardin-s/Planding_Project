package action.project.insert;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import vo.ActionForward;

public class InsertNewProjectFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		/** 프로젝트 등록 과정 **********************************************
		 * 1. 프로젝트 등록하기 클릭 -> 기부 프로젝트/펀딩 프로젝트 선택
		 * 2. 기부 프로젝트 -> kind값 donate설정 후 insertProjectPlannerForm.jsp로 이동
		 *    펀딩 프로젝트 -> kind값 fund설정 후 insertProjectPlannerForm.jsp로 이동
		 * 3. insertProjectPlanner.jsp에서 프로젝트 기획자 정보 입력 후 insertProjectPlanner.pj 요청
		 *    프로젝트 기획자를 테이블에 저장하지 않고(프로젝트id 필요) 세션에 저장 후 다음단계로(insertProjectContentForm.jsp)
		 * 4. 프로젝트명, 프로젝트요약, 썸네일 이미지, 프로젝트 내용이미지, 프로젝트 내용,
		 *    시작예정일, 종료일, 목표 모금액 설정 후 insertProjectTemp.pj 요청
		 *    insertProjectAction에서 프로젝트 내용으로 project_tbl에 프로젝트 insert
		 *    프로젝트id를 가지고 와 프로젝트 기획자 내용으로 project_planner_tbl에 기획자 insert
		 * 5. 성공 시, 기부프로젝트는 미리보기 페이지로 이동, 펀딩프로젝트는 미리보기+리워드추가 페이지로 이동
		 * 6. 기부프로젝트 : 미리보기 페이지에서 제출하기 클릭 시, submitProject.pj 요청
		 *    펀딩프로젝트 : 
		 */
				
		
		
		HttpSession session = request.getSession();
		
		String u_id = (String) session.getAttribute("u_id");
		
		if(u_id == null) {//로그인이 안 된 상태라면
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인이 필요한 서비스입니다.');");
			out.println("location.href='userLoginForm.usr';");
			out.println("</script>");
		}else {
			
			session.removeAttribute("plannerInfo");
    		session.removeAttribute("projectInfo");
    		session.removeAttribute("otherBankName");
    		session.removeAttribute("r_name");
    		session.removeAttribute("r_contents");
    		session.removeAttribute("r_price");
    		session.removeAttribute("rewardInfo");
    		session.removeAttribute("rewardList");
			
			//이때, 기부 프로젝트 클릭 시, session에 kind값으로 donate가 저장,
			//펀딩 프로젝트 클릭 시, session에 kind값으로 fund가 저장된 상태로 다음 페이지로 이동
			request.setAttribute("showPage", "project/insert/insertNewProject.jsp");//어느 폼 보기인지 showPage이름 속성으로 저장
			forward = new ActionForward("userTemplate.jsp",false);//반드시 디스패치 (request를 공유)
			
		}
		
		return forward;
	}

}
