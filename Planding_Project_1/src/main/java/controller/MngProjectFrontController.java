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
import action.admin.manageProject.ManageProjectViewAction;
import action.admin.manageProject.RefundAllDonationAction;
import action.admin.manageProject.SendTotalAmountAction;
import action.admin.manageProject.UnauthorizeProjectAction;
import action.admin.manageProject.donate.ManageDonateProjectListAction;
import action.admin.manageProject.donate.OngoingDonateProjectListAction;
import action.admin.manageProject.donate.ReadyDonateProjectListAction;
import action.admin.manageProject.donate.SuccessDonateProjectListAction;
import action.admin.manageProject.donate.UnauthDonateProjectListAction;
import action.admin.manageProject.fund.DoneFundProjectListAction;
import action.admin.manageProject.fund.ManageFundProjectListAction;
import action.admin.manageProject.fund.OngoingFundProjectListAction;
import action.admin.manageProject.fund.ReadyFundProjectListAction;
import action.admin.manageProject.fund.SuccessFundProjectListAction;
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
		
		
		/*-- '공개예정 기부 프로젝트 관리 페이지 보기 (승인된 기부프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/readyDonateProjectList.mngp")) {
			
			action = new ReadyDonateProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		
		/*-- '진행중 기부 프로젝트 관리 페이지 보기 (진행중 기부프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/ongoingDonateProjectList.mngp")) {
			
			action = new OngoingDonateProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		
		
		/*-- 성공한 기부 프로젝트 목록 -------------------------------------------------------------------------*/
		
		/*-- '성공한 기부 프로젝트 관리 페이지 보기 (종료된 기부프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/successDonateProjectList.mngp")) {
			
			action = new SuccessDonateProjectListAction();
			
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
		
	
		/*-- '미승인 펀딩 프로젝트 관리 페이지 보기 (미승인 펀딩프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/unauthFundProjectList.mngp")) {
			
			action = new UnauthFundProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		
		/*-- '공개예정 펀딩 프로젝트 관리 페이지 보기 (공개예정 펀딩프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/readyFundProjectList.mngp")) {
			
			action = new ReadyFundProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}	
		
		/*-- '진행중 펀딩 프로젝트 관리 페이지 보기 (진행중 펀딩프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/ongoingFundProjectList.mngp")) {
			
			action = new OngoingFundProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		
		
		/*-- '종료된 펀딩 프로젝트 관리 페이지 보기 (종료된 펀딩프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/doneFundProjectList.mngp")) {
			
			action = new DoneFundProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		
		/*-- '성공한 펀딩 프로젝트 관리 페이지 보기 (성공한 펀딩프로젝트 목록) ' 요청 --------------------------------------*/
		else if(command.equals("/successFundProjectList.mngp")) {
			
			action = new SuccessFundProjectListAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
		
		
		/*-- '관리자모드에서 프로젝트 상세보기' 요청 --------------------------------------*/
		else if(command.equals("/manageProjectView.mngp")) {
			
			action = new ManageProjectViewAction();
			
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
		/*-- '진행 중인 프로젝트를 진행 취소하기' 요청 --------------------------------------*/
		else if(command.equals("/cancelProjectAction.mngp")) {
			
			action = new CancelProjectAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		/*-- '성공 프로젝트의 모금액을 송금하기' 요청 --------------------------------------*/
		else if(command.equals("/sendTotalAmount.mngp")) {
			
			action = new SendTotalAmountAction();
			
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		/*-- '종료된 펀딩 프로젝트의 모금액을 후원자들에게 환불하기' 요청 --------------------------------------*/
		else if(command.equals("/refundAllDonation.mngp")) {
			
			action = new RefundAllDonationAction();
			
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
