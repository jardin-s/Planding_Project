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
    <link href="../../resources/lib/animate/animate.min.css" rel="stylesheet">
    <link href="../../resources/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="../../resources/lib/lightbox/css/lightbox.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="../../resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="../../resources/css/style.css" rel="stylesheet">
    
    <!-- Custom Stylesheet -->
    <link href="../../resources/css/customStyle.css" rel="stylesheet">
    
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
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userDonationHistory.usr">내 후원내역</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userMyQnaList.usr">내 문의글</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link active fw-bold" aria-current="page" href="userUploadProjectList.usr">등록 프로젝트</a></li>
	            </ul>
            </div>
        </div>
    </div>
    <!-- Page Header End -->


    <c:if test="${pageInfo.listCount == 0 || pageInfo.listCount == null}">
    	<div class="container-xxl py-5">
    		<div class="container col-10 col-md-6 col-lg-4">
    			<div class="col-12 mb-5">
    				<p class="text-center">후원 내역이 없습니다.</p>
    			</div>
    		</div>
    	</div>
    </c:if>
    <c:if test="${pageInfo.listCount != 0 and pageInfo.listCount != null}">
		<div class="container-xxl py-5">
    		<div class="container col-lg-8 mb-5">
   				<h3 class="text-center">${reward_name }<br>후원목록</h3>
    		</div>
    	</div>
	</c:if>
		<c:forEach items="${donationList }" var="donation">
		    	<!-- Table Start  반복문 -->
			    <div class="container-fluid pt-0 pb-2">
			        <div class="container col-lg-8">
			            <div class="row justify-content-center" style="height:auto; overflow-y:auto">
							<table class="table table-bordered">
								<thead>
									<tr class="text-center">
										<th colspan="6">후원정보</th>
									</tr>
									<tr class="text-center">
										<th scope="col" class="col-1">후원번호</th>
										<th scope="col" class="col-auto">리워드번호</th>
										<th scope="col" class="col-2">후원자ID</th>
										<th scope="col" class="col-2">금액</th>
										<th scope="col" class="col-2">추가금액</th>
										<th scope="col" class="col-2">신청일</th>
									</tr>
								
								
										<tr class="text-center">
									        <td>${donation.donation_id }</td>
									        <td>${donation.reward_id }</td>
									        <td>${donation.member_id }</td>
									        <td>${donation.r_price }</td>
									        <td>${donation.add_donation }</td>
									        <td>${donation.donatedate }</td>
								   	 	</tr>
								</thead>
								<tbody class="table-group-divider">
							<c:if test="${donation.address_id ne null}">   
								   	 	<tr class="text-center">
	 	
								   	 	<th colspan="6">주소정보</th>
										</tr>
										<tr class="text-center">
											<th>배송번호</th>
											<td>${donation.address_id }</td>
											<th>받는 분 성함</th>
											<td>${donation.receiver_name }</td>
											<th>받는 분 연락처</th>
											<td>${donation.receiver_phone }</td>
										</tr>
										
										<tr class="text-center">
											<td>우편번호<br>(${donation.postcode })</td>
											<td colspan="2">주소 : ${donation.address1 }</td>
											<td colspan="3">상세주소 : ${donation.address2 }</td>
										</tr>
							</c:if>		
										
								</tbody>
								
							</table>
						</div>
			        </div>		
			    </div>
			    <%-- Table End --%>
		</c:forEach>
		
	<c:if test="${pageInfo.listCount != 0 and pageInfo.listCount != null}">
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
								<a class="page-link" href="byRewardDonationList.pj?page=${pageInfo.page -1 }&reward_id=${reward_id}" aria-label="Previous">
									<span aria-hidden="true">&laquo;</span>
								</a>	
							</c:if>								
						</li>
						
						<c:forEach var="pNum" begin="${pageInfo.startPage }" end="${pageInfo.endPage}" step="1">
							<c:if test="${pNum eq pageInfo.page }">
								<li class="page-item active" aria-current="page"><a class="page-link">${pNum}</a></li>
							</c:if>
							<c:if test="${pNum ne pageInfo.page }">
								<li class="page-item"><a class="page-link" href="byRewardDonationList.pj?page=${pNum}&reward_id=${reward_id}">${pNum}</a></li>
							</c:if>
						</c:forEach>
						
						<li class="page-item">
							<c:if test="${pageInfo.page >= pageInfo.maxPage }">
								<a class="page-link" aria-label="Next">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</c:if>
							<c:if test="${pageInfo.page < pageInfo.maxPage }">
								<a class="page-link" href="byRewardDonationList.pj?page=${pageInfo.page +1 }&reward_id=${reward_id}" aria-label="Next">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</c:if>							
						</li>
					</ul>
		        </div>
	        </div>
	    </div>
	    <%-- Pagination End --%>       
	</c:if>
</body>
</html>