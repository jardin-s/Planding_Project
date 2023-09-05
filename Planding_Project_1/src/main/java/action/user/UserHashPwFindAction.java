package action.user;

import java.io.PrintWriter;
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

import action.Action;
import svc.user.UserHashPwFindService;
import util.SHA256;
import vo.ActionForward;
import vo.MemberBean;

public class UserHashPwFindAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		String u_id = request.getParameter("member_id");
		String u_email = request.getParameter("email");
		
		System.out.println("[UserHashPwFindAction]");
		System.out.println("파라미터로 넘어온 member_id = "+u_id);
		System.out.println("파라미터로 넘어온 email = "+u_email);
		
		UserHashPwFindService userHashPwFindService = new UserHashPwFindService();
		MemberBean userInfo = userHashPwFindService.findHashPw(u_id, u_email);
		
		if(userInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디 또는 이메일이 일치하지 않습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			//8자리 임시비밀번호 생성 -> 다시 암호화시켜 DB에 저장
			String random_password =SHA256.getRandomPassword(8);
			System.out.println("[UserHashPwFindAction] 생성된 임시비밀번호 random_password = "+random_password);
			
			boolean isSetHashPwSuccess = userHashPwFindService.setHashPw(u_id, u_email, random_password);
			
			if(!isSetHashPwSuccess) { //비밀번호 재설정에 실패할 경우
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('임시 비밀번호 발급에 문제가 발생했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			
			}else { //비밀번호 재설정에 성공할 경우
				
				
				//1. 이메일로 임시비밀번호를 전송 ----------------------------------------------------
				
				/* 구글의 기본적인 환경 정리가 끝난 후 JavaMail을 이용하여 메일 보내기 위한 5단계
				 * 1. 발신자의 메일계정과 비밀번호 등을 설정 (예: 구글계정 + 앱비밀번호)
				 * 2. Property에 SMTP서버 정보를 설정(예: 구글의 SMTP서버 정보를 설정)
				 * 	  ※SMTP(Simple Mail Transfer Protocol) : 메일 전송 프로토콜. 대부분의 메일서버는 SMTP서버를 거쳐 메일을 전송.
				 * 3. SMTP서버 정보와 사용자 정보를 기본으로 javax.mail.Session 객체 생성
				 * 4. Message클래스의 객체를 사용하여 수신자, 제목, 내용을 작성
				 * 5. Transport 클래스를 사용하여 작성한 Message 객체를 수신자에게 전달 
				 */
				
				//요청객체와 응답객체 인코딩 설정
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				
				//★주의 : 서블릿은 반드시 출력객체를 직접 생성해야 함 (JSP는 아래 코드 생략)
				PrintWriter out = response.getWriter();
				
				//1. 발신자의 메일계정과 비밀번호 등을 설정 (예: 구글계정 + 앱비밀번호)
				String sender = "admin@mac.com";//보내는 사람
				String receiver = u_email;//받는 사람
				String subject = "임시비밀번호 발급";//제목
				String content = "임시비밀번호 8자리 : "+random_password;//내용
						
				
				final String host = "smtp.gmail.com"; //SMTP서버주소 (구글로 이메일 전송). 만약 보내는쪽이 네이버 "smtp.naver.com"
				final String username = "wjddnjs051339"; //구글 아이디 입력
				final String password = "gvbyothmkbtbtlbr"; //구글 아이디 입력
				
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
					
					System.out.println("UserPwFindAction : 메일이 정상적으로 전송되었습니다.");//콘솔에 출력 : 메일전송 확인			
					
					
				}catch(Exception e) {
					System.out.println("UserPwFindAction : SMTP서버가 잘못 설정되었거나 서비스에 문제가 있습니다.");//콘솔에 출력 : 메일전송 확인
					e.printStackTrace(); //콘솔에 출력 : 개발자가 에러를 좀더 찾기 쉽게
				}
				
				
				//2. 화면에 임시 비밀번호를 뿌리기 위해 request 속성값으로 저장 ----------------------------------------------------
				request.setAttribute("random_password", random_password);
				request.setAttribute("u_email", u_email);
				
				//임시비밀번호를 화면에 뿌림
				request.setAttribute("showPage", "user/account/hash/findHashPwComplete.jsp");
				forward = new ActionForward("userTemplate.jsp",false);
					
			}
		
		
		}
		
		return forward;
		
	}

}
