package svc.admin.manageMember;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.ManageMemberDAO;
import vo.AddressBean;
import vo.DonationBean;
import vo.MemberBean;
import vo.QnaBean;

public class MemberViewService {

	public MemberBean getMemberInfo(String member_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageMemberDAO manageMemberDAO = ManageMemberDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageMemberDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		MemberBean memberInfo = manageMemberDAO.selectMember(member_id);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return memberInfo;
	}

	public ArrayList<AddressBean> getMemberAddressList(String member_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageMemberDAO manageMemberDAO = ManageMemberDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageMemberDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<AddressBean> addressList = manageMemberDAO.selectMemberAddressList(member_id);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return addressList;
	}

	public ArrayList<DonationBean> getMemberDonationList(String member_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageMemberDAO manageMemberDAO = ManageMemberDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageMemberDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<DonationBean> donationList = manageMemberDAO.selectMemberDonationList(member_id);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return donationList;
	}

	//회원이 작성한 문의글 리스트를 가져옴
	public ArrayList<QnaBean> getMemberQnaList(String member_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ManageMemberDAO manageMemberDAO = ManageMemberDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		manageMemberDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<QnaBean> qnaList = manageMemberDAO.selectMemberQnaList(member_id);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return qnaList;
	}

	
	
}
