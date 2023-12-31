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
<body>
	
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid page-header py-5 mb-5 wow fadeIn" data-wow-delay="0.1s">
        <div class="container text-center pt-5 pb-4">
            <h3 class="display-6 text-white animated slideInDown">${sessionScope.a_name} 관리자님의 페이지</h3>
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
                    <ul class="list-group list-group-flush">
					  <li class="list-group-item"><a href="adminUpdateForm.adm">관리자 정보 수정</a></li>
					  <li class="list-group-item"><a href="adminHashPwChangeForm.adm?a_id=${a_id}">비밀번호 변경</a></li>
					  <li class="list-group-item"><a href="adminDeleteForm.adm">관리자 계정 삭제</a></li>
					</ul>           
                </div>
            </div>
        </div>
    </div>
    <!-- 404 End -->
	
</body>
</html>