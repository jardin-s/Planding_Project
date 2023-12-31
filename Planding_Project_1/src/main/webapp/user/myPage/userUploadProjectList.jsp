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


    <c:if test="${uploadProjectList eq null }">
    	<div class="container-xxl py-5">
    		<div class="container col-lg-8 mb-5">
   				<p class="text-center">등록한 프로젝트가 없습니다.</p>
    		</div>
    	</div>
    </c:if>
	
	<c:if test="${uploadProjectList ne null}">
    	
    	<!-- Table Start -->
	    <div class="container-fluid pt-0 pb-2">
	        <div class="container col-lg-8">
	            <div class="row justify-content-center" style="height:300px; overflow-y:auto">
					<table class="table table-hover">
						<thead>
							<tr class="text-center">
								<th scope="col" style="width:10%">#</th>
								<th scope="col" style="width:auto">프로젝트 제목</th>
								<th scope="col" style="width:10%">수정</th>
								<th scope="col" style="width:10%">관리</th>
							</tr>
						</thead>
						<tbody class="table-group-divider">
							<c:forEach var="uploadProject" items="${requestScope.uploadProjectList }" varStatus="status">
								<tr class="text-center">
									<th scope="row" class="text-center">${status.count}</th>
									<td>
										<a href="userProjectView.pj?project_id=${uploadProject.project_id}">${uploadProject.title}</a>
									</td>
									<td>
										<c:if test="${uploadProject.p_status eq 'unauthorized' || uploadProject.p_status eq 'ready' }">
											<button class="btn btn-outline-primary py-1 px-1" type="button" id="editBtn" onclick="location.href='editProjectForm.pj?project_id=${uploadProject.project_id}'">수정</button>
										</c:if>
									</td>
									<td>
										<c:if test="${uploadProject.kind eq 'donate' }">
											<button class="btn btn-outline-primary py-1 px-1" type="button" id="mngBtn" onclick="location.href='userProjectDonationListALL.pj?project_id=${uploadProject.project_id}'">관리</button>
										</c:if>
										<c:if test="${uploadProject.kind eq 'fund' }">
											<button class="btn btn-outline-primary py-1 px-1" type="button" id="mngBtn" onclick="location.href='manageProject.pj?project_id=${uploadProject.project_id}'">관리</button>
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
		    
    </c:if>
	
</body>
</html>