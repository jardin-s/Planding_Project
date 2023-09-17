package vo;

public class RewardBean {

	private String reward_id;
	private String r_name;
	private String r_content;
	private int r_price;
	
	public RewardBean () {}

	//모든 필드를 사용한 생성자 (DB 등록용)
	public RewardBean(String reward_id, String r_name, String r_content, int r_price) {
		super();
		this.reward_id = reward_id;
		this.r_name = r_name;
		this.r_content = r_content;
		this.r_price = r_price;
	}
	
	//폼에서 입력받은 값을 사용한 생성자 (session 저장용)
	public RewardBean(String r_name, String r_content, int r_price) {
		super();
		this.r_name = r_name;
		this.r_content = r_content;
		this.r_price = r_price;
	}

	public String getReward_id() {
		return reward_id;
	}

	public void setReward_id(String reward_id) {
		this.reward_id = reward_id;
	}

	public String getR_name() {
		return r_name;
	}

	public void setR_name(String r_name) {
		this.r_name = r_name;
	}

	public String getR_content() {
		return r_content;
	}

	public void setR_content(String r_content) {
		this.r_content = r_content;
	}

	public int getR_price() {
		return r_price;
	}

	public void setR_price(int r_price) {
		this.r_price = r_price;
	}
	
	
}
