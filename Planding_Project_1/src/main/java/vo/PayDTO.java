package vo;

//결제정보가 담기는 DTO 클래스
public class PayDTO {

	private String orderId;/*1. 결제번호(주문 구분. 6~64자 사이 문자열)*/
	
	private String mId;/* 2. 상점 아이디, 토스페이먼츠에서 발급 (최대길이 14)*/
	private String paymentKey;/* 3. 결제의 키값(결제취소 시 필수값) : 결제 식별하는 역할. 중복되지 않는 고유한 값 (최대길이 200)*/
	
	private int order_num;/* 4. 주문번호 */
	
	private String member_id;/* 5. 사용자 Id */ 
	private String email;/* 6. 사용자 이메일 */
	
	private String orderName;/* 7. 주문명 (최대 100) (예: 생수 외 1건) (결제취소 시)*/
	
	private String pay_method;/* 8. [결제수단] 카드(토스로 실행위해 이것을 선택)(파라미터값은 method이름으로 넘어옴), 가상계좌, 간편결제, 은행 등 */
	private String easyPay;/* 9. 토스간편결제 이용 시, 결제 후 [결제수단] easyPay : ("provider": "토스페이","amount":100, "discount":500)*/
	private String pay_bank;/*10. 결제은행 */
	
	private String pay_status;/*11. 결제상황("DONE"성공,"CANCELED"실패) */
	private String pay_date;/*12. 결제일 (결제 승인한 날짜시간) */
	private int totalAmount;/* 13. 총 결제금액 */
	
	private String cancelReason;/*14. [결제취소] 시 취소사유(필수) */
	private String cancel_date;/*15. 결제 취소일 (취소된 날짜시간) */
	private int cancelAmount;/* 16. 총 취소 금액 */
	
	
	//기본생성자
	public PayDTO() {
		super();
	}

	//모든 필드를 사용하는 생성자
	public PayDTO(String orderId, String mId, String paymentKey, int order_num, String member_id, String email,
			String orderName, String pay_method, String easyPay, String pay_bank, String pay_status, String pay_date,
			int totalAmount, String cancelReason, String cancel_date, int cancelAmount) {
		super();
		this.orderId = orderId;
		this.mId = mId;
		this.paymentKey = paymentKey;
		this.order_num = order_num;
		this.member_id = member_id;
		this.email = email;
		this.orderName = orderName;
		this.pay_method = pay_method;
		this.easyPay = easyPay;
		this.pay_bank = pay_bank;
		this.pay_status = pay_status;
		this.pay_date = pay_date;
		this.totalAmount = totalAmount;
		this.cancelReason = cancelReason;
		this.cancel_date = cancel_date;
		this.cancelAmount = cancelAmount;
	}
	
	//결제 시 사용
	public PayDTO(String paymentKey, int order_num, String member_id, String email, String orderName, String pay_method,
			String easyPay, String pay_bank, String pay_status, String pay_date, int totalAmount) {
		super();
		this.paymentKey = paymentKey;
		this.order_num = order_num;
		this.member_id = member_id;
		this.email = email;
		this.orderName = orderName;
		this.pay_method = pay_method;
		this.easyPay = easyPay;
		this.pay_bank = pay_bank;
		this.pay_status = pay_status;
		this.pay_date = pay_date;
		this.totalAmount = totalAmount;
	}
	
	//주문 후, 주문번호까지 조회하기 위함
	public PayDTO(String orderId, String mId, String paymentKey, int order_num, String member_id, String email,
			String orderName, String pay_method, String easyPay, String pay_bank, String pay_status, String pay_date,
			int totalAmount) {
		super();
		this.orderId = orderId;
		this.mId = mId;
		this.paymentKey = paymentKey;
		this.order_num = order_num;
		this.member_id = member_id;
		this.email = email;
		this.orderName = orderName;
		this.pay_method = pay_method;
		this.easyPay = easyPay;
		this.pay_bank = pay_bank;
		this.pay_status = pay_status;
		this.pay_date = pay_date;
		this.totalAmount = totalAmount;
	}
	
	

	@Override
	public String toString() {
		return "PayDTO [orderId=" + orderId + ", mId=" + mId + ", paymentKey=" + paymentKey + ", order_num=" + order_num
				+ ", member_id=" + member_id + ", email=" + email + ", orderName=" + orderName + ", pay_method="
				+ pay_method + ", easyPay=" + easyPay + ", pay_bank=" + pay_bank + ", pay_status=" + pay_status
				+ ", pay_date=" + pay_date + ", totalAmount=" + totalAmount + ", cancelReason=" + cancelReason
				+ ", cancel_date=" + cancel_date + ", cancelAmount=" + cancelAmount + "]";
	}
	
	
	
	
	//getter setter
	public String getOrderId() {
		return orderId;
	}


	

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getmId() {
		return mId;
	}


	public void setmId(String mId) {
		this.mId = mId;
	}


	public String getPaymentKey() {
		return paymentKey;
	}


	public void setPaymentKey(String paymentKey) {
		this.paymentKey = paymentKey;
	}


	public int getOrder_num() {
		return order_num;
	}


	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}


	public String getMember_id() {
		return member_id;
	}


	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getOrderName() {
		return orderName;
	}


	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}


	public String getPay_method() {
		return pay_method;
	}


	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}


	public String getEasyPay() {
		return easyPay;
	}


	public void setEasyPay(String easyPay) {
		this.easyPay = easyPay;
	}


	public String getPay_bank() {
		return pay_bank;
	}


	public void setPay_bank(String pay_bank) {
		this.pay_bank = pay_bank;
	}


	public String getPay_status() {
		return pay_status;
	}


	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}


	public String getPay_date() {
		return pay_date;
	}


	public void setPay_date(String pay_date) {
		this.pay_date = pay_date;
	}


	public int getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}


	public String getCancelReason() {
		return cancelReason;
	}


	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}


	public String getCancel_date() {
		return cancel_date;
	}


	public void setCancel_date(String cancel_date) {
		this.cancel_date = cancel_date;
	}


	public int getCancelAmount() {
		return cancelAmount;
	}


	public void setCancelAmount(int cancelAmount) {
		this.cancelAmount = cancelAmount;
	}

	
}
