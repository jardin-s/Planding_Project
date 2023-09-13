package svc.admin.manageIncome;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import dao.AdminIncomeDAO;
import vo.AdminIncomeBean;

public class ManageIncomeService {

	//필드 없음

	//기본생성자
	
	//메서드
	//1. 날짜범위의 수익 리스트 얻어오기
	public ArrayList<AdminIncomeBean> selectList(String db_startDate, String db_endDate) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		AdminIncomeDAO adminIncomeDAO = AdminIncomeDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		adminIncomeDAO.setConnection(con);
		
		System.out.println("[ManageIncomeService]");
		System.out.println("db_startDate = "+db_startDate);
		System.out.println("db_endDate = "+db_endDate);
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<AdminIncomeBean> adminIncomeList = adminIncomeDAO.selectIncomeDateList(db_startDate, db_endDate);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return adminIncomeList;
	}

}
