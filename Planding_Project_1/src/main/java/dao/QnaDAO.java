package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


import vo.QnaBean;

public class QnaDAO {
	
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
	private QnaDAO() {}
	
	
	
	private static QnaDAO qnaDAO; //static메서드인 getInstance에서 쓸 수 있게 static (단 외부에서 직접 접근 불가능하도록 private)
	
	public static QnaDAO getInstance() {
		if(qnaDAO == null) {//DogDAO객체가 없으면
			qnaDAO = new QnaDAO();//객체 생성
		}
		
		return qnaDAO;//기존 DogDAO객체의 주소 리턴
	}
	
	/******************************************************************/
	
	/**
	 * 1. "DB연결(Service)" -> 2. SQL 실행 -> 3. 결과처리 -> 4. DB연결해제(Service)
	 */
	//DB연결 : 매개값으로 받은 Connection객체의 주소값을 필드con에 셋팅
	public void setConnection(Connection con){
		this.con = con;
	}

	
	//1. 전체 문의글 개수를 가져옴
	public int selectQnaCount() {
		int qnaCount = 0;
		
		String sql = "select count(*) from qna_tbl";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				qnaCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[QnaDAO] selectQnaCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return qnaCount;
	}
	
	//2. 원하는 페이지의 원하는 개수만큼 글 불러오기
	public ArrayList<QnaBean> selectQnaList(int page, int limit){
		ArrayList<QnaBean> qnaList = null;
			
		int startrow = (page-1)*10;
		//1페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 0부터
		//2페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 10부터
		//3페이지 조회 -> 글목록의 제일 윗 글은 sql에서 row index 20부터
		
		String sql = "select qna_id, q_writer,"
				  + " q_title, q_content, q_image, q_private,"
				  + " DATE_FORMAT(q_time,'%Y.%m.%d') as q_time,"
				  + " a_writer, a_content, DATE_FORMAT(a_time,'%Y.%m.%d') as a_time"
				  + " from qna_tbl"
				  + " order by qna_id desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);//startrow번째행부터 limit개만 가져옴
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				qnaList = new ArrayList<>();
				
				do {
					QnaBean qna = new QnaBean();
					
					qna.setQna_id(rs.getInt("qna_id"));
					qna.setQ_writer(rs.getString("q_writer"));
					
					qna.setQ_title(rs.getString("q_title"));
					qna.setQ_content(rs.getString("q_content"));
					qna.setQ_image(rs.getString("q_image"));
					qna.setQ_private(rs.getString("q_private"));
					qna.setQ_time(rs.getString("q_time"));
					
					qna.setA_writer(rs.getString("a_writer"));
					qna.setA_content(rs.getString("a_content"));
					qna.setA_time(rs.getString("a_time"));
					
					System.out.println("[QnaDAO] add한 qna객체 : "+qna);
					
					qnaList.add(qna);
				
				}while(rs.next());
			}
			
		} catch(Exception e) {
			System.out.println("[QnaDAO] selectQnaList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return qnaList;
	}
	
	//3. 특정 문의 글 조회
	public QnaBean selectQna(int qna_id) {
		QnaBean qna = null;
		
		String sql = "select qna_id, q_writer,"
				 + " q_title, q_content, q_image, q_private,"
				 + " DATE_FORMAT(q_time,'%Y.%m.%d %H:%i') as q_time,"
				 + " a_writer, a_content,"
				 + " DATE_FORMAT(a_time,'%Y.%m.%d %H:%i') as a_time"
				 + " from qna_tbl"
				 + " where qna_id=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qna_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				qna = new QnaBean();
				
				qna.setQna_id(rs.getInt("qna_id"));
				qna.setQ_writer(rs.getString("q_writer"));
				
				qna.setQ_title(rs.getString("q_title"));
				qna.setQ_content(rs.getString("q_content"));
				qna.setQ_image(rs.getString("q_image"));
				qna.setQ_private(rs.getString("q_private"));
				qna.setQ_time(rs.getString("q_time"));
				
				qna.setA_writer(rs.getString("a_writer"));
				qna.setA_content(rs.getString("a_content"));
				qna.setA_time(rs.getString("a_time"));
				
				System.out.println("[QnaDAO] selectQna 정상조회?" + qna);
			}
						
		} catch(Exception e) {
			System.out.println("[QnaDAO] selectQna() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return qna;
	}

	
	//4. 문의글 삭제
	public int deleteQna(int qna_id) {
		
		int deleteQnaCount = 0;
		
		String sql = "delete from qna_tbl where qna_id=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qna_id);
			
			deleteQnaCount = pstmt.executeUpdate();			
						
		} catch(Exception e) {
			System.out.println("[QnaDAO] deleteQna() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return deleteQnaCount;
	}
	
	
	/* 사용자 모드 ---------------------------------------------------------------------------*/
	
	//1. 회원이 문의 글 등록
	public int insertNewQuestion(QnaBean qna) {
		
		int insertNewQuestionCount = 0;
		
		String sql = "insert into qna_tbl(q_writer, q_title, q_content, q_image, q_private) values(?,?,?,?,?)";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, qna.getQ_writer());
			pstmt.setString(2, qna.getQ_title());
			pstmt.setString(3, qna.getQ_content());
			pstmt.setString(4, qna.getQ_image());
			pstmt.setString(5, qna.getQ_private());
			
			insertNewQuestionCount = pstmt.executeUpdate();
						
		} catch(Exception e) {
			System.out.println("[QnaDAO] insertNewQuestion() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return insertNewQuestionCount;
		
	}

	//2. 회원이 문의글 수정 (이미지 수정X)
	public int updateQuestion(QnaBean qna) {
		int updateQuestionCount = 0;
		
		String sql = "update qna_tbl"
				  + " set q_title=?, q_content=?, q_private=?"
				  + " where qna_id=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, qna.getQ_title());
			pstmt.setString(2, qna.getQ_content());
			pstmt.setString(3, qna.getQ_private());
			pstmt.setInt(4, qna.getQna_id());
			
			updateQuestionCount = pstmt.executeUpdate();
						
		} catch(Exception e) {
			System.out.println("[QnaDAO] updateQuestion() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return updateQuestionCount;
	}

	//3. 회원이 문의글 수정 (이미지 수정)
	public int updateQuestionImg(QnaBean qna) {
		
		int updateQuestionImgCount = 0;
		
		String sql = "update qna_tbl"
				  + " set q_title=?, q_content=?, q_image=?, q_private=?"
				  + " where qna_id=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, qna.getQ_title());
			pstmt.setString(2, qna.getQ_content());
			pstmt.setString(3, qna.getQ_image());
			pstmt.setString(4, qna.getQ_private());
			pstmt.setInt(5, qna.getQna_id());
			
			updateQuestionImgCount = pstmt.executeUpdate();
			
						
		} catch(Exception e) {
			System.out.println("[QnaDAO] updateQuestionImg() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return updateQuestionImgCount;
	}

	

		
	/* 관리자 모드 ---------------------------------------------------------------------------*/
	
	//1. 관리자가 문의 글 답변 등록
	public int updateNewAnswer(QnaBean qna) {
		int udpateNewAnswerCount = 0;
		
		String sql = "update qna_tbl"
				  + " set a_writer=?, a_content=?, a_time=current_timestamp"
				  + " where qna_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, qna.getA_writer());
			pstmt.setString(2, qna.getA_content());
			pstmt.setInt(3, qna.getQna_id());			
			
			udpateNewAnswerCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[QnaDAO] updateNewAnswer() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return udpateNewAnswerCount;
	}
	
	//2. 관리자가 답변 수정
	public int updateAnswer(QnaBean qna) {
		int udpateAnswerCount = 0;
		
		String sql = "update qna_tbl"
				  + " set a_content=?"
				  + " where qna_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, qna.getA_content());
			pstmt.setInt(2, qna.getQna_id());			
			
			udpateAnswerCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[QnaDAO] updateAnswer() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return udpateAnswerCount;
	}

	
	//검색조건에 맞는 문의글 개수를 얻어옴
	public int searchQnaCount(String search_title) {
		int searchQnaCount = 0;
		
		String sql = "select count(*)"
				  + " from qna_tbl"
				  + " where q_title regexp ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, search_title);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				searchQnaCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[QnaDAO] searchQnaCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return searchQnaCount;
	}
	
	//검색조건에 맞는 문의글 목록을 얻어옴
	public ArrayList<QnaBean> searchQnaList(String search_title, int page, int limit) {
		ArrayList<QnaBean> qnaList = null;
		
		int startrow = (page-1)*10;
		
		String sql = "select qna_id, q_writer,"
				  + " q_title, q_content, q_image, q_private,"
				  + " DATE_FORMAT(q_time,'%Y.%m.%d') as q_time,"
				  + " a_writer, a_content, DATE_FORMAT(a_time,'%Y.%m.%d') as a_time"
				  + " from qna_tbl"
				  + " where q_title regexp ?"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, search_title);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				qnaList = new ArrayList<>();
				
				do {
					qnaList.add(new QnaBean(rs.getInt("qna_id"),
											rs.getString("q_writer"),
											rs.getString("q_title"),
											rs.getString("q_content"),
											rs.getString("q_image"),
											rs.getString("q_private"),
											rs.getString("q_time"),
											rs.getString("a_writer"),
											rs.getString("a_content"),
											rs.getString("a_time")
											)
								);
				}while(rs.next());
				
			}
			
			
		} catch(Exception e) {
			System.out.println("[QnaDAO] searchQnaList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return qnaList;
	}

	/** 특정 회원의 문의글 개수를 알아냄 */
	public int selectUserQnaCount(String member_id) {
		int userQnaCount = 0;
		
		String sql = "select count(*) from qna_tbl where q_writer = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userQnaCount = rs.getInt(1);				
			}
			
		} catch(Exception e) {
			System.out.println("[QnaDAO] selectUserQnaCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return userQnaCount;
	}
	
	/** 특정 회원의 문의글 리스트를 얻어옴 */
	public ArrayList<QnaBean> selectUserQnaList(String member_id, int page, int limit) {
		ArrayList<QnaBean> qnaList = null;
		
		int startrow = (page-1)*10;
		
		String sql = "select qna_id, q_writer, q_title, q_content,"
				  + " DATE_FORMAT(q_time,'%Y.%m.%d') as q_time_F,"
				  + " a_writer"
				  + " from qna_tbl"
				  + " where q_writer=?"
				  + " order by q_time desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				qnaList = new ArrayList<>();
				
				do {
					
					qnaList.add(new QnaBean(rs.getInt("qna_id"),
											rs.getString("q_writer"),
											rs.getString("q_title"),
											rs.getString("q_time_F"),
											rs.getString("a_writer")
											)
								);
					
				}while(rs.next());
				
			}
			
			
		} catch(Exception e) {
			System.out.println("[QnaDAO] selectUserQnaList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return qnaList;
	}

	
	
}
