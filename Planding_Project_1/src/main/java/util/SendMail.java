package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import vo.AddressBean;
import vo.DonationBean;
import vo.MemberBean;
import vo.ProjectBean;
import vo.ProjectDonationRewardBean;
import vo.ProjectPlannerBean;
import vo.RewardBean;

 

public class SendMail {
   
   private String donateSuccessMsgDefault;
   private String donateSuccessMsgSelect;
   
   private String cancelDonateMsgDefault;
   private String cancelDonateMsgSelect;
   
   private String sendDonationAmountMsg;//모금성공한 기획자에게 모금액이 송금되었음을 안내
   private String sendRefundDonationMsg;//모금실패한 펀딩기획자에게 모금액이 후원자에게 환불처리되었음을 안내
   
   public void setDonateSuccessMsgDefault(ProjectBean projectInfo, RewardBean rewardInfo, MemberBean userInfo, int add_donation) {
	   System.out.println("[setDonateSuccessMsgDefault] 내용구성에 필요한 변수들 값 확인 -------");
	   System.out.println("projectInfo.getDeadline() = "+projectInfo.getDeadline());
	   System.out.println("projectInfo.getTitle() = "+projectInfo.getTitle());
	   System.out.println("projectInfo.getCurr_amount_df() = "+projectInfo.getCurr_amount_df());
	   System.out.println("projectInfo.getProgress() = "+projectInfo.getProgress());
	   System.out.println("rewardInfo.getR_price() = "+rewardInfo.getR_price());
	   System.out.println("add_donation = "+add_donation);
	   System.out.println("projectInfo.getDeadline() = "+projectInfo.getDeadline());
	   System.out.println("rewardInfo.getR_price() + add_donation = "+rewardInfo.getR_price() + add_donation);
	   System.out.println("------------------------------------------------------------");
	   
	   
	   String deadline = null;
	   if(projectInfo.getDeadline() == 0) {
		   deadline = "오늘 마감";
	   }else {
		   deadline = projectInfo.getDeadline()+"일 남음";
	   }
	   
	   StringBuffer sb = new StringBuffer();
	   sb.append("<div><div>후원 완료 안내 메일입니다.</div><br><br><div><p><b>");
	   sb.append("<div><div><strong>[ 후원정보 ]</strong></div><br><div><p><b>");
	   sb.append(projectInfo.getTitle());
	   sb.append("</b><br>&nbsp;");
	   sb.append(projectInfo.getCurr_amount_df());
	   sb.append("원 | 달성률 : ");
	   sb.append(projectInfo.getProgress());
	   sb.append("%");
	   sb.append(deadline);
	   sb.append("<br></div><br><div><p><b>후원금액</b><br>");
	   sb.append("&nbsp;기본 금액 : ");
	   sb.append(rewardInfo.getR_price());
	   sb.append("원<br>&nbsp;추가 후원금액 : ");
	   sb.append(add_donation);
	   sb.append("원<br>&nbsp;<b>최종 후원금액</span>&nbsp;&nbsp;:&nbsp;&nbsp;");
	   sb.append((rewardInfo.getR_price()+add_donation));
	   sb.append("원</b><br></p></div></div>");
	   
	   System.out.println("[SendMail] setDonateSuccessMsgDefault 완료");
	   
	   donateSuccessMsgDefault = sb.toString();
   }
   
