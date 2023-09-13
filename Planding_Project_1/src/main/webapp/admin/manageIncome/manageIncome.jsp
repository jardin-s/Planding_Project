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


<body>
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid pt-4 mb-5 wow fadeIn" data-wow-delay="0.1s" style="background-color:#86B381">
        <div class="container text-center pt-5">
            <h3 class="display-6 pb-3 text-white animated slideInDown">매출관리</h3>
            <div class="row justify-content-center">
	            <ul class="col-12 col-lg-8 nav nav-pills justify-content-center mt-4 mb-0">
					<li class="col-4 nav-item"><a class="nav-link active fw-bold" aria-current="page" href="#">전체 회원</a></li>
					<li class="col-4 nav-item"><a class="nav-link text-white" href="undeletedMemberList.mngm">미탈퇴 회원</a></li>
					<li class="col-4 nav-item"><a class="nav-link text-white" href="deletedMemberList.mngm">탈퇴 회원</a></li>
	            </ul>
            </div>
        </div>
    </div>
    <!-- Page Header End -->
    
    <!-- 날짜 네비게이션 -->
    <div>
    	<div class="navigation">
    		<!-- 이전 해 -->
    		<a href="manageIncome.mngi?year=${today_info.search_year-1 }&month=${today_info.search_month-1 }">
    			<span aria-hidden="true">&laquo;</span>
    		</a>
    		<!-- 이전 달 -->
    		<a href="manageIncome.mngi?year=${today_info.before_month}&month=${today_info.before_month }">
    			<span aria-hidden="true">&lt;</span>
    		</a>
    		
    		<!-- 현재 달 -->
  	  		<span>&nbsp;${today_info.search_year}
  	  			<c:if test="${today_info.search_month < 10 }">0</c:if>${today_info.search_month }
  	  		</span>
    		
    		<!-- 다음 달 -->
    		<a href="manageIncome.mngi?year=${today_info.after_year}&month=${today_info.after_month}">
    			<span aria-hidden="true">&gt;</span>
    		</a>
    		<!-- 다음 해 -->
    		<a href="manageIncome.mngi?year=${today_info.search_year+1}&month=${today_info.search_month+1}">
    			<span aria-hidden="true">&raquo;</span>
    		</a>
    	</div>
    </div>
    
    <table class="table">
    	<thead>
    		<tr class="text-center">
    			<td>일</td>
    			<td>월</td>
    			<td>화</td>
    			<td>수</td>
    			<td>목</td>
    			<td>금</td>
    			<td>토</td>
    		</tr>
    	</thead>
    	<tbody>
    		<tr>
	    		<c:forEach var="incomedate" items="${dateList }" varStatus="date_status">
	    			<c:choose>
	    				<c:when test="${incomedate.value eq 'today' }">
	    					<td class="bg-primary">
	    						<div>${incomedate.date }</div>
	    						<div>
	    							<c:set var="totalIncome" value="${0}"/>
	    							<c:forEach var="income" items="${incomedate.incomeList }">
	    								<c:set var="totalIncome" value="${totalIncome + income.fee_income }"/>
	    							</c:forEach>
	    							<button class="btn btn-light dropdown-toggle text-white" type="button" data-bs-toggle="dropdown" aria-expanded="false">
	    								${totalIncome}원
	   								</button>
	   								<ul class="dropdown-menu dropdown-menu-light">
	   									<c:forEach var="income" items="${incomedate.incomeList }">
	   										<li><a class="dropdown-item">${income.fee_income}원</a></li>   										
	   									</c:forEach>
	   								</ul>
	    						</div>
	    					</td>
	    				</c:when>
	    				<%-- 토요일 index 0~6 중 6 --%>
	    				<c:when test="${date_status.index % 7 == 6}">
	    					<td>
	    						<div class="text-primary">${incomedate.date}</div>
	    						<div>
	    							<c:set var="totalIncome" value="${0}"/>
	    							<c:forEach var="income" items="${incomedate.incomeList }">
	    								<c:set var="totalIncome" value="${totalIncome + income.fee_income }"/>
	    							</c:forEach>
	    							<button class="btn btn-light dropdown-toggle text-white" type="button" data-bs-toggle="dropdown" aria-expanded="false">
	    								${totalIncome}원
	   								</button>
	   								<ul class="dropdown-menu dropdown-menu-light">
	   									<c:forEach var="income" items="${incomedate.incomeList }">
	   										<li><a class="dropdown-item">${income.fee_income}원</a></li>   										
	   									</c:forEach>
	   								</ul>
	    						</div>
	    					</td>
	    				</c:when>
	    				<%-- 일요일 index 0~6 중 0 --%>
	    				<c:when test="${date_status.index % 7 == 0}">
		    				<%-- 한 줄에 7칸씩 출력하고 줄바꿈 --%>
		    				</tr>
		    				<tr>
	    					<td>
	    						<div class="text-danger">${incomedate.date}</div>
	    						<div>
	    							<c:set var="totalIncome" value="${0}"/>
	    							<c:forEach var="income" items="${incomedate.incomeList }">
	    								<c:set var="totalIncome" value="${totalIncome + income.fee_income }"/>
	    							</c:forEach>
	    							<button class="btn btn-light dropdown-toggle text-white" type="button" data-bs-toggle="dropdown" aria-expanded="false">
	    								${totalIncome}원
	   								</button>
	   								<ul class="dropdown-menu dropdown-menu-light">
	   									<c:forEach var="income" items="${incomedate.incomeList }">
	   										<li><a class="dropdown-item">${income.fee_income}원</a></li>   										
	   									</c:forEach>
	   								</ul>
	    						</div>
	    					</td>
	    				</c:when>
	    				<c:otherwise>
	    					<td>
	    						<div>${incomedate.date}</div>
	    						<div>
	    							<c:set var="totalIncome" value="${0}"/>
	    							<c:forEach var="income" items="${incomedate.incomeList }">
	    								<c:set var="totalIncome" value="${totalIncome + income.fee_income }"/>
	    							</c:forEach>
	    							<button class="btn btn-light dropdown-toggle text-white" type="button" data-bs-toggle="dropdown" aria-expanded="false">
	    								${totalIncome}원
	   								</button>
	   								<ul class="dropdown-menu dropdown-menu-light">
	   									<c:forEach var="income" items="${incomedate.incomeList }">
	   										<li><a class="dropdown-item">${income.fee_income}원</a></li>   										
	   									</c:forEach>
	   								</ul>
	    						</div>
	    					</td>
	    				</c:otherwise>
	    			</c:choose>
	    		</c:forEach>
	    	</tr>
    	</tbody>
    </table>
    
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