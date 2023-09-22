package action.user;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.UserTopupMoneyService;
import vo.ActionForward;

public class UserTopupMoneyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		//파라미터 값을 가져옴
		String topUpMoney_str = request.getParameter("topUpMoney");
		if(topUpMoney_str.equals("on")) {//직접입력 선택 시
			topUpMoney_str = request.getParameter("topUpMoneyText");//입력한 숫자금액을 가져와 대입
		}
		
		//int로 형변환
		int topUpMoney = Integer.parseInt(topUpMoney_str);
		
		String member_id = request.getParameter("member_id");
		
		System.out.println("[UserTopupMoneyAction] 파라미터 값 확인");
		System.out.println("member_id = "+member_id);
		System.out.println("topUpMoney = "+topUpMoney);
		
		
		//사용자 계좌에 금액을 충전
		UserTopupMoneyService userTopupMoneyService = new UserTopupMoneyService();
		boolean isTopUpMoneySuccess = userTopupMoneyService.topUpMoney(member_id, topUpMoney);
		
		if(!isTopUpMoneySuccess) {//충전 실패 시
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('금액 충전이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {//충전 성공 시			
			//충전된 현재 금액을 들고 옴
			int userMoney = userTopupMoneyService.getUserMoney(member_id);
			
			//request속성값으로 저장
			request.setAttribute("finalTotalMoney", userMoney);
						
			//다시 금액충전 팝업창으로 포워딩
			forward = new ActionForward("topUp.jsp", false);
		}
		
		return forward;
	}

}
