<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <title>PlanDing - Fund for Our Plannet</title>
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
    <div class="container-fluid page-header pt-5 mb-5">
        <div class="container text-center pt-5">
            <div class="text-center mx-auto" style="max-width: 500px;">
                <p class="fs-5 fw-bold text-white">프로젝트 둘러보기</p>
                <h1 class="display-5 mb-5 text-white">기부 프로젝트</h1>
                <div class="row justify-content-center">
	            <ul class="col-12 col-lg-8 nav nav-pills justify-content-center mt-5 mb-0">
					<li class="col-4 nav-item"><a class="nav-link active text-primary fw-bold bg-light" aria-current="page" href="#">진행중</a></li>
					<li class="col-4 nav-item"><a class="nav-link text-white" href="unauthFundProjectList.mngp">공개예정</a></li>
					<li class="col-4 nav-item"><a class="nav-link text-white" href="authFundProjectList.mngp">종료</a></li>
	            </ul>
            </div>
            </div>
        </div>
    </div>
    <!-- Page Header End -->
    
       
        
	<%-- Search Tab Start --%>
	    <div class="container-fluid pt-4 pb-3">
	    	<div class="container col-lg-8 px-3">
	    		<div class="row">
					
					<%-- Order --%>
					<div class="col-4 col-md-3">
						<div class="d-flex justify-content-start">
							<form action="orderFundProjectList.mngp" method="post" name="forder">
				    			<select class="form-select py-1" name="selectOrder" id="selectOrder" aria-label="selectOrder" onchange="changeOrder()">
									<option value="default" selected>-- 정렬하기 --</option>
									<option value="ready">최신순</option>
									<option value="ongoing">오래된순</option>
									<option value="done">높은 관심순</option>
								</select>
							</form>
		    			</div>
		    		</div>
		    		
		    		<%-- Search --%>
		    		<div class="col auto">
			    		<div class="d-flex justify-content-end">
			    			<form action="searchFundProjectList.mngp" method="post" name="fsearch">
				    			<div class="btn btn-outline-light py-1 px-2 me-1">
					    			<input type="text" name="title" id="title" class="border-0" placeholder="제목으로 검색">
					    			<a href="javascript:searchProjectList();"><i class="fas fa-search"></i></a>
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
				<div class="col-12 col-md-6 col-lg-4 mb-5">
					<table class="table table-borderless">
						<thead>
							<tr>
								<td colspan="2">
									<img src="../resources/img/service-1.jpg" style="width:100%">
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<span style="font-size:0.7rem">기부</span>
									<span class="mx-1" style="color:#ccc;font-size:0.7rem">|</span>
									<span style="font-size:0.7rem">언더 더 파고라</span>
									<br>
									<strong>풍요로운 대자연의 선물 : 흙</strong><br>
									<span style="color:#ccc;font-size:0.8rem">풍요로운 대자연의 선물 : 흙</span>
								</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-start">
									<span class="me-2 text-danger fw-bold">2307%</span>
									<span style="font-size:0.8rem">11,535,400원</span>
								</td>
								<td class="text-end">
									<span style="font-size:0.8rem"><b>9일 남음</b></span>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="px-2 pt-0">
									<div class="progress col-12" role="progressbar" aria-label="Basic example" aria-valuenow="2307" aria-valuemin="0" aria-valuemax="100" style="height:2px">
										<div class="progress-bar bg-danger" style="width: 2307%"></div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="col-12 col-md-6 col-lg-4 mb-5">
					<table class="table table-borderless">
						<thead>
							<tr>
								<td colspan="2">
									<img src="../resources/img/service-1.jpg" style="width:100%">
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<span style="font-size:0.7rem">기부</span>
									<span class="mx-1" style="color:#ccc;font-size:0.7rem">|</span>
									<span style="font-size:0.7rem">언더 더 파고라</span>
									<br>
									<strong>풍요로운 대자연의 선물 : 흙</strong><br>
									<span style="color:#ccc;font-size:0.8rem">풍요로운 대자연의 선물 : 흙</span>
								</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-start">
									<span class="me-2 text-black fw-bold">57%</span>
									<span style="font-size:0.8rem">285,000원</span>								
								</td>
								<td class="text-end">
									<span style="font-size:0.8rem"><b>종료</b></span>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="px-2 pt-0">
									<div class="progress col-12" role="progressbar" aria-label="Basic example" aria-valuenow="57" aria-valuemin="0" aria-valuemax="100" style="height:2px">
										<div class="progress-bar bg-secondary" style="width: 57%"></div>
									</div>
								</td>
							</tr>
						</tbody>					
					</table>
				</div>
				<div class="col-12 col-md-6 col-lg-4 mb-5">
					<table class="table table-borderless">
						<thead>
							<tr>
								<td colspan="2">
									<img src="../resources/img/service-1.jpg" style="width:100%">
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<span style="font-size:0.7rem">기부</span>
									<span class="mx-1" style="color:#ccc;font-size:0.7rem">|</span>
									<span style="font-size:0.7rem">언더 더 파고라</span>
									<br>
									<strong>풍요로운 대자연의 선물 : 흙</strong><br>
									<span style="color:#ccc;font-size:0.8rem">풍요로운 대자연의 선물 : 흙</span>
								</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-start">
									<span class="me-2 text-black fw-bold">2307%</span>
									<span style="font-size:0.8rem">11,535,400원</span>
								</td>
								<td class="text-end">
									<span style="font-size:0.8rem"><b>성공</b></span>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="px-2 pt-0">
									<div class="progress col-12" role="progressbar" aria-label="Basic example" aria-valuenow="2307" aria-valuemin="0" aria-valuemax="100" style="height:2px">
										<div class="progress-bar bg-secondary" style="width: 2307%"></div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="col-12 col-md-6 col-lg-4 mb-5">
					<table class="table table-borderless">
						<thead>
							<tr>
								<td colspan="2">
									<img src="../resources/img/service-1.jpg" style="width:100%">
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<span style="font-size:0.7rem">기부</span>
									<span class="mx-1" style="color:#ccc;font-size:0.7rem">|</span>
									<span style="font-size:0.7rem">언더 더 파고라</span>
									<br>
									<strong>풍요로운 대자연의 선물 : 흙</strong><br>
									<span style="color:#ccc;font-size:0.8rem">풍요로운 대자연의 선물 : 흙</span>
								</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-start">
									<span class="me-2 text-danger fw-bold">2307%</span>
									<span style="font-size:0.8rem">11,535,400원</span>
								</td>
								<td class="text-end">
									<span style="font-size:0.8rem"><b>9일 남음</b></span>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="px-2 pt-0">
									<div class="progress col-12" role="progressbar" aria-label="Basic example" aria-valuenow="2307" aria-valuemin="0" aria-valuemax="100" style="height:2px">
										<div class="progress-bar bg-danger" style="width: 2307%"></div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="col-12 col-md-6 col-lg-4 mb-5">
					<table class="table table-borderless">
						<thead>
							<tr>
								<td colspan="2">
									<img src="../resources/img/service-1.jpg" style="width:100%">
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<span style="font-size:0.7rem">기부</span>
									<span class="mx-1" style="color:#ccc;font-size:0.7rem">|</span>
									<span style="font-size:0.7rem">언더 더 파고라</span>
									<br>
									<strong>풍요로운 대자연의 선물 : 흙</strong><br>
									<span style="color:#ccc;font-size:0.8rem">풍요로운 대자연의 선물 : 흙</span>
								</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-start">
									<span class="me-2 text-black fw-bold">57%</span>
									<span style="font-size:0.8rem">285,000원</span>								
								</td>
								<td class="text-end">
									<span style="font-size:0.8rem"><b>종료</b></span>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="px-2 pt-0">
									<div class="progress col-12" role="progressbar" aria-label="Basic example" aria-valuenow="57" aria-valuemin="0" aria-valuemax="100" style="height:2px">
										<div class="progress-bar bg-secondary" style="width: 57%"></div>
									</div>
								</td>
							</tr>
						</tbody>					
					</table>
				</div>
				<div class="col-12 col-md-6 col-lg-4 mb-5">
					<table class="table table-borderless">
						<thead>
							<tr>
								<td colspan="2">
									<img src="../resources/img/service-1.jpg" style="width:100%">
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<span style="font-size:0.7rem">기부</span>
									<span class="mx-1" style="color:#ccc;font-size:0.7rem">|</span>
									<span style="font-size:0.7rem">언더 더 파고라</span>
									<br>
									<strong>풍요로운 대자연의 선물 : 흙</strong><br>
									<span style="color:#ccc;font-size:0.8rem">풍요로운 대자연의 선물 : 흙</span>
								</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-start">
									<span class="me-2 text-black fw-bold">2307%</span>
									<span style="font-size:0.8rem">11,535,400원</span>
								</td>
								<td class="text-end">
									<span style="font-size:0.8rem"><b>성공</b></span>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="px-2 pt-0">
									<div class="progress col-12" role="progressbar" aria-label="Basic example" aria-valuenow="2307" aria-valuemin="0" aria-valuemax="100" style="height:2px">
										<div class="progress-bar bg-secondary" style="width: 2307%"></div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>		
	</div>
	<%-- Table End --%>
	
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
					<li class="page-item"><a class="page-link" href="#">1</a></li>
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