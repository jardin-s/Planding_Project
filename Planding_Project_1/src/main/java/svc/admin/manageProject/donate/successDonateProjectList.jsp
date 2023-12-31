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
	
	if(selectedValue != 'default'){//선택한 값이 default만 아니면 전송
		document.forder.submit();
	}	
}

//검색어 유효성 검사 및 검색요청
function searchProjectList() {
	
	let search_id = document.getElementById("searchTitle").value;
	
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
					<li class="col-2 nav-item"><a class="nav-link text-white" href="manageDonateProjectList.mngp">전체</a></li>
					<li class="col-2 nav-item"><a class="nav-link text-white" href="unauthDonateProjectList.mngp">미승인</a></li>
					<li class="col-2 nav-item"><a class="nav-link text-white" href="readyDonateProjectList.mngp">공개예정</a></li>
					<li class="col-2 nav-item"><a class="nav-link text-white" href="ongoingDonateProjectList.mngp">진행중</a></li>
					<li class="col-2 nav-item"><a class="nav-link active fw-bold" aria-current="page" href="#">성공</a></li>
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
						<form action="successDonateProjectList.mngp" method="post" name="forder">
			    			<select class="form-select py-1" name="selectOrder" id="selectOrder" aria-label="selectOrder" onchange="changeOrder()">
								<c:if test="${orderKeyword eq null }"><option value="default" selected>-- 정렬조건 --</option></c:if>
								<c:if test="${orderKeyword ne null }"><option value="default">-- 정렬조건 --</option></c:if>
								<c:if test="${orderKeyword eq 'send' }"><option value="send" selected>미송금 우선</option></c:if>
								<c:if test="${orderKeyword eq 'send' }"><option value="send">미송금 우선</option></c:if>
								<c:if test="${orderKeyword eq 'new' }"><option value="new" selected>최근 마감순</option></c:if>
								<c:if test="${orderKeyword ne 'new' }"><option value="new">최근 마감순</option></c:if>
								<c:if test="${orderKeyword eq 'old' }"><option value="old" selected>오래된 마감순</option></c:if>
								<c:if test="${orderKeyword ne 'old' }"><option value="old">오래된 마감순</option></c:if>
								<c:if test="${orderKeyword eq 'az' }"><option value="az" selected>가나다순</option></c:if>
								<c:if test="${orderKeyword ne 'az' }"><option value="az">가나다순</option></c:if>
								<c:if test="${orderKeyword eq 'za' }"><option value="za" selected>역가나다순</option></c:if>
								<c:if test="${orderKeyword ne 'za' }"><option value="za">역가나다순</option></c:if>
							</select>
						</form>
	    			</div>
	    		</div>
	    		
	    		<%-- Search --%>
	    		<div class="col auto">
		    		<div class="d-flex justify-content-end">
		    			<form action="successDonateProjectList.mngp" method="post" name="fsearch">
			    			<div class="btn btn-outline-light py-1 px-2 me-1">
				    			<input type="text" name="searchTitle" value="${searchKeyword }" id="searchTitle" class="border-0" placeholder="제목으로 검색">
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
    	<c:set var="p_index" value="${pageInfo.listCount - (pageInfo.page-1)*10 }" />
	
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
										<c:if test="${project.p_status eq 'success'}">
											<button class="btn btn-outline-primary py-1" type="button" id="sendBtn" onclick="location.href='sendTotalAmount.mngp?project_id=${project.project_id}'">송금</button>
										</c:if>
										<c:if test="${project.p_status eq 'remitcom'}">성공</c:if>
									</td>
									<td>${project.regdate}</td>
								</tr>
								<c:set var="p_index" value="${p_index -1 }"/>
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
								<c:choose>
									<c:when test="${orderKeyword ne null }">
										<a class="page-link" href="successDonateProjectList.mngp?page=${pageInfo.page -1 }&selectOrder=${orderKeyword}" aria-label="Previous">
											<span aria-hidden="true">&laquo;</span>
										</a>
									</c:when>
									<c:when test="${searchKeyword ne null }">
										<a class="page-link" href="successDonateProjectList.mngp?page=${pageInfo.page -1 }&searchTitle=${searchKeyword}" aria-label="Previous">
											<span aria-hidden="true">&laquo;</span>
										</a>
									</c:when>
									<c:otherwise>
										<a class="page-link" href="successDonateProjectList.mngp?page=${pageInfo.page -1 }" aria-label="Previous">
											<span aria-hidden="true">&laquo;</span>
										</a>
									</c:otherwise>
								</c:choose>
							</c:if>								
						</li>
						
						<c:forEach var="pNum" begin="${pageInfo.startPage }" end="${pageInfo.endPage}" step="1">
							<c:if test="${pNum eq pageInfo.page }">
								<li class="page-item active" aria-current="page"><a class="page-link">${pNum}</a></li>
							</c:if>
							<c:if test="${pNum ne pageInfo.page }">
								<c:choose>
									<c:when test="${orderKeyword ne null }">
										<li class="page-item"><a class="page-link" href="successDonateProjectList.mngp?page=${pNum}&selectOrder=${orderKeyword}">${pNum}</a></li>
									</c:when>
									<c:when test="${searchKeyword ne null }">
										<li class="page-item"><a class="page-link" href="successDonateProjectList.mngp?page=${pNum}&searchTitle=${searchKeyword}">${pNum}</a></li>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link" href="successDonateProjectList.mngp?page=${pNum}">${pNum}</a></li>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>
						
						<li class="page-item">
							<c:if test="${pageInfo.page >= pageInfo.maxPage }">
								<a class="page-link" aria-label="Next">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</c:if>
							<c:if test="${pageInfo.page < pageInfo.maxPage }">
								<c:choose>
									<c:when test="${orderKeyword ne null }">
										<a class="page-link" href="successDonateProjectList.mngp?page=${pageInfo.page +1 }&selectOrder=${orderKeyword}" aria-label="Next">
											<span aria-hidden="true">&raquo;</span>
										</a>
									</c:when>
									<c:when test="${searchKeyword ne null }">
										<a class="page-link" href="successDonateProjectList.mngp?page=${pageInfo.page +1 }&searchTitle=${searchKeyword}" aria-label="Next">
											<span aria-hidden="true">&raquo;</span>
										</a>
									</c:when>
									<c:otherwise>
										<a class="page-link" href="successDonateProjectList.mngp?page=${pageInfo.page +1 }" aria-label="Next">
											<span aria-hidden="true">&raquo;</span>
										</a>
									</c:otherwise>
								</c:choose>
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