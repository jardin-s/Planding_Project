package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.user.UserUploadProjectListAction;
import action.user.qna.UserDeleteQnaAction;
import action.user.qna.UserInsertNewQnaQAction;
import action.user.qna.UserInsertNewQnaQFormAction;
import action.user.qna.UserModifyQnaQAction;
import action.user.qna.UserModifyQnaQFormAction;
import action.user.qna.UserQnaListAction;
import action.user.qna.UserQnaViewAction;
import action.user.UserBookmarkListAction;
import action.user.UserDeleteAction;
import action.user.UserFundProjectListAction;
import action.user.UserHashPwChangeAction;
import action.user.UserIdCheckAction;
import action.user.UserIdFindAction;
import action.user.UserJoinAction;
import action.user.UserLoginAction;
import action.user.UserLogoutAction;
import action.user.UserHashPwFindAction;
import action.user.UserUpdateAction;
import action.user.UserUpdateFormAction;
import vo.ActionForward;

/**
 * Servlet implementation class DogFrontController
 */

//확장자가 qna이면 무조건 UserFrontController로 이동하여 doProcess(request, response); 실행
@WebServlet("*.qna")
public class QnaFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QnaFrontController() {
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
		
		
		/* [사용자모드] *****************************************************/
		
		if(command.equals("/qnaList.qna")) {//'index.jsp'에서 사용자모드 뷰페이지(userMain.jsp) 보기 요청이면

			//부모인터페이스 = 구현한 자식객체
			action = new UserQnaListAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response); //DogListAction의 execute()를 실행
			} catch(Exception e) {
				e.printStackTrace();
			}
 
		}
				
		/*-- '문의글 쓰기 폼 보기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/insertNewQnaQForm.qna")) {//문의글 쓰기 폼 보기 요청
			
			action = new UserInsertNewQnaQFormAction();

			try {
				forward = action.execute(request, response); //DogListAction의 execute()를 실행
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		else if(command.equals("/insertNewQnaQAction.qna")) {//문의글 등록 요청
			
			action = new UserInsertNewQnaQAction();

			try {
				forward = action.execute(request, response); //DogListAction의 execute()를 실행
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/*-- '문의글 보기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/qnaView.qna")) {//문의글 쓰기 폼 보기 요청
			
			action = new UserQnaViewAction();

			try {
				forward = action.execute(request, response); //DogListAction의 execute()를 실행
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/*-- '문의글 수정 폼 보기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/modifyQnaQForm.qna")) {//문의글 수정 폼 보기 요청
			
			action = new UserModifyQnaQFormAction();

			try {
				forward = action.execute(request, response); //DogListAction의 execute()를 실행
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		else if(command.equals("/modifyQnaQAction.qna")) {//문의글 수정하기 요청
			
			action = new UserModifyQnaQAction();

			try {
				forward = action.execute(request, response); //DogListAction의 execute()를 실행
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/*-- '문의글 삭제하기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/deleteQnaAction.qna")) {//문의글 삭제하기 요청
			
			action = new UserDeleteQnaAction();

			try {
				forward = action.execute(request, response); //DogListAction의 execute()를 실행
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		/* [관리자모드] *****************************************************/
		
		
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
