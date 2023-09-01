package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


import util.SHA256;
import vo.AddressBean;
import vo.MemberBean;
import vo.MemberPwChangeBean;
import vo.ProjectBean;

public class ProjectDAO {
	
	//필드 : 전역변수 - 전체 메서드에서 사용가능하도록 선언
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	
	
	/** 싱글톤 패턴 : DogDAO 객체를 단 1개만 생성 **************************
	 * 이유? 외부 클래스에서 '처음 생성된 UserDAO객체를 공유해어 사용하도록' 하기 위함
	 * - 생성자를 private으로 하여 외부에서 객체 생성을 막음
	 * - getInstance()메서드로 객체를 하나만 생성
	 */
	
	//1. 생성자는 무조건 private : 외부에서 객체 생성 불가능
	private ProjectDAO() {}
	
	
	
	private static ProjectDAO projectDAO; //static메서드인 getInstance에서 쓸 수 있게 static (단 외부에서 직접 접근 불가능하도록 private)
	
	public static ProjectDAO getInstance() {
		if(projectDAO == null) {//DogDAO객체가 없으면
			projectDAO = new ProjectDAO();//객체 생성
		}
		
		return projectDAO;//기존 DogDAO객체의 주소 리턴
	}
	
	/******************************************************************/
	
	/**
	 * 1. "DB연결(Service)" -> 2. SQL 실행 -> 3. 결과처리 -> 4. DB연결해제(Service)
	 */
	//DB연결 : 매개값으로 받은 Connection객체의 주소값을 필드con에 셋팅
	public void setConnection(Connection con){
		this.con = con;
	}

	//1. 프로젝트 ID로 프로젝트 정보를 얻어옴
	public ProjectBean getProjectInfo(int p_id) {
		ProjectBean projectInfo = null;
		
		String sql = "select * from project_tbl where project_id=?";
		   	
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, p_id);
						
			if(rs.next()) {
				projectInfo = new ProjectBean(rs.getInt("project_id"),
											  rs.getString("kind"),
											  rs.getString("title"),
											  rs.getString("summary"),
											  rs.getString("thumbnail"),
											  rs.getString("content"),
											  rs.getString("image"),
											  rs.getString("startdate"),
											  rs.getString("enddate"),
											  rs.getInt("goal_amount"),
											  rs.getInt("curr_amount"),
											  rs.getString("category"),
											  rs.getString("status"),
											  rs.getInt("likes")
											  );
			}
			
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] getProjectInfo() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectInfo;
	}
}