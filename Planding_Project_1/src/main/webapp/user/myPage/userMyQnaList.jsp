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
    <link href="../resources/img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Jost:wght@500;600;700&family=Open+Sans:wght@400;500&display=swap" rel="stylesheet">  

    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="../resources/lib/animate/animate.min.css" rel="stylesheet">
    <link href="../resources/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="../resources/lib/lightbox/css/lightbox.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="../resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="../resources/css/style.css" rel="stylesheet">
    
    <!-- Custom Stylesheet -->
    <link href="../resources/css/customStyle.css" rel="stylesheet">
    
    <style>
    .nav-pills > .nav-item > .active {
		background-color: #E8F5E9 !important; 
		color: #348E38 !important;
	}
    </style>
</head>

<script type="text/javascript">

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
	
	if(editBtnText == "편집"){
		editBtn.innerText = "취소";
	}else if(editBtnText == "취소"){
		editBtn.innerText = "편집";
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
		
		if(confirm('정말로 해당 문의글들을 삭제하시겠습니까?')){
			document.dlt.submit();
		}else{
			alert('문의글 삭제를 취소합니다.');
			return false;
		}		
	}
	
	
}
</script>


<body>
	
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid page-header pt-5 mb-5 wow fadeIn" data-wow-delay="0.1s">
        <div class="container text-center pt-5">
            <h3 class="display-6 text-white animated slideInDown">${sessionScope.u_name}님의 페이지</h3>
            <div class="row justify-content-center">
	            <ul class="col-12 col-lg-8 nav nav-pills justify-content-center mt-4 mb-0">
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userMyPage.usr">내 정보관리</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userDonationHistory.usr">내 후원내역</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link active fw-bold" aria-current="page" href="#">내 문의글</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userUploadProjectList.usr">등록 프로젝트</a></li>
	            </ul>
            </div>
        </div>
    </div>
    <!-- Page Header End -->


    <c:if test="${pageInfo.listCount == 0 }">
    	<div class="container-xxl py-5">
    		<div class="container col-10 col-md-6 col-lg-4">
    			<div class="col-12 mb-5">
    				<p class="text-center">작성한 문의글이 없습니다.</p>
    			</div>
    		</div>
    	</div>
    </c:if>
    
    <c:if test="${pageInfo.listCount != 0 }">
    	<c:set var="q_index" value="${pageInfo.listCount - (pageInfo.page-1)*10 }" />
	    
	    <%-- Table Start --%>
	    <form action="userDeleteQnaList.usr" method="post" name="dlt">
		    <div class="container-fluid pt-0 pb-2">
		        <div class="container col-lg-8">
		            <div class="row justify-content-center">
						<table class="table table-hover">
							<thead>
								<tr>
									<th scope="col" class="remove-th d-none text-center"><input class="form-check-input" type="checkbox" name="allCheck" onclick="checkAll(this.form);"></th>
									<th scope="col" class="col-1 text-center">#</th>
									<th scope="col" class="col-7">제목</th>
									<th scope="col" class="col-2">날짜</th>
									<th scope="col" class="col-2">답변</th>
								</tr>
							</thead>
							<tbody class="table-group-divider">
								<c:forEach var="qna" items="${qnaList}">
									<tr>
										<th class="remove-th d-none">
											<input class="form-check-input" type="checkbox" name="remove" value="${qna.qna_id}">
										</th>
										<th scope="row" class="text-center">${q_index}</th>
										<td><a href="userMyQnaView.usr?qna_id=${qna.qna_id}">${qna.q_title }</a>
											<c:if test="${qna.q_private eq 'Y'}">
												<i class="fas fa-lock ms-1"></i>
											</c:if>
										</td>
										<td>${qna.q_time }</td>
										<td>
											<c:if test="${qna.a_writer eq null }">미답변</c:if>
											<c:if test="${qna.a_writer ne null }">답변완료</c:if>
										</td>
									</tr>
									<c:set var="q_index" value="${q_index -1 }"/>
								</c:forEach>
							</tbody>
						</table>
					</div>
		        </div>		
		    </div>
		    <%-- Table End --%>
		    
		     <%-- Delete Button --%>
		    <div class="container-fluid mt-0 pt-0 pb-5">
		    	<div class="container col-lg-8 px-0">
		    		<div class="d-flex justify-content-end">
		    			<button class="btn btn-outline-primary float-right me-2 py-1 d-none" type="submit" id="deleteBtn" onclick="selectDelete(); return false;">선택삭제</button>
		    			<button class="btn btn-outline-primary float-right py-1" type="button" id="editBtn" onclick="switchEditCancel();">편집</button>	    			
		    		</div>
		    	</div>
		   	</div>
	   	</form>
	    
	    
	    <%-- Pagination Start --%>
	    <div class="container-fluid mt-0 pt-0 pb-5">
	    	<div class="container col-lg-4">
		    	<div class="row">
					<ul class="pagination justify-content-center">
						<%-- 이전버튼 --%>
						<li class="page-item">
							<c:if test="${pageInfo.page <= 1}">
								<a class="page-link" aria-label="Previous">
									<span aria-hidden="true">&laquo;</span>
								</a>	
							</c:if>
							<c:if test="${pageInfo.page > 1}">
								<a class="page-link" href="userMyQnaList.usr?page=${pageInfo.page -1 }" aria-label="Previous">
									<span aria-hidden="true">&laquo;</span>
								</a>	
							</c:if>								
						</li>
						
						<%-- 1~10까지 --%>
						<c:forEach var="pNum" begin="${pageInfo.startPage }" end="${pageInfo.endPage}" step="1">
							<c:if test="${pNum eq pageInfo.page }">
								<li class="page-item active" aria-current="page"><a class="page-link">${pNum}</a></li>
							</c:if>
							<c:if test="${pNum ne pageInfo.page }">
								<li class="page-item"><a class="page-link" href="userMyQnaList.usr?page=${pNum}">${pNum}</a></li>
							</c:if>
						</c:forEach>
						
						<%-- 다음버튼 --%>
						<li class="page-item">
							<c:if test="${pageInfo.page >= pageInfo.maxPage }">
								<a class="page-link" aria-label="Next">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</c:if>
							<c:if test="${pageInfo.page < pageInfo.maxPage }">
								<a class="page-link" href="userMyQnaList.usr?page=${pageInfo.page +1 }" aria-label="Next">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</c:if>							
						</li>
					</ul>
		        </div>
	        </div>
	    </div>
	    <%-- Pagination Start --%>       
    
    </c:if>
	
</body>
</html>