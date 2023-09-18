<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <title>PlanDing - Fund for Our Planet</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Favicon -->
    <link href="../../resources/img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Jost:wght@500;600;700&family=Open+Sans:wght@400;500&display=swap" rel="stylesheet">  

    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="../../resources/lib/animate/animate.min.css" rel="stylesheet">
    <link href="../../resources/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="../../resources/lib/lightbox/css/lightbox.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="../../resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="../../resources/css/style.css" rel="stylesheet">
</head>

<script type="text/javascript">

//정렬 요청
function changeOrder() {
	
	let selectedValue = document.getElementById("selectOrder").value;
	
	if(selectedValue != 'default'){
		document.forder.submit();
	}
	
}

//검색어 유효성 검사 및 검색요청
function searchMemberList() {
	
	let search_id = document.getElementById("member_id").value;
	
	if(search_id == ''){
		alert('검색어를 입력하세요.');
		return false;
	}
	
	document.fsearch.submit();
	
}

//편집을 취소버튼으로 변경하고 삭제버튼 보이기
function switchEditCancel(){
	
	//편집 버튼 클릭 시 -> 편집버튼을 취소버튼으로 변경하고, 선택삭제버튼 보이기
	switchBtn();
	
	//form-check클래스 div태그 안에 체크박스 태그 삽입
	const checkElements = document.getElementsByClassName('remove-th');
	for(let i=0; i<checkElements.length; i++){
		
		if(checkElements[i].classList.contains('d-none')){//숨김 상태면 보이기로 전환
			checkElements[i].classList.remove('d-none');
		}else{//보이기 상태면 숨김으로 전환
			checkElements[i].classList.add('d-none');
		}
	}
	
}

//편집을 취소로 변경. 삭제버튼 활성화
function switchBtn(){
	
	//편집을 취소로 변경
	//취소를 편집으로 변경
	const editBtn = document.getElementById("editBtn");
	const editBtnText = editBtn.innerText;
	
	if(editBtnText == "회원편집"){
		editBtn.innerText = "취소";
	}else if(editBtnText == "취소"){
		editBtn.innerText = "회원편집";
	}
	
	//삭제버튼 없으면 활성화
	//삭제버튼 있으면 비활성화
	const deleteBtn = document.getElementById("deleteBtn");
	if(deleteBtn.classList.contains('d-none')){
		deleteBtn.classList.remove('d-none');
	}else{
		deleteBtn.classList.add('d-none');
	}
	
}

//전체선택
function checkAll(theForm){
	
	if(theForm.remove.length == undefined){//폼의 remove(체크박스)배열의 길이가 정의되어 있지 않다면 == 항목이 1개만 있다면
		
		theForm.remove.checked = theForm.allCheck.checked; //전체선택 체크하면, 모든 항목이 체크됨
	
	}else{//항목이 2개 이상 있다면 -> 배열로 생성(같은이름(remove)의 checkbox)
	
		for(var i=0; i<theForm.remove.length; i++){
			theForm.remove[i].checked = theForm.allCheck.checked; //remove배열의 각 값 checked
		}
	}
	
}

function selectDelete(){
	
	const checkElements = document.getElementsByClassName('form-check-input');
	
	let isCheckboxChecked = false;
	for(let i=0; i<checkElements.length; i++){
		
		//하나라도 체크된 것이 있으면 체크여부 true로 변경하고 반복문 끝
		if(checkElements[i].checked == true){
			isCheckboxChecked = true;
			break;
		}		
	}
	
	if(!isCheckboxChecked){//체크된 것이 없으면
		return alert('선택된 항목이 없어 삭제할 수 없습니다.');
	
	}else{//체크된 것이 있으면
		
		if(confirm('회원을 삭제하면 회원 아이디를 제외한 모든 개인정보가 삭제됩니다. 정말로 삭제하시겠습니까?')){
			document.dlt.submit();
		}else{
			alert('회원 삭제를 취소합니다.');
			return false;
		}		
	}
	
	
}


function authorizeProject(project_id){
	if(confirm('이 프로젝트를 승인하시겠습니까?')){
		location.href='authorizeProject.mngp?project_id='+project_id;
	}else{
		alert('승인 취소되었습니다.');
	}
}

function unauthorizeProject(project_id){
	if(confirm('이 프로젝트의 승인을 거부하시겠습니까?')){
		location.href='unauthorizeProject.mngp?project_id='+project_id;
	}else{
		alert('승인거부가 취소되었습니다.');
	}
}

function cancelProject(project_id, status){
	if(confirm('이 프로젝트의 진행을 취소하시겠습니까?')){
		location.href='cancelProjectForm.mngp?project_id='+project_id+'&status='+status;
	}else{
		alert('프로젝트를 계속 진행합니다.');
	}
}

</script>

