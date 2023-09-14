package vo;

public class NoticeBean {//공지사항 DTO
	
	private int notice_id;//공지사항 ID
	
	private String member_id;//작성자 ID
	private String n_title;//공지사항 제목
	private String n_content;//공지사항 내용
	private String n_image;//공지사항 이미지
	
	private String importance;//중요도 여부 Y N
	
	private int viewcount;//조회수
	private String writetime;//작성일시
	
	public NoticeBean() {
		super();
	}

	//공지사항 글 작성 시 (SQL 자동 : notice_id 자동1씩 증가, writetime 자동세팅)
	public NoticeBean(String member_id, String n_title, String n_content, String n_image,
			String importance) {
		super();
		this.member_id = member_id;
		this.n_title = n_title;
		this.n_content = n_content;
		this.n_image = n_image;
		this.importance = importance;
	}
	
	//공지사항 글 가져올 때
	public NoticeBean(int notice_id, String member_id, String n_title, String n_content, String n_image,
			String importance, int viewcount, String writetime) {
		super();
		this.notice_id = notice_id;
		this.member_id = member_id;
		this.n_title = n_title;
		this.n_content = n_content;
		this.n_image = n_image;
		this.importance = importance;
		this.viewcount = viewcount;
		this.writetime = writetime;		
	}

	
	//getter & setter
	
	public int getNotice_id() {
		return notice_id;
	}

	public void setNotice_id(int notice_id) {
		this.notice_id = notice_id;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getN_title() {
		return n_title;
	}

	public void setN_title(String n_title) {
		this.n_title = n_title;
	}

	public String getN_content() {
		return n_content;
	}

	public void setN_content(String n_content) {
		this.n_content = n_content;
	}

	public String getN_image() {
		return n_image;
	}

	public void setN_image(String n_image) {
		this.n_image = n_image;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public int getViewcount() {
		return viewcount;
	}
	
	public void setViewcount(int viewcount) {
		this.viewcount = viewcount;
	}
	
	public String getWritetime() {
		return writetime;
	}

	public void setWritetime(String writetime) {
		this.writetime = writetime;
	}
	
	
	
}
