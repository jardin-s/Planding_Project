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

<script type="text/javascript">

function noticeFormCheck(){
	
	//제목 데이터 유효성 검사
	if(document.f.n_title.value == ''){
		alert("제목을 입력해주세요.");
		document.f.n_title.focus();
		return false;
	}
	
	//내용 데이터 유효성 검사
	if(document.f.n_content.value == ''){
		alert('내용을 입력해주세요.')
		document.f.n_content.focus();
		return false;
	}
	
	//파일확장자 유효성 검사 (jpg, png, gif, bmp 이미지 파일만 가능)
	const regImageFile = /^[\S\s]+(\.(jpg|png|gif|bmp))$/i;
	if(document.f.n_image.value != ''){
		if(!regImageFile.test(document.f.n_image.value.trim())){
			alert("jpg, png, gif, bmp 확장자의 이미지 파일만 첨부가능합니다.");
			return false;
		}
	}
		
	//비밀글 값 가져오기
	var check_value = document.getElementById("checkbox");
	if(check_value.checked == true){
		document.f.importance.value = 'Y';
	}else{
		document.f.importance.value = 'N';
	}
				
	document.f.submit();
	
}


//기존파일을 삭제하고, 파일input태그 나타내기
function deleteFile(){
	
	//기존파일정보와 실제 값이 담긴 hidden태그
	var deleteFile = document.getElementById("dltFile");
	
	//해당 태그들의 부모태그를 가져와
	var parentDiv = deleteFile.parentNode;
	
	//자식태그인 두 태그를 삭제하고
	parentDiv.removeChild(deleteFile);
	
	//파일input HTML태그를 추가
	parentDiv.innerHTML = "<input type='file' name='n_image' class='form-control' id='n_image' aria-describedby='n_image' accept='image/*' aria-label='n_image' maxlength='100'>";
		
}

function setCheck(){
	var importance = document.getElementById("importance_chk").value;
	var checkbox = document.getElementById("checkbox");
	
	if(importance == 'Y'){
		checkbox.checked = true;
	}else{
		checkbox.checked = false;
	}
}

</script>

<body onload="javascript:setCheck();">
	
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid py-5 mb-5 wow fadeIn" data-wow-delay="0.1s" style="background-color:#86B381">
        <div class="container text-center py-5">
            <h3 class="display-6 text-white mb-3 animated slideInDown">공지사항</h3>
        </div>
    </div>
    <!-- Page Header End -->

    <!-- Table Start -->
    <div class="container-fluid pt-4 pb-4">
        <div class="container col-md-8 col-lg-7 col-xl-6 col-xxl-5">
            <div class="row justify-content-center">
				<form action="adminModifyNoticeAction.adm" method="post" name="f" enctype="multipart/form-data">
					<input type="hidden" name="page" value="${page}">
					<input type="hidden" name="notice_id" value="${noticeInfo.notice_id}">
					
					<input type="hidden" name="importance_chk" value="${noticeInfo.importance}" id="importance_chk">
					
					<div class="input-group mb-2">
						<span class="input-group-text" id="n_title">제목</span>
						<input type="text" name="n_title" value="${noticeInfo.n_title }" class="form-control" placeholder="제목을 입력하세요." aria-label="n_title" aria-describedby="n_title">
					</div>
					<div class="input-group mb-2">
						<span class="input-group-text">내용</span>
						<textarea name="n_content" class="form-control" placeholder="내용을 입력하세요." aria-label="n_content" rows="10">${noticeInfo.n_content }</textarea>
					</div>
					<div class="input-group mb-3">
						<c:if test="${noticeInfo.n_image eq null }">
							<input type="file" name="n_image" class="form-control" id="n_image" aria-describedby="n_image" accept="image/*" aria-label="n_image" maxlength="100">
						</c:if>
						<c:if test="${noticeInfo.n_image ne null }">
							<div id="dltFile"><i class="far fa-image me-2"></i>${noticeInfo.n_image }&nbsp;&nbsp;<a href="#" onclick="javascript:deleteFile();">삭제</a></div>
						</c:if>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="checkbox" name="checkbox" value="Y" id="checkbox" ${importanceChk }>
						<label class="form-check-label" for="checkbox">중요글 표시</label>
						<input type="hidden" name="importance"> 
					</div>
					
					<div class="col-12 text-center">
						<button type="submit" class="btn btn-primary" onclick="noticeFormCheck(); return false;">공지글 수정</button>
					</div>
				</form>
			</div>
        </div>		
    </div>   
    <!-- Table End -->
		
	    
</body>
</html>