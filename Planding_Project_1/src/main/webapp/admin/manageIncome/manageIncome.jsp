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
    <div class="container-fluid py-4 mb-5 wow fadeIn" data-wow-delay="0.1s" style="background-color:#86B381">
        <div class="container text-center pt-5">
            <h3 class="display-6 py-3 text-white animated slideInDown">매출관리</h3>
        </div>
    </div>
    <!-- Page Header End -->
    
   	<!-- 달력 (width 768px이상일 때만 보임) -->
    <div class="container-fluid py-4 mb-5 d-none d-md-block">
    	<!-- 날짜 네비게이션 -->
    	<div class="container col-12 col-md-10 col-lg-7 mb-5">
            <div class="row justify-content-center">
		    	<div class="text-center">
		    		<!-- 이전 해 -->
		    		<a class="me-2" href="manageIncome.mngi?year=${today_info.search_year-1 }&month=${today_info.search_month-1 }">
		    			<span class="fs-3" aria-hidden="true">&laquo;</span>
		    		</a>
		    		<!-- 이전 달 -->
		    		<a class="me-2" href="manageIncome.mngi?year=${today_info.before_year}&month=${today_info.before_month }">
		    			<span class="fs-3" aria-hidden="true">&lt;</span>
		    		</a>
		    		
		    		<!-- 현재 달 -->
		  	  		<span class="fs-4">&nbsp;${today_info.search_year}년
		  	  			${today_info.search_month }월
		  	  		</span>
		    		
		    		<!-- 다음 달 -->
		    		<a class="ms-2" href="manageIncome.mngi?year=${today_info.after_year}&month=${today_info.after_month}">
		    			<span class="fs-3" aria-hidden="true">&gt;</span>
		    		</a>
		    		<!-- 다음 해 -->
		    		<a class="ms-2" href="manageIncome.mngi?year=${today_info.search_year+1}&month=${today_info.search_month+1}">
		    			<span class="fs-3" aria-hidden="true">&raquo;</span>
		    		</a>
		    	</div>
	    	</div>
	    </div>
	    
	    <%-- 달력 출력 --%>
	    <div class="container col-12 col-md-10 col-lg-8 col-xl-7 col-xxl-6">
		    <table class="table">
		    	<thead>
		    		<tr class="text-center">
		    			<td style="width:15%">일</td>
		    			<td style="width:14%">월</td>
		    			<td style="width:14%">화</td>
		    			<td style="width:14%">수</td>
		    			<td style="width:14%">목</td>
		    			<td style="width:14%">금</td>
		    			<td style="width:15%">토</td>
		    		</tr>
		    	</thead>
		    	<tbody>
		    		<tr>
			    		<%-- incomedate : 각 날짜+해당날짜의 수익기록 (연, 월, 일, 오늘/노멀, 수익리스트) --%>
			    		<c:forEach var="incomedate" items="${dateList }" varStatus="date_status">
			    			<c:choose>
			    				<%-- 오늘이라면 (incomedate의 값이 있으므로 빈칸이 아님) --%>
			    				<c:when test="${incomedate.value eq 'today' }">
			    					<td class="bg-light" style="height:100px">
			    						<%-- 날짜 --%>
			    						<div class="fw-bold">${incomedate.date }</div><%-- n일 --%>
			    						
			    						<%-- 수익리스트 --%>
			    						<div class="dropdown text-center">
				    						<c:if test="${incomedate.du_incomeList ne null}">
				    							<c:set var="totalIncome" value="0"/><%-- 해당 날짜의 총수익 0으로 초기화 --%>
				    							
				    							<%-- 수익리스트의 각 수익을 가져와서 총 수익을 계산 --%>
				    							<c:forEach var="income" items="${incomedate.du_incomeList }">
				    								<%-- 해당 날짜의 총수익 : 0+수익+수익+... --%>
				    								<c:set var="totalIncome" value="${totalIncome + income.fee_income }"/>
				    							</c:forEach>
				    							
				    							<%-- 계산된 총 수익을 드롭다운 제일 위에 출력 --%>
			    								<button class="btn btn-primary dropdown-toggle text-white px-2 py-0 mx-0" type="button" data-bs-toggle="dropdown" aria-expanded="false">
				    								+ ${totalIncome}
				   								</button>
				   								
				   								<ul class="dropdown-menu dropdown-menu-light mx-0">
				   									<c:forEach var="income" items="${incomedate.du_incomeList }">
				   										<li><a class="dropdown-item">+${income.fee_income}원</a></li>   										
				   									</c:forEach>
				   								</ul>
				    						</c:if>
			    						</div>
			    					</td>
			    				</c:when>
			    				<%-- 토요일 index 0~6 중 6 --%>
			    				<c:when test="${date_status.index % 7 == 6}">
			    					<td style="height:100px">
			    						
			    						<%-- 날짜 --%>
			    						<div class="text-info">${incomedate.date}</div>
			    						
			    						<%-- 수익리스트 --%>
			    						<div class="drowndown text-center">
				    						<c:if test="${incomedate.du_incomeList ne null}">
				    							<c:set var="totalIncome" value="0"/>
				    							<c:forEach var="income" items="${incomedate.du_incomeList }">
				    								<c:set var="totalIncome" value="${totalIncome + income.fee_income }"/>
				    							</c:forEach>
				    							
				    							<button class="btn btn-primary dropdown-toggle text-white px-2 py-0 mx-0" type="button" data-bs-toggle="dropdown" aria-expanded="false">
				    								+ ${totalIncome}
				   								</button>
				   								
				   								<ul class="dropdown-menu dropdown-menu-light mx-0">
				   									<c:forEach var="income" items="${incomedate.du_incomeList }">
				   										<li><a class="dropdown-item">+${income.fee_income}원</a></li>   										
				   									</c:forEach>
				   								</ul>
			   								</c:if>
			    						</div>
			    					</td>
			    				</c:when>
			    				<%-- 일요일 index 0~6 중 0 --%>
			    				<c:when test="${date_status.index % 7 == 0}">
				    				<%-- 한 줄에 7칸씩 출력하고 줄바꿈 --%>
				    				</tr>
				    				<tr>
			    					<td style="height:100px">
										<%-- 날짜 --%>
										<div class="text-danger">${incomedate.date}</div>
										
										<%-- 수익리스트 --%>
			    						<div class="drowndown text-center">
			    							<c:if test="${incomedate.du_incomeList ne null}">
				    							<c:set var="totalIncome" value="0"/>
				    							<c:forEach var="income" items="${incomedate.du_incomeList }">
				    								<c:set var="totalIncome" value="${totalIncome + income.fee_income }"/>
				    							</c:forEach>
				    							
				    							<button class="btn btn-primary dropdown-toggle text-white px-2 py-0 mx-0" type="button" data-bs-toggle="dropdown" aria-expanded="false">
				    								+ ${totalIncome}
				   								</button>
				   								
				   								<ul class="dropdown-menu dropdown-menu-light mx-0">
				   									<c:forEach var="income" items="${incomedate.du_incomeList }">
				   										<li><a class="dropdown-item">${income.fee_income}원</a></li>   										
				   									</c:forEach>
				   								</ul>
			   								</c:if>
			    						</div>
			    					</td>
			    				</c:when>
			    				
			    				<%-- 그외 나머지 날들 --%>
			    				<c:otherwise>
			    					<td style="height:100px">
			    						<%-- 날짜 --%>
			    						<div>${incomedate.date}</div>
			    						
			    						<%-- 수익리스트 --%>
			    						<div class="drowndown text-center">
			    							<c:if test="${incomedate.du_incomeList ne null}">
				    							<c:set var="totalIncome" value="0"/>
				    							<c:forEach var="income" items="${incomedate.du_incomeList }">
				    								<c:set var="totalIncome" value="${totalIncome + income.fee_income }"/>
				    							</c:forEach>
				    							<button class="btn btn-primary dropdown-toggle text-white px-2 py-0 mx-0" type="button" data-bs-toggle="dropdown" aria-expanded="false">
				    								+ ${totalIncome}
				   								</button>
				   								<ul class="dropdown-menu dropdown-menu-light mx-0">
				   									<c:forEach var="income" items="${incomedate.du_incomeList }">
				   										<li><a class="dropdown-item">+${income.fee_income}원</a></li>   										
				   									</c:forEach>
				   								</ul>
			   								</c:if>
			    						</div>
			    					</td>
			    				</c:otherwise>
			    			</c:choose>
			    		</c:forEach>
			    	</tr>
		    	</tbody>
		    </table>
    	</div>
    </div>
    
    
    <!-- 모든 수익기록을 리스트로 출력 -->
    <div class="container-fluid py-4 mb-5">
    	<div class="container col-12 col-md-10 col-lg-7 mb-5">
    		<div class="row justify-content-center">
    			<c:if test="${allIncomeList eq null }">
    				<p class="text-center">수익이 없습니다.</p>
    			</c:if>
    			<c:if test="${allIncomeList ne null }">
	    			<div style="height:300px; overflow-y:auto">
		    			<table class="table">
		    				<thead>
		    					<tr class="text-center">
		    						<th>프로젝트 ID</th>
		    						<th>수수료 수익</th>
		    						<th>수익일자</th>
		    					</tr>
		    				</thead>
		    				<tbody>
		    					<c:forEach var="incomeInfo" items="${allIncomeList}">
		    						<tr class="text-center">
		    							<td>${incomeInfo.project_id}</td>
		    							<td>+${incomeInfo.fee_income}원</td>
		    							<td>${incomeInfo.incomedate}</td>
		    						</tr>
		    					</c:forEach>
		    				</tbody>
		    			</table>
	    			</div>
    			</c:if>
    		</div>
    	</div>
    </div>
    
    
    
</body>
</html>