<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
							<th colspan="3"><h3>${noticeInfo.n_title }</h3></th>							
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="col-6 col-md-7 col-xl-8 col-xxl-9"></td>
							<td colspan="2" class="col-6 col-md-5 col-xl-4 col-xxl-3 text-end">${noticeInfo.writetime } | 조회수 ${noticeInfo.viewcount }</td>
						</tr>
						<c:if test="${noticeInfo.n_image ne null }">
							<tr class="text-center">
								<td colspan="3">
									<img src="<%=request.getContextPath() %>/images/notice/${noticeInfo.n_image}" style="width:80%">		
								</td>
							</tr>
						</c:if>							
						<tr class="text-center">
							<td colspan="3">${noticeInfo.n_content }</td>
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
	
    
</body>
</html>