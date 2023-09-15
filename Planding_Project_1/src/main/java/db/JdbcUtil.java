//DB연결하여 Connection객체를 생성하고 해제(close)하는 모든 내용이 담긴 클래스

/**
 * DB 작업 시 공통적으로 사용하는 메서드를 정리 (모든 메서드가 public static메서드)
 */

package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JdbcUtil {
	//Connection객체를 싱글톤 패턴을 하지 않는 이유?
	//어차피 Connection객체 생성 후 사용한 다음 close()해야 함(자원해제 안 하면 서버에 문제가 생김)
	//close()하면 connection객체를 다시 커넥션 풀로 돌려줌 -> 따라서 싱글톤 패턴을 할 필요가 없음 (어차피 다시 반납)
	
	
	//'커넥션 풀'에서 Connection 객체를 얻어와 반환 (DataSource를 커넥션 풀이라고 생각하면 됨)
	public static Connection getConnection(){
		Connection con = null; //1.리턴할 타입의 변수 = null;로 먼저 설정
		
		try {
			Context initCtx = new InitialContext();
			Context envCtx= (Context) initCtx.lookup("java:comp/env"); //앞에 java:comp/env를 적음 (항상 고정) -> 돌려주는 타입이 Object이므로 다운캐스팅
			DataSource ds = (DataSource) envCtx.lookup("jdbc/PlandingDB"); //"context.xml의 name"으로 DataSource객체를 가져옴 -> Object로 돌려주므로 다운캐스팅
			con = ds.getConnection();//커넥션 풀에서 커넥션 객체를 가져옴
			
			con.setAutoCommit(false); //★★Connection객체에 트랜잭션(insert,update,delete)을 자동으로 완료하지 못하도록 false -> 실패 시 rollback 할 수 있도록
						
		}catch(Exception e) {
			//e.printStackTrace(); //정확하게 어디서 오류가 났는지 집어줌
			System.out.println("JdbcUtil 클래스 : getConnection() 에러 "+ e);
		}
		
		return con; //2.return 리턴할 변수; 미리 적어둠
	}
	
	
	/**
	 * Connection 객체를 닫아주는 메서드
	 */
	public static void close(Connection con){
		try{
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Statement 객체를 닫아주는 메서드
	 */
	public static void close(Statement stmt){
		try{
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * PreparedStatement 객체를 닫아주는 메서드
	 */
	public static void close(PreparedStatement pstmt){
		try{
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ResultSet 객체를 닫아주는 메서드
	 */
	public static void close(ResultSet rs){
		try{
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/*---------------------------------------------------*/
	
	/*
	 * 트랜잭션 중에 실행된 작업들을 '완료'시키는 메서드
	 * insert, delete, update '성공'한 후 commit함
	 */
	public static void commit(Connection con) {
		try{
			con.commit();//DB영구저장 (성공했을 때 commit)
			System.out.println("commit success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 트랜잭션 중에 실행된 작업들을 '취소'시키는 메서드
	 * insert, delete, update '실패'하면 rollback함
	 */
	public static void rollback(Connection con) {
		try{
			con.rollback();//실패 시 rollback
			System.out.println("rollback success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
