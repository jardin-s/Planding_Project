package svc.admin.manageMember;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.ManageMemberDAO;

public class DeleteMemberService {
	
	/** 여러 회원을 삭제 */
	public boolean deleteMemberList(String[] memberArr) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageMemberDAO manageMemberDAO = ManageMemberDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageMemberDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int deleteMemberListCount = 0;
		
		for(int i=0; i<memberArr.length; i++) {
			String member_id = memberArr[i];
			
			deleteMemberListCount += manageMemberDAO.updateDeleteUser(member_id);
		}
		
		
		
		boolean isDeleteMemberListResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(deleteMemberListCount == memberArr.length) {//모든 삭제작업이 성공하면 length와 같음
			isDeleteMemberListResult = true;
			commit(con);
		}else{
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isDeleteMemberListResult;
	}
	
	
	/** 특정 회원을 삭제 */
	public boolean deleteMember(String member_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageMemberDAO manageMemberDAO = ManageMemberDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageMemberDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int deleteMemberCount = manageMemberDAO.updateDeleteUser(member_id);
		
		boolean isDeleteMemberResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(deleteMemberCount > 0) {//모든 삭제작업이 성공하면 length와 같음
			isDeleteMemberResult = true;
			commit(con);
		}else{
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isDeleteMemberResult;
	}
}
