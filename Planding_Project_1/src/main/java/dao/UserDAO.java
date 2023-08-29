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

public class UserDAO {
	
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
	private UserDAO() {}
	
	
	
	private static UserDAO userDAO; //static메서드인 getInstance에서 쓸 수 있게 static (단 외부에서 직접 접근 불가능하도록 private)
	
	public static UserDAO getInstance() {
		if(userDAO == null) {//DogDAO객체가 없으면
			userDAO = new UserDAO();//객체 생성
		}
		
		return userDAO;//기존 DogDAO객체의 주소 리턴
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
	public String selectLoginId(MemberBean user) {
		String loginId = null;
		
		String sql = "select member_id, password from member_tbl where member_id=? and password=?";
		
		System.out.println("[UserDAO] selectLoginId() - 매개변수의 id : "+user.getMember_id());
		System.out.println("[UserDAO] selectLoginId() - 매개변수의 password : "+user.getPassword());
		
		try {
			//PreparedStatement 객체 생성
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getMember_id());
			pstmt.setString(2, user.getPassword());
			
			//sql 실행
			rs = pstmt.executeQuery();	
			
			//결과 처리
			if(rs.next()) {
				loginId = rs.getString("member_id"); 
			}
			
		} catch(Exception e) {
			System.out.println("[UserDAO] selectLoginId() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return loginId;
	}

	public MemberBean selectUserInfo(String u_id) {
		MemberBean userInfo = null;
		
		String sql = "select * from member_tbl where member_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { 
				userInfo = new MemberBean();//기본값으로 채워짐
				
				//조회된 값으로 채움 (★단 비밀번호는 담지 말 것)
				userInfo.setMember_id(rs.getString("member_id"));
				userInfo.setName(rs.getString("name"));
				userInfo.setEmail(rs.getString("email"));
				userInfo.setAccount(rs.getInt("account"));
				userInfo.setAdmin(rs.getBoolean("isAdmin"));
			}
			
			
		} catch(Exception e) {
			System.out.println("[UserDAO] selectUserInfo() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return userInfo;
	}

		
	//아이디 중복체크
	public MemberBean checkIdExist(String id) {
		MemberBean userInfo = null;
		
		String sql = "select * from member_tbl where member_id=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userInfo = new MemberBean();
				
				userInfo.setMember_id(rs.getString("member_id"));
			}						
			
		} catch(Exception e) {
			System.out.println("[UserDAO] checkIdExist() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return userInfo;
		
	}

	public int insertUser(MemberBean user) {
		int insertUserCount = 0;
		
		//joindate timestamp default now() -> joindate 생략
		String sql = "insert into member_tbl(member_id, password, name, email, account, isAdmin) "
					+ "values(?,?,?,?,?,?,?)";
		
		//joindate timestamp (디폴트값 없음) -> insert into member_tbl values(?,?,?,?,?,?,now());
		
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, user.getMember_id());
			
			pstmt.setString(2, user.getPassword());
			//암호화가 안 된 상태라면 pstmt.setString(3, SHA256.encodeSHA256(user.getPassword()));
						
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getEmail());
			pstmt.setInt(5, user.getAccount());
			pstmt.setBoolean(6, user.isAdmin());
			
			insertUserCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[UserDAO] insertUser() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return insertUserCount;
	}

	public int insertAddr(AddressBean addr) {
		int insertAddrCount = 0;
		
		String sql = "insert into address_tbl(member_id, receiver_name, phone, postcode, address1, address2) values(?,?,?,?,?,?)";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, addr.getMember_id());
			pstmt.setString(2, addr.getReceiver_name());
			pstmt.setString(3, addr.getPhone());
			pstmt.setInt(4, addr.getPostcode());
			pstmt.setString(5, addr.getAddress1());
			pstmt.setString(6, addr.getAddress2());
			
			insertAddrCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[UserDAO] insertAddr() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return insertAddrCount;
	}

	public AddressBean selectAddrInfo(String u_id) {
		AddressBean addrInfo = null;
		
		String sql = "select * from address_tbl where member_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				addrInfo = new AddressBean();
				
				addrInfo.setMember_id(rs.getString("member_id"));
				addrInfo.setReceiver_name(rs.getString("receiver_name"));
				addrInfo.setPhone(rs.getString("phone"));
				addrInfo.setPostcode(rs.getInt("postcode"));
				addrInfo.setAddress1(rs.getString("address1"));
				addrInfo.setAddress2(rs.getString("address2"));
			}
			
		} catch(Exception e) {
			System.out.println("[UserDAO] selectAddrInfo() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return addrInfo;
	}
	
	//회원정보수정
	public int updateUser(MemberBean user) {
		int updateUserCount = 0;
		
		String sql = "update member_tbl"
				   + " set password=?, name=?, email=?"
				   + " where member_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, user.getPassword());
			//암호화가 안 된 상태라면 pstmt.setString(3, SHA256.encodeSHA256(user.getPassword()));
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getEmail());
			
			pstmt.setString(4, user.getMember_id());
			
			updateUserCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[UserDAO] updateUser() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return updateUserCount;
	}

	//회원정보수정 시 회원의 주소 수정
	public int updateAddr(AddressBean addr) {
		int updateAddrCount = 0;
		
		String sql = "update address_tbl"
					+ " set receiver_name=?, phone=?, postcode=?, address1=?, address2=?"
			   		+ " where member_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, addr.getReceiver_name());
			pstmt.setString(2, addr.getPhone());
			pstmt.setInt(3, addr.getPostcode());
			pstmt.setString(4, addr.getAddress1());
			pstmt.setString(5, addr.getAddress2());
			
			pstmt.setString(6, addr.getMember_id());
			
			updateAddrCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[UserDAO] updateAddr() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return updateAddrCount;
	}

	//회원탈퇴 시 회원정보 삭제
	public int deleteUser(String id) {
		int deleteeAddrCount = 0;
		
		String sql = "delete from member_tbl where member_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
						
			deleteeAddrCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[UserDAO] deleteUser() 에러 : "+e);//예외객체종류 + 예외메시지
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
		
		String sql = "delete from address_tbl where member_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			
			deleteeAddrCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[UserDAO] deleteAddr() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return deleteeAddrCount;
	}

	//이메일로 '아이디 찾기'
	public MemberBean findId(String email) {
		MemberBean userInfo = null;
		
		String sql = "select * from member_tbl where email=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userInfo = new MemberBean();
				
				userInfo.setMember_id(rs.getString("member_id"));
				userInfo.setName(rs.getString("name"));
				userInfo.setEmail(rs.getString("email"));
			}
			
		} catch(Exception e) {
			System.out.println("[UserDAO] findId() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return userInfo;
	}

	//암호화된 비밀번호 찾기 : 임시비밀번호를 화면에 출력하거나 이메일로 전송
	//해당 아이디와 이메일이 존재하는지 먼저 확인
	public MemberBean findHashPw(String u_id, String u_email) {
		MemberBean userInfo = null;
		
		//방법-1
		String sql = "select name from member_tbl where member_id=? and email=?";
		
		//방법-2
		//String sql = "select * from member_tbl where member_id=? and email=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u_id);
			pstmt.setString(2, u_email);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userInfo = new MemberBean();
				
				//방법-1
				userInfo.setMember_id(u_id);
				userInfo.setEmail(u_email);
				userInfo.setName(rs.getString("name"));	
				
				//방법-2
				//userInfo.setId(rs.getString("id"));				
				//userInfo.setEmail(rs.getString("email"));				
				//userInfo.setName(rs.getString("name"));		
			}
			
		} catch(Exception e) {
			System.out.println("[UserDAO] findHashPw() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return userInfo;
	}

	//임시 비밀번호를 암호화시켜 다시 member_tbl에 저장
	public int setHashPw(String id, String email, String random_password) {
		int setHashPwCount = 0;
		
		String sql = "update member_tbl set password=? where member_id=? and email=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, SHA256.encodeSHA256(random_password));
			pstmt.setString(2, id);
			pstmt.setString(3, email);
			
			setHashPwCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[UserDAO] setHashPw() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return setHashPwCount;
	}

	//임시비밀번호 발급 후 새 비밀번호로 변경
	public int changeHashPw(MemberPwChangeBean memberPwChangeBean) {
		int changeHashPwCount = 0;
		
		String sql = "update member_tbl set password=? where member_id=? and password=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberPwChangeBean.getNew_password());
			pstmt.setString(2, memberPwChangeBean.getId());
			pstmt.setString(3, memberPwChangeBean.getPre_password());
			
			changeHashPwCount = pstmt.executeUpdate();
			System.out.println("changeHashPwCount: "+changeHashPwCount);
			
		} catch(Exception e) {
			System.out.println("[UserDAO] changeHashPw() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return changeHashPwCount;
	}
	
}
