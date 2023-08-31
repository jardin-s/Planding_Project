package vo;

import java.util.Date;

public class ProjectBean {
	
	private int project_id;
	
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
	
	private String category;
	private String status;
	private int likes;
	
	
	public ProjectBean() {}
	
	public ProjectBean(int project_id, String kind, String title, String summary, String thumbnail, String content,
			String image, String startdate, String enddate, int goal_amount, int curr_amount, String category, String status, int likes) {
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
		
		this.category = category;
		this.status = status;
		this.likes = likes;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

}
