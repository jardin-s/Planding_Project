package vo;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ProjectPlannerBean {

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
	
	
	
	//[PlannerBean의 필드] -------------------------------------------------------------------
	
	private String member_id;//기획자 ID
	private String planner_name;//기획자 이름
	private String introduce;//기획자 소개
	private String bank;//계좌은행
	private String account_num;//계좌번호
	
	
	
	/* **************************************************************************************
	 * 
	 * 생성자
	 * 
	 * **************************************************************************************/

	//기본생성자
	public ProjectPlannerBean() {
		super();
	}
	
	//모든 필드를 사용하는 생성자
	public ProjectPlannerBean(int project_id, String kind, String title, String summary, String thumbnail,
			String content, String image, String startdate, String enddate, int goal_amount, int curr_amount,
			String status, int likes, String regdate, double progress, int deadline, String goal_amount_df,
			String curr_amount_df, String member_id, String planner_name, String introduce, String bank,
			String account_num) {
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
		this.status = status;
		this.likes = likes;
		this.regdate = regdate;
		this.progress = progress;
		this.deadline = deadline;
		this.goal_amount_df = goal_amount_df;
		this.curr_amount_df = curr_amount_df;
		this.member_id = member_id;
		this.planner_name = planner_name;
		this.introduce = introduce;
		this.bank = bank;
		this.account_num = account_num;
	}
	
	//모든 프로젝트 DB필드 + 기획자는 이름만 ([사용자모드] 프로젝트 리스트 용)
	public ProjectPlannerBean(int project_id, String kind, String title, String summary, String thumbnail,
			String content, String image, String startdate, String enddate, int goal_amount, int curr_amount,
			String status, int likes, String regdate, String planner_name) {
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
		this.status = status;
		this.likes = likes;
		this.regdate = regdate;
		this.planner_name = planner_name;
	}
	
	/* **************************************************************************************
	 * 
	 * ProjectBean의 메서드
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
	

	public int getDeadline() {
		return deadline;
	}

	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}
	
	//남은일수 계산 : 오늘날짜-종료일
	public void setDeadline_exc(String enddate) {
		LocalDate today = LocalDate.now();
		LocalDate enddate_date = LocalDate.parse(enddate.replace(".", "-"));
		
		long deadline = ChronoUnit.DAYS.between(today, enddate_date);//두 날짜 사이 일수차이를 구함
		
		this.deadline = (int) deadline;
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
	
	
	/* **************************************************************************************
	 * 
	 * PlannerBean의 메서드
	 * 
	 * **************************************************************************************/
	
	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getPlanner_name() {
		return planner_name;
	}

	public void setPlanner_name(String planner_name) {
		this.planner_name = planner_name;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccount_num() {
		return account_num;
	}

	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}
	
	
}
