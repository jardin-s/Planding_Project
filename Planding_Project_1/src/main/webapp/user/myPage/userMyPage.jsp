<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <title>PlanDing - Fund for Our Planet</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Favicon -->
    <link href="resources/img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Jost:wght@500;600;700&family=Open+Sans:wght@400;500&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Gothic+A1:100,400,500,700,800" rel="stylesheet">  

    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="resources/lib/animate/animate.min.css" rel="stylesheet">
    <link href="resources/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="resources/lib/lightbox/css/lightbox.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="resources/css/style.css" rel="stylesheet">
    
    <!-- Custom Stylesheet -->
    <link href="resources/css/customStyle.css" rel="stylesheet">
    
    <style>
    .nav-pills > .nav-item > .active {
		background-color: #E8F5E9 !important; 
		color: #348E38 !important;
	}
    </style>
</head>

<script>
function topUpOpen(member_id){
	var popupX = (document.body.offsetWidth/2) - (500/2); 
	var popupY = (window.screen.height/2) - (420/2);
	
	window.open('user/myPage/topUp/topUp.jsp?member_id='+member_id,'금액 충전하기','top='+popupY+', left='+popupX+', width=500, height=420');
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
					<li class="col-6 col-md-3 nav-item"><a class="nav-link active fw-bold" aria-current="page" href="#">내 정보관리</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userBookmarkList.usr">관심 프로젝트</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userUploadProjectList.usr">등록 프로젝트</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userDonationHistory.usr">내 후원내역</a></li>
	            </ul>
            </div>
        </div>
    </div>
    <!-- Page Header End -->


    <!-- 404 Start -->
    <div class="container-xxl py-5 animated fadeIn" data-wow-delay="0.1s">
        <div class="container text-center">
            <div class="row justify-content-center">
            	<div class="col-lg-4">
            		<img class="img-thumbnail" src="resources/img/mypage_sample.jpg" style="width:15rem">
            	</div>
                <div class="col-md-8 col-lg-4 py-5 py-lg-0">
                    <div class="rounded py-3 px-5 mb-5 text-center" style="background-color:#86B381">
                    	<div class="row justify-content-center">
                    		<div class="col-6">
		                    	<span class="fs-6 text-white">잔여금액</span><br>
		                    	<form name="f">
	                    			<input type="text" class="form-control fw-bold text-center text-dark py-0 mx-auto" name="money" value="${userMoney}" style="width:100px" readonly/>
			                    </form>
	                    	</div>
	                    	<div class="col-6 my-auto">
	                    		<div class="col mb-1">
	                    			<button type="button" class="btn btn-light fw-bold px-md-0 px-lg-2 py-0 m-0" id="topUpBtn" onclick="topUpOpen('${sessionScope.u_id}');">충전하기</button>
	                    		</div>
	                    	</div>
                    	</div>                    	
                    </div>
                    <ul class="list-group list-group-flush">
					  <li class="list-group-item"><a href="userUpdateForm.usr">회원정보 수정</a></li>
					  <li class="list-group-item"><a href="userHashPwChangeForm.usr?u_id=${u_id}">비밀번호 변경</a></li>
					  <li class="list-group-item"><a href="userMyQnaList.usr">나의 문의글 리스트 보기</a></li>
					  <li class="list-group-item"><a href="userDeleteForm.usr">회원 탈퇴</a></li>
					</ul>           
                </div>
            </div>
        </div>
    </div>
    <!-- 404 End -->
	
    
</body>
</html>