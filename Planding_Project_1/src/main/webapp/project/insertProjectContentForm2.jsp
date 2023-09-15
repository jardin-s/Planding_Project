<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- 백업용 -->
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
  function startDate() {
    var startDateInput = document.getElementById("startdate");
    var endDateInput = document.getElementById("enddate");
    var startDate = new Date(startDateInput.value);
    var today = new Date();
    var threeMonthsLater = new Date(today);
    threeMonthsLater.setMonth(threeMonthsLater.getMonth() + 3);
    var oneYearLater = new Date(today);
    oneYearLater.setFullYear(oneYearLater.getFullYear() + 1);

    if (startDate < today || startDate > threeMonthsLater) {
      document.getElementById("startdate-error").textContent = "시작일은 오늘로부터 3개월 이내여야 합니다.";
    } else {
      document.getElementById("startdate-error").textContent = "";
    }

    if (endDateInput.value !== "" && new Date(endDateInput.value) > oneYearLater) {
      endDateInput.value = ""; // 종료일 초기화
    }
  }

  function endDate() {
    var startDateInput = document.getElementById("startdate");
    var endDateInput = document.getElementById("enddate");
    var startDate = new Date(startDateInput.value);
    var endDate = new Date(endDateInput.value);

    if (endDate < startDate) {
      document.getElementById("enddate-error").textContent = "종료일은 시작일보다 이후여야 합니다.";
    } else {
      document.getElementById("enddate-error").textContent = "";
    }
  }
  function formatCurrency(amount) {
	    return amount.toLocaleString();
	}

  function updateFormattedAmount(inputElement) {
	    var rawValue = inputElement.value.replace(/[^\d]/g, ''); // 숫자와 반점 제외한 문자 제거
	    
	    if (!rawValue || rawValue === "") {
	        inputElement.value = "0";
	        return;
	    }
	    
	    var goalAmount = parseInt(rawValue);
	    if (!isNaN(goalAmount)) {
	        var formattedAmount = formatCurrency(goalAmount);
	        inputElement.value = formattedAmount; // 반점 자동 입력
	    }
	}
  function deleteImageField(button) {
	    var imageFieldContainer = button.parentElement;
	    imageFieldContainer.remove();
	}
  
  
  
  document.addEventListener("DOMContentLoaded", function () {
	    var imageFieldsContainer = document.getElementById("imageFieldsContainer");
	    var addImageButton = document.getElementById("addImageButton");
	    var maxImageFields = 10; // 최대 이미지 입력 필드 개수

	    addImageButton.addEventListener("click", function () {
	        // 현재 이미지 입력 필드의 개수를 센다.
	        var currentImageFieldsCount = document.querySelectorAll('input[name="contentImg"]').length;
			var currentImageFileName="contentImg"+currentImageFieldsCount;
	        // 최대 이미지 입력 필드 개수를 초과하면 추가를 막는다.
	        if (currentImageFieldsCount >= maxImageFields) {
	            alert("최대 " + maxImageFields + "개의 이미지만 추가할 수 있습니다.");
	            return;
	        }

	        // 새 이미지 입력 필드를 생성한다.
	        var newImageField = document.createElement("div");
	        newImageField.classList.add("mb-3");
	        newImageField.innerHTML = `
	            <label for="contentImg" class="form-label">프로젝트 내용 이미지</label>
	            <input type="file" class="form-control" name="contentImg" accept="image/*" value="${sessionScope.contentImg}">
	            <button class="btn btn-danger" onclick="deleteImageField(this)">이미지 삭제</button>

	        `;

	        // 새 이미지 입력 필드를 컨테이너에 추가한다.
	        imageFieldsContainer.appendChild(newImageField);
	    });
	});
	</script>
