package util;

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
import vo.MemberBean;
import vo.ProjectBean;
import vo.RewardBean;

 

public class SendMail {
   
   private String donateSuccessMsgDefault;
   private String donateSuccessMsgSelect;
   
   public void setDonateSuccessMsgDefault(ProjectBean projectInfo, RewardBean rewardInfo, MemberBean userInfo, int add_donation) {
	   
	   String deadline = null;
	   if(projectInfo.getDeadline() == 0) {
		   deadline = "오늘 마감";
	   }else {
		   deadline = projectInfo.getDeadline()+"일 남음";
	   }
	   
	   StringBuffer sb = new StringBuffer();
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
	   
	   
	   donateSuccessMsgDefault = sb.toString();
   }
   
   public void setDonateSuccessMsgSelect(ProjectBean projectInfo, RewardBean rewardInfo, MemberBean userInfo, int add_donation, AddressBean addressInfo) {
	   
	   String deadline = null;
	   if(projectInfo.getDeadline() == 0) {
		   deadline = "오늘 마감";
	   }else {
		   deadline = projectInfo.getDeadline()+"일 남음";
	   }
	   
	   StringBuffer sb = new StringBuffer();
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
	   sb.append("<br>&nbsp;우편번호 : ");
	   sb.append(addressInfo.getPostcode());
	   sb.append("<br>&nbsp;기본주소 : ");
	   sb.append(addressInfo.getAddress1());
	   sb.append("<br>&nbsp;상세주소 : ");
	   sb.append(addressInfo.getAddress2());
	   sb.append("<br></p></div></div>");
	  
	   donateSuccessMsgSelect = sb.toString();
	   
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
			
			System.out.println("UserDonateSuccessDefault : 메일이 정상적으로 전송되었습니다.");//콘솔에 출력 : 메일전송 확인			
			return true;
			
		}catch(Exception e) {
			System.out.println("UserDonateSuccessDefault : SMTP서버가 잘못 설정되었거나 서비스에 문제가 있습니다.");//콘솔에 출력 : 메일전송 확인
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
		   
		   System.out.println("UserDonateSuccessSelect : 메일이 정상적으로 전송되었습니다.");//콘솔에 출력 : 메일전송 확인			
		   return true;
		   
	   }catch(Exception e) {
		   System.out.println("UserDonateSuccessSelect : SMTP서버가 잘못 설정되었거나 서비스에 문제가 있습니다.");//콘솔에 출력 : 메일전송 확인
		   e.printStackTrace(); //콘솔에 출력 : 개발자가 에러를 좀더 찾기 쉽게
		   return false;
	   }   
	   
   }

}