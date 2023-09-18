package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import action.project.InsertNewProjectFormAction;
import action.project.InsertProjectPlannerAction;
import action.project.InsertDonateProjectTempAction;
import action.project.InsertFundProjectRewardFormAction;
import action.project.InsertFundProjectTempAction;
import action.project.SubmitDonateProjectAction;
import action.project.SubmitFundProjectAction;
import action.project.UserDonateProjectListAction;
import action.project.UserProjectViewAction;
import vo.ActionForward;

/**
 * Servlet implementation class DogFrontController
 */

//확장자가 pj이면 무조건 ProjectFrontController로 이동하여 doProcess(request, response); 실행
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
		
		System.out.println("[Project]command : "+command);//어떤 요청인지 확인하기 위해 콘솔에 출력
		
		/*-- '프로젝트 등록 페이지 이동' 요청 (기부/펀딩 선택) ------------------------------------------*/
		if(command.equals("/insertNewProject.pj")) {
			action = new InsertNewProjectFormAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		/*-- '프로젝트 등록 폼' 요청 -> 처리 --------------------------------------*/
		
		/*-- '기부 프로젝트 등록 폼' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/donateProjectInsert.pj")) {//'프로젝트 등록 폼 보기' 요청이면
			//HttpSession session = request.getSession();
			//session.setAttribute("kind", "donate");//kind를 donate로 설정
			
			request.setAttribute("kind", "donate");
			request.setAttribute("showPage", "project/insertProjectPlannerForm.jsp");//어느 폼 보기인지 showPage이름 속성으로 저장
			forward = new ActionForward("userTemplate.jsp",false);//반드시 디스패치 (request를 공유)
		}
		
		/*-- '펀딩 프로젝트 등록 폼' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/fundProjectInsert.pj")) {//'프로젝트 등록 폼 보기' 요청이면
			//HttpSession session = request.getSession();
			//session.setAttribute("kind", "fund");//kind를 fund로 설정
			
			request.setAttribute("kind", "fund");
			request.setAttribute("showPage", "project/insertProjectPlannerForm.jsp");//어느 폼 보기인지 showPage이름 속성으로 저장
			forward = new ActionForward("userTemplate.jsp",false);//반드시 디스패치 (request를 공유)
		}
		
		/*-- '프로젝트 기획자 등록하기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/insertProjectPlanner.pj")) {//'프로젝트 등록 폼 보기' 요청이면
			action = new InsertProjectPlannerAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		/*-- '리워드 입력 폼 보기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/insertFundProjectRewardForm.pj")) {//'프로젝트 등록 폼 보기' 요청이면
			action = new InsertFundProjectRewardFormAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		/*-- '프로젝트 등록 폼에서 이전단계로' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/insertProjectContentsBack.pj")) {
			request.setAttribute("showPage", "project/insertProjectContentForm.jsp?kind=fund");//어느 폼 보기인지 showPage이름 속성으로 저장
			forward = new ActionForward("userTemplate.jsp",false);//반드시 디스패치 (request를 공유)
		}
		
		/*-- '펀딩 프로젝트의 리워드 등록하기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/insertFundProjectReward.pj")) {//'프로젝트 등록 폼 보기' 요청이면
			request.setAttribute("showPage", "project/insertProjectReward.jsp");//어느 폼 보기인지 showPage이름 속성으로 저장
			forward = new ActionForward("userTemplate.jsp",false);//반드시 디스패치 (request를 공유)
		}
		
		/*-- '기부 프로젝트 미리보기 요청' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/insertDonateProjectTemp.pj")) {
			action = new InsertDonateProjectTempAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		/*-- '펀딩 프로젝트 미리보기 요청' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/insertFundProjectTemp.pj")) {
			action = new InsertFundProjectTempAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
				
		/*-- '기부 프로젝트를 실제로 등록' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/submitDonateProjectAction.pj")) {
			action = new SubmitDonateProjectAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		/*-- '펀딩 프로젝트를 실제로 등록' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/submitFundProjectAction.pj")) {
			action = new SubmitFundProjectAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		/*-- '[사용자] 프로젝트 상세보기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/userProjectView.pj")) {
			action = new UserProjectViewAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		/*-- '[사용자] 기부 프로젝트 목록 보기' 요청 -> 처리 --------------------------------------*/
		else if(command.equals("/userDonateProjectList.pj")) {
			action = new UserDonateProjectListAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
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
