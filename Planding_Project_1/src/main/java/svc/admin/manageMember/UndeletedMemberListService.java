package svc.admin.manageMember;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.ManageMemberDAO;
import vo.MemberBean;

public class UndeletedMemberListService {

	//필드 없음

	//기본생성자
	
	//메서드
	//1. 미탈퇴 회원 수 얻어오기
	public int getUndeletedMemberCount() {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageMemberDAO manageMemberDAO = ManageMemberDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageMemberDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int memberListCount = manageMemberDAO.selectDeleteUndeletedCount("N");
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return memberListCount;
	}
	
	//2. 조건에 맞는 미탈퇴 회원 수 얻어오기
	public int getUndeletedMemberCount(String member_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageMemberDAO manageMemberDAO = ManageMemberDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageMemberDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int searchMemberCount = manageMemberDAO.selectDeleteUndeletedCount("N");
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return searchMemberCount;
	}
	
	/*---------------------------------------------------------------------------------------------------*/
	
	//1. 미탈퇴 회원 목록 가져오기 (최근가입순) [기본값]
	public ArrayList<MemberBean> getUndeletedMemberList(int page, int limit) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (ManageMemberDAO 객체를 하나만 만들어서 계속 사용)
		ManageMemberDAO manageMemberDAO = ManageMemberDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 ManageMemberDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageMemberDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<MemberBean> memberList = manageMemberDAO.selectDeleteUndeletedMemberList("N", page, limit);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return memberList;
	}
	
	//2. 조건에 맞는 미탈퇴 회원 목록 가져오기 (최근가입순) [기본값]
	public ArrayList<MemberBean> getSearchUnDeletedMemberList(String member_id, int page, int limit) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (ManageMemberDAO 객체를 하나만 만들어서 계속 사용)
		ManageMemberDAO manageMemberDAO = ManageMemberDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 ManageMemberDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageMemberDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<MemberBean> memberList = manageMemberDAO.searchDeleteUndeletedMemberList("N", member_id, page, limit);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return memberList;
	}
	
}
