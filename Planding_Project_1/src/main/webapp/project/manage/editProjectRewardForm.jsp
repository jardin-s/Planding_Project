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
function confirmDelete() {
    if (confirm("정말로 삭제하시겠습니까? 삭제된 리워드는 다시 복구 할 수 없습니다.")) {
        // 사용자가 확인을 선택한 경우
        window.location.href = 'deleteReward.pj?reward_id=${rewardInfo.reward_id}&project_id=${project_id}';
    } else {
        // 사용자가 취소를 선택한 경우
        // 아무 작업도 수행하지 않음
    }
}


</script>
</head>
<body>

	<!-- Main Section -->
	
	<div class="m-5">
		<form action="editReward.pj" method="post">
			
			<!-- 리워드 입력란 -->
			<div class="mb-3">
	            <label for="reward_id" class="form-label"></label>
	            <input type="hidden" class="form-control" id="reward_id" name="reward_id" required value="${rewardInfo.reward_id }">
	        </div>
			<div class="mb-3">
	            <label for="project_id" class="form-label"></label>
	            <input type="hidden" class="form-control" id="project_id" name="project_id" required value="${project_id }">
	        </div>
			<div class="mb-3">
	            <label for="r_name" class="form-label">리워드 이름</label>
	            <input type="text" class="form-control" id="r_name" name="r_name" required value="${rewardInfo.r_name }">
	        </div>
	        <div class="mb-3">
	            <label for="r_content" class="form-label">리워드 설명</label>
	            <textarea class="form-control" name="r_content" rows="3" maxlength="100" required>${rewardInfo.r_content }</textarea>
	        </div>
	        <div class="mb-3">
	            <label for="r_price" class="form-label">리워드 금액</label>
	            <div class="input-group">
	                <input type="text" class="form-control" name="r_price" required oninput="updateFormattedAmount(this);" value="${rewardInfo.r_price }">
	                <span class="input-group-text">원</span>
	            </div>
	        </div>
			
			<br>
		   
			<!-- 최종 submit 버튼 -->
			<div align="center">				
				<input class="btn btn-primary" type="button" value="삭제하기" onclick="confirmDelete()">
				<input class="btn btn-primary" type="button" value="취소하고 돌아가기" onclick="history.back();">
				<input class="btn btn-primary" id="submit" type="submit" value="제출하기"><!-- 유효성 검사는 모든 항목에 required를 입력하여 대체함 -->
				<br>
			</div>
			
		</form>
		
	</div>
	
</body>
</html>