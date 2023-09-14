package vo;

public class DonationBean {
	
	private int donation_id;
	private int project_id;
	private String member_id;
	private int reward_id;
	private int r_price;//해당 리워드 금액
	private int add_donation;//추가 후원금액
	
	private String address_id;//리워드 배송받을 경우 (펀딩프로젝트)
	private String donatedate;
	
	private int totalDonation;//리워드금액 + 추가후원금액 = 총 후원금액 (sql에는 없음)
	
	public DonationBean () {}

	//후원할 시
	public DonationBean(int project_id, String member_id, int reward_id, int r_price, int add_donation) {
		super();
		//this.donation_id = donation_id; //sql 자동1증가
		this.project_id = project_id;
		this.member_id = member_id;
		this.reward_id = reward_id;
		this.r_price = r_price;
		this.add_donation = add_donation;
		//this.donatedate = donatedate; //sql 자동세팅
	}
	
	//후원할 시
	public DonationBean(int project_id, String member_id, int reward_id, int r_price, int add_donation, String address_id) {
		super();
		//this.donation_id = donation_id; //sql 자동1증가
		this.project_id = project_id;
		this.member_id = member_id;
		this.reward_id = reward_id;
		this.r_price = r_price;
		this.add_donation = add_donation;
		this.address_id = address_id;
		//this.donatedate = donatedate; //sql 자동세팅
	}
	
	//후원기록 조회시
	public DonationBean(int donation_id, int project_id, String member_id, int reward_id, int r_price, int add_donation,
			String address_id, String donatedate) {
		super();
		this.donation_id = donation_id;
		this.project_id = project_id;
		this.member_id = member_id;
		this.reward_id = reward_id;
		this.r_price = r_price;
		this.add_donation = add_donation;
		this.address_id = address_id;
		this.donatedate = donatedate;
	}
	
	//후원기록 조회시
	public DonationBean(int donation_id, int project_id, String member_id, int reward_id, int r_price, int add_donation,
			int totalDonation, String address_id, String donatedate) {
		super();
		this.donation_id = donation_id;
		this.project_id = project_id;
		this.member_id = member_id;
		this.reward_id = reward_id;
		this.r_price = r_price;
		this.totalDonation = totalDonation;
		this.add_donation = add_donation;
		this.address_id = address_id;
		this.donatedate = donatedate;
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
	
	public int getR_price() {
		return r_price;
	}

	public void setR_price(int r_price) {
		this.r_price = r_price;
	}

	public int getAdd_donation() {
		return add_donation;
	}

	public void setAdd_donation(int add_donation) {
		this.add_donation = add_donation;
	}
	
	public int getTotalDonation() {
		return totalDonation;
	}

	public void setTotalDonation(int totalDonation) {
		this.totalDonation = totalDonation;
	}
	
	public String getAddress_id() {
		return address_id;
	}
	
	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}

	public String getDonatedate() {
		return donatedate;
	}

	public void setDonatedate(String donatedate) {
		this.donatedate = donatedate;
	}

	
}
