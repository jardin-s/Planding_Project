package action.admin.manageProject;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.admin.manageProject.RefundAllDonationService;
import util.SendMail;
import vo.ActionForward;
import vo.DonationBean;
import vo.MemberBean;

public class RefundAllDonationAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		/* <실패한 펀딩 프로젝트 모금액 환불하기>
		 * ★★사전작업!! 프로젝트 상태 Done을 Refund와 Done으로 나누기
		 * (Refund? 목표모금액에 실패한 프로젝트로 환불작업이 필요)
		 * (Done? 목표모금액에 실패한 프로젝트로 환불작업 완료) 
		 * 1. 해당 프로젝트의 후원기록을 가져와
		 * 2. 각 후원기록의 총후원금을 후원자에게 각각 환불
		 *    (원래는 각 후원자에게 환불메일을 보내는 게 맞지만 자바메일이 서버에 부담이 많이 가기 때문에 이 부분 생략)
		 * 3. 프로젝트 기획자의 이메일로 해당 펀딩 프로젝트가 실패하여 모든 모금액이 정상 환불처리되었다는 안내메일 발송
		 */
		
		int project_id = Integer.parseInt(request.getParameter("project_id"));
		System.out.println("[RefundDonationAction] 파라미터값 project_id = "+project_id);
		
		//[순서-1] 해당 프로젝트의 후원기록을 가져오기
		RefundAllDonationService refundAllDonationService = new RefundAllDonationService();
		ArrayList<DonationBean> donationList = refundAllDonationService.getDonationList(project_id);
		
		if(donationList == null) {
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('후원기록이 없습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			
			boolean isRefundDonationSuccess = refundAllDonationService.refundDonation(donationList);
			
			if(!isRefundDonationSuccess) {
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('후원금 환불 처리에 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}else {
				
				//기획자에게 환불처리 안내메일 발송하기 위해 기획자 정보를 가져옴
				String planner_id = refundAllDonationService.getPlannerId(project_id);
				MemberBean plannerInfo = refundAllDonationService.getPlannerInfo(planner_id);
				
				//메일 발송을 위한 SendMail객체 생성
				SendMail sendMail = new SendMail();
				//메일 내용 추가 + 위에서 프로젝트-후원기록 객체로 가지고 올 것
				
				
			}
			
		}
		
		return forward;
	}

}