   public void setDonateSuccessMsgSelect(ProjectBean projectInfo, RewardBean rewardInfo, MemberBean userInfo, int add_donation, AddressBean addressInfo) {
	   System.out.println("[setDonateSuccessMsgSelect] 내용구성에 필요한 변수들 값 확인 -------");
	   System.out.println("projectInfo.getDeadline() = "+projectInfo.getDeadline());
	   System.out.println("projectInfo.getTitle() = "+projectInfo.getTitle());
	   System.out.println("projectInfo.getCurr_amount_df() = "+projectInfo.getCurr_amount_df());
	   System.out.println("projectInfo.getProgress() = "+projectInfo.getProgress());
	   System.out.println("rewardInfo.getR_name() = "+rewardInfo.getR_name());
	   System.out.println("rewardInfo.getR_content() = "+rewardInfo.getR_content());
	   System.out.println("rewardInfo.getR_price() = "+rewardInfo.getR_price());
	   System.out.println("add_donation = "+add_donation);
	   System.out.println("projectInfo.getDeadline() = "+projectInfo.getDeadline());
	   System.out.println("rewardInfo.getR_price() + add_donation = "+rewardInfo.getR_price() + add_donation);
	   
	   System.out.println("addressInfo.getReceiver_name() = "+addressInfo.getReceiver_name());
	   System.out.println("addressInfo.getReceiver_phone() = "+addressInfo.getReceiver_phone());
	   System.out.println("addressInfo.getPostcode() = "+addressInfo.getPostcode());
	   System.out.println("addressInfo.getAddress1() = "+addressInfo.getAddress1());
	   System.out.println("addressInfo.getAddress2() = "+addressInfo.getAddress2());
	   System.out.println("------------------------------------------------------------");
	   
	   
	   String deadline = null;
	   if(projectInfo.getDeadline() == 0) {
		   deadline = "오늘 마감";
	   }else {
		   deadline = projectInfo.getDeadline()+"일 남음";
	   }
	   
	   StringBuffer sb = new StringBuffer();
	   sb.append("<div><div>후원 완료 안내 메일입니다.</div><br><br><div><p><b>");
	   sb.append("<div><div><strong>[ 후원정보 ]</strong></div><br><div><p><b>");
	   sb.append(projectInfo.getTitle());
	   sb.append("</b><br>&nbsp;");
	   sb.append(projectInfo.getCurr_amount_df());
	   sb.append("원 | 달성률 : ");
	   sb.append(projectInfo.getProgress());
	   sb.append("%");
	   sb.append(deadline);
	   sb.append("<br></div><br><div><p><b>리워드 : ");
	   sb.append(rewardInfo.getR_name());
	   sb.append("</b><br>&nbsp;");
	   sb.append(rewardInfo.getR_content());
	   sb.append("<br>&nbsp;리워드 금액 : ");
	   sb.append(rewardInfo.getR_price());
	   sb.append("원<br>&nbsp;추가 후원금액 : ");
	   sb.append(add_donation);
	   sb.append("원<br>&nbsp;<b>최종 후원금액</span>&nbsp;&nbsp;:&nbsp;&nbsp;");
	   sb.append((rewardInfo.getR_price()+add_donation));
	   sb.append("원</b><br></p></div><div><p><b>배송지</b><br>&nbsp;수령인 이름 : ");
	   sb.append(addressInfo.getReceiver_name());
	   sb.append("<br>&nbsp;수령인 연락처 : ");
	   sb.append(addressInfo.getReceiver_phone());
	   sb.append("<br>&nbsp;우편번호 : ");
	   sb.append(addressInfo.getPostcode());
	   sb.append("<br>&nbsp;기본주소 : ");
	   sb.append(addressInfo.getAddress1());
	   sb.append("<br>&nbsp;상세주소 : ");
	   sb.append(addressInfo.getAddress2());
	   sb.append("<br></p></div></div>");
	  
	   System.out.println("[SendMail] setDonateSuccessMsgSelect 완료");
	   
	   donateSuccessMsgSelect = sb.toString();
	   
   }
   
