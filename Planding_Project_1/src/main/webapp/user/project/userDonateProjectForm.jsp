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
    
</head>


<body>
	
	<div>
		<jsp:include page="../userHeader.jsp" />
	</div>
	
	<div class="container-xxl mt-5 py-5">
   		<div class="container col-12 col-md-10">
   			<div class="mb-3">
   				<i class="fas fa-heart mx-2"></i></i><strong>프로젝트 후원하기</strong>
   			</div>
   			<div class="row justify-content-center">
   				<div class="col-12 col-lg-6">
   					<div class="bg-light rounded p-4 animated fadeIn" data-wow-delay="0.1s">                        
                        <div class="row g-3">
                            <div class="col-12">
                            	<div class="bg-white rounded">
                                    <p class="p-3" style="line-height:2rem">
                                    	<b><i class="far fa-file-powerpoint me-2 text-primary"></i>결식아동에게 밥 한끼를 선물해주세요.</b><br>
                                    	&nbsp;1565400원 | 달성률 : 87% | 6일 남음<br>
                                    </p>
                                </div>
                                <div class="bg-white rounded">
                                    <p class="p-3" style="line-height:2rem">
                                    	<b><i class="fas fa-gift me-2 text-primary"></i>리워드 : 밥을 잘 챙겨먹자 (A구성)</b><br>
                                    	&nbsp;숟가락 1개 + 젓가락 1개<br>
                                    	&nbsp;리워드 금액 : 10000원<br> 
                                    	&nbsp;추가 후원금액 : 10000원<br> 
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
   				</div>
   				
   				<div class="col-12 col-md-6">
   					<div class="bg-light rounded p-4 animated fadeIn" data-wow-delay="0.1s">                        
                        <div class="row g-3">
                            <div class="col-12">
                            	<div class="bg-white rounded">
                                    <p class="p-3" style="line-height:2rem">
                                    	<b><span class="text-danger me-3">최종 후원금액</span> 15000원</b><br>
                                    </p>
                                </div>
                                <div class="bg-white rounded p-2">
                                    <div class="form-check">
	                                    <input type="checkbox" name="agree" value="agree" class="form-check-input border-1" id="agree">
	                                    <label class="form-check-label" for="agree">개인정보 제 3자 제공 동의</label> <a href="#">내용보기</a>
	                                </div>
                                </div>
                                <div class="bg-white rounded">
                                    <p class="p-3" style="line-height:2rem">
                                    	<b>추가 후원금액</b><br>
                                    	&nbsp;추가 후원금액 : 15000원<br> 
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
   				</div>
   			</div>
   		</div>
   	</div>
   	
   	<div>
		<jsp:include page="../userFooter.jsp" />
	</div>
   	
   	
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