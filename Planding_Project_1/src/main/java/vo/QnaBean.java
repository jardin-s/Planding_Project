package vo;

public class QnaBean {
	
	private int qna_id;
	
	private String q_writer;
		
	private String q_title;
	private String q_content;
	private String q_image;
	private String q_private;

	private String q_time; //SQL에서 자동셋팅
	
	private String a_writer;
	private String a_content;
	private String a_time; //SQL에서 자동셋팅
		
	
	public QnaBean() {}

	
	//문의글 등록 시
	public QnaBean(int qna_id, String q_writer, String q_title, String q_content, String q_image, String q_private,
			String a_writer) {
		super();
		this.qna_id = qna_id;
		this.q_writer = q_writer;
		this.q_title = q_title;
		this.q_content = q_content;
		this.q_image = q_image;
		this.q_private = q_private;
		this.a_writer = a_writer;
	}
	
	//문의글 조회 시
	public QnaBean(int qna_id, String q_writer, String q_title, String q_content, String q_image, String q_private,
			String q_time, String a_writer, String a_content, String a_time) {
		super();
		this.qna_id = qna_id;
		this.q_writer = q_writer;
		this.q_title = q_title;
		this.q_content = q_content;
		this.q_image = q_image;
		this.q_private = q_private;
		this.q_time = q_time;
		this.a_writer = a_writer;
		this.a_content = a_content;
		this.a_time = a_time;
	}
	
	//답글 등록 시 (해당 qna_id로 문의글 업데이트)
	public QnaBean(int qna_id, String a_writer, String a_content) {
		super();
		this.qna_id = qna_id;
		this.a_writer = a_writer;
		this.a_content = a_content;
		//답글시간은 sql에서 자동세팅
	}
	
	//관리자모드 - 회원의 문의글리스트
	public QnaBean(int qna_id, String q_writer, String q_title, String q_time, String a_writer) {
		super();
		this.qna_id = qna_id;
		this.q_writer = q_writer;
		this.q_title = q_title;
		this.q_time = q_time;
		this.a_writer = a_writer;
	}


	public int getQna_id() {
		return qna_id;
	}


	public void setQna_id(int qna_id) {
		this.qna_id = qna_id;
	}


	public String getQ_writer() {
		return q_writer;
	}


	public void setQ_writer(String q_writer) {
		this.q_writer = q_writer;
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

	public String getA_writer() {
		return a_writer;
	}


	public void setA_writer(String a_writer) {
		this.a_writer = a_writer;
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


	@Override
	public String toString() {
		return "QnaBean [qna_id=" + qna_id + ", q_writer=" + q_writer + ", q_title=" + q_title + ", q_content="
				+ q_content + ", q_image=" + q_image + ", q_private=" + q_private + ", q_time=" + q_time + ", a_writer="
				+ a_writer + ", a_content=" + a_content + ", a_time=" + a_time + "]";
	}	
	
}

	