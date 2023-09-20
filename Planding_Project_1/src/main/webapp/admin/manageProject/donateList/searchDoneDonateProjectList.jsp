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

//정렬 요청
function changeOrder() {
	
	let selectedValue = document.getElementById("selectOrder").value;
	let orderKey = document.getElementById("orderKey").value;
	
	if(orderKey != null){//정렬하여 로딩한 경우
	
		if(selectedValue != 'default' && selectedValue != orderKey){//선택한 값이 default나 기존값이 아닐 때만 전송
			document.forder.submit();
		}
	
	}else{//정렬하여 로딩하지 않은 경우 (처음 로딩)
		
		if(selectedValue != 'default'){//선택한 값이 default만 아니면 전송
			document.forder.submit();
		}
	
	}
	
	
	
}

//검색어 유효성 검사 및 검색요청
function searchProjectList() {
	
	let search_id = document.getElementById("title").value;
	
	if(search_id == ''){
		alert('검색어를 입력하세요.');
		return false;
	}
	
	document.fsearch.submit();
	
}

</script>

<body>
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid pt-4 mb-5 wow fadeIn" data-wow-delay="0.1s" style="background-color:#86B381">
        <div class="container text-center pt-5">
            <h3 class="display-6 pt-3 mb-4 text-white animated slideInDown">기부 프로젝트 관리</h3>
            <div class="row justify-content-center">
	            <ul class="col-12 col-lg-8 nav nav-pills justify-content-center mt-5 mb-0">
					<li class="col-3 nav-item"><a class="nav-link text-white" href="manageDonateProjectList.mngp">전체 프로젝트</a></li>
					<li class="col-3 nav-item"><a class="nav-link text-white" href="unauthDonateProjectList.mngp">미승인 프로젝트</a></li>
					<li class="col-3 nav-item"><a class="nav-link text-white" href="authDonateProjectList.mngp">승인된 프로젝트</a></li>
					<li class="col-3 nav-item"><a class="nav-link active fw-bold" aria-current="page" href="#">종료된 프로젝트</a></li>
	            </ul>
            </div>
        </div>
    </div>
    <!-- Page Header End -->
    
	    
    <%-- Search Tab Start --%>
    <div class="container-fluid pt-4 pb-3">
    	<div class="container col-lg-8 px-0">
    		<div class="row">
				
				<%-- Order --%>
				<div class="col-4 col-md-3">
					<div class="d-flex justify-content-start">
						<form action="orderDonateProjectList.mngp" method="post" name="forder">
			    			<select class="form-select py-1" name="selectOrder" id="selectOrder" aria-label="selectOrder" onchange="changeOrder()">
								<option value="default" selected>-- 정렬조건 --</option>
								<option value="new">최근 등록순</option>
								<option value="old">오래된 등록순</option>
								<option value="az">가나다순</option>
								<option value="za">역가나다순</option>
							</select>
						</form>
	    			</div>
	    		</div>
	    		
	    		<%-- Search --%>
	    		<div class="col auto">
		    		<div class="d-flex justify-content-end">
		    			<form action="searchDonateProjectList.mngp" method="post" name="fsearch">
			    			<div class="btn btn-outline-light py-1 px-2 me-1">
				    			<input type="text" name="title" value="${searchKeyword}" id="title" class="border-0" placeholder="제목으로 검색">
				    			<a href="javascript:searchProjectList();"><i class="fas fa-search"></i></a>
			    			</div>
		    			</form>
	    			</div>
    			</div>
   			</div>
    	</div>
    </div>
    <%-- Search Tab End --%>
	
	<c:if test="${pageInfo.listCount == 0 }">
    	<div class="container-xxl mb-5 py-5" style="height:30vh">
    		<div class="container col-10 col-md-6 col-lg-4">
    			<div class="col-12 mb-5">
    				<c:if test="${searchKeyword ne null }">
    					<p class="text-center">${searchKeyword }이(가) 제목에 포함된 프로젝트가 없습니다.</p>
    				</c:if>
    				<c:if test="${searchKeyword eq null }">
    					<p class="text-center">등록된 프로젝트가 없습니다.</p>
    				</c:if>
    			</div>
    		</div>
    	</div>
    </c:if>
	
	<c:if test="${pageInfo.listCount != 0 }">
    	<c:set var="p_index" value="${(pageInfo.page-1)*10 +1}" />
	
	    <%-- Table Start --%>
	    <div class="container-fluid pt-0 pb-2">
	        <div class="container col-lg-8">
	            <div class="row justify-content-center">
					<table class="table table-hover">
						<thead>
							<tr class="text-center">
								<th scope="col" class="remove-th col-1 d-none"><input class="form-check-input" type="checkbox" name="allCheck" onclick="checkAll(this.form);"></th>
								<th scope="col" class="col-1">#</th>
								<th scope="col" class="col-auto">제목</th>
								<th scope="col" class="col-3">상태</th>
								<th scope="col" class="col-3">등록일자</th>							
							</tr>
						</thead>
						<tbody class="table-group-divider">						
							<c:forEach var="project" items="${projectList}">
								<tr class="text-center">
									<th scope="row">${p_index}</th>
									<td><a href="manageProjectView.mngp?project_id=${project.project_id}&page=${pageInfo.page}">${project.title}</a></td>
									<td>
										<c:if test="${project.p_status eq 'unauthorized'}">미승인</c:if>
										<c:if test="${project.p_status eq 'ready'}">공개예정</c:if>
										<c:if test="${project.p_status eq 'ongoing'}">진행중</c:if>
										<c:if test="${project.p_status eq 'done'}">종료</c:if>
										<c:if test="${project.p_status eq 'success'}">성공</c:if>
									</td>
									<td>
										<c:if test="${project.incomedate eq null}">
											<button class="btn btn-outline-primary py-1" type="button" id="sendBtn" onclick="location.href='sendTotalAmount.mngp?project_id=${project.project_id}'">송금</button>
										</c:if>
										<c:if test="${project.incomedate ne null}">
											<span style="color:#ccc">완료</span>
										</c:if>
									</td>
									<td>${project.regdate}</td>
								</tr>
								<c:set var="p_index" value="${p_index +1 }"/>
							</c:forEach>
						</tbody>
					</table>
					<hr>
				</div>
	        </div>		
	    </div>
	    <%-- Table End --%>
	    
	    	    
	    <%-- Pagination Start --%>
	    <div class="container-fluid mb-5 mt-0 pt-0 pb-5">
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
								<a class="page-link" href="searchDoneDonateProjectList.mngp?page=${pageInfo.page -1 }&title=${searchKeyword}" aria-label="Previous">
									<span aria-hidden="true">&laquo;</span>
								</a>	
							</c:if>								
						</li>
						
						<c:forEach var="pNum" begin="${pageInfo.startPage }" end="${pageInfo.endPage}" step="1">
							<c:if test="${pNum eq pageInfo.page }">
								<li class="page-item active" aria-current="page"><a class="page-link">${pNum}</a></li>
							</c:if>
							<c:if test="${pNum ne pageInfo.page }">
								<li class="page-item"><a class="page-link" href="searchDoneDonateProjectList.mngp?page=${pNum}&title=${searchKeyword}">${pNum}</a></li>
							</c:if>
						</c:forEach>
						
						<li class="page-item">
							<c:if test="${pageInfo.page >= pageInfo.maxPage }">
								<a class="page-link" aria-label="Next">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</c:if>
							<c:if test="${pageInfo.page < pageInfo.maxPage }">
								<a class="page-link" href="searchDoneDonateProjectList.mngp?page=${pageInfo.page +1 }&title=${searchKeyword}" aria-label="Next">
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