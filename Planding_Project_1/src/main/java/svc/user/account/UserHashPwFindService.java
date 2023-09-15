package svc.user.account;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.UserDAO;
import vo.MemberBean;

public class UserHashPwFindService {
	//필드 없음

	//기본생성자
	
	//메서드
	//1. 회원확인 후 임시비밀번호 생성
	public MemberBean findHashPw(String u_id, String u_email) {

		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		UserDAO userDAO = UserDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		userDAO.setConnection(con);
		
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		MemberBean userInfo = userDAO.findHashPw(u_id, u_email);
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
			
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return userInfo;
	}
	
	//2. 회원비밀번호를 임시비밀번호로 재설정
	public boolean setHashPw(String id, String email, String hashPassword){
		
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		UserDAO userDAO = UserDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		userDAO.setConnection(con);
		
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int updatePwCount = userDAO.setHashPw(id, email, hashPassword);
		
		boolean isSetHashPwSuccess = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(updatePwCount > 0) {
			isSetHashPwSuccess = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isSetHashPwSuccess;
	}

}
