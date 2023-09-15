package svc.project;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.ProjectDAO;

public class SubmitProjectService {

	/** 프로젝트 상태를 temp(임시저장)에서 unauthorized(미승인)상태로 변경 */
	public boolean updateStatusUnautorized(int project_id) {

		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int updateProjectStatusCount = projectDAO.updateProjectStatus(project_id, "unauthorized");
		
		boolean isUpdateStatusResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(updateProjectStatusCount > 0) {
			isUpdateStatusResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isUpdateStatusResult;
	}
}