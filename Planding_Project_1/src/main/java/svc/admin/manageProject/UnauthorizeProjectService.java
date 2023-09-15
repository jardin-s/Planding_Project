package svc.admin.manageProject;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.ManageProjectDAO;
import dao.ProjectDAO;
import dao.UserDAO;
import vo.MemberBean;
import vo.PlannerBean;
import vo.RewardBean;

public class UnauthorizeProjectService {
	//필드 없음

	//기본생성자
	
	//메서드
	//1. 미승인 프로젝트 승인 거절
	public boolean unautorizeProject(int project_id, ArrayList<RewardBean> rewardList) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageProjectDAO manageProjectDAO = ManageProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageProjectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		//[순서-1] 해당 프로젝트의 플래너를 먼저 삭제
		int deletePlannerCount = manageProjectDAO.deletePlanner(project_id);
		
		//[순서-2] 해당 프로젝트의 리워드 목록 중 기본리워드가 아닌 리워드의 아이디
		int deleteProjectRewardMapCount = 0;
		int deleteRewardCount = 0;
		//만약 리워드 목록이 null이라면? 기본리워드만 사용하고 있으므로 -> 매핑데이터만 삭제
		if(rewardList == null) {
			deleteProjectRewardMapCount = manageProjectDAO.deleteProjectRewardMap(project_id);
			deleteRewardCount = 1;//삭제할 리워드가 없으므로 우선 1로 세팅 (삭제실패가 발생하지 않음)
		}
		//만약 리워드 목록이 null이 아니라면? 기본리워드 외 다른 리워드를 등록했으므로 매핑데이터 삭제 후, 해당 리워드를 리워드 테이블에서 삭제
		else {
			
			//2-1. 모든 매핑데이터 삭제
			deleteProjectRewardMapCount = manageProjectDAO.deleteProjectRewardMap(project_id);
			
			//2-2. 모든 리워드 삭제
			for(int i=0; i<rewardList.size(); i++) {
				deleteRewardCount += manageProjectDAO.deleteReward(rewardList.get(i).getReward_id());
			}
			if(deleteRewardCount == rewardList.size()) {//모든 리워드 데이터 삭제에 성공했다면
				deleteRewardCount = 1; //성공대표값 1로 세팅
			}else {//모든 리워드 데이터를 삭제하는 데 실패했다면
				deleteRewardCount = 0; //실패대표값 0으로 세팅
			}			
			
		}
		
		
		//[순서-3] 해당 프로젝트 정보 삭제
		int deleteProjceCount = manageProjectDAO.deleteProject(project_id);
		
		//[순서-4] 결과 처리
		boolean isUnautorizeResult = false;
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(deletePlannerCount > 0 && deleteProjectRewardMapCount > 0 &&  deleteRewardCount > 0 && deleteProjceCount > 0) {
			isUnautorizeResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isUnautorizeResult;
	}

	
	//프로젝트 기획자 정보를 얻어옴
	public PlannerBean getProjectPlanner(int project_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		PlannerBean plannerInfo = projectDAO.selectPlanner(project_id);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return plannerInfo;
	}
	
	
	//프로젝트 기획자의 회원ID로 회원정보를 얻어옴
	public MemberBean getPlannerUserInfo(String planner_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		UserDAO userDAO = UserDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		userDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		MemberBean userInfo = userDAO.selectUserInfo(planner_id);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return userInfo;
	}


	/** 프로젝트 ID로 프로젝트-리워드 매핑테이블에서 리워드 목록 얻어오기 */
	public ArrayList<RewardBean> getProjectRewardIdList(int project_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<RewardBean> rewardList = projectDAO.selectRewardIdList(project_id);
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return rewardList;
	}
}