   public void setCancelDonateMsgDefault(ProjectDonationRewardBean donation) {
	   
	   System.out.println("[setCancelDonateMsgDefault] 내용구성에 필요한 변수들 값 확인 -------");
	   System.out.println("donation.getTitle() = "+donation.getTitle());
	   System.out.println("donation.getR_price() = "+donation.getR_price());
	   System.out.println("donation.getAdd_donation() = "+donation.getAdd_donation());
	   System.out.println("donation.getTotalDonation() = "+donation.getTotalDonation());
	   System.out.println("donation.getDonatedate() = "+donation.getDonatedate());
	   System.out.println("------------------------------------------------------------");
	   
	   Date now = new Date();
	   SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
	   String now_str = df.format(now);
	   System.out.println("[setCancelDonateMsgDefault] now_str = "+now_str);
	   	   
	   StringBuffer sb = new StringBuffer();
	   sb.append("<div><div>후원 취소 안내 메일입니다.</div><br><br><div><p><b>");
	   sb.append("<div><div><strong>[ 후원정보 ]</strong></div><br><div><p><b>");
	   sb.append(donation.getTitle());
	   sb.append("</b><br>&nbsp;<br></div><br><div><p><b>후원금액</b><br>&nbsp;기본 금액 : ");
	   sb.append(donation.getR_price());
	   sb.append("원<br>&nbsp;추가 후원금액 : ");
	   sb.append(donation.getAdd_donation());
	   sb.append("원<br>&nbsp;<b>최종 후원금액</span>&nbsp;&nbsp;:&nbsp;&nbsp;");
	   sb.append(donation.getTotalDonation());
	   sb.append("원</b><br>&nbsp;후원일자 : ");
	   sb.append(donation.getDonatedate());
	   sb.append("<br>&nbsp;취소일자 : ");
	   sb.append(now_str);
	   sb.append("</p></div></div>");
	   
	   System.out.println("[SendMail] setCancelDonateMsgDefault 완료");
	   	   	   
	   cancelDonateMsgDefault = sb.toString();
   }
   
   
   public void setCancelDonateMsgSelect(ProjectDonationRewardBean donation, AddressBean addressInfo) {
	  
	   System.out.println("[setCancelDonateMsgSelect] 내용구성에 필요한 변수들 값 확인 -------");
	   System.out.println("donation.getTitle() = "+donation.getTitle());
	   System.out.println("donation.getR_name() = "+donation.getR_name());
	   System.out.println("donation.getR_content() = "+donation.getR_content());
	   System.out.println("donation.getR_price() = "+donation.getR_price());
	   System.out.println("donation.getAdd_donation() = "+donation.getAdd_donation());
	   System.out.println("donation.getTotalDonation() = "+donation.getTotalDonation());
	   System.out.println("donation.getDonatedate() = "+donation.getDonatedate());
	   
	   System.out.println("addressInfo.getReceiver_name() = "+addressInfo.getReceiver_name());
	   System.out.println("addressInfo.getReceiver_phone() = "+addressInfo.getReceiver_phone());
	   System.out.println("addressInfo.getPostcode() = "+addressInfo.getPostcode());
	   System.out.println("addressInfo.getAddress1() = "+addressInfo.getAddress1());
	   System.out.println("addressInfo.getAddress2() = "+addressInfo.getAddress2());
	   System.out.println("------------------------------------------------------------");
	   
	   Date now = new Date();
	   SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
	   String now_str = df.format(now);
	   System.out.println("[setCancelDonateMsgSelect] now_str = "+now_str);
	   
	   StringBuffer sb = new StringBuffer();
	   sb.append("<div><div>후원 취소 안내 메일입니다.</div><br><br><div><p><b>");
	   sb.append("<div><div><strong>[ 후원정보 ]</strong></div><br><div><p><b>");
	   sb.append(donation.getTitle());
	   sb.append("</b><br>&nbsp;");
	   sb.append("<br></div><br><div><p><b>리워드 : ");
	   sb.append(donation.getR_name());
	   sb.append("</b><br>&nbsp;");
	   sb.append(donation.getR_content());
	   sb.append("<br>&nbsp;리워드 금액 : ");
	   sb.append(donation.getR_price());
	   sb.append("원<br>&nbsp;추가 후원금액 : ");
	   sb.append(donation.getAdd_donation());
	   sb.append("원<br>&nbsp;<b>최종 후원금액</span>&nbsp;&nbsp;:&nbsp;&nbsp;");
	   sb.append(donation.getTotalDonation());
	   sb.append("원</b><br></p></div><div><p><b>배송지</b><br>&nbsp;수령인 이름 : ");
	   sb.append(addressInfo.getReceiver_name());
	   sb.append("<br>&nbsp;수령인 연락처 : ");
	   sb.append(addressInfo.getReceiver_phone());
	   sb.append("<br>&nbsp;우편번호 : ");
	   sb.append(addressInfo.getPostcode());
	   sb.append("<br>&nbsp;기본주소 : ");
	   sb.append(addressInfo.getAddress1());
	   sb.append("<br>&nbsp;상세주소 : ");
	   sb.append(addressInfo.getAddress2());
	   sb.append("<br></p></div>");
	   sb.append("<br>&nbsp;후원일자 : ");
	   sb.append(donation.getDonatedate());
	   sb.append("<br>&nbsp;취소일자 : ");
	   sb.append(now_str);
	   sb.append("</p></div></div>");
	   
	   System.out.println("[SendMail] setCancelDonateMsgSelect 완료");
	   
	   cancelDonateMsgSelect = sb.toString();
	   
   }
   
   
   public void setSendDonationAmountMsg(ProjectPlannerBean projectPlanner, int fee_income) {
	   
	   Date now = new Date();
	   SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
	   String now_str = df.format(now);
	   System.out.println("[setCancelDonateMsgSelect] now_str = "+now_str);
	   
	   StringBuffer sb = new StringBuffer();
	   sb.append("<div><div>후원 성공 모금액 송금 안내 메일입니다.</div><br><br><div><p><b>");
	   sb.append("<div><div><strong>[ 송금정보 ]</strong></div><br><div><p><b>");
	   sb.append(projectPlanner.getTitle());
	   sb.append("</b><br>&nbsp;");
	   sb.append("<br></div><br><div>");
	   sb.append("<br><div><p>&nbsp;<b>목표 모금액 : ");
	   sb.append(projectPlanner.getGoal_amount());
	   sb.append("원</b><br>&nbsp;<b>현재 모금액 : ");
	   sb.append("원</b><br>&nbsp;<b>수수료 : ");
	   sb.append(fee_income);
	   sb.append("원</b><br>&nbsp;<b>최종 전달 모금액 : ");
	   sb.append((projectPlanner.getCurr_amount() - fee_income));
	   sb.append("원</b><br></p></div></div>");
	   
	   System.out.println("[SendMail] setSendDonationAmountMsg 완료");
	   
	   sendDonationAmountMsg = sb.toString();
	   
   }
   
