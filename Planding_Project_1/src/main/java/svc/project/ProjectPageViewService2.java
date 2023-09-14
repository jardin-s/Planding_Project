package svc.project;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;

import dao.ProjectDAO;
import vo.PlannerBean;

public class ProjectPageViewService2 {
	public PlannerBean projectPageViewService2(int project_id, String member_id) {
	//1. 커넥션 풀에서 Connection객체를 얻어와
	Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
	
	//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
	ProjectDAO projectDAO = ProjectDAO.getInstance();
	
	//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
	projectDAO.setConnection(con);
			
	/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
	PlannerBean planner = projectDAO.selectPlanner(project_id, member_id);
	
	/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
	 * 		 단, select는 이런 작업을 제외 ------------------*/
	
	//4. 해제
	close(con); //JdbcUtil. 생략(이유?import static 하여)
	
	return planner;
}

}