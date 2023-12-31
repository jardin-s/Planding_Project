package action.project.insert;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import vo.ActionForward;
import vo.PlannerBean;

public class InsertProjectPlannerAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		/* [이주헌]
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
		*/
		
		//[손정원] - 위의 소스를 수정함 (각 값을 따로따로 저장하지 않고, PlannerBean객체를 저장)
		//기획자 이름, 기획자 소개글, 은행, 계좌번호 파라미터값 가져옴
		String member_id = request.getParameter("member_id");
		String planner_name = request.getParameter("planner_name");
		String introduce = request.getParameter("introduce").trim();
		String bank = (request.getParameter("bank") != null) ? request.getParameter("bank") : ((request.getParameter("otherBankName") != null) ? request.getParameter("otherBankName") : null);				
		String account_num = request.getParameter("account_num");
		
		System.out.println("[InsertProjectPlannerAction] 파라미터값");
		System.out.println("member_id = "+ member_id);
		System.out.println("planner_name = "+ planner_name);
		System.out.println("introduce = "+ introduce);
		System.out.println("bank = "+ bank);
		System.out.println("account_num = "+ account_num);
		
		//파라미터값으로 PlannerBean객체 생성
		PlannerBean plannerInfo = new PlannerBean(member_id, planner_name, introduce, bank, account_num);
		
		//세션에 planner이름으로 PlannerBean객체 저장
		HttpSession session = request.getSession();
		session.setAttribute("plannerInfo", plannerInfo);
		//이전단계에서, 은행이름 직접입력시, 해당 값을 넣어야 하므로
		if(request.getParameter("otherBankName") != null) {
			session.setAttribute("otherBankName", request.getParameter("otherBankName"));
		};
		
		
		
		/* <여기까지 세션에 저장된 값>
		 * kind(프로젝트 종류)
		 * member_id(기획자ID), planner_name(기획자 이름), introduce(기획자 소개글), bank(기획자 은행), account(기획자 계좌번호)
		 *  */
		
		/*--- [손정원] 프로젝트 시작일 설정 시, 시작일을 내일부터 설정가능하도록 minDate 설정 ---*/
		Calendar mindateCal = Calendar.getInstance();
		SimpleDateFormat dd_format = new SimpleDateFormat("dd");
		int min_startday = Integer.parseInt(dd_format.format(mindateCal.getTime())) + 1;//오늘이 아닌 내일로 세팅
		int min_endday = Integer.parseInt(dd_format.format(mindateCal.getTime())) + 2;//모레로 세팅 (종료일과 시작일 최소 하루 차이는 나도록)
		
		SimpleDateFormat yyyymm_format = new SimpleDateFormat("yyyy-MM");
		String minStartdate = yyyymm_format.format(mindateCal.getTime()) + "-"+ min_startday;
		String minEnddate = yyyymm_format.format(mindateCal.getTime()) + "-"+ min_endday;
		
		System.out.println("[InsertProjectPlannerAction] 최소시작일 = "+ minStartdate);
		System.out.println("[InsertProjectPlannerAction] 최소종료일 = "+ minEnddate);
		
		//최소시작일(내일날짜) request속성값으로 저장
		request.setAttribute("minStartdate", minStartdate);
		request.setAttribute("minEnddate", minEnddate);
		
		//[손정원]
		//hidden타입으로 넘어온 kind값을 가져옴		
		String kind = request.getParameter("kind");
		System.out.println("[InsertProjectPlannerAction] 파라미터로 넘어온 kind = "+kind);
		request.setAttribute("kind", kind);

		//프로젝트 내용입력 폼으로 이동
		request.setAttribute("showPage", "project/insert/insertProjectContentForm.jsp");
		forward=new ActionForward("userTemplate.jsp", false);
		
		
		return forward;
	}

}
