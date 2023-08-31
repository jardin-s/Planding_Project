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
		function disableFormElements(disable) {
		    var formElements = document.forms[0].elements;
		    for (var i = 0; i < formElements.length; i++) {
		        var element = formElements[i];
		        if (element.id !== "content") { // content 필드는 활성화 유지
		            element.disabled = disable;
		        }
		        if (element.id !== "submit") {
		            element.disabled = disable;
		        }
		    }
		}
		
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
	            disableFormElements(true);
	        } else {
	            document.getElementById("startdate-error").textContent = "";
	            disableFormElements(false);
	        }

	        if (endDateInput.value !== "" && new Date(endDateInput.value) > oneYearLater) {
	            endDateInput.value = "";
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

	</script>
</head>
<body>
	<%
	String bank=request.getParameter("bank");
	String otherBankName=request.getParameter("otherBankName");
	%>
	<div>
		<jsp:include page="../userHeader.jsp" />
	</div>

	<div class="m-5">
		<form action="insert_project.pj" method="post">
			<input type="text" name="kind" value="${kind}" readonly required> 
			<input type="text" name="member_id" value="${member_id}" readonly required>
			
			<div class="mb-3">
				<label for="kind" class="form-label">프로젝트 종류</label> 
				<input type="text" class="form-control" id="kind" name="kind" maxlength="20" required readonly>
			</div>
			<div class="mb-3">
				<label for="member_id" class="form-label">아이디</label> 
				<input type="text" class="form-control" id="member_id" name="member_id" maxlength="20" required readonly>
			</div>
			<div class="mb-3">
				<label for="planner_name" class="form-label">기획자명</label> 
				<input type="text" class="form-control" id="planner_name" name="planner_name" maxlength="20" required placeholder="개인 또는 기업, 단체명">
			</div>
			<div class="mb-3">
				<label for="introduce" class="form-label">기획자소개</label>
				<textarea class="form-control" id="introduce" name="introduce" rows="3" maxlength="100" required></textarea>
			</div>
			<div class="mb-3">
				<select class="form-select" aria-label="bank"  id="bank" name="bank" onchange="otherBank()" required>
					<option disabled <%=(bank!=null)?"selected":"" %>>입금계좌 은행</option>
			  		<option value="shinhan" <%=(bank.equals("shinhan"))?"selected":"" %>>신한은행</option>
					<option value="kb" <%=(bank.equals("kb"))?"selected":"" %>>국민은행</option>
					<option value="woori" <%=(bank.equals("woori"))?"selected":"" %>>우리은행</option>
					<option value="hana" <%=(bank.equals("hana"))?"selected":"" %>>하나은행</option>
					<option value="ibk" <%=(bank.equals("ibk"))?"selected":"" %>>기업은행</option>
					<option value="nh" <%=(bank.equals("nh"))?"selected":"" %>>농협은행</option>
					<option value="gn" <%=(bank.equals("gn"))?"selected":"" %>>경남은행</option>
					<option value="kwangju" <%=(bank.equals("kwangju"))?"selected":"" %>>광주은행</option>
					<option value="daegu" <%=(bank.equals("daegu"))?"selected":"" %>>대구은행</option>
					<option value="busan" <%=(bank.equals("busan"))?"selected":"" %>>부산은행</option>
					<option value="ibk" <%=(bank.equals("ibk"))?"selected":"" %>>산업은행</option>
					<option value="suhyup" <%=(bank.equals("suhyup"))?"selected":"" %>>수협은행</option>
					<option value="jb" <%=(bank.equals("jb"))?"selected":"" %>>전북은행</option>
					<option value="jeju" <%=(bank.equals("jeju"))?"selected":"" %>>제주은행</option>
					<option value="keb" <%=(bank.equals("keb"))?"selected":"" %>>케이뱅크</option>
					<option value="kakaobank" <%=(bank.equals("kakaobank"))?"selected":"" %>>카카오뱅크</option>
					<option value="shinhyup" <%=(bank.equals("shinhyup"))?"selected":"" %>>신협은행</option>
					<option value="upost" <%=(bank.equals("upost"))?"selected":"" %>>우체국은행</option>
					<option value="OtherBank" <%=(bank.equals("OtherBank"))?"selected":"" %>>기타(직접입력)</option>
			</select>
			</div>
			<div class="form-select" id="otherBank" style="display:none;" >
            	<label for="otherBankName">은행명</label>
           		<input type="text" id="otherBankName" name="otherBankName" maxlength="20" value="<%=(otherBankName!=null)?otherBankName:"" %>">
       		</div>
			<div class="mb-3">
				<label for="account" class="form-label">입금계좌번호</label> 
				<input type="text" class="form-control" id="account" name="account" maxlength="45byte" required placeholder="펀딩 성공 시 입금받을 계좌를 입력해주세요">
			</div>

			<div class="mb-3">
				<label for="title" class="form-label">프로젝트명</label> 
				<input type="text" class="form-control" id="title" name="title" maxlength="50" required>
			</div>
			<div class="mb-3">
				<label for="summary" class="form-label">프로젝트 요약</label>
				<textarea class="form-control" id="summary" name="summary" rows="3" maxlength="100" required></textarea>
			</div>
			<div class="mb-3">
				<label for="contentImg" class="form-label">프로젝트 내용 이미지 어케해요..</label> 
				<input type="file" class="form-control" id="contentImg" name="contentImg">
			</div>
			<div class="mb-3">
				<label for="content" class="form-label">프로젝트 내용</label>
				<textarea class="form-control" id="content" name="content" rows="3" maxlength="500" required></textarea>
			</div>
			<div class="mb-3">
				<label for="thumbnail" class="form-label">썸네일 이미지</label>
				<!-- image_tbl -->
				<input type="file" class="form-control" id="thumbnail" name="thumbnail" required>
			</div>
			<div class="mb-3">
				<label for="startdate" class="form-label">시작예정일</label> 
					<input type="date" class="form-control" id="startdate" name="startdate" required oninput="startDate()"> 
					<span id="startdate-error" style="color: red;"></span>
			</div>
			<div class="mb-3">
				<label for="enddate" class="form-label">종료일</label> 
					<input type="date" class="form-control" id="enddate" name="enddate" required oninput="endDate()"> 
					<span id="enddate-error" style="color: red;"></span>
			</div>
			<div class="mb-3">
		        <label for="goal_amount" class="form-label">목표 모금액</label>
		        <div class="input-group">
		            <input type="text" class="form-control" id="goal_amount" name="goal_amount" required oninput="updateFormattedAmount(this);">
		            <span class="input-group-text">원</span>
		        </div>
		        <p id="korean-amount"></p>
		    </div>
			<div class="mb-3">
				<label for="category">카테고리:</label><br> 
					<select id="category" name="category" class="form-select" required>
						<option value="nature">환경</option>
						<option value="animal">동물</option>
						<option value="human">사람</option>
				</select>
			</div>

			<div align="center">
			<input class="btn btn-primary" type="submit" id="submit" value="프로젝트 수정하기"><!-- 유효성 검사는 모든 항목에 required를 입력하여 대체함 -->
			</div>
		</form>

	</div>
	<div>
		<jsp:include page="../userFooter.jsp" />
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