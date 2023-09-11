package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.AddressBean;
import vo.DonationBean;
import vo.MemberBean;
import vo.ProjectBean;
import vo.QnaBean;
import vo.RewardBean;

public class ManageProjectDAO {

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
	private ManageProjectDAO() {}
	
	
	
	private static ManageProjectDAO manageProjectDAO; //static메서드인 getInstance에서 쓸 수 있게 static (단 외부에서 직접 접근 불가능하도록 private)
	
	public static ManageProjectDAO getInstance() {
		if(manageProjectDAO == null) {//DogDAO객체가 없으면
			manageProjectDAO = new ManageProjectDAO();//객체 생성
		}
		
		return manageProjectDAO;//기존 DogDAO객체의 주소 리턴
	}
	
	/******************************************************************/
	
	/**
	 * 1. "DB연결(Service)" -> 2. SQL 실행 -> 3. 결과처리 -> 4. DB연결해제(Service)
	 */
	//DB연결 : 매개값으로 받은 Connection객체의 주소값을 필드con에 셋팅
	public void setConnection(Connection con){
		this.con = con;
	}
	
	/* *************************************************************************
	 *  기부 프로젝트 관리 
	 * *************************************************************************/
	
	//1. 모든 기부 프로젝트 수를 얻어옴
	public int selectDonateProjectCount() {
		int projectCount = 0;
		
		String sql = "select count(*) from project_tbl where kind='donate'";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				projectCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] selectProjectCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectCount;
	}
	
	//2. 전체 기부프로젝트 중 조건에 맞는 프로젝트 수를 얻어옴
	public int searchDonateProjectCount(String project_title) {
		int searchProjectCount = 0;
		
		String sql = "select count(*) from project_tbl where kind='donate' and title regexp ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, project_title);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				searchProjectCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchDonateProjectCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return searchProjectCount;
	}
	
	//3. 기부프로젝트 중 상태별 프로젝트 수를 얻어옴
	public int selectStatusDonateCount(String status) {
		int statusDonateCount = 0;
		
		String sql = "select count(*) from project_tbl where kind = 'donate' and status=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				statusDonateCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] selectStatusDonateCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return statusDonateCount;
	}
	
	//4. 기부 프로젝트에서 상태별 프로젝트 중 조건에 맞는 회원 수를 얻어옴
	public int searchStatusDonateCount(String status, String project_title) {
		int searchStatusDonateCount = 0;
		
		String sql = "select count(*) from member_tbl"
			   	  + " where kind = 'donate' and status=?"
			   	  + " and title regexp ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setString(2, project_title);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				searchStatusDonateCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchStatusDonateCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return searchStatusDonateCount;
	}
	
	
	//5. 기부프로젝트 중 종료된 프로젝트 수를 얻어옴
		public int selectDoneDonateCount() {
			int doneDonateCount = 0;
			
			String sql = "select count(*)"
					  + " from project_tbl"
					  + " where kind = 'donate'"
					  + " and (status='done' or status='success')";
			
			try {
				
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					doneDonateCount = rs.getInt(1);
				}
				
			} catch(Exception e) {
				System.out.println("[ManageProjectDAO] selectDoneDonateCount() 에러 : "+e);//예외객체종류 + 예외메시지
			} finally {
				close(pstmt); //JdbcUtil.생략가능
				close(rs); //JdbcUtil.생략가능
				//connection 객체에 대한 해제는 DogListService에서 이루어짐
			}
			
			return doneDonateCount;
		}
	
	//6. 기부 프로젝트에서 종료된 프로젝트 중 조건에 맞는 회원 수를 얻어옴
	public int searchDoneDonateCount(String project_title) {
		int searchDoneDonateCount = 0;
		
		String sql = "select count(*) from member_tbl"
			   	  + " where kind = 'donate'"
			   	  + " and (status='done' or status='success')"
			   	  + " and title regexp ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, project_title);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				searchDoneDonateCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchDoneDonateCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return searchDoneDonateCount;
	}
	
	
