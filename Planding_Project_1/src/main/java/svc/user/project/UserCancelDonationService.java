package svc.user.project;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.ProjectDAO;
import dao.UserDAO;
import vo.AddressBean;
import vo.MemberBean;
import vo.ProjectBean;
import vo.ProjectDonationRewardBean;

public class UserCancelDonationService {
	
	//필드 없음

	//기본생성자
	
	//메서드
	/** 후원 ID로 모든 후원 정보를 얻어오기 */
	public ProjectDonationRewardBean getDonationInfo(int donation_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ProjectDonationRewardBean donationInfo = projectDAO.selectDonationInfo(donation_id);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return donationInfo;
	}
	
	/** 후원 취소하기 */
	public boolean cancelDonation(ProjectDonationRewardBean donation) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		
		//[순서-1] 프로젝트 현재모금액 빼기
		int updateProjectAmountMinusCount = projectDAO.updateProjectCurrAmountMinus(donation.getProject_id(), donation.getTotalDonation());
		System.out.println("[UserCancelDonationService] ProjectDonationRewardBean : updateProjectAmountMinusCount = "+updateProjectAmountMinusCount);
		
		//[순서-2] 회원 계좌에 모금액 더하기
		int updateUserMoneyPlusCount = projectDAO.updateUserPlusMoney(donation.getMember_id(), donation.getTotalDonation());
		System.out.println("[UserCancelDonationService] ProjectDonationRewardBean : updateUserMoneyPlusCount = "+updateUserMoneyPlusCount);
		
		//[순서-3] 후원 기록 삭제
		int deleteDonationCount = projectDAO.deleteDonation(donation.getDonation_id());
		System.out.println("[UserCancelDonationService] ProjectDonationRewardBean : deleteDonationCount = "+deleteDonationCount);
				
		boolean isCancelDonationResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(updateProjectAmountMinusCount > 0 && updateUserMoneyPlusCount > 0 && deleteDonationCount > 0) {
			isCancelDonationResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isCancelDonationResult;
	}

	public AddressBean getAddrInfo(String address_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		UserDAO userDAO = UserDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		userDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		AddressBean addressInfo = userDAO.selectAddrInfoById(address_id);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return addressInfo;
	}

	public boolean deleteNonBasicAddress(String address_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		UserDAO userDAO = UserDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		userDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int deleteAddrCount = userDAO.deleteAddrById(address_id);
		
		boolean isDeleteAddrResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(deleteAddrCount > 0) {
			isDeleteAddrResult = true;
			commit(con);
		}else {
			rollback(con);
		}		
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isDeleteAddrResult;
	}

	public MemberBean getUserInfo(String member_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		UserDAO userDAO = UserDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		userDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		MemberBean userInfo = userDAO.selectUserInfo(member_id);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return userInfo;
	}
	
}
