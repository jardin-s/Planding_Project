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
	const checkElements = document.getElementsByClassName('form-check');
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
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="uploadProjectList.usr">등록 프로젝트</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="fundProjectList.usr">후원 프로젝트</a></li>
	            </ul>
            </div>
        </div>
    </div>
    <!-- Page Header End -->

	
	<!-- Edit Button Start -->
	<form action="bookmarkDelete.usr" method="post" name="f">
		<div class="container-xxl py-0">
			<div class="container">
				<div class="d-grid gap-2 d-md-flex justify-content-md-end">
					<button class="btn btn-white border-secondary" type="button" id="editBtn" onClick="switchEditCancel();">편집</button>
					<button class="btn btn-white border-secondary d-none" type="button" id="deleteBtn" onClick="selectDelete();">선택삭제</button>
				</div>
			</div>
		</div>
		<!-- Edit Button End -->
		
	    <!-- List Start -->
	    <div class="container-xxl py-5">
	        <div class="container">
				<div class="row g-4">
					<div class="col-lg-4 col-md-6 wow fadeInUp" data-wow-delay="0.1s">
						<div class="form-check d-none">
						  <input class="form-check-input" name="project_id" type="checkbox" value="${bookmark.project_id }" id="project_id">
						</div>
						<div class="service-item rounded d-flex h-100">
							<div class="service-img rounded">
								<img class="img-fluid" src="../../resources/img/service-1.jpg" alt="">
							</div>
							<div class="service-text rounded p-5">
								<h4 class="mb-3">프로젝트 제목</h4>
								<p class="mb-4">이 글은 프로젝트에 대한 대략적인 소개글로서 요약글이라고 할 수 있습니다.</p>
								<a class="btn btn-sm" href="projectView.prj?project_id=${bookmark.project_id}"><i class="fa fa-plus text-primary me-2"></i>보러가기</a>
							</div>
	                    </div>
	                </div>
	                <div class="col-lg-4 col-md-6 wow fadeInUp" data-wow-delay="0.1s">
						<div class="form-check d-none">
						  <input class="form-check-input" name="project_id" type="checkbox" value="${bookmark.project_id }" id="project_id">
						</div>
						<div class="service-item rounded d-flex h-100">
							<div class="service-img rounded">
								<img class="img-fluid" src="../../resources/img/service-1.jpg" alt="">
							</div>
							<div class="service-text rounded p-5">
								<h4 class="mb-3">프로젝트 제목</h4>
								<p class="mb-4">이 글은 프로젝트에 대한 대략적인 소개글로서 요약글이라고 할 수 있습니다.</p>
								<a class="btn btn-sm" href="projectView.prj?project_id=${bookmark.project_id}"><i class="fa fa-plus text-primary me-2"></i>보러가기</a>
							</div>
	                    </div>
	                </div>
	                <div class="col-lg-4 col-md-6 wow fadeInUp" data-wow-delay="0.1s">
						<div class="form-check d-none">
						  <input class="form-check-input" name="project_id" type="checkbox" value="${bookmark.project_id }" id="project_id">
						</div>
						<div class="service-item rounded d-flex h-100">
							<div class="service-img rounded">
								<img class="img-fluid" src="../../resources/img/service-1.jpg" alt="">
							</div>
							<div class="service-text rounded p-5">
								<h4 class="mb-3">프로젝트 제목</h4>
								<p class="mb-4">이 글은 프로젝트에 대한 대략적인 소개글로서 요약글이라고 할 수 있습니다.</p>
								<a class="btn btn-sm" href="projectView.prj?project_id=${bookmark.project_id}"><i class="fa fa-plus text-primary me-2"></i>보러가기</a>
							</div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
    </form>   
    <!-- List End -->
    
   
	
    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="../../resources/lib/wow/wow.min.js"></script>
    <script src="../../resources/lib/easing/easing.min.js"></script>
    <script src="../../resources/lib/waypoints/waypoints.min.js"></script>
    <script src="../../resources/lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="../../resources/lib/counterup/counterup.min.js"></script>
    <script src="../../resources/lib/parallax/parallax.min.js"></script>
    <script src="../../resources/lib/isotope/isotope.pkgd.min.js"></script>
    <script src="../../resources/lib/lightbox/js/lightbox.min.js"></script>

    <!-- Template Javascript -->
    <script src="../../resources/js/main.js"></script>
</body>



</html>