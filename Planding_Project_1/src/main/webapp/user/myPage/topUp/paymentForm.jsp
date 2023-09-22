<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <title>금액 충전하기</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Favicon -->
    <link href="../../../resources/img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Jost:wght@500;600;700&family=Open+Sans:wght@400;500&display=swap" rel="stylesheet">  

    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="../../../resources/lib/animate/animate.min.css" rel="stylesheet">
    <link href="../../../resources/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="../../../resources/lib/lightbox/css/lightbox.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="../../../resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="../../../resources/css/style.css" rel="stylesheet">
    
    <!-- 결제예약 스타일 -->
    
</head>

<!-- 순서-1. 토스페이먼츠 결제창 SDK 추가 -->
<script src="https://js.tosspayments.com/v1/payment"></script>

<!-- 순서-2. '클라이언트'로 초기화하여 결제창(TossPayments객체) 생성
키발급방법 : 토스 회원가입하여 내 개발정보
'클라이언트키' : 브라우저에서 토스페이먼츠 SDK를 연동할 때 사용 -->

<script>

//------------------------------------------------------------------

function payment(){
	
	//결제방법 유효성 체크
	if(document.payForm.payPlan.value == "선택"){
		alert("결제 종류를 선택하세요.");
		return false;
	}
	
	//순서3.--------------------------------------------
	
	//메서드는 무조건 카드 (그래야 토스페이먼츠로 진행됨)
	const method = "카드";
	
	const paymentData = {
		amount : 1000,//결제금액	
		orderId : (Math.random() + "").substring(2),//주문번호 ID : 6~64 문자열 무작위생성 (앞의 두 자리 제외)
		orderName : '${sessionScope.topUpMoney}',//주문명 (예) 콜라 1잔 (1000원 충전)
		customerName : '${sessionScope.u_name}',//주문자 이름
		
		successUrl : window.location.origin + "/Planding_Project/paymentAction.pay",//결제 성공시 이동할 페이지
		
		failUrl : window.location.origin + "/Planding_Project/failOrderPayment.pay"//결제 실패
	}//★★주의 : 세미콜론; 제거
	
	
	//순서4.--------------------------------------------
	
	//결제수단과 주문정보가담긴 paymentData객체를 매개값으로 requsetPayment메서드 호출하여 결제 진행
	tossPayments.requestPayment(method, paymentData);
	
	
	//메서드 실행에 실패시 처리
	/*
	tossPayments.catch(function(error){
		if(error.code == 'USER_CANCEL'){
			//결제 고객이 결제창을 닫았을 때 에러 처리
		}else if(error.code == 'INVALID_ORDER_ID'){
			//유효하지 않은 orderId값 처리	
		}
		}else if(error.code == 'INVALID_ORDER_NAME'){
			//유효하지 않은 orderId값 처리	
		}
		}else if(error.code == 'INVALID_CUSTOMER_NAME'){
			//유효하지 않은 orderId값 처리	
		}		
	});
	*/
	
}//payment() 함수 끝

//순서-5. 현재 결제창에서 결제 요청이 성공하면 결제성공 페이지로 이동한다.(successUrl)--------------
/*
 https://{ORIGIN}/success?paymentKey={PAYMENT_KEY}&orderId={ORDER_ID}&amount={AMOUNT}
결제 성공 페이지의 URL에는 paymentKey, orderId, amount 세 가지 쿼리 파라미터가 들어있다. 
- paymentKey : 결제의 키 값입니다. 최대 길이는 200자입니다. 결제를 식별하는 역할로, 중복되지 않는 고유한 값입니다. 
				결제 데이터 관리를 위해 반드시 저장해야 합니다. 결제 상태가 변해도 값이 유지됩니다. 
				결제 승인, 결제 조회, 결제 취소 API에 사용합니다.
- orderId :    주문 ID이다. 주문한 결제를 식별하는 역할로, 결제를 요청할 때 가맹점에서 만들어서 requestPayment()에 담아 보낸 값이다. 
                 결제 데이터 관리를 위해 반드시 저장해야 한다. 중복되지 않는 고유한 값을 발급해야 합니다. 결제 상태가 변해도 값이 유지된다.
- amount: 실제로 결제된 금액이다.

 결제 요청이 실패하면 결제창을 열 때 설정한 결제 실패 페이지(failUrl)로 이동한다.  
 
 */

</script>

<!-- 결제일을 오늘 날짜로 자동입력 -->
<script type="text/javascript">
	window.onload = function(){
		//[방법-1] slice(0, 9+1);
		//var today = new Date();
		//today = today.toISOString().slice(0, 9+1);//2023-09-22(0~9인덱스까지. 연월일)
		
		//[방법-2] substring
		var today = new Date().toISOString().substring(0, 9+1);//2023-09-22(0~9인덱스까지. 연월일)
		
		var bir = document.getElementById("now_date");
		bir.value = today;
	}