/* [기부 프로젝트 목록 조회] -----------------------------------------------------------------------------------------------*/
	
	//1. 원하는 페이지의 원하는 개수만큼 전체 기부프로젝트 목록을 가져옴 (최근등록순)
	public ArrayList<ProjectBean> selectDonateProjectList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		//최근 가입한 회원순(탈퇴회원도 포함) (관리자 제외)
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = 'donate'"
				  + " order by regdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				projectList = new ArrayList<>();
				
				projectList.add(new ProjectBean(rs.getInt("project_id"),
												rs.getString("kind"),
												rs.getString("title"),
												rs.getString("status"),
												rs.getString("regdate_F")
												)
								);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] selectDonateProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//2. 원하는 페이지의 원하는 개수만큼 조건에 맞는 기부프로젝트 목록을 가져옴 (전체 기부프로젝트)
	public ArrayList<ProjectBean> searchDonateProjectList(String project_title, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		//최근 가입한 회원순(탈퇴회원도 포함) (관리자 제외)
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = 'donate'"
				  + " and title regexp ?"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, project_title);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchDonateProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//3. 원하는 페이지의 원하는 개수만큼 상태별 프로젝트 목록을 가져옴 (최근등록순 [기본값]) 
	public ArrayList<ProjectBean> selectStatusDonateProjectList(String status, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = 'donate'"
				  + " and status=?"
				  + " order by regdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate_F")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] selectStatusDonateProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//4. 원하는 페이지의 원하는 개수만큼 조건에 맞는 상태별 기부프로젝트 목록을 가져옴
	public ArrayList<ProjectBean> searchStatusDonateProjectList(String status, String project_title, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = 'donate'"
				  + " and status=? and title regexp ?"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setString(2, project_title);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchStatusDonateProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//5. 원하는 페이지의 원하는 개수만큼 종료된 프로젝트 목록을 가져옴 (최근등록순 [기본값]) 
	public ArrayList<ProjectBean> selectDoneDonateProjectList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = 'donate'"
				  + " and (status='done' or status='success')"
				  + " order by regdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate_F")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] selectDoneDonateProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//6. 원하는 페이지의 원하는 개수만큼 조건에 맞는 종료된 기부프로젝트 목록을 가져옴
	public ArrayList<ProjectBean> searchDoneDonateProjectList(String project_title, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = 'donate'"
				  + " and (status='done' or status='success')"
				  + " and title regexp ?"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, project_title);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchDoneDonateProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}

	/* 정렬하여 조회 -------------------------------------------------------------------------------------------------------*/
	
	//5. 선택한 기준에 따라 정렬된 전체 회원 목록 가져오기
	//5-1. 최근가입일순
	public ArrayList<ProjectBean> orderNewDonateList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = 'donate'"
				  + " order by regdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate_F")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderNewDonateList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//5-2. 오래된 가입일순
	public ArrayList<ProjectBean> orderOldDonateList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = 'donate'"
				  + " order by regdate asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate_F")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderOldDonateList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//5-3. 가나다순
	public ArrayList<ProjectBean> orderAZDonateList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = 'donate'"
				  + " order by title asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderAZDonateList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//5-4. 역가나다순
	public ArrayList<ProjectBean> orderZADonateList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = 'donate'"
				  + " order by title desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderZADonateList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	
	//6. 선택한 기준에 따라 정렬된 상태별 기부 프로젝트 목록 가져오기
	//6-1. 최근가입일순
	public ArrayList<ProjectBean> orderNewStatusDonateList(String status, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = 'donate' and status=?"
				  + " order by regdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate_F")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderNewStatusDonateList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//6-2. 오래된 가입일순
	public ArrayList<ProjectBean> orderOldStatusDonateList(String status, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = 'donate' and status=?"
				  + " order by regdate asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderOldStatusDonateList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//6-3. 가나다순
	public ArrayList<ProjectBean> orderAZStatusDonateList(String status, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = 'donate' and status=?"
				  + " order by title asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderAZStatusDonateList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//6-4. 역가나다순
	public ArrayList<ProjectBean> orderZAStatusDonateList(String status, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = 'donate' and status=?"
				  + " order by title desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderZAStatusDonateList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//7. 선택한 기준에 따라 정렬된 종료된 기부프로젝트 목록 가져오기
	//7-1. 최근가입일순
	public ArrayList<ProjectBean> orderNewDoneDonateList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = 'donate'"
				  + " and (status='done' or status='success')"
				  + " order by regdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate_F")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderNewDoneDonateList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//7-2. 오래된 가입일순
	public ArrayList<ProjectBean> orderOldDoneDonateList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = 'donate'"
				  + " and (status='done' or status='success')"
				  + " order by regdate asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderOldDoneDonateList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//7-3. 가나다순
	public ArrayList<ProjectBean> orderAZDoneDonateList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = 'donate'"
				  + " and (status='done' or status='success')"
				  + " order by title asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderAZDoneDonateList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//7-4. 역가나다순
	public ArrayList<ProjectBean> orderZADoneDonateList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = 'donate'"
				  + " and (status='done' or status='success')"
				  + " order by title desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderZADoneDonateList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}

	/* *************************************************************************
	 *  펀딩 프로젝트 관리 
	 * *************************************************************************/
	
	//1. 모든 펀딩 프로젝트 수를 얻어옴
	public int selectFundProjectCount() {
		int projectCount = 0;
		
		String sql = "select count(*) from project_tbl where kind='fund'";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				projectCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] selectFundProjectCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectCount;
	}
	
	//2. 전체 펀딩프로젝트 중 조건에 맞는 프로젝트 수를 얻어옴
	public int searchFundProjectCount(String project_title) {
		int searchProjectCount = 0;
		
		String sql = "select count(*) from project_tbl where kind='fund' and title regexp ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, project_title);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				searchProjectCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchFundProjectCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return searchProjectCount;
	}
	
	//3. 펀딩프로젝트 중 상태별 프로젝트 수를 얻어옴
	public int selectStatusFundCount(String status) {
		int statusDonateCount = 0;
		
		String sql = "select count(*) from project_tbl where kind = 'fund' and status=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				statusDonateCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] selectStatusFundCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return statusDonateCount;
	}
	
	//4. 펀딩 프로젝트에서 상태별 프로젝트 중 조건에 맞는 회원 수를 얻어옴
	public int searchStatusFundCount(String status, String project_title) {
		int searchStatusDonateCount = 0;
		
		String sql = "select count(*) from member_tbl"
			   	  + " where kind = 'fund' and status=?"
			   	  + " and title regexp ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setString(2, project_title);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				searchStatusDonateCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchStatusFundCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return searchStatusDonateCount;
	}
	
	
	//5. 펀딩프로젝트 중 종료된 프로젝트 수를 얻어옴
		public int selectDoneFundCount() {
			int doneDonateCount = 0;
			
			String sql = "select count(*)"
					  + " from project_tbl"
					  + " where kind = 'fund'"
					  + " and (status='done' or status='success')";
			
			try {
				
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					doneDonateCount = rs.getInt(1);
				}
				
			} catch(Exception e) {
				System.out.println("[ManageProjectDAO] selectDoneFundCount() 에러 : "+e);//예외객체종류 + 예외메시지
			} finally {
				close(pstmt); //JdbcUtil.생략가능
				close(rs); //JdbcUtil.생략가능
				//connection 객체에 대한 해제는 DogListService에서 이루어짐
			}
			
			return doneDonateCount;
		}
	
	//6. 펀딩 프로젝트에서 종료된 프로젝트 중 조건에 맞는 회원 수를 얻어옴
	public int searchDoneFundCount(String project_title) {
		int searchDoneDonateCount = 0;
		
		String sql = "select count(*) from member_tbl"
			   	  + " where kind = 'fund'"
			   	  + " and (status='done' or status='success')"
			   	  + " and title regexp ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, project_title);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				searchDoneDonateCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchDoneFundCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return searchDoneDonateCount;
	}
	
	
