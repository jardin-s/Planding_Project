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
function deleteConfirm(page, qna_id, member_id){
	if(!confirm('문의글을 삭제하시겠습니까?')){
		return false;
	}else{
		location.href="userDeleteQnaAction.qna?page="+page+"&qna_id="+qna_id+"&q_writer="+q_writer;
	}
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
    <div class="container-fluid pt-4 pb-4">
        <div class="container col-md-8 col-lg-7">
            <div class="row justify-content-center">
				<h4 class="mb-3">${qnaInfo.q_title}</h4>
				<hr>
				<div class="mb-3">
					<span>${qnaInfo.q_writer}</span> | <span>${qnaInfo.q_time}</span>
				</div>
				<hr>
                <p class="mb-3">
                	${qnaInfo.q_content}
                </p>
                <c:if test="${qnaInfo.q_image ne null }">
                	<hr>
	                <div>
	                	<img src="${pageContext.request.contextPath}/images/qna/${qnaInfo.q_image}">	                	
	                </div>
	                <hr>
                </c:if>
                <hr class="mb-5">
                <c:if test="${qnaInfo.a_content eq 'unanswered'}">
                	<div class="pt-3 pb-5">
                		<p>아직 등록된 답변이 없습니다.</p>
                	</div>
                </c:if>
                <c:if test="${qnaInfo.a_content ne 'unanswered'}">
                	<div class="mb-3">
                		<span class="fw-bold fs-5">문의사항에 대한 답변입니다.</span> | <span>답변일시</span>
                	</div>
                	<hr>
                	<p>답변 내용</p>
                	<hr class="mb-4">
                </c:if>
                
                <div class="col-12 text-center">
                	<c:if test="${sessionScope.u_id eq qna.member_id }">
						<button class="btn btn-light" type="button" onclick="location.href='userModifyQnaQForm.usr?qna_id=${qnaInfo.qna_id}&q_writer=${qnaInfo.q_writer}&page=${page}'">수정</button>
                		<button class="btn btn-light" type="button" onclick="deleteConfirm(${page},${qnaInfo.qna_id},'${qnaInfo.q_writer}');">삭제</button>
                	</c:if>
                	<button class="btn btn-light" onclick="location.href='userQnaList.usr?page=${page}'">글 목록</button>
                </div>
                
			</div>
        </div>		
    </div>   
    <!-- Table End -->   
		
	    
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