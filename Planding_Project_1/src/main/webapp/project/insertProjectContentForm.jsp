<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>PlanDing - Fund for Our Planet</title>
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
//날짜 유효성 검사
function calculateDateDifference() {
	var startDateInput = document.getElementById("startdate");
	var endDateInput = document.getElementById("enddate");
	var daysCounter = document.getElementById("days-counter");

	var startDate = new Date(startDateInput.value);
	var endDate = new Date(endDateInput.value);

	if (isNaN(startDate.getTime()) || isNaN(endDate.getTime())) {
    	// 시작일 또는 종료일이 유효한 날짜가 아닌 경우
    	daysCounter.textContent = "";
    	return;
	}

	var timeDifference = endDate - startDate;
	var daysDifference = Math.floor(timeDifference / (1000 * 60 * 60 * 24));

	if (daysDifference < 0) {
    	//종료일이 시작일 이전인 경우
		daysCounter.textContent = "종료일은 시작일보다 이후여야 합니다.";
		return;
	}

	// 날짜 차이를 "days-counter" 요소에 표시
	daysCounter.textContent = "시작일로부터 " + daysDifference + "일";
}

//날짜 유효성 검사
function validateDates() {
	var startDateInput = document.getElementById("startdate");
	var endDateInput = document.getElementById("enddate");

    // 시작일이 오늘보다 이전인 경우
	var today = new Date();
	today.setHours(0, 0, 0, 0); // 오늘 날짜의 시간을 00:00:00으로 설정
	var startDate = new Date(startDateInput.value);
	
	if (startDate < today) {
		document.getElementById("startdate-error").textContent =
        "시작일은 오늘 이후로 선택해야 합니다.";
      return false;
	} else {
      document.getElementById("startdate-error").textContent = "";
      return true;
    }
}
  
//금액 천단위 구분쉼표
function formatCurrency(amount) {
    return amount.toLocaleString();
}

//금액 정규화
function updateFormattedAmount(inputElement) {
    var rawValue = inputElement.value.replace(/[^\d]/g, ''); // 숫자와 반점 제외한 문자 제거
    
    if (!rawValue || rawValue === "") {//입력한 값이 없으면 0으로 세팅
        inputElement.value = "0";
        return;
    }
    
    var goalAmount = parseInt(rawValue);//숫자로 변환
    if (!isNaN(goalAmount)) {//숫자가 아니라면
        var formattedAmount = formatCurrency(goalAmount);
        inputElement.value = formattedAmount; // 반점 자동 입력
    }
}

//이미지 파일 추가
function addImageField() {
    var imageFields = document.querySelectorAll('[id^=imageField]');
    for (var i = 0; i < imageFields.length; i++) {
        if (imageFields[i].style.display === 'none') {
            imageFields[i].style.display = 'block';
            return; // 이미 숨겨진 필드를 보이게 한 후 종료
        }
    }
}

//이미지 필드 숨기기 함수 (이미지 삭제 버튼 클릭 시 사용)
function deleteImageField(button) {
	var imageFieldContainer = button.parentNode;
	imageFieldContainer.style.display = 'none';

	//이미지 필드의 값을 초기화
	var input = imageFieldContainer.querySelector('input[type="file"]');
	input.value = '';
}

//이미지 확장자 유효성 검사
function validateImageFile(input) {
	var file = input.files[0];
	var allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i; // 허용되는 이미지 파일 확장자 목록

	if (!allowedExtensions.exec(file.name)) {
		alert('이미지 파일만 업로드 가능합니다.');
		input.value = ''; // 파일 필드 초기화
		return false;
	}

    return true;
}

</script>
</head>
<body>
<%

//세션에 이미지목록이 있으면 contentImgss 변수에 담음
String[] contentImgss = null;
if(session.getAttribute("contentImgs") != null){
	contentImgss = (String[]) session.getAttribute("contentImgs");
}

