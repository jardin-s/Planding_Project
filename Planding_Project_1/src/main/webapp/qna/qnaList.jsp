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
    
    <c:if test="${pageInfo.listCount == 0 }">
    	<div class="container-xxl py-5">
    		<div class="container col-lg-8">
    				<p class="text-center">작성된 문의글이 없습니다.</p>
	    			<button class="btn btn-outline-primary float-right py-1">문의글 작성</button>
    			</div>
    		</div>
    	</div>
    </c:if>
    
    <c:if test="${pageInfo.listCount != 0 }">
    	<c:set var="q_index" value="${pageInfo.listCount - (pageInfo.page-1)*10 }" />
	    
	    <%-- Search Tab Start --%>
	    <div class="container-fluid pt-4 pb-3">
	    	<div class="container col-lg-8 px-0">
	    		<div class="d-flex justify-content-end">
					<form>
		    			<div class="btn btn-outline-light py-1 px-2 me-1">
			    			<input type="text" name="q_title" id="q_title" class="border-0 me-2" placeholder="제목으로 검색">
			    			<a href="javascript:searchQnaList();"><i class="fas fa-search"></i></a>
		    			</div>
		    		</form>
	    		</div>
	    	</div>
	    </div>
	    <%-- Search Tab End --%>
	
	    <%-- Table Start --%>
	    <div class="container-fluid pt-0 pb-2">
	        <div class="container col-lg-8">
	            <div class="row justify-content-center">
					<table class="table table-hover">
						<thead>
							<tr>
								<th scope="col">번호</th>
								<th scope="col">제목</th>
								<th scope="col">작성자</th>
								<th scope="col">날짜</th>
							</tr>
						</thead>
						<tbody class="table-group-divider">
							<c:forEach var="qna" items="${qnaList }">
								<tr>
									<th scope="row">${q_index}</th>
									<td><a href="qnaView.qna?qna_id=${qna.qna_id}&page=${pageInfo.page}&isPrivate=${qna.isPrivate}">${qna.q_title }</a>
										<c:if test="${qna.isPrivate eq true}">
											<i class="fas fa-lock ms-1"></i>
										</c:if>
									</td>
									<td>${qna.member_id }</td>
									<td>${qna.q_time }</td>
								</tr>
								<c:set var="q_index" value="${q_index -1 }"/>
							</c:forEach>
						</tbody>
					</table>
				</div>
	        </div>		
	    </div>
	    <%-- Table End --%>
	    
	    <div class="container-fluid mt-0 pt-0 pb-5">
	    	<div class="container col-lg-8 px-0">
	    		<div class="d-flex justify-content-end">
	    			<button class="btn btn-outline-primary float-right py-1" type="button" onclick="location.href='qnaNewQuestionForm.qna'">문의글 작성</button>
	    		</div>
	    	</div>
	   	</div>
	    
	    
	    <%-- Pagination Start --%>
	    <div class="container-fluid mt-0 pt-0 pb-5">
	    	<div class="container col-lg-4">
		    	<div class="row">
					<ul class="pagination justify-content-center">
						<li class="page-item">
							<a class="page-link" href="#" aria-label="Previous">
								<span aria-hidden="true">&laquo;</span>
							</a>
						</li>
						
							<li class="page-item active" aria-current="page"><a class="page-link" href="#">1</a></li>
							<li class="page-item"><a class="page-link" href="#">2</a></li>
							<li class="page-item"><a class="page-link" href="#">3</a></li>
						
						<li class="page-item">
							<a class="page-link" href="#" aria-label="Next">
								<span aria-hidden="true">&raquo;</span>
							</a>
						</li>
					</ul>
		        </div>
	        </div>
	    </div>
	    <%-- Pagination Start --%>       
    
    </c:if>
    
    
		
	    
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