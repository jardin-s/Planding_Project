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

</script>

<body>
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid py-4 mb-5 wow fadeIn" data-wow-delay="0.1s" style="background-color:#86B381">
        <div class="container text-center pt-5">
            <h3 class="display-6 py-3 text-white animated slideInDown">회원상세</h3>
        </div>
    </div>
    <!-- Page Header End -->
    
    
    <div class="container-xxl mb-5 py-5">
   		<div class="container col-12 col-md-8 col-lg-6">
   			<div class="col-12 mb-5">
   				<h4 class="text-center mb-4">${memberInfo.member_id }의 상세정보</h4>
   				<table class="table table-bordered mb-5">
			    	<tr>
			    		<th class="text-center">아이디</th>
			    		<td>${memberInfo.member_id }</td>
			    		<th class="text-center">이름</th>
			    		<td>${memberInfo.name }</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">이메일</th>
			    		<td>${memberInfo.email }</td>
			    		<th class="text-center">전화번호</th>
			    		<td>${memberInfo.phone }</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">가입일</th>
			    		<td>${memberInfo.joindate }</td>
			    		<th class="text-center">계좌잔액</th>
			    		<td>${memberInfo.account }원</td>
			    	</tr>
			    </table>
			    
			    <h4 class="text-center mb-4">등록된 배송지</h4>
   				<table class="table table-bordered mb-5">
			    	<tr class="text-center">
			    		<th>우편번호</th>
			    		<th>주소</th>
			    		<th>상세주소</th>
			    		<th>수령인</th>
			    		<th>전화번호</th>
			    	</tr>
			    	<c:forEach var="address" items="${addressList }">
			    		<tr class="text-center">
				    		<td>${address.postcode }</td>
				    		<td>${address.address1}</td>
				    		<td>${address.address2}</td>
				    		<td>${address.receiver_name}</td>
				    		<td>${address.receiver_phone}</td>
				    	</tr>
			    	</c:forEach>
			    </table>
			    
			    <h4 class="text-center mb-4">후원기록</h4>
   				<table class="table table-bordered mb-5">
			    	<tr class="text-center">
			    		<th>프로젝트 ID</th>
			    		<th>리워드 ID</th>
			    		<th>총 후원금액</th>
			    		<th>후원일자</th>
			    	</tr>
			    	<c:forEach var="donation" items="${donationList }">
			    		<tr class="text-center">
				    		<td>${donation.project_id}</td>
				    		<td>${donation.reward_id }</td>
				    		<td>${donation.totalDonation}원</td>
				    		<td>${donation.donatedate}</td>
				    	</tr>
			    	</c:forEach>
			    </table>
			    
			    <h4 class="text-center mb-4">작성한 문의글</h4>
   				<table class="table table-bordered">
			    	<tr class="text-center">
			    		<th>문의글 ID</th>
			    		<th>문의글 제목</th>
			    		<th>답변등록 여부</th>
			    		<th>작성일자</th>
			    	</tr>
			    	<c:forEach var="qna" items="${qnaList }">
			    		<tr class="text-center">
				    		<td>${qna.qna_id}</td>
				    		<td>${qna.q_title }</td>
				    		<td>
				    			<c:if test="${qna.a_writer ne null }">답변완료</c:if>
				    			<c:if test="${qna.a_writer eq null }">
				    				<button class="btn btn-outline-primary py-0" type="button" id="answerBtn" onclick="location.href='adminInsertQnaAForm.adm?qna_id=${qna.qna_id}&page=1'">답변하기</button>
				    			</c:if>
				    		</td>
				    		<td>${qna.q_time}</td>
				    	</tr>
			    	</c:forEach>
			    </table>
			    
			    <div class="col-12 text-center mt-5 mx-auto">
			    	<button class="btn btn-light" onclick="location.href='manageMemberList.mngm'">회원 목록</button>
			    	<button class="btn btn-light" onclick="location.href='deleteMember.mngm?member_id=${memberInfo.member_id}'">회원 삭제</button>			    	
			    </div>
   			</div>
   		</div>
   	</div>
    
	    
	    
    
</body>
</html>