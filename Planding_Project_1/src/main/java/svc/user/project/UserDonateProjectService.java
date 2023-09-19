package svc.user.project;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.ProjectDAO;
import dao.UserDAO;
import vo.AddressBean;
import vo.DonationBean;

public class UserDonateProjectService {
	
	//필드 없음

	//기본생성자
	
	//메서드
	/** 후원하기 (기본리워드+추가금액) */
	public boolean donateProjectNotAddr(DonationBean donation) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		
		//[순서-1] 사용자 계좌에서 money 감소
		int updateUserMoneyCount = projectDAO.updateUserMinusMoney(donation.getMember_id(), donation.getTotalDonation_Cal());		
		System.out.println("[UserDonateProjectService] donateProjectNotAddr : "+updateUserMoneyCount);
		
		//[순서-2] Project의 curr_amount 증가
		int updateProjectAmountCount = projectDAO.updateProjectCurrAmount(donation.getProject_id(), donation.getTotalDonation_Cal());
		System.out.println("[UserDonateProjectService] donateProjectNotAddr : "+updateProjectAmountCount);
		
		//[순서-3] 후원기록 tbl에 insert
		int insertDonationCount = projectDAO.insertDonationNotAddr(donation);
		System.out.println("[UserDonateProjectService] donateProjectNotAddr : "+insertDonationCount);
		
		
		boolean isDonateProjectResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(updateUserMoneyCount > 0 && updateProjectAmountCount > 0 && insertDonationCount > 0) {
			isDonateProjectResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isDonateProjectResult;
	}
	
	/** 후원하기 (선택리워드+추가금액) */
	public boolean donateProjectAddr(DonationBean donation) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		
		//[순서-1] 사용자 계좌에서 money 감소
		int updateUserMoneyCount = projectDAO.updateUserMinusMoney(donation.getMember_id(), donation.getTotalDonation_Cal());		
		System.out.println("UserDonateProjectService");
		
		//[순서-2] Project의 curr_amount 증가
		int updateProjectAmountCount = projectDAO.updateProjectCurrAmount(donation.getProject_id(), donation.getTotalDonation_Cal());
		
		//[순서-3] 후원기록 tbl에 insert
		int insertDonationCount = projectDAO.insertDonationAddr(donation);
		
		
		boolean isDonateProjectResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(updateUserMoneyCount > 0 && updateProjectAmountCount > 0 && insertDonationCount > 0) {
			isDonateProjectResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isDonateProjectResult;
	}
	
	
	/** 입력한 주소정보에 해당하는 Address를 가져옴 (없으면 새로 insert해야함) */
	public AddressBean matchAddr(AddressBean address) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		UserDAO userDAO = UserDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		userDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		AddressBean addressInfo = userDAO.selectAddrInfo(address);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return addressInfo;
	}
	
	
	/** 기존에 있는 주소가 아니면 해당 주소를 업데이트 */
	public boolean insertNewAddr(AddressBean address) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		UserDAO userDAO = UserDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		userDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int insertAddrCount = userDAO.insertAddr(address);
		
		boolean isInsertAddrResult = false; 
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(insertAddrCount > 0) {
			isInsertAddrResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isInsertAddrResult;
	}

		
}
