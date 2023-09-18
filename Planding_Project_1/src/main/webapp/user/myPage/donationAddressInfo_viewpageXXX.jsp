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
<body>
	
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid page-header pt-5 mb-5 wow fadeIn" data-wow-delay="0.1s">
        <div class="container text-center pt-5">
            <h3 class="display-6 text-white animated slideInDown">${sessionScope.u_name}님의 페이지</h3>
            <div class="row justify-content-center">
	            <ul class="col-12 col-lg-8 nav nav-pills justify-content-center mt-4 mb-0">
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userMyPage.usr">내 정보관리</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userBookmarkList.usr">관심 프로젝트</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link active fw-bold" aria-current="page" href="#">등록 프로젝트</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userDonatedProjectList.usr">후원 프로젝트</a></li>
	            </ul>
            </div>
        </div>
    </div>
    <!-- Page Header End -->
    	
    	<div class="container-xxl py-5">
    		<div class="container col-lg-8 mb-5">
   				<h3 class="text-center">(reward_id)reward_name<br>후원목록</h3>
    		</div>
    	</div>
    	
    	
    	<!-- Table Start  반복문 -->
	    <div class="container-fluid pt-0 pb-2">
	        <div class="container col-lg-8">
	            <div class="row justify-content-center" style="height:auto; overflow-y:auto">
					<table class="table table-bordered">
							<caption class="text-left" style="caption-side: top;">후원번호</caption>
						<thead>
							<tr class="text-left">
								<th scope="col" class="col-2">후원자ID</th>
								<th scope="col" class="col-3">받는 분 이름</th>
								<th scope="col" class="col-auto">받는 분 연락처</th>
							</tr>
						</thead>
						<tbody class="table-group-divider">
								
								
							<tr class="text-left">
								<td>후원자ID</td>
								<td>receiver_name</td>
								<td>receiver_phone</td>

							</tr>
							<tr class="text-left" style="border-top: 2px solid #A9A9A9;">
								<td>우편번호<br>(postcode)</td>
								<td>주소 : address1</td>
								<td>상세주소 : address2</td>
							</tr>
							
						</tbody>
					</table>
				</div>
	        </div>		
	    </div>
	    <%-- Table End --%>
	    
	    
	<!-- JavaScript Libraries -->
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
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