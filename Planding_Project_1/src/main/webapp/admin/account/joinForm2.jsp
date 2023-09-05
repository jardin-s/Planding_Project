<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html class="h-100">
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
</head>
<body class="d-flex align-items-center h-100" data-wow-delay="0.1s">
	
	<div class="container-fluid pt-4 pb-5 animated fadeIn">
        <div class="container">
            <form action="adminLoginAction.adm" method="post">
	            <div class="row justify-content-center">
	                <div class="col-md-9 col-lg-7">
	                	
	                	<h4 class="text-primary mb-4 text-center">관리자 등록</h4>
	                	 
	                    <div class="bg-light rounded p-4 px-sm-5 py-sm-3">                        
	                        <div class="row g-5 pt-5">
	                            
	                            <div class="mb-3 row g-3 justify-content-center">
								    <label for="member_id" class="col-3 col-form-label text-center">아이디</label>
								    <div class="col-6 me-0 pe-0">
								      <input type="text" class="form-control" name="member_id" id="member_id" max-length="20" placeholder="8~20자 영문숫자 조합">
								    </div>
		                            <div class="col-3 text-end">
		                                <button class="btn btn-primary" type="button" name="idck" id="idck" onclick="idCheckOpen();">
		                                	<span style="font-size:0.9rem">중복체크</span>
		                                </button>	                                
		                            </div>
		                            <input type="hidden" name="idChecked" value="unchecked" id="idChecked" placeholder="아이디중복체크 여부">
	                            </div>
	                            <div class="mb-3 row gx-3 justify-content-center">
								    <label for="password" class="col-3 col-form-label text-center">비밀번호</label>
								    <div class="col-9">
								      <input type="password" class="form-control" name="password" id="password" max-length="20" placeholder="8~20자 영문숫자 조합">
								    </div>
	                            </div>
	                            <div class="mb-3 row gx-3 justify-content-center">
								    <label for="confirm_password" class="col-3 col-form-label text-center"><span style="font-size:1rem; word-break: keep-all">비밀번호 확인</span></label>
								    <div class="col-9">
								      <input type="password" class="form-control" name="confirm_password" id="confirm_password" max-length="20" placeholder="위 비밀번호와 동일하게 입력">
								    </div>
	                            </div>
	                            <div class="mb-3 row gx-3 justify-content-center">
								    <label for="name" class="col-3 col-form-label text-center">이름</label>
								    <div class="col-9">
								      <input type="text" class="form-control" name="name" id="name" placeholder="한글 또는 영문만 입력">
								    </div>
	                            </div>
	                            <div class="mb-3 row gx-3 justify-content-center">
								    <label for="email" class="col-3 col-form-label text-center">이메일</label>
								    <div class="col-9">
								      <input type="text" class="form-control" name="email" id="email" placeholder="example@example.com">
								    </div>
	                            </div>
	                            <div class="mb-3 row gx-3 justify-content-center">
								    <label for="phone" class="col-3 col-form-label text-center">전화번호</label>
								    <div class="col-9">
								      <input type="text" class="form-control" name="phone" id="phone" maxlength="11" placeholder="(-)없이 숫자만 입력">
								    </div>
	                            </div>
	                            <!-- 사용자 가상계좌 -->
	                            <input type="hidden" class="form-control" name="account" id="account" value="0">
	                            <!-- 관리자 여부 : false -->
	                            <input type="hidden" class="form-control" name="isAdmin" id="isAdmin" value="true">								    
	                                                        
	                            <div class="col-12 text-center">
	                                <button class="btn btn-primary py-2 px-4" type="submit" onclick="checkJoinForm(); return false;">등록하기</button>
	                            </div>
	                            
	                        </div>
	                    </div>
	                    
	                    <ul class="nav justify-content-center mt-3">
						  <li class="nav-item">
						    <a class="nav-link text-secondary" href="loginForm.jsp">로그인 하기</a>
						  </li>
						  <li class="nav-item">
						    <a class="nav-link text-secondary" href="adminIdFindForm.adm">아이디 찾기</a>
						  </li>
						  <li class="nav-item">
						    <a class="nav-link text-secondary" href="adminHashPwFindForm.adm">비밀번호 찾기</a>
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