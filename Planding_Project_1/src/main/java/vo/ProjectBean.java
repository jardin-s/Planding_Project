package vo;

import java.util.Date;

public class ProjectBean {
	
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
	
	private double progress;//달성률 (DB에 없음. 조회시, 현재모금액/목표모금액 계산하여 세팅)
	
	
	
	public ProjectBean() {}
	
	//프로젝트 모든 정보
	public ProjectBean(int project_id, String kind, String title, String summary, String thumbnail, String content,
			String image, String startdate, String enddate, int goal_amount, int curr_amount, String status, int likes,
			String regdate) {
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
	}

	//프로젝트 등록 시
	public ProjectBean(String kind, String title, String summary, String thumbnail, String content,
			String image, String startdate, String enddate, int goal_amount, int curr_amount) {
		super();
		//this.project_id = project_id; //SQL 자동1씩 증가
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
		//this.status = status; //등록시 항상 처음은 unauthorized(미승인)
		//this.likes = likes; //등록시 항상 처음은 0으로 시작
		//this.regdate = regdate; //SQL에서 현재시간으로 자동세팅
	}
	
	//관리자모드 - 프로젝트 목록 조회 시
	public ProjectBean(int project_id, String kind, String title, String status, String regdate) {
		super();
		this.project_id = project_id;
		this.kind = kind;
		this.title = title;
		this.status = status; //등록시 항상 처음은 unauthorized(미승인)
		this.regdate = regdate; //SQL에서 현재시간으로 자동세팅
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

}
