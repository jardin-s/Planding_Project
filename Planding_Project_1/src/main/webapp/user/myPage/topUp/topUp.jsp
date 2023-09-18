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
</head>

<script>

//직접입력 선택 시, text 인풋태그 보이기
function showTextInput(){
	var customRadio = document.getElementById("topUpMoneyCustom");
	var customText = document.getElementById("topUpMoneyText");
	
	if(customRadio.checked == true){
		if(customText.classList.contains('d-none')){
			customText.classList.remove('d-none');
		}
	}
}

//다른 옵션 선택 시, text 인풋태그 숨기기
function hideTextInput(){
	var customRadio = document.getElementById("topUpMoneyCustom");
	var customText = document.getElementById("topUpMoneyText");
	
	if(customRadio.checked == false){
		if(!customText.classList.contains('d-none')){
			customText.value = "";
			customText.classList.add('d-none');
		}
	}
}

//하나라도 선택이 되어있어야 submit
function checkTopup(){
	
	//topUpMoney 라디오버튼을 배열로 가지고 오기
	const form = document.f;
	const radioButtons = Array.from(form.querySelectorAll('input[type="radio"]'));
	
	//직접입력 라디오버튼과 입력텍스트창 가지오 오기
	const customRadio = document.getElementById("topUpMoneyCustom");
	const customText = document.getElementById("topUpMoneyText");
	
	//숫자 정규식 (직접입력 시)
	const regNum = /^[0-9]+$/;
	
	var isRadioChecked = false;
	for(var i=0; i<radioButtons.length; i++){
		if(radioButtons[i].checked == true){
			isRadioChecked = true;
			break;
		}
	}
	
	
	if(isRadioChecked == false){//라디오버튼이 아무것도 선택되어 있지 않으면
		alert("충전할 금액을 선택해주세요.");
		document.f.topUpMoney.focus();
		return false;
	}else{//라디오버튼 선택된 것이 있음
		
		if(customRadio.checked == true){//만약 '직접입력' 선택이 된 상황에서
			
			if(customText.value == ''){//충전할 금액이 입력안된 경우
				alert('충전할 금액을 입력해주세요.');
				document.f.topUpMoneyText.focus();
				return false;
			}
			else if(!regNum.test(customText.value)){//숫자만 입력한 것이 아닐 경우
				alert('숫자만 입력해주세요.');
				document.f.topUpMoneyText.select();
				return false;
			}			
		}	
	}
	
	document.f.submit();
}

//결과값을 마이페이지에 세팅
function confirmResult(){
	opener.document.f.account.value = document.getElementById("finalTotalMoney").value;
				
	window.close();
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
                <form action="userTopupMoney.usr" method="post" name="f">
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