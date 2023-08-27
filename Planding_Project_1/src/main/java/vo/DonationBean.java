package vo;

import java.util.Date;

public class DonationBean {
	
	private int donation_id;
	private int project_id;
	private String member_id;
	private int reward_id;
	private int add_donation;
	private Date donatedate;
	private String comment;
	
	public DonationBean () {}

	public DonationBean(int donation_id, int project_id, String member_id, int reward_id, int add_donation,
			Date donatedate, String comment) {
		super();
		this.donation_id = donation_id;
		this.project_id = project_id;
		this.member_id = member_id;
		this.reward_id = reward_id;
		this.add_donation = add_donation;
		this.donatedate = donatedate;
		this.comment = comment;
	}

	public int getDonation_id() {
		return donation_id;
	}

	public void setDonation_id(int donation_id) {
		this.donation_id = donation_id;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public int getReward_id() {
		return reward_id;
	}

	public void setReward_id(int reward_id) {
		this.reward_id = reward_id;
	}

	public int getAdd_donation() {
		return add_donation;
	}

	public void setAdd_donation(int add_donation) {
		this.add_donation = add_donation;
	}

	public Date getDonatedate() {
		return donatedate;
	}

	public void setDonatedate(Date donatedate) {
		this.donatedate = donatedate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
