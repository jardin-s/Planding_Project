package svc.user;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.UserDAO;

public class UserBookmarkDeleteService {
	
	public boolean deleteBookmarkList(String[] projectIdArr) {
		
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		UserDAO userDAO = UserDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		userDAO.setConnection(con);
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int deleteBookmarkCount = 0;
		
		for(int i=0; i<projectIdArr.length; i++) {
			int project_id = Integer.parseInt(projectIdArr[i]);
			
			deleteBookmarkCount += userDAO.deleteBookmark(project_id);			
		}
		
		boolean isDeleteBookmarkListResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(deleteBookmarkCount == projectIdArr.length) {
			isDeleteBookmarkListResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isDeleteBookmarkListResult;
				
	}

}
