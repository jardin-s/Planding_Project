package svc.user.account;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.UserDAO;
import vo.MemberBean;

public class UserDeleteService {
	
	//필드 없음

	//기본생성자
	
	//메서드
	//1. 본인 인증
	public String checkUserSelf(MemberBean user) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		UserDAO userDAO = UserDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		userDAO.setConnection(con);
		
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		String u_id = userDAO.selectLoginId(user);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		return u_id;
	}
	
	//2. 회원삭제
	public boolean userDelete(MemberBean user){
		
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		UserDAO userDAO = UserDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		userDAO.setConnection(con);
		
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		
		//회원 개인정보를 삭제하고 탈퇴여부와 탈퇴일자 업데이트
		int updateDeleteUserCount = userDAO.updateDeleteUser(user.getMember_id());
		
		//만약, 회원이 신청한 펀딩에서 배송완료되지 않은 상태가 아니라면 주소 삭제 (배송완료 & 신청한 펀딩 없음)
		int deleteAddrCount = userDAO.deleteAddr(user.getMember_id());
		
		boolean isUserDeleteResult = false;	
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(updateDeleteUserCount > 0 && deleteAddrCount > 0) {
			isUserDeleteResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isUserDeleteResult;
	}

		
}
