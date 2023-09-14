package svc.project;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.ProjectDAO;
import vo.ProjectBean;

public class GetProjectIDService {
	//필드 없음
	
			//기본생성자
			
			//메서드
			public int getProjectID(ProjectBean pj){
				
				//1. 커넥션 풀에서 Connection객체를 얻어와
				Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
				
				//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
				ProjectDAO projectDAO = ProjectDAO.getInstance();
				
				//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
				projectDAO.setConnection(con);
				
				
				
				/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
				int projectID = projectDAO.getProjectID(pj);
				
				
				
				//4. 해제
				close(con); //JdbcUtil. 생략(이유?import static 하여)
				
				return projectID;
			}

			
		}
