package vo;

public class PlannerBean {
	
	private int project_id;
	private String member_id;
	private String planner_name;
	private String introduce;
	private String bank;
	private String account;

	
	
	
	public PlannerBean() {}




	public PlannerBean(int project_id, String member_id, String planner_name, String introduce, String bank,
			String account) {
		super();
		this.project_id = project_id;
		this.member_id = member_id;
		this.planner_name = planner_name;
		this.introduce = introduce;
		this.bank = bank;
		this.account = account;
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




	public String getAccount() {
		return account;
	}




	public void setAccount(String account) {
		this.account = account;
	}
	
	
	
		

}
