package svc.project;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

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
		ProjectBean pj = projectDAO.selectProjectTime(project_id);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return pj;
	}
	
	/** 프로젝트ID로 프로젝트 정보를 얻어옴 (날짜 : 연월일만) */
	public ProjectBean getProjectInfoDate(int project_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ProjectBean pj = projectDAO.selectProjectDate(project_id);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return pj;
	}
	
	/** 프로젝트ID, 사용자ID로 프로젝트 기획자 정보를 얻어옴 */
	public PlannerBean getPlannerInfo(int project_id) {
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
	
	/** 리워드 ID로 리워드 정보를 얻어옴 */
	public RewardBean getRewardInfo(String reward_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		RewardDAO rewardDAO = RewardDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		rewardDAO.setConnection(con);
				
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		RewardBean reward = rewardDAO.selectReward(reward_id);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return reward;
	}

	/** 프로젝트ID로 리워드ID 리스트를 얻어옴 */
	public ArrayList<String> getRewardIdList(int project_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
				
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<String> rewardIdList = projectDAO.selectRewardIdList(project_id);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return rewardIdList;
	}
	
	
	/** 리워드ID 리스트로 리워드정보가 담긴 리워드리스트를 얻어옴 (기본리워드 포함 (관리자용)) */
	public ArrayList<RewardBean> getRewardAllList(ArrayList<String> rewardIdList) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		RewardDAO rewardDAO = RewardDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		rewardDAO.setConnection(con);
				
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<RewardBean> rewardList = new ArrayList<>();
		for(int i=-1; i<rewardIdList.size(); i++) {
			RewardBean reward = null;
			
			//기본리워드 정보를 먼저 담음
			if(i == -1) {
				reward = rewardDAO.selectReward("default");
			}else {
				//리워드ID로 각 리워드 정보를 얻어옴 (기본리워드 제외)
				String reward_id = rewardIdList.get(i);
				reward = rewardDAO.selectReward(reward_id);
			}			
			
			rewardList.add(reward);
		}
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(rewardList.size() == 0) {//만약 리워드 정보 담기에 실패한다면 null 대입
			rewardList = null;
		}
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return rewardList;
	}
	
	
	/** 리워드ID 리스트로 리워드정보가 담긴 리워드리스트를 얻어옴 (기본리워드 제외 (사용자용)) */
	public ArrayList<RewardBean> getRewardList(ArrayList<String> rewardIdList) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		RewardDAO rewardDAO = RewardDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		rewardDAO.setConnection(con);
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<RewardBean> rewardList = new ArrayList<>();
		for(int i=0; i<rewardIdList.size(); i++) {
			
			//리워드ID로 각 리워드 정보를 얻어옴
			String reward_id = rewardIdList.get(i);
			RewardBean reward = rewardDAO.selectReward(reward_id);
					
			rewardList.add(reward);
		}
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(rewardList.size() == 0) {//만약 리워드 정보 담기에 실패한다면 null 대입
			rewardList = null;
		}
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return rewardList;
	}

}