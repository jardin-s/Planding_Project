package svc.user;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.UserDAO;


public class UserTopupMoneyService {

	public boolean topUpMoney(String member_id, int topUpMoney) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		UserDAO userDAO = UserDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		userDAO.setConnection(con);
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int updateUserPlusMoneyCount = userDAO.updateUserPlusMoney(member_id, topUpMoney);
		
		boolean isTopUpMoneyResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(updateUserPlusMoneyCount > 0) {
			isTopUpMoneyResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isTopUpMoneyResult;
	}

	/** 사용자 현재 계좌잔액을 가져옴 */
	public int getUserMoney(String member_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		UserDAO userDAO = UserDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		userDAO.setConnection(con);
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int userMoney = userDAO.getUserMoney(member_id);
		
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return userMoney;
	}

}
