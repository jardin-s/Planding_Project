package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.user.UserUploadProjectListAction;
import action.user.account.UserDeleteAction;
import action.user.account.UserHashPwChangeAction;
import action.user.account.UserHashPwFindAction;
import action.user.account.UserIdCheckAction;
import action.user.account.UserIdFindAction;
import action.user.account.UserJoinAction;
import action.user.account.UserLoginAction;
import action.user.account.UserLogoutAction;
import action.user.account.UserUpdateAction;
import action.user.account.UserUpdateFormAction;
import action.user.notice.UserNoticeListAction;
import action.user.notice.UserNoticeViewtAction;
import action.user.qna.SearchQnaListAction;
import action.user.qna.UserDeleteQnaAction;
import action.user.qna.UserDeleteQnaListAction;
import action.user.qna.UserInsertQnaQAction;
import action.user.qna.UserInsertQnaQFormAction;
import action.user.qna.UserModifyQnaQAction;
import action.user.qna.UserModifyQnaQFormAction;
import action.user.qna.UserMyQnaListAction;
import action.user.qna.UserMyQnaViewAction;
import action.user.qna.UserQnaListAction;
import action.user.qna.UserQnaViewAction;
import action.user.UserBookmarkDeleteAction;
import action.user.UserBookmarkListAction;
import action.user.UserDonatedProjectListAction;
import action.user.UserTopupMoneyAction;
import vo.ActionForward;

/**
 * Servlet implementation class DogFrontController
 */

