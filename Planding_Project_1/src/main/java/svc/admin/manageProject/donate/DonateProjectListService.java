package svc.admin.manageProject.donate;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import dao.ManageProjectDAO;
import vo.ProjectAdminIncomeBean;
import vo.ProjectBean;

public class DonateProjectListService {
	//필드 없음

	//기본생성자
	
	//메서드
	//1. 프로젝트 수 얻어오기
	public int getDonateCount(String kind, String p_status) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int donateProjectCount = 0;
		
		if(p_status.equalsIgnoreCase("done")) {//종료 프로젝트 -> done + clear(환불완)
			donateProjectCount = manageProjectDAO.selectDoneClearProjectCount(kind);
		}else if(p_status.equalsIgnoreCase("success")){//성공 프로젝트 -> success + remitcom(송금완)
			donateProjectCount = manageProjectDAO.selectSuccessRemitcomProjectCount(kind);		
		}else {
			donateProjectCount = manageProjectDAO.selectStatusProjectCount(kind, p_status);
		}
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return donateProjectCount;
	}

	//2. 조건에 맞는 프로젝트 수를 가져옴
	public int getSearchDonateCount(String kind, String p_status, String project_title) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int searchDonateCount = 0;
		if(p_status.equalsIgnoreCase("done")) {
			searchDonateCount = manageProjectDAO.searchDoneProjectCount(kind, project_title);
		}else if(p_status.equalsIgnoreCase("success")) {
			searchDonateCount = manageProjectDAO.searchSuccessProjectCount(kind, project_title);
		}else {
			searchDonateCount = manageProjectDAO.searchStatusProjectCount(kind, p_status, project_title);
		}
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return searchDonateCount;
	}
	
	
	/*---------------------------------------------------------------------------------------------------*/
	
	//1. 특정 상태의 전체 프로젝트 목록 가져오기 (최근가입순) [기본값]
	public ArrayList<ProjectBean> getDonateList(String kind, String p_status, int page, int limit) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		ArrayList<ProjectBean> donateList = null;
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		if(p_status.equalsIgnoreCase("done")) {
			donateList = manageProjectDAO.selectDoneProjectList(kind, page, limit);
		}else if(p_status.equalsIgnoreCase("success")) {
			donateList = manageProjectDAO.selectSuccessProjectList(kind, page, limit);
		}else {
			donateList = manageProjectDAO.selectStatusProjectList(kind, p_status, page, limit);
		}
			
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return donateList;
	}
	
	
	//2. 조건에 맞는 프로젝트 목록 가져오기 
	public ArrayList<ProjectBean> getSearchDonateList(int page, int limit, String kind, String p_status, String project_title) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<ProjectBean> searchDonateList = null;
		if(p_status.equalsIgnoreCase("done")) {
			searchDonateList = manageProjectDAO.searchDoneProjectList(kind, project_title, page, limit);
		}else if(p_status.equalsIgnoreCase("success")) {
			searchDonateList = manageProjectDAO.searchSuccessProjectList(kind, project_title, page, limit);
		}else {
			searchDonateList = manageProjectDAO.searchStatusProjectList(kind, p_status, project_title, page, limit);
		}
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return searchDonateList;
	}

	//3. 선택한 정렬기준에 따라 정렬된 종료된 프로젝트 목록 가져오기
	public ArrayList<ProjectBean> getOrderDoneDonateList(String kind, String p_status, String order, int page, int limit) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<ProjectBean> orderDonateList = null;
		
		if(p_status.equalsIgnoreCase("done")) {
			if(order.equals("new")) { orderDonateList = manageProjectDAO.selectDoneProjectList("donate", page, limit); }
			if(order.equals("old")) { orderDonateList = manageProjectDAO.orderOldDoneProjectList("donate", page, limit); }
			if(order.equals("az")) { orderDonateList = manageProjectDAO.orderAZDoneProjectList("donate", page, limit); }
			if(order.equals("za")) { orderDonateList = manageProjectDAO.orderZADoneProjectList("donate", page, limit); }
			if(order.equals("unsent")) { orderDonateList = manageProjectDAO.orderUnsentDoneProjectList("donate", page, limit); }
		}else if(p_status.equalsIgnoreCase("success")) {
			if(order.equals("new")) { orderDonateList = manageProjectDAO.selectSuccessProjectList("donate", page, limit); }
			if(order.equals("old")) { orderDonateList = manageProjectDAO.orderOldSuccessProjectList("donate", page, limit); }
			if(order.equals("az")) { orderDonateList = manageProjectDAO.orderAZSuccessProjectList("donate", page, limit); }
			if(order.equals("za")) { orderDonateList = manageProjectDAO.orderZASuccessProjectList("donate", page, limit); }
			if(order.equals("unsent")) { orderDonateList = manageProjectDAO.orderUnsentSuccessProjectList("donate", page, limit); }
		}else {
			if(order.equals("new")) { orderDonateList = manageProjectDAO.selectStatusProjectList("donate", p_status, page, limit); }
			if(order.equals("old")) { orderDonateList = manageProjectDAO.orderOldStatusProjectList("donate", p_status, page, limit); }
			if(order.equals("az")) { orderDonateList = manageProjectDAO.orderAZStatusProjectList("donate", p_status, page, limit); }
			if(order.equals("za")) { orderDonateList = manageProjectDAO.orderZAStatusProjectList("donate", p_status, page, limit); }
		}
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return orderDonateList;
	}
}
