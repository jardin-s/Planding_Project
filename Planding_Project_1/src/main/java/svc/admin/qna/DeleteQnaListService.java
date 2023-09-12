package svc.admin.qna;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.QnaDAO;

public class DeleteQnaListService {
	
	public boolean deleteQnaList(ArrayList<Integer> qnaIdList) {
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		QnaDAO qnaDAO = QnaDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		qnaDAO.setConnection(con);
				
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		int deleteQnaCount = 0;
		
		for(Integer qna_id : qnaIdList) {
			deleteQnaCount += qnaDAO.deleteQna(qna_id);//성공 1, 실패 0
		}
		
		boolean isDeleteQnaListResult = false;
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(deleteQnaCount == qnaIdList.size()) {//성공한 횟수 == qna_id 개수여야 성공
			isDeleteQnaListResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return isDeleteQnaListResult;
	}
}
