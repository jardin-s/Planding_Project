<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
</head>

<script>
//은행 직접입력 칸
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
//계좌번호 유효성 검사
function validateAccountInput() {
    var accountInput = document.getElementById("account_num");
    var inputValue = accountInput.value;
    var numericValue = inputValue.replace(/\D/g, ''); // 숫자가 아닌 문자 제거

    // 숫자만 포함된 값을 입력 필드에 업데이트
    accountInput.value = numericValue;
}

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



//---------------------------------------------------------------------- 이미지 관련

//기존파일을 삭제하고, 파일input태그 나타내기 : 썸네일
function deleteThumbnail(){
  // 썸네일을 표시하는 div 요소를 가져옵니다.
  var thumbnailDiv = document.getElementById("dltThumbnail");

  // 부모 요소를 가져옵니다.
  var parentDiv = thumbnailDiv.parentNode;

  // 썸네일을 표시하는 div 요소를 삭제하고, 새로운 파일 입력(input) 태그로 대체합니다.
  parentDiv.removeChild(thumbnailDiv);
  parentDiv.innerHTML = "<input type='file' name='thumbnail' class='form-control' id='thumbnail' accept='image/*' aria-describedby='thumbnail' aria-label='thumbnail' maxlength='100' onchange='validateImageFile(this);'>";
}




//기존 이미지 개수
let orgImgCount = ${originImgCount};
let allImgCount = ${originImgCount};

//기존파일을 삭제
function deleteOrigImage(filetag){
	
	//기존파일정보가 담긴 태그
	var parentSpan = filetag.parentNode;//상위 span (<span>this</span>)
	var grandParentDiv = filetag.parentNode.parentNode;//상위 div (<div><span>this</span></div>)
	
	grandParentDiv.removeChild(parentSpan);
	
	orgImgCount -= 1;//기존이미지 개수 마이너스 1
	allImgCount -= 1;//전체이미지 개수 마이너스 1
}


//이미지 파일 추가
function addImageField() {
    if (allImgCount < 10) {//전체 개수가 10 미만일때만 추가
        // 이미지 필드를 동적으로 생성하고 컨테이너에 추가
        var imageContainer = document.getElementById('imageContainer');
        var imageField = document.createElement('div');
        imageField.className = 'mb-3';
        imageField.innerHTML = '<input type="file" class="form-control" id="contentImg'+(allImgCount+1)+'" name="contentImg'+(allImgCount+1)+'" accept="image/*" onchange="validateImageFile(this)"><button class="btn btn-danger" type="button" onclick="deleteImageField(this)">이미지 삭제</button>';

        imageContainer.appendChild(imageField);
        allImgCount += 1;//전체이미지 개수 플러스 1
    }
}

