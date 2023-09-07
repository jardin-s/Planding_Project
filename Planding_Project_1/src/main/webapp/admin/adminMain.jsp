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
        
    <!-- chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
	
	<!-- Facts Start -->
    <div class="container-fluid mb-5 py-5">
        <div class="container">
            <div class="row justify-content-center">
            	<div class="bg-light rounded p-4 p-sm-5 animated fadeIn" data-wow-delay="0.1s">
	            	<div class="row g-3 justify-content-center">
	            		<!-- 수익 차트 -->
		            	<div class="col-6 text-center">
		            		<h4 class="text-center text-primary pb-3">최근 6개월 간 수익</h4>
		                    <canvas id="myChart" width="100%" height="50px"></canvas>
		                </div>
		                <!-- 가입 회원수 -->
		                <div class="col-6">
		                	<div class="d-flex align-items-center">
		                		<div class="row justify-content-center text-center">
				                	<h1 class="display-4 text-white" data-toggle="counter-up">1234</h1>
				                	<h5 class="fs-5 fw-semi-bold text-dark">가입 회원 수</h5>
			                	</div>
		                	</div>
		                </div>
	                </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Facts End -->
	
    <!-- Facts Start -->
    <div class="container-fluid facts mb-5 py-5" data-parallax="scroll" data-image-src="../resources/img/carousel-1.jpg">
        <div class="container py-5">
            <div class="row g-5">
                <div class="col-6 col-lg-3 text-center wow fadeIn" data-wow-delay="0.1s">
                    <h1 class="display-4 text-white" data-toggle="counter-up">1234</h1>
                    <span class="fs-5 fw-semi-bold text-light">승인대기 프로젝트</span>
                </div>
                <div class="col-6 col-lg-3 text-center wow fadeIn" data-wow-delay="0.3s">
                    <h1 class="display-4 text-white" data-toggle="counter-up">1234</h1>
                    <span class="fs-5 fw-semi-bold text-light">공개예정 프로젝트</span>
                </div>
                <div class="col-6 col-lg-3 text-center wow fadeIn" data-wow-delay="0.5s">
                    <h1 class="display-4 text-white" data-toggle="counter-up">1234</h1>
                    <span class="fs-5 fw-semi-bold text-light">진행중인 프로젝트</span>
                </div>
                <div class="col-6 col-lg-3 text-center wow fadeIn" data-wow-delay="0.7s">
                    <h1 class="display-4 text-white" data-toggle="counter-up">1234</h1>
                    <span class="fs-5 fw-semi-bold text-light">성공한 프로젝트</span>
                </div>
            </div>
        </div>
    </div>
    <!-- Facts End -->
    
    <!-- About Start -->
    <div class="container-xxl pb-5">
        <div class="container">
            <div class="row g-5 align-items-start">
                <!-- 승인 대기중인 새로 신청이 올라온 프로젝트들 -->
                <div class="col-lg-6 col-md-12 wow fadeInUp" data-wow-delay="0.1s">
                    <h3 class="text-primary mb-0">승인 대기 중</h3>
                    <p class="text-secondary mb-4">최근 신청이 올라온 프로젝트들입니다.</p>
                    <table class="table">
                    	<thead>
                    		<tr>
	                    		<th scope="col">#</th>
	                    		<th scope="col">프로젝트 제목</th>
	                    		<th scope="col">신청자</th>
                    		</tr>
                    	</thead>
                    	<tbody>
                    		<tr>
                    			<td>1</td>
                    			<td>지구에 생기를 불러오는 천연..</td>
                    			<td>testuser0001</td>
                    		</tr>                    		
                    	</tbody>
                    </table>
                </div>
                <!-- 답변 대기 중인 최근 올라온 문의사항 -->
                <div class="col-lg-6 col-md-12 wow fadeInUp" data-wow-delay="0.1s">
                    <h3 class="text-primary mb-0">답변 대기 중</h3>
                    <p class="text-secondary mb-4">최근 올라온 문의사항들입니다.</p>
                    <table class="table">
                    	<thead>
                    		<tr>
	                    		<th scope="col">#</th>
	                    		<th scope="col">문의글 제목</th>
	                    		<th scope="col">작성자</th>
                    		</tr>
                    	</thead>
                    	<tbody>
                    		<tr>
                    			<td>1</td>
                    			<td>지구에 생기를 불러오는 천연..</td>
                    			<td>testuser0001</td>
                    		</tr>                    		
                    	</tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- About End -->
    
    <!-- About Start -->
    <!-- 최근 등록한 공지글 --> 
    <div class="container-xxl py-5">
        <div class="container">
            <div class="row g-5 align-items-start">
                <!-- 승인 대기중인 새로 신청이 올라온 프로젝트들 -->
                <div class="col-12 wow fadeInUp" data-wow-delay="0.1s">
                    <h3 class="text-primary mb-0">승인 대기 중</h3>
                    <p class="text-secondary mb-4">최근 신청이 올라온 프로젝트들입니다.</p>
                    <table class="table">
                    	<thead>
                    		<tr>
	                    		<th scope="col">#</th>
	                    		<th scope="col">프로젝트 제목</th>
	                    		<th scope="col">신청자</th>
                    		</tr>
                    	</thead>
                    	<tbody>
                    		<tr>
                    			<td>1</td>
                    			<td>지구에 생기를 불러오는 천연..</td>
                    			<td>testuser0001</td>
                    		</tr>                    		
                    	</tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- About End -->
    

	

	<script>
	function addZero(i) {
		var rtn = i + 100;//i=이번달+1. i가 9일 때, trn = 109
		return rtn.toString().substr(1, 3);//"109".substr(1,3) (index1부터 3개 추출) -> "09"
	}

	var monthList = [];//'월' 배열 : 값이 비어있는 배열 선언
	var monthData = [50,60,70,45,50,66];//각 달에 들어갈 데이터

	var dt = new Date();//오늘날짜
	var year = dt.getFullYear();//올해 연도 2023
	var mon = addZero(eval(dt.getMonth()+1));//(이번달 9)로 addZero호출 ->"09" (getMonth 1월0~12월11)		
	var now = year+mon;//2023+"09"=202309

	for(var i = (now - 5); i <= now; i++){//최근 6개월 데이터 불러오기 202304~202309
		var format =  i;
		monthList.push(format);//위 monthList배열에 format값을 push
	}

	const ctx = document.getElementById('myChart').getContext('2d');//2D그래픽의 차트 가져오기
	const myChart = new Chart(ctx, {
		type: 'line',//선 그래프 타입
		data: {
			labels: monthList,//아래 표시할 라벨 : 각 월 배열
			datasets: [{//각 라벨의 데이터세팅
				data: monthData,//데이터값 : monthData 배열의 값들
				borderColor:'rgba(54, 124, 72, 1)',
				borderWidth: 3
			}]
		},

		options: {
		  responsive: true,
		  plugins: {
			legend: {
			  display: false
			},
			title: {
			},
			elements: {
			  borderCapStyle: 'round'
			}
		  }
		}
	});
	</script>
    
    
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