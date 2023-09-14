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
<%String kind=request.getParameter("kind"); %>

		<table style="border-collapse: collapse; width: 50%; border-style: none;" border="1">
			<tbody>
			<tr style="height: 162px;">
			<td style="width: 99.9999%; height: 162px;" colspan="3"><img src="../resources/images/${thumbnail}.jpg"></td><!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
			</tr>
			<tr style="height: 596px;">
			<td style="width: 99.9999%; height: 596px;" colspan="3"><img src="../resources/images/${image}.jpg"></td>
			</tr>
			<tr style="height: 71px;">
			<td style="width: 33.3333%; height: 71px;">같은 카테고리 프로젝트1</td>
			<td style="width: 33.3333%; height: 71px;">같은 카테고리 프로젝트2</td>
			<td style="width: 33.3333%; height: 71px;">같은 카테고리 프로젝트3</td>
			</tr>
			</tbody>
		</table>
		<table style="border-collapse: collapse; width: 35%; border-style: none;" border="1">
			<tbody>
			<tr style="height: 54px;">
			<td style="width: 100%; height: 54px;">${project_title}</td>
			</tr>
			<tr style="height: 22px;">
			<td style="width: 100%; height: 22px;">${project_id}</td>
			</tr>
			<tr style="height: 22px;">
			<td style="width: 100%; height: 22px;">기간:${startdate}~${enddate}</td>
			</tr>
			<tr style="height: 22px;">
			<td style="width: 100%; height: 22px;">${project_curr_amount}/${project_goal_amount} (${project_curr_amount/project_goal_amount}% 달성!)</td>
			</tr>
			<tr style="height: 22px;">
			<td style="width: 100%; height: 22px;">${likes}<button onclick="likepj()">추천하기</button>/<button onclick="pj()"><%=(kind.equals("donate"))?"기부하기":"펀딩하기" %></button></td><!-- 함수 채워놓기, 따봉이미지로 바꿀까 -->
			</tr>
			<tr style="height: 22px;">
			<td style="width: 100%; height: 22px;">기획자 이름: ${planner_name}</td>
			</tr>
			<tr style="height: 22px;">
			<td style="width: 100%; height: 22px;">기획자 간단 소개: ${introduce}</td>
			</tr>
			</tbody>
		</table>
		
		<table style="border-collapse: collapse; width: 35%; border-style: none;" border="1">
			<tbody>
				<!-- 리워드 선택하기(반복문처리할것) -->
				<tr>
				<td style="width: 100%;">리워드 선택하기(반복문)</td>
				</tr>
			
			</tbody>
		</table>

	

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