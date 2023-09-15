<%@page import="java.text.DecimalFormat"%>
<%@page import="vo.*"%>
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
   <style>
  /* 버튼 스타일 초기화 */
  .custom-button {
    background: none;
    border: none;
    outline: none;
    padding: 0;
    margin: 0;
    cursor: default;
  }

  /* 버튼 내부 span 스타일 지정 */
  .custom-button span {
    background-color: #007bff; /* 원하는 배경색 지정 */
    color: #fff; /* 텍스트 색상 */
    border-radius: 15px; /* 둥근 모서리를 위한 값, 원하는 값으로 조정 */
    padding: 5px 10px; /* 내부 여백 조정, 원하는 값으로 조정 */
  }

  /* 버튼에 마우스를 올렸을 때와 클릭했을 때의 스타일 지정 (반응하지 않도록 설정) */
  .custom-button:hover,
  .custom-button:active {
    background: none;
    border: none;
    outline: none;
    cursor: default;
  }
</style>
   

</head>
<body>
<%
String[] contentImgSysName=null;
ProjectBean pj=(ProjectBean)request.getAttribute("pj");
PlannerBean planner=(PlannerBean)request.getAttribute("planner");
RewardBean reward=(RewardBean)request.getAttribute("reward");


if(pj.getImage()!=null){
	
	contentImgSysName=pj.getImage().split(";");
}

DecimalFormat df = new DecimalFormat("#.#");

//progress 계산 및 소수점 첫째 자리까지 포맷팅
double progress = (double) pj.getCurr_amount() / pj.getGoal_amount();
String formattedProgress = df.format(progress);

progress=Double.parseDouble(formattedProgress);
%>
<c:set var="progress" value="<%= progress%>"/>

<div class="container">
		<div>
			작성하신 프로젝트를 확인하는 페이지 입니다. 
		</div>
    <div class="row">
        <div class="col-md-6 col-lg-4">
            <div><img src="<%= request.getContextPath() %>/images/temp/${projectInfo.thumbnail}"></div>
        </div>
        <div class="col-md-6 col-lg-8">
            <table class="table table-sm">
                <tr>
                    <th>${projectInfo.title }
	                    <button class="custom-button">
						<span class="badge bg-primary rounded-pill"> ${projectInfo.project_id}-${projectInfo.kind}</span>
						</button>
					</th>
                </tr>
                <tr>
                    <td>${projectInfo.summary}</td>
                </tr>
                <tr>
                    <td>플래너 : ${planner.planner_name }<br>플래너 한 마디 : ${planner.introduce }</td>
                </tr>
                <tr>
                    <th>기간:${projectInfo.startdate }~${projectInfo.enddate }</th>
                </tr>
                <tr>
                    <td>
                        <c:if test="${progress < 100 }">
                            <div class="progress" role="progressbar" aria-label="Example with label" aria-valuenow="${progress }" aria-valuemin="0" aria-valuemax="100">
                                <div class="progress-bar">목표 달성률 ${progress}%</div>
                            </div>	
                        </c:if>	
                        <c:if test="${progress >= 100 }">
                            <div class="progress" role="progressbar" aria-label="Danger example" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100">
                                <div class="progress-bar">${progress }%</div>
                                <div class="progress-bar bg-danger"></div><!-- if문으로 100이상일때 빨갛게 보이도록  -->
                            </div>	
                        </c:if>	
                    </td>
                </tr>
                <tr>
                    <td><button type="button" class="btn btn-danger">관심표현하기<span class="badge bg-primary rounded-pill">0</span></button>  <button type="button" class="btn btn-danger">${(kind  eq "donate") ? "기부하기" : "펀딩하기"}</button></td>
                </tr>
            </table>
        </div>
    </div>

	<table class="table table-sm col-md-6 col-lg-4">
		<tr>
			<td colspan="2">
				<%for(int i=contentImgSysName.length-1;i>=0;i--) {%>
				<img src="<%= request.getContextPath() %>/images/temp/<%= contentImgSysName[i] %>"><br>
				<%} %>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="text-break">${pj.content}</td>
		</tr>
	
	
	
	
	</table>

	<c:if test="${(pj.kind eq 'fund') and (reward.reward_id eq null) }">
		<a href="insertFundProjectReward.pj"><button>리워드 추가하기</button></a>
	</c:if>
	<table class="table table-sm col-md-6 col-lg-4">
		<tr>
			<td>
				<c:if test="${pj.kind eq 'fund' }">
					<ol class="list-group list-group-numbered">
						<li class="list-group-item d-flex justify-content-between align-items-start">
							<div class="ms-2 me-auto">
								<div class="fw-bold">나눔 더하기</div>
								추가 기부 금액 : <input type="number" name="r_price" min="1000" step="1" value="1000" readonly>
							</div>
						</li>
					</ol>
				</c:if>

				<c:if test="${pj.kind eq 'donate' }">
				<ol class="list-group list-group-numbered">
				
					
					<li class="list-group-item d-flex justify-content-between align-items-start">
						
<%-- 						<form action="<c:if test="${pj.status eq 'ongoing' }">newDonation.pj</c:if>" method="post" > --%>
						
						
						<div class="ms-2 me-auto">
							<div class="fw-bold">기부하실 금액을 입력해주세요.</div>
							<input type="number" name="r_price" min="1000" step="1000" value="1000">
						</div>
							<input type="button" value="기부하기" disabled="disabled">
<!-- 						</form> -->
					</li>
					
				</ol>
				</c:if>
			</td>
		</tr>
	</table>
		<div class="btn-group" role="group" aria-label="Basic example">
			아직<a href="editProject.pj"><button type="button" class="btn btn-primary">수정하기</button></a>
			못만듬<a href="deleteProject.pj"><button type="button" class="btn btn-danger">삭제하기</button></a>
		</div>
		<br><hr>
		<form action="submitProject.pj" method="post">
			<input type="text" name="project_id" value="${pj.project_id }" style="display: none">
			<input type="text" name="reward_id" value="${reward.reward_id }" style="display: none">
			<input type="text" name="editStatus" value="unauthorized" style="display: none">
			<input type="submit" value="제출하기">
		</form>
		<p>제출하신 프로젝트는 검토 후 게시됩니다.</p>

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

</html>