//이미지 필드 숨기기 함수 (이미지 삭제 버튼 클릭 시 사용)
function deleteImageField(button) {
	var imageFieldContainer = button.parentNode;
	imageFieldContainer.style.display = 'none';

	//이미지 필드의 값을 초기화
	var input = imageFieldContainer.querySelector('input[type="file"]');
	input.value = '';
	allImgCount -= 1;//전체이미지 개수 마이너스 1
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

function confirmDelete(project_id) {
    if (confirm("정말로 삭제하시겠습니까?")) {
        // '확인' 버튼을 눌렀을 때의 처리 (삭제 동작 실행)
        window.location.href = 'deleteProject.pj?project_id=' + project_id;
    }
}

</script>

<body>

	<div class="m-5">
	    <form action="editProject.pj?project_id=${projectInfo.project_id}" method="post" enctype="multipart/form-data">
	     			<div class="mb-3">
					<label for="project_id" class="form-label">프로젝트 아이디 : ${projectInfo.project_id}</label> 
					<input type="hidden" class="form-control" id="project_id" name="project_id" maxlength="20" value="${projectInfo.project_id}" required readonly>
				
				
				</div>
				<div class="mb-3">
					<label for="kind" class="form-label">프로젝트 종류 : ${projectInfo.kind }</label> 
					<input type="hidden" class="form-control" id="kind" name="kind" maxlength="20" value="${projectInfo.kind }" required readonly>
				</div>
				<div class="mb-3">
					<label for="member_id" class="form-label"></label> 
					<input type="hidden" class="form-control" id="member_id" name="member_id" maxlength="20" value="${plannerInfo.member_id}" required readonly>
				</div>
				<div class="mb-3">
					<label for="planner_name" class="form-label">기획자명</label> 
					<input type="text" class="form-control" id="planner_name" name="planner_name" maxlength="20" value="${plannerInfo.planner_name}" required placeholder="개인 또는 기업, 단체명">
				</div>
				<div class="mb-3">
					<label for="introduce" class="form-label">기획자소개</label>
					<textarea class="form-control" id="introduce" name="introduce" rows="3" maxlength="100" required>${plannerInfo.introduce }</textarea>
				</div>
				<div class="mb-3">
					<select class="form-select" aria-label="bank" id="bank" name="bank" onchange="otherBank()" required>
					    <option disabled ${plannerInfo.bank != null ? 'selected' : ''}>입금계좌 은행</option>
					    <option value="shinhan" ${plannerInfo.bank == 'shinhan' ? 'selected' : ''}>신한은행</option>
					    <option value="kb" ${plannerInfo.bank == 'kb' ? 'selected' : ''}>국민은행</option>
					    <option value="woori" ${plannerInfo.bank == 'woori' ? 'selected' : ''}>우리은행</option>
					    <option value="hana" ${plannerInfo.bank == 'hana' ? 'selected' : ''}>하나은행</option>
					    <option value="ibk" ${plannerInfo.bank == 'ibk' ? 'selected' : ''}>기업은행</option>
					    <option value="nh" ${plannerInfo.bank == 'nh' ? 'selected' : ''}>농협은행</option>
					    <option value="gn" ${plannerInfo.bank == 'gn' ? 'selected' : ''}>경남은행</option>
					    <option value="kwangju" ${plannerInfo.bank == 'kwangju' ? 'selected' : ''}>광주은행</option>
					    <option value="daegu" ${plannerInfo.bank == 'daegu' ? 'selected' : ''}>대구은행</option>
					    <option value="busan" ${plannerInfo.bank == 'busan' ? 'selected' : ''}>부산은행</option>
					    <option value="kdb" ${plannerInfo.bank == 'kdb' ? 'selected' : ''}>산업은행</option>
					    <option value="suhyup" ${plannerInfo.bank == 'suhyup' ? 'selected' : ''}>수협은행</option>
					    <option value="jb" ${plannerInfo.bank == 'jb' ? 'selected' : ''}>전북은행</option>
					    <option value="jeju" ${plannerInfo.bank == 'jeju' ? 'selected' : ''}>제주은행</option>
					    <option value="keb" ${plannerInfo.bank == 'keb' ? 'selected' : ''}>케이뱅크</option>
					    <option value="kakaobank" ${plannerInfo.bank == 'kakaobank' ? 'selected' : ''}>카카오뱅크</option>
					    <option value="shinhyup" ${plannerInfo.bank == 'shinhyup' ? 'selected' : ''}>신협은행</option>
					    <option value="upost" ${plannerInfo.bank == 'upost' ? 'selected' : ''}>우체국은행</option>
					    <option value="OtherBank" ${plannerInfo.bank == 'OtherBank' ? 'selected' : ''}>기타(직접입력)</option>
					</select>
				</div>
				<div class="form-select" id="otherBank" style="display:none;" >
	            	<label for="otherBankName">은행명</label>
	           		<input type="text" id="otherBankName" name="otherBankName" maxlength="20" value="${plannerInfo.bank }">
	       		</div>
				<div class="mb-3">
					<label for="account_num" class="form-label">입금계좌번호</label> 
					<input type="text" class="form-control" id="account_num" name="account_num" maxlength="45byte" value="${plannerInfo.account_num}" required placeholder="펀딩 성공 시 입금받을 계좌를 입력해주세요">
				</div>
	
				<div class="mb-3">
					<label for="title" class="form-label">프로젝트명</label> 
					<input type="text" class="form-control" id="title" name="title" maxlength="50" value="${projectInfo.title}" required>
				</div>
				
				
				
				<div class="mb-3">
					<label for="summary" class="form-label">프로젝트 요약</label>
					<textarea class="form-control" id="summary" name="summary" rows="3" maxlength="1000" required>${projectInfo.summary}</textarea>
				</div>
				<div class="mb-3">
					썸네일 : 
				</div>
				<div class="input-group mb-3">
				  <c:if test="${projectInfo.thumbnail ne null }">
				    <div id="dltThumbnail">
				      <i class="far fa-image me-2"></i>${projectInfo.thumbnail }&nbsp;&nbsp;
				      <a href="javascript:void(0)" onclick="deleteThumbnail();">이미지수정</a>
				    </div>
				  </c:if>
				</div>
				<hr>
				
				<!-- 이미지 수정 부분 -->
				<div class="mb-1" id="editImg">
					<span class="mb-3">프로젝트 내용 이미지</span>
					
					<!-- 기존 이미지 개수 -->
					<input type="hidden" name="originImgCount" value="${originImgCount }">
					
					
					<!-- 기존 이미지 목록 출력 -->
					<c:forTokens var="p_image" items="${projectInfo.image }" delims=";" varStatus="status">
						<div>
					    	<span style="width:380px;display:block;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;word-break: break-all;">
					    		<a href="javascript:void(0);" class="text-danger" onclick="deleteOrigImage(this);">X</a>
					    		&nbsp;&nbsp;<i class="far fa-image me-2"></i>${p_image }
					    		<input type="hidden" name="originImages" value="${p_image}">
					    	</span>
					    </div>
					</c:forTokens>
					<div id="imageContainer">
					</div>
	                
					<c:if test="${originImgCount <10 }">
						<button class="btn btn-primary" id="addImageButton" type="button" onclick="addImageField()">이미지 추가</button>
					</c:if>
					
				</div>
				
				<!-- 이미지 수정 부분 끝 -->
				
				<hr>
				
				<div class="mb-3">
					<label for="content" class="form-label">프로젝트 내용</label>
					<textarea class="form-control" id="content" name="content" rows="3" maxlength="5000" required placeholder="줄바꿈 할 시 직접 &lt;br&gt;을 입력해주세요. 해당 기능이 개발 예정중에 있습니다.">${projectInfo.content}</textarea>
				</div>            
	
	            
	 
				<div class="mb-3"  style="clear: both;">
					<label for="startdate" class="form-label">시작일</label> 
						<input type="date" class="form-control" id="startdate" name="startdate" required oninput="startDate()" min="${requestScope.minStartdate }" value="${projectInfo.startdate}"> 
						<span id="startdate-error" style="color: red;"></span>
				</div>
				<div class="mb-3">
					<label for="enddate" class="form-label">종료일</label> 
						<input type="date" class="form-control" id="enddate" name="enddate" required oninput="endDate()" min="${requestScope.minEnddate }" value="${projectInfo.enddate}"> 
						<span id="enddate-error" style="color: red;"></span>
				</div>
				<div class="mb-3">
			        <label for="goal_amount" class="form-label">목표 모금액</label>
			        <div class="input-group">
			            <input type="text" class="form-control" id="goal_amount" name="goal_amount" required oninput="updateFormattedAmount(this);" value="${projectInfo.goal_amount_df }">
			            <span class="input-group-text">원</span>
			        </div>
			        <p id="korean-amount"></p>
			    </div>
			    <div align="center">
					<input type="submit"  class="btn btn-primary" value="프로젝트 수정하기">
				</div>  
		</form>
		
		<div align="center">
			<button type="submit" class="btn btn-outline-danger" onclick="confirmDelete(${projectInfo.project_id})">프로젝트 삭제하기</button>
		</div>
		  
	</div>

</body>
</html>