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

	
	//1. 로그인 폼에서 전달받은 id와 pw로 회원여부 조회한 후, 그 id를 다시 반환 (boolean으로 반환해도 됨)
	public MemberBean selectLoginAdmin(MemberBean admin) {
		MemberBean loginAdmin = null;
		
		String sql = "select member_id, password, admin_status from member_tbl where member_id=? and password=?";
		
		System.out.println("[AdminDAO] selectLoginId() - 매개변수의 id : "+admin.getMember_id());
		System.out.println("[AdminDAO] selectLoginId() - 매개변수의 password : "+admin.getPassword());
		
		try {
			//PreparedStatement 객체 생성
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, admin.getMember_id());
			pstmt.setString(2, admin.getPassword());
			
			//sql 실행
			rs = pstmt.executeQuery();	
			
			//결과 처리
			if(rs.next()) {
				loginAdmin = new MemberBean();
				loginAdmin.setMember_id(rs.getString("member_id"));
				loginAdmin.setAdmin_status(rs.getString("admin_status"));
			}
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] selectLoginId() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return loginAdmin;
	}

	public MemberBean selectAdminInfo(String a_id) {
		MemberBean adminInfo = null;
		
		String sql = "select * from member_tbl where member_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, a_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { 
				adminInfo = new MemberBean();//기본값으로 채워짐
				
				//조회된 값으로 채움 (★단 비밀번호는 담지 말 것)
				adminInfo.setMember_id(rs.getString("member_id"));
				adminInfo.setName(rs.getString("name"));
				adminInfo.setEmail(rs.getString("email"));
				adminInfo.setPhone(rs.getString("phone"));
				adminInfo.setAccount(rs.getInt("account"));
				adminInfo.setAdmin_status(rs.getString("admin_status"));
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

		
	//아이디 중복체크
	public MemberBean checkIdExist(String id) {
		MemberBean adminInfo = null;
		
		String sql = "select * from member_tbl where member_id=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				adminInfo = new MemberBean();
				
				adminInfo.setMember_id(rs.getString("member_id"));
			}						
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] checkIdExist() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return adminInfo;
		
	}

	public int insertAdmin(MemberBean admin) {
		int insertAdminCount = 0;
		
		//joindate timestamp default now() -> joindate 생략
		String sql = "insert into member_tbl(member_id, password, name, email, phone, account, admin_status) "
					+ "values(?,?,?,?,?,?,?)";
		
		//joindate timestamp (디폴트값 없음) -> insert into member_tbl values(?,?,?,?,?,?,now());
		
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, admin.getMember_id());
			
			pstmt.setString(2, admin.getPassword());
			//암호화가 안 된 상태라면 pstmt.setString(3, SHA256.encodeSHA256(admin.getPassword()));
						
			pstmt.setString(3, admin.getName());
			pstmt.setString(4, admin.getEmail());
			pstmt.setString(5, admin.getPhone());
			pstmt.setInt(6, admin.getAccount());
			pstmt.setString(7, admin.getAdmin_status());
			
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
		
		String sql = "select * from address_tbl where member_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, a_id);
			
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
		
		String sql = "update member_tbl"
				   + " set password=?, name=?, email=?, phone=?"
				   + " where member_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, admin.getPassword());
			//암호화가 안 된 상태라면 pstmt.setString(3, SHA256.encodeSHA256(admin.getPassword()));
			pstmt.setString(2, admin.getName());
			pstmt.setString(3, admin.getEmail());
			pstmt.setString(4, admin.getPhone());
			
			pstmt.setString(5, admin.getMember_id());
			
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
			System.out.println("[AdminDAO] updateAddr() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return updateAddrCount;
	}

	//회원탈퇴 시 회원정보 삭제
	public int updateDeleteAdmin(String id) {
		int udpateDeleteAdminCount = 0;
		
		String sql = "update member_tbl"
			   	  + " set password='delete', name='delete', email='delete', phone='delete',"
			   	  + " delete_status='Y',"
			   	  + " deletedate=current_timestamp"
			   	  + " where member_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
						
			udpateDeleteAdminCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[AdminDAO] deleteAdmin() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return udpateDeleteAdminCount;
	}

	//회원탈퇴 시 회원의 주소 삭제
	public int deleteAddr(String id) {
		int deleteeAddrCount = 0;
		
		String sql = "delete from address_tbl where member_id=?";
		
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
		
		String sql = "select * from member_tbl where email=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				adminInfo = new MemberBean();
				
				adminInfo.setMember_id(rs.getString("member_id"));
				adminInfo.setName(rs.getString("name"));
				adminInfo.setEmail(rs.getString("email"));
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

	//암호화된 비밀번호 찾기 : 임시비밀번호를 화면에 출력하거나 이메일로 전송
	//해당 아이디와 이메일이 존재하는지 먼저 확인
	public MemberBean findHashPw(String a_id, String a_email) {
		MemberBean adminInfo = null;
		
		//방법-1
		String sql = "select name from member_tbl where member_id=? and email=?";
		
		//방법-2
		//String sql = "select * from member_tbl where member_id=? and email=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, a_id);
			pstmt.setString(2, a_email);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				adminInfo = new MemberBean();
				
				//방법-1
				adminInfo.setMember_id(a_id);
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
			System.out.println("[AdminDAO] setHashPw() 에러 : "+e);//예외객체종류 + 예외메시지
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
			System.out.println("[AdminDAO] changeHashPw() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return changeHashPwCount;
	}

	
	

	
}
