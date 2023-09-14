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
    
    <!-- Custom Stylesheet -->
    <link href="../resources/css/customStyle.css" rel="stylesheet">
</head>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
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
function checkUpateForm(){
	
	//아이디와 비밀번호 값 데이터 정규화 공식
	const regId = /^[a-zA-Z0-9]{7,19}$/;
	if(!document.f.member_id.value.trim()){
		alert("아이디를 입력해주세요.");
		document.f.member_id.focus();
		return false;
	}else if(!regId.test(document.f.member_id.value.trim())){
		alert("아이디를 8~20자 사이의 영문과 숫자로 입력해주세요.");
		return false;
	}
	/*
	else if( document.f.idDuplication.value == 'idUncheck'){
		alert("아이디 중복체크를 해주세요.");
		document.f.idck.focus();
		return false;
	}
	*/
	
	//이름 정규화 공식
	const regName = /^[가-힣a-zA-Z]{2,}$/;
	if(!document.f.name.value.trim()){
		alert("이름을 입력해주세요.");
		document.f.name.focus();
		return false;
	}else if(!regIdPass.test(document.f.password.value.trim())){
		alert("이름을 한글 또는 영문으로만 입력해주세요.");
		return false;		
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
	
	if(!document.f.postcode.value.trim()){
		alert("우편번호를 입력해주세요.");
		document.f.postcode.focus();
		return false;
	}
	if(!document.f.address1.value.trim()){
		alert("주소를 입력해주세요.");
		document.f.address1.focus();
		return false;
	}
	if(!document.f.address2.value.trim()){
		alert("상세주소를 입력해주세요.");
		document.f.address2.focus();
		return false;
	}
	
	
	document.f.submit();
		
	/*
	 postcode(우편번호)와 address1은 "DAUM api" 검색한 내용으로 자동세팅되어 생략 
 	 */
	
	
}
</script>

<body>
	
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid page-header pt-4 pb-5 mb-5 wow fadeIn" data-wow-delay="0.1s">
        <div class="container text-center py-5">
            <h3 class="display-6 text-white mb-4 animated slideInDown">관리자 정보 수정</h3>
        </div>
    </div>
    <!-- Page Header End -->


    <!-- Form Start -->
    <div class="container-fluid pt-4 pb-5">
        <div class="container">
            <form action="adminUpdateAction.adm" method="post" name="f">
	            <div class="row justify-content-center">
	                <div class="col-md-9 col-lg-7">
	                    <div class="bg-light rounded p-4 px-sm-5 py-sm-3 animated fadeIn" data-wow-delay="0.1s">                        
	                        <div class="row g-5 pt-5">
	                            <div class="mb-3 row g-3 justify-content-center">
								    <label for="member_id" class="col-3 col-form-label text-center">아이디</label>
								    <div class="col-9">
								      <input type="text" class="form-control" name="member_id" id="member_id" value="${admin.member_id }" readonly>
								    </div>
	                            </div>
	                            <div class="mb-3 row gx-3 justify-content-center">
								    <label for="name" class="col-3 col-form-label text-center">이름</label>
								    <div class="col-9">
								      <input type="text" class="form-control" name="name" id="name" value="${admin.name }" placeholder="한글 또는 영문만 입력">
								    </div>
	                            </div>
	                            <div class="mb-3 row gx-3 justify-content-center">
								    <label for="email" class="col-3 col-form-label text-center">이메일</label>
								    <div class="col-9">
								      <input type="text" class="form-control" name="email" id="email" value="${admin.email }" placeholder="example@example.com">
								    </div>
	                            </div>
	                            <div class="mb-3 row gx-3 justify-content-center">
								    <label for="phone" class="col-3 col-form-label text-center">전화번호</label>
								    <div class="col-9">
								      <input type="text" class="form-control" name="phone" id="phone" value="${admin.phone }" maxlength="11" placeholder="(-)없이 숫자만 입력">
								    </div>
	                            </div>
	                            
	                            <div class="mb-3 row g-3 justify-content-center">
								    <label for="postcode" class="col-3 col-form-label text-center">우편번호</label>
								    <div class="col-6 me-0 pe-0">
								      <input type="text" class="form-control" name="postcode" id="postcode" value="${addr.postcode}" max-length="20" placeholder="우편번호만 입력" required>
								    </div>
		                            <div class="col-3 text-end">
		                                <input type="button" class="btn btn-primary" type="button" name="addck" id="addck" value="번호검색" onclick="findAddr();" required>                                
		                            </div>
	                            </div>
	                            <div class="mb-3 row gx-3 justify-content-center">
								    <label for="address1" class="col-3 col-form-label text-center">주소</label>
								    <div class="col-9">
								      <input type="text" class="form-control" name="address1" id="address1" value="${addr.address1}" maxlength="11" placeholder="주소">
								    </div>
	                            </div>
	                            <div class="mb-3 row gx-3 justify-content-center">
								    <label for="address2" class="col-3 col-form-label text-center">상세주소</label>
								    <div class="col-9">
								      <input type="text" class="form-control" name="address2" id="address2" value="${addr.address2}" maxlength="11" placeholder="직접 입력">
								    </div>
	                            </div>
	                            <!-- 주소 ID -->
	                            <input type="hidden" class="form-control" name="address_id" id="address_id" value="${addr.address_id}">
	                            <!-- 사용자 가상계좌 -->
	                            <div class="mb-3 row gx-3 justify-content-center">
								    <label for="account" class="col-3 col-form-label text-center">잔여금액</label>
								    <div class="col-9">
								      <input type="text" class="form-control" name="account" id="account" value="${admin.account }" readonly>
								    </div>
	                            </div>
	                            <div class="col-12 text-center">
	                                <button class="btn btn-primary py-2 px-4" type="submit" onclick="checkUpdateForm(); return false;">수정하기</button>
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