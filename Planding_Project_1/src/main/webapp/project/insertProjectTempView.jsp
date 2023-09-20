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

<script>
function confirmDeleteTemp(){
	if(!confirm('지금까지 입력된 모든 정보가 삭제됩니다. 등록을 취소할까요?')){
		alert('입력 정보를 유지합니다.');
	}else{
		location.href='deleteTempProject.pj';
	}
}
</script>

<body>
<%

ProjectBean projectInfo = (ProjectBean)session.getAttribute("projectInfo");
int goal_amount = projectInfo.getGoal_amount();
int curr_amount = (int)(goal_amount * 0.75);//예시로 보여주는 것이므로 임의로 75%로 지정


//현재모금액, 목표모금액 천단위 구분쉼표 넣기
DecimalFormat df = new DecimalFormat("###,###");
String curr_amount_str = df.format(curr_amount);
String goal_amount_str = df.format(goal_amount);

/*
String[] contentImgSysName = null; //프로젝트 이미지 (split해서 뿌려야 함)
ProjectBean project = (ProjectBean)session.getAttribute("projectInfo");

if(project.getImage()!=null){//프로젝트 이미지가 있으면
	//;을 기준으로 나누어 각 이미지파일이름을 배열에 저장
	contentImgSysName = project.getImage().split(";");
}
*/

