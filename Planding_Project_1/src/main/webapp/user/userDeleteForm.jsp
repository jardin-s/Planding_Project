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
</head>

<script>
function checkDeleteForm(){
	
	if(!document.f.confirm.checked){
		alert('탈퇴 전 유의사항을 읽고 확인란에 체크해주세요.');
		document.f.confirm.focus();
		return false;
	}
	else
	
	if(document.f.member_id == ''){
		alert('아이디를 입력해주세요.');
		document.f.member_id.focus();
		return false;
	}
	else
	
	if(document.f.password == ''){
		alert('비밀번호를 입력해 본인인증을 완료해주세요.');
		document.f.password.focus();
		return false;
	}
	
	document.f.submit();
	
}
</script>

<body>
	
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid page-header pt-4 pb-5 mb-5 wow fadeIn" data-wow-delay="0.1s">
        <div class="container text-center py-5">
            <h3 class="display-6 text-white mb-4 animated slideInDown">회원탈퇴</h3>
        </div>
    </div>
    <!-- Page Header End -->


    <!-- Form Start -->
    <div class="container-fluid pt-4 pb-5">
        <div class="container">
            <form action="userDeleteAction.usr" method="post">
	            <div class="row justify-content-center">
	                <div class="col-12 col-md-10 col-lg-8">
	                    <div class="bg-light rounded p-4 animated fadeIn" data-wow-delay="0.1s">                        
	                        <div class="row g-3">
	                            <div class="col-12">
	                                <div class="bg-white rounded">
	                                    <p class="p-3">
	                                    	<span class="fw-bold">&nbsp;서비스를 이용하는 데 불편함이 있었나요?</span><br><br>	                                    	
	                                    	&nbsp;다음 중 탈퇴 사유를 골라 사이트를 개선하는 데 도움을 주세요.
						                    <div class="col-12 col-lg-8 px-4 pb-3 pt-0">
							                    <select class="form-select" name="reason">
													<option value="" selected>--- 탈퇴 사유를 골라주세요. ---</option>
													<option value="1">원하는 프로젝트를 찾을 수 없음</option>
													<option value="2">프로젝트 등록 절차가 복잡함</option>
													<option value="3">프로젝트 후원 절차가 복잡함</option>
													<option value="4">개인정보를 삭제하고 싶음</option>
													<option value="5">직접입력</option>
												</select>
											</div>               	
										</p>
	                                </div>
	                                <div class="bg-white rounded">
	                                    <p class="p-3">
	                                    	<span class="fw-bold">&nbsp;탈퇴 전 유의사항을 확인해주세요.</span><br><br>	                                    	
	                                    	&nbsp;타인이 동일 아이디로 회원가입하여 부정이용을 하는 경우를 방지하기 위해 아이디를 포함한 개인정보는 6개월 뒤 삭제됩니다.<br><br>
	                                    	&nbsp;검토 중 또는 공개예정이었던 프로젝트는 삭제되며 진행 중이던 프로젝트는 즉시 중단됩니다.<br>
	                                    	&nbsp;이미 종료된 프로젝트는 삭제되지 않습니다.<br><br>
	                                    	&nbsp;종료된 프로젝트의 후원은 취소되지 않습니다. 리워드 배송을 위해 배송지 기록은 리워드 배송이 모두 끝나기 전까지 삭제되지 않습니다.<br>
	                                    	&nbsp;진행 중인 프로젝트의 후원은 환불처리됩니다. (카드사에 따라 최대 7일 정도 소요될 수 있습니다.)
	                                    </p>	              
	                                    <div class="px-4 pb-3">                      
		                                    <div class="form-check">
			                                    <input type="checkbox" name="confirm" value="confirmed" class="form-check-input border-1" id="confirm">
			                                    <label class="form-check-label" for="confirm">탈퇴 전 유의사항을 모두 확인했습니다.</label>
			                                </div>
		                                </div>
	                                </div>
	                                <div class="bg-white rounded mt-3">
	                                    <p class="px-3 pt-3">
	                                    	<span class="fw-bold">&nbsp;탈퇴를 위한 마지막 절차로 본인인증이 필요합니다.</span>             	
										</p>
										<div class="px-4 pt-2 pb-3">	                                    	
	                                    	<div class="col-12 col-lg-8 mb-2">
				                                <div class="form-floating">
				                                    <input type="text" name="member_id" value="${sessionScope.u_id }" class="form-control border-1" id="member_id" placeholder="아이디">
				                                    <label for="member_id">아이디</label>
				                                </div>
				                            </div>
				                            <div class="col-12 col-lg-8">
				                                <div class="form-floating">
				                                    <input type="password" name="password" class="form-control border-1" id="password" placeholder="비밀번호">
				                                    <label for="password">비밀번호</label>
				                                </div>
				                            </div> 
										</div>
	                                </div>
	                            </div>
	                            <div class="col-12 text-center">
	                                <button class="btn btn-primary py-2 px-4" type="submit" onclick="checkDeleteForm();">탈퇴하기</button>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </div>
            </form>
        </div>
    </div>
    <!-- Form End -->
		
	    
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