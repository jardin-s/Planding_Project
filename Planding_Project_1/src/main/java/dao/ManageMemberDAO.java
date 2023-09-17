package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.AddressBean;
import vo.DonationBean;
import vo.MemberBean;
import vo.QnaBean;
import vo.RewardBean;

public class ManageMemberDAO {

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
	private ManageMemberDAO() {}
	
	
	
	private static ManageMemberDAO manageMemberDAO; //static메서드인 getInstance에서 쓸 수 있게 static (단 외부에서 직접 접근 불가능하도록 private)
	
	public static ManageMemberDAO getInstance() {
		if(manageMemberDAO == null) {//DogDAO객체가 없으면
			manageMemberDAO = new ManageMemberDAO();//객체 생성
		}
		
		return manageMemberDAO;//기존 DogDAO객체의 주소 리턴
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
	 *  회원 관리 
	 * *************************************************************************/
	
	//1. 관리자가 아닌 회원 수를 얻어옴
	public int selectMemberCount() {
		int memberCount = 0;
		
		String sql = "select count(*) from member_tbl where admin_status = 'N'";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				memberCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] selectMembetCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return memberCount;
	}
	
	//2. 관리자가 아닌 회원 중 조건에 맞는 회원 수를 얻어옴
	public int searchMemberCount(String member_id) {
		int searchMemberCount = 0;
		
		String sql = "select count(*) from member_tbl where admin_status = 'N' and member_id regexp ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				searchMemberCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] searchMemberCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return searchMemberCount;
	}
	
	//3. 관리자가 아닌 탈퇴/미탈퇴 회원 수를 얻어옴
	public int selectDeleteUndeletedCount(String delete_status) {
		int memberCount = 0;
		
		String sql = "select count(*) from member_tbl where admin_status = 'N' and delete_status=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, delete_status);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				memberCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] selectDeleteUndeletedCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return memberCount;
	}
	

	//4. 탈퇴/미탈퇴회원 중 조건에 맞는 회원 수를 얻어옴
	public int searchDeleteUndeletedMemberCount(String delete_status, String member_id) {
		int searchMemberCount = 0;
		
		String sql = "select count(*) from member_tbl"
			   	  + " where admin_status = 'N' and delete_status=?"
			   	  + " and member_id regexp ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, delete_status);
			pstmt.setString(2, member_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				searchMemberCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] searchDeleteUndeletedMemberCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return searchMemberCount;
	}
	
	
/* [회원 목록 조회] -----------------------------------------------------------------------------------------------*/
	
	//1. 원하는 페이지의 원하는 개수만큼 회원 목록을 가져옴 (전체 회원, 최근가입순)
	public ArrayList<MemberBean> selectMemberList(int page, int limit) {
		ArrayList<MemberBean> memberList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		//최근 가입한 회원순(탈퇴회원도 포함) (관리자 제외)
		String sql = "select member_id, name, email,"
				  + " phone, account, admin_status,"
				  + " DATE_FORMAT(joindate,'%Y.%m.%d') as joindate_F,"
				  + " delete_status,"
				  + " DATE_FORMAT(deletedate,'%Y.%m.%d') as deletedate"
				  + " from member_tbl"
				  + " where admin_status = 'N'"
				  + " order by joindate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				memberList = new ArrayList<>();
				
				do {
					
					memberList.add(new MemberBean(rs.getString("member_id"),
												  rs.getString("name"),
												  rs.getString("email"),
												  rs.getString("phone"),
												  rs.getInt("account"),
												  rs.getString("joindate_F"),
												  rs.getString("delete_status"),
												  rs.getString("delete_status")
												  )
								  );
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] selectMemberList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return memberList;
	}
	
