package vo;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

//프로젝트-후원기록 뷰 (내 후원내역 조회용)
public class ProjectDonationRewardBean {
	
	//[ProjectBean의 필드] -------------------------------------------------------------------
	private int project_id;//SQL 자동 1씩 증가
	
	private String kind;
	private String title;
	private String summary;
	private String thumbnail;
	
	private String content;
	private String image;
	
	private String startdate;
	private String enddate;
	
	private int goal_amount;
	private int curr_amount;
	
	private String status;//처음은 항상 unauthorized(미승인)
	private int likes;//0부터 시작
	
	private String regdate;//등록일자 (SQL 자동 현재시간 세팅)
	
	//----------------------------------------------------- 아래는 DB에 없음
	
	private double progress;//달성률 (조회시, 현재모금액/목표모금액 계산하여 세팅)
	
	private int deadline;//남은일수 (조회시, 오늘날짜-마감일 계산하여 세팅)
	
	private String goal_amount_df; //목표모금액 포맷 (천단위 구분자 넣어서 세팅)
	private String curr_amount_df; //현재모금액 포맷 (천단위 구분자 넣어서 세팅)
	
	
	//[DonationBean의 필드] -------------------------------------------------------------------
	private int donation_id;
	private String member_id;
	private String reward_id;
	private int r_price;//해당 리워드 금액
	private int add_donation;//추가 후원금액
	
	private String address_id;//리워드 배송받을 경우 (펀딩프로젝트)
	private String donatedate;
	
	//-------------------------------------------------------
	
	private int totalDonation;//리워드금액 + 추가후원금액 = 총 후원금액 (sql에는 없음)
	
	
	//[RewardBean의 필드] -------------------------------------------------------------------
	private String r_name;
	private String r_content;
	
	
	
	/* **************************************************************************************
	 * 
	 * 생성자
	 * 
	 * **************************************************************************************/
	
	//기본생성자
	public ProjectDonationRewardBean() {
		super();
	}
	
	//후원내역에서 사용
	public ProjectDonationRewardBean(int project_id, String title, String member_id, String reward_id, int r_price,
			int add_donation, String donatedate, int totalDonation, String r_name) {
		super();
		this.project_id = project_id;
		this.title = title;
		this.member_id = member_id;
		this.reward_id = reward_id;
		this.r_price = r_price;
		this.add_donation = add_donation;
		this.donatedate = donatedate;
		this.totalDonation = totalDonation;
		this.r_name = r_name;
	}
	
	//내 후원내역 조회시
	public ProjectDonationRewardBean(int donation_id, int project_id, String title, String status, String reward_id, String r_name, int r_price,
			int add_donation, int totalDonation, String donatedate) {
		super();
		this.donation_id = donation_id;
		this.project_id = project_id;
		this.title = title;
		this.status = status;
		this.r_name = r_name;
		this.reward_id = reward_id;
		this.r_price = r_price;
		this.add_donation = add_donation;
		this.donatedate = donatedate;
		this.totalDonation = totalDonation;
	}
	
	//회원 후원취소 시 메일에 정보 출력 
	public ProjectDonationRewardBean(int donation_id, int project_id, String member_id, String address_id, String title, String status, String kind, String reward_id, String r_name, String r_content,
			int r_price, int add_donation, int totalDonation, String donatedate) {
		super();
		this.donation_id = donation_id;
		this.project_id = project_id;
		this.member_id = member_id;
		this.address_id = address_id;
		this.title = title;
		this.status = status;
		this.kind = kind;
		this.reward_id = reward_id;
		this.r_name = r_name;
		this.r_content = r_content;
		this.r_price = r_price;
		this.add_donation = add_donation;
		this.totalDonation = totalDonation;
		this.donatedate = donatedate;
	}
	
	public ProjectDonationRewardBean(String title, String r_name, int r_price,
			int add_donation, String donatedate) {
		super();
		this.title = title;
		this.r_name = r_name;
		this.r_price = r_price;
		this.add_donation = add_donation;
		this.donatedate = donatedate;		
	}
	
	
	
