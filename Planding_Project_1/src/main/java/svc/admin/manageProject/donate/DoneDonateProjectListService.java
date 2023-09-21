package svc.admin.manageProject.donate;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.ManageProjectDAO;
import vo.ProjectAdminIncomeBean;

public class DoneDonateProjectListService {
	//필드 없음

	//기본생성자
	
	//메서드
	//1. 미승인 프로젝트 수 얻어오기
	public int getDoneDonateCount() {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int donateProjectCount = manageProjectDAO.selectDoneProjectCount("donate");
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return donateProjectCount;
	}
	

	//2. 조건에 맞는 미승인 프로젝트 수를 가져옴
	public int getSearchDoneDonateCount(String project_title) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int searchDonateCount = manageProjectDAO.searchDoneProjectCount("donate", project_title);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return searchDonateCount;
	}
	
	
	/*---------------------------------------------------------------------------------------------------*/
	
	//1. 전체 종료된 기부프로젝트 목록 가져오기 (최근가입순) [기본값]
	public ArrayList<ProjectAdminIncomeBean> getDoneDonateList(int page, int limit) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<ProjectAdminIncomeBean> donateList = manageProjectDAO.selectDoneProjectIncomeList("donate", page, limit);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return donateList;
	}


	//2. 종료된 기부프로젝트 중 조건에 맞는 목록 가져오기 
	public ArrayList<ProjectAdminIncomeBean> getSearchDoneDonateList(int page, int limit, String project_title) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<ProjectAdminIncomeBean> searchDonateList = manageProjectDAO.searchDoneProjectIncomeList("donate", project_title, page, limit);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return searchDonateList;
	}

	//3. 선택한 정렬기준에 따라 정렬된 종료된 기부 프로젝트 목록 가져오기
	public ArrayList<ProjectAdminIncomeBean> getOrderDoneDonateList(String order, int page, int limit) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<ProjectAdminIncomeBean> orderDonateList = null;
		
		if(order.equals("new")) { orderDonateList = manageProjectDAO.selectDoneProjectIncomeList("donate", page, limit); }
		if(order.equals("old")) { orderDonateList = manageProjectDAO.orderOldDoneProjectIncomeList("donate", page, limit); }
		if(order.equals("az")) { orderDonateList = manageProjectDAO.orderAZDoneProjectIncomeList("donate", page, limit); }
		if(order.equals("za")) { orderDonateList = manageProjectDAO.orderZADoneProjectIncomeList("donate", page, limit); }
				
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return orderDonateList;
	}
}
