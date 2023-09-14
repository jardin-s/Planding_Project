package svc.user;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import dao.ProjectDAO;
import dao.UserDAO;
import vo.ProjectBean;

public class UserUploadProjectListService {
	//필드 없음

	//기본생성자
	
	//메서드
	//1. 등록 프로젝트 id 리스트 얻어오기
	public ArrayList<Integer> getProjectIdList(String p_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		UserDAO userDAO = UserDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		userDAO.setConnection(con);
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<Integer> uploadProjectIdList = userDAO.selectUploadProjectIdList(p_id); 
				
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return uploadProjectIdList;
	}
	
	//2. 등록 프로젝트 정보가 담긴 리스트 얻어오기
	public ArrayList<ProjectBean> getProjectList(ArrayList<Integer> uploadProjectIdList) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ProjectDAO 객체 생성 (ProjectDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<ProjectBean> projectList = new ArrayList<>(); 
		ProjectBean projectInfo = null;
		for(int i=0; i<uploadProjectIdList.size(); i++) {
			
			int p_id = uploadProjectIdList.get(i);
			projectInfo = projectDAO.selectProject(p_id);
			
			//해당 아이디의 프로젝트가 없으면 반복문 종료
			if(projectInfo == null) break; 
			
			projectList.add(projectInfo);
			
		}
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return projectList;		
	}
}