</script>

<body>
		
	<!-- Main Section -->
	<c:if test="${param.member_id ne null and requestScope.check_id eq null}">
		<c:set var="setId" value="${param.member_id }"/>
	</c:if>
	<c:if test="${param.member_id eq null and requestScope.check_id ne null}">
		<c:set var="setId" value="${requestScope.check_id }"/>
	</c:if>
	
	<div class="container-fluid py-2 px-3">
        <div class="row text-center">
            <div class="col-lg-6 py-5 border border-primary">
                <p class="fw-bold fs-4">플랜딩 계좌 금액 충전</p><br>
                <form action="userTopupMoneyPay.usr" method="post" name="f">
					<div class="mb-3 d-flex justify-content-center g-0">
						
						<!-- 충전금액 선택 -->
						<div class="col-5 text-start">
							<div class="form-check">
					      		<input class="form-check-input" type="radio" name="topUpMoney" value="5000" id="topUpMoney5000" onclick="hideTextInput();">
								<label class="form-check-label" for="topUpMoney5000">5000원</label>
						    </div>
                	 		<div class="form-check">
					      		<input class="form-check-input" type="radio" name="topUpMoney" value="10000" id="topUpMoney10000" onclick="hideTextInput();">
								<label class="form-check-label" for="topUpMoney10000">10,000원</label>
						    </div>
                	 		<div class="form-check">
					      		<input class="form-check-input" type="radio" name="topUpMoney" value="20000" id="topUpMoney20000" onclick="hideTextInput();">
								<label class="form-check-label" for="topUpMoney20000">20,000원</label>
						    </div>
                	 		<div class="form-check">
					      		<input class="form-check-input" type="radio" name="topUpMoney" value="30000" id="topUpMoney30000" onclick="hideTextInput();">
								<label class="form-check-label" for="topUpMoney30000">30,000원</label>
						    </div>
                	 		<div class="form-check">
					      		<input class="form-check-input" type="radio" name="topUpMoney" value="40000" id="topUpMoney40000" onclick="hideTextInput();">
								<label class="form-check-label" for="topUpMoney40000">40,000원</label>
						    </div>
                	 		<div class="form-check">
					      		<input class="form-check-input" type="radio" name="topUpMoney" value="50000" id="topUpMoney50000" onclick="hideTextInput();">
								<label class="form-check-label" for="topUpMoney50000">50,000원</label>
						    </div>
                	 		<div class="form-check">
					      		<input class="form-check-input" type="radio" name="topUpMoney" id="topUpMoneyCustom" onclick="showTextInput();">
								<label class="form-check-label" for="topUpMoneyCustom">직접입력</label>
								<input class="form-control py-0 d-none" type="text" name="topUpMoneyText" id="topUpMoneyText">
						    </div>
						</div>				    	
					</div>
					
					<!-- 사용자ID를 파라미터값으로 넘겨줌 -->
					<input type="hidden" name="member_id" value="${param.member_id}">
	                
	                <!-- 충전/닫기 버튼 -->
					<c:if test="${requestScope.finalTotalMoney ne null }">
	                	<p>
	                	충전완료되었습니다.<br>
	                	현재 잔액 : ${requestScope.finalTotalMoney }원
	                	</p>
	                	<input type="hidden" name="final" value="${finalTotalMoney }" id="finalTotalMoney">
	                	<button class="btn btn-sm btn-primary" type="button" onclick="confirmResult();">
	                		<span style="font-size:1rem">확인</span>
	                	</button>
	                </c:if>
	                <c:if test="${requestScope.finalTotalMoney eq null }">
						<button class="btn btn-sm btn-primary" type="submit" onclick="checkTopup(); return false;">
							<span style="font-size:1rem">충전하기</span>
						</button>
						<button class="btn btn-sm btn-primary" type="button" onclick="window.close();">
							<span style="font-size:1rem">닫기</span>
						</button>
					</c:if>
                </form>
                
                
            </div>
        </div>
    </div>	
	
    
    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="../../../resources/lib/wow/wow.min.js"></script>
    <script src="../../../resources/lib/easing/easing.min.js"></script>
    <script src="../../../resources/lib/waypoints/waypoints.min.js"></script>
    <script src="../../../resources/lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="../../../resources/lib/counterup/counterup.min.js"></script>
    <script src="../../../resources/lib/parallax/parallax.min.js"></script>
    <script src="../../../resources/lib/isotope/isotope.pkgd.min.js"></script>
    <script src="../../../resources/lib/lightbox/js/lightbox.min.js"></script>

    <!-- Template Javascript -->
    <script src="../../../resources/js/main.js"></script>
</body>
</html>