//확장자가 usr이면 무조건 UserFrontController로 이동하여 doProcess(request, response); 실행
@WebServlet("*.usr")
public class UserFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserFrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	//doGet과 doPost메서드 안의 내용이 request의 UTF-8인코딩 빼고 다 똑같기 때문에 doProcess로 같이 처리
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");//★★반드시 첫 줄에서 처리(post방식) (get방식에서 해도 문제없음)
		
		/**
		 * 1. 전송된 요청 파악
		 * URL = "http://localhost:8090/project명/dogList.dog"
		 * URI = "/project명/dogList.dog"
		 * ContextPath = "/project명"
		 * 
		 * URI - ContextPath = "/dogList.dog"
		 * substring(contextPath.length())
		 */
		
		String requestURI = request.getRequestURI(); // "/project명/burgerList.urs" -> "burgerList.urs"만 부분문자열로 추출하기 위함
		String contextPath = request.getContextPath(); // "/project명"
		
		//command : 최종적으로 무슨 요청인지 파악
		String command = requestURI.substring(contextPath.length());// "/burgerList.urs"
		
		/***********************************************************
		 * 2. 각 요청별로 비지니스로직 호출하여 처리
		 ***********************************************************/
		Action action = null;
		ActionForward forward = null;
		
		System.out.println("[User]command : "+command);//어떤 요청인지 확인하기 위해 콘솔에 출력
		
		if(command.equals("/userMain.usr")) {//'index.jsp'에서 사용자모드 뷰페이지(userMain.jsp) 보기 요청이면
			request.setAttribute("showPage", "user/userMain.jsp");//어느 폼 보기인지 showPage이름 속성으로 저장
			forward = new ActionForward("userTemplate.jsp",false);
			//false(디스패치) 이유? 
		}
		
		/*-- '로그인 폼 보기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/userLoginForm.usr")) {//'로그인 폼 보기' 요청이면
			
			request.setAttribute("showPage", "user/account/loginForm.jsp");//어느 폼 보기인지 showPage이름 속성으로 저장
			forward = new ActionForward("userTemplate.jsp",false);//반드시 디스패치 (request를 공유)
					
		}
		
		else if(command.equals("/userLoginAction.usr")) {//'로그인 처리' 요청이면
			
			//부모인터페이스 = 구현한 자식객체
			action = new UserLoginAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
					
		}
		
		/*-- '로그아웃 하기' 요청 -> 처리 -------------------------------------*/
		else if(command.equals("/userLogout.usr")) {//'로그아웃' 요청이면
			
			//부모인터페이스 = 구현한 자식객체
			action = new UserLogoutAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}		
		}
					
		/*-- '회원가입 폼 보기' 요청 -> 처리 -------------------------------------*/
		else if(command.equals("/userJoinForm.usr")) {//'회원가입 폼 보기' 요청이면
			
			request.setAttribute("showPage", "user/account/joinForm.jsp");//어느 폼 보기인지 showPage이름 속성으로 저장
			forward = new ActionForward("userTemplate.jsp",false);//반드시 디스패치 (request를 공유)
					
		}
		
		else if(command.equals("/userJoinAction.usr")) {//'회원가입 처리' 요청이면
			
			//부모인터페이스 = 구현한 자식객체
			action = new UserJoinAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
					
		}
		
		else if(command.equals("/user/account/idCheck/userIdCheckAction.usr")) {//'아이디 중복체크 처리' 요청이면
			
			//부모인터페이스 = 구현한 자식객체
			action = new UserIdCheckAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
					
		}
		
		/*-- '회원정보가 세팅된 회원정보관리 폼 보기(수정 폼)' 요청 -> 수정 처리 -------------------------------------*/
		else if(command.equals("/userUpdateForm.usr")) {//'회원정보관리 보기' 요청이면

			//부모인터페이스 = 구현한 자식객체
			action = new UserUpdateFormAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
						
		}
		
		else if(command.equals("/userUpdateAction.usr")) {//'회원정보수정 처리' 요청
			
			//부모인터페이스 = 구현한 자식객체
			action = new UserUpdateAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
					
		}
		
		/*-- '회원탈퇴 폼 보기' 요청 -> 수정 처리 -------------------------------------*/
		else if(command.equals("/userDeleteForm.usr")) {//'회원탈퇴 폼 보기' 요청
			
			//부모인터페이스 = 구현한 자식객체
			request.setAttribute("showPage", "user/account/userDeleteForm.jsp");
			forward = new ActionForward();
					
		}
		else if(command.equals("/userDeleteAction.usr")) {//'회원탈퇴 처리' 요청
			
			//부모인터페이스 = 구현한 자식객체
			action = new UserDeleteAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
					
		}
		else if(command.equals("/userIdFindForm.usr")) {//'아이디 찾기 폼 보기' 요청
			
			request.setAttribute("showPage", "user/account/userIdFindForm.jsp");//어느 폼 보기인지 showPage이름 속성으로 저장
			forward = new ActionForward("userTemplate.jsp",false);//반드시 디스패치 (request를 공유)
					
		}
		else if(command.equals("/userIdFindAction.usr")) {//'아이디 찾기 처리' 요청
			
			//부모인터페이스 = 구현한 자식객체
			action = new UserIdFindAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
					
		}
		
		// 8/24 과제
		/*------- '암호화된 비밀번호찾기 폼 보기' → 처리(임시비밀번호 받아서 메일로 보내기) -------------------------------*/
		
		else if(command.equals("/userHashPwFindForm.usr")) {//'비밀번호찾기 폼 보기' 요청이면
			request.setAttribute("showPage", "user/account/hash/userHashPwFindForm.jsp");
			forward = new ActionForward("userTemplate.jsp",false); //반드시 디스패치 방식으로 포워딩
		}
		else if(command.equals("/userHashPwFindAction.usr")) {//'비밀번호 찾기 처리'요청하면
			//action:부모인터페이스 = UserLoginAction:구현한자식객체;
			action = new UserHashPwFindAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
		}
		
		/*------- '암호화된 비밀번호 변경 폼 보기' → 처리 -------------------------------*/
		else if(command.equals("/userHashPwChangeForm.usr")) {//'비밀번호 변경 폼 보기' 요청이면
			request.setAttribute("showPage", "user/account/hash/userHashPwChangeForm.jsp");
			forward = new ActionForward("userTemplate.jsp",false); //반드시 디스패치 방식으로 포워딩
		}
		else if(command.equals("/userHashPwChangeAction.usr")) {//'비밀번호 변경 처리'요청하면
			//action:부모인터페이스 = UserHashPwChangeAction:구현한자식객체;
			action = new UserHashPwChangeAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
		}
		
		/***************************************************************************************
		 * 마이페이지
		 ***************************************************************************************/
				
		/*------- '사용자 마이페이지 보기' -------------------------------*/
		else if(command.equals("/userMyPage.usr")) {//'비밀번호 변경 폼 보기' 요청이면
			request.setAttribute("showPage", "user/myPage/userMyPage.jsp");
			forward = new ActionForward("userTemplate.jsp",false); //반드시 디스패치 방식으로 포워딩
		}
		
		/*------- '관심 프로젝트 목록 보기' -------------------------------*/
		else if(command.equals("/userBookmarkList.usr")) {//'관심 프로젝트 목록 보기' 요청이면
			//action:부모인터페이스 = UserHashPwChangeAction:구현한자식객체;
			action = new UserBookmarkListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
		}
		
		/*------- '등록한 프로젝트 목록 보기' -------------------------------*/
		else if(command.equals("/userUploadProjectList.usr")) {//'등록한 프로젝트 목록 보기' 요청이면
			//action:부모인터페이스 = UserHashPwChangeAction:구현한자식객체;
			action = new UserUploadProjectListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
		}
		
		/*------- '후원한 프로젝트 목록 보기' -------------------------------*/
		else if(command.equals("/userDonatedProjectList.usr")) {//'후원한 프로젝트 목록 보기' 요청이면
			//action:부모인터페이스 = UserHashPwChangeAction:구현한자식객체;
			action = new UserDonatedProjectListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
		}
		
		/*------- '선택한 관심 프로젝트 삭제하기' -------------------------------*/
		else if(command.equals("/userBookmarkDelete.usr")) {//'관심프로젝트 삭제' 요청이면
			//action:부모인터페이스 = UserHashPwChangeAction:구현한자식객체;
			action = new UserBookmarkDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
		}
		
		/*------- '나의 문의글 목록 보기' -------------------------------*/
		else if(command.equals("/userMyQnaList.usr")) {//'나의 문의글 목록 보기' 요청이면
			//action:부모인터페이스 = UserHashPwChangeAction:구현한자식객체;
			action = new UserMyQnaListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
		}	
		
		/*------- '내 계좌 금액 충전하기' -------------------------------*/
		else if(command.equals("/user/myPage/topUp/userTopupMoney.usr")) {//'내 계좌 금액 충전하기' 요청이면
			//action:부모인터페이스 = UserHashPwChangeAction:구현한자식객체;
			action = new UserTopupMoneyAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
		}	
		
		
		/***************************************************************************************
		 * 문의사항 게시판
		 ***************************************************************************************/
		
		/*-- '문의글 목록 보기' 요청 --------------------------------------*/
		if(command.equals("/userQnaList.usr")) {//

			//부모인터페이스 = 구현한 자식객체
			action = new UserQnaListAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
 
		}
		/*-- '검색 문의글 목록 보기' 요청 --------------------------------------*/
		if(command.equals("/searchQnaList.usr")) {//
			
			//부모인터페이스 = 구현한 자식객체
			action = new SearchQnaListAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
				
		/*-- '문의글 쓰기 폼 보기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/userInsertQnaQForm.usr")) {//'문의글 쓰기 폼 보기' 요청
			
			action = new UserInsertQnaQFormAction();

			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		else if(command.equals("/userInsertQnaQAction.usr")) {//'문의글 등록' 요청
			
			action = new UserInsertQnaQAction();

			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/*-- '문의글 상세보기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/userQnaView.usr")) {//'문의글 상세보기' 요청
			
			action = new UserQnaViewAction();

			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
				
		/*-- '문의글 수정 폼 보기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/userModifyQnaQForm.usr")) {//'문의글 수정 폼 보기' 요청
			
			action = new UserModifyQnaQFormAction();

			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		else if(command.equals("/userModifyQnaQAction.usr")) {//'문의글 수정하기' 요청
			
			action = new UserModifyQnaQAction();

			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/*-- '문의글 삭제하기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/userDeleteQnaAction.usr")) {//문의글 삭제하기 요청
			
			action = new UserDeleteQnaAction();

			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/*-- '내가 작성한 문의글 상세보기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/userMyQnaView.usr")) {//여러 문의글 삭제하기 요청
			
			action = new UserMyQnaViewAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/*-- '내가 작성한 문의글 여러 개 삭제하기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/userDeleteQnaList.usr")) {//여러 문의글 삭제하기 요청
			
			action = new UserDeleteQnaListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/***************************************************************************************
		 * 공지사항 게시판
		 ***************************************************************************************/
		/*-- [사용자모드] '공지사항 글 목록 보기' 요청 --------------------------------------*/
		else if(command.equals("/userNoticeList.usr")) {//'공지사항 글 목록 보기' 요청
			
			action = new UserNoticeListAction();

			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		/*-- [사용자모드] '공지사항 글 상세 보기' 요청 --------------------------------------*/
		else if(command.equals("/userNoticeView.usr")) {//'공지사항 글 보기' 요청
			
			action = new UserNoticeViewtAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}

		
		
		/***********************************************************
		 * 3. 포워딩
		 * *********************************************************/
		if(forward != null) {
			if(forward.isRedirect()) {//isRedirect==false(디스패치), ==true(리다이렉트) 
				response.sendRedirect(forward.getPath());//"userMain.jsp" response는 있지만, request가 없음 -> 공유하지 않음
			}else {//디스패치 : 기존request 공유
				request.getRequestDispatcher(forward.getPath()).forward(request, response);//이동할 때, request, response를 그대로 같이 보냄
			}
		}
		
	}

}
