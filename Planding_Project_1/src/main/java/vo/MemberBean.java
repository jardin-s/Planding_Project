package vo;//=DTO

import util.SHA256;

public class MemberBean {
	
	//PlanDing
	private String member_id; //회원 ID
	private String password; //비밀번호
	private String name; //이름
	private String email; //이메일
	private int account; //처음 가입한 회원은 0부터 시작
	private boolean isAdmin; //회원은 false, 관리자는 true
	
	private String joindate; //SQL에서 가입일 자동세팅
		
	/* 
	 * 매개변수가 없는 생성자 : 암호화방법-1
	 * - 방법-1 : 기본값 -> 암호화X -> DAO에서 insert 전에 암호화
	 * - 방법-2 : 기본값 -> set() 암호화O -> DAO에서 insert
	 */
	public MemberBean() {}

	public MemberBean(String member_id, String password, String name, String email, int account, boolean isAdmin) {
		super();
		this.member_id = member_id;
		this.password = SHA256.encodeSHA256(password);
		this.name = name;
		this.email = email;
		this.account = account;
		this.isAdmin = isAdmin;
		//this.joindate = joindate;
	}
	

	/* 
	 * 매개변수가 있는 생성자 : 암호화방법-2
	 * password 암호화O -> DAO에서 insert
	 */
	
	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	//매개값을 암호화하여 세팅하는 set메서드
	public void setSHA256Password(String password) {
		this.password = SHA256.encodeSHA256(password);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public String getJoindate() {
		return joindate;
	}

	public void setJoindate(String joindate) {
		this.joindate = joindate;
	}
	
	
}