/* [펀딩 프로젝트 목록 조회] -----------------------------------------------------------------------------------------------*/
	
	//1. 원하는 페이지의 원하는 개수만큼 전체 펀딩프로젝트 목록을 가져옴 (최근등록순)
	public ArrayList<ProjectBean> selectFundProjectList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		//최근 가입한 회원순(탈퇴회원도 포함) (관리자 제외)
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = 'fund'"
				  + " order by regdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				projectList = new ArrayList<>();
				
				projectList.add(new ProjectBean(rs.getInt("project_id"),
												rs.getString("kind"),
												rs.getString("title"),
												rs.getString("status"),
												rs.getString("regdate_F")
												)
								);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] selectFundProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//2. 원하는 페이지의 원하는 개수만큼 조건에 맞는 펀딩프로젝트 목록을 가져옴 (전체 펀딩프로젝트)
	public ArrayList<ProjectBean> searchFundProjectList(String project_title, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		//최근 가입한 회원순(탈퇴회원도 포함) (관리자 제외)
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = 'fund'"
				  + " and title regexp ?"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, project_title);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchFundProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//3. 원하는 페이지의 원하는 개수만큼 상태별 프로젝트 목록을 가져옴 (최근등록순 [기본값]) 
	public ArrayList<ProjectBean> selectStatusFundProjectList(String status, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = 'fund'"
				  + " and status=?"
				  + " order by regdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate_F")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] selectStatusFundProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//4. 원하는 페이지의 원하는 개수만큼 조건에 맞는 상태별 펀딩프로젝트 목록을 가져옴
	public ArrayList<ProjectBean> searchStatusFundProjectList(String status, String project_title, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = 'fund'"
				  + " and status=? and title regexp ?"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setString(2, project_title);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchStatusFundProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//5. 원하는 페이지의 원하는 개수만큼 종료된 프로젝트 목록을 가져옴 (최근등록순 [기본값]) 
	public ArrayList<ProjectBean> selectDoneFundProjectList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = 'fund'"
				  + " and (status='done' or status='success')"
				  + " order by regdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate_F")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] selectDoneFundProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//6. 원하는 페이지의 원하는 개수만큼 조건에 맞는 종료된 펀딩프로젝트 목록을 가져옴
	public ArrayList<ProjectBean> searchDoneFundProjectList(String project_title, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = 'fund'"
				  + " and (status='done' or status='success')"
				  + " and title regexp ?"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, project_title);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchDoneFundProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}

	/* 정렬하여 조회 -------------------------------------------------------------------------------------------------------*/
	
	//5. 선택한 기준에 따라 정렬된 전체 회원 목록 가져오기
	//5-1. 최근가입일순
	public ArrayList<ProjectBean> orderNewFundList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = 'fund'"
				  + " order by regdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate_F")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderNewFundList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//5-2. 오래된 가입일순
	public ArrayList<ProjectBean> orderOldFundList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = 'fund'"
				  + " order by regdate asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate_F")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderOldFundList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//5-3. 가나다순
	public ArrayList<ProjectBean> orderAZFundList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = 'fund'"
				  + " order by title asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderAZFundList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//5-4. 역가나다순
	public ArrayList<ProjectBean> orderZAFundList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = 'fund'"
				  + " order by title desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderZAFundList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	
	//6. 선택한 기준에 따라 정렬된 상태별 펀딩 프로젝트 목록 가져오기
	//6-1. 최근가입일순
	public ArrayList<ProjectBean> orderNewStatusFundList(String status, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = 'fund' and status=?"
				  + " order by regdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate_F")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderNewStatusFundList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//6-2. 오래된 가입일순
	public ArrayList<ProjectBean> orderOldStatusFundList(String status, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = 'fund' and status=?"
				  + " order by regdate asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderOldStatusFundList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//6-3. 가나다순
	public ArrayList<ProjectBean> orderAZStatusFundList(String status, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = 'fund' and status=?"
				  + " order by title asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderAZStatusFundList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//6-4. 역가나다순
	public ArrayList<ProjectBean> orderZAStatusFundList(String status, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = 'fund' and status=?"
				  + " order by title desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderZAStatusFundList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//7. 선택한 기준에 따라 정렬된 종료된 펀딩프로젝트 목록 가져오기
	//7-1. 최근가입일순
	public ArrayList<ProjectBean> orderNewDoneFundList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = 'fund'"
				  + " and (status='done' or status='success')"
				  + " order by regdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate_F")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderNewDoneFundList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//7-2. 오래된 가입일순
	public ArrayList<ProjectBean> orderOldDoneFundList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = 'fund'"
				  + " and (status='done' or status='success')"
				  + " order by regdate asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderOldDoneFundList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//7-3. 가나다순
	public ArrayList<ProjectBean> orderAZDoneFundList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = 'fund'"
				  + " and (status='done' or status='success')"
				  + " order by title asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderAZDoneFundList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//7-4. 역가나다순
	public ArrayList<ProjectBean> orderZADoneFundList(int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = 'fund'"
				  + " and (status='done' or status='success')"
				  + " order by title desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderZADoneFundList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}


	
}
