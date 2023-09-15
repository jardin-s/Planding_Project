package svc.user.account;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.UserDAO;
import vo.MemberPwChangeBean;

public class UserHashPwChangeService {

	//필드 없음

	//기본생성자
	
	//메서드
	//1. 회원 가입
	public boolean changeHashPw(MemberPwChangeBean userPwChangeBean) {
		
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		UserDAO userDAO = UserDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		userDAO.setConnection(con);
		
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int changeHashPwCount = userDAO.changeHashPw(userPwChangeBean);
		
		boolean isChangeHashPwResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(changeHashPwCount > 0) {
			isChangeHashPwResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isChangeHashPwResult;
			
	}
	

}