   //종료프로젝트 모금액 후원자에게 환불된 것을 기획자에게 알림
   public void setSendRefundDonationMsg(ProjectBean projectInfo, int donationCount) {
   	   
	   Date now = new Date();
	   SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
	   String now_str = df.format(now);
	   System.out.println("[setCancelDonateMsgSelect] now_str = "+now_str);
	   
	   StringBuffer sb = new StringBuffer();
	   sb.append("<div align='center'><p>종료된 프로젝트의 모금액 환불 처리 완료 안내드립니다.</p><table border='1' style='margin:0 auto'><caption>프로젝트 최종결산</caption><tr><th colspan='2'>");
	   sb.append(projectInfo.getTitle());
	   sb.append("</th></tr><tr><th>목표모금액</th><td>");
	   sb.append(projectInfo.getGoal_amount());
	   sb.append("원</td></tr><tr><th>최종모금액</th><td>");
	   sb.append(projectInfo.getCurr_amount());
	   sb.append("원</td></tr><tr><th>달성률</th<td>>");
	   sb.append(projectInfo.getProgress());
	   sb.append("%</td></tr><tr><th>총 후원자 수</th><td>");
	   sb.append(donationCount);
	   sb.append("명</td></tr></table><p>저희 사이트를 이용해주셔서 감사합니다.</p></div>");
	  
	   System.out.println("[SendMail] setSendRefundDonationMsg 완료");
	   
	   sendRefundDonationMsg = sb.toString();
	   
   }
   
   

