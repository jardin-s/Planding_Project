package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


import util.SHA256;
import vo.AddressBean;
import vo.MemberBean;
import vo.MemberPwChangeBean;

public class AdminDAO {
	
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
	private AdminDAO() {}
	
	
	
	private static AdminDAO adminDAO; //static메서드인 getInstance에서 쓸 수 있게 static (단 외부에서 직접 접근 불가능하도록 private)
	
	public static AdminDAO getInstance() {
		if(adminDAO == null) {//DogDAO객체가 없으면
			adminDAO = new AdminDAO();//객체 생성
		}
		
		return adminDAO;//기존 DogDAO객체의 주소 리턴
	}
	
	/******************************************************************/
	
	/**
	 * 1. "DB연결(Service)" -> 2. SQL 실행 -> 3. 결과처리 -> 4. DB연결해제(Service)
	 */
	//DB연결 : 매개값으로 받은 Connection객체의 주소값을 필드con에 셋팅
	public void setConnection(Connection con){
		this.con = con;
	}

	//1. 모든 개 상품정보를 조회하여 ArrayList<Dog> 객체 반환
//	public ArrayList<Dog> selectDogList(String id) {
//		
//		ArrayList<Dog> dogList = null;
//		//ArrayList<Dog> dogList = new ArrayList<>();//기본값(null)으로 채워짐
//		
//		//sql
//		String sql = "select * from dog";
//		
//		try {
//			
//			pstmt = con.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			
//			//[방법-1] 위에서 dogList 객체 생성 후
//			/*
//			while(rs.next()) {
//				Dog dog = new Dog();//기본값으로 채워진 Dog객체를
//				
//				//조회된 dog의 정보로 채움
//				dog.setId(rs.getInt("id"));
//				dog.setKind(rs.getString("kind"));
//				dog.setCountry(rs.getString("country"));
//				dog.setPrice(rs.getInt("price"));
//				dog.setHeight(rs.getInt("height"));
//				dog.setWeight(rs.getInt("weight"));
//				dog.setContent(rs.getString("content"));
//				dog.setImage(rs.getString("image"));
//				dog.setReadcount(rs.getInt("readcount"));
//				
//				dogList.add(dog);
//			}
//			*/
//			
//			//[방법-2] 위에서 dogList 선언만 함
//			if(rs.next()) {
//				
//				dogList = new ArrayList<>();//기본값으로 채워짐
//				
//				do {
//					/*
//					Dog dog = new Dog();
//					
//					dog.setId(rs.getInt("id"));
//					dog.setKind(rs.getString("kind"));
//					dog.setCountry(rs.getString("country"));
//					dog.setPrice(rs.getInt("price"));
//					dog.setHeight(rs.getInt("height"));
//					dog.setWeight(rs.getInt("weight"));
//					dog.setContent(rs.getString("content"));
//					dog.setImage(rs.getString("image"));
//					dog.setReadcount(rs.getInt("readcount"));
//					
//					dogList.add(dog);				
//					*/
//					
//					dogList.add(new Dog(rs.getInt("id"),
//										rs.getString("kind"),
//										rs.getString("country"),
//										rs.getInt("price"),
//										rs.getInt("height"),
//										rs.getInt("weight"),
//										rs.getString("content"),
//										rs.getString("image"),
//										rs.getInt("readcount")
//										)
//								);
//				}while(rs.next());
//				
//			}
//			
//			
//		} catch(Exception e) {
//			System.out.println("DogDAO클래스 : selectDogList() 에러 "+e);//예외객체종류 + 예외메시지
//		} finally {
//			close(pstmt); //JdbcUtil.생략가능
//			close(rs); //JdbcUtil.생략가능
//			//connection 객체에 대한 해제는 DogListService에서 이루어짐
//		}
//		
//		return dogList;
//	}
	
