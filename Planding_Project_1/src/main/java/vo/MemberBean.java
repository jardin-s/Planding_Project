package vo;//=DTO

import util.SHA256;

public class MemberBean {
	
	//PlanDing
	private String member_id; //회원 ID
	private String password; //비밀번호
	private String name; //이름
	private String email; //이메일
	private String phone; //전화번호
	private int account; //처음 가입한 회원은 0부터 시작
	private String admin_status; //Y관리자 N회원
	
	private String joindate; //SQL에서 가입일 자동세팅
	
	private String delete_status; //탈퇴시 업데이트 (YN) - 기본값N
	private String deletedate; //탈퇴시 SQL에서 업데이트
		
	/* 
	 * 매개변수가 없는 생성자 : 암호화방법-1
	 * - 방법-1 : 기본값 -> 암호화X -> DAO에서 insert 전에 암호화
	 * - 방법-2 : 기본값 -> set() 암호화O -> DAO에서 insert
	 */
	public MemberBean() {}

	//회원가입 시 사용
	public MemberBean(String member_id, String password, String name, String email, String phone, int account, String admin_status) {
		super();
		this.member_id = member_id;
		this.password = SHA256.encodeSHA256(password);
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.account = account;
		this.admin_status = admin_status;
		//this.joindate = joindate;
	}
	
	//탈퇴회원 조회시 사용
	public MemberBean(String member_id, String password, String name, String email, String phone, int account,
			String admin_status, String joindate, String delete_status, String deletedate) {
		super();
		this.member_id = member_id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.account = account;
		this.admin_status = admin_status;
		this.joindate = joindate;
		this.delete_status = delete_status;
		this.deletedate = deletedate;
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
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAdmin_status() {
		return admin_status;
	}

	public void setAdmin_status(String admin_status) {
		this.admin_status = admin_status;
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

	public String getDelete_status() {
		return delete_status;
	}

	public void setDelete_status(String delete_status) {
		this.delete_status = delete_status;
	}
	
	public String getDeletedate() {
		return deletedate;
	}

	public void setDeletedate(String deletedate) {
		this.deletedate = deletedate;
	}

}
