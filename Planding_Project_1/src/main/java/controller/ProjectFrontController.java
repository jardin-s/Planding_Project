package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.project.RegisterNewDonateAction;
import action.project.RegisterNewFundAction;
import vo.ActionForward;

/**
 * Servlet implementation class DogFrontController
 */

//확장자가 usr이면 무조건 UserFrontController로 이동하여 doProcess(request, response); 실행
@WebServlet("*.pj")
public class ProjectFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectFrontController() {
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
		
		System.out.println("[Fund]command : "+command);//어떤 요청인지 확인하기 위해 콘솔에 출력
		
		/*-- '프로젝트 등록 페이지 이동' 요청 ------------------------------------------*/
		if(command.equals("/registerNewProject.pj")) {//'userMain.jsp'에서 프로젝트 등록하기 요청이면
			forward = new ActionForward("fund/registerNewProject.jsp", false);
			//false(디스패치) 이유? 
		}
		
		/*-- '기부 프로젝트 폼 보기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/fund/registerNewDonateForm.pj")) {//'기부 프로젝트 등록 폼 보기' 요청이면
			
			forward = new ActionForward("fund/registerNewDonateForm.jsp",false);//반드시 디스패치 (request를 공유)
					
		}
		
		else if(command.equals("/fund/registerNewDonateAction.pj")) {//'로그인 처리' 요청이면
			
			//부모인터페이스 = 구현한 자식객체
			action = new RegisterNewDonateAction();//부모인터페이스인 Action으로 받음 
		
			try {
				forward = action.execute(request, response); //DogListAction의 execute()를 실행
			} catch(Exception e) {
				e.printStackTrace();
			}
					
		}
		
		/*-- '펀딩 프로젝트 폼 보기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/fund/registerNewFundForm.pj")) {//'펀딩 프로젝트 등록 폼 보기' 요청이면
			
			forward = new ActionForward("fund/registerNewFundForm.jsp",false);//반드시 디스패치 (request를 공유)
					
		}
		
		else if(command.equals("/fund/registerNewFundAction.pj")) {//'로그인 처리' 요청이면
			
			//부모인터페이스 = 구현한 자식객체
			action = new RegisterNewFundAction();//부모인터페이스인 Action으로 받음 
			
			try {
				forward = action.execute(request, response); //DogListAction의 execute()를 실행
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
