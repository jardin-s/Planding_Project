package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


import util.SHA256;
import vo.AddressBean;
import vo.BookmarkBean;
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

	//아이디로 회원정보를 가져옴
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
				userInfo.setPhone(rs.getString("phone"));
				userInfo.setMoney(rs.getInt("money"));
				userInfo.setAdmin_status(rs.getString("admin_status"));
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
		String sql = "insert into member_tbl(member_id, password, name, email, phone, money, admin_status) "
					+ "values(?,?,?,?,?,?,?)";
		
		//joindate timestamp (디폴트값 없음) -> insert into member_tbl values(?,?,?,?,?,?,now());
		
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, user.getMember_id());
			
			pstmt.setString(2, user.getPassword());
			//암호화가 안 된 상태라면 pstmt.setString(3, SHA256.encodeSHA256(user.getPassword()));
						
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getPhone());
			pstmt.setInt(6, user.getMoney());
			pstmt.setString(7, user.getAdmin_status());
			
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
		
		String sql = "insert into address_tbl values(?,?,?,?,?,?,?,?)";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, addr.getAddress_id());
			pstmt.setString(2, addr.getMember_id());
			pstmt.setString(3, addr.getReceiver_name());
			pstmt.setString(4, addr.getReceiver_phone());
			pstmt.setInt(5, addr.getPostcode());
			pstmt.setString(6, addr.getAddress1());
			pstmt.setString(7, addr.getAddress2());
			pstmt.setString(8, addr.getBasic_status());
			
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

	public AddressBean selectBasicAddrInfo(String member_id) {
		AddressBean addrInfo = null;
		
		//해당 회원의 id 중, 기본주소의 정보만 가져옴
		String sql = "select * "
				  + " from address_tbl"
				  + " where basic_status='Y'"
				  + " and member_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				addrInfo = new AddressBean();
				
				addrInfo.setAddress_id(rs.getString("address_id"));
				addrInfo.setMember_id(rs.getString("member_id"));
				addrInfo.setReceiver_name(rs.getString("receiver_name"));
				addrInfo.setReceiver_phone(rs.getString("receiver_phone"));
				addrInfo.setPostcode(rs.getInt("postcode"));
				addrInfo.setAddress1(rs.getString("address1"));
				addrInfo.setAddress2(rs.getString("address2"));
			}
			
		} catch(Exception e) {
			System.out.println("[UserDAO] selectBasicAddrInfo() 에러 : "+e);//예외객체종류 + 예외메시지
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
				   + " set name=?, email=?, phone=?"
				   + " where member_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			//pstmt.setString(1, user.getPassword());
			//암호화가 안 된 상태라면 pstmt.setString(3, SHA256.encodeSHA256(user.getPassword()));
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPhone());
			
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
	public int updateBasicAddr(AddressBean addr) {
		int updateAddrCount = 0;
		
		String sql = "update address_tbl"
					+ " set receiver_name=?, receiver_phone=?, postcode=?, address1=?, address2=?"
			   		+ " where address_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, addr.getReceiver_name());
			pstmt.setString(2, addr.getReceiver_phone());
			pstmt.setInt(3, addr.getPostcode());
			pstmt.setString(4, addr.getAddress1());
			pstmt.setString(5, addr.getAddress2());
			
			pstmt.setString(6, addr.getAddress_id());
			
			updateAddrCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[UserDAO] updateBasicAddr() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return updateAddrCount;
	}

	//회원탈퇴 시 회원정보 삭제
	public int updateDeleteUser(String id) {
		int updateDeleteUserCount = 0;
		
		//비밀번호, 이름, 이메일, 전화번호의 개인정보 삭제(계좌잔액, 관리자여부 제외)
		//탈퇴여부 Y, 탈퇴일시 현재시간으로 업데이트
		String sql = "update member_tbl"
				 + " set password='delete', name='delete',"
				 + " email='delete', phone='delete',"
				 + " delete_status='Y',"
				 + " deletedate=current_timestamp"
				 + " where member_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
						
			updateDeleteUserCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[UserDAO] updateDeleteUser() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return updateDeleteUserCount;
	}

	//회원탈퇴 시 회원의 모든 주소 삭제
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
				userInfo.setPhone(rs.getString("phone"));
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
				
				System.out.println("[UserDAO] findHashPw() : 찾은회원의 name = "+rs.getString("name"));
				
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

	public ArrayList<BookmarkBean> selectBookmarkList(String u_id) {
		ArrayList<BookmarkBean> bookmarkList = null;
		
		String sql = "select project_id, DATE_FORMAT(likedate, %Y-%m-%d) as likedate";
			   sql += " from bookmark_tbl";
			   sql += " where member_id=?";
			   sql += " order by likedate desc";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bookmarkList = new ArrayList<>();
				
				do {
					bookmarkList.add(new BookmarkBean(u_id,
													  rs.getInt("project_id"),
													  rs.getString("likedate")));
				}while(rs.next());
			}
			
			
		} catch(Exception e) {
			System.out.println("[UserDAO] selectBookmarkList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return bookmarkList;
	}

	public ArrayList<Integer> selectBookmarkIdList(String u_id) {
		
		ArrayList<Integer> bookmarkIdList = null;
		
		//최근순으로 정렬하여 projectId를 얻어옴
		String sql = "select project_id";
			   sql += " from bookmark_tbl";
			   sql += " where member_id=?";
			   sql += " order by likedate desc";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bookmarkIdList = new ArrayList<>();
				
				do {
					bookmarkIdList.add(rs.getInt("project_id"));
				}while(rs.next());
			}
			
			
		} catch(Exception e) {
			System.out.println("[UserDAO] selectBookmarkList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return bookmarkIdList;
	}

	/** 회원ID로 회원이 기획한 프로젝트ID리스트를 알아냄 */
	public ArrayList<Integer> selectUploadProjectIdList(String member_id) {
		
		ArrayList<Integer> uploadProjectIdList = null;
		
		String sql = "select project_id";
			   sql += " from project_planner_tbl";
			   sql += " where member_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				uploadProjectIdList = new ArrayList<>();
				
				do {
					uploadProjectIdList.add(rs.getInt("project_id"));
					System.out.println("[UserDAO] selectUploadProjectIdList() 값 확인 : "+rs.getInt("project_id"));
					
				}while(rs.next());
			}
			
			
		} catch(Exception e) {
			System.out.println("[UserDAO] selectUploadProjectIdList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return uploadProjectIdList;
	}

	public ArrayList<Integer> selectFundProjectIdList(String p_id) {
		ArrayList<Integer> fundProjectIdList = null;
		
		String sql = "select project_id";
			   sql += " from donation_tbl";
			   sql += " where member_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, p_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				fundProjectIdList = new ArrayList<>();
				
				do {
					fundProjectIdList.add(rs.getInt("project_id"));
				}while(rs.next());
			}
			
			
		} catch(Exception e) {
			System.out.println("[UserDAO] selectFundProjectIdList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return fundProjectIdList;
	}

	/** 회원의 계좌 잔액을 업데이트 (더하기) */
	public int updateUserPlusMoney(String member_id, int money) {
		
		int updateUserMoneyCount = 0;
		
		String sql = "update member_tbl"
				  + " set money = money + ?"
				  + " where member_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, money);
			pstmt.setString(2, member_id);
			
			updateUserMoneyCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[UserDAO] updateUserMoney() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return updateUserMoneyCount;
	}

	/** 사용자의 관심프로젝트 수를 가져옴 */
	public int selectBookmarkListCount(String member_id) {
		
		int bookmarkListCount = 0;
		
		String sql = "select count(*) from bookmark_tbl where member_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bookmarkListCount = rs.getInt(1);
			}			
			
		} catch(Exception e) {
			System.out.println("[UserDAO] selectBookmarkListCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return bookmarkListCount;
		
	}

	/** 프로젝트ID로 관심프로젝트 목록에서 삭제 */
	public int deleteBookmark(int project_id) {
		int deleteBookmarkCount = 0;
		
		String sql = "delete from bookmark_tbl where project_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, project_id);
			
			deleteBookmarkCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[UserDAO] deleteBookmark() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return deleteBookmarkCount;
	}

	/** 사용자ID로 현재 계좌잔액을 가져옴 */
	public int getUserMoney(String member_id) {
		int userMoney = 0;
		
		String sql = "select money from member_tbl where member_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userMoney = rs.getInt("money");
			}
			
		} catch(Exception e) {
			System.out.println("[UserDAO] getUserMoney() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return userMoney;
	}

	

	
	
}
