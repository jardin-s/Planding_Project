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
</head>

<body>

	<!-- Spinner Start -->
    <div id="spinner" class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
        <div class="spinner-border text-primary" role="status" style="width: 3rem; height: 3rem;"></div>
    </div>
    <!-- Spinner End -->


    <!-- Topbar Start -->
    <div class="container-fluid bg-dark text-light px-0 py-2">
        <div class="row gx-0 d-lg-flex">
            <div class="col-lg-7 px-5 text-start d-none d-lg-block">
                <div class="h-100 d-inline-flex align-items-center">
                    <c:if test="${sessionScope.u_id ne null }">
                    	<span>${u_name}님 환영합니다.</span>
                    </c:if> 
                </div>
            </div>
            <div class="col-lg-5 px-3 px-md-4 px-lg-5 text-end">
                <div class="h-100 d-inline-flex align-items-center mx-n3">
                    <c:if test="${sessionScope.u_id eq null }">
                    	<a class="btn btn-link text-light" href="userLoginForm.usr">로그인하기</a>	
                    </c:if>
                    <c:if test="${sessionScope.u_id ne null }">
                    	<a class="btn btn-link text-light" href="#" onclick="userLogout();">로그아웃하기</a>
                    	<a class="btn btn-link text-light" href="userMyPage.usr"><i class="fas fa-user"></i></a>
                    	<a class="btn btn-link text-light" href=""><i class="far fa-heart"></i></a>
                    </c:if>                    
                    <a class="btn btn-link text-light" href=""><i class="fas fa-search"></i></a>
                </div>
            </div>
        </div>
    </div>
    <!-- Topbar End -->


    <!-- Navbar Start -->
    <nav class="navbar navbar-expand-lg bg-white navbar-light sticky-top p-0">
        <a href="userMain.usr" class="navbar-brand d-flex align-items-center px-4 px-lg-5">
            <h1 class="m-0">PlanDing</h1>
        </a>
        <button type="button" class="navbar-toggler me-4" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <div class="navbar-nav ms-auto p-4 p-lg-0">
                <a href="service.html" class="nav-item nav-link fs-6">기부하기</a>
                <a href="project.html" class="nav-item nav-link fs-6">펀딩하기</a>
                <div class="nav-item dropdown">
                    <a href="#" class="nav-link fs-6 dropdown-toggle" data-bs-toggle="dropdown">모아보기</a>
                    <div class="dropdown-menu bg-light m-0">
                        <a href="feature.html" class="dropdown-item fs-6">신규 프로젝트</a>
                        <a href="quote.html" class="dropdown-item fs-6">마감임박 프로젝트</a>
                        <a href="team.html" class="dropdown-item fs-6">공개예정 프로젝트</a>
                    </div>
                </div>
                <div class="nav-item dropdown">
                    <a href="#" class="nav-link fs-6 dropdown-toggle" data-bs-toggle="dropdown">고객센터</a>
                    <div class="dropdown-menu bg-light m-0">
                        <a href="about.html" class="dropdown-item fs-6">사이트소개</a>
                        <a href="userNoticeList.usr" class="dropdown-item fs-6">공지사항</a>
                        <a href="userQnaList.usr" class="dropdown-item fs-6">문의사항</a>
                    </div>
                </div>
                <a href="registerNewProject.pj" class="nav-item nav-link fs-6 fw-bold d-lg-none d-xl-none d-xxl-none">프로젝트 등록하기</a>
            </div>
            <a href="registerNewProject.pj" class="btn btn-primary py-4 px-lg-4 rounded-0 d-none d-lg-block">프로젝트 등록하기<i class="fa fa-arrow-right ms-3"></i></a>
            
        </div>
    </nav>
    <!-- Navbar End -->
    

	<!-- Carousel Start -->
    <div class="container-fluid p-0 wow fadeIn" data-wow-delay="0.1s">
        <div id="header-carousel" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img class="w-100" src="../resources/img/carousel-1.jpg" alt="Image">
                    <div class="carousel-caption">
                        <div class="container">
                            <div class="row justify-content-center">
                                <div class="col-lg-8">
                                    <h1 class="display-1 text-white mb-5 animated slideInDown">Make Your Home Like Garden</h1>
                                    <a href="" class="btn btn-primary py-sm-3 px-sm-4">Explore More</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="carousel-item">
                    <img class="w-100" src="../resources/img/carousel-2.jpg" alt="Image">
                    <div class="carousel-caption">
                        <div class="container">
                            <div class="row justify-content-center">
                                <div class="col-lg-7">
                                    <h1 class="display-1 text-white mb-5 animated slideInDown">Create Your Own Small Garden At Home</h1>
                                    <a href="" class="btn btn-primary py-sm-3 px-sm-4">Explore More</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#header-carousel"
                data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#header-carousel"
                data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </div>
    <!-- Carousel End -->

    <!-- Projects Start -->
    <div class="container-xxl py-5">
        <div class="container">
            <div class="text-center mx-auto wow fadeInUp" data-wow-delay="0.1s" style="max-width: 500px;">
                <p class="fs-5 fw-bold text-primary">신규 프로젝트</p>
            </div>
            <div class="row wow fadeInUp" data-wow-delay="0.3s">
                <div class="owl-carousel">
                	<div class="item">
                		<img class="img-fluid" src="../resources/img/service-5.jpg" alt="">
                        <div class="portfolio-text">
                            <h4 class="text-white mb-4">Green Technology</h4>
                        </div>
                	</div>
                	<div class="item">
                		<img class="img-fluid" src="../resources/img/service-5.jpg" alt="">
                        <div class="portfolio-text">
                            <h4 class="text-white mb-4">Green Technology</h4>
                        </div>
                	</div>
                	<div class="item">
                		<img class="img-fluid" src="../resources/img/service-5.jpg" alt="">
                        <div class="portfolio-text">
                            <h4 class="text-white mb-4">Green Technology</h4>
                        </div>
                	</div>
                	<div class="item">
                		<img class="img-fluid" src="../resources/img/service-5.jpg" alt="">
                        <div class="portfolio-text">
                            <h4 class="text-white mb-4">Green Technology</h4>
                        </div>
                	</div>
                	<div class="item">
                		<img class="img-fluid" src="../resources/img/service-5.jpg" alt="">
                        <div class="portfolio-text">
                            <h4 class="text-white mb-4">Green Technology</h4>
                        </div>
                	</div>
                </div>
            </div>
        </div>
    </div>
    <!-- Projects End -->
    
    
    <!-- Projects Start -->
    <div class="container-xxl py-5">
        <div class="container">
            <div class="text-center mx-auto wow fadeInUp" data-wow-delay="0.1s" style="max-width: 500px;">
                <p class="fs-5 fw-bold text-primary">마감임박 프로젝트</p>
            </div>
            <div class="row wow fadeInUp" data-wow-delay="0.3s">
                <div class="owl-carousel">
                	<div class="item">
                		<img class="img-fluid" src="../resources/img/service-5.jpg" alt="">
                        <div class="portfolio-text">
                            <h4 class="text-white mb-4">Green Technology</h4>
                        </div>
                	</div>
                	<div class="item">
                		<img class="img-fluid" src="../resources/img/service-5.jpg" alt="">
                        <div class="portfolio-text">
                            <h4 class="text-white mb-4">Green Technology</h4>
                        </div>
                	</div>
                	<div class="item">
                		<img class="img-fluid" src="../resources/img/service-5.jpg" alt="">
                        <div class="portfolio-text">
                            <h4 class="text-white mb-4">Green Technology</h4>
                        </div>
                	</div>
                	<div class="item">
                		<img class="img-fluid" src="../resources/img/service-5.jpg" alt="">
                        <div class="portfolio-text">
                            <h4 class="text-white mb-4">Green Technology</h4>
                        </div>
                	</div>
                	<div class="item">
                		<img class="img-fluid" src="../resources/img/service-5.jpg" alt="">
                        <div class="portfolio-text">
                            <h4 class="text-white mb-4">Green Technology</h4>
                        </div>
                	</div>
                </div>
            </div>
        </div>
    </div>
    <!-- Projects End -->
    
    <!-- Projects Start -->
    <div class="container-xxl py-5">
        <div class="container">
            <div class="text-center mx-auto wow fadeInUp" data-wow-delay="0.1s" style="max-width: 500px;">
                <p class="fs-5 fw-bold text-primary">공개예정 프로젝트</p>
            </div>
            <div class="row wow fadeInUp" data-wow-delay="0.3s">
                <div class="owl-carousel">
                	<div class="item">
                		<img class="img-fluid" src="../resources/img/service-5.jpg" alt="">
                        <div class="portfolio-text">
                            <h4 class="text-white mb-4">Green Technology</h4>
                        </div>
                	</div>
                	<div class="item">
                		<img class="img-fluid" src="../resources/img/service-5.jpg" alt="">
                        <div class="portfolio-text">
                            <h4 class="text-white mb-4">Green Technology</h4>
                        </div>
                	</div>
                	<div class="item">
                		<img class="img-fluid" src="../resources/img/service-5.jpg" alt="">
                        <div class="portfolio-text">
                            <h4 class="text-white mb-4">Green Technology</h4>
                        </div>
                	</div>
                	<div class="item">
                		<img class="img-fluid" src="../resources/img/service-5.jpg" alt="">
                        <div class="portfolio-text">
                            <h4 class="text-white mb-4">Green Technology</h4>
                        </div>
                	</div>
                	<div class="item">
                		<img class="img-fluid" src="../resources/img/service-5.jpg" alt="">
                        <div class="portfolio-text">
                            <h4 class="text-white mb-4">Green Technology</h4>
                        </div>
                	</div>
                </div>
            </div>
        </div>
    </div>
    <!-- Projects End -->
    
    <!-- Footer Start -->
    <div class="container-fluid bg-dark text-light footer mt-5 py-5 wow fadeIn" data-wow-delay="0.1s">
        <div class="container py-5">
            <div class="row g-5">
                <div class="col-lg-4 col-md-6">
                    <h4 class="text-white mb-4">PlanDing</h4>
                    <p class="mb-2"><i class="fa fa-map-marker-alt me-3"></i>대구광역시 달서구 달구벌대로251안길 15</p>
                    <p class="mb-2"><i class="fa fa-phone-alt me-3"></i>053-427-3313</p>
                    <p class="mb-2"><i class="fa fa-envelope me-3"></i>plandingproject@gmail.com</p>
                    <div class="d-flex pt-2">
                        <a class="btn btn-square btn-outline-light rounded-circle me-2" href=""><i class="fab fa-twitter"></i></a>
                        <a class="btn btn-square btn-outline-light rounded-circle me-2" href=""><i class="fab fa-facebook-f"></i></a>
                        <a class="btn btn-square btn-outline-light rounded-circle me-2" href=""><i class="fab fa-youtube"></i></a>
                        <a class="btn btn-square btn-outline-light rounded-circle me-2" href=""><i class="fab fa-linkedin-in"></i></a>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6">
                    <a class="btn btn-link" href="about.html">사이트 소개</a>
                    <a class="btn btn-link" href="">개인정보처리방침</a>
                    <a class="btn btn-link" href="">이용약관</a>
                    <a class="btn btn-link" href="">광고센터</a>
                    <a class="btn btn-link" href="">인재채용</a>
                </div>
                <div class="col-lg-4 col-md-6">
                    <span>대표이사 : 손정원, 이주헌</span>
                </div>
            </div>
        </div>
    </div>
    <!-- Footer End -->


    <!-- Copyright Start -->
    <div class="container-fluid copyright py-4">
        <div class="container">
            <div class="row">
                <div class="col-md-6 text-center text-md-start mb-3 mb-md-0">
                    &copy; <a class="border-bottom" href="#">PlanDing</a>, All Right Reserved.
                    <a class="btn btn-sm btn-dark ms-3 py-0 px-3 text-light" href="adminLoginForm.adm">관리자 페이지</a>
                </div>
                <div class="col-md-6 text-center text-md-end">
                    <!--/*** This template is free as long as you keep the footer author’s credit link/attribution link/backlink. If you'd like to use the template without the footer author’s credit link/attribution link/backlink, you can purchase the Credit Removal License from "https://htmlcodex.com/credit-removal". Thank you for your support. ***/-->
                    Designed By <a class="border-bottom" href="https://htmlcodex.com">HTML Codex</a> Distributed By <a href="https://themewagon.com">ThemeWagon</a>
                </div>
            </div>
        </div>
    </div>
    <!-- Copyright End -->


    <!-- Back to Top -->
    <a href="#" class="btn btn-lg btn-primary btn-lg-square rounded-circle back-to-top"><i class="bi bi-arrow-up"></i></a>
    


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
    
    <!-- owl-carousel -->
	<script>
	(function ($) {
		$('.owl-carousel').owlCarousel({
		    loop:false,
		    margin:10,
		    responsiveClass:true,
		    responsive:{
		        0:{
		            items:1,
		            nav:true
		        },
		        600:{
		            items:3,
		            nav:false
		        },
		        1000:{
		            items:5,
		            nav:true,
		            loop:false
		        }
		    }
		})    
	})(jQuery);
	</script>
    
</body>
</html>