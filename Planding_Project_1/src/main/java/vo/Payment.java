package vo;

public class Payment {
	
	private Long paymentId;//결제 ID
	private PayType payType;//결제 타입
	private Long amount;//결젝 금액
	private String orderName;//주문 이름
	private String orderId;//주문 ID
	
	private boolean paySuccessYN;//결제 성공 여부
	private MemberBean customer;//고객
	private String paymentKey;//결제 키
	private String failReason; //결제실패 원인
	
	private boolean cancelYN;//결제취소 여부
	private String cancelReason; //결제취소 원인
	
	//응답 DTO로 변환하는 메서드
	public PaymentResDTO toPaymentResDTO() {
		PaymentResDTO paymentResDTO = new PaymentResDTO();
		
		paymentResDTO.setPayType(payType.getDescription());
		paymentResDTO.setAmount(amount);
		paymentResDTO.setOrderName(orderName);
		paymentResDTO.setOrderId(orderId);
		paymentResDTO.setCustomerEmail(customer.getEmail());
		paymentResDTO.setCustomerName(customer.getName());
		paymentResDTO.setCreatedAt(paymentResDTO.getCreatedAt());
		paymentResDTO.setCancelYN(cancelYN);
		paymentResDTO.setFailReason(failReason);
				
		return paymentResDTO;
	}
	

}
