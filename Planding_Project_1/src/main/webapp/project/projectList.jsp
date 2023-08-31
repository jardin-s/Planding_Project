<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <title>PlanDing - Fund for Our Plannet</title>
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
	<div>
		<jsp:include page="../userHeader.jsp" />
	</div>


    <!-- Projects Start -->
    <div class="container-xxl py-5">
        <div class="container">
            <div class="text-center mx-auto wow fadeInUp" data-wow-delay="0.1s" style="max-width: 500px;">
                <p class="fs-5 fw-bold text-primary">Our Projects</p>
                <h1 class="display-5 mb-5">Planding</h1>
            </div>
            <div class="row wow fadeInUp" data-wow-delay="0.3s">
                <div class="col-12 text-center">
                    <ul class="list-inline rounded mb-5" id="portfolio-flters">
                        <li class="mx-2 active" data-filter="*">All</li>
                        <li class="mx-2" data-filter=".nature">환경</li>
                        <li class="mx-2" data-filter=".animal">동물</li>
                        <li class="mx-2" data-filter=".human">사람</li>
                        <li class="mx-2" data-filter=".finished">완료된 프로젝트</li>
                    </ul>
                </div>
            </div>
            
            
            <!-- 프로젝트 반복문으로 처리하기 -->
            
            <div class="row g-4 portfolio-container">
<%--                <c:forEach items="${projects}" var="project"> --%><!-- 기능 구현 시 블록해제 할 것 -->
				<div class="col-lg-4 col-md-6 portfolio-item ${project_category} wow fadeInUp ${project_status} " data-wow-delay="0.1s">
	                    <div class="portfolio-inner rounded">
		                   <img class="img-fluid" src="../resources/img/${project_thumbnail}" alt="${project_title}">
	                        <div class="portfolio-text">
	                            <h4 class="text-white mb-4">${project_title}</h4>
	                            <div class="d-flex">
	                                <a class="btn btn-lg-square rounded-circle mx-2" href="../resources/img/${project_thumbnail}" data-lightbox="portfolio"><i class="fa fa-eye"></i></a>
	                                <a class="btn btn-lg-square rounded-circle mx-2" href="projectView.jsp?id=${project_id }"><i class="fa fa-link"></i></a>
	                            </div>
	                        </div>
		                        <table class="table">
								  <thead>
								    <tr>
								      <th scope="col">${project_id}</th>
								      <th scope="col"><a href="projectView.jsp?id=${project_id }">${project_title}</a></th>
								    </tr>
								  </thead>
								  <tbody>
								    <tr>
								      <th scope="row">카테고리</th>
								      <td>${project_category}</td>
								    </tr>
								    <tr>
								      <th scope="row">${project_status}</th>
								      <td>${project_startdate}~${project_enddate}</td>
								    </tr>
								    <tr>
								      <th scope="row">모금액</th>
								      <td>${project_curr_amount}(${project_curr_amount/project_goal_amount}%)</td>
								    </tr>
								  </tbody>
								</table>
	                    </div>
	            	</div>
<%-- 	             </c:forEach> --%>
            </div>
            
            <!-- 프로젝트 end-->
            
            
        </div>
        
    </div>
    <!-- Projects End -->

	<div class="container text-center"><!-- 기능구현할때 건드릴 것 반복문 숫자를 결정함 url?start=1&end=10 이런식으로 10개씩보기 기능 구현할 것-->
	    <nav aria-label="Page navigation example">
			  <ul class="pagination">
			    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
			    <li class="page-item"><a class="page-link" href="#">1</a></li>
			    <li class="page-item"><a class="page-link" href="#">2</a></li>
			    <li class="page-item"><a class="page-link" href="#">3</a></li>
			    <li class="page-item"><a class="page-link" href="#">Next</a></li>
			  </ul>
		</nav>
	</div>
	
	
    <div>
		<jsp:include page="../userFooter.jsp" />
	</div>
    
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