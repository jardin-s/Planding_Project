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

	<div>
		<jsp:include page="../user/userHeader.jsp" />
	</div>

	<div class="container-fluid pt-5 pb-2">
		<div class="container col-lg-8">
			<div class="row g-3 justify-content-center">
				<!-- 제목 -->
				<div class="col-12 text-center">
					펀딩 프로젝트
				</div>
				<div class="col-12 text-center">
					<h1 class="display-6">풍요로운 대자연의 선물 : 흙</h1>
				</div>
				
				<!-- 상품 헤더 -->
				<!-- 좌측 썸네일 이미지 -->
				<div class="col-md-6 p-3 text-center">
					<img src="../resources/img/service-1.jpg" style="width:100%">
				</div>
				
				<!-- 우측 프로젝트 정보 -->
				<div class="col-md-6 pt-4 px-4">
					<table class="table">
						<thead>
							<tr class="text-start">
								<th class="py-2">
									<span class="fw-normal">&nbsp;현재 모금액</span><br>
									&nbsp;<span style="font-size:2rem; font-weight:normal">11,535,400</span>&nbsp;원
								</th>					
							</tr>
							<tr class="text-start">
								<th class="py-2">
									<span class="fw-normal">&nbsp;달성률</span>
									<div class="progress col-10 mt-2" role="progressbar" aria-label="Basic example" aria-valuenow="2307" aria-valuemin="0" aria-valuemax="100" style="height:20px">
										<div class="progress-bar" style="width: 2307%">2307%</div>
									</div>
								</th>					
							</tr>
							<tr class="text-start">
								<th class="py-2">
									<span class="fw-normal">&nbsp;남은 시간</span><br>
									&nbsp;<span style="font-size:2rem; font-weight:normal">9</span>&nbsp;일
								</th>					
							</tr>
							<tr class="text-start">
								<th class="py-3">
									&nbsp;목표 금액 <span class="fw-normal ms-2">500,000원</span><br>
									&nbsp;후원 기간 <span class="fw-normal ms-2">2023.9.17 ~ 2023.9.27</span>
								</th>					
							</tr>
						</thead>
						<tbody>
							<form>
								<tr>
									<td class="pt-3">
										<div class="row">
											<div class="col-8 my-auto">
											<select class="form-select form-select-lg mb-3">
												<option selected>리워드 선택</option>
												<optgroup label="1,000원">
													<option value="1">리워드 없이 후원하기</option>
												</optgroup>
												<optgroup label="39,800원">
													<option value="1">[얼리버드] '흙' 향수 1개 (2차)</option>
												</optgroup>
												<optgroup label="76,600원">
													<option value="1">[얼리버드] '흙' 향수 1개 + 이전향수(흙 포함) 1개 골라담기 (3차)</option>
												</optgroup>
												<optgroup label="113,400원">
													<option value="1">[얼리버드] '흙' 향수 1개 + 이전향수(흙 포함) 2개 골라담기 (2차)</option>
												</optgroup>
											</select>
											</div>
											<div class="col-4">
												<button type="submit" class="btn btn-primary btn-lg px-4 d-block d-md-none d-xl-block" onclick="checkDonate(); return false;">
													<i class="fas fa-donate py-1 d-block d-xxl-none"></i><span class="d-none d-xxl-block">후원하기</span>
												</button>
												<button type="submit" class="btn btn-primary py-2 px-4 d-none d-md-block d-xl-none" onclick="checkDonate(); return false;">
													<i class="fas fa-donate"></i>
												</button>
											</div>
										</div>
										<input class="form-control" type="text" placeholder="추가 후원금 (선택)">	
									</td>
								</tr>
							</form>
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
								<td>언더 더 파고라</td>
							</tr>
							<tr>
								<td>소개글</td>
								<td>향과 거품이 만들어낸 쉼터아래... 휴식을 만드는 브랜드 언더 더 파고라 입니다.</td>
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
									<img src="../resources/img/service-1.jpg" style="width:60%; margin-bottom: 2rem">
									<img src="../resources/img/service-2.jpg" style="width:60%; margin-bottom: 2rem">
									<img src="../resources/img/service-3.jpg" style="width:60%; margin-bottom: 2rem">
									<img src="../resources/img/service-4.jpg" style="width:60%; margin-bottom: 2rem">
								</th>
							</tr>
						</thead>
						<tbody>
							<tr class="text-center">
								<td style="padding:50px">
								먼저 소개해 드린 쐐기와 늪처럼 - 흙의 아주 처음이자, 전체 숲 시리즈의 첫 시작은 로맨틱 판타지 풍으로 소개해 드렸던 저희의 첫 번째 향수 프로젝트에서 이어지는 이야기로 로맨틱 판타지 스토리 속 여주인공이 여름 숲속 피크닉을 떠나 만나게 된 향 이란 주제로 
								<br><br>
								1. 숲의 풀 향 섞인 바람<br>
								2. 레몬에이드를 마신 여주인공의 숨결<br>
								3. 우연히 마주치게 된 남주인공의 첫인상의 향 <br>
								<br><br>
								...이라는 내용의 3가지 향을 각각 혹은 서로 레이어링 하여 사용할 수 있는 제품들을 소개하는 프로젝트를 준비하고 있었는데요.
								<br><br><br>
								 
								
								하지만 저희의 첫 향수 프로젝트는 아쉽게도 그렇게 많은 분들의 관심을 받지 못하고 마무리 짓게 되었고 저희는 동일 컨셉으로 준비 중이던 숲 시리즈 - 전체 제품의 방향성, 소개 페이지, 디자인, 향 등등... 모든 부분을 재정비하고 새로이 구상하는 시간을 갖게 되었습니다. 어쩜 이 일련의 과정과 배경이 정신적으로나 심적으로 꽤 소모적이었던 걸까요? 
								<br><br>
								
								제 기억은 제가 많이 외롭고 지쳐있던 시절 - 항상 찾던 곳...
								<br><br><br>
								 
								
								숲 시리즈의 근간이 되었던 그 숲의 일부이자 저희 브랜드의 첫 시작이 된 그 장소 '파고라'로 돌아가게 됩니다.
								<br><br>
								때는 200 n연도, 아버지 사업으로 가족들과 함께 이사를 가게 되었던 중국에서 중, 고등학교를 마치고 대학 진학을 위하여 이번엔 홀로 영국 런던으로 떠나게 됩니다. 
								<br><br><br>
								 
								
								제가 입학 전 상상하던 대학 캠퍼스의 모습은 밝은 분위기의 젊음 넘치는 자유롭고 즐거운 청춘들이 모여 있는 그런 모습이었지만...
								<br>
								현실은 영국의 날씨를 반영하듯 100년은 된 듯한 우중충하고 먼지 낀 교실에 주 3회 모여 서로의 작품에 대한 공개적인 날선 크리틱(비평)의 시간을 갖고 다시 흩어져 자신들의 작품을 이를 갈며(?) 보완해서 다시 다음 크리틱의 시간을 준비하는 게 일상이었던... 다소 씁쓸한 기억이 떠오르네요. 
								<br><br><br>
								 
								
								그 시기엔 늘 지쳐있고 - 분하다 못해 암울해져 있고, 통장잔고는 늘 간당간당 - 불안하고 초조하던 기억... 그땐 꽤나 우울하고 외로웠던 것 같아요. 
								<br><br><br>
								 
								
								그러던 하루는 집으로 돌아와 우울함도 떨치고 동네도 좀 더 익힐 겸 씩씩하게 산책을 나갔습니다.
								<br><br><br><br>
								 
								
								 
								
								그리곤 그렇게 길을 잃어버렸습니다... 
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<!-- 리워드 세부사항 -->
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
							<tr>
								<td class="col-4">[얼리버드] '흙' 향수 1개 (2차)</td>
								<td class="col-auto">흙 EDP 50ml ( x 1 )</td>
								<td class="col-2">39,800원</td>
							</tr>
							<tr>
								<td class="col-4">[얼리버드] '흙 '향수 1개 + 이전향수(흙 포함) 1개 골라담기 (3차)</td>
								<td class="col-auto">흙 EDP 50ml ( x 1 ) + 흙 or 이전 향수 골라담기 ( x 1 )</td>
								<td class="col-2">76,600원</td>
							</tr>
							<tr>
								<td class="col-4">[얼리버드] '흙'향수 1개 + 이전향수( 흙 포함) 2개 골라담기 (2차)</td>
								<td class="col-auto">흙 EDP 50ml ( x 1 ) + 흙 or 이전 향수 골라담기 ( x 2 )</td>
								<td class="col-2">113,400원</td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<div class="col-12 text-center">
					<button class="btn btn-light" onclick="location.href='userQnaList.usr?page=${page}'">프로젝트 목록</button>
				</div>
			</div>
		</div>
	</div>
	
	<div>
		<jsp:include page="../user/userFooter.jsp" />
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