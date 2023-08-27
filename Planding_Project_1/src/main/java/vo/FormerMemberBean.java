package vo;

import java.util.Date;

public class FormerMemberBean {
	
	private String member_id;
	private String email;
	private Date joindate;
	private Date withdrawdate;
	
	public FormerMemberBean() {}
	
	public FormerMemberBean(String member_id, String email, Date joindate, Date withdrawdate) {
		super();
		this.member_id = member_id;
		this.email = email;
		this.joindate = joindate;
		this.withdrawdate = withdrawdate;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getJoindate() {
		return joindate;
	}

	public void setJoindate(Date joindate) {
		this.joindate = joindate;
	}

	public Date getWithdrawdate() {
		return withdrawdate;
	}

	public void setWithdrawdate(Date withdrawdate) {
		this.withdrawdate = withdrawdate;
	}
	
}
