<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    
    <!-- Custom Stylesheet (style modify) -->
    <link href="../resources/css/customStyle.css" rel="stylesheet">
    
    
    <style>
        .service-item:hover .service-text .btn{
            width: 98px !important;
        }

        /* 가로 배치 */
        @media (min-width: 768px) {
            .vertical-center {
                align-items: center;
            }
        }

        /* 세로 배치 */
        @media (max-width: 767px) {
            .horizontal-center {
                text-align: center;
            }
        }
        
         /* 버튼의 크기가 글자 크기에 영향을 받지 않도록 설정 */
                .service-item:hover .service-text .btn {
            flex-grow: 0;
        }

        /* .vertical-center와 .horizontal-center 클래스 추가 */
        .vertical-center {
            display: flex;
            align-items: center;
        }

        .horizontal-center {
            display: flex;
            justify-content: center;
        }
    </style>
</head>
<body>
    <!-- Main Section -->
    <div class="container my-5">
        <div class="text-center mx-auto wow fadeInUp" data-wow-delay="0.1s" style="max-width: 500px;">
            <p class="fs-5 fw-bold text-primary">새 프로젝트 등록하기</p>
            <h1 class="display-6 mb-5">프로젝트를 등록하여<br>기부 또는 투자를 받으세요.</h1>
        </div>
        <div class="row g-4 justify-content-center">
            <div class="col-md-5 wow fadeInUp horizontal-center">
                <div class="service-item rounded d-flex h-100 vertical-center">
                    <div class="service-text rounded p-5">
                        <h4 class="mb-3">기부 프로젝트</h4>
                        <p class="mb-4">취약계층이나 동물, 환경을 보호하기 <br>위해 기부 받고자 한다면</p>
                        <a class="btn btn-sm" href="donateProjectInsert.pj?u_id=${u_id }"><i class="fas fa-plus me-2"></i>등록하기</a>
                    </div>
                </div>
            </div>
            <div class="col-md-5 wow fadeInUp horizontal-center">
                <div class="service-item rounded d-flex h-100 vertical-center">
                    <div class="service-text rounded p-5">
                        <h4 class="mb-3">펀딩 프로젝트</h4>
                        <p class="mb-4">지구를 위한 아이템을 개발하기 위해<br>투자 받고자 한다면</p>
                        <a class="btn btn-sm" href="fundProjectInsert.pj?u_id=${u_id }"><i class="fas fa-plus me-2"></i>등록하기</a>
                    </div>
                </div>
            </div>
        </div>
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
