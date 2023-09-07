<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <title>PlanDing - Fund for Our Plannet</title>
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
<script>
function deleteConfirm(page, notice_id){
	if(!confirm('공지글을 삭제하시겠습니까?')){
		return false;
	}else{
		location.href="adminDeleteNoticeAction.adm?page="+page+"&notice_id="+notice_id;
	}
}
</script>

<body>
	
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid py-5 mb-5 wow fadeIn" data-wow-delay="0.1s" style="background-color:#86B381">
        <div class="container text-center py-5">
            <h3 class="display-6 text-white mb-4 animated slideInDown">공지사항</h3>
        </div>
    </div>
    <!-- Page Header End -->


    <!-- Table Start -->
    <div class="container-fluid py-4 mb-5">
        <div class="container col-md-8 col-lg-7">
            <div class="row justify-content-center">
				<table class="table">
					<thead>
						<tr>
							<th colspan="3"><h3>공지사항 제목</h3></th>							
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="col-7 col-lg-8 col-xl-9"></td>
							<td class="col-3 col-lg-2 col-xl-2">작성시간</td>
							<td class="col-2 col-lg-2 col-xl-1">조회수</td>
						</tr>
						<tr>
							<td colspan="3">공지사항 이미지</td>
						</tr>
						<tr>
							<td colspan="3">공지사항 내용</td>
						</tr>
					</tbody>
				</table>    
				
				<div class="col-12 text-center mx-auto">
					<button class="btn btn-light" type="button" onclick="location.href='adminModifyNoticeForm.adm?page=${page}&notice_id=${noticeInfo.notice_id}'">수정</button>
					<button class="btn btn-light" type="button" onclick="deleteConfirm(${page},${noticeInfo.notice_id});">삭제</button>
               		<button class="btn btn-light" onclick="location.href='adminNoticeList.adm?page=${page}'">글 목록</button>
				</div>        
			</div>
        </div>		
    </div>   
    <!-- Table End -->  
	
    
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