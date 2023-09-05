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
    <link href="../../../resources/img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Jost:wght@500;600;700&family=Open+Sans:wght@400;500&display=swap" rel="stylesheet">  

    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="../../../resources/lib/animate/animate.min.css" rel="stylesheet">
    <link href="../../../resources/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="../../../resources/lib/lightbox/css/lightbox.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="../../../resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="../../../resources/css/style.css" rel="stylesheet">
</head>

<script type="text/javascript">

function pwCheck() { //비밀번호 정규화 체크
	const regPass = /^[a-zA-Z0-9]{7,19}$/;
	
	//변수 사용해서 코딩하면 효율적 : 길이 차이가 많이 날 때
	//let pre_password = document.f.pre_password;
	//let new_password = document.f.new_password;
	//let confirm_password = document.f.confirm_password;
	
	//document. 생략가능
	if(!document.f.pre_password.value.trim()){
		alert("기존 비밀번호를 입력해주세요.");
		document.f.pre_password.focus();
		return false;
	}
	else if(!regPass.test(document.f.pre_password.value.trim())) {
		alert("비밀번호는 8~12자의 영어 대소문자와 숫자로만 입력가능합니다.");
		document.f.pre_password.select();
		return false;
	}
	
	else
	
	if(document.f.new_password.value.trim() == ''){
		alert("새 비밀번호를 입력해주세요.");
		document.f.new_password.focus();
		return false;
	}
	else if(!regPass.test(document.f.new_password.value.trim())) {
		alert("비밀번호는 8~20자의 영어 대소문자와 숫자로만 입력가능합니다.");
		document.f.new_password.select();
		return false;
	}
	
	else
	
	if(document.f.confirm_password.value.trim() == ''){
		alert("새 비밀번호와 동일하게 다시 입력해주세요.");
		document.f.confirm_password.focus();
		return false;
	}
	else if(!regPass.test(document.f.confirm_password.value.trim())) {
		alert("비밀번호는 8~20자의 영어 대소문자와 숫자로만 입력가능합니다.");
		document.f.confirm_password.select();
		return false;
	}
	
	else
	
		//이전 비밀번호와 새 비밀번호가 일치하면 알림창
	if(document.f.pre_password.value.trim() == document.f.new_password.value.trim()){
		alert("기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
		document.f.confirm_password.select();
		return false;
	}
	
	else
		//새 비밀번호와 새 비밀번호 확인이 일치하지 않으면 알림창
	if(document.f.new_password.value.trim() != document.f.confirm_password.value.trim()){
		alert("변경할 새 비밀번호와 비밀번호 확인이 일치하지 않습니다. 다시 확인해주세요.");
		document.f.confirm_password.select();
		return false;
	}
	
	//위 조건이 거짓이면
	document.f.submit();
}

</script>

<body>
	
	<!-- Main Section -->
	
    <!-- Form Start -->
    <div class="container-fluid pt-4 pb-5 animated fadeIn" data-wow-delay="0.1s">
        <div class="container">
            <form action="adminHashPwFindAction.adm" method="f">
	            <div class="row justify-content-center">
	                <div class="col-8 col-md-6 col-lg-5">
	                	
	                	<h4 class="text-primary mb-4 text-center">비밀번호 찾기</h4>
	                		                
	                    <div class="bg-light rounded p-4 animated fadeIn" data-wow-delay="0.1s">                        
	                        <div class="row g-3">
	                            <div class="col-12">
	                                <div class="form-floating">
	                                    <input type="text" name="member_id" class="form-control border-0" id="member_id" placeholder="아이디">
	                                    <label for="member_id">아이디 입력</label>
	                                </div>
	                            </div>
	                            <div class="col-12">
	                                <div class="form-floating">
	                                    <input type="text" name="email" class="form-control border-0" id="email" placeholder="이메일">
	                                    <label for="email">이메일 입력</label>
	                                </div>
	                            </div>
	                            <div class="col-12 text-center mt-4">
	                                <button class="btn btn-primary py-2 px-4" type="submit" onclick="check(); return false;">확인</button>
	                            </div>
	                        </div>
	                    </div>
	                    
	                    <ul class="nav justify-content-center mt-3 animated fadeIn">
                           	<li class="nav-item">
								<a class="nav-link text-secondary" href="adminLoginForm.adm">로그인 하기</a>
							</li>
                           	<li class="nav-item">
								<a class="nav-link text-secondary" href="adminIdFindForm.adm">아이디 찾기</a>
							</li>
							<li class="nav-item">
								<a class="nav-link text-secondary" href="adminJoin.adm">관리자 등록</a>
							</li>
						</ul>
						
	                </div>
	            </div>
            </form>
        </div>
    </div>
    <!-- Form End -->
	    
    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="../../../resources/lib/wow/wow.min.js"></script>
    <script src="../../../resources/lib/easing/easing.min.js"></script>
    <script src="../../../resources/lib/waypoints/waypoints.min.js"></script>
    <script src="../../../resources/lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="../../../resources/lib/counterup/counterup.min.js"></script>
    <script src="../../../resources/lib/parallax/parallax.min.js"></script>
    <script src="../../../resources/lib/isotope/isotope.pkgd.min.js"></script>
    <script src="../../../resources/lib/lightbox/js/lightbox.min.js"></script>

    <!-- Template Javascript -->
    <script src="../../../resources/js/main.js"></script>
</body>
</html>