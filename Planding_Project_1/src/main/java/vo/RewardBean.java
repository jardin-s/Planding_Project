package vo;

public class RewardBean {

	private int reward_id;
	private String name;
	private String desc;
	private int price;
	
	public RewardBean () {}

	public RewardBean(int reward_id, String name, String desc, int price) {
		super();
		this.reward_id = reward_id;
		this.name = name;
		this.desc = desc;
		this.price = price;
	}

	public int getReward_id() {
		return reward_id;
	}

	public void setReward_id(int reward_id) {
		this.reward_id = reward_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
}
