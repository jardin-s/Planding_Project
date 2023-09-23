package svc.admin.manageProject;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import dao.AdminIncomeDAO;
import dao.ProjectDAO;
import dao.UserDAO;
import vo.DonationBean;
import vo.MemberBean;
import vo.PlannerBean;
import vo.ProjectBean;
import vo.ProjectPlannerBean;

public class RefundAllDonationService {

	//필드 없음

	//기본생성자
	
	//메서드
	/** 해당 프로젝트의 후원기록을 얻어옴 */
	public ArrayList<DonationBean> getDonationList(int project_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<DonationBean> donationList = projectDAO.selectProjectDonationList(project_id);
		//여기서 가져오는 donation객체들은 모두 totalDonation이 생성할때 세팅됨
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return donationList;
	}

	/** 후원금액을 각 후원자에게 환불하고, 프로젝트 상태를 'clear'로 변경 */
	public boolean refundDonation(ArrayList<DonationBean> donationList, int project_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		int updateUserPlusMoneyCount = 0;
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		for(int i=0; i<donationList.size(); i++) {
			DonationBean donation = donationList.get(i);
			updateUserPlusMoneyCount += projectDAO.updateUserPlusMoney(donation.getMember_id(), donation.getTotalDonation());
		}
		System.out.println("[RefundAllDonationService] refundDonation() : updateUserPlusMoneyCount = "+updateUserPlusMoneyCount);
		
		int updateProjectStatusCount = projectDAO.updateProjectStatus(project_id, "clear");
		System.out.println("[RefundAllDonationService] refundDonation() : updateProjectStatusCount = "+updateProjectStatusCount);
		
		boolean isRefundDonationResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(updateUserPlusMoneyCount == donationList.size() && updateProjectStatusCount > 0) {
			isRefundDonationResult = true;
			commit(con);
		}else{
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isRefundDonationResult;
	}

	/** 프로젝트 기획자 ID를 얻어옴 */
	public String getPlannerId(int project_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		String plannerId = projectDAO.selectPlannerId(project_id);
		System.out.println("[RefundAllDonationService] getPlannerId() : plannerId = "+plannerId);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return plannerId;
	}
	
	/** 프로젝트 기획자 ID로 프로젝트기획자의 회원정보를 얻어옴 */
	public MemberBean getPlannerInfo(String member_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		UserDAO userDAO = UserDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		userDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		MemberBean userInfo = userDAO.selectUserInfo(member_id);
		System.out.println("[RefundAllDonationService] getPlannerInfo() : userInfo = "+userInfo);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return userInfo;
	}

	/** 해당 프로젝트의 정보를 얻어냄 */
	public ProjectBean getProjectInfo(int project_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ProjectBean projectInfo = projectDAO.selectProjectDate(project_id);
		System.out.println("[RefundAllDonationService] getProjectInfo() : projectInfo = "+projectInfo);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return projectInfo;
	}
	
	/** 해당 프로젝트의 총 후원자 수를 알아냄 */
	public int getDonationCount(int project_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int donationCount = projectDAO.selectDonationCount(project_id);
		System.out.println("[RefundAllDonationService] getDonationCount() : donationCount = "+donationCount);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return donationCount;
	}
	
	
}
