<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid page-header py-5 mb-5 wow fadeIn" data-wow-delay="0.1s">
        <div class="container text-center py-5">
            <h1 class="display-3 text-white mb-4 animated slideInDown">회원가입</h1>
            <nav aria-label="breadcrumb animated slideInDown">
                <ol class="breadcrumb justify-content-center mb-0">
                    <li class="breadcrumb-item"><a href="userLogin.usr">로그인</a></li>
                    <li class="breadcrumb-item"><a href="userIdFindForm.usr">아이디 찾기</a></li>
                    <li class="breadcrumb-item"><a href="userHashPwFindForm.usr">비밀번호 찾기</a></li>
                    <li class="breadcrumb-item active" aria-current="page">회원가입</li>                    
                </ol>
            </nav>
        </div>
    </div>
    <!-- Page Header End -->


    <!-- Form Start -->
    <div class="container-fluid py-5">
        <div class="container">
            <div class="text-center mx-auto wow fadeInUp" data-wow-delay="0.1s" style="max-width: 500px;">
                <p class="fs-5 fw-bold text-primary">회원가입하기</p>
            </div>
            <form action="userJoinAction.usr" method="post">
	            <div class="row justify-content-center">
	                <div class="col-lg-7">
	                    <div class="bg-light rounded p-4 p-sm-5 wow fadeInUp" data-wow-delay="0.1s">                        
	                        <div class="row g-5 pt-5">
	                            <div class="mb-3 row">
								    <label for="member_id" class="col-sm-3 col-form-label">아이디</label>
								    <div class="col-sm-6">
								      <input type="text" class="form-control" name="member_id" id="member_id" max-length="20" placeholder="8~20자 영문숫자 조합">
								    </div>
		                            <div class="col-sm-3">
		                                <button class="btn btn-primary" type="button" name="idck" id="idck" onclick="window.open('idCheck/idCheck.jsp?openInit=true','아이디중복확인','top=10, left=10, width=500, height=300')">
		                                	<span style="font-size:0.9rem">중복체크</span>
		                                </button>	                                
		                            </div>
	                            </div>
	                            <div class="mb-3 row">
								    <label for="password" class="col-sm-3 col-form-label">비밀번호</label>
								    <div class="col-sm-9">
								      <input type="password" class="form-control" name="password" id="password" max-length="20" placeholder="8~20자 영문숫자특수문자 조합">
								    </div>
	                            </div>
	                            <div class="mb-3 row">
								    <label for="confirm_password" class="col-sm-3 col-form-label"><span style="font-size:0.9rem">비밀번호 확인</span></label>
								    <div class="col-sm-9">
								      <input type="confirm_password" class="form-control" name="confirm_password" id="confirm_password" max-length="20" placeholder="위 비밀번호와 동일하게 입력">
								    </div>
	                            </div>
	                            <div class="mb-3 row">
								    <label for="name" class="col-sm-3 col-form-label">이름</label>
								    <div class="col-sm-9">
								      <input type="text" class="form-control" name="name" id="name" placeholder="한글 또는 영문만 입력">
								    </div>
	                            </div>
	                            <div class="mb-3 row">
								    <label for="email" class="col-sm-3 col-form-label">이메일</label>
								    <div class="col-sm-9">
								      <input type="text" class="form-control" name="email" id="email" placeholder="example@example.com">
								    </div>
	                            </div>
	                            <input type="hidden" class="form-control" name="isAdmin" id="isAdmin" value="false" placeholder="example@example.com">								    
	                                                        
	                            <div class="col-12 text-center">
	                                <button class="btn btn-primary py-3 px-4" type="submit" onclick="check(); return false;">가입하기</button>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </div>
            </form>
        </div>
    </div>
    <!-- Form End -->
    
	
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