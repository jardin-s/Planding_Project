package action.user.project;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.project.ProjectPageViewService;
import svc.user.project.UserDonateProjectService;
import util.SendMail;
import vo.ActionForward;
import vo.AddressBean;
import vo.DonationBean;
import vo.MemberBean;
import vo.ProjectBean;
import vo.RewardBean;

public class UserDonateProjectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		/* <후원하기> 
		 * 1. 기부프로젝트 - 기본리워드로 후원 (1000원 + 추가후원금액), 배송지 없음
		 * 2. 펀딩프로젝트 - 기본리워드로 후원 (1000원 + 추가후원금액), 배송지 없음
		 * 3. 펀딩프로젝트 - 리워드 선택 (리워드 금액 + 추가후원금액), 배송지 있음
		 * 
		 * 파라미터값 : (공통) project_id, reward_id, r_price, add_donation, member_id, money + project_kind(편의를 위해 파라미터값으로 같이 넘김)
		 * 		    +(펀딩 리워드선택시) receiver_name, receiver_phone, postcode, address1, address2
		 * 
		 * 후원기록 테이블에 후원기록으로 insert
		 * 프로젝트 테이블에 현재모금액 update
		 * 사용자 계좌에서 후원금액만큼 빼기 update
		 * 입력된 배송지 정보가 기존주소와 다를경우, 새 주소로 insert
		 */
		
		//공통 파라미터
		int project_id = Integer.parseInt(request.getParameter("project_id"));
		String reward_id = request.getParameter("reward_id");
		int r_price = Integer.parseInt(request.getParameter("r_price"));
		int add_donation = Integer.parseInt(request.getParameter("add_donation"));//추가후원금액 없을 경우 이미 0으로 세팅된 상태
		String member_id = request.getParameter("member_id");
		int u_money = Integer.parseInt(request.getParameter("money"));//사용자 가상계좌 잔액
		
		System.out.println("[UserDonateProjectAction]");
		System.out.println("project_id = "+project_id);
		System.out.println("reward_id = "+reward_id);
		System.out.println("r_price = "+r_price);
		System.out.println("add_donation = "+add_donation);
		System.out.println("member_id = "+member_id);
		System.out.println("u_money = "+u_money);
		
		
		
		if(u_money < (r_price + add_donation)) {//사용자 현재 잔액이 총후원금액보다 적을경우
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('계좌 잔액이 부족하여 후원할 수 없습니다.');");//알림창을 띄우고 로그인폼보기 요청으로 돌아감
			out.println("history.back();");		//(☆history.back()보다는 요청으로 할 것)
			out.println("</script>");
		}else {
			
			UserDonateProjectService userDonateProjectService = new UserDonateProjectService();

			boolean isDonateProjectSuccess = false;
			AddressBean addressInfo = null;
			
			//선택한 리워드에 따라 다르게 처리
			if(reward_id.equalsIgnoreCase("default")){//펀딩 프로젝트에서 기본리워드 선택 시
				
				//후원하기 (주소 X)
				DonationBean donation = new DonationBean(project_id, member_id, reward_id, r_price, add_donation);
				isDonateProjectSuccess = userDonateProjectService.donateProjectNotAddr(donation);
								
			}else {
				
				//입력한 주소정보가 기존주소인지 확인
				AddressBean address = new AddressBean(member_id,
													  request.getParameter("receiver_name"),
													  request.getParameter("receiver_phone"),
													  Integer.parseInt(request.getParameter("postcode")),
													  request.getParameter("address1"),
													  request.getParameter("address2"));
				
				addressInfo = userDonateProjectService.matchAddr(address);
				
				if(addressInfo != null) {//기존에 있는 주소라면
				
					//후원하기 (주소 O)
					DonationBean donation = new DonationBean(project_id, member_id, reward_id, r_price, add_donation, addressInfo.getAddress_id());
					isDonateProjectSuccess = userDonateProjectService.donateProjectAddr(donation);
				
				} else {//기존에 없는 주소라면
					//주소ID 생성
					Date now = new Date();
					SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
					String address_id = format.format(now);
					System.out.println("생성된 address_id = "+address_id);
					
					//주소 객체 생성
					address.setAddress_id(address_id);
					address.setBasic_status("N");
					addressInfo = address;//addressInfo변수가 새로 생성된 주소객체를 참조하도록 함
					
					//새 주소를 insert
					boolean isInsertNewAddrSuccess = userDonateProjectService.insertNewAddr(addressInfo);
					
					if(!isInsertNewAddrSuccess) {//새주소 입력 실패 시
						response.setContentType("text/html; charset=UTF-8");
						
						PrintWriter out = response.getWriter();
						out.println("<script>");
						out.println("alert('배송지 저장에 실패했습니다.');");//알림창을 띄우고 로그인폼보기 요청으로 돌아감
						out.println("history.back();");		//(☆history.back()보다는 요청으로 할 것)
						out.println("</script>");
					}else {
					
						//후원하기 (주소 O)
						DonationBean donation = new DonationBean(project_id, member_id, reward_id, r_price, add_donation, address_id);
						isDonateProjectSuccess = userDonateProjectService.donateProjectAddr(donation);
												
						
					}//새주소 insert 성공여부
				
				}//기존주소인지 확인
				
												
			}//기본리워드 선택여부
			
			if(!isDonateProjectSuccess) {//후원실패시
				
				response.setContentType("text/html; charset=UTF-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('후원처리에 실패했습니다.');");//알림창을 띄우고 로그인폼보기 요청으로 돌아감
				out.println("history.back();");		//(☆history.back()보다는 요청으로 할 것)
				out.println("</script>");
				
			}else {//후원성공시
				
				//후원내역 출력을 위해 프로젝트 정보, 리워드 정보를 얻어옴
				ProjectPageViewService projectPageViewService = new ProjectPageViewService();
				ProjectBean projectInfo = projectPageViewService.getProjectInfoDate(project_id);
				RewardBean rewardInfo = projectPageViewService.getRewardInfo(reward_id);
				
				//request속성으로 저장
				request.setAttribute("projectInfo", projectInfo);
				request.setAttribute("rewardInfo", rewardInfo);
				request.setAttribute("add_donation", add_donation);
				
				//후원내역 메일 전송을 위해 SendMail객체 생성, 사용자 정보를 가져옴
				SendMail mail = new SendMail(); 
				MemberBean userInfo= userDonateProjectService.getUserInfo(member_id);
				
				//이때, 사용자 잔액 다시 세션에 저장
				HttpSession session = request.getSession();
				session.setAttribute("u_money", userInfo.getMoney());
				
				if(reward_id.equalsIgnoreCase("default")) {//기본리워드
					response.setContentType("text/html; charset=UTF-8");
					//메일 내용 세팅
					mail.setDonateSuccessMsgDefault(projectInfo, rewardInfo, userInfo, add_donation);
					
					response.setContentType("text/html; charset=UTF-8");
					boolean isMailSendSuccess = mail.sendMailDonateSuccessDefault(userInfo);
					
					if(!isMailSendSuccess) {
						System.out.println("메일 전송에 실패했습니다.");
					}
					
					request.setAttribute("showPage", "user/project/userDonateSuccess_Default.jsp");
					forward = new ActionForward("userTemplate.jsp", false);
					
				}else {//리워드 선택
					//메일 내용 세팅
					mail.setDonateSuccessMsgSelect(projectInfo, rewardInfo, userInfo, add_donation, addressInfo);
					
					response.setContentType("text/html; charset=UTF-8");
					boolean isMailSendSuccess = mail.sendMailDonateSuccessSelect(userInfo);
					if(!isMailSendSuccess) {
						System.out.println("[UserDonateProjectAction] 메일 전송에 실패했습니다.");
					}

					//주소를 request속성으로 저장
					request.setAttribute("addressInfo", addressInfo);
					
					request.setAttribute("showPage", "user/project/userDonateSuccess_Select.jsp");
					forward = new ActionForward("userTemplate.jsp", false);
				}
				
			}
			
		}//사용자 계좌 잔액 확인	
		
		
		return forward;
	}

}
