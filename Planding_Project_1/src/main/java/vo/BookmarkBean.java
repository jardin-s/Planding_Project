package vo;

public class BookmarkBean {
	
	private String member_id;
	private int project_id;
	private String likedate;
	
	public BookmarkBean () {}

	public BookmarkBean(String member_id, int project_id, String likedate) {
		super();
		this.member_id = member_id;
		this.project_id = project_id;
		this.likedate = likedate;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public String getLikedate() {
		return likedate;
	}

	public void setLikedate(String likedate) {
		this.likedate = likedate;
	}
	
	

}
