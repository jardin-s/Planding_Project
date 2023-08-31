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
<body>
	
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid page-header pt-5 mb-5 wow fadeIn" data-wow-delay="0.1s">
        <div class="container text-center pt-5">
            <h3 class="display-6 text-white animated slideInDown">${sessionScope.u_name}님의 페이지</h3>
            <div class="row justify-content-center">
	            <ul class="col-12 col-lg-8 nav nav-pills justify-content-center mt-4 mb-0">
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userMyPage.usr">내 정보관리</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="bookmarkList.usr">관심 프로젝트</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link active fw-bold" aria-current="page" href="#">등록 프로젝트</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="fundProjectList.usr">후원 프로젝트</a></li>
	            </ul>
            </div>
        </div>
    </div>
    <!-- Page Header End -->


    <!-- Service Start -->
    <div class="container-xxl py-5">
        <div class="container">
            <div class="text-center mx-auto animated fadeIn" data-wow-delay="0.1s" style="max-width: 500px;">
                <p class="fw-bold text-dark mb-5 pb-5" style="font-size:2rem">관심 프로젝트 리스트</p>
            </div>
            <div class="row g-4">
                <div class="col-lg-4 col-md-6 animated fadeIn" data-wow-delay="0.1s">
                    <div class="service-item rounded d-flex h-100">
                        <div class="service-img rounded">
                            <img class="img-fluid" src="../resources/img/service-1.jpg" alt="">
                        </div>
                        <div class="service-text rounded p-5">
                            <h4 class="mb-3">카테고리 (기부 - 환경)</h4>
                            <p class="mb-4">프로젝트명 얼마나 길지 모르니까 여기에</p>
                            <a class="btn btn-sm" href=""><i class="fa fa-plus text-primary me-2"></i>보러가기</a>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6 animated fadeIn" data-wow-delay="0.3s">
                    <div class="service-item rounded d-flex h-100">
                        <div class="service-img rounded">
                            <img class="img-fluid" src="../resources/img/service-2.jpg" alt="">
                        </div>
                        <div class="service-text rounded p-5">
                            <h4 class="mb-3">카테고리 (기부 - 동물)</h4>
                            <p class="mb-4">프로젝트명 얼마나 길지 모르니까 여기에</p>
                            <a class="btn btn-sm" href=""><i class="fa fa-plus text-primary me-2"></i>보러가기</a>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6 animated fadeIn" data-wow-delay="0.5s">
                    <div class="service-item rounded d-flex h-100">
                        <div class="service-img rounded">
                            <img class="img-fluid" src="../resources/img/service-3.jpg" alt="">
                        </div>
                        <div class="service-text rounded p-5">
                            <h4 class="mb-3">카테고리 (펀딩 - 취약계층)</h4>
                            <p class="mb-4">프로젝트명 얼마나 길지 모르니까 여기에.</p>
                            <a class="btn btn-sm" href=""><i class="fa fa-plus text-primary me-2"></i>보러가기</a>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6 animated fadeIn" data-wow-delay="0.1s">
                    <div class="service-item rounded d-flex h-100">
                        <div class="service-img rounded">
                            <img class="img-fluid" src="../resources/img/service-4.jpg" alt="">
                        </div>
                        <div class="service-text rounded p-5">
                            <h4 class="mb-3">Garden Maintenance </h4>
                            <p class="mb-4">프로젝트명 얼마나 길지 모르니까 여기에</p>
                            <a class="btn btn-sm" href=""><i class="fa fa-plus text-primary me-2"></i>보러가기</a>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6 animated fadeIn" data-wow-delay="0.3s">
                    <div class="service-item rounded d-flex h-100">
                        <div class="service-img rounded">
                            <img class="img-fluid" src="../resources/img/service-5.jpg" alt="">
                        </div>
                        <div class="service-text rounded p-5">
                           <h4 class="mb-3">Green Technology</h4>
                            <p class="mb-4">프로젝트명 얼마나 길지 모르니까 여기에</p>
                            <a class="btn btn-sm" href=""><i class="fa fa-plus text-primary me-2"></i>보러가기</a>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6 animated fadeIn" data-wow-delay="0.5s">
                    <div class="service-item rounded d-flex h-100">
                        <div class="service-img rounded">
                            <img class="img-fluid" src="../resources/img/service-6.jpg" alt="">
                        </div>
                        <div class="service-text rounded p-5">
                           <h4 class="mb-3">Urban Gardening</h4>
                            <p class="mb-4">프로젝트명 얼마나 길지 모르니까 여기에</p>
                            <a class="btn btn-sm" href=""><i class="fa fa-plus text-primary me-2"></i>보러가기</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Service End -->
	
    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
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