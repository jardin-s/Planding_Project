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

function changeOrder() {
	
	let selectedValue = document.getElementById("selectOrder").value;
	
	if(selectedValue != 'default'){
		document.forder.submit();
	}
	
}

function searchMemberList() {
	
	let search_id = document.getElementById("member_id").value;
	
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
            <h3 class="display-6 pb-3 text-white animated slideInDown">회원관리</h3>
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
    
    <c:if test="${pageInfo.listCount == 0 }">
    	<div class="container-xxl py-5">
    		<div class="container col-10 col-md-6 col-lg-4">
    			<div class="col-12 mb-5">
    				<c:if test="${search_id ne null }">
    					<p class="text-center">${search_id }이(가) 포함된 회원 아이디가 없습니다.</p>
    				</c:if>
    				<c:if test="${search_id eq null }">
    					<p class="text-center">가입한 회원이 없습니다.</p>
    				</c:if>
    			</div>
    		</div>
    	</div>
    </c:if>
    
    <c:if test="${pageInfo.listCount != 0 }">
    	<c:set var="m_index" value="${pageInfo.listCount - (pageInfo.page-1)*10 }" />
	    
	    <%-- Search Tab Start --%>
	    <div class="container-fluid pt-4 pb-3">
	    	<div class="container col-lg-8 px-0">
	    		<div class="row">
					
					<%-- Order --%>
					<div class="col-4 col-md-3">
						<div class="d-flex justify-content-start">
							<form action="orderMemberList.mngm" method="post" name="forder">
				    			<select class="form-select py-1" name="selectOrder" id="selectOrder" aria-label="selectOrder" onchange="changeOrder()">
									<option value="default">-- 정렬조건 --</option>
									<c:forEach var="order" items="${orderArr}">
										<c:if test="${orderKeyword eq order}">
											<option value="${order}" selected>
												<c:choose>
													<c:when test="${order eq 'new'}">최근 가입순</c:when>
													<c:when test="${order eq 'old'}">오래된 가입순</c:when>
													<c:when test="${order eq 'az'}">가나다순</c:when>
													<c:when test="${order eq 'za'}">역가나다순</c:when>
												</c:choose>
											</option>
										</c:if>
										<c:if test="${orderKeyword ne order }">
											<option value="${order}">
												<c:choose>
													<c:when test="${order eq 'new'}">최근 가입순</c:when>
													<c:when test="${order eq 'old'}">오래된 가입순</c:when>
													<c:when test="${order eq 'az'}">가나다순</c:when>
													<c:when test="${order eq 'za'}">역가나다순</c:when>
												</c:choose>
											</option>
										</c:if>
										
									</c:forEach>								
								</select>
							</form>
		    			</div>
		    		</div>
		    		
		    		<%-- Search --%>
		    		<div class="col auto">
			    		<div class="d-flex justify-content-end">
			    			<form action="searchMemberList.mngm" method="post" name="fsearch">
				    			<div class="btn btn-outline-light py-1 px-2 me-1">
					    			<input type="text" name="member_id" value="${search_id}" id="member_id" class="border-0" placeholder="아이디로 검색">
					    			<a href="javascript:searchMemberList();"><i class="fas fa-search"></i></a>
				    			</div>
			    			</form>
		    			</div>
	    			</div>
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
							<tr class="text-center">
								<th scope="col" class="col-1 d-none"><input type="checkbox" name="removeAll"></th>
								<th scope="col" class="col-1">#</th>
								<th scope="col" class="col-auto">아이디</th>
								<th scope="col" class="col-3">가입일자</th>
								<th scope="col" class="col-3">탈퇴여부</th>
							</tr>
						</thead>
						<tbody class="table-group-divider">						
							<c:forEach var="member" items="${memberList}">
								<tr class="text-center">
									<th class="d-none">
										<c:if test="${member.delete_status eq 'Y' }">
											<input type="checkbox" name="remove" value="${member.member_id}">
										</c:if>
										<c:if test="${member.delete_status ne 'Y' }">
											<input type="checkbox" name="remove" value="" disabled>
										</c:if>										
									</th>
									<th scope="row">${m_index}</th>
									<td><a href="memberView.adm?member_id=${member.member_id}&page=${pageInfo.page}">${member.member_id }</a></td>
									<td>${member.joindate }</td>
									<td>${member.delete_status }</td>
								</tr>
								<c:set var="m_index" value="${m_index -1 }"/>
							</c:forEach>
						</tbody>
					</table>
					<hr>
				</div>
	        </div>		
	    </div>
	    <%-- Table End --%>
	    
	    <%-- Delete Button --%>
	    <div class="container-fluid mt-0 pt-0 pb-5">
	    	<div class="container col-lg-8 px-0">
	    		<div class="d-flex justify-content-end">
	    			<button class="btn btn-outline-primary float-right py-1" type="submit" onclick="">선택회원 추방</button>
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