package action.user;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.UserLoginService;
import util.SHA256;
import vo.ActionForward;
import vo.MemberBean;

public class UserLoginAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		//회원아이디, 비밀번호
		String u_id = request.getParameter("member_id");
		String u_password = request.getParameter("password");
		
		String checkbox = request.getParameter("checkbox");//'아이디저장' 체크여부
		
		//파라미터 전송값 확인
		System.out.println("[UserLoginAction]");
		System.out.println("u_id : "+u_id);
		System.out.println("u_password : "+u_password);
		System.out.println("checkbox : "+checkbox);
		
		//DTO=VO
		MemberBean user = new MemberBean();//기본값으로 채워짐
		user.setMember_id(u_id);;
		//user.setPassword(u_password);//암호화X
		user.setPassword(SHA256.encodeSHA256(u_password));//암호화O 방법-1
		//암호화O 방법-2 : MemberBean에서 처리 (매개변수로 받은 암호값을 MemberBean에서 단방향암호화하여 세팅)
		System.out.println("로그인 시 비밀번호 암호화 : "+SHA256.encodeSHA256(u_password));
		
		UserLoginService userLoginService = new UserLoginService();
		boolean isLoginSuccess = userLoginService.login(user);
		
		if(!isLoginSuccess) {//거짓 : 로그인 실패하면 (회원이 아니거나 잘못침)
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디나 비밀번호가 일치하지 않습니다.');");//알림창을 띄우고 로그인폼보기 요청으로 돌아감
			out.println("location.href='userLogin.usr'");		//(☆history.back()보다는 요청으로 할 것)
			out.println("</script>");
			
		}else {//참 : 로그인 성공하면 (회원가입 + 아이디와 비밀번호가 일치 맞음)
			
			//1. '아이디 저장' 체크 했으면 아이디를 쿠키영역에 저장해야 하므로 쿠키 생성
			Cookie cookieU_id = new Cookie("u_id",u_id);
			cookieU_id.setMaxAge(24*60*60);//24시간
			System.out.println("cookieU_id의 Cookie 객체 생성");
			
			//체크박스 체크여부를 저장
			//Cookie cookieCheckbox = new Cookie("checkbox","checked");
			//System.out.println("cookieCheckbox의 Cookie 객체 생성");
			
			if(checkbox != null) {//아이디 저장 체크 O
				response.addCookie(cookieU_id);//반드시 마지막에 response에 쿠키를 추가 (클라이언트쪽에 보내기 위해)
			}else {//아이디 저장 체크 X
				cookieU_id.setMaxAge(0);//쿠키를 즉시 삭제 (※-1 : 세션이 끝날 때 삭제)
				response.addCookie(cookieU_id);//반드시 마지막에 response에 쿠키를 추가
			}
			
			//2. 입력한 id로 회원정보를 가져와(이유? session 영역에 공유 - 모든 페이지에서 공유할 수 있음) 
			MemberBean userInfo = userLoginService.getUserInfo(u_id);
					
			/*3. 로그인에 성공하면 session영역에 속성으로 저장하여 모든 페이지에서 공유
			 * u_id, u_password, u_name, u_email, isAdmin
			 * (★password : 세션영역은 보안이 강함)  
			 */
			HttpSession session = request.getSession();//첫요청이면 세션영역생성, 첫요청아니면 기존세션 가져옴
			session.setAttribute("u_id", u_id); //session.setAttribute("u_id", u_id);
			session.setAttribute("u_password", u_password); //selectUserInfo()에서 비밀번호는 세팅안되어 기본값이 채워져있으므로, userInfo에서 가져오지 않고 입력한 값을 저장함
			
			session.setAttribute("u_name", userInfo.getName());			
			session.setAttribute("u_email", userInfo.getEmail());
			
			//isAdmin이 false이면 관리자페이지 접근제한
			session.setAttribute("isAdmin", userInfo.isAdmin());
			
			//세션 유지시간 (※세션은 해당 브라우저에서만 유효) (기본값 30분이 지나거나 창을 닫으면 세션영역이 사라짐) 
			session.setMaxInactiveInterval(1*60*60);//초단위 1시간 = 3600초	
			
			forward = new ActionForward("../index.jsp", true);//request에 저장한 게 없으므로 리다이렉션 포워딩
		}
		
		return forward;
	}

}