//남은 일수 : TempViewAction에서 종료일-시작일 뺀 숫자로 deadline세팅
%>
	
	<div class="container-fluid pt-5 pb-2">
		<div class="container col-lg-8">
			<div class="row g-3 justify-content-center">
				<!-- 제목 -->
				<div class="col-12 text-center">
					<span class="text-danger fw-bold">** 프로젝트 상세페이지 미리보기 **</span>
				</div>
				<div class="col-12 text-center">
					<c:if test="${projectInfo.kind eq 'donate' }">기부 프로젝트</c:if>
					<c:if test="${projectInfo.kind eq 'fund' }">펀딩 프로젝트</c:if>
				</div>
				<div class="col-12 text-center">
					<h1 class="display-6">${projectInfo.title }</h1>
				</div>
				
				<!-- 상품 헤더 -->
				<!-- 좌측 썸네일 이미지 -->
				<div class="col-md-6 p-3 text-center">
					<img src="<%=request.getContextPath()%>/images/temp/${projectInfo.thumbnail }" style="width:100%">
				</div>
				
				<!-- 우측 프로젝트 정보 -->
				<div class="col-md-6 pt-4 px-4">
					<table class="table">
						<thead>
							<tr class="text-start">
								<th class="py-2">
									<span class="fw-normal">&nbsp;현재 모금액</span><br>
									<span style="font-size:2rem; font-weight:normal"><%=curr_amount_str %></span>&nbsp;원
								</th>					
							</tr>
							<tr class="text-start">
								<th class="py-2">
									<span class="fw-normal">&nbsp;달성률</span>
									<div class="progress col-10 mt-2" role="progressbar" aria-label="Basic example" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="height:20px">
										<div class="progress-bar" style="width: 75%">75%</div>
									</div>
								</th>					
							</tr>
							<tr class="text-start">
								<th class="py-2">
									<span class="fw-normal">&nbsp;남은 시간</span><br>
									&nbsp;<span style="font-size:2rem; font-weight:normal">${projectInfo.deadline }</span>&nbsp;일
								</th>					
							</tr>
							<tr class="text-start">
								<th class="py-3">
									&nbsp;목표 금액 <span class="fw-normal ms-2"><%=goal_amount_str %>원</span><br>
									&nbsp;후원 기간 <span class="fw-normal ms-2">${projectInfo.startdate } ~ ${projectInfo.enddate }</span>
								</th>					
							</tr>
						</thead>
						<tbody>
							<tr>
								<form>
									
									<td class="pt-3">
										<div class="row">
											<div class="col-8 my-auto">
												<c:if test="${projectInfo.kind eq 'donate' }">
													<select class="form-select form-select-lg mb-3" name="reward_id">
														<option value="default" selected>1000원 (최소후원금)</option>
													</select>
												</c:if>
												<c:if test="${projectInfo.kind eq 'fund' }">
													<select class="form-select form-select-lg mb-3" name="reward_id">
														<option selected>리워드 선택</option>
														<optgroup label="1,000원">
															<option value="default">리워드 없이 후원하기</option>
														</optgroup>
														<c:forEach var="reward" items="${rewardList}">
															<optgroup label="${reward.r_price}원">
																<option>${reward.r_name }</option>
															</optgroup>
														</c:forEach>
													</select>
												</c:if>
											</div>
											<div class="col-4">
												<button type="button" class="btn btn-primary btn-lg px-4 d-block d-md-none d-xl-block">
													<i class="fas fa-donate py-1 d-block d-xxl-none"></i><span class="d-none d-xxl-block">후원하기</span>
												</button>
												<button type="button" class="btn btn-primary py-2 px-4 d-none d-md-block d-xl-none">
													<i class="fas fa-donate"></i>
												</button>
											</div>
										</div>
										<input class="form-control" type="text" name="add_donation" id="add_donation" placeholder="추가 후원금 (선택)">	
									</td>
								</form>
							</tr>
							<tr class="text-center">
								<td class="py-3">
									<button type="button" class="btn btn-light" style="width:100%"><i class="fas fa-heart me-2"></i>관심프로젝트 추가</button>
								</td>				
							</tr>
						</tbody>
					</table>
				</div>
				<!-- 상품 헤더 끝 -->
				
				<!-- 상품 바디 -->
				<!-- 기획자 정보 -->
				<div class="col-12">
					<table class="table">
						<thead>
							<tr>
								<th colspan="2">기획자 정보</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>기획자명</td>
								<td>${plannerInfo.planner_name }</td>
							</tr>
							<tr>
								<td>소개글</td>
								<td>${plannerInfo.introduce }</td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<!-- 프로젝트 세부사항 -->
				<div class="col-12">
					<table class="table">
						<thead>
							<tr>
								<th colspan="3">프로젝트 상세</th>
							</tr>
							<tr class="text-center">
								<th>
									<c:forTokens var="image" items="${projectInfo.image }" delims=";">
										<img src="<%=request.getContextPath()%>/images/temp/${image}" style="width:60%; margin-bottom: 2rem">
									</c:forTokens>
								</th>
							</tr>
						</thead>
						<tbody>
							<tr class="text-center">
								<td style="padding:50px">
								${projectInfo.content }
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<!-- 리워드 세부사항 (펀딩 프로젝트만 보임) -->
				<c:if test="${projectInfo.kind eq 'fund' }">
					<div class="col-12">
						<table class="table">
							<thead>
								<tr>
									<th>리워드 이름</th>
									<th>세부내용</th>
									<th>금액</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="reward" items="${rewardList}">
									<tr>
										<td class="col-4">${reward.r_name}</td>
										<td class="col-auto">${reward.r_content }</td>
										<td class="col-2">${reward.r_price }원</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
				
				
				<div class="col-12 text-center">
					<p class="mb-2">제출하신 프로젝트는 검토 후 승인을 거쳐 최종적으로 등록이 됩니다.</p>
					<button class="btn btn-light" onclick="history.back();">이전 단계로</button>
					<button class="btn btn-light" onclick="confirmDeleteTemp();">등록 취소</button>
					<c:if test="${projectInfo.kind eq 'donate' }">
						<button class="btn btn-light" onclick="location.href='submitDonateProjectAction.pj'">제출하기</button>
					</c:if>
					<c:if test="${projectInfo.kind eq 'fund' }">
						<button class="btn btn-light" onclick="location.href='submitFundProjectAction.pj'">제출하기</button>
					</c:if>					
				</div>
			</div>
		</div>
	</div>
	    
</body>

</html>