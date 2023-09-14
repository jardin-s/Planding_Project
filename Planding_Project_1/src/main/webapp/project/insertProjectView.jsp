<%@page import="vo.ProjectBean"%>
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
<%
String[] contentImgSysName=null;
ProjectBean pj=(ProjectBean)request.getAttribute("pj");
if(pj.getImage()!=null){
	
	contentImgSysName=pj.getImage().split(";");
}
%>
	<div class="container">
		<table class="table table-sm col-md-6 col-lg-4">
			<caption>${pj.project_id}-${pj.kind}</caption>
			
			<tr>
			    <td rowspan="6"><img src="<%= request.getContextPath() %>/images/temp/${pj.thumbnail}"></td>
			    <th>${pj.title }</th>
			</tr>
			<tr>
				<td>${pj.summary}</td>
			</tr>
			<tr>
				<td>플래너 : ${planner.planner_name }<br>플래너 한 마디 : ${planner.introduce }</td>
			</tr>
			<tr>
				<th>기간:${pj.startdate }~${pj.enddate }</th>
			</tr>
			<tr>
				<td>
			<c:if test="${pj.progress < 100 }">
				<div class="progress" role="progressbar" aria-label="Example with label" aria-valuenow="${pj.progress }" aria-valuemin="0" aria-valuemax="100">
				  	<div class="progress-bar">목표 달성률 ${pj.progress }%</div>
				</div>	
			</c:if>	
			<c:if test="${pj.progress >= 100 }">
				<div class="progress" role="progressbar" aria-label="Danger example" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100">
					<div class="progress-bar">${pj.progress }%</div>
				  	<div class="progress-bar bg-danger"></div><!-- if문으로 100이상일때 빨갛게 보이도록  -->
				</div>	
			</c:if>	
				</td>
			</tr>
			<tr>
				<td><button type="button" class="btn btn-danger">관심표현하기<span class="badge bg-primary rounded-pill">0</span></button>  <button type="button" class="btn btn-danger">${(kind  eq "donate") ? "기부하기" : "펀딩하기"}</button></td>
			</tr>
			
			    <tr>
			    	 <td colspan="2">
			    	<%for(int i=contentImgSysName.length-1;i>=0;i--) {%>
			        
			            
			               <img src="<%= request.getContextPath() %>/images/temp/<%= contentImgSysName[i] %>"><br>
			            
			        
			        <%} %>
			        </td>
			    </tr>
			
			
			<tr>
			<td colspan="2" class="text-break">${content}</td>
			</tr>
			
			
			
			
		</table>

		<c:if test="${rewardBean.reward_id eq null }">
			<a href="insertFundProjectReward.pj"><button>리워드 추가하기</button></a>
		</c:if>
		<table class="table table-sm col-md-6 col-lg-4">
			
				<!-- 리워드 선택하기(반복문처리할것) -->
				<tr>
				<td>
				<ol class="list-group list-group-numbered">
				
				<c:forEach var="i" begin="0" end="${r_name.length }" step="1">
				  <li class="list-group-item d-flex justify-content-between align-items-start">
				    <div class="ms-2 me-auto">
				      <div class="fw-bold">${r_name[i] }</div>
				     	<input type="number" name="r_price" min="1000" step="1000" value="${r_price }">
				    </div>
				  </li>
				</c:forEach> 
				  
				 
				</ol>
				</td>
				</tr>
			
		</table>

	</div>

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

</html>s