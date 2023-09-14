package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.admin.account.AdminDeleteAction;
import action.admin.account.AdminHashPwChangeAction;
import action.admin.account.AdminHashPwFindAction;
import action.admin.account.AdminIdCheckAction;
import action.admin.account.AdminIdFindAction;
import action.admin.account.AdminJoinAction;
import action.admin.account.AdminLoginAction;
import action.admin.account.AdminLogoutAction;
import action.admin.account.AdminUpdateAction;
import action.admin.account.AdminUpdateFormAction;
import action.admin.manageIncome.ManageIncomeAction;
import action.admin.manageMember.DeleteMemberAction;
import action.admin.manageMember.DeletedMemberListAction;
import action.admin.manageMember.DeletedOrderMemberListAction;
import action.admin.manageMember.DeletedSearchMemberListAction;
import action.admin.manageMember.ManageMemberListAction;
import action.admin.manageMember.MemberViewAction;
import action.admin.manageMember.OrderMemberListAction;
import action.admin.manageMember.SearchMemberListAction;
import action.admin.manageMember.UndeletedMemberListAction;
import action.admin.manageMember.UndeletedOrderMemberListAction;
import action.admin.manageMember.UndeletedSearchMemberListAction;
import action.admin.notice.AdminDeleteNoticeAction;
import action.admin.notice.AdminInsertNoticeAction;
import action.admin.notice.AdminInsertNoticeFormAction;
import action.admin.notice.AdminModifyNoticeFormAction;
import action.admin.notice.AdminNoticeListAction;
import action.admin.notice.AdminNoticeViewAction;
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
@WebServlet("*.mngi")
public class MngIncomeFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MngIncomeFrontController() {
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
		
		System.out.println("[MngIncome]command : "+command);//어떤 요청인지 확인하기 위해 콘솔에 출력
		
		
		/***************************************************************************************
		 * 매출관리
		 ***************************************************************************************/
		
		/*-- 매출관리 페이지 -------------------------------------------------------------------------*/
		
		/*-- '매출관리 페이지 보기 (전체회원 목록) ' 요청 --------------------------------------*/
		if(command.equals("/manageIncome.mngi")) {//회원관리 페이지 보기 요청
			
			action = new ManageIncomeAction();
			
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
