<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		  function otherBank() {
		      var bankSelect = document.getElementById("bank");
		      var bankInput = document.getElementById("otherBank");
		      var selectedValue = bankSelect.value;
		            
		      if (selectedValue === "OtherBank") {
		       bankInput.style.display = "block";
		      } else {
		        bankInput.style.display = "none";
		           }
		  }

</script>
</head>
<body>

	<div class="m-5">
		<form action="insertProjectPlanner.pj" method="post">
			<input type="hidden" name="kind" value="${kind}" required> 
			<input type="hidden" name="member_id" value="${member_id}" required>

			<div class="mb-3">
				<label for="planner_name" class="form-label">기획자명</label> 
				<input type="text" class="form-control" id="planner_name" name="planner_name" maxlength="20" required placeholder="개인 또는 기업, 단체명" value="${sessionScope.planner_name}">
			</div>
			<div class="mb-3">
				<label for="introduce" class="form-label">기획자소개</label>
				<textarea class="form-control" id="introduce" name="introduce" rows="3" maxlength="100" required>${sessionScope.introduce}</textarea>
			</div>
			<div class="mb-3">
				<select class="form-select" aria-label="bank"  id="bank" name="bank" onchange="otherBank()" required>
					<option disabled selected="${sessionScope.bank != '' ? '' : 'selected'}">입금계좌 은행</option>
			  		<option value="shinhan" ${sessionScope.bank == 'shinhan' ? 'selected' : ''}>신한은행</option>
					<option value="kb" ${sessionScope.bank == 'kb' ? 'selected' : ''}>국민은행</option>
					<option value="woori" ${sessionScope.bank == 'woori' ? 'selected' : ''}>우리은행</option>
					<option value="hana" ${sessionScope.bank == 'hana' ? 'selected' : ''}>하나은행</option>
					<option value="ibk" ${sessionScope.bank == 'ibk' ? 'selected' : ''}>기업은행</option>
					<option value="nh" ${sessionScope.bank == 'nh' ? 'selected' : ''}>농협은행</option>
					<option value="gn" ${sessionScope.bank == 'gn' ? 'selected' : ''}>경남은행</option>
					<option value="kwangju" ${sessionScope.bank == 'kwangju' ? 'selected' : ''}>광주은행</option>
					<option value="daegu" ${sessionScope.bank == 'daegu' ? 'selected' : ''}>대구은행</option>
					<option value="busan" ${sessionScope.bank == 'busan' ? 'selected' : ''}>부산은행</option>
					<option value="ibk" ${sessionScope.bank == 'ibk' ? 'selected' : ''}>산업은행</option>
					<option value="suhyup" ${sessionScope.bank == 'suhyup' ? 'selected' : ''}>수협은행</option>
					<option value="jb" ${sessionScope.bank == 'jb' ? 'selected' : ''}>전북은행</option>
					<option value="jeju" ${sessionScope.bank == 'jeju' ? 'selected' : ''}>제주은행</option>
					<option value="keb" ${sessionScope.bank == 'keb' ? 'selected' : ''}>케이뱅크</option>
					<option value="kakaobank" ${sessionScope.bank == 'kakaobank' ? 'selected' : ''}>카카오뱅크</option>
					<option value="shinhyup" ${sessionScope.bank == 'shinhyup' ? 'selected' : ''}>신협은행</option>
					<option value="upost" ${sessionScope.bank == 'upost' ? 'selected' : ''}>우체국은행</option>
					<option value="OtherBank" ${sessionScope.bank == 'OtherBank' ? 'selected' : ''}>기타(직접입력)</option>
			</select>
			</div>
			<div class="form-select" id="otherBank" style="display:none;" >
            	<label for="otherBankName">은행명</label>
           		<input type="text" id="otherBankName" name="otherBankName" maxlength="20" value="${sessionScope.otherBankName}">
       		</div>
			<div class="mb-3">
				<label for="account" class="form-label">입금계좌번호</label> 
				<input type="text" class="form-control" id="account" name="account" pattern="[0-9]+" maxlength="45byte" required placeholder="펀딩 성공 시 입금받을 계좌를 (-)하이푼 없이 숫자만 입력해주세요" value="${sessionScope.account}">
			</div>
		
			
	

			<div align="center">
			<input class="btn btn-primary" type="submit" value="다음 단계로"><!-- 유효성 검사는 모든 항목에 required를 입력하여 대체함, 현재 값은 세션에 올려서 한번에 등록 -->
			</div>
		</form>
	</div>
	


	<!-- JavaScript Libraries -->
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="../resources/lib/wow/wow.min.js"></script>
	<script src="../resources/lib/easing/easing.min.js"></script>
	<script src="../resources/lib/waypoints/waypoints.min.js"></script>
	<script src="../resources/lib/owlcarousel/owl.carousel.min.js"></script>
	<script src="../resources/lib/counterup/counterup.min.js"></script>
	<script src="../resources/lib/parallax/parallax.min.js"></script>
	<script src="../resources/lib/isotope/isotope.pkgd.min.js"></script>
	<script src="../resources/lib/lightbox/js/lightbox.min.js"></script>

	<!-- Template Javascript -->
	<script src="../resources/js/main.js"></script>
</body>
</html>