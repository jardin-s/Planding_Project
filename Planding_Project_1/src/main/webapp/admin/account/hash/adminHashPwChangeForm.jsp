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
	<!-- Page Header Start -->
    <div class="container-fluid page-header pt-4 pb-5 mb-5 wow fadeIn" data-wow-delay="0.1s">
        <div class="container text-center py-5">
            <h3 class="display-6 text-white mb-4 animated slideInDown">비밀번호 변경</h3>
        </div>
    </div>
    <!-- Page Header End -->


    <!-- Form Start -->
    <div class="container-fluid pt-4 pb-5">
        <div class="container">
            <form action="adminHashPwChangeAction.adm" method="post">
	            <div class="row justify-content-center">
	                <div class="col-8 col-md-6 col-lg-4">
	                    <div class="bg-light rounded p-4 px-sm-5 py-sm-3 animated fadeIn" data-wow-delay="0.1s">                        
	                        <div class="row g-3">
	                            <input type="hidden" name="member_id" value="${param.u_id}">
	                            <div class="col-12">
	                                <div class="form-floating">
	                                    <input type="password" name="pre_password" class="form-control border-0" id="pre_password" placeholder="기존 비밀번호">
	                                    <label for="pre_password">기존 비밀번호 입력</label>
	                                </div>
	                            </div>
	                            <div class="col-12">
	                                <div class="form-floating">
	                                    <input type="password" name="new_password" class="form-control border-0" id="new_password" placeholder="새 비밀번호">
	                                    <label for="new_password">새 비밀번호 입력</label>
	                                </div>
	                            </div>
	                            <div class="col-12">
	                                <div class="form-floating">
	                                    <input type="password" name="confirm_password" class="form-control border-0" id="new_password" placeholder="새 비밀번호와 동일하게 입력">
	                                    <label for="confirm_password">새 비밀번호 확인</label>
	                                </div>
	                            </div>
	                            <div class="col-12 text-center mt-4">
	                                <button class="btn btn-primary py-2 px-4" type="submit" onclick="pwCheck(); return false;">비밀번호 변경</button>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </div>
            </form>
        </div>
    </div>
    <!-- Form End -->
	    
</body>
</html>