package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.NoticeBean;
import vo.QnaBean;

public class NoticeDAO {
	
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
	private NoticeDAO() {}
	
	
	
	private static NoticeDAO noticeDAO; //static메서드인 getInstance에서 쓸 수 있게 static (단 외부에서 직접 접근 불가능하도록 private)
	
	public static NoticeDAO getInstance() {
		if(noticeDAO == null) {//DogDAO객체가 없으면
			noticeDAO = new NoticeDAO();//객체 생성
		}
		
		return noticeDAO;//기존 DogDAO객체의 주소 리턴
	}
	
	/******************************************************************/
	
	/**
	 * 1. "DB연결(Service)" -> 2. SQL 실행 -> 3. 결과처리 -> 4. DB연결해제(Service)
	 */
	//DB연결 : 매개값으로 받은 Connection객체의 주소값을 필드con에 셋팅
	public void setConnection(Connection con){
		this.con = con;
	}

	
	//1. 전체 공지글 개수를 가져옴
	public int selectNoticeCount() {
		int qnaCount = 0;
		
		String sql = "select count(*) from notice_tbl";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				qnaCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[NoticeDAO] selectNoticeCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return qnaCount;
	}
	
	
	//2. 중요공지글의 개수를 알아냄
	public int getImportantCount() {
		
		int importantCount = 0;
		
		String sql = "select count(*) from notice_tbl where importance='Y'";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				importantCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[NoticeDAO] getImportantCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return importantCount;
		
	}
	
	//3. 중요공지글 목록을 얻어옴
	public ArrayList<NoticeBean> selectImportantNoticeList(){
		ArrayList<NoticeBean> importantList = null;
		
		String sql = "select notice_id, member_id,"
				  + " n_title, n_content, n_image, importance,"
				  + " viewcount,"
				  + " DATE_FORMAT(writetime,'%Y.%m.%d') as writetime"
				  + " from notice_tbl"
				  + " where importance = 'Y'"
				  + " order by writetime desc";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				importantList = new ArrayList<>();
				
				do {
					importantList.add(new NoticeBean(rs.getInt("notice_id"),
													 rs.getString("member_id"),
													 rs.getString("n_title"),
													 rs.getString("n_content"),
													 rs.getString("importance"),
													 rs.getString("n_image"),
													 rs.getInt("viewcount"),
													 rs.getString("writetime")
													 )
									 );
					
				}while(rs.next());
				
			}
			
			
		} catch(Exception e) {
			System.out.println("[NoticeDAO] getImportantCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return importantList;
	}
	
	
	//4. 원하는 페이지의 원하는 개수만큼 글 불러오기
	public ArrayList<NoticeBean> selectNoticeList(int page, int limit){
		ArrayList<NoticeBean> noticeList = null;
			
		int startrow = (page-1)*10;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 10부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		
		String sql = "select notice_id, member_id,"
				  + " n_title, n_content, n_image, importance,"
				  + " viewcount,"
				  + " DATE_FORMAT(writetime,'%Y.%m.%d') as writetime"
				  + " from notice_tbl"
				  + " order by writetime desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);//startrow번째행부터 limit개만 가져옴
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				noticeList = new ArrayList<>();
				
				do {
					NoticeBean noticeInfo = new NoticeBean();
					
					noticeInfo.setNotice_id(rs.getInt("notice_id"));
					noticeInfo.setMember_id(rs.getString("member_id"));
					noticeInfo.setN_title(rs.getString("n_title"));
					noticeInfo.setN_content(rs.getString("n_content"));
					noticeInfo.setN_image(rs.getString("n_image"));
					noticeInfo.setImportance(rs.getString("importance"));
					noticeInfo.setViewcount(rs.getInt("viewcount"));
					noticeInfo.setWritetime(rs.getString("writetime"));
				
					noticeList.add(noticeInfo);
					
				}while(rs.next());
			}
			
		} catch(Exception e) {
			System.out.println("[NoticeDAO] selectNoticeList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return noticeList;
	}

	
	//5. 공지사항 글 작성
	public int insertNotice(NoticeBean notice) {
		int insertNoticeCount = 0;
		
		String sql = "insert into notice_tbl(member_id, n_title, n_content, n_image, importance, viewcount) values(?,?,?,?,?,0)";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, notice.getMember_id());
			pstmt.setString(2, notice.getN_title());
			pstmt.setString(3, notice.getN_content());
			pstmt.setString(4, notice.getN_image());
			pstmt.setString(5, notice.getImportance());
			
			insertNoticeCount = pstmt.executeUpdate();
						
		} catch(Exception e) {
			System.out.println("[NoticeDAO] insertNotice() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return insertNoticeCount;
	}

	//6. 특정 공지글 상세보기
	public NoticeBean selectNotice(int notice_id) {
		NoticeBean noticeInfo = null;
		
		String sql = "select notice_id, member_id,"
				  + " n_title, n_content, n_image,"
				  + " importance, viewcount,"
				  + " DATE_FORMAT(writetime,'%Y.%m.%d %H:%i') as writetime"
				  + " from notice_tbl where notice_id=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, notice_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				noticeInfo = new NoticeBean();
				
				noticeInfo.setNotice_id(rs.getInt("notice_id"));
				noticeInfo.setMember_id(rs.getString("member_id"));
				
				noticeInfo.setN_title(rs.getString("n_title"));
				noticeInfo.setN_content(rs.getString("n_content"));
				noticeInfo.setN_image(rs.getString("n_image"));
				
				noticeInfo.setImportance(rs.getString("importance"));
				noticeInfo.setViewcount(rs.getInt("viewcount"));
				noticeInfo.setWritetime(rs.getString("writetime"));
								
				System.out.println("[NoticeDAO] selectNotice 정상조회?" + noticeInfo);
			}
						
		} catch(Exception e) {
			System.out.println("[NoticeDAO] selectNotice() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return noticeInfo;
	}
	
	
	//공지사항 조회 시, 조회수 1 증가
	public int updateNoticeViewcount(int notice_id) {
		
		int updateNoticeViewCount = 0;
		
		String sql = "update notice_tbl"
				  + " set viewcount = viewcount+1"
				  + " where notice_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, notice_id);
			
			updateNoticeViewCount = pstmt.executeUpdate();
						
		} catch(Exception e) {
			System.out.println("[NoticeDAO] updateNoticeViewcount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return updateNoticeViewCount;
		
	}

	//7. 관리자모드 - 공지사항 글 삭제하기
	public int deleteNotice(int notice_id) {
		int deleteNoticeCount = 0;
		
		String sql = "delete from notice_tbl where notice_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, notice_id);
			
			deleteNoticeCount = pstmt.executeUpdate();
						
		} catch(Exception e) {
			System.out.println("[NoticeDAO] deleteNotice() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return deleteNoticeCount;
	}

	

	
	
}
