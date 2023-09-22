package svc.project;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.ProjectDAO;
import dao.RewardDAO;
import vo.PlannerBean;
import vo.ProjectBean;
import vo.RewardBean;

public class InsertProjectService {
	//필드 없음
	
	//기본생성자
	
	//메서드
	/** 프로젝트 insert */
	public boolean insertProject(ProjectBean pj){
		
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
				
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int insertProjectCount = projectDAO.insertProject(pj);
		
		boolean isInsertProjectResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(insertProjectCount > 0) {
			isInsertProjectResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isInsertProjectResult;
	}
	
	/** 프로젝트 기획자 insert 하기 위해 project_id를 가지고 옴 */ 
	public int getProjectID(String title, String thumbnail){
		
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
				
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int projectID = projectDAO.getProjectID(title, thumbnail);
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return projectID;
	}
	
	/** 프로젝트 기획자 insert */
	public boolean insertPlanner(PlannerBean planner) {
	
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
	
		int insertPlannerCount = projectDAO.insertPlanner(planner);
		
		boolean isInsertPlanner = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(insertPlannerCount > 0) {
			isInsertPlanner = true;
			commit(con);
		}else {
			rollback(con);
		}
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isInsertPlanner;
	}
	
	/** 리워드 목록들을 리워드테이블에 insert */
	public boolean insertRewardList(ArrayList<RewardBean> rewardList) {
		
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		RewardDAO rewardDAO = RewardDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		rewardDAO.setConnection(con);
		
				
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int insertProjectCount = 0;
		for(int i=0; i<rewardList.size(); i++) {
			RewardBean reward = rewardList.get(i);
			insertProjectCount += rewardDAO.insertReward(reward);
		}
				
		boolean isInsertRewardListResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(insertProjectCount == rewardList.size()) {
			isInsertRewardListResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isInsertRewardListResult;
		
	}
	
	/** [기부프로젝트 insert 시 사용] 프로젝트와 리워드를 매핑 (프로젝트-리워드 매핑테이블에 insert) */
	public boolean mapProjectReward(int project_id, String reward_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		RewardDAO rewardDAO = RewardDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		rewardDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int insertProjectRewardCount = rewardDAO.insertProjectReward(project_id, reward_id);
		
		boolean isMapProjectRewardResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(insertProjectRewardCount > 0) {
			isMapProjectRewardResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isMapProjectRewardResult;
	}
	
	/** [펀딩프로젝트 insert 시 사용] 프로젝트와 리워드를 매핑 (프로젝트-리워드 매핑테이블에 insert) */
	public boolean mapProjectRewardList(int project_id, ArrayList<RewardBean> rewardList) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		RewardDAO rewardDAO = RewardDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		rewardDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int insertProjectRewardCount = 0; //rewardDAO.insertProjectReward(project_id, reward_id);
		
		for(int i=0; i<rewardList.size(); i++) {
			String reward_id = rewardList.get(i).getReward_id();
			insertProjectRewardCount += rewardDAO.insertProjectReward(project_id, reward_id);
		}
		//입력한 리워드 모두 매핑한 후, 기본리워드도 매핑하기
		insertProjectRewardCount += rewardDAO.insertProjectReward(project_id, "default");
		
		boolean isMapProjectRewardListResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(insertProjectRewardCount == (rewardList.size()+1)) {//리워드개수 + 기본리워드 1개
			isMapProjectRewardListResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isMapProjectRewardListResult;
	}
	
	
	/** (이주헌 EditRewardAction) 리워드빈 객체로 리워드DAO에서 해당 리워드 수정 */
	public boolean editReward(RewardBean rewardInfo) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		RewardDAO rewardDAO = RewardDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		rewardDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int insertProjectRewardCount = 0; //rewardDAO.insertProjectReward(project_id, reward_id);

			insertProjectRewardCount = rewardDAO.editReward(rewardInfo);

		
		boolean isEditRewardResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(insertProjectRewardCount >0) {
			isEditRewardResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isEditRewardResult;
	}
	
}
