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
	
	document.f.submit();
	
}
</script>

<body>
	
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid py-4 mb-5 wow fadeIn" data-wow-delay="0.1s" style="background-color:#86B381">
        <div class="container text-center py-5">
            <h3 class="display-6 py-3 text-white animated slideInDown">프로젝트 진행 취소 및 삭제</h3>
        </div>
    </div>
    <!-- Page Header End -->


    <!-- Form Start -->
    <div class="container-fluid pt-4 pb-5">
        <div class="container">
            <form action="cancelProjectAction.mngp" method="post" name="f">
	            <div class="row justify-content-center">
	                <div class="col-12 col-md-10 col-lg-8">
	                    <div class="bg-light rounded p-4 animated fadeIn" data-wow-delay="0.1s">                        
	                        <div class="row g-3">
	                            <div class="col-12">
                                    <p class="p-3">
                                    	<span class="fw-bold">&nbsp;프로젝트 취소 시 유의사항을 확인해주세요.</span><br><br>
                                    	&nbsp;검토 중 또는 공개예정이었던 프로젝트는 삭제되며 진행 중이던 프로젝트는 즉시 중단됩니다.<br>
                                    	&nbsp;진행 중인 프로젝트의 후원은 환불처리되며, 관련된 리워드 등 모든 프로젝트 관련 정보가 삭제됩니다.
                                    	&nbsp;프로젝트 취소는 번복할 수 없으므로, 신중하게 판단해주세요.
                                    </p>	              
                                    <div class="px-4 pb-3">                      
	                                    <div class="form-check">
		                                    <input type="checkbox" name="confirm" value="confirmed" class="form-check-input border-1" id="confirm">
		                                    <label class="form-check-label" for="confirm">취소 시 유의사항을 모두 확인했습니다.</label>
		                                </div>
	                                </div>
	                                <input type="hidden" name="project_id" value="${project_id}">
	                                <input type="hidden" name="status" value="${status}">
	                            </div>
	                            <div class="col-12 text-center">
	                                <button class="btn btn-primary py-2 px-4" type="submit" onclick="checkDeleteForm();">프로젝트 진행 취소하기</button>
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