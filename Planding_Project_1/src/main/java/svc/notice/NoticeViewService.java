package svc.notice;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.NoticeDAO;
import vo.NoticeBean;

public class NoticeViewService {

	public NoticeBean getNoticeInfo(int notice_id) {
		
		//1. 커넥션 풀에서 Connection객체를 얻어와
		Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
		
		//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		
		//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
		noticeDAO.setConnection(con);
		
		/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
		
		//공지글 조회 시 조회수 1 증가
		int isUpdateViewcountCount = noticeDAO.updateNoticeViewcount(notice_id);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		if(isUpdateViewcountCount > 0) {
			commit(con);
		}else {
			rollback(con);
		}
		
		//id로 공지글 정보 가져오기
		NoticeBean noticeInfo = noticeDAO.selectNotice(notice_id);
		
		/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
		 * 		 단, select는 이런 작업을 제외 ------------------*/
		
		//4. 해제
		close(con); //JdbcUtil. 생략(이유?import static 하여)
		
		return noticeInfo;
		
	}

}
