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
	
	//내용 데이터 유효성 검사
	if(document.f.a_content.value == ''){
		alert('답변을 입력해주세요.')
		document.f.a_content.focus();
		return false;
	}
	
	document.f.submit();
	
}

</script>

<body>
	
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid py-5 mb-5 wow fadeIn" data-wow-delay="0.1s" style="background-color:#86B381">
        <div class="container text-center pt-5">
            <h3 class="display-6 text-white animated slideInDown">문의사항</h3>
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
	                	<%-- <img src="qna/images/${qnaInfo.q_image}"> --%>
	                	<span>첨부파일 : ${qnaInfo.q_image}</span>	                	
	                </div>
	                <hr>
                </c:if>
                <hr class="mb-5">
                
                <form action="adminInsertQnaAAction.adm" method="post" name="f">
					<input type="hidden" name="qna_id" value="${qnaInfo.qna_id}">
					<input type="hidden" name="a_writer" value="${sessionScope.a_id}">
					<input type="hidden" name="page" value="${page}">
					<div class="input-group mb-4">
						<textarea name="a_content" class="form-control" placeholder="답변을 입력하세요." aria-label="a_content" rows="10"></textarea>
					</div>
					<div class="col-12 text-center">
						<button type="submit" class="btn btn-primary" onclick="qnaFormCheck(); return false;">답변 등록</button>
					</div>
				</form>
			</div>
        </div>		
    </div>   
    <!-- Table End -->   
		
</body>
</html>