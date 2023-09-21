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
<body>
	
	<!-- Form Start -->
    <div class="container-fluid pt-4 pb-5 animated fadeIn" data-wow-delay="0.1s">
        <div class="container">
            <form action="adminIdFindAction.adm" method="post" name="f">
	            <div class="row justify-content-center">
	                <div class="col-8 col-md-6 col-lg-5">
	                	
	                	<h4 class="text-primary mb-4 text-center">아이디 찾기</h4>
	                
	                    <div class="bg-light rounded p-4 animated fadeIn" data-wow-delay="0.1s">                        
	                        <div class="row g-3">
	                            <div class="col-12">
	                                <div class="form-floating">
	                                    <input type="text" name="email" class="form-control border-0" id="email" placeholder="이메일">
	                                    <label for="email">가입 시 등록한 이메일 입력</label>
	                                </div>
	                            </div>
	                            <div class="col-12 text-center mt-4">
	                                <button class="btn btn-primary py-2 px-4" type="submit" onclick="mailCheck(); return false;">확인</button>
	                            </div>
	                        </div>
	                    </div>
	                    
	                    <ul class="nav justify-content-center mt-3 animated fadeIn">
                           	<li class="nav-item">
								<a class="nav-link text-secondary" href="adminLoginForm.adm">로그인 하기</a>
							</li>
                           	<li class="nav-item">
								<a class="nav-link text-secondary" href="adminHashPwFindForm.adm">비밀번호 찾기</a>
							</li>
							<li class="nav-item">
								<a class="nav-link text-secondary" href="adminJoin.adm">회원가입</a>
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
    <!-- Form End -->
	
</body>
</html>