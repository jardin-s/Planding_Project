package vo;

public class AdminIncomeBean {

	private int project_id;
	private int fee_income;
	private String incomedate;
	
	
	public AdminIncomeBean() {}
	
	public AdminIncomeBean(int project_id, int fee_income, String incomedate) {
		super();
		this.project_id = project_id;
		this.fee_income = fee_income;
		this.incomedate = incomedate;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
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
