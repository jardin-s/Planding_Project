package vo;

public class PlannerBean {//프로젝트 기획자
	
	private int project_id;//프로젝트 ID
	private String member_id;//기획자 ID
	private String planner_name;//기획자 이름
	private String introduce;//기획자 소개
	private String bank;//계좌은행
	private String account_num;//계좌번호

	
	//기본 생성자
	public PlannerBean() {}

	//모든 필드를 사용하는 생성자
	public PlannerBean(int project_id, String member_id, String planner_name, String introduce, String bank,
			String account_num) {
		super();
		this.project_id = project_id;
		this.member_id = member_id;
		this.planner_name = planner_name;
		this.introduce = introduce;
		this.bank = bank;
		this.account_num = account_num;
	}
	
	//폼에서 입력받은 값으로만 채우는 생성자 (세션 저장용)
	public PlannerBean(String member_id, String planner_name, String introduce, String bank, String account_num) {
		super();
		this.member_id = member_id;
		this.planner_name = planner_name;
		this.introduce = introduce;
		this.bank = bank;
		this.account_num = account_num;
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

	@Override
	public String toString() {
		return "PlannerBean [project_id=" + project_id + ", member_id=" + member_id + ", planner_name=" + planner_name
				+ ", introduce=" + introduce + ", bank=" + bank + ", account_num=" + account_num + "]";
	}
	
	

}
	