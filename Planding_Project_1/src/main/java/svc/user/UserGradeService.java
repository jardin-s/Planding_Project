package svc.user;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.UserDAO;
import vo.MemberBean;

public class UserGradeService {
	
	//필드 없음
	
		//기본생성자
		
		//메서드
		//1. 지난달 구매금액에 따라 등급을 변경
		public static MemberBean updateGrade(MemberBean user) {
			//1. 커넥션 풀에서 Connection객체를 얻어와
			Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
			
			//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
			UserDAO userDAO = UserDAO.getInstance();
			
			//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
			userDAO.setConnection(con);
			
			
			
			/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
			
			//ID로 지난달 구매금액을 조회
			int lastMonthMoney = userDAO.getLastMonthMoney(user.getId());
			
			//현재 등급 얻어옴 (이유? 현재 등급을 알아야 다음 등급으로 업그레이드) -> 필요없음. 금액만 알면 됨.
			//String grade = user.getGrade();
			
			/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
			 * 		 단, select는 이런 작업을 제외 ------------------*/
			
			//1달 10만원 이상 -> GOLD -> 3달 간 10만원 이상 => VIP (단계적)
			int gradeCount = 0;
			String now_grade = user.getGrade();
			
			//현재 등급이 NORMAL이고
			if(now_grade.equalsIgnoreCase("NORMAL")) {
				//지난달 구매금액이
				if(lastMonthMoney < 100000) {//10만원보다 작으면 NORMAL
					gradeCount = userDAO.gradeNORMAL(user);
				}else {//10만원 이상이면 GOLD
					gradeCount = userDAO.gradeGOLD(user);
				}
			//현재 등급이 GOLD이고	
			}else if(now_grade.equalsIgnoreCase("GOLD")) {
				//지난달 구매금액이
				if(lastMonthMoney < 50000) {//5만원보다 작으면 다시 NORMAL로
					gradeCount = userDAO.gradeNORMAL(user);
				}else if(50000 <= lastMonthMoney && lastMonthMoney < 100000){//5만원 이상~10만원 미만이면 GOLD 유지
					gradeCount = userDAO.gradeGOLD(user);
				}else {//10만원 이상이면 VIP로
					gradeCount = userDAO.gradeVIP(user);
				}
			//현재 등급이 VIP이고
			}else if(now_grade.equalsIgnoreCase("VIP")) {
				//지난달 구매금액이
				if(lastMonthMoney < 100000) {//10만원보다 작으면 다시 GOLD로
					gradeCount = userDAO.gradeGOLD(user);
				}else {//10만원 이상이면 VIP로
					gradeCount = userDAO.gradeVIP(user);
				}
			}
			
			MemberBean userInfo = userDAO.selectUserInfo(user.getId());
			
			
//			int gradeCount = 0;
//			if(lastMonthMoney < 50000) {//지난달구매금액이 5만원 미만이면
//				//등급 "NORMAL"로 수정
//				gradeCount = userDAO.gradeNORMAL(user);
//			}else if(50000 <= lastMonthMoney && lastMonthMoney < 100000) {//지난달구매금액이 5만원 이상 ~ 10만원 미만이면
//				//등급 "GOLD"로 수정
//				gradeCount = userDAO.gradeGOLD(user);
//			}else if(lastMonthMoney >= 100000) {//지난달구매금액이 10만원 이상이면
//				//등급 "VIP"로 수정
//				gradeCount = userDAO.gradeVIP(user);
//			}
//			
//			if(gradeCount > 0) { //성공시 commit, 실패시 rollback
//				commit(con);
//			}else {
//				rollback(con);
//			}
//			
//			//수정된 사용자 정보를 id로 다시 조회하여 리턴
//			MemberBean userInfo = userDAO.selectUserInfo(user.getId());
			
//			
//			//현재 등급이 NORMAL이면서
//			if(grade.trim().equalsIgnoreCase("NORMAL")) {
//								
//				if(50000 <= lastMonthMoney && lastMonthMoney < 100000) {//지난달구매금액이 5만원 이상 ~ 10만원 미만이면
//					//등급 "GOLD"로 업그레이드
//					gradeCount = userDAO.upGradeGOLD(user);
//				}else if(lastMonthMoney >= 100000) {//지난달구매금액이 10만원 이상이면
//					//등급 "VIP"로 업그레이드
//					gradeCount = userDAO.upGradeVIP(user);
//				}
//				
//				if(gradeCount > 0) { //성공시 commit, 실패시 rollback
//					commit(con);
//				}else {
//					rollback(con);
//				}
//			}
//			//현재 등급이 GOLD이면서
//			else if(grade.trim().equalsIgnoreCase("GOLD")){
//				
//				if(lastMonthMoney < 50000) {//지난달구매금액이 5만원 미만이면
//					gradeCount = userDAO.downGradeNORMAL(user);//등급 "NORMAL"로 다운그레이드
//				}else if(50000 <= lastMonthMoney && lastMonthMoney < 100000) {//지난달구매금액이 5만원 이상 ~ 10만원 미만이면
//					gradeCount = userDAO.upGradeGOLD(user);//등급 "GOLD"로 유지
//				}else if(lastMonthMoney >= 100000) {//지난달구매금액이 10만원 이상이면
//					gradeCount = userDAO.upGradeVIP(user);//등급 "VIP"로 업그레이드
//				}
//				
//				if(gradeCount > 0) { //성공시 commit, 실패시 rollback
//					commit(con);
//				}else {
//					rollback(con);
//				}
//			
//			//현재 등급이 VIP이면서
//			}else if(grade.trim().equalsIgnoreCase("VIP")) {
//				if(lastMonthMoney < 50000) {//지난달구매금액이 5만원 미만이면
//					gradeCount = userDAO.downGradeNORMAL(user);//등급 "NORMAL"로 다운그레이드
//				}else if(50000 <= lastMonthMoney && lastMonthMoney < 100000) {//지난달구매금액이 5만원 이상 ~ 10만원 미만이면
//					gradeCount = userDAO.downGradeGOLD(user);//등급 "GOLD"로 다운그레이드
//				}else if(lastMonthMoney >= 100000) {//지난달구매금액이 10만원 이상이면
//					gradeCount = userDAO.upGradeVIP(user);//등급 "VIP"로 유지
//				}
//				
//				if(gradeCount > 0) { //성공시 commit, 실패시 rollback
//					commit(con);
//				}else {
//					rollback(con);
//				}
//			}
			
			//4. 해제
			close(con); //JdbcUtil. 생략(이유?import static 하여)
			
			return userInfo;
		}
		
		//구매 시 사용
		public double getSaleRate(String u_grade) {
			
			//1. 커넥션 풀에서 Connection객체를 얻어와
			Connection con = getConnection(); //JdbcUtil. 생략(이유?import static 하여)
			
			//2. 싱글톤 패턴 : UserDAO 객체 생성 (DogDAO 객체를 하나만 만들어서 계속 사용)
			UserDAO userDAO = UserDAO.getInstance();
			
			//3. DB작업에 사용될 Connection객체를 DogDAO에 전달하여 DB연결하여 DAO에서 작업하도록 "서비스"해줌
			userDAO.setConnection(con);
			
			
			/*-------DAO의 해당 메서드를 호출하여 처리----------------------------------------------------*/
			
			//ID로 지난달 구매금액을 조회
			double saleRate = 1.0;
			saleRate = userDAO.getSaleRate(u_grade);
						
			/*-------(insert, update, delete) 성공하면 commit(), 실패하면 rollback() 호출
			 * 		 단, select는 이런 작업을 제외 ------------------*/
			
			//4. 해제
			close(con); //JdbcUtil. 생략(이유?import static 하여)
			
			return saleRate;
		}
	
}
