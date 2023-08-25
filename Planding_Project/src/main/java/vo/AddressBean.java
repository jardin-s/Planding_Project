package vo;

public class AddressBean {
	
	private String id;
	private int postcode;
	private String address1;
	private String address2;
	
	//매개변수가 없는 생성자
	public AddressBean( ) {}

	//매개변수가 있는 생성자
	public AddressBean(String id, int postcode, String address1, String address2) {
		super();
		this.id = id;
		this.postcode = postcode;
		this.address1 = address1;
		this.address2 = address2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPostcode() {
		return postcode;
	}

	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	
}
