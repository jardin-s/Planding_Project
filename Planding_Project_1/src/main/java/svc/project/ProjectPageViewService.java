package svc.project;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.ProjectDAO;
import dao.RewardDAO;
import vo.PlannerBean;
import vo.ProjectBean;
import vo.RewardBean;


public class ProjectPageViewService {
	
	/** 프로젝트ID로 프로젝트 정보를 얻어옴 */
	public ProjectBean getProjectInfo(int project_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
				
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ProjectBean pj = projectDAO.selectProject(project_id);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return pj;
	}
	
	/** 프로젝트ID, 사용자ID로 프로젝트 기획자 정보를 얻어옴 */
	public PlannerBean getPlannerInfo(int project_id, String member_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
				
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		PlannerBean planner = projectDAO.selectPlanner(project_id);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return planner;
	}
	
	/** 리워드 ID, 리워드 이름으로 리워드 정보를 얻어옴 */
	public RewardBean getRewardInfo(int reward_id, String r_name) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		RewardDAO rewardDAO = RewardDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		rewardDAO.setConnection(con);
				
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		RewardBean reward = rewardDAO.selectReward(reward_id, r_name);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return reward;
	}

}