</head>
<body>
<%
String[] contentImgss;
if(session.getAttribute("contentImgs") != null){
	contentImgss = (String[]) session.getAttribute("contentImgs"); 

}else{
	contentImgss=null;
}
%>
	<div class="m-5">
		<form action="insertProjectContents.pj" method="post">

			<div class="mb-3">
				<label for="title" class="form-label">프로젝트명</label> 
				<input type="text" class="form-control" id="title" name="title" maxlength="50" required value="${sessionScope.title}">
			</div>
			<div class="mb-3">
				<label for="summary" class="form-label">프로젝트 요약</label>
				<textarea class="form-control" id="summary" name="summary" rows="3" maxlength="100" required>${sessionScope.summary}</textarea>
			</div>
			<div class="mb-3">
				<label for="thumbnail" class="form-label">썸네일 이미지</label>
				<!-- image_tbl -->
				<input type="file" class="form-control" id="thumbnail" name="thumbnail" required value="${sessionScope.thumbnail }" accept="image/*" >
			</div>
			<div class="mb-3" id="imageFieldsContainer">
			    <label for="contentImg" class="form-label">프로젝트 내용 이미지</label>
			    <input type="file" class="form-control" id="contentImg" name="contentImg" accept="image/*" value="<%=(contentImgss!=null && contentImgss[0]!=null)?contentImgss[0]:""%>">
			</div>
		<c:if test="contentImgs.length>1">
		<% 
		if(contentImgss.length>1){
		for(int i=1;i<contentImgss.length;i++){%>
			<div class="mb-3" id="imageFieldsContainer">
			    <label for="contentImg" class="form-label">프로젝트 내용 이미지</label>
			    <input type="file" class="form-control" id="contentImg" name="contentImg" accept="image/*" value="<%=contentImgss[i]%>">
			    <button class="btn btn-danger" onclick="deleteImageField(this)">이미지 삭제</button>
			</div>
		<%} }%>
		</c:if>
			<button class="btn btn-primary" id="addImageButton">이미지 추가</button>
			<div class="mb-3">
				<label for="content" class="form-label">프로젝트 내용</label>
				<textarea class="form-control" id="content" name="content" rows="3" maxlength="500" required>${sessionScope.content}</textarea>
			</div>
			
			<div class="mb-3">
				<label for="startdate" class="form-label">시작예정일</label> 
					<input type="date" class="form-control" id="startdate" name="startdate" required oninput="startDate()" value="${sessionScope.startdate }"> 
					<span id="startdate-error" style="color: red;"></span>
			</div>
			<div class="mb-3">
				<label for="enddate" class="form-label">종료일</label> 
					<input type="date" class="form-control" id="enddate" name="enddate" required oninput="endDate()" value="${sessionScope.enddate }"> 
					<span id="enddate-error" style="color: red;"></span>
			</div>
			<div class="mb-3">
		        <label for="goal_amount" class="form-label">목표 모금액</label>
		        <div class="input-group">
		            <input type="text" class="form-control" id="goal_amount" name="goal_amount" value="${sessionScope.goal_amount }" required oninput="updateFormattedAmount(this);" >
		            <span class="input-group-text">원</span>
		        </div>
		    </div>
			<div class="mb-3">
				<label for="category">카테고리:</label><br> 
					<select id="category" name="category" class="form-select" required>
						<option value="nature" ${sessionScope.category == 'nature' ? 'selected' : ''}>환경</option>
						<option value="animal" ${sessionScope.category == 'animal' ? 'selected' : ''}>동물</option>
						<option value="human" ${sessionScope.category == 'human' ? 'selected' : ''}>사람</option>
				</select>
			</div>

			<div align="center">
			<c:if test="${sessionScope.kind == 'donate'}" >
			<input class="btn btn-primary" type="button" value="이전 단계로" onclick="window.location.href = 'donateProjectInsert.pj'">
			</c:if>
			<c:if test="${sessionScope.kind == 'fund'}">
			<input class="btn btn-primary" type="button" value="이전 단계로" onclick="window.location.href = 'fundProjectInsert.pj'">
			</c:if>
			<input class="btn btn-primary" id="submit" type="submit" value="다음 단계로"><!-- 유효성 검사는 모든 항목에 required를 입력하여 대체함 세션에 등록해두고 한번에 넣기 -->
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