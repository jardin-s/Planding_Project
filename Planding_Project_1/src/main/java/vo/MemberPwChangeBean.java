package vo;

import util.SHA256;

public class MemberPwChangeBean {

	private String id;
	private String pre_password;
	private String new_password;
	
	public MemberPwChangeBean() {}

	//비밀번호를 암호화 시켜서 세팅하는 생성자
	public MemberPwChangeBean(String id, String pre_password, String new_password) {
		super();
		this.id = id;
		this.pre_password = SHA256.encodeSHA256(pre_password);//암호화된 상태로 DB에 들어가있는 기존 비밀번호와 비교
		this.new_password = SHA256.encodeSHA256(new_password);//암호화시켜 DB에 저장
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPre_password() {
		return pre_password;
	}

	public void setPre_password(String pre_password) {
		this.pre_password = pre_password;
	}

	public String getNew_password() {
		return new_password;
	}

	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}
	
	
	
}
