package svc.qna;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import dao.ManageProjectDAO;
import dao.QnaDAO;
import vo.QnaBean;

public class SearchQnaListService {

	public int getSearchListCount(String search_title) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		QnaDAO qnaDAO = QnaDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		qnaDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int searchQnaCount = qnaDAO.searchQnaCount(search_title);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return searchQnaCount;
	}

	public ArrayList<QnaBean> getSearchQnaList(String search_title, int page, int limit) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : ManageMemberDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		QnaDAO qnaDAO = QnaDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		qnaDAO.setConnection(con);
		
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		ArrayList<QnaBean> searchQnaList = qnaDAO.searchQnaList(search_title, page, limit);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
				
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return searchQnaList;
	}

}
