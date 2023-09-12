<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String id = ""; //초기화 값을 반드시 ""로 함 (null X) -> 이유? 쿠키에 저장된 id가 없을 경우 value로 셋팅되는 값이 ""가 될 수 있게
	String checked = "";
	
	/*
	//[방법-1] id와 체크된 상태까지 가져옴
	String cookie = request.getHeader("Cookie");//생략가능
	
	if(cookie != null){//생략가능
		
		Cookie[] cookies = request.getCookies(); //세션아이디도 배열에 저장되어 있음 (세션ID를 쿠키배열에 안 담는 브라우저도 있음)
		for(int i=0; i<cookies.length; i++){
			String cookieName = cookies[i].getName();
			
			//클라이언트의 쿠키에 저장된 아이디값을 가져오기
			if(cookieName.equals("u_id")){ //쿠키 중 name이 u_id인 쿠키가 있으면
				id = cookies[i].getValue();//쿠키 id의 value를 String u_id인에 저장 
				System.out.println("id쿠키값 : "+id);
			}

			//이전에 아이디 저장에 체크를 했다면, checked되도록 하기
			if(cookieName.equals("checkbox")){ //쿠키 중 name이 remember인 쿠키가 있으면
				checked = cookies[i].getValue();//쿠키 remember의 value를 String remember에 저장
				System.out.println("checkbox쿠키값 : "+checked);
			}
		}
	}
	*/
	
	//[방법-2] id만 가지고 옴
	Cookie[] cookies = request.getCookies();
	if(cookies != null && cookies.length > 0) {
		for(int i=0; i<cookies.length; i++){
			if(cookies[i].getName().equals("u_id")){
				id = cookies[i].getValue();
				checked="checked";
				break;
			}
			
		}
	}
	
	
	%>
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
<body>
	
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid page-header pt-4 pb-5 mb-5 wow fadeIn" data-wow-delay="0.1s">
        <div class="container text-center py-5">
            <h3 class="display-6 text-white mb-4 animated slideInDown">로그인</h3>
        </div>
    </div>
    <!-- Page Header End -->


    <!-- Form Start -->
    <div class="container-fluid pt-4 pb-5">
        <div class="container">
            <form action="userLoginAction.usr" method="post" name="f">
	            <div class="row justify-content-center">
	                <div class="col-8 col-md-6 col-lg-5 col-xl-4">
	                    <div class="bg-light rounded p-4 animated fadeIn" data-wow-delay="0.1s">                        
	                        <div class="row g-3">
	                            <div class="col-12">
	                                <div class="form-floating">
	                                    <input type="text" name="member_id" value="<%=id %>" class="form-control border-0" id="member_id" placeholder="아이디">
	                                    <label for="member_id">아이디</label>
	                                </div>
	                            </div>
	                            <div class="col-12">
	                                <div class="form-floating">
	                                    <input type="password" name="password" class="form-control border-0" id="password" placeholder="아이디">
	                                    <label for="password">비밀번호</label>
	                                </div>
	                            </div>
	                            <div class="col-12">
	                                <div class="form-check">
	                                    <input type="checkbox" name="checkbox" value="checked" <%=checked %> class="form-check-input border-0" id="checkbox">
	                                    <label class="form-check-label" for="checkbox">아이디 기억하기</label>
	                                </div>
	                            </div>
	                            <div class="col-12 text-center">
	                                <button class="btn btn-primary py-2 px-4" type="submit">로그인</button>
	                            </div>
	                        </div>
	                    </div>
	                    
	                    <ul class="nav justify-content-center mt-3 animated fadeIn">
						  <li class="nav-item">
						    <a class="nav-link text-secondary" href="userIdFindForm.usr">아이디 찾기</a>
						  </li>
						  <li class="nav-item">
						    <a class="nav-link text-secondary" href="userHashPwFindForm.usr">비밀번호 찾기</a>
						  </li>
						  <li class="nav-item">
						    <a class="nav-link text-secondary" href="userJoinForm.usr">회원가입</a>
						  </li>
						</ul>
	                </div>
	            </div>
            </form>
        </div>
    </div>
    <!-- Form End -->
		
	    
   
</body>
</html>