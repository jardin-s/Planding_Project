package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.admin.manageProject.AuthorizeProjectAction;
import action.admin.manageProject.CancelProjectAction;
import action.admin.manageProject.CancelProjectFormAction;
import action.admin.manageProject.UnauthorizeProjectAction;
import action.admin.manageProject.donate.AuthDonateProjectListAction;
import action.admin.manageProject.donate.DoneDonateProjectListAction;
import action.admin.manageProject.donate.ManageDonateProjectListAction;
import action.admin.manageProject.donate.OrderAuthDonateProjectListAction;
import action.admin.manageProject.donate.OrderDonateProjectListAction;
import action.admin.manageProject.donate.OrderDoneDonateProjectListAction;
import action.admin.manageProject.donate.OrderUnauthDonateProjectListAction;
import action.admin.manageProject.donate.SearchAuthDonateProjectListAction;
import action.admin.manageProject.donate.SearchDonateProjectListAction;
import action.admin.manageProject.donate.SearchDoneDonateProjectListAction;
import action.admin.manageProject.donate.SearchUnauthDonateProjectListAction;
import action.admin.manageProject.donate.UnauthDonateProjectListAction;
import action.admin.manageProject.fund.AuthFundProjectListAction;
import action.admin.manageProject.fund.DoneFundProjectListAction;
import action.admin.manageProject.fund.ManageFundProjectListAction;
import action.admin.manageProject.fund.OrderAuthFundProjectListAction;
import action.admin.manageProject.fund.OrderDoneFundProjectListAction;
import action.admin.manageProject.fund.OrderFundProjectListAction;
import action.admin.manageProject.fund.OrderUnauthFundProjectListAction;
import action.admin.manageProject.fund.SearchAuthFundProjectListAction;
import action.admin.manageProject.fund.SearchDoneFundProjectListAction;
import action.admin.manageProject.fund.SearchFundProjectListAction;
import action.admin.manageProject.fund.SearchUnauthFundProjectListAction;
import action.admin.manageProject.fund.UnauthFundProjectListAction;
import vo.ActionForward;

/**
 * Servlet implementation class DogFrontController
 */

//확장자가 adm이면 무조건 AdminFrontController로 이동하여 doProcess(request, response); 실행
@WebServlet("*.mngp")
public class MngProjectFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MngProjectFrontController() {
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
		
		System.out.println("[MngProject]command : "+command);//어떤 요청인지 확인하기 위해 콘솔에 출력
			
		
		/*******************************************************************************************
		   기부 프로젝트 목록
		 ******************************************************************************************/
		
