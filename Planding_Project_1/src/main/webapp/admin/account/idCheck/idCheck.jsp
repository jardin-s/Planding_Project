<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <title>아이디 중복 확인창</title>
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
function useCheckId(){
	opener.document.f.member_id.value = document.chk.ckid.value;
	opener.document.f.idChecked.value = "checked";
			
	window.close();
}

function cancel(){
	opener.document.f.member_id.value = "";
	opener.document.f.idChecked.value = "unchecked";
	
	window.close();
}
</script>

<body>
	
	
	<!-- Main Section -->
	<c:if test="${param.member_id ne null and requestScope.check_id eq null}">
		<c:set var="setId" value="${param.member_id }"/>
	</c:if>
	<c:if test="${param.member_id eq null and requestScope.check_id ne null}">
		<c:set var="setId" value="${requestScope.check_id }"/>
	</c:if>
	
	<div class="container-fluid py-2 px-3">
        <div class="row text-center">
            <div class="col-lg-6 py-5 border border-primary">
                <p class="fw-bold fs-4">아이디 중복 확인</p><br>
                <form action="adminIdCheckAction.adm" method="post" name="chk">
                	 <div class="mb-3 row justify-content-center g-0">
				    	<div class="col-10 text-center">
				      		<input type="text" class="form-control-sm col-11" name="ckid" id="ckid" value="${setId}" max-length="20" placeholder="중복체크할 아이디 입력">
					    </div>
	                    <div class="col-2 text-center">
	                        <button class="btn btn-sm btn-primary" type="submit">
	                        		<span style="font-size:1rem">확인</span>
	                        </button>	                                
	                    </div>
	                  </div>
                </form>
                
                <c:if test="${requestScope.isIdUsable == true }">
                	<p>사용가능한 아이디입니다.</p>
                	<button class="btn btn-sm btn-primary" type="button" onclick="useCheckId();">
                		<span style="font-size:1rem">사용하기</span>
                	</button>
                </c:if>
                <c:if test="${requestScope.isIdUsable == false }">
                	<p>이미 사용 중인 아이디입니다.</p>
                </c:if>
                
                <button class="btn btn-sm btn-primary" type="button" onclick="cancel();">
                	<span style="font-size:1rem">닫기</span>
                </button>
            </div>
        </div>
    </div>	
	
    
    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="../../resources/lib/wow/wow.min.js"></script>
    <script src="../../resources/lib/easing/easing.min.js"></script>
    <script src="../../resources/lib/waypoints/waypoints.min.js"></script>
    <script src="../../resources/lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="../../resources/lib/counterup/counterup.min.js"></script>
    <script src="../../resources/lib/parallax/parallax.min.js"></script>
    <script src="../../resources/lib/isotope/isotope.pkgd.min.js"></script>
    <script src="../../resources/lib/lightbox/js/lightbox.min.js"></script>

    <!-- Template Javascript -->
    <script src="../../resources/js/main.js"></script>
</body>
</html>