   public boolean sendMailDonateSuccessDefault(MemberBean userInfo) {

		//1. 발신자의 메일계정과 비밀번호 등을 설정 (예: 구글계정 + 앱비밀번호)
		String sender = "plandingproject@gmail.com";//보내는 사람
		String receiver = userInfo.getEmail();//받는 사람
		String subject = "[PlanDing] "+userInfo.getName()+"님의 후원내역을 보내드립니다.";//제목
		String content = donateSuccessMsgDefault;//내용
				
		
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
			Session session = Session.getDefaultInstance(properties,//SMTP서버 정보
														 new Authenticator() {//사용자 인증 정보 객체 : Authenticator추상클래스 생성자() 정의와 동시에 객체생성
																
															//재정의해야 할 메서드 (추상클래스의 추상메서드) -> 추상메서드를 재정의했으므로 일반클래스가 되어 클래스객체 생성이 가능해짐
															@Override
															protected PasswordAuthentication getPasswordAuthentication() {
																return new PasswordAuthentication(username, password);//"wjddnjs051339","앱비밀번호"
															}//사용자인증정보 객체를 반환
															   
														 }//생성자 끝
														  
														 ); 
			
			//4. Message클래스의 객체를 사용하여 수신자, 제목, 내용을 작성
			Message message = new MimeMessage(session); //사용자인증정보가 담긴 session객체로 Message객체 생성
			
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
			
			System.out.println("sendMailDonateSuccessDefault : 메일이 정상적으로 전송되었습니다.");//콘솔에 출력 : 메일전송 확인			
			return true;
			
		}catch(Exception e) {
			System.out.println("sendMailDonateSuccessDefault : SMTP서버가 잘못 설정되었거나 서비스에 문제가 있습니다.");//콘솔에 출력 : 메일전송 확인
			e.printStackTrace(); //콘솔에 출력 : 개발자가 에러를 좀더 찾기 쉽게
			return false;
		}   

   }
   
   public boolean sendMailDonateSuccessSelect(MemberBean userInfo) {
	   
	   //1. 발신자의 메일계정과 비밀번호 등을 설정 (예: 구글계정 + 앱비밀번호)
	   String sender = "plandingproject@gmail.com";//보내는 사람
	   String receiver = userInfo.getEmail();//받는 사람
	   String subject = "[PlanDing] "+userInfo.getName()+"님의 후원내역을 보내드립니다.";//제목
	   String content = donateSuccessMsgSelect;//내용
	   
	   
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
		   Session session = Session.getDefaultInstance(properties,//SMTP서버 정보
				   new Authenticator() {//사용자 인증 정보 객체 : Authenticator추상클래스 생성자() 정의와 동시에 객체생성
			   
			   //재정의해야 할 메서드 (추상클래스의 추상메서드) -> 추상메서드를 재정의했으므로 일반클래스가 되어 클래스객체 생성이 가능해짐
			   @Override
			   protected PasswordAuthentication getPasswordAuthentication() {
				   return new PasswordAuthentication(username, password);//"wjddnjs051339","앱비밀번호"
			   }//사용자인증정보 객체를 반환
			   
		   }//생성자 끝
		   
				   ); 
		   
		   //4. Message클래스의 객체를 사용하여 수신자, 제목, 내용을 작성
		   Message message = new MimeMessage(session); //사용자인증정보가 담긴 session객체로 Message객체 생성
		   
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
		   
		   System.out.println("sendMailDonateSuccessSelect : 메일이 정상적으로 전송되었습니다.");//콘솔에 출력 : 메일전송 확인			
		   return true;
		   
	   }catch(Exception e) {
		   System.out.println("sendMailDonateSuccessSelect : SMTP서버가 잘못 설정되었거나 서비스에 문제가 있습니다.");//콘솔에 출력 : 메일전송 확인
		   e.printStackTrace(); //콘솔에 출력 : 개발자가 에러를 좀더 찾기 쉽게
		   return false;
	   }   
	   
   }
   
   public boolean sendMailCancelDonateDefault(MemberBean userInfo) {
	   
	   //1. 발신자의 메일계정과 비밀번호 등을 설정 (예: 구글계정 + 앱비밀번호)
	   String sender = "plandingproject@gmail.com";//보내는 사람
	   String receiver = userInfo.getEmail();//받는 사람
	   String subject = "[PlanDing] "+userInfo.getName()+"님의 후원취소내역을 보내드립니다.";//제목
	   String content = cancelDonateMsgDefault;//내용
	   
	   
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
		   Session session = Session.getDefaultInstance(properties,//SMTP서버 정보
				   new Authenticator() {//사용자 인증 정보 객체 : Authenticator추상클래스 생성자() 정의와 동시에 객체생성
			   
			   //재정의해야 할 메서드 (추상클래스의 추상메서드) -> 추상메서드를 재정의했으므로 일반클래스가 되어 클래스객체 생성이 가능해짐
			   @Override
			   protected PasswordAuthentication getPasswordAuthentication() {
				   return new PasswordAuthentication(username, password);//"wjddnjs051339","앱비밀번호"
			   }//사용자인증정보 객체를 반환
			   
		   }//생성자 끝
		   
				   ); 
		   
		   //4. Message클래스의 객체를 사용하여 수신자, 제목, 내용을 작성
		   Message message = new MimeMessage(session); //사용자인증정보가 담긴 session객체로 Message객체 생성
		   
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
		   
		   System.out.println("sendMailCancelDonateDefault : 메일이 정상적으로 전송되었습니다.");//콘솔에 출력 : 메일전송 확인			
		   return true;
		   
	   }catch(Exception e) {
		   System.out.println("sendMailCancelDonateDefault : SMTP서버가 잘못 설정되었거나 서비스에 문제가 있습니다.");//콘솔에 출력 : 메일전송 확인
		   e.printStackTrace(); //콘솔에 출력 : 개발자가 에러를 좀더 찾기 쉽게
		   return false;
	   }   
	   
   }
   
   public boolean sendMailCancelDonateSelect(MemberBean userInfo) {
	   
	   //1. 발신자의 메일계정과 비밀번호 등을 설정 (예: 구글계정 + 앱비밀번호)
	   String sender = "plandingproject@gmail.com";//보내는 사람
	   String receiver = userInfo.getEmail();//받는 사람
	   String subject = "[PlanDing] "+userInfo.getName()+"님의 후원내역을 보내드립니다.";//제목
	   String content = cancelDonateMsgSelect;//내용
	   
	   
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
		   Session session = Session.getDefaultInstance(properties,//SMTP서버 정보
				   new Authenticator() {//사용자 인증 정보 객체 : Authenticator추상클래스 생성자() 정의와 동시에 객체생성
			   
			   //재정의해야 할 메서드 (추상클래스의 추상메서드) -> 추상메서드를 재정의했으므로 일반클래스가 되어 클래스객체 생성이 가능해짐
			   @Override
			   protected PasswordAuthentication getPasswordAuthentication() {
				   return new PasswordAuthentication(username, password);//"wjddnjs051339","앱비밀번호"
			   }//사용자인증정보 객체를 반환
			   
		   }//생성자 끝
		   
				   ); 
		   
		   //4. Message클래스의 객체를 사용하여 수신자, 제목, 내용을 작성
		   Message message = new MimeMessage(session); //사용자인증정보가 담긴 session객체로 Message객체 생성
		   
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
		   
		   System.out.println("sendMailCancelDonateSelect : 메일이 정상적으로 전송되었습니다.");//콘솔에 출력 : 메일전송 확인			
		   return true;
		   
	   }catch(Exception e) {
		   System.out.println("sendMailCancelDonateSelect : SMTP서버가 잘못 설정되었거나 서비스에 문제가 있습니다.");//콘솔에 출력 : 메일전송 확인
		   e.printStackTrace(); //콘솔에 출력 : 개발자가 에러를 좀더 찾기 쉽게
		   return false;
	   }   
	   
   }
   
   
   public boolean sendDonationAmount(MemberBean userInfo) {
	   
	   //1. 발신자의 메일계정과 비밀번호 등을 설정 (예: 구글계정 + 앱비밀번호)
	   String sender = "plandingproject@gmail.com";//보내는 사람
	   String receiver = userInfo.getEmail();//받는 사람
	   String subject = "[PlanDing] "+userInfo.getName()+"님의 후원내역을 보내드립니다.";//제목
	   String content = sendDonationAmountMsg;//내용
	   
	   
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
		   Session session = Session.getDefaultInstance(properties,//SMTP서버 정보
				   new Authenticator() {//사용자 인증 정보 객체 : Authenticator추상클래스 생성자() 정의와 동시에 객체생성
			   
			   //재정의해야 할 메서드 (추상클래스의 추상메서드) -> 추상메서드를 재정의했으므로 일반클래스가 되어 클래스객체 생성이 가능해짐
			   @Override
			   protected PasswordAuthentication getPasswordAuthentication() {
				   return new PasswordAuthentication(username, password);//"wjddnjs051339","앱비밀번호"
			   }//사용자인증정보 객체를 반환
			   
		   }//생성자 끝
		   
				   ); 
		   
		   //4. Message클래스의 객체를 사용하여 수신자, 제목, 내용을 작성
		   Message message = new MimeMessage(session); //사용자인증정보가 담긴 session객체로 Message객체 생성
		   
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
		   
		   System.out.println("sendDonationAmount : 메일이 정상적으로 전송되었습니다.");//콘솔에 출력 : 메일전송 확인			
		   return true;
		   
	   }catch(Exception e) {
		   System.out.println("sendDonationAmount : SMTP서버가 잘못 설정되었거나 서비스에 문제가 있습니다.");//콘솔에 출력 : 메일전송 확인
		   e.printStackTrace(); //콘솔에 출력 : 개발자가 에러를 좀더 찾기 쉽게
		   return false;
	   }   
	   
   }
   
   public boolean sendRefundDonation(MemberBean userInfo) {
	   
	   //1. 발신자의 메일계정과 비밀번호 등을 설정 (예: 구글계정 + 앱비밀번호)
	   String sender = "plandingproject@gmail.com";//보내는 사람
	   String receiver = userInfo.getEmail();//받는 사람
	   String subject = "[PlanDing] "+userInfo.getName()+"님의 후원내역을 보내드립니다.";//제목
	   String content = sendRefundDonationMsg;//내용
	   
	   
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
		   Session session = Session.getDefaultInstance(properties,//SMTP서버 정보
				   new Authenticator() {//사용자 인증 정보 객체 : Authenticator추상클래스 생성자() 정의와 동시에 객체생성
			   
			   //재정의해야 할 메서드 (추상클래스의 추상메서드) -> 추상메서드를 재정의했으므로 일반클래스가 되어 클래스객체 생성이 가능해짐
			   @Override
			   protected PasswordAuthentication getPasswordAuthentication() {
				   return new PasswordAuthentication(username, password);//"wjddnjs051339","앱비밀번호"
			   }//사용자인증정보 객체를 반환
			   
		   }//생성자 끝
		   
				   ); 
		   
		   //4. Message클래스의 객체를 사용하여 수신자, 제목, 내용을 작성
		   Message message = new MimeMessage(session); //사용자인증정보가 담긴 session객체로 Message객체 생성
		   
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
		   
		   System.out.println("sendDonationAmount : 메일이 정상적으로 전송되었습니다.");//콘솔에 출력 : 메일전송 확인			
		   return true;
		   
	   }catch(Exception e) {
		   System.out.println("sendDonationAmount : SMTP서버가 잘못 설정되었거나 서비스에 문제가 있습니다.");//콘솔에 출력 : 메일전송 확인
		   e.printStackTrace(); //콘솔에 출력 : 개발자가 에러를 좀더 찾기 쉽게
		   return false;
	   }   
	   
   }

}