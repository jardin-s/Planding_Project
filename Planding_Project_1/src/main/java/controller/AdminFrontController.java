package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.admin.AdminDeleteAction;
import action.admin.AdminHashPwChangeAction;
import action.admin.AdminHashPwFindAction;
import action.admin.AdminIdCheckAction;
import action.admin.AdminIdFindAction;
import action.admin.AdminJoinAction;
import action.admin.AdminLoginAction;
import action.admin.AdminLogoutAction;
import action.admin.AdminUpdateAction;
import action.admin.AdminUpdateFormAction;
import action.admin.notice.AdminNoticeListAction;
import action.admin.qna.AdminDeleteQnaAction;
import action.admin.qna.AdminInsertQnaAAction;
import action.admin.qna.AdminInsertQnaAFormAction;
import action.admin.qna.AdminModifyQnaAAction;
import action.admin.qna.AdminModifyQnaAFormAction;
import action.admin.qna.AdminQnaListAction;
import action.admin.qna.AdminQnaViewAction;
import vo.ActionForward;

/**
 * Servlet implementation class DogFrontController
 */

//확장자가 adm이면 무조건 AdminFrontController로 이동하여 doProcess(request, response); 실행
@WebServlet("*.adm")
public class AdminFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminFrontController() {
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
		
		System.out.println("[Admin]command : "+command);//어떤 요청인지 확인하기 위해 콘솔에 출력
		
		if(command.equals("/adminMain.adm")) {//'index.jsp'에서 사용자모드 뷰페이지(adminMain.jsp) 보기 요청이면
			request.setAttribute("showAdmin", "admin/adminMain.jsp");//어느 폼 보기인지 showAdmin이름 속성으로 저장
			forward = new ActionForward("adminTemplate.jsp",false);
		}
		
