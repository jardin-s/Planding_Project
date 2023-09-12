package vo;

public class AddressBean {
	
	private String address_id; //멤버아이디 + 오늘날짜(분까지)
	
	private String member_id; //회원 id
	private String receiver_name; //수령인 이름
	private String receiver_phone; //연락처
	
	private int postcode; //우편번호
	private String address1; //주소1 (자동세팅)
	private String address2; //주소2 (상세주소 직접입력)
	
	private String basic_status; //기본주소 여부 (가입시 입력주소 Y, 그외 모두 N)
		
	public AddressBean () {}

	//주소 등록 시
	public AddressBean(String member_id, String receiver_name, String receiver_phone, int postcode, String address1,
			String address2, String basic_status) {
		super();
		this.member_id = member_id;
		this.receiver_name = receiver_name;
		this.receiver_phone = receiver_phone;
		this.postcode = postcode;
		this.address1 = address1;
		this.address2 = address2;
		this.basic_status = basic_status;
	}
	//회원가입 시 사용
	public AddressBean(String address_id, String member_id, String receiver_name, String receiver_phone, int postcode, String address1,
			String address2, String basic_status) {
		super();
		this.address_id = address_id;
		this.member_id = member_id;
		this.receiver_name = receiver_name;
		this.receiver_phone = receiver_phone;
		this.postcode = postcode;
		this.address1 = address1;
		this.address2 = address2;
		this.basic_status = basic_status;
	}
	//회원정보 수정 시 사용
	public AddressBean(String address_id, String member_id, String receiver_name, String receiver_phone, int postcode, String address1,
			String address2) {
		super();
		this.address_id = address_id;
		this.member_id = member_id;
		this.receiver_name = receiver_name;
		this.receiver_phone = receiver_phone;
		this.postcode = postcode;
		this.address1 = address1;
		this.address2 = address2;
		this.basic_status = basic_status;
	}
	
	//관리자모드 - 회원상세정보 보기 시, 주소 가져오기
	public AddressBean(String address_id, String member_id, int postcode, String address1,	String address2) {
		super();
		this.address_id = address_id;
		this.member_id = member_id;
		this.postcode = postcode;
		this.address1 = address1;
		this.address2 = address2;
	}

	public String getAddress_id() {
		return address_id;
	}

	public void setAddress_id(String address_id) {
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

	public String getReceiver_phone() {
		return receiver_phone;
	}

	public void setReceiver_phone(String receiver_phone) {
		this.receiver_phone = receiver_phone;
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

	public String getBasic_status() {
		return basic_status;
	}

	public void setBasic_status(String basic_status) {
		this.basic_status = basic_status;
	}
	
	
	
}