package svc.admin.manageProject;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import dao.AdminIncomeDAO;
import dao.ManageProjectDAO;
import dao.ProjectDAO;
import dao.UserDAO;
import vo.AdminIncomeBean;
import vo.MemberBean;
import vo.ProjectPlannerBean;

public class SendTotalAmountService {

	//필드 없음

	//기본생성자
	
	//메서드
	/** 프로젝트 정보를 얻어옴 */
	public ProjectPlannerBean getProjectPlannerInfo(int project_id) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ProjectPlannerBean projectPlanner = projectDAO.selectProjectPlannerInfo(project_id);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return projectPlanner;
	}
	
	/** 플래너의 회원 정보를 얻어옴 */
	public MemberBean getPlannerInfo(String member_id) {
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
	
	/** 관리자에게 수수료 수익을 insert, 기획자 계좌로 송금하고 프로젝트 상태를 remitcom으로 변경 */
	public boolean sendAmountPlanner(String member_id, int finalAmount, int project_id, int fee_income) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		projectDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		
		//관리자에게 수수료 수익을 insert
		int insertAdminIncomeCount = projectDAO.insertAdminIncome(project_id, fee_income);
		System.out.println("[SendTotalAmountService] sendAmountPlanner : insertAdminIncomeCount = "+insertAdminIncomeCount);
		
		//기획자에게 돈을 송금 (기획자의 돈을 update)
		int updateUserPlusMoneyCount = projectDAO.updateUserPlusMoney(member_id, finalAmount);
		System.out.println("[SendTotalAmountService] member_id = "+member_id+", finalAmount = "+finalAmount);
		System.out.println("[SendTotalAmountService] sendAmountPlanner : updateUserPlusMoneyCountt = " + updateUserPlusMoneyCount);
		
		//프로젝트 상태를 remitcom으로 변경 (remittance completed)
		int updateProjectStatusCount = projectDAO.updateProjectStatus(project_id, "remitcom");
		System.out.println("[SendTotalAmountService] sendAmountPlanner : updateProjectStatusCountt = " + updateProjectStatusCount);
		
		boolean isSendAmountPlannerResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(insertAdminIncomeCount > 0 && updateUserPlusMoneyCount > 0 && updateProjectStatusCount > 0) {
			isSendAmountPlannerResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isSendAmountPlannerResult;		
	}
	
}