	//2. 원하는 페이지의 원하는 개수만큼 조건에 맞는 회원 목록을 가져옴 (전체 회원)
	public ArrayList<MemberBean> searchMemberList(String member_id, int page, int limit) {
		ArrayList<MemberBean> memberList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		//최근 가입한 회원순(탈퇴회원도 포함) (관리자 제외)
		String sql = "select member_id, name, email,"
				+ " phone, account, admin_status,"
				+ " DATE_FORMAT(joindate,'%Y.%m.%d') as joindate,"
				+ " delete_status,"
				+ " DATE_FORMAT(deletedate,'%Y.%m.%d') as deletedate"
				+ " from member_tbl"
				+ " where admin_status = 'N'"
				+ " and member_id regexp ?"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				memberList = new ArrayList<>();
				
				do {
					
					memberList.add(new MemberBean(rs.getString("member_id"),
							rs.getString("name"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getInt("account"),
							rs.getString("joindate"),
							rs.getString("delete_status"),
							rs.getString("deletedate")
							)
							);
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] searchMemberList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return memberList;
	}
	
	//3. 원하는 페이지의 원하는 개수만큼 탈퇴/미탈퇴회원 목록을 가져옴 (최근가입순 [기본값]) 
	public ArrayList<MemberBean> selectDeleteUndeletedMemberList(String delete_status, int page, int limit) {
		ArrayList<MemberBean> memberList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		//최근 가입한 회원순(탈퇴회원도 포함) (관리자 제외)
		String sql = "select member_id, name, email,"
				+ " phone, account, admin_status,"
				+ " DATE_FORMAT(joindate,'%Y.%m.%d') as joindate_F,"
				+ " delete_status,"
				+ " DATE_FORMAT(deletedate,'%Y.%m.%d') as deletedate"
				+ " from member_tbl"
				+ " where admin_status = 'N' and delete_status=?"
				+ " order by joindate desc"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, delete_status);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				memberList = new ArrayList<>();
				
				do {
					
					memberList.add(new MemberBean(rs.getString("member_id"),
							rs.getString("name"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getInt("account"),
							rs.getString("joindate_F"),
							rs.getString("delete_status"),
							rs.getString("deletedate")
							)
							);
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] selectDeleteUndeletedMemberList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return memberList;
	}
	
	//4. 원하는 페이지의 원하는 개수만큼 조건에 맞는 탈퇴/미탈퇴회원 목록을 가져옴 (최근가입순 [기본값]) 
	public ArrayList<MemberBean> searchDeleteUndeletedMemberList(String delete_status, String member_id, int page, int limit) {
		ArrayList<MemberBean> memberList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		//최근 가입한 회원순(탈퇴회원도 포함) (관리자 제외)
		String sql = "select member_id, name, email,"
				+ " phone, account, admin_status,"
				+ " DATE_FORMAT(joindate,'%Y.%m.%d') as joindate_F,"
				+ " delete_status,"
				+ " DATE_FORMAT(deletedate,'%Y.%m.%d') as deletedate"
				+ " from member_tbl"
				+ " where admin_status = 'N' and delete_status='?'"
				+ " and member_id LIKE '%?%'"
				+ " order by joindate desc"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, delete_status);
			pstmt.setString(2, member_id);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				memberList = new ArrayList<>();
				
				do {
					
					memberList.add(new MemberBean(rs.getString("member_id"),
							rs.getString("name"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getInt("account"),
							rs.getString("joindate_F"),
							rs.getString("delete_status"),
							rs.getString("deletedate")
							)
							);
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] searchDeleteUndeletedMemberList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return memberList;
	}

	
	//5. 선택한 기준에 따라 정렬된 전체 회원 목록 가져오기
	//5-1. 최근가입일순
	public ArrayList<MemberBean> orderNewMemberList(int page, int limit) {
		ArrayList<MemberBean> memberList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		//(탈퇴회원도 포함) (관리자 제외)
		String sql = "select member_id, name, email,"
				+ " phone, account, admin_status,"
				+ " DATE_FORMAT(joindate,'%Y.%m.%d') as joindate_F,"
				+ " delete_status,"
				+ " DATE_FORMAT(deletedate,'%Y.%m.%d') as deletedate"
				+ " from member_tbl"
				+ " where admin_status = 'N'"
				+ " order by joindate desc"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				memberList = new ArrayList<>();
				
				do {
					
					memberList.add(new MemberBean(rs.getString("member_id"),
												  rs.getString("name"),
												  rs.getString("email"),
												  rs.getString("phone"),
												  rs.getInt("account"),
												  rs.getString("joindate_F"),
												  rs.getString("delete_status"),
												  rs.getString("deletedate")
												  )
								  );
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] orderNewMemberList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return memberList;
	}
	//5-2. 오래된 가입일순
	public ArrayList<MemberBean> orderOldMemberList(int page, int limit) {
		ArrayList<MemberBean> memberList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		//(탈퇴회원도 포함) (관리자 제외)
		String sql = "select member_id, name, email,"
				+ " phone, account, admin_status,"
				+ " DATE_FORMAT(joindate,'%Y.%m.%d') as joindate_F,"
				+ " delete_status,"
				+ " DATE_FORMAT(deletedate,'%Y.%m.%d') as deletedate"
				+ " from member_tbl"
				+ " where admin_status = 'N'"
				+ " order by joindate asc"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				memberList = new ArrayList<>();
				
				do {
					
					memberList.add(new MemberBean(rs.getString("member_id"),
							rs.getString("name"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getInt("account"),
							rs.getString("joindate_F"),
							rs.getString("delete_status"),
							rs.getString("deletedate")
							)
							);
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] orderOldMemberList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return memberList;
	}
	//5-3. 가나다순
	public ArrayList<MemberBean> orderAZMemberList(int page, int limit) {
		ArrayList<MemberBean> memberList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		//(탈퇴회원도 포함) (관리자 제외)
		String sql = "select member_id, name, email,"
				+ " phone, account, admin_status,"
				+ " DATE_FORMAT(joindate,'%Y.%m.%d') as joindate_F,"
				+ " delete_status,"
				+ " DATE_FORMAT(deletedate,'%Y.%m.%d') as deletedate"
				+ " from member_tbl"
				+ " where admin_status = 'N'"
				+ " order by member_id asc"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				memberList = new ArrayList<>();
				
				do {
					
					memberList.add(new MemberBean(rs.getString("member_id"),
							rs.getString("name"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getInt("account"),
							rs.getString("joindate_F"),
							rs.getString("delete_status"),
							rs.getString("deletedate")
							)
							);
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] orderAZMemberList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return memberList;
	}
	//5-4. 역가나다순
	public ArrayList<MemberBean> orderZAMemberList(int page, int limit) {
		ArrayList<MemberBean> memberList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		//(탈퇴회원도 포함) (관리자 제외)
		String sql = "select member_id, name, email,"
				+ " phone, account, admin_status,"
				+ " DATE_FORMAT(joindate,'%Y.%m.%d') as joindate_F,"
				+ " delete_status,"
				+ " DATE_FORMAT(deletedate,'%Y.%m.%d') as deletedate"
				+ " from member_tbl"
				+ " where admin_status = 'N'"
				+ " order by member_id desc"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				memberList = new ArrayList<>();
				
				do {
					
					memberList.add(new MemberBean(rs.getString("member_id"),
							rs.getString("name"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getInt("account"),
							rs.getString("joindate_F"),
							rs.getString("delete_status"),
							rs.getString("deletedate")
							)
							);
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] orderZAMemberList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return memberList;
	}
	
	
	//6. 선택한 기준에 따라 정렬된 탈퇴/미탈퇴 회원 목록 가져오기
	//6-1. 최근가입일순
	public ArrayList<MemberBean> orderNewDeleteUndeleteMemberList(String delete_status, int page, int limit) {
		ArrayList<MemberBean> memberList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		//(탈퇴회원도 포함) (관리자 제외)
		String sql = "select member_id, name, email,"
				+ " phone, account, admin_status,"
				+ " DATE_FORMAT(joindate,'%Y.%m.%d') as joindate_F,"
				+ " delete_status,"
				+ " DATE_FORMAT(deletedate,'%Y.%m.%d') as deletedate"
				+ " from member_tbl"
				+ " where admin_status = 'N' and delete_status = ?"
				+ " order by joindate desc"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, delete_status);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				memberList = new ArrayList<>();
				
				do {
					
					memberList.add(new MemberBean(rs.getString("member_id"),
							rs.getString("name"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getInt("account"),
							rs.getString("joindate_F"),
							rs.getString("delete_status"),
							rs.getString("deletedate")
							)
							);
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] orderNewDeleteUndeleteMemberList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return memberList;
	}
	//6-2. 오래된 가입일순
	public ArrayList<MemberBean> orderOldDeleteUndeleteMemberList(String delete_status, int page, int limit) {
		ArrayList<MemberBean> memberList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		//(탈퇴회원도 포함) (관리자 제외)
		String sql = "select member_id, name, email,"
				+ " phone, account, admin_status,"
				+ " DATE_FORMAT(joindate,'%Y.%m.%d') as joindate_F,"
				+ " delete_status,"
				+ " DATE_FORMAT(deletedate,'%Y.%m.%d') as deletedate"
				+ " from member_tbl"
				+ " where admin_status = 'N' and delete_status = ?"
				+ " order by joindate asc"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, delete_status);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				memberList = new ArrayList<>();
				
				do {
					
					memberList.add(new MemberBean(rs.getString("member_id"),
							rs.getString("name"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getInt("account"),
							rs.getString("joindate_F"),
							rs.getString("delete_status"),
							rs.getString("deletedate")
							)
							);
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] orderOldDeleteUndeleteMemberList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return memberList;
	}
	//6-3. 가나다순
	public ArrayList<MemberBean> orderAZDeleteUndeleteMemberList(String delete_status, int page, int limit) {
		ArrayList<MemberBean> memberList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		//(탈퇴회원도 포함) (관리자 제외)
		String sql = "select member_id, name, email,"
				+ " phone, account, admin_status,"
				+ " DATE_FORMAT(joindate,'%Y.%m.%d') as joindate_F,"
				+ " delete_status,"
				+ " DATE_FORMAT(deletedate,'%Y.%m.%d') as deletedate"
				+ " from member_tbl"
				+ " where admin_status = 'N' and delete_status = ?"
				+ " order by member_id asc"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, delete_status);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				memberList = new ArrayList<>();
				
				do {
					
					memberList.add(new MemberBean(rs.getString("member_id"),
							rs.getString("name"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getInt("account"),
							rs.getString("joindate_F"),
							rs.getString("delete_status"),
							rs.getString("deletedate")
							)
							);
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] orderAZDeleteUndeleteMemberList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return memberList;
	}
	//6-4. 역가나다순
	public ArrayList<MemberBean> orderZADeleteUndeleteMemberList(String delete_status, int page, int limit) {
		ArrayList<MemberBean> memberList = null;
		
		int startrow = (page-1)*20;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 40부터
		
		//(탈퇴회원도 포함) (관리자 제외)
		String sql = "select member_id, name, email,"
				+ " phone, account, admin_status,"
				+ " DATE_FORMAT(joindate,'%Y.%m.%d') as joindate_F,"
				+ " delete_status,"
				+ " DATE_FORMAT(deletedate,'%Y.%m.%d') as deletedate"
				+ " from member_tbl"
				+ " where admin_status = 'N' and delete_status = ?"
				+ " order by member_id desc"
				+ " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, delete_status);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				memberList = new ArrayList<>();
				
				do {
					
					memberList.add(new MemberBean(rs.getString("member_id"),
							rs.getString("name"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getInt("account"),
							rs.getString("joindate_F"),
							rs.getString("delete_status"),
							rs.getString("deletedate")
							)
							);
					
				}while(rs.next());
				
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] orderZADeleteUndeleteMemberList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return memberList;
	}

	//회원탈퇴 시 회원정보 삭제
	public int updateDeleteUser(String id) {
		int updateDeleteUserCount = 0;
		
		//비밀번호, 이름, 이메일, 전화번호의 개인정보 삭제(계좌잔액, 관리자여부 제외)
		//탈퇴여부 Y, 탈퇴일시 현재시간으로 업데이트
		String sql = "update member_tbl"
				 + " set password='delete', name='delete',"
				 + " email='delete', phone='delete',"
				 + " delete_status='Y',"
				 + " deletedate=current_timestamp"
				 + " where member_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
						
			updateDeleteUserCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] updateDeleteUser() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return updateDeleteUserCount;
	}

	//회원 상세보기 - 회원정보를 얻어옴
	public MemberBean selectMember(String member_id) {
		MemberBean memberInfo = null;
		
		String sql = "select member_id, name, email,"
				+ " phone, account, admin_status,"
				+ " DATE_FORMAT(joindate,'%Y.%m.%d') as joindate,"
				+ " delete_status,"
				+ " DATE_FORMAT(deletedate,'%Y.%m.%d') as deletedate"
				+ " from member_tbl"
				+ " where member_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				memberInfo = new MemberBean();
				memberInfo.setMember_id(member_id);
				memberInfo.setName(rs.getString("name"));
				memberInfo.setEmail(rs.getString("email"));
				memberInfo.setPhone(rs.getString("phone"));
				memberInfo.setJoindate(rs.getString("joindate"));
				memberInfo.setDelete_status("delete_status");
				memberInfo.setDelete_status(rs.getString("deletedate"));
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] getMemberInfo() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return memberInfo;
	}

	//회원 상세보기 - 회원 주소정보를 얻어옴
	public ArrayList<AddressBean> selectMemberAddressList(String member_id) {
		ArrayList<AddressBean> addressList = null;
		
		String sql = "select address_id, member_id,"
				  + " receiver_name, receiver_phone,"
				  + " postcode, address1, address2,"
				  + " basic_status"
				  + " from address_tbl"
				  + " where member_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				addressList = new ArrayList<>();
				
				do{
					addressList.add(new AddressBean(rs.getString("address_id"),
													rs.getString("member_id"),
													rs.getString("receiver_name"),
													rs.getString("receiver_phone"),
													rs.getInt("postcode"),
													rs.getString("address1"),
													rs.getString("address2"),
													rs.getString("basic_status")
													)
									);
					
				}while(rs.next());
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] selectMemberAddressList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return addressList;
	}

	//회원 상세보기 - 회원 후원기록를 얻어옴
	public ArrayList<DonationBean> selectMemberDonationList(String member_id) {
		
		ArrayList<DonationBean> donationList = null;
		
		String sql = "select donation_id, project_id, member_id, reward_id, address_id"
				  + " r_price, nvl(add_donation,0) as add_donation,"
				  + " DATE_FORMAT(donatedate,'%Y.%m.%d') as donatedate"
				  + " from donation_tbl"
				  + " where member_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				donationList = new ArrayList<>();
				
				do {
					DonationBean donation = new DonationBean();
					donation.setDonation_id(rs.getInt("donation_id"));
					donation.setProject_id(rs.getInt("project_id"));
					donation.setMember_id(rs.getString("member_id"));
					donation.setAddress_id(rs.getString("address_id"));
					donation.setReward_id(rs.getString("reward_id"));
					donation.setR_price(rs.getInt("r_price"));
					donation.setAdd_donation(rs.getInt("add_donation"));
					donation.setTotalDonation(rs.getInt("r_price") + rs.getInt("add_donation"));
					donation.setDonatedate(rs.getString("donatedate"));
					
				}while(rs.next());
			}
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] selectMemberDonationList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return donationList;
	}

	//회원 상세보기 - 회원 문의글 리스트를 얻어옴
	public ArrayList<QnaBean> selectMemberQnaList(String member_id) {
		ArrayList<QnaBean> qnaList = null;
		
		String sql = "select qna_id, q_writer, q_title, q_content,"
				  + " DATE_FORMAT(q_time,'%Y.%m.%d') as q_time,"
				  + " a_writer"
				  + " from qna_tbl"
				  + " where q_writer=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				qnaList = new ArrayList<>();
				
				do {
					
					qnaList.add(new QnaBean(rs.getInt("qna_id"),
											rs.getString("q_writer"),
											rs.getString("q_title"),
											rs.getString("q_time"),
											rs.getString("a_writer")
											)
							   );
					
				}while(rs.next());
				
			}
			
			
		} catch(Exception e) {
			System.out.println("[ManageMemberDAO] selectMemberQnaList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return qnaList;
	}

	
}
