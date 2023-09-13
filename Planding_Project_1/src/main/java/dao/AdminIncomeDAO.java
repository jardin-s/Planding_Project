package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.AdminIncomeBean;

public class AdminIncomeDAO {
	
	//필드 : 전역변수 - 전체 메서드에서 사용가능하도록 선언
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	
	
	/** 싱글톤 패턴 : DogDAO 객체를 단 1개만 생성 **************************
	 * 이유? 외부 클래스에서 '처음 생성된 AdminDAO객체를 공유해어 사용하도록' 하기 위함
	 * - 생성자를 private으로 하여 외부에서 객체 생성을 막음
	 * - getInstance()메서드로 객체를 하나만 생성
	 */
	
	//1. 생성자는 무조건 private : 외부에서 객체 생성 불가능
	private AdminIncomeDAO() {}
	
	
	
	private static AdminIncomeDAO adminIncomeDAO; //static메서드인 getInstance에서 쓸 수 있게 static (단 외부에서 직접 접근 불가능하도록 private)
	
	public static AdminIncomeDAO getInstance() {
		if(adminIncomeDAO == null) {//AdminIncomeDAO객체가 없으면
			adminIncomeDAO = new AdminIncomeDAO();//객체 생성
		}
		
		return adminIncomeDAO;//기존 AdminIncomeDAO객체의 주소 리턴
	}
	
	/******************************************************************/
	
	/**
	 * 1. "DB연결(Service)" -> 2. SQL 실행 -> 3. 결과처리 -> 4. DB연결해제(Service)
	 */
	//DB연결 : 매개값으로 받은 Connection객체의 주소값을 필드con에 셋팅
	public void setConnection(Connection con){
		this.con = con;
	}

	
	//1. 지정한 날짜 범위에 있는 수익기록 목록을 가져옴
	public ArrayList<AdminIncomeBean> selectIncomeDateList(String db_startDate, String db_endDate){
		
		ArrayList<AdminIncomeBean> adminIncomeList = null;
		
		String sql = "select project_id, fee_income,"
				  + " DATE_FORMAT(incomedate,'%Y.%m.%d') as incomedate"
				  + " from admin_income_tbl"
				  + " where incomedate between ? and ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, db_startDate);
			pstmt.setString(2, db_endDate);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				adminIncomeList = new ArrayList<>();
				
				do {
					
					adminIncomeList.add(new AdminIncomeBean(rs.getInt("project_id"),
															rs.getInt("fee_income"),
															rs.getString("incomedate")
															)
										);
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[AdminIncomeDAO] selectIncomeDateList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return adminIncomeList;
		
	}
	
	
	//2. 특정 날짜의 수익기록 목록을 가져옴
	public ArrayList<AdminIncomeBean> selectDayIncomeList(String searchDate){
		
		ArrayList<AdminIncomeBean> adminIncomeList = null;
		
		String sql = "select project_id, fee_income,"
				  + " DATE_FORMAT(incomedate,'%Y.%m.%d') as incomedate"
				  + " from admin_income_tbl"
				  + " where incomedate = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchDate);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				adminIncomeList = new ArrayList<>();
				
				do {
					
					adminIncomeList.add(new AdminIncomeBean(rs.getInt("project_id"),
															rs.getInt("fee_income"),
															rs.getString("incomedate")
															)
										);
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[AdminIncomeDAO] selectDayIncomeList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return adminIncomeList;
		
	}
	
	
	//3. 모든 수익기록 목록을 가져옴 (최근순)
	public ArrayList<AdminIncomeBean> selectAllIncomeList(){
		
		ArrayList<AdminIncomeBean> adminIncomeList = null;
		
		String sql = "select project_id, fee_income,"
				  + " DATE_FORMAT(incomedate,'%Y.%m.%d') as incomedate"
				  + " from admin_income_tbl"
				  + " order by incomedate desc";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				adminIncomeList = new ArrayList<>();
				
				do {
					
					adminIncomeList.add(new AdminIncomeBean(rs.getInt("project_id"),
															rs.getInt("fee_income"),
															rs.getString("incomedate")
															)
										);
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[AdminIncomeDAO] selectAllIncomeList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return adminIncomeList;
		
	}
	

	
}