%>
	<div class="m-5">
		<form method="post" enctype="multipart/form-data" name="f">

			<div class="mb-3">
				<label for="title" class="form-label">프로젝트명</label> 
				<input type="text" class="form-control" id="title" name="title" maxlength="50" required value="${sessionScope.projectInfo.title}">
			</div>
			<div class="mb-3">
				<label for="summary" class="form-label">프로젝트 요약</label>
				<textarea class="form-control" id="summary" name="summary" rows="3" maxlength="1000" required>${sessionScope.projectInfo.summary}</textarea>
			</div>
			<div class="mb-3">
				<label for="thumbnail" class="form-label">썸네일 이미지</label>
				<!-- image_tbl -->
				<input type="file" class="form-control" id="thumbnail" name="thumbnail" required value="${sessionScope.projectInfo.thumbnail }" accept="image/*"  onchange="validateImageFile(this)">
			</div>
			<div class="mb-3" id="imageField1">
                <label for="contentImg1" class="form-label">프로젝트 내용 이미지 1</label>
                <input type="file" class="form-control" id="contentImg1" name="contentImg1" accept="image/*" value="${not empty contentImgss[0]}"  onchange="validateImageFile(this)">
            </div>
            
        
        <c:forEach var="i" begin="1" end="9" step="1" varStatus="loop">
	   		<div class="mb-3" id="imageField${loop.index + 1}" 
		   		<c:if test="${empty contentImgss[i]}">style="display: none;"</c:if> 
		   		<c:if test="${ not empty contentImgss[i]}">style="display: block;"</c:if>
	   		>
			        <label for="contentImg${loop.index + 1}" class="form-label" >프로젝트 내용 이미지 ${loop.index + 1}</label>
			        <input type="file" class="form-control" id="contentImg${loop.index + 1}" name="contentImg${loop.index + 1}" accept="image/*"  onchange="validateImageFile(this)" value="${contentImgss[i]}">
			       	<button class="btn btn-danger" onclick="deleteImageField(this)">이미지 삭제</button>
			</div>
		</c:forEach>
	                <button class="btn btn-primary" id="addImageButton" type="button" onclick="addImageField()">이미지 추가</button>
	            
	       
			<div class="mb-3">
				<label for="content" class="form-label">프로젝트 내용</label>
				<textarea class="form-control" id="content" name="content" rows="3" maxlength="5000" required placeholder="줄바꿈 할 시 직접 &lt;br&gt;을 입력해주세요. 해당 기능이 개발 중에 있습니다.">${sessionScope.projectInfo.content}</textarea>
			</div>
			
			<div class="mb-3">
				<label for="startdate" class="form-label">시작예정일</label> 
					<input type="date" class="form-control" id="startdate" name="startdate" min="${requestScope.minStartdate }" required oninput="calculateDateDifference(); validateDates()"
					  value="${sessionScope.startdate }">
			<span id="startdate-error" style="color: red;"></span>
			</div>
			<div class="mb-3">
				<label for="enddate" class="form-label">종료일</label> 
					<input type="date" class="form-control" id="enddate" name="enddate" min="${requestScope.minEnddate }" required oninput="calculateDateDifference(); validateDates()"
  						value="${sessionScope.enddate }">
				<span id="enddate-error" style="color: red;"></span>
			</div>
			<div class="mb-3">
		        <label for="goal_amount" class="form-label">목표 모금액</label>
		        <div class="input-group">
		            <input type="text" class="form-control" id="goal_amount" name="goal_amount"  value="${sessionScope.projectInfo.goal_amount }" required oninput="updateFormattedAmount(this);" >
		            <span class="input-group-text">원</span>
		        </div>
		    </div>
			
			<%-- request속성값으로 넘어온 member_id를 파라미터로 넘겨줌 --%>
			<input type="hidden" name="member_id" value="${requestScope.member_id}">
			<input type="hidden" name="kind" value="${requestScope.kind}">
			
			<div align="center">
				<input class="btn btn-primary" type="button" value="이전 단계로" onclick="history.back();">
				<c:if test="${requestScope.kind == 'donate'}" >
					<input class="btn btn-primary" type="submit" id="temp" value="저장하기" formaction="insertDonateProjectTemp.pj">
				</c:if>
				
				<c:if test="${requestScope.kind == 'fund'}">
					<input class="btn btn-primary" type="submit" id="temp" value="다음 단계로" formaction="insertFundProjectRewardForm.pj">
				</c:if>
			</div>
		</form>

	</div>


	<!-- JavaScript Libraries -->
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
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