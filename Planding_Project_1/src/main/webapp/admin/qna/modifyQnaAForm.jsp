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

function qnaFormCheck(){
		
	if(document.f.q_content == ''){
		alert('내용을 입력해주세요.')
		document.f.q_content.focus();
		return false;
	}
	
	document.f.submit();
	
}

</script>

<body>
	
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid page-header py-5 mb-5 wow fadeIn" data-wow-delay="0.1s">
        <div class="container text-center py-5">
            <h3 class="display-6 text-white mb-5 animated slideInDown">문의사항</h3>
            <nav aria-label="breadcrumb animated slideInDown">
                <ol class="breadcrumb justify-content-center mb-0">
                    <li class="breadcrumb-item"><a href="#">메인</a></li>
                    <li class="breadcrumb-item"><a href="#">고객센터</a></li>
                    <li class="breadcrumb-item active" aria-current="page">문의사항</li>
                </ol>
            </nav>
        </div>
    </div>
    <!-- Page Header End -->

    <!-- Table Start -->
    <div class="container-fluid py-4 mb-5">
        <div class="container col-md-8 col-lg-7">
            <div class="row justify-content-center">
				<h4 class="mb-3">${qnaInfo.q_title }</h4>
				<hr>
				<div class="mb-3">
					<span>${qnaInfo.member_id }</span> | <span>${qnaInfo.q_time }</span>
				</div>
				<hr>
                <p class="mb-3">
                	${qnaInfo.q_content }
                </p>
                <c:if test="${qnaInfo.q_image ne null }">
                	<hr>
	                <div>
	                	<%-- <img src="qna/images/${qnaInfo.q_image}"> --%>
	                	<span>첨부파일 : ${qnaInfo.q_image}</span>	                	
	                </div>
	                <hr>
                </c:if>
                <hr class="mb-5">
                
                <form action="adminModifyQnaAAction.adm" method="post" name="f">
					<input type="hidden" name="page" value="${page}">
					<input type="hidden" name="qna_id" value="${qnaInfo.qna_id}">
					<div class="input-group mb-2">
						<textarea name="a_content" class="form-control" aria-label="a_content" rows="10">${qnaInfo.a_content}</textarea>
					</div>
					<div class="col-12 text-center">
						<button type="submit" class="btn btn-primary" onclick="qnaFormCheck(); return false;">답변 수정</button>
					</div>
				</form>
			</div>
        </div>		
    </div>   
    <!-- Table End -->    
		
	    
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
</body>
</html>