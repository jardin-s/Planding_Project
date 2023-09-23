package svc.admin.manageProject;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.ManageProjectDAO;
import vo.ProjectBean;

public class ManageProjectListService {
	//필드 없음

	//기본생성자
	
	//메서드
	//1. 회원 수 얻어오기
	public int getProjectCount(String kind) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int projectCount = manageProjectDAO.selectProjectCount(kind);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return projectCount;
	}
	

	//2. 조건에 맞는 회원 수를 가져옴
	public int getSearchProjectCount(String kind, String project_title) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int searchProjectCount = manageProjectDAO.searchProjectCount(kind, project_title);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return searchProjectCount;
	}
	
	
	/*---------------------------------------------------------------------------------------------------*/
	
	//1. 전체 기부프로젝트 목록 가져오기 (최근가입순) [기본값]
	public ArrayList<ProjectBean> getProjectList(String kind, int page, int limit) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<ProjectBean> projectList = manageProjectDAO.selectProjectList(kind, page, limit);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return projectList;
	}


	//2. 전체 기부프로젝트 중 조건에 맞는 목록 가져오기 
	public ArrayList<ProjectBean> getSearchProjectList(String kind, String project_title, int page, int limit) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<ProjectBean> searchProjectList = manageProjectDAO.searchProjectList(kind, project_title , page, limit);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return searchProjectList;
	}

	//3. 선택한 정렬기준에 따라 정렬된 기부 프로젝트 목록 가져오기
	public ArrayList<ProjectBean> getOrderProjectList(String kind, String order, int page, int limit) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<ProjectBean> orderProjectList = null;
		
		if(order.equals("new")) { orderProjectList = manageProjectDAO.orderNewProjectList(kind, page, limit); }
		if(order.equals("old")) { orderProjectList = manageProjectDAO.orderOldProjectList(kind, page, limit); }
		if(order.equals("az")) { orderProjectList = manageProjectDAO.orderAZProjectList(kind, page, limit); }
		if(order.equals("za")) { orderProjectList = manageProjectDAO.orderZAProjectList(kind, page, limit); }
				
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return orderProjectList;
	}
}
