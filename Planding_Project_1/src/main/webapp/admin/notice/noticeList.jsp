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
    <div class="container-fluid py-5 mb-5 wow fadeIn" data-wow-delay="0.1s" style="background-color:#86B381">
        <div class="container text-center py-5">
            <h3 class="display-6 text-white mb-4 animated slideInDown">공지사항</h3>
        </div>
    </div>
    <!-- Page Header End -->
    
    <c:if test="${pageInfo.listCount == 0 }">
    	<div class="container-xxl py-5">
    		<div class="container col-10 col-md-6 col-lg-4">
    			<div class="col-12 mb-5">
    				<p class="text-center">작성된 공지글이 없습니다.</p>
    			</div>
    			<div class="col-12 text-center mx-auto mb-5">
					<button class="btn btn-outline-primary py-1" type="button" onclick="location.href='adminInsertNoticeForm.adm'">공지글 작성</button>
				</div>
    		</div>
    	</div>
    </c:if>
    
    <c:if test="${pageInfo.listCount != 0 }">
    	<c:set var="n_index" value="${pageInfo.listCount - (pageInfo.page-1)*10 }" />
	    
	    <%-- Search Tab Start --%>
	    <div class="container-fluid pt-4 pb-3">
	    	<div class="container col-lg-8 px-0">
	    		<div class="d-flex justify-content-end">
					<form>
		    			<div class="btn btn-outline-light py-1 px-2 me-1">
			    			<input type="text" name="n_title" id="n_title" class="border-0 me-2" placeholder="제목으로 검색">
			    			<a href="javascript:searchNoticeList();"><i class="fas fa-search"></i></a>
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
								<th scope="col" class="col-1 text-center">#</th>
								<th scope="col" class="col-8">제목</th>
								<th scope="col" class="col-2">날짜</th>
								<th scope="col" class="col-1">조회수</th>
							</tr>
						</thead>
						<tbody class="table-group-divider">
							<c:forEach var="noticeImp" items="${importantList}">
								<tr>
									<th scope="row"><i class="fas fa-exclamation"></i></th>
									<td><a href="adminNoticeView.adm?notice_id=${noticeImp.notice_id}&page=${pageInfo.page}">${noticeImp.n_title }</a></td>
									<td>${noticeImp.writetime }</td>
									<td>${noticeImp.viewcount }</td>
								</tr>
								<c:set var="n_index" value="${n_index -1 }"/>
							</c:forEach>							
							<c:forEach var="notice" items="${noticeList}">
								<tr>
									<th scope="row">${n_index}</th>
									<td><a href="adminNoticeView.adm?notice_id=${notice.notice_id}&page=${pageInfo.page}">${notice.n_title }</a></td>
									<td>${notice.writetime }</td>
									<td>${notice.viewcount }</td>
								</tr>
								<c:set var="n_index" value="${n_index -1 }"/>
							</c:forEach>
						</tbody>
					</table>
				</div>
	        </div>		
	    </div>
	    <%-- Table End --%>
	    
	    <%-- Write Button --%>
	    <div class="container-fluid mt-0 pt-0 pb-5">
	    	<div class="container col-lg-8 px-0">
	    		<div class="d-flex justify-content-end">
	    			<button class="btn btn-outline-primary float-right py-1" type="button" onclick="location.href='adminInsertNoticeForm.adm'">공지글 작성</button>
	    		</div>
	    	</div>
	   	</div>
	    
	    	    
	    <%-- Pagination Start --%>
	    <div class="container-fluid mt-0 pt-0 pb-5">
	    	<div class="container col-lg-4">
		    	<div class="row">
					<ul class="pagination justify-content-center">
						<li class="page-item">
							<c:if test="${pageInfo.page <= 1}">
								<a class="page-link" aria-label="Previous">
									<span aria-hidden="true">&laquo;</span>
								</a>	
							</c:if>
							<c:if test="${pageInfo.page > 1}">
								<a class="page-link" href="userNoticeList.usr?page=${pageInfo.page -1 }" aria-label="Previous">
									<span aria-hidden="true">&laquo;</span>
								</a>	
							</c:if>								
						</li>
						
						<c:forEach var="pNum" begin="${pageInfo.startPage }" end="${pageInfo.endPage}" step="1">
							<c:if test="${pNum eq pageInfo.page }">
								<li class="page-item active" aria-current="page"><a class="page-link">${pNum}</a></li>
							</c:if>
							<c:if test="${pNum ne pageInfo.page }">
								<li class="page-item"><a class="page-link" href="userNoticeList.usr?page=${pNum}">${pNum}</a></li>
							</c:if>
						</c:forEach>
						
						<li class="page-item">
							<c:if test="${pageInfo.page >= pageInfo.maxPage }">
								<a class="page-link" aria-label="Next">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</c:if>
							<c:if test="${pageInfo.page < pageInfo.maxPage }">
								<a class="page-link" href="userNoticeList.usr?page=${pageInfo.page +1 }" aria-label="Next">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</c:if>							
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