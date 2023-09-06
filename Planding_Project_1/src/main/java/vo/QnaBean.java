package vo;

import util.SHA256;

public class QnaBean {
	
	private int qna_id;
	
	private String member_id;
		
	private String q_title;
	private String q_content;
	private String q_image;
	private String q_private;

	private String q_time; //SQL에서 자동셋팅
	
	private String a_content;
	private String a_time; //SQL에서 자동셋팅
		
	
	public QnaBean() {}


	public QnaBean(int qna_id, String member_id, String q_title, String q_content, String q_image, String q_private,
			String a_content) {
		super();
		this.qna_id = qna_id;
		this.member_id = member_id;
		this.q_title = q_title;
		this.q_content = q_content;
		this.q_image = q_image;
		this.q_private = q_private;
		this.a_content = a_content;
	}


	public int getQna_id() {
		return qna_id;
	}


	public void setQna_id(int qna_id) {
		this.qna_id = qna_id;
	}


	public String getMember_id() {
		return member_id;
	}


	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}


	public String getQ_title() {
		return q_title;
	}


	public void setQ_title(String q_title) {
		this.q_title = q_title;
	}


	public String getQ_content() {
		return q_content;
	}


	public void setQ_content(String q_content) {
		this.q_content = q_content;
	}


	public String getQ_image() {
		return q_image;
	}


	public void setQ_image(String q_image) {
		this.q_image = q_image;
	}


	public String getQ_private() {
		return q_private;
	}


	public void setQ_private(String q_private) {
		this.q_private = q_private;
	}


	public String getQ_time() {
		return q_time;
	}


	public void setQ_time(String q_time) {
		this.q_time = q_time;
	}


	public String getA_content() {
		return a_content;
	}


	public void setA_content(String a_content) {
		this.a_content = a_content;
	}


	public String getA_time() {
		return a_time;
	}


	public void setA_time(String a_time) {
		this.a_time = a_time;
	}
	
	
}

	