<body>
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid py-4 mb-5 wow fadeIn" data-wow-delay="0.1s" style="background-color:#86B381">
        <div class="container text-center py-5">
            <h3 class="display-6 py-3 text-white animated slideInDown">프로젝트 상세</h3>
        </div>
    </div>
    <!-- Page Header End -->
    
    
    <div class="container-xxl mb-5 py-5">
   		<div class="container col-10 col-md-8">
   			<div class="col-12 mb-5">
   				<h4 class="text-center mb-5">${projectInfo.title }</h4>
   				<table class="table table-bordered mb-5">
			    	<tr>
			    		<th colspan="2" class="text-center">
			    			<img src="<%=request.getContextPath()%>/images/project_No_${projectInfo.project_id }/${projectInfo.project_id }_${projectInfo.thumbnail }" style="width:80%">
			    		</th>
			    	</tr>
			    	<tr>
			    		<th class="text-center col-4">프로젝트 ID</th>
			    		<td>${projectInfo.project_id }</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">프로젝트 제목</th>
			    		<td>${projectInfo.title }</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">프로젝트 구분</th>
			    		<td>
			    			<c:if test="${projectInfo.kind eq 'donate'}">기부</c:if>
			    			<c:if test="${projectInfo.kind eq 'fund'}">펀딩</c:if>
			    		</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">프로젝트 상태</th>
			    		<td>
			    			<c:if test="${projectInfo.status eq 'unauthorized'}">미승인</c:if>
			    			<c:if test="${projectInfo.status eq 'ready'}">공개예정</c:if>
			    			<c:if test="${projectInfo.status eq 'ongoing'}">진행중</c:if>
			    			<c:if test="${projectInfo.status eq 'done'}">종료</c:if>
			    			<c:if test="${projectInfo.status eq 'success'}">성공</c:if>
			    		</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">등록일자</th>
			    		<td>${projectInfo.regdate}</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">시작일</th>
			    		<td>${projectInfo.startdate}</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">종료일</th>
			    		<td>${projectInfo.enddate}</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">목표 모금액</th>
			    		<td>${projectInfo.goal_amount}원</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">현재 모금액</th>
			    		<td>${projectInfo.curr_amount}원</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">달성률</th>
			    		<td>${projectInfo.progress}%</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">관심등록 수</th>
			    		<td>${projectInfo.likes}</td>
			    	</tr>
			    	<tr>
			    		<th colspan="2" class="text-center">요약글</th>
			    	</tr>
			    	<tr>
			    		<td colspan="2" class="text-center">${projectInfo.summary}</td>
			    	</tr>
			    	<tr>
			    		<th colspan="2" class="text-center">내용</th>
			    	</tr>
			    	<tr>
			    		<td colspan="2" class="text-center">${projectInfo.content}</td>
			    	</tr>
			    	<tr>
			    		<th colspan="2" class="text-center">첨부 이미지</th>
			    	</tr>
			    	<tr>
			    		<td colspan="2" class="text-center">
			    			<c:forTokens var="image" items="${projectInfo.image }" delims=";">
								<img src="<%=request.getContextPath()%>/images/project_No_${projectInfo.project_id }/${projectInfo.project_id }_${image}" style="width:60%; margin-bottom: 2rem">
							</c:forTokens>
			    		</td>
			    	</tr>
			    </table>
			    
			    <h4 class="text-center mb-4">프로젝트 기획자</h4>
   				<table class="table table-bordered mb-5">
			    	<tr class="text-center">
			    		<th>기획자 ID</th>
			    		<td>${plannerInfo.member_id}</td>
			    		<th>기획자 이름</th>
			    		<td>${plannerInfo.planner_name}</td>
			    	</tr>
			    	<tr class="text-center">
			    		<th>계좌 은행</th>
			    		<td>${plannerInfo.bank }</td>
			    		<th>계좌번호</th>
			    		<td>${plannerInfo.account }</td>
			    	</tr>
			    	<tr class="text-center">
			    		<th colspan="4">소개글</th>
			    	</tr>
			    	<tr class="text-center">
			    		<td colspan="4">${plannerInfo.introduce }</td>
			    	</tr>
			    </table>
			    
			    <h4 class="text-center mb-4">리워드 정보</h4>
   				<table class="table table-bordered mb-5">
			    	<tr class="text-center">
			    		<th>리워드 ID</th>
			    		<th>리워드 이름</th>
			    		<th>리워드 내용</th>
			    		<th>리워드 가격</th>
			    	</tr>
			    	<c:forEach var="reward" items="${rewardList}">
			    		<tr class="text-center">
				    		<td>${reward.reward_id }</td>
				    		<td>${reward.r_name }</td>
				    		<td>${reward.r_content }</td>
				    		<td>${reward.r_price}원</td>
				    	</tr>
			    	</c:forEach>
			    </table>
			    
			   
			    <div class="col-12 text-center mx-auto">
			    	<c:if test="${projectInfo.status eq 'unauthorized'}">
	    				<button class="btn btn-outline-primary py-1" type="button" id="authBtn" onclick="authorizeProject(${projectInfo.project_id});">프로젝트 승인</button>
	    				<button class="btn btn-outline-primary py-1" type="button" id="unauthBtn" onclick="unauthorizeProject(${projectInfo.project_id});">승인 거절</button>
	    			</c:if>
			    	<c:if test="${projectInfo.status eq 'ready' or projectInfo.status eq 'ongoing'}">
	    				<button class="btn btn-outline-primary py-1" type="button" id="cancelBtn" onclick="cancelProject(${projectInfo.project_id}, '${projectInfo.status }');">프로젝트 취소</button>
	    			</c:if>
	    			<br><br>
			    	<c:if test="${projectInfo.kind eq 'donate' }">
			    		<button class="btn btn-light" onclick="location.href='manageDonateProjectList.mngp?page=${page}'">프로젝트 목록</button>
			    	</c:if>
			    	<c:if test="${projectInfo.kind eq 'fund' }">
			    		<button class="btn btn-light" onclick="location.href='manageFundProjectList.mngppage=${page}'">프로젝트 목록</button>
			    	</c:if>			    	
			    </div>
   			</div>
   		</div>
   	</div>
    
	    
</body>
</html>