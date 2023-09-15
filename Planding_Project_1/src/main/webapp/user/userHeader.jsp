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
</head>

<script>
function userLogout(){
	if(confirm('로그아웃하시겠습니까?')){
		location.href="userLogout.usr";	
	}
}
</script>

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
                    	<a class="btn btn-link text-light" href="userBookmarkList.usr"><i class="far fa-heart"></i></a>
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
                    <a href="#" class="nav-link fs-6 dropdown-toggle" data-bs-toggle="dropdown">고객센터</a>
                    <div class="dropdown-menu bg-light m-0">
                        <a href="about.html" class="dropdown-item fs-6">사이트소개</a>
                        <a href="userNoticeList.usr" class="dropdown-item fs-6">공지사항</a>
                        <a href="userQnaList.usr" class="dropdown-item fs-6">문의사항</a>
                    </div>
                </div>
                <a href="insertNewProject.pj" class="nav-item nav-link fs-6 fw-bold d-lg-none d-xl-none d-xxl-none">프로젝트 등록하기</a>
            </div>
            <a href="insertNewProject.pj" class="btn btn-primary py-4 px-lg-4 rounded-0 d-none d-lg-block">프로젝트 등록하기<i class="fa fa-arrow-right ms-3"></i></a>
            
        </div>
    </nav>
    <!-- Navbar End -->
    
    
    
</body>
</html>