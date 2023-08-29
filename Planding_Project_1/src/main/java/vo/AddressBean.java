package vo;

public class AddressBean {
	
	private int address_id; //sql에서 자동 1씩 증가
	
	private String member_id; //회원 id
	private String receiver_name; //수령인 이름
	private String phone; //연락처
	
	private int postcode; //우편번호
	private String address1; //주소1 (자동세팅)
	private String address2; //주소2 (상세주소 직접입력)
		
	public AddressBean () {}

	public AddressBean(String member_id, String receiver_name, String phone, int postcode, String address1,
			String address2) {
		super();
		this.member_id = member_id;
		this.receiver_name = receiver_name;
		this.phone = phone;
		this.postcode = postcode;
		this.address1 = address1;
		this.address2 = address2;
	}

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getReceiver_name() {
		return receiver_name;
	}

	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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