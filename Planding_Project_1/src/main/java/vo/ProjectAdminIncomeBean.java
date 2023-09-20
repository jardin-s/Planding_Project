package vo;

//프로젝트-관리자수익 뷰 (성공한 프로젝트 송금용)
public class ProjectAdminIncomeBean {
	
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
	
	private String p_status;//처음은 항상 unauthorized(미승인)
	private int likes;//0부터 시작
	
	private String regdate;//등록일자 (SQL 자동 현재시간 세팅)
	
	//----------------------------------------------------- 아래는 DB에 없음
	
	private double progress;//달성률 (조회시, 현재모금액/목표모금액 계산하여 세팅)
	
	private int deadline;//남은일수 (조회시, 오늘날짜-마감일 계산하여 세팅)
	
	private String goal_amount_df; //목표모금액 포맷 (천단위 구분자 넣어서 세팅)
	private String curr_amount_df; //현재모금액 포맷 (천단위 구분자 넣어서 세팅)
	
	
	
	//[AdminIncomeBean의 필드] -------------------------------------------------------------------
	private int fee_income;
	private String incomedate;
	
	
	
	
	/* **************************************************************************************
	 * 
	 * 생성자
	 * 
	 * **************************************************************************************/
	
	//기본생성자
	public ProjectAdminIncomeBean() {
		super();
	}
	
	//모든 필드를 사용하는 생성자
	public ProjectAdminIncomeBean(int project_id, String kind, String title, String summary, String thumbnail,
			String content, String image, String startdate, String enddate, int goal_amount, int curr_amount,
			String p_status, int likes, String regdate, double progress, int deadline, String goal_amount_df,
			String curr_amount_df, int fee_income, String incomedate) {
		super();
		this.project_id = project_id;
		this.kind = kind;
		this.title = title;
		this.summary = summary;
		this.thumbnail = thumbnail;
		this.content = content;
		this.image = image;
		this.startdate = startdate;
		this.enddate = enddate;
		this.goal_amount = goal_amount;
		this.curr_amount = curr_amount;
		this.p_status = p_status;
		this.likes = likes;
		this.regdate = regdate;
		this.progress = progress;
		this.deadline = deadline;
		this.goal_amount_df = goal_amount_df;
		this.curr_amount_df = curr_amount_df;
		this.fee_income = fee_income;
		this.incomedate = incomedate;
	}
	
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
	public String getP_status() {
		return p_status;
	}
	public void setP_status(String p_status) {
		this.p_status = p_status;
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
	public int getFee_income() {
		return fee_income;
	}
	public void setFee_income(int fee_income) {
		this.fee_income = fee_income;
	}
	public String getIncomedate() {
		return incomedate;
	}
	public void setIncomedate(String incomedate) {
		this.incomedate = incomedate;
	}
	
}