		/*-- '기부 프로젝트 관리 페이지 보기 (전체 기부프로젝트 목록) ' 요청 --------------------------------------*/
		if(command.equals("/manageDonateProjectList.mngp")) {
			
			action = new ManageDonateProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		/*-- '정렬기준으로 정렬된 기부 프로젝트 관리 페이지 보기 (전체 기부프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/orderDonateProjectList.mngp")) {
			
			action = new OrderDonateProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		/*-- '검색조건에 따른 기부 프로젝트 관리 페이지 보기 (전체 기부프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/searchDonateProjectList.mngp")) {
			
			action = new SearchDonateProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/*-- 미승인 기부 프로젝트 목록 -------------------------------------------------------------------------*/
		
		/*-- '미승인 기부 프로젝트 관리 페이지 보기 (미승인 기부프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/unauthDonateProjectList.mngp")) {
			
			action = new UnauthDonateProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		/*-- '정렬기준으로 정렬된 미승인 기부 프로젝트 관리 페이지 보기 (미승인 기부프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/orderUnauthDonateProjectList.mngp")) {
			
			action = new OrderUnauthDonateProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		/*-- '검색조건에 따른 미승인 기부 프로젝트 관리 페이지 보기 (미승인 기부프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/searchUnauthDonateProjectList.mngp")) {
			
			action = new SearchUnauthDonateProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		/*-- 승인된 기부 프로젝트 목록 (진행중) -------------------------------------------------------------------------*/
		
		/*-- '승인된 기부 프로젝트 관리 페이지 보기 (승인된 기부프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/authDonateProjectList.mngp")) {
			
			action = new AuthDonateProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		/*-- '정렬기준으로 정렬된 승인된 기부 프로젝트 관리 페이지 보기 (승인된 기부프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/orderAuthDonateProjectList.mngp")) {
			
			action = new OrderAuthDonateProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		/*-- '검색조건에 따른 승인된 기부 프로젝트 관리 페이지 보기 (승인된 기부프로젝트 목록) ' 요청 --------------------------------------*/
		if(command.equals("/searchAuthDonateProjectList.mngp")) {
			
			action = new SearchAuthDonateProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		/*-- 종료된 기부 프로젝트 목록 (종료/성공) -------------------------------------------------------------------------*/
		
		/*-- '종료된 기부 프로젝트 관리 페이지 보기 (종료된 기부프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/doneDonateProjectList.mngp")) {
			
			action = new DoneDonateProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		/*-- '정렬기준으로 정렬된 종료된 기부 프로젝트 관리 페이지 보기 (종료된 기부프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/orderDoneDonateProjectList.mngp")) {
			
			action = new OrderDoneDonateProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		/*-- '검색조건에 따른 종료된 기부 프로젝트 관리 페이지 보기 (종료된 기부프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/searchDoneDonateProjectList.mngp")) {
			
			action = new SearchDoneDonateProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		/*******************************************************************************************
		   펀딩 프로젝트 목록
		 *******************************************************************************************/
		
		/*-- '펀딩 프로젝트 관리 페이지 보기 (전체 펀딩프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/manageFundProjectList.mngp")) {
			
			action = new ManageFundProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		/*-- '정렬기준으로 정렬된 펀딩 프로젝트 관리 페이지 보기 (전체 펀딩프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/orderFundProjectList.mngp")) {
			
			action = new OrderFundProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		/*-- '검색조건에 따른 펀딩 프로젝트 관리 페이지 보기 (전체 펀딩프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/searchFundProjectList.mngp")) {
			
			action = new SearchFundProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/*-- 미승인 펀딩 프로젝트 목록 -------------------------------------------------------------------------*/
		
		/*-- '미승인 펀딩 프로젝트 관리 페이지 보기 (미승인 펀딩프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/unauthFundProjectList.mngp")) {
			
			action = new UnauthFundProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		/*-- '정렬기준으로 정렬된 미승인 기부 프로젝트 관리 페이지 보기 (미승인 펀딩프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/orderUnauthFundProjectList.mngp")) {
			
			action = new OrderUnauthFundProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		/*-- '검색조건에 따른 미승인 기부 프로젝트 관리 페이지 보기 (미승인 펀딩프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/searchUnauthFundProjectList.mngp")) {
			
			action = new SearchUnauthFundProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		/*-- 승인된 펀딩 프로젝트 목록 (진행중) -------------------------------------------------------------------------*/
		
		/*-- '승인된 펀딩 프로젝트 관리 페이지 보기 (승인된 펀딩프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/authFundProjectList.mngp")) {
			
			action = new AuthFundProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		/*-- '정렬기준으로 정렬된 승인된 펀딩 프로젝트 관리 페이지 보기 (승인된 펀딩프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/orderAuthFundProjectList.mngp")) {
			
			action = new OrderAuthFundProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		/*-- '검색조건에 따른 승인된 펀딩 프로젝트 관리 페이지 보기 (승인된 펀딩프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/searchAuthFundProjectList.mngp")) {
			
			action = new SearchAuthFundProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		/*-- 종료된 펀딩 프로젝트 목록 (종료/성공) -------------------------------------------------------------------------*/
		
		/*-- '종료된 펀딩 프로젝트 관리 페이지 보기 (종료된 펀딩프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/doneFundProjectList.mngp")) {
			
			action = new DoneFundProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		/*-- '정렬기준으로 정렬된 종료된 펀딩 프로젝트 관리 페이지 보기 (종료된 펀딩프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/orderDoneFundProjectList.mngp")) {
			
			action = new OrderDoneFundProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		/*-- '검색조건에 따른 종료된 펀딩 프로젝트 관리 페이지 보기 (종료된 펀딩프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/searchDoneFundProjectList.mngp")) {
			
			action = new SearchDoneFundProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		/*-- '미승인 프로젝트를 승인하기' 요청 --------------------------------------*/
		else if(command.equals("/authorizeProject.mngp")) {
			
			action = new AuthorizeProjectAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/*-- '미승인 프로젝트를 승인거부하기' 요청 --------------------------------------*/
		else if(command.equals("/unauthorizeProject.mngp")) {
			
			action = new UnauthorizeProjectAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/*-- '진행 중인 프로젝트를 진행 취소 폼 보기' 요청 --------------------------------------*/
		else if(command.equals("/cancelProjectForm.mngp")) {
			
			action = new CancelProjectFormAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		/*-- '진행 중인 프로젝트를 진행 취소 폼 보기' 요청 --------------------------------------*/
		else if(command.equals("/cancelProjectAction.mngp")) {
			
			action = new CancelProjectAction();
			
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
