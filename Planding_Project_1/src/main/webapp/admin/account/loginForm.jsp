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
</head>
<body>
	
	<div class="container-fluid pt-4 pb-5 animated fadeIn" data-wow-delay="0.1s">
        <div class="container">
            <form action="adminLoginAction.adm" method="post" name="f">
	            <div class="row justify-content-center">
	                <div class="col-8 col-md-6 col-lg-5 col-xl-4">
	                	
	                	<h4 class="text-primary mb-4 text-center">관리자 로그인</h4>
	                	 
	                    <div class="bg-light rounded p-4">                  
	                        <div class="row g-3">
	                            <div class="col-12">
	                                <div class="form-floating">
	                                    <input type="text" name="member_id" value="" class="form-control border-0" id="member_id" placeholder="아이디">
	                                    <label for="member_id">아이디</label>
	                                </div>
	                            </div>
	                            <div class="col-12">
	                                <div class="form-floating">
	                                    <input type="password" name="password" class="form-control border-0" id="password" placeholder="아이디">
	                                    <label for="password">비밀번호</label>
	                                </div>
	                            </div>
	                            <div class="col-12">
	                                <div class="form-check">
	                                    <input type="checkbox" name="checkbox" value="checked" class="form-check-input border-0" id="checkbox">
	                                    <label class="form-check-label" for="checkbox">아이디 기억하기</label>
	                                </div>
	                            </div>
	                            <div class="col-12 text-center">
	                                <button class="btn btn-primary py-2 px-4" type="submit">로그인</button>
	                            </div>
	                        </div>
	                    </div>
	                    <ul class="nav justify-content-center mt-3">
						  <li class="nav-item">
						    <a class="nav-link text-secondary" href="adminIdFindForm.adm">아이디 찾기</a>
						  </li>
						  <li class="nav-item">
						    <a class="nav-link text-secondary" href="adminHashPwFindForm.adm">비밀번호 찾기</a>
						  </li>
						  <li class="nav-item">
						    <a class="nav-link text-secondary" href="adminJoin.adm">관리자 등록</a>
						  </li>
						  <li class="nav-item">
						    <a class="nav-link text-secondary" href="userMain.usr">돌아가기</a>
						  </li>
						</ul>
	                </div>
	            </div>
            </form>
        </div>
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

  </form>
</body>
</html>