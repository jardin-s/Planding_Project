package action.user.project;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.project.UserCancelDonationService;
import util.SendMail;
import vo.ActionForward;
import vo.AddressBean;
import vo.DonationBean;
import vo.MemberBean;
import vo.ProjectDonationRewardBean;

public class UserCancelDonationAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		
		if(u_id == null) {//로그인이 풀린 상태라면
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('세션이 만료되었습니다. 다시 로그인해주세요.');");
			out.println("location.href='userLoginForm.usr';");
			out.println("</script>");
		}else {
			
			/* <후원 취소>
			 * 1. 총후원금액만큼 프로젝트의 현재모금액 빼기
			 * 2. 총후원금액만큼 사용자 계좌에 더하기
			 * 3. 후원기록 삭제
			 * 4. 사용자 id로 사용자 현재잔액 가져와 세션에 다시 저장
			 */
			
			//후원기록 id를 가져옴
			int donation_id = Integer.parseInt(request.getParameter("donation_id"));
			System.out.println("[UserCancelDonationAction] 파라미터 donation_id = "+donation_id);
			
			UserCancelDonationService userCancelDonationService = new UserCancelDonationService();
			
			//1. 후원ID로 후원정보를 들고 오기
			ProjectDonationRewardBean donationInfo = userCancelDonationService.getDonationInfo(donation_id);
						
			//2. 후원 취소
			boolean isCancelDonationSuccess = userCancelDonationService.cancelDonation(donationInfo);
							
			
			if(!isCancelDonationSuccess) {//후원 취소 실패
				response.setContentType("text/html; charset=UTF-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('후원 취소를 처리하는 데 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
				
			}else {//후원취소 성공
				
				
				//후원 취소 메일 전송을 위해 SendMail객체 생성, 사용자 정보를 들고옴
				SendMail mail =  new SendMail();
				MemberBean userInfo = userCancelDonationService.getUserInfo(donationInfo.getMember_id());
				System.out.println("[UserCancelDonationAction] 메일전송을 위해 조회한 userInfo = "+userInfo);
				
				if(donationInfo.getReward_id().equalsIgnoreCase("default")) {//기본리워드 후원 시
					response.setContentType("text/html; charset=UTF-8");
					
					//내용 세팅 후 메일 발송
					mail.setCancelDonateMsgDefault(donationInfo);
					boolean isMailSendSuccess = mail.sendMailCancelDonateDefault(userInfo);
					if(!isMailSendSuccess) {
						System.out.println("[UserCancelDonationAction] 메일 전송 실패");
					}
					
					//후원취소 시 후원내역
					request.setAttribute("donationInfo", donationInfo);
					request.setAttribute("showPage", "user/project/userCancelDonateSuccess_Default.jsp");
					forward = new ActionForward("userTemplate.jsp", false);
					
					
				}else {//선택리워드 후원 시
					
					//주소Id로 주소정보 가져오기 (★★★만약 address_id가 기본주소가 아니라면 해당 주소는 삭제)
					AddressBean addressInfo = userCancelDonationService.getAddrInfo(donationInfo.getAddress_id());
					if(!addressInfo.getBasic_status().equalsIgnoreCase("Y")) {
						boolean isDeleteAddressSuccess = userCancelDonationService.deleteNonBasicAddress(addressInfo.getAddress_id());
						
						if(!isDeleteAddressSuccess) {
							System.out.println("[UserCancelDonationAction] 주소 삭제 실패");
						}else {
							System.out.println("[UserCancelDonationAction] 주소 삭제 성공.");
						}//주소 삭제 여부
						
					}//주소가 기본주소가 아니면 
					
					response.setContentType("text/html; charset=UTF-8");
					
					//내용 세팅 후 메일 발송
					mail.setCancelDonateMsgSelect(donationInfo, addressInfo);
					boolean isMailSendSuccess = mail.sendMailCancelDonateSelect(userInfo);
					
					if(!isMailSendSuccess) {
						System.out.println("[UserCancelDonationAction] 메일 전송 실패");
					}
					
					//후원취소 시 후원내역
					request.setAttribute("donationInfo", donationInfo);
					request.setAttribute("addressInfo", addressInfo);
					request.setAttribute("showPage", "user/project/userCancelDonateSuccess_Select.jsp");
					forward = new ActionForward("userTemplate.jsp", false);
					
				
				}// 기본리워드/선택리워드 여부
				
			}//후원취소 성공여부
						
		}//로그인확인
		
		return forward;
	}

}