		/*-- '로그인 폼 보기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/adminLoginForm.adm")) {//'로그인 폼 보기' 요청이면
			
			request.setAttribute("showPage", "admin/account/loginForm.jsp");//어느 폼 보기인지 showAdmin이름 속성으로 저장
			forward = new ActionForward("adminLoginTemplate.jsp",false);
					
		}
		
		else if(command.equals("/adminLoginAction.adm")) {//'로그인 처리' 요청이면
			
			//부모인터페이스 = 구현한 자식객체
			action = new AdminLoginAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response); //DogListAction의 execute()를 실행
			} catch(Exception e) {
				e.printStackTrace();
			}
					
		}
		
		/*-- '로그아웃 하기' 요청 -> 처리 -------------------------------------*/
		else if(command.equals("/adminLogout.adm")) {//'로그아웃' 요청이면
			
			//부모인터페이스 = 구현한 자식객체
			action = new AdminLogoutAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}		
		}
					
		/*-- '회원가입 폼 보기' 요청 -> 처리 -------------------------------------*/
		else if(command.equals("/adminJoin.adm")) {//'회원가입 폼 보기' 요청이면
			
			request.setAttribute("showPage", "admin/account/joinForm.jsp");//어느 폼 보기인지 showAdmin이름 속성으로 저장
			forward = new ActionForward("adminLoginTemplate.jsp",false);//반드시 디스패치 (request를 공유)
					
		}
		
		else if(command.equals("/adminJoinAction.adm")) {//'회원가입 처리' 요청이면
			
			//부모인터페이스 = 구현한 자식객체
			action = new AdminJoinAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
					
		}
		
		else if(command.equals("/admin/account/idCheck/adminIdCheckAction.adm")) {//'아이디 중복체크 처리' 요청이면
			
			//부모인터페이스 = 구현한 자식객체
			action = new AdminIdCheckAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
					
		}
		
		/*-- '회원정보가 세팅된 회원정보관리 폼 보기(수정 폼)' 요청 -> 수정 처리 -------------------------------------*/
		else if(command.equals("/adminUpdateForm.adm")) {//'회원정보관리 보기' 요청이면

			//부모인터페이스 = 구현한 자식객체
			action = new AdminUpdateFormAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
						
		}
		
		else if(command.equals("/adminUpdateAction.adm")) {//'회원정보수정 처리' 요청
			
			//부모인터페이스 = 구현한 자식객체
			action = new AdminUpdateAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
					
		}
		
		else if(command.equals("/adminDelete.adm")) {//'회원탈퇴 처리' 요청
			
			//부모인터페이스 = 구현한 자식객체
			action = new AdminDeleteAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
					
		}
		else if(command.equals("/adminIdFindForm.adm")) {//'아이디 찾기 폼 보기' 요청
			
			request.setAttribute("showPage", "admin/account/adminIdFindForm.jsp");//어느 폼 보기인지 showAdmin이름 속성으로 저장
			forward = new ActionForward("adminLoginTemplate.jsp",false);//반드시 디스패치 (request를 공유)
					
		}
		else if(command.equals("/adminIdFindAction.adm")) {//'아이디 찾기 처리' 요청
			
			//부모인터페이스 = 구현한 자식객체
			action = new AdminIdFindAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
					
		}
		
		// 8/24 과제
		/*------- '암호화된 비밀번호찾기 폼 보기' → 처리(임시비밀번호 받아서 메일로 보내기) -------------------------------*/
		
		else if(command.equals("/adminHashPwFindForm.adm")) {//'비밀번호찾기 폼 보기' 요청이면
			request.setAttribute("showPage", "admin/account/hash/adminHashPwFindForm.jsp");
			forward = new ActionForward("adminLoginTemplate.jsp",false); //반드시 디스패치 방식으로 포워딩
		}
		else if(command.equals("/adminHashPwFindAction.adm")) {//'비밀번호 찾기 처리'요청하면
			//action:부모인터페이스 = AdminLoginAction:구현한자식객체;
			action = new AdminHashPwFindAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
		}
		
		/*------- '암호화된 비밀번호 변경 폼 보기' → 처리 -------------------------------*/
		else if(command.equals("/adminHashPwChangeForm.adm")) {//'비밀번호 변경 폼 보기' 요청이면
			request.setAttribute("showAdmin", "admin/hash/adminHashPwChangeForm.jsp");
			forward = new ActionForward("adminTemplate.jsp",false); //반드시 디스패치 방식으로 포워딩
		}
		else if(command.equals("/adminHashPwChangeAction.adm")) {//'비밀번호 변경 처리'요청하면
			//action:부모인터페이스 = AdminHashPwChangeAction:구현한자식객체;
			action = new AdminHashPwChangeAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
		}
		
		/*------- '관리자 마이페이지 보기' -------------------------------*/
		else if(command.equals("/adminMyPage.adm")) {//'비밀번호 변경 폼 보기' 요청이면
			request.setAttribute("showAdmin", "admin/myPage/adminMyPage.jsp");
			forward = new ActionForward("adminTemplate.jsp",false); //반드시 디스패치 방식으로 포워딩
		}
		
		
		/***************************************************************************************
		 * 문의사항 게시판
		 ***************************************************************************************/
		
		/*-- '문의글 목록 보기' 요청 --------------------------------------*/
		if(command.equals("/adminQnaList.adm")) {//

			//부모인터페이스 = 구현한 자식객체
			action = new AdminQnaListAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
 
		}
		

		/*-- '문의글 상세보기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/adminQnaView.adm")) {//'문의글 상세보기' 요청
			
			action = new AdminQnaViewAction();

			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
				
		/*-- '답변 쓰기 폼 보기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/adminInsertNewQnaAForm.adm")) {//'답변 쓰기 폼 보기' 요청
			
			action = new AdminInsertQnaAFormAction();

			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		else if(command.equals("/adminInsertQnaAAction.adm")) {//'답변 등록' 요청
			
			action = new AdminInsertQnaAAction();

			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		/*-- '답글 수정 폼 보기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/adminModifyQnaAForm.adm")) {//'답글 수정 폼 보기' 요청
			
			action = new AdminModifyQnaAFormAction();

			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		else if(command.equals("/adminModifyQnaAAction.adm")) {//'답글 수정하기' 요청
			
			action = new AdminModifyQnaAAction();

			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/*-- '문의글 삭제하기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/adminDeleteQnaAction.adm")) {//문의글 삭제하기 요청
			
			action = new AdminDeleteQnaAction();

			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/***************************************************************************************
		 * 공지사항 게시판
		 ***************************************************************************************/
		
		/*-- '공지글 목록 보기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/adminNoticeList.adm")) {//공지글 목록 보기 요청
			
			action = new AdminNoticeListAction();

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
				response.sendRedirect(forward.getPath());//"adminMain.jsp" response는 있지만, request가 없음 -> 공유하지 않음
			}else {//디스패치 : 기존request 공유
				request.getRequestDispatcher(forward.getPath()).forward(request, response);//이동할 때, request, response를 그대로 같이 보냄
			}
		}
		
	}

}
