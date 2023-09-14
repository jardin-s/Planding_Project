package vo;

public class RewardBean {

	private int reward_id;
	private String r_name;
	private String r_content;
	private int r_price;
	
	public RewardBean () {}

	public RewardBean(int reward_id, String name, String desc, int price) {
		super();
		this.reward_id = reward_id;
		this.r_name = name;
		this.r_content = desc;
		this.r_price = price;
	}

	public int getReward_id() {
		return reward_id;
	}

	public void setReward_id(int reward_id) {
		this.reward_id = reward_id;
	}

	public String getName() {
		return r_name;
	}

	public void setName(String name) {
		this.r_name = name;
	}

	public String getDesc() {
		return r_content;
	}

	public void setDesc(String desc) {
		this.r_content = desc;
	}

	public int getPrice() {
		return r_price;
	}

	public void setPrice(int price) {
		this.r_price = price;
	}
	
	
	
}
