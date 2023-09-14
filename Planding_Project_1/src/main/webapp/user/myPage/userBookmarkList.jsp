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
    
    <!-- Custom Stylesheet -->
    <link href="../../resources/css/customStyle.css" rel="stylesheet">
    
    <style>
    .nav-pills > .nav-item > .active {
		background-color: #E8F5E9 !important; 
		color: #348E38 !important;
	}
    </style>
</head>

<script type="text/javascript">
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

//선택한 항목 삭제
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
		document.f.submit();
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
					<li class="col-6 col-md-3 nav-item"><a class="nav-link active fw-bold" aria-current="page" href="#">관심 프로젝트</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userUploadProjectList.usr">등록 프로젝트</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userDonatedProjectList.usr">후원 프로젝트</a></li>
	            </ul>
            </div>
        </div>
    </div>
    <!-- Page Header End -->


	<c:if test="${bookmarkList eq null }">
    	<div class="container-xxl py-5">
    		<div class="container col-lg-8 mb-5">
   				<p class="text-center">관심 프로젝트가 없습니다.</p>
    		</div>
    	</div>
    </c:if>
	
	<c:if test="${bookmarkList ne null}">
    	
		<!-- Edit Button Start -->
		<form action="userBookmarkDelete.usr" method="post" name="f">
			
			<div class="container-fluid mt-0 pt-0 pb-5">
		    	<div class="container col-lg-8 px-0">
		    		<div class="d-flex justify-content-end">
		    			<button class="btn btn-outline-primary float-right me-2 py-1 d-none" type="submit" id="deleteBtn" onclick="selectDelete(); return false;">선택삭제</button>
		    			<button class="btn btn-outline-primary float-right py-1" type="button" id="editBtn" onclick="switchEditCancel();">편집</button>	    			
		    		</div>
		    	</div>
		   	</div>
			<!-- Edit Button End -->
			
		    <!-- List Start -->
		    <div class="container-fluid pt-0 pb-2">
		        <div class="container col-lg-8">
		            <div class="row justify-content-center" style="height:300px; overflow-y:auto">
						<table class="table table-hover">
							<thead>
								<tr class="text-center">
									<th scope="col" class="remove-th d-none text-center"><input class="form-check-input" type="checkbox" name="allCheck" onclick="checkAll(this.form);"></th>
									<th scope="col" class="col-1 text-center">#</th>
									<th scope="col" class="col-auto">프로젝트 제목</th>
								</tr>
							</thead>
							<tbody class="table-group-divider">
								<c:forEach var="bookmark" items="${requestScope.bookmarkList }" varStatus="status">
									<tr class="text-center">
										<th class="remove-th d-none">
											<input class="form-check-input" type="checkbox" name="remove" value="${bookmark.product_id}">
										</th>
										<th scope="row" class="text-center">${status.count}</th>
										<td>
											<a href="projectView.pj?project_id=${bookmark.product_id}">${bookmark.title}</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
		        </div>		
		    </div>
		    <%-- Table End --%>
		    
		</form>
	</c:if>
    <!-- List End -->
	
</body>
</html>