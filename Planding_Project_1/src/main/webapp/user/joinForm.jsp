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

<script type="text/javascript">
function findAddr(){
	//카카오 지도 발생 -> 주소 입력 후 [검색] -> 찾는 주소 [선택] -> 우편번호와 주소 세팅
	//postcode 객체 생성하여 바로 open
	new daum.Postcode({
		oncomplete : function(data) {//[선택] 시 입력값 세팅 (검색결과 중 선택한 주소)
			console.log(data);
			//alert(data);//테스트를 위해 값이 뜨도록 함
			
			document.getElementById("postcode").value = data.zonecode; //우편번호를 가져와 postcode에 넣음
			
			let roadAddr = data.roadAddress;//도로명 주소
			let jibunAddr = data.jibunAddress;//지번 주소
			
			if(roadAddr != ''){//도로명 주소가 있으면 도로명 주소를 등록 ('': 자바스크립트 널)
				document.getElementById("address1").value = roadAddr;	
			}else if(jibunAddr != ''){//도로명 주소가 없고 지번 주소만 있으면 지번주소를 등록
				document.getElementById("address1").value = jibunAddr;
			}
			
			document.getElementById("address2").focus();//상세주소 입력창에 커서를 깜빡거리게 함
			
		}
	}).open();
}

function check(){
	

	//아이디와 비밀번호 값 데이터 정규화 공식
	const regIdPass = /^[a-zA-Z0-9]{7,19}$/;
	if(!document.f.id.value.trim()){
		alert("아이디를 입력해주세요.");
		document.f.id.focus();
		return false;
	}else if(!regIdPass.test(document.f.id.value.trim())){
		alert("아이디를 8~20자 사이의 영문과 숫자로 입력해주세요.");
		
	}
	/*
	else if( document.f.idDuplication.value == 'idUncheck'){
		alert("아이디 중복체크를 해주세요.");
		document.f.idck.focus();
		return false;
	}
	*/

	if(!document.f.password.value.trim()){
		alert("비밀번호를 입력해주세요.");
		document.f.password.focus();
		return false;
	}else if(!regIdPass.test(document.f.password.value.trim())){
		alert("비밀번호를 8~20자 사이의 영문과 숫자로 입력해주세요.");
		
	}	
	if(document.f.password.value != document.f.check_pw.value) {
	       alert("비밀번호가 다릅니다. 다시 확인해 주세요.");
	       f.check_pw.value = "";
	       f.password.focus();
	       return false;
	}
	
	//이름 정규화 공식
	const regName = /^[가-힣a-zA-Z]{2,}$/;
	if(!document.f.name.value.trim()){
		alert("이름을 입력해주세요.");
		document.f.name.focus();
		return false;
	}else if(!regIdPass.test(document.f.password.value.trim())){
		alert("이름을 한글 또는 영문으로만 입력해주세요.");
		
	}

	//email 정규화 공식
	const regEmail = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	if(!document.f.email.value.trim()){
		alert("이메일 주소를 입력해주세요.");
		document.f.email.focus();
		return false;
	}else if(!regEmail.test(document.f.email.value.trim())){
		alert("유효하지 않은 이메일주소 입니다.");
		document.f.email.select();
		return false;
	}
	
	//휴대번호 정규화 공식
	const regPhone = /^\d{3}\d{3,4}\d{4}$/; //-제외
	if(!document.f.phone.value.trim()){
		alert("휴대전화번호를 입력해주세요.");
		document.f.phone.focus();
		return false;
	}else if(!regPhone.test(document.f.phone.value.trim())){
		alert("휴대전화번호를 (-)없이 숫자만 입력해주세요.");
		document.f.phone.select();
		return false;
	}
	
	
	document.f.submit();
	/*
	 postcode(우편번호)와 address1은 "DAUM api" 검색한 내용으로 자동세팅되어 생략 
 	 */
	
	
}

function idCheckOpen(){
	if(document.f.id.value == ''){
		window.open('idCheck/idCheck.jsp','아이디중복확인','top=10, left=10, width=500, height=300');
	}else{
		window.open('idCheck/idCheck.jsp?id='+document.f.id.value,'아이디중복확인','top=10, left=10, width=500, height=300');
	}
}
</script>

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
		                                <button class="btn btn-primary" type="button" name="idck" id="idck" onclick="idCheckOpen();">
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