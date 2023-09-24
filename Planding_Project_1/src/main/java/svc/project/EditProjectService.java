package svc.project;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.ProjectDAO;
import dao.RewardDAO;
import vo.PlannerBean;
import vo.ProjectBean;
import vo.RewardBean;

public class EditProjectService {
	//필드 없음
	
	//기본생성자
	
	//메서드
	/** 프로젝트 edit */
	public boolean editProject(ProjectBean pj){
		
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
				
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int editProjectCount = projectDAO.editProject(pj);
		
		boolean isEditProjectResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(editProjectCount > 0) {
			isEditProjectResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isEditProjectResult;
	}
	

	
	/** 프로젝트 기획자 insert */
	public boolean editPlanner(PlannerBean planner) {
	
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
	
		int editPlannerCount = projectDAO.editPlanner(planner);
		
		boolean isEditPlanner = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(editPlannerCount > 0) {
			isEditPlanner = true;
			commit(con);
		}else {
			rollback(con);
		}
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isEditPlanner;
	}
	
	
	/** 기획자 정보를 삭제 */
	public boolean deletePlanner(int project_id, String member_id) {
		
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
				
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int deletePlannerCount = projectDAO.deletePlanner(project_id, member_id);
		
		boolean isDeletePlannerResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(deletePlannerCount > 0) {
			isDeletePlannerResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isDeletePlannerResult;
	}


	/** 프로젝트 정보를 삭제 */
	public boolean deleteProject(int project_id) {
		
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
				
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int deleteProjectCount = projectDAO.deleteProject(project_id);
		
		boolean isDeleteProjectResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(deleteProjectCount > 0) {
			isDeleteProjectResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isDeleteProjectResult;
	}


	/** 프로젝트와 플래너 정보 수정 */
	public boolean editProjectPlanner(ProjectBean projectInfo, PlannerBean plannerInfo) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
				
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		//[순서-1] 프로젝트 정보를 수정
		int updateProjectCount = 0;
		if(projectInfo.getThumbnail() == null) {
			updateProjectCount = projectDAO.editProjectNotThumbnail(projectInfo);
		}else {
			projectDAO.editProject(projectInfo);
		}
		
		
		//[순서-2] 기획자 정보를 수정
		int updatePlannerCount = projectDAO.editPlanner(plannerInfo);
		
		boolean isEditProjectPlannerResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(updateProjectCount > 0 && updatePlannerCount > 0) {
			isEditProjectPlannerResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isEditProjectPlannerResult;
	}



}
