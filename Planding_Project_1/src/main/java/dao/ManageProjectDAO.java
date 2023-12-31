package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.ProjectAdminIncomeBean;
import vo.ProjectBean;


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
	
	//1. 모든 기부/펀딩 프로젝트 수를 얻어옴
	public int selectProjectCount(String kind) {
		int projectCount = 0;
		
		String sql = "select count(*) from project_tbl where kind=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
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
	
	//2. 전체 기부/펀딩프로젝트 중 조건에 맞는 프로젝트 수를 얻어옴
	public int searchProjectCount(String kind, String project_title) {
		int searchProjectCount = 0;
		
		String sql = "select count(*) from project_tbl where kind=? and title regexp ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, project_title);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				searchProjectCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchProjectCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return searchProjectCount;
	}
	
	//3. 기부/펀딩프로젝트 중 상태별 프로젝트 수를 얻어옴
	public int selectStatusProjectCount(String kind, String p_status) {
		int selectStatusProjectCount = 0;
		
		String sql = "select count(*) from project_tbl where kind = ? and p_status=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, p_status);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				selectStatusProjectCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] selectStatusProjectCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return selectStatusProjectCount;
	}
	
	//4. 기부/펀딩 프로젝트에서 상태별 프로젝트 중 조건에 맞는 회원 수를 얻어옴
	public int searchStatusProjectCount(String kind, String p_status, String project_title) {
		int searchStatusProjectCount = 0;
		
		String sql = "select count(*) from project_tbl"
			   	  + " where kind = ? and p_status=?"
			   	  + " and title regexp ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, p_status);
			pstmt.setString(3, project_title);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				searchStatusProjectCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchStatusDonateCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return searchStatusProjectCount;
	}
	
	
	//5. 펀딩프로젝트 중 종료된 프로젝트 수를 얻어옴
	public int selectDoneClearProjectCount(String kind) {
		int doneProjectCount = 0;
		
		String sql = "select count(*)"
				  + " from project_tbl"
				  + " where kind = ?"
				  + " and (p_status='done' or p_status='clear')";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				doneProjectCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] selectDoneClearProjectCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return doneProjectCount;
	}
	
	//5. 기부/펀딩프로젝트 중 성공한 프로젝트 수를 얻어옴
	public int selectSuccessRemitcomProjectCount(String kind) {
		int doneProjectCount = 0;
		
		String sql = "select count(*)"
				+ " from project_tbl"
				+ " where kind = ?"
				+ " and (p_status='success' or p_status='remitcom')";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				doneProjectCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] selectSuccessRemitcomProjectCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return doneProjectCount;
	}
	
	//6. 기부/펀딩 프로젝트에서 종료된 프로젝트 중 조건에 맞는 회원 수를 얻어옴
	public int searchDoneProjectCount(String kind, String project_title) {
		int searchDoneProjectCount = 0;
		
		String sql = "select count(*) from member_tbl"
			   	  + " where kind = ?"
			   	  + " and (p_status='done' or p_status='clear')"
			   	  + " and title regexp ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, project_title);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				searchDoneProjectCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchDoneProjectCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return searchDoneProjectCount;
	}
	
	//6. 기부/펀딩 프로젝트에서 성공한 프로젝트 중 조건에 맞는 회원 수를 얻어옴
	public int searchSuccessProjectCount(String kind, String project_title) {
		int searchDoneProjectCount = 0;
		
		String sql = "select count(*) from member_tbl"
				+ " where kind = ?"
				+ " and (p_status='success' or p_status='remitcom')"
				+ " and title regexp ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, project_title);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				searchDoneProjectCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchSuccessProjectCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return searchDoneProjectCount;
	}
	
	
