package svc.admin.manageProject;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import dao.ManageProjectDAO;
import vo.ProjectBean;

public class ManageStatusProjectListService {
	
	
	//1. 프로젝트 수 얻어오기
	public int getProjectCount(String kind, String p_status) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int projectCount = 0;
		
		if(p_status.equalsIgnoreCase("done")) {//종료 프로젝트 -> done + clear(환불완)
			projectCount = manageProjectDAO.selectDoneClearProjectCount(kind);
		}else if(p_status.equalsIgnoreCase("success")){//성공 프로젝트 -> success + remitcom(송금완)
			projectCount = manageProjectDAO.selectSuccessRemitcomProjectCount(kind);		
		}else {
			projectCount = manageProjectDAO.selectStatusProjectCount(kind, p_status);
		}
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return projectCount;
	}

	//2. 조건에 맞는 프로젝트 수를 가져옴
	public int getSearchProjectCount(String kind, String p_status, String project_title) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int searchProjectCount = 0;
		if(p_status.equalsIgnoreCase("done")) {
			searchProjectCount = manageProjectDAO.searchDoneProjectCount(kind, project_title);
		}else if(p_status.equalsIgnoreCase("success")) {
			searchProjectCount = manageProjectDAO.searchSuccessProjectCount(kind, project_title);
		}else {
			searchProjectCount = manageProjectDAO.searchStatusProjectCount(kind, p_status, project_title);
		}
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return searchProjectCount;
	}
	
	
	/*---------------------------------------------------------------------------------------------------*/
	
	//1. 특정 상태의 전체 프로젝트 목록 가져오기 (최근가입순) [기본값]
	public ArrayList<ProjectBean> getProjectList(String kind, String p_status, int page, int limit) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		ArrayList<ProjectBean> projectList = null;
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		if(p_status.equalsIgnoreCase("done")) {
			projectList = manageProjectDAO.selectDoneProjectList(kind, page, limit);
		}else if(p_status.equalsIgnoreCase("success")) {
			projectList = manageProjectDAO.selectSuccessProjectList(kind, page, limit);
		}else {
			projectList = manageProjectDAO.selectStatusProjectList(kind, p_status, page, limit);
		}
			
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return projectList;
	}
	
	
	//2. 조건에 맞는 프로젝트 목록 가져오기 
	public ArrayList<ProjectBean> getSearchProjectList(String kind, String p_status, String project_title, int page, int limit) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<ProjectBean> searchProjectList = null;
		if(p_status.equalsIgnoreCase("done")) {
			searchProjectList = manageProjectDAO.searchDoneProjectList(kind, project_title, page, limit);
		}else if(p_status.equalsIgnoreCase("success")) {
			searchProjectList = manageProjectDAO.searchSuccessProjectList(kind, project_title, page, limit);
		}else {
			searchProjectList = manageProjectDAO.searchStatusProjectList(kind, p_status, project_title, page, limit);
		}
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return searchProjectList;
	}

	//3. 선택한 정렬기준에 따라 정렬된 프로젝트 목록 가져오기
	public ArrayList<ProjectBean> getOrderProjectList(String kind, String p_status, String order, int page, int limit) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<ProjectBean> orderProjectList = null;
		
		if(p_status.equalsIgnoreCase("done")) {
			if(order.equals("new")) { orderProjectList = manageProjectDAO.selectDoneProjectList(kind, page, limit); }
			if(order.equals("old")) { orderProjectList = manageProjectDAO.orderOldDoneProjectList(kind, page, limit); }
			if(order.equals("az")) { orderProjectList = manageProjectDAO.orderAZDoneProjectList(kind, page, limit); }
			if(order.equals("za")) { orderProjectList = manageProjectDAO.orderZADoneProjectList(kind, page, limit); }
			if(order.equals("unsent")) { orderProjectList = manageProjectDAO.orderUnsentDoneProjectList(kind, page, limit); }
		}else if(p_status.equalsIgnoreCase("success")) {
			if(order.equals("new")) { orderProjectList = manageProjectDAO.selectSuccessProjectList(kind, page, limit); }
			if(order.equals("old")) { orderProjectList = manageProjectDAO.orderOldSuccessProjectList(kind, page, limit); }
			if(order.equals("az")) { orderProjectList = manageProjectDAO.orderAZSuccessProjectList(kind, page, limit); }
			if(order.equals("za")) { orderProjectList = manageProjectDAO.orderZASuccessProjectList(kind, page, limit); }
			if(order.equals("unsent")) { orderProjectList = manageProjectDAO.orderUnsentSuccessProjectList(kind, page, limit); }
		}else {
			if(order.equals("new")) { orderProjectList = manageProjectDAO.selectStatusProjectList(kind, p_status, page, limit); }
			if(order.equals("old")) { orderProjectList = manageProjectDAO.orderOldStatusProjectList(kind, p_status, page, limit); }
			if(order.equals("az")) { orderProjectList = manageProjectDAO.orderAZStatusProjectList(kind, p_status, page, limit); }
			if(order.equals("za")) { orderProjectList = manageProjectDAO.orderZAStatusProjectList(kind, p_status, page, limit); }
		}
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return orderProjectList;
	}
}
