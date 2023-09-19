package svc.user.project;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.ProjectDAO;
import vo.ProjectPlannerBean;

public class UserDoneDonateProjectListService {

	//필드 없음

	//기본생성자
	
	//메서드
	/** 진행중인 기부 프로젝트 수 얻어오기 */
	public int getDonateProjectDoneCount() {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int donateProjectCount = projectDAO.selectProjectKindDoneCount("donate");
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return donateProjectCount;
	}
	
	/** 검색조건에 따른 진행중인 기부 프로젝트-기획자 리스트 수를 얻어옴 */
	public int getSearchDonateProjectDoneCount(String title) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int donateProjectCount = projectDAO.selectSearchProjectKindDoneCount("donate", title);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return donateProjectCount;
	}

	/** 원하는 페이지의 원하는 개수만큼 진행중인 기부 프로젝트-기획자 목록 얻어오기 (최근 마감 순)*/
	public ArrayList<ProjectPlannerBean> getDonateProjectPlannerDoneList(int page, int limit) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<ProjectPlannerBean> donateProjectPlannerList = projectDAO.selectProjectPlannerDoneList("donate", page, limit);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return donateProjectPlannerList;
	}

	/** 정렬기준에 따라 원하는페이지/개수만큼 진행중인 프로젝트-기획자 리스트 얻어옴 */
	public ArrayList<ProjectPlannerBean> getOrderDonateProjectPlannerDoneList(String order, int page,
			int limit) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<ProjectPlannerBean> orderProjectPlannerList = null;
		
		if(order.equals("new")) { orderProjectPlannerList = projectDAO.selectProjectPlannerDoneList("donate", page, limit);/*기본값이 최신순*/ }
		if(order.equals("old")) { orderProjectPlannerList = projectDAO.selectOldProjectPlannerDoneList("donate", page, limit); }
		if(order.equals("popular")) { orderProjectPlannerList = projectDAO.selectPopularProjectPlannerDoneList("donate", page, limit); }	
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return orderProjectPlannerList;
	}
	
	
	/** 검색어에 따라 원하는페이지/개수만큼 진행중인 프로젝트-기획자 리스트 얻어옴 */
	public ArrayList<ProjectPlannerBean> getSearchDonateProjectPlannerDoneList(String title, int page,
			int limit) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<ProjectPlannerBean> searchProjectPlannerList = projectDAO.selectSearchProjectPlannerDoneList("donate", title, page, limit);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return searchProjectPlannerList;
	}

	
	
}
