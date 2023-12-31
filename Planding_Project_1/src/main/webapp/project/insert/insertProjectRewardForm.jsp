<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>PlanDing - Fund for Our Plannet</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<!-- Favicon -->
<link href="../resources/img/favicon.ico" rel="icon">

<!-- Google Web Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Jost:wght@500;600;700&family=Open+Sans:wght@400;500&display=swap"
	rel="stylesheet">

<!-- Icon Font Stylesheet -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
	rel="stylesheet">

<!-- Libraries Stylesheet -->
<link href="../resources/lib/animate/animate.min.css" rel="stylesheet">
<link href="../resources/lib/owlcarousel/assets/owl.carousel.min.css"
	rel="stylesheet">
<link href="../resources/lib/lightbox/css/lightbox.min.css"
	rel="stylesheet">

<!-- Customized Bootstrap Stylesheet -->
<link href="../resources/css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="../resources/css/style.css" rel="stylesheet">

<script>

//금액 천단위 구분쉼표
function updateFormattedAmount(input) {
    // 입력된 금액을 가져옵니다.
    var amount = input.value.replace(/\D/g, ''); // 숫자 이외의 문자 제거

    // 3자리마다 쉼표를 추가하여 포맷합니다.
    var formattedAmount = amount.replace(/\B(?=(\d{3})+(?!\d))/g, ',');

    // 포맷된 금액을 입력란에 설정합니다.
    input.value = formattedAmount;
}

//리워드 추가 함수
function addReward() {
    var rewardFields = document.querySelectorAll('[id^=rewardField]');
    for (var i = 0; i < rewardFields.length; i++) {
        if (rewardFields[i].style.display === 'none') {
            rewardFields[i].style.display = 'block';

            // 추가한 리워드 필드의 input 요소들에 required 속성 추가
            var inputs = rewardFields[i].querySelectorAll('input[type="text"], textarea');
            for (var j = 0; j < inputs.length; j++) {
                inputs[j].setAttribute('required', 'required');
            }

            return; // 이미 숨겨진 필드를 보이게 한 후 종료
        }
    }
}

//리워드 삭제 함수
function deleteReward(button) {
    var rewardFieldContainer = button.parentNode;
    rewardFieldContainer.style.display = 'none';

    // 필드의 값을 초기화
    var inputs = rewardFieldContainer.querySelectorAll('input[type="text"], textarea');
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].value = '';
    }

    // 필드의 required 속성 제거
    var requiredInputs = rewardFieldContainer.querySelectorAll('input[required], textarea[required]');
    for (var i = 0; i < requiredInputs.length; i++) {
        requiredInputs[i].removeAttribute('required');
    }
}

</script>
</head>
<body>

	<!-- Main Section -->
	
	<div class="m-5">
		<form action="insertFundProjectTemp.pj" method="post">
			
			<!-- 리워드 입력란 -->
			<div class="mb-3">
	            <label for="reward_id" class="form-label"></label>
	            <input type="hidden" class="form-control" id="reward_id" name="reward_id" required value="${r_name }">
	        </div>
			<div class="mb-3">
	            <label for="r_name" class="form-label">리워드 이름</label>
	            <input type="text" class="form-control" id="r_name" name="r_name" required value="${r_name }">
	        </div>
	        <div class="mb-3">
	            <label for="r_content" class="form-label">리워드 설명</label>
	            <textarea class="form-control" name="r_content" rows="3" maxlength="100" required>${r_content }</textarea>
	        </div>
	        <div class="mb-3">
	            <label for="r_price" class="form-label">리워드 금액</label>
	            <div class="input-group">
	                <input type="text" class="form-control" name="r_price" required oninput="updateFormattedAmount(this);" value="${r_price }">
	                <span class="input-group-text">원</span>
	            </div>
	        </div>
			
			<!-- 리워드 추가 시 (10개까지만 가능) -->
			<c:forEach var="i" begin="1" end="9" step="1" varStatus="loop">
		        <div class="mb-3" id="rewardField${loop.index + 1}" 
		             <c:if test="${empty rewardNames[i]}">style="display: none;"</c:if> 
		             <c:if test="${ not empty rewardNames[i]}">style="display: block;"</c:if>
		        >
		            <div class="mb-3">
		                <label for="r_name${loop.index + 1}" class="form-label">리워드 이름</label>
		                <input type="text" class="form-control" id="r_name${loop.index + 1}" name="r_name" value="${r_name[i] }">
		            </div>
		            <div class="mb-3">
		                <label for="r_content${loop.index + 1}" class="form-label">리워드 설명</label>
		                <textarea class="form-control" name="r_content" rows="3" maxlength="100">${r_contents[i] }</textarea>
		            </div>
		            <div class="mb-3">
		                <label for="r_price${loop.index + 1}" class="form-label">리워드 금액</label>
		                <div class="input-group">
		                    <input type="text" class="form-control" name="r_price" oninput="updateFormattedAmount(this);" value="${r_prices[i] }">
		                    <span class="input-group-text">원</span>
		                </div>
		            </div>
		            <button type="button" class="btn btn-danger" onclick="deleteReward(this)">삭제</button>
		        </div>
		    </c:forEach>
			<br>
			<button class="btn btn-primary" type="button" id="addRewardButton" onclick="addReward()">리워드 추가</button>
		   
			<!-- 최종 submit 버튼 -->
			<div align="center">
				<input class="btn btn-primary" type="button" value="이전 단계로" onclick="history.back();">
				<input class="btn btn-primary" id="submit" type="submit" value="저장하고 미리보기"><!-- 유효성 검사는 모든 항목에 required를 입력하여 대체함 -->
			</div>
			
		</form>
		
	</div>
	

</body>
</html>