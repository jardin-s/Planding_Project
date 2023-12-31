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
</head>

<script type="text/javascript">

function qnaFormCheck(){
	
	//제목 데이터 유효성 검사
	if(document.f.q_title.value == ''){
		alert("제목을 입력해주세요.");
		document.f.q_title.focus();
		return false;
	}
	
	//내용 데이터 유효성 검사
	if(document.f.q_content.value == ''){
		alert('내용을 입력해주세요.')
		document.f.q_content.focus();
		return false;
	}
	
	//파일확장자 유효성 검사 (jpg, png, gif, bmp 이미지 파일만 가능)
	const regImageFile = /^[\S\s]+(\.(jpg|png|gif|bmp))$/i;
	if(document.f.q_image.value != ''){
		if(!regImageFile.test(document.f.q_image.value.trim())){
			alert("jpg, png, gif, bmp 확장자의 이미지 파일만 첨부가능합니다.");
			return false;
		}
	}
		
	//비밀글 값 가져오기
	var check_value = document.f.checkbox.checked ? "Y" : "N";
	document.f.q_private.value = check_value;
	
	
	document.f.submit();
	
}

</script>

<body>
	
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid page-header py-5 mb-5 wow fadeIn" data-wow-delay="0.1s">
        <div class="container text-center py-5">
            <h3 class="display-6 text-white mb-5 animated slideInDown">문의사항</h3>
            <nav aria-label="breadcrumb animated slideInDown">
                <ol class="breadcrumb justify-content-center mb-0">
                    <li class="breadcrumb-item"><a href="#">메인</a></li>
                    <li class="breadcrumb-item"><a href="#">고객센터</a></li>
                    <li class="breadcrumb-item active" aria-current="page">문의사항</li>
                </ol>
            </nav>
        </div>
    </div>
    <!-- Page Header End -->


    <!-- Table Start -->
    <div class="container-fluid pt-4 pb-4">
        <div class="container col-md-8 col-lg-7">
            <div class="row justify-content-center">
				<form action="userInsertQnaQAction.usr" method="post" name="f" enctype="multipart/form-data">
					<input type="hidden" name="q_writer" value="${sessionScope.u_id}">
					<div class="input-group mb-2">
						<span class="input-group-text" id="q_title">제목</span>
						<input type="text" name="q_title" class="form-control" placeholder="제목을 입력하세요." aria-label="q_title" aria-describedby="q_title">
					</div>
					<div class="input-group mb-2">
						<span class="input-group-text">내용</span>
						<textarea name="q_content" class="form-control" placeholder="내용을 입력하세요." aria-label="q_content" rows="10"></textarea>
					</div>
					<div class="input-group mb-3">
						<input type="file" name="q_image" class="form-control" id="q_image" accept="image/*" aria-describedby="q_image" aria-label="q_image" maxlength="100">
					</div>
					<div class="form-check">
						<input class="form-check-input" type="checkbox" name="checkbox" value="Y" id="checkbox">
						<label class="form-check-label" for="checkbox">비밀글로 하기</label>
						<input type="hidden" name="q_private"> 
					</div>
					<div class="col-12 text-center">
						<button type="submit" class="btn btn-primary" onclick="qnaFormCheck(); return false;">문의글 등록</button>
					</div>
				</form>
			</div>
        </div>		
    </div>   
    <!-- Table End -->   
		
</body>
</html>