	/* **************************************************************************************
	 * 
	 * 기본 Getter & Setter
	 * 
	 * **************************************************************************************/
	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public int getGoal_amount() {
		return goal_amount;
	}

	public void setGoal_amount(int goal_amount) {
		this.goal_amount = goal_amount;
	}

	public int getCurr_amount() {
		return curr_amount;
	}

	public void setCurr_amount(int curr_amount) {
		this.curr_amount = curr_amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}
	
	public String getRegdate() {
		return regdate;
	}
	
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}
	
	public int getDeadline() {
		return deadline;
	}

	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}
	

	//기본 getter & setter
	public String getGoal_amount_df() {
		return goal_amount_df;
	}

	public void setGoal_amount_df(String goal_amount_df) {
		this.goal_amount_df = goal_amount_df;
	}

	public String getCurr_amount_df() {
		return curr_amount_df;
	}

	public void setCurr_amount_df(String curr_amount_df) {
		this.curr_amount_df = curr_amount_df;
	}

	
	public int getDonation_id() {
		return donation_id;
	}

	public void setDonation_id(int donation_id) {
		this.donation_id = donation_id;
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
	
	public String getR_name() {
		return r_name;
	}

	public void setR_name(String r_name) {
		this.r_name = r_name;
	}

	public String getR_content() {
		return r_content;
	}

	public void setR_content(String r_content) {
		this.r_content = r_content;
	}

	
	
	/* **************************************************************************************
	 * 
	 * 추가한 메서드
	 * 
	 * **************************************************************************************/
	
	//★★모금액 달성률 계산을 위한 get, set 메서드 생성
	//현재모금액과 목표모금액으로 계산되어 소수첫째자리까지 표시된 달성률을 get
	public double getProgressFormat() {
		double d = (double) this.curr_amount / this.goal_amount;
		d = Math.floor((d*10)/10.0);//둘째자리에서 반올림
		
		return d;
	}
	//현재모금액과 목표모금액을 매개값으로 달성률을 set
	public void setProgressFormatWithCurrGoal(int curr_amount, int goal_amount) {
		double d = (double) curr_amount / goal_amount;
		this.progress = Math.floor((d*10)/10.0);//둘째자리에서 반올림
	}

	
	//남은일수 계산 : 오늘날짜-종료일
	public void setDeadline_exc(String enddate) {
		LocalDate today = LocalDate.now();
		LocalDate enddate_date = LocalDate.parse(enddate.replace(".", "-"));
		
		long deadline = ChronoUnit.DAYS.between(today, enddate_date);//두 날짜 사이 일수차이를 구함
		
		this.deadline = (int) deadline;
	}
	//남은일수 계산 : 오늘날짜-시작일 (공개예정 프로젝트)
	public void setDeadline_start_exc(String startdate) {
		LocalDate today = LocalDate.now();
		LocalDate startdate_date = LocalDate.parse(startdate.replace(".", "-"));
		
		long deadline = ChronoUnit.DAYS.between(today, startdate_date);//두 날짜 사이 일수차이를 구함
		
		this.deadline = (int) deadline;
	}
	
	//set메서드에서 포맷으로 천단위구분자 넣기
	public void setGoal_amount_df_exc(int goal_amount) {
		DecimalFormat df = new DecimalFormat("###,###");
		this.goal_amount_df = df.format(goal_amount);
	}

	//set메서드에서 포맷으로 천단위구분자 넣기
	public void setCurr_amount_df_exc(int curr_amount) {
		DecimalFormat df = new DecimalFormat("###,###");
		this.curr_amount_df = df.format(curr_amount);
	}
	
	//★★★ 리워드 금액 + 추가후원금액으로 세팅된 걸로 가지고오기
	public int getTotalDonation_Cal() {
		return r_price + add_donation;
	}
	
	//★★★ 리워드 금액 + 추가후원금액으로 세팅
	public void setTotalDonation_Cal(int r_price, int add_donation) {
		this.totalDonation = r_price + add_donation;
	}
	
}
