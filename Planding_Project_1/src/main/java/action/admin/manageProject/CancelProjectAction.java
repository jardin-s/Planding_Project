package action.admin.manageProject;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.admin.manageProject.CancelProjectService;
import vo.ActionForward;
import vo.DonationBean;
import vo.MemberBean;
import vo.PlannerBean;
import vo.RewardBean;

public class CancelProjectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		int project_id =  Integer.parseInt(request.getParameter("project_id"));
		String status = request.getParameter("status");
		String kind = request.getParameter("kind");
		
		HttpSession session =  request.getSession();
		
		String a_id = (String) session.getAttribute("a_id");
		
		if(a_id == null) {//로그인이 풀린 상태라면
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('관리자 로그인이 필요합니다.');");
			out.println("adminLoginForm.adm;");
			out.println("</script>");
		}else {
			
			CancelProjectService cancelProjectService = new CancelProjectService();
			
			//프로젝트 ID로 프로젝트 기획자 정보를 얻어옴
			PlannerBean plannerInfo = cancelProjectService.getProjectPlanner(project_id);
			
			//기획자의 회원 ID로 이메일 정보를 얻어옴
			MemberBean plannerUserInfo = cancelProjectService.getPlannerUserInfo(plannerInfo.getMember_id());
			
			if(plannerInfo == null || plannerUserInfo == null) {//기획자 정보를 얻는 데 실패
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('프로젝트 기획자 정보를 얻는 데 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			
			}else {
				//기획자 email로 승인거절 안내 메일을 보내기 위해 email만 따로 저장
				String planner_email = plannerUserInfo.getEmail();
				
				/* [프로젝트 삭제 과정]
				 * 1. 프로젝트 기획자 삭제
				 * 2. 프로젝트-리워드 매핑테이블에서 해당 프로젝트의 리워드를 얻어와
				 * 2. 프로젝트-리워드 매핑테이블의 데이터 삭제 
				 * 3. 해당 프로젝트의 리워드 중 id가 1이 아닌 것만 리워드 테이블에서 삭제
				 * 4. 프로젝트 테이블에서 프로젝트 데이터 삭제
				 * */
				
				//★★공개예정 또는 진행중인 프로젝트를 취소
				//1. 공개예정이라면? 기획자, 리워드, 리워드매핑, 프로젝트데이터만 삭제
				//2. 진행중이라면? 환불처리 후, 기획자, 회원북마크(프로젝트 삭제 시 자동삭제), 후원기록, 리워드, 리워드매핑, 프로젝트 데이터 삭제
				
				//프로젝트ID로 리워드ID리스트를 얻어옴
				ArrayList<String> rewardIdList = cancelProjectService.getProjectRewardIdList(project_id);
				
				boolean isCancelProject = false;
				if(status.equalsIgnoreCase("ready")) {//공개예정
					
					isCancelProject = cancelProjectService.cancelProject(project_id, rewardIdList);
					
				}else if(status.equalsIgnoreCase("ongoing")) {//진행중
					
					//[순서-1] 후원자에게 환불처리 (사용자 계좌 업데이트 & 후원기록 테이블에서 후원기록 삭제)
					//후원기록 테이블에서 후원기록을 가져와서 후원자에게 환불처리
					
					ArrayList<DonationBean> donationList = cancelProjectService.getDonationList(project_id);
					boolean isRefundSuccess = cancelProjectService.refundDonation(donationList, project_id);
					
					//[순서-2] 기획자, 리워드, 리워드매핑, 프로젝트 데이터 삭제 (회원북마크는 프로젝트 삭제 시 자동삭제)
					
					boolean isCancelOngoingProject = cancelProjectService.cancelProject(project_id, rewardIdList);
					
					if(isRefundSuccess && isCancelOngoingProject) {//둘다 성공일 때만, isCancelProject가 true
						isCancelProject = true;
					}
				}
				
				if(!isCancelProject) {//모든 취소 작업에 실패하면
					PrintWriter out = response.getWriter();
					
					out.println("<script>");
					out.println("alert('프로젝트 취소가 실패했습니다.');");
					out.println("history.back();");
					out.println("</script>");
					
				}else {//모든 삭제작업이 성공했으면
					
					//기획자 email로 안내메일 전송-----------------------------------------------
					
					
					/* 구글의 기본적인 환경 정리가 끝난 후 JavaMail을 이용하여 메일 보내기 위한 5단계
					 * 1. 발신자의 메일계정과 비밀번호 등을 설정 (예: 구글계정 + 앱비밀번호)
					 * 2. Property에 SMTP서버 정보를 설정(예: 구글의 SMTP서버 정보를 설정)
					 * 	  ※SMTP(Simple Mail Transfer Protocol) : 메일 전송 프로토콜. 대부분의 메일서버는 SMTP서버를 거쳐 메일을 전송.
					 * 3. SMTP서버 정보와 사용자 정보를 기본으로 javax.mail.Session 객체 생성
					 * 4. Message클래스의 객체를 사용하여 수신자, 제목, 내용을 작성
					 * 5. Transport 클래스를 사용하여 작성한 Message 객체를 수신자에게 전달 
					 */
					
					//요청객체와 응답객체 인코딩 설정
					//request.setCharacterEncoding("UTF-8"); //컨트롤러에서 세팅된 request객체가 넘어옴
					response.setContentType("text/html; charset=UTF-8");
					
					//★주의 : 서블릿은 반드시 출력객체를 직접 생성해야 함 (JSP는 아래 코드 생략)
					//PrintWriter out = response.getWriter();
					
					//1. 발신자의 메일계정과 비밀번호 등을 설정 (예: 구글계정 + 앱비밀번호)
					String sender = "plandingproject@gmail.com";//보내는 사람
					String receiver = planner_email;//받는 사람
					String subject = "[PlanDing] 프로젝트 취소 안내";//제목
					String content = "<p>회원님이 신청 또는 진행중이셨던 프로젝트가 취소되었음을 알려드립니다.</p>";//내용
							
					
					final String host = "smtp.gmail.com"; //SMTP서버주소 (구글로 이메일 전송). 만약 보내는쪽이 네이버 "smtp.naver.com"
					final String username = "plandingproject"; //구글 아이디 입력
					final String password = "hnbknxjoayprexvn"; //구글 앱비번 입력
					
					final int port = 587;//구글에 대한 포트번호 : 프로그램 구분 (네이버:25)(※4465:2차보안인증(임시비밀번호). 메일전송이 안 됨)
					
					try {
						//2. Property에 SMTP서버 정보를 설정(예: 구글의 SMTP서버 정보를 설정)
						Properties properties = System.getProperties();
						
						//starttls Command를 사용할 수 있게(=enable) 설정
						//※start TLS : TLS 버전문제로 오류 발생 (브라우저에서 TLS 1.2를 지원하지 않음)
						properties.put("mail.smtp.starttls.enable", "true");//gmail은 무조건 true
						
						//오류발생-Caused by: javax.net.ssl.SSLHandshakeException: No appropriate protocol 프로토콜에 도달할 수 없음
						properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
						
						//SMTP서버 주소 속성값으로 구글SMTP서버주소를 넣음
						properties.put("mail.smtp.host", host);//"smtp.gmail.com"
						
						//auth Command를 사용하여 사용자인증을 할 수 있게 설정
						properties.put("mail.smtp.auth", "true");//gmail은 무조건 true
						
						//포트번호 설정
						properties.put("mail.smtp.port", port);//587
						
						
						//3. SMTP서버 정보와 사용자 정보를 기본으로 Session 객체 생성
									
						//mail에 대한 Session (javax.mail.Session)
						Session mail_session = Session.getDefaultInstance(properties,//SMTP서버 정보
																		 new Authenticator() {//사용자 인증 정보 객체 : Authenticator추상클래스 생성자() 정의와 동시에 객체생성
																				
																			//재정의해야 할 메서드 (추상클래스의 추상메서드) -> 추상메서드를 재정의했으므로 일반클래스가 되어 클래스객체 생성이 가능해짐
																			@Override
																			protected PasswordAuthentication getPasswordAuthentication() {
																				return new PasswordAuthentication(username, password);//"wjddnjs051339","앱비밀번호"
																			}//사용자인증정보 객체를 반환
																			   
																		 }//생성자 끝
																		  
																		 ); 
						
						//4. Message클래스의 객체를 사용하여 수신자, 제목, 내용을 작성
						Message message = new MimeMessage(mail_session); //사용자인증정보가 담긴 session객체로 Message객체 생성
						
						//메일을 보내는 주소 생성
						Address sender_address = new InternetAddress(sender);
						
						//메일을 받는 주소 생성
						Address receiver_address = new InternetAddress(receiver);
						
						//메일전송에 필요한 값 설정
						message.setHeader("content-type", "text/html; charset=utf-8");
						message.setFrom(sender_address); //보내는 메일주소 세팅
						message.addRecipient(Message.RecipientType.TO, receiver_address); //메세지타입(TO: ~에게), 받는 메일주소 세팅
						
						message.setSubject(subject);//메일제목 세팅
						message.setContent(content, "text/html; charset=utf-8");//메일내용 세팅
						message.setSentDate(new java.util.Date());//날짜 생성하여 세팅 (오늘날짜)
						
						//5. Transport 클래스를 사용하여 작성한 Message 객체를 수신자에게 전달 
						Transport.send(message);
						
						System.out.println("[CancelProjectAction] 메일이 정상적으로 전송되었습니다.");//콘솔에 출력 : 메일전송 확인			
						
						
					}catch(Exception e) {
						System.out.println("[CancelProjectAction] SMTP서버가 잘못 설정되었거나 서비스에 문제가 있습니다.");//콘솔에 출력 : 메일전송 확인
						e.printStackTrace(); //콘솔에 출력 : 개발자가 에러를 좀더 찾기 쉽게
					}
					
					if(kind.equalsIgnoreCase("donate")) {
						//다시 프로젝트 목록보기 요청
						forward = new ActionForward("manageDonateProjectList.mngp", true);
					}else if(kind.equalsIgnoreCase("fund")) {
						//다시 프로젝트 목록보기 요청
						forward = new ActionForward("manageFundProjectList.mngp", true);
					}
								
				
				}//모든 프로젝트 삭제, 취소 작업이 성공했는지 여부				
			
			}//기획자 정보를 얻었는지 여부
						
		}//현재 관리자 로그인이 되어있는지 여부
		
		return forward;
	}

}