	//1. 로그인 폼에서 전달받은 id와 pw로 회원여부 조회한 후, 그 id를 다시 반환 (boolean으로 반환해도 됨)
	public String selectLoginId(MemberBean admin) {
		String loginId = null;
		
		String sql = "select id, password from member_table where id=? and password=?";
		
		System.out.println("[AdminDAO] selectLoginId() - 매개변수의 id : "+admin.getId());
		System.out.println("[AdminDAO] selectLoginId() - 매개변수의 password : "+admin.getPassword());
		
		try {
			//PreparedStatement 객체 생성
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, admin.getId());
			pstmt.setString(2, admin.getPassword());
			
			//sql 실행
			rs = pstmt.executeQuery();	
			
			//결과 처리
			if(rs.next()) {
				loginId = rs.getString("id"); 
			}
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] selectLoginId() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return loginId;
	}

	public MemberBean selectAdminInfo(String a_id) {
		MemberBean adminInfo = null;
		
		String sql = "select * from member_table where id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, a_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { 
				adminInfo = new MemberBean();//기본값으로 채워짐
				
				//조회된 값으로 채움 (★단 비밀번호는 담지 말 것)
				adminInfo.setId(rs.getString("id"));
				adminInfo.setGrade(rs.getString("grade"));
				adminInfo.setName(rs.getString("name"));
				adminInfo.setEmail(rs.getString("email"));
				adminInfo.setPhone(rs.getString("phone"));
			}
			
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] selectAdminInfo() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return adminInfo;
	}

	public int getLastMonthMoney(String id) {
		int lastMonthMoney = 0;
		
		String sql = "";
		
		try {
			
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, a_id);
//			
//			rs = pstmt.executeQuery();
//			
//			if(rs.next()) { 
//				adminInfo = new MemberBean();//기본값으로 채워짐
//				
//				//조회된 값으로 채움 (★단 비밀번호는 담지 말 것)
//				adminInfo.setId(rs.getString("id"));
//				adminInfo.setGrade(rs.getString("grade"));
//				adminInfo.setName(rs.getString("name"));
//				adminInfo.setEmail(rs.getString("email"));
//				adminInfo.setPhone(rs.getString("phone"));
//			}
			
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] getLastMonthMoney() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return lastMonthMoney;
	}
	
	
	//등급을 'NORMAL'이나 'GOLD'나 'VIP'로 업그레이드 ---------------------------------------------------
	//public int updateGrade(Memberbean admin, String grade){} 이걸로 만들고 싶당..
	public int gradeNORMAL(MemberBean admin) {
		// TODO 자동 생성된 메소드 스텁
		int gradeCount = 0; //수정 성공시 1, 실패시 0
		
		String sql = "update member_table set grade='NORMAL' where id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, admin.getId());
			
			gradeCount = pstmt.executeUpdate();//성공시 1, 실패시 0 리턴
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] gradeNORMAL() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
	
		return gradeCount;
	}
	
	public int gradeGOLD(MemberBean admin) {
		// TODO 자동 생성된 메소드 스텁
		int gradeCount = 0; //수정 성공시 1, 실패시 0
		
		String sql = "update member_table set grade='GOLD' where id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, admin.getId());
			
			gradeCount = pstmt.executeUpdate();//성공시 1, 실패시 0 리턴
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] gradeGOLD() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
	
		return gradeCount;
	}
	
	public int gradeVIP(MemberBean admin) {
		// TODO 자동 생성된 메소드 스텁
		int gradeCount = 0; //수정 성공시 1, 실패시 0
		
		String sql = "update member_table set grade='VIP' where id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, admin.getId());
			
			gradeCount = pstmt.executeUpdate();//성공시 1, 실패시 0 리턴
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] gradeVIP() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
	
		return gradeCount;
	}

	public double getSaleRate(String a_grade) {
		
		double saleRate = 1.0;//값*1.0 : 할인없이 제값을 지불 (근데 이건 지불할 금액이 아니라 할인률인데 0이 맞지 않나? '정가-(정가*할인률) = 지불할금액' 이렇게 될 텐데..)
		
		String sql = "select salerate from grade_table where grade=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, a_grade);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				//NORMAL=0/100.0=0, GOLD=5/100.0=0.05, VIP=10/100.0=0.1
				saleRate = rs.getInt("salerate")/100.0;
			}						
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] getSaleRate() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return saleRate;
		
	}

	public int insertAdmin(MemberBean admin) {
		int insertAdminCount = 0;
		
		//joindate timestamp default now() -> joindate 생략
		String sql = "insert into member_table(id, grade, password, name, email, phone) "
					+ "values(?,?,?,?,?,?)";
		
		//joindate timestamp (디폴트값 없음) -> insert into member_table values(?,?,?,?,?,?,now());
		
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, admin.getId());
			pstmt.setString(2, admin.getGrade());
			
			pstmt.setString(3, admin.getPassword());
			//암호화가 안 된 상태라면 pstmt.setString(3, SHA256.encodeSHA256(admin.getPassword()));
						
			pstmt.setString(4, admin.getName());
			pstmt.setString(5, admin.getEmail());
			pstmt.setString(6, admin.getPhone());
			
			insertAdminCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] insertAdmin() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return insertAdminCount;
	}

	public int insertAddr(AddressBean addr) {
		int insertAddrCount = 0;
		
		String sql = "insert into address_table(id, postcode, address1, address2) values(?,?,?,?)";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, addr.getId());
			pstmt.setInt(2, addr.getPostcode());
			pstmt.setString(3, addr.getAddress1());
			pstmt.setString(4, addr.getAddress2());
			
			insertAddrCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] insertAddr() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return insertAddrCount;
	}

	public AddressBean selectAddrInfo(String a_id) {
		AddressBean addrInfo = null;
		
		String sql = "select * from address_table where id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, a_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				addrInfo = new AddressBean();
				
				addrInfo.setId(rs.getString("id"));
				addrInfo.setPostcode(rs.getInt("postcode"));
				addrInfo.setAddress1(rs.getString("address1"));
				addrInfo.setAddress2(rs.getString("address2"));
			}
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] selectAddrInfo() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return addrInfo;
	}
	
	//회원정보수정
	public int updateAdmin(MemberBean admin) {
		int updateAdminCount = 0;
		
		String sql = "update member_table"
				   + " set password=?, name=?, email=?, phone=?"
				   + " where id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, admin.getPassword());
			//암호화가 안 된 상태라면 pstmt.setString(3, SHA256.encodeSHA256(admin.getPassword()));
			pstmt.setString(2, admin.getName());
			pstmt.setString(3, admin.getEmail());
			pstmt.setString(4, admin.getPhone());
			
			pstmt.setString(5, admin.getId());
			
			updateAdminCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] updateAdmin() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return updateAdminCount;
	}

	//회원정보수정 시 회원의 주소 수정
	public int updateAddr(AddressBean addr) {
		int updateAddrCount = 0;
		
		String sql = "update address_table"
					+ " set postcode=?, address1=?, address2=?"
			   		+ " where id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, addr.getPostcode());
			pstmt.setString(2, addr.getAddress1());
			pstmt.setString(3, addr.getAddress2());
			pstmt.setString(4, addr.getId());
			
			updateAddrCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] updateAddr() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return updateAddrCount;
	}

	//회원탈퇴 시 회원정보 삭제
	public int deleteAdmin(String id) {
		int deleteeAddrCount = 0;
		
		String sql = "delete from member_table where id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
						
			deleteeAddrCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] deleteAdmin() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return deleteeAddrCount;
	}

	//회원탈퇴 시 회원의 주소 삭제
	public int deleteAddr(String id) {
		int deleteeAddrCount = 0;
		
		String sql = "delete from address_table where id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			
			deleteeAddrCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] deleteAddr() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return deleteeAddrCount;
	}

	//이메일로 '아이디 찾기'
	public MemberBean findId(String email) {
		MemberBean adminInfo = null;
		
		String sql = "select * from member_table where email=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				adminInfo = new MemberBean();
				
				adminInfo.setId(rs.getString("id"));
				adminInfo.setGrade(rs.getString("grade"));
				adminInfo.setName(rs.getString("name"));
				adminInfo.setEmail(rs.getString("email"));
				adminInfo.setPhone(rs.getString("phone"));
			}
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] findId() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return adminInfo;
	}

	//아이디 중복 체크 (해당 아이디가 member_table에 존재하는지 확인)
	public int checkId(String id) {
		
		int idCheckCount = 0;
		
		String sql = "select id from member_table where id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] checkId() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return idCheckCount;
		
	}
	
	//암호화된 비밀번호 찾기 : 임시비밀번호를 화면에 출력하거나 이메일로 전송
	public MemberBean findHashPw(String a_id, String a_email) {
		MemberBean adminInfo = null;
		
		//방법-1
		String sql = "select name from member_table where id=? and email=?";
		
		//방법-2
		//String sql = "select * from member_table where id=? and email=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, a_id);
			pstmt.setString(2, a_email);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				adminInfo = new MemberBean();
				
				//방법-1
				adminInfo.setId(a_id);
				adminInfo.setEmail(a_email);
				adminInfo.setName(rs.getString("name"));	
				
				//방법-2
				//adminInfo.setId(rs.getString("id"));				
				//adminInfo.setEmail(rs.getString("email"));				
				//adminInfo.setName(rs.getString("name"));		
			}
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] findHashPw() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return adminInfo;
	}

	//임시 비밀번호를 암호화시켜 다시 member_table에 저장
	public int setHashPw(String id, String email, String random_password) {
		int setHashPwCount = 0;
		
		String sql = "update member_table set password=? where id=? and email=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, SHA256.encodeSHA256(random_password));
			pstmt.setString(2, id);
			pstmt.setString(3, email);
			
			setHashPwCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] setHashPw() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return setHashPwCount;
	}

	public int changeHashPw(MemberPwChangeBean memberPwChangeBean) {
		int changeHashPwCount = 0;
		
		String sql = "update member_table set password=? where id=? and password=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberPwChangeBean.getNew_password());
			pstmt.setString(2, memberPwChangeBean.getId());
			pstmt.setString(3, memberPwChangeBean.getPre_password());
			
			changeHashPwCount = pstmt.executeUpdate();
			System.out.println("changeHashPwCount: "+changeHashPwCount);
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] changeHashPw() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return changeHashPwCount;
	}
	
}