/* [프로젝트 목록 조회] -----------------------------------------------------------------------------------------------*/
	
	//1. 원하는 페이지의 원하는 개수만큼 전체 기부/펀딩프로젝트 목록을 가져옴 (최근등록순)
	public ArrayList<ProjectBean> selectProjectList(String kind, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = ?"
				  + " order by regdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				projectList = new ArrayList<>();
				
				do {
				projectList.add(new ProjectBean(rs.getInt("project_id"),
												rs.getString("kind"),
												rs.getString("title"),
												rs.getString("p_status"),
												rs.getString("regdate_F")
												)
								);
				
				System.out.println("[ManageProjectDAO] selectProjectList");
				System.out.println("조회한 project_id = "+rs.getInt("project_id"));
				System.out.println("조회한 kind = "+rs.getString("kind"));
				System.out.println("조회한 title = "+rs.getString("title"));
				System.out.println("조회한 p_status = "+rs.getString("p_status"));
				System.out.println("조회한 regdate_F = "+rs.getString("regdate_F"));
				
				}while(rs.next());
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] selectProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
		
	//2. 원하는 페이지의 원하는 개수만큼 조건에 맞는 기부/펀딩프로젝트 목록을 가져옴 (전체 기부프로젝트)
	public ArrayList<ProjectBean> searchProjectList(String kind, String project_title, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		//최근 가입한 회원순(탈퇴회원도 포함) (관리자 제외)
		String sql = "select project_id, kind, title, p_status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = ?"
				  + " and title regexp ?"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
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
													rs.getString("p_status"),
													rs.getString("regdate")
													)
									);					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//3. 원하는 페이지의 원하는 개수만큼 상태별 프로젝트 목록을 가져옴 (최근등록순 [기본값]) 
	public ArrayList<ProjectBean> selectStatusProjectList(String kind, String p_status, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = ?"
				  + " and p_status=?"
				  + " order by regdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, p_status);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("p_status"),
													rs.getString("regdate_F")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] selectStatusProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//4. 원하는 페이지의 원하는 개수만큼 조건에 맞는 상태별 기부/펀딩프로젝트 목록을 가져옴
	public ArrayList<ProjectBean> searchStatusProjectList(String kind, String p_status, String project_title, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = ?"
				  + " and p_status=? and title regexp ?"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, p_status);
			pstmt.setString(3, project_title);
			pstmt.setInt(4, startrow);
			pstmt.setInt(5, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("p_status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchStatusProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//5. 원하는 페이지의 원하는 개수만큼 종료된 프로젝트 목록을 가져옴 (최근등록순 [기본값]) 
	public ArrayList<ProjectBean> selectDoneProjectList(String kind, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = ?"
				  + " and (p_status='done' or p_status='clear')"
				  + " order by regdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("p_status"),
													rs.getString("regdate_F")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] selectDoneProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//5. 원하는 페이지의 원하는 개수만큼 성공한 프로젝트 목록을 가져옴 (최근등록순 [기본값]) 
	public ArrayList<ProjectBean> selectSuccessProjectList(String kind, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				+ " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				+ " from project_tbl"
				+ " where kind = ?"
				+ " and (p_status='success' or p_status='remitcom')"
				+ " order by regdate desc"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
							rs.getString("kind"),
							rs.getString("title"),
							rs.getString("p_status"),
							rs.getString("regdate_F")
							)
							);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] selectDoneProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//6. 원하는 페이지의 원하는 개수만큼 조건에 맞는 종료된 기부/펀딩프로젝트 목록을 가져옴
	public ArrayList<ProjectBean> searchDoneProjectList(String kind, String project_title, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = ?"
				  + " and (p_status='done' or p_status='clear')"
				  + " and title regexp ?"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
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
													rs.getString("p_status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchDoneProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//6. 원하는 페이지의 원하는 개수만큼 조건에 맞는 종료된 기부/펀딩프로젝트 목록을 가져옴
	public ArrayList<ProjectBean> searchSuccessProjectList(String kind, String project_title, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				+ " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				+ " from project_tbl"
				+ " where kind = ?"
				+ " and (p_status='success' or p_status='remitcom')"
				+ " and title regexp ?"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
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
							rs.getString("p_status"),
							rs.getString("regdate")
							)
							);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] searchDoneProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}

	/* 정렬하여 조회 -------------------------------------------------------------------------------------------------------*/
	
	//5. 선택한 기준에 따라 정렬된 전체 기부/펀딩 프로젝트 목록 가져오기
	//5-1. 최근가입일순
	public ArrayList<ProjectBean> orderNewProjectList(String kind, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = ?"
				  + " order by regdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("p_status"),
													rs.getString("regdate_F")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderNewProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//5-2. 오래된 가입일순
	public ArrayList<ProjectBean> orderOldProjectList(String kind, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = ?"
				  + " order by regdate asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("p_status"),
													rs.getString("regdate_F")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderOldProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//5-3. 가나다순
	public ArrayList<ProjectBean> orderAZProjectList(String kind, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = ?"
				  + " order by title asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("p_status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderAZProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//5-4. 역가나다순
	public ArrayList<ProjectBean> orderZAProjectList(String kind, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = ?"
				  + " order by title desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("p_status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderZAProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	
	//6. 선택한 기준에 따라 정렬된 상태별 기부/펀딩 프로젝트 목록 가져오기
	//6-1. 최근가입일순
	public ArrayList<ProjectBean> orderNewStatusProjectList(String kind, String p_status, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = ? and p_status=?"
				  + " order by regdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, p_status);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("p_status"),
													rs.getString("regdate_F")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderNewStatusProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//6-2. 오래된 가입일순
	public ArrayList<ProjectBean> orderOldStatusProjectList(String kind, String p_status, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate_F"
				  + " from project_tbl"
				  + " where kind = ? and p_status=?"
				  + " order by regdate asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, p_status);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("p_status"),
													rs.getString("regdate_F")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderOldStatusProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//6-3. 가나다순
	public ArrayList<ProjectBean> orderAZStatusProjectList(String kind, String p_status, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = ? and p_status=?"
				  + " order by title asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, p_status);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("p_status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderAZStatusProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//6-4. 역가나다순
	public ArrayList<ProjectBean> orderZAStatusProjectList(String kind, String p_status, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = ? and p_status=?"
				  + " order by title desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, p_status);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("p_status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderZAStatusProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	//7. 선택한 기준에 따라 정렬된 종료된 기부/펀딩프로젝트 목록 가져오기
	//7-1. 최근마감순
	public ArrayList<ProjectBean> orderNewDoneProjectList(String kind, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = ?"
				  + " and (p_status='done' or p_status='clear')"
				  + " order by enddate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("p_status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderNewDoneProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	public ArrayList<ProjectBean> orderNewSuccessProjectList(String kind, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				+ " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				+ " from project_tbl"
				+ " where kind = ?"
				+ " and (p_status='remitcom' or p_status='success')"
				+ " order by enddate desc"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
							rs.getString("kind"),
							rs.getString("title"),
							rs.getString("p_status"),
							rs.getString("regdate_F")
							)
							);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderNewDoneProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//7-2. 오래된 마감순
	public ArrayList<ProjectBean> orderOldDoneProjectList(String kind, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = ?"
				  + " and (p_status='done' or p_status='clear')"
				  + " order by enddate asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("p_status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderOldDoneProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	public ArrayList<ProjectBean> orderOldSuccessProjectList(String kind, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				+ " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				+ " from project_tbl"
				+ " where kind = ?"
				+ " and (p_status='remitcom' or p_status='success')"
				+ " order by enddate asc"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
							rs.getString("kind"),
							rs.getString("title"),
							rs.getString("p_status"),
							rs.getString("regdate")
							)
							);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderOldDoneProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//7-3. 가나다순
	public ArrayList<ProjectBean> orderAZDoneProjectList(String kind, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = ?"
				  + " and (p_status='done' or p_status='clear')"
				  + " order by title asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("p_status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderAZDoneProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	public ArrayList<ProjectBean> orderAZSuccessProjectList(String kind, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				+ " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				+ " from project_tbl"
				+ " where kind = ?"
				+ " and (p_status='remitcom' or p_status='success')"
				+ " order by title asc"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
							rs.getString("kind"),
							rs.getString("title"),
							rs.getString("p_status"),
							rs.getString("regdate")
							)
							);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderAZDoneProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	//7-4. 역가나다순
	public ArrayList<ProjectBean> orderZADoneProjectList(String kind, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				  + " from project_tbl"
				  + " where kind = ?"
				  + " and (p_status='done' or p_status='clear')"
				  + " order by title desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
													rs.getString("kind"),
													rs.getString("title"),
													rs.getString("p_status"),
													rs.getString("regdate")
													)
									);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderZADoneProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	public ArrayList<ProjectBean> orderZASuccessProjectList(String kind, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				+ " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				+ " from project_tbl"
				+ " where kind = ?"
				+ " and (p_status='remitcom' or p_status='success')"
				+ " order by title desc"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
							rs.getString("kind"),
							rs.getString("title"),
							rs.getString("p_status"),
							rs.getString("regdate")
							)
							);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderZADoneProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	/** 종료 프로젝트 중 미환불 프로젝트 우선정렬 (done 미환불, clear 환불완) */
	public ArrayList<ProjectBean> orderUnsentDoneProjectList(String kind, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				+ " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				+ " from project_tbl"
				+ " where kind = ?"
				+ " and (p_status='done' or p_status='clear')"
				+ " order by p_status desc"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
							rs.getString("kind"),
							rs.getString("title"),
							rs.getString("p_status"),
							rs.getString("regdate")
							)
							);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderZADoneProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	/** 성공 프로젝트 중 미송금 프로젝트 우선정렬 (success 미송금, remitcom 송금완) */
	public ArrayList<ProjectBean> orderUnsentSuccessProjectList(String kind, int page, int limit) {
		ArrayList<ProjectBean> projectList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		String sql = "select project_id, kind, title, p_status,"
				+ " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				+ " from project_tbl"
				+ " where kind = ?"
				+ " and (p_status='remitcom' or p_status='success')"
				+ " order by p_status desc"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectList = new ArrayList<>();
				
				do {
					projectList.add(new ProjectBean(rs.getInt("project_id"),
							rs.getString("kind"),
							rs.getString("title"),
							rs.getString("p_status"),
							rs.getString("regdate")
							)
							);
					
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] orderZADoneProjectList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectList;
	}
	
	
	
	/**********************************************************************************
	 * 프로젝트 승인, 취소, 삭제 등	 *
	 * ********************************************************************************/
	
	/** 미승인 프로젝트 승인 */
	public int authorizeProject(int project_id) {
		int authorizeProjectCount = 0;
		
		String sql = "update project_tbl"
				  + " set p_status = 'ready'"
				  + " where project_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, project_id);
			authorizeProjectCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] authorizeProject() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return authorizeProjectCount;
	}
	
	/** 프로젝트 승인 거절 시 (1) - 프로젝트 기획자 삭제 */
	public int deletePlanner(int project_id) {
		int deletePlannerCount = 0;
		
		String sql = "delete from project_planner_tbl"
				  + " where project_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, project_id);
			
			deletePlannerCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] deletePlanner() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return deletePlannerCount;
	}
	
	/** 프로젝트 승인 거절 시 (2) - 프로젝트-리워드 매핑 데이터 삭제 */
	public int deleteProjectRewardMap(int project_id) {
		int deleteProjectRewardMapCount = 0;
		
		String sql = "delete from project_reward_tbl"
				  + " where project_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, project_id);
			
			deleteProjectRewardMapCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] deleteProjectRewardMap() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return deleteProjectRewardMapCount;
	}
	
	/** 프로젝트 승인 거절 시 (2) - (기본리워드 제외) 프로젝트에 등록된 리워드 삭제 */
	public int deleteReward(String reward_id) {
		int deleteRewardCount = 0;
		
		String sql = "delete from reward_tbl where reward_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, reward_id);
			
			deleteRewardCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] deleteReward() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return deleteRewardCount;
	}
		
	/** 프로젝트 승인 거절 시 (4) - 프로젝트 삭제 */
	public int deleteProject(int project_id) {
		int deleteProjectCount = 0;
		
		String sql = "delete from project_tbl"
				  + " where project_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, project_id);
			deleteProjectCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] deleteProject() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return deleteProjectCount;
	}

	/** 회원의 계좌 잔액을 업데이트 (더하기) */
	public int updateUserPlusMoney(String member_id, int money) {
		
		int updateUserMoneyCount = 0;
		
		String sql = "update member_tbl"
				  + " set money = money + ?"
				  + " where member_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, money);
			pstmt.setString(2, member_id);
			
			updateUserMoneyCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] updateUserPlusMoney() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return updateUserMoneyCount;
	}

	/** 특정 프로젝트의 모든 후원기록을 삭제? */
	public int deleteDonation(int project_id) {
		int deleteDonationCount = 0;
		
		String sql = "delete from donation_tbl"
				  + " where project_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, project_id);
			
			deleteDonationCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[ManageProjectDAO] deleteDonation() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return deleteDonationCount;
	}

	
	/** 수익여부? */
	public int selectProjectAdminIncomeCount(int project_id) {
		int selectProjectAdminIncomeCount = 0;
		
		String sql = "select count(*) from admin_income_tbl where project_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, project_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) selectProjectAdminIncomeCount = rs.getInt(1);
			
		} catch(Exception e) {
			System.out.println("[UserDAO] selectProjectAdminIncomeCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return selectProjectAdminIncomeCount;
	}
	
	
	
	
	
}
