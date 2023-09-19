package svc.project;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.ProjectDAO;
import dao.RewardDAO;

public class DeleteRewardService {
	
	
	//Donation테이블에서 후원기록을 카운트해옴
	public int seleteDonation_Reward(String reward_id) {
		int donationCount = 0;
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		RewardDAO rewardDAO = RewardDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		rewardDAO.setConnection(con);
		
				
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		donationCount=rewardDAO.seleteDonation_Reward(reward_id);

		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return donationCount;
	}
	//프로젝트 리워드 테이블에서 매핑삭제
	public boolean deleteMapReward(int project_id, String reward_id) {
		
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		RewardDAO rewardDAO = RewardDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		rewardDAO.setConnection(con);
		
				
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int deleteMapReward = rewardDAO.deleteMapReward(project_id, reward_id);
		
		boolean isDeleteMapRewardResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(deleteMapReward > 0) {
			isDeleteMapRewardResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isDeleteMapRewardResult;
	}
	//리워드테이블에서 리워드 삭제
	public boolean deleteReward(String reward_id) {
		
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		RewardDAO rewardDAO = RewardDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		rewardDAO.setConnection(con);
		
				
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int deleteReward = rewardDAO.deleteReward(reward_id);
		
		boolean isdeleteRewardResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(deleteReward > 0) {
			isdeleteRewardResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isdeleteRewardResult;
	}
}
