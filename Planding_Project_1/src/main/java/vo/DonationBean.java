package vo;

public class DonationBean {
	
	private int donation_id;
	private int project_id;
	private String member_id;
	private String reward_id;
	private int r_price;//해당 리워드 금액
	private int add_donation;//추가 후원금액
	
	private String address_id;//리워드 배송받을 경우 (펀딩프로젝트)
	private String donatedate;
	
	//-------------------------------------------------------
	
	private int totalDonation;//리워드금액 + 추가후원금액 = 총 후원금액 (sql에는 없음)
	
	
	//(이주헌) ByRewardDonationListAction
	//DB과 상관 없이 후원정보에 종속된 주소를 가져오고자 함
	private String receiver_name; //수령인 이름
	private String receiver_phone; //연락처
	
	private int postcode; //우편번호
	private String address1; //주소1 (자동세팅)
	private String address2; //주소2 (상세주소 직접입력)
	
	
	
	
	//생성자
	
	public DonationBean () {}

	//후원할 시
	public DonationBean(int project_id, String member_id, String reward_id, int r_price, int add_donation) {
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
	public DonationBean(int project_id, String member_id, String reward_id, int r_price, int add_donation, String address_id) {
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
	public DonationBean(int donation_id, int project_id, String member_id, String reward_id, int r_price, int add_donation,
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
	public DonationBean(int donation_id, int project_id, String member_id, String reward_id, int r_price, int add_donation,
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

	public String getReward_id() {
		return reward_id;
	}

	public void setReward_id(String reward_id) {
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
	
	//★★★ 리워드 금액 + 추가후원금액으로 세팅된 걸로 가지고오기
	public int getTotalDonation_Cal() {
		return r_price + add_donation;
	}
	
	//★★★ 리워드 금액 + 추가후원금액으로 세팅
	public void setTotalDonation_Cal(int r_price, int add_donation) {
		this.totalDonation = r_price + add_donation;
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
	
	
	
	//------------------------------------------------------------------

	public String getReceiver_name() {
		return receiver_name;
	}

	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public String getReceiver_phone() {
		return receiver_phone;
	}

	public void setReceiver_phone(String receiver_phone) {
		this.receiver_phone = receiver_phone;
	}

	public int getPostcode() {
		return postcode;
	}

	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	
	

	
}
