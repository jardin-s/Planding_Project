package vo;

public class PaymentResDTO {

	private String payType;//결제타입
    private Long amount;//결제금액
    private String orderName;//주문 이름 (프로젝트이름 + 리워드이름)
    private String orderId;//주문 ID (후원기록ID)
    private String customerEmail;//고객이메일
    private String customerName;//고객이름
    private String successUrl;//성공시 url
    private String failUrl;//실패시 url

    private String failReason;//실패 원인
    private boolean cancelYN;//결제취소 여부
    private String cancelReason;//결제취소 원인
    private String createdAt;//생성위치
    
    
    //기본생성자
    public PaymentResDTO() {}
    
    //모든 필드를 사용한 생성자
    public PaymentResDTO(String payType, Long amount, String orderName, String orderId, String customerEmail,
			String customerName, String successUrl, String failUrl, String failReason, boolean cancelYN,
			String cancelReason, String createdAt) {
		super();
		this.payType = payType;
		this.amount = amount;
		this.orderName = orderName;
		this.orderId = orderId;
		this.customerEmail = customerEmail;
		this.customerName = customerName;
		this.successUrl = successUrl;
		this.failUrl = failUrl;
		this.failReason = failReason;
		this.cancelYN = cancelYN;
		this.cancelReason = cancelReason;
		this.createdAt = createdAt;
	}
	//getter & setter
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getSuccessUrl() {
		return successUrl;
	}
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}
	public String getFailUrl() {
		return failUrl;
	}
	public void setFailUrl(String failUrl) {
		this.failUrl = failUrl;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public boolean isCancelYN() {
		return cancelYN;
	}
	public void setCancelYN(boolean cancelYN) {
		this.cancelYN = cancelYN;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
    
    
	
}
