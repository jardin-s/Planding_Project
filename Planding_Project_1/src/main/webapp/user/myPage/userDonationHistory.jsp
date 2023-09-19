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
    
    <!-- Custom Stylesheet -->
    <link href="../resources/css/customStyle.css" rel="stylesheet">
    
    <style>
    .nav-pills > .nav-item > .active {
		background-color: #E8F5E9 !important; 
		color: #348E38 !important;
	}
    </style>
</head>
<body>
	
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid page-header pt-5 mb-5 wow fadeIn" data-wow-delay="0.1s">
        <div class="container text-center pt-5">
            <h3 class="display-6 text-white animated slideInDown">${sessionScope.u_name}님의 페이지</h3>
            <div class="row justify-content-center">
	            <ul class="col-12 col-lg-8 nav nav-pills justify-content-center mt-4 mb-0">
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userMyPage.usr">내 정보관리</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userBookmarkList.usr">관심 프로젝트</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userUploadProjectList.usr">등록 프로젝트</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link active fw-bold" aria-current="page" href="#">내 후원내역</a></li>
	            </ul>
            </div>
        </div>
    </div>
    <!-- Page Header End -->

	<c:if test="${donationHistory eq null }">
    	<div class="container-xxl py-5">
    		<div class="container col-lg-8 mb-5">
   				<p class="text-center">후원내역이 없습니다.</p>
    		</div>
    	</div>
    </c:if>
	
	<c:if test="${donationHistory ne null}">
    	
    	<!-- Table Start -->
	    <div class="container-fluid pt-0 pb-2">
	        <div class="container col-lg-8">
	            <div class="row justify-content-center">
					<table class="table table-hover">
						<thead>
							<tr class="text-center">
								<th scope="col" class="col-auto text-truncate">프로젝트</th>
								<th scope="col" class="col-auto text-truncate">리워드</th>
								<th scope="col" class="col-md-2">총금액</th>
								<th scope="col" class="col-md-3">후원일자</th>
								<th scope="col" class="col-lg-1">취소</th>
							</tr>
						</thead>
						<tbody class="table-group-divider">
							<c:forEach var="donation" items="${donationHistory}" varStatus="status">
								<tr class="text-center">
									<td>${donation.title }</td>
									<td>
										<c:if test="${donation.reward_id eq 'default'}">기본 리워드</c:if>
										<c:if test="${donation.reward_id ne 'default'}">${donation.r_name }</c:if>
									</td>
									<td>${donation.totalDonation }</td>
									<td>${donation.donatedate }</td>
									<td class="d-flex justify-content-center">
										<c:if test="${donation.status eq 'ongoing' }">
											<button class="btn btn-outline-primary py-1 px-1 d-none d-sm-block" type="button" onclick="location.href='cancelDonation.pj?donation_id=${donation.donation_id}'">취소</button>
											<a href="cancelDonation.pj?donation_id=${donation.donation_id}" class="d-block d-sm-none"><i class="fas fa-window-close text-primary"></i></a>
										</c:if>
										<c:if test="${donation.status ne 'ongoing'}">
											<button class="btn btn-outline-primary py-1 px-1 d-none d-sm-block" type="button" disabled>취소</button>
											<a href="cancelDonation.pj?donation_id=${donation.donation_id}" class="d-block d-sm-none"><i class="far fa-window-close text-secondary"></i></a>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
	        </div>		
	    </div>
	    <%-- Table End --%>
		    
  
    
   	 	<%-- Pagination Start --%>
	    <div class="container-fluid mt-0 pt-0 pb-5">
	    	<div class="container col-lg-4">
		    	<div class="row">
					<ul class="pagination justify-content-center">
						<%-- 이전버튼 --%>
						<li class="page-item">
							<c:if test="${pageInfo.page <= 1}">
								<a class="page-link" aria-label="Previous">
									<span aria-hidden="true">&laquo;</span>
								</a>	
							</c:if>
							<c:if test="${pageInfo.page > 1}">
								<a class="page-link" href="userDonationHistory.usr?page=${pageInfo.page -1 }" aria-label="Previous">
									<span aria-hidden="true">&laquo;</span>
								</a>	
							</c:if>								
						</li>
						
						<%-- 1~10까지 --%>
						<c:forEach var="pNum" begin="${pageInfo.startPage }" end="${pageInfo.endPage}" step="1">
							<c:if test="${pNum eq pageInfo.page }">
								<li class="page-item active" aria-current="page"><a class="page-link">${pNum}</a></li>
							</c:if>
							<c:if test="${pNum ne pageInfo.page }">
								<li class="page-item"><a class="page-link" href="userDonationHistory.usr?page=${pNum}">${pNum}</a></li>
							</c:if>
						</c:forEach>
						
						<%-- 다음버튼 --%>
						<li class="page-item">
							<c:if test="${pageInfo.page >= pageInfo.maxPage }">
								<a class="page-link" aria-label="Next">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</c:if>
							<c:if test="${pageInfo.page < pageInfo.maxPage }">
								<a class="page-link" href="userDonationHistory.usr?page=${pageInfo.page +1 }" aria-label="Next">
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
</body>
</html>