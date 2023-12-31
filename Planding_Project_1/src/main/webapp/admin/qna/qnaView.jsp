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

<script>
function deleteQConfirm(page, qna_id){
	if(!confirm('문의글을 삭제하시겠습니까?')){
		return false;
	}else{
		location.href="adminDeleteQnaQAction.adm?page="+page+"&qna_id="+qna_id;
	}
}
function deleteAConfirm(page, qna_id, a_writer){
	if(!confirm('답변을 삭제하시겠습니까?')){
		return false;
	}else{
		location.href="adminDeleteQnaAAction.adm?page="+page+"&qna_id="+qna_id+"&a_writer="+a_writer;
	}
}
</script>

<body>
	
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid py-5 mb-5 wow fadeIn" data-wow-delay="0.1s" style="background-color:#86B381">
        <div class="container text-center py-5">
            <h3 class="display-6 text-white mb-4 animated slideInDown">문의사항</h3>
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
					<span>${qnaInfo.q_writer }</span> | <span>${qnaInfo.q_time }</span>
				</div>
				<hr>
                <p class="mb-3">
                	${qnaInfo.q_content }
                </p>
                <c:if test="${qnaInfo.q_image ne null }">
                	<hr>
	                <div>
	                	<img src="images/qna/${qnaInfo.q_image}" style="width:100%">	                	
	                </div>
	                <hr>
                </c:if>
                <hr class="mb-5">
                
                <c:if test="${qnaInfo.a_writer ne null}">
                	<div class="mb-3">
                		<span class="fw-bold fs-5">문의사항에 대한 답변입니다.</span> | <span>${qnaInfo.a_time}</span>
                	</div>
                	<hr>
                	<p>${qnaInfo.a_content }</p>
                	<hr class="mb-4">
                	<div class="col-12 text-center mx-auto">
                		<button class="btn btn-light" type="button" onclick="location.href='adminModifyQnaAForm.adm?page=${page}&qna_id=${qnaInfo.qna_id}&a_writer=${qnaInfo.a_writer }'">답변 수정</button>
                		<button class="btn btn-light" type="button" onclick="deleteAConfirm(${page},${qnaInfo.qna_id},'${qnaInfo.a_writer }');">답변 삭제</button>
						<button class="btn btn-light" type="button" onclick="deleteQConfirm(${page},${qnaInfo.qna_id});">삭제</button>
                		<button class="btn btn-light" onclick="location.href='adminQnaList.adm?page=${page}'">글 목록</button>
					</div>
                </c:if>
                <c:if test="${qnaInfo.a_writer eq null}">
                	<div class="col-12">
	    				<p class="text-center">아직 답변이 작성되지 않았습니다.</p>
	    			</div>
	    			<div class="col-12 text-center mx-auto">
						<button class="btn btn-light" type="button" onclick="location.href='adminInsertQnaAForm.adm?page=${page}&qna_id=${qnaInfo.qna_id}'">답변 작성</button>
						<button class="btn btn-light" type="button" onclick="deleteQConfirm(${page},${qnaInfo.qna_id});">삭제</button>
                		<button class="btn btn-light" onclick="location.href='adminQnaList.adm?page=${page}'">글 목록</button>
					</div>
                </c:if>                
			</div>
        </div>		
    </div>   
    <!-- Table End -->   
		
</body>
</html>