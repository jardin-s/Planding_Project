package action.admin.manageProject;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.admin.manageProject.SendTotalAmountService;
import util.SendMail;
import vo.ActionForward;
import vo.MemberBean;
import vo.ProjectPlannerBean;

public class SendTotalAmountAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		/* <성공한 프로젝트 모금액 송금하기>
		 * 1. 현재모금액을 가져와 수수료 5%를 떼서 관리자 수익테이블에 insert
		 * 2. 남은 금액을 프로젝트 기획자 계좌로 송금
		 */
		
		int project_id = Integer.parseInt(request.getParameter("project_id"));
		
		SendTotalAmountService sendTotalAmountService = new SendTotalAmountService();
		
		//프로젝트-기획자 정보를 가져옴
		ProjectPlannerBean projectPlanner = sendTotalAmountService.getProjectPlannerInfo(project_id);
		
		//기획자 회원정보를 가져옴
		MemberBean plannerInfo = sendTotalAmountService.getPlannerInfo(projectPlanner.getMember_id());
		
		//수수료 수익 계산
		int fee_income = (int)((double) projectPlanner.getCurr_amount() * 5/100);
		int finalAmount = projectPlanner.getCurr_amount() - fee_income;
		
		boolean isInsertFeeIncomeSuccess = sendTotalAmountService.insertFeeIncome(project_id, fee_income);
		
		if(!isInsertFeeIncomeSuccess) {
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수수료 수익 계산에 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			
			boolean isSendAmountPlannerSuccess = sendTotalAmountService.sendAmountPlanner(projectPlanner.getMember_id(), finalAmount);
			
			if(!isSendAmountPlannerSuccess) {
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('모금액 송금에 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}else {
				
				//모금액 전달 안내 메일을 보냄
				SendMail mail = new SendMail();
				mail.setSendDonationAmountMsg(projectPlanner, fee_income);
				boolean isMailSendSuccess = mail.sendDonationAmount(plannerInfo);
				
				if(!isMailSendSuccess) System.out.println("모금액 전달 완료 안내메일 전송에 실패했습니다.");
				else System.out.println("모금액 전달 완료 안내메일 전송에 성공했습니다.");
				
				//프로젝트 종류에 따라 포워딩 달라짐
				if(projectPlanner.getKind().equalsIgnoreCase("donate")) {
					
					forward = new ActionForward("manageDonateProjectList.mngp", true);
					
				}else if(projectPlanner.getKind().equalsIgnoreCase("fund")) {
					
					forward = new ActionForward("manageFundProjectList.mngp", true);
					
				}			
				
			}
		}
		
		
		return forward;
	}

}
