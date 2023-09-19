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
    
    <!-- Custom Stylesheet -->
    <link href="../../resources/css/customStyle.css" rel="stylesheet">
    
</head>

<body>
	
	<!-- Page Header Start -->
    <div class="container-fluid py-4 mb-5 wow fadeIn" data-wow-delay="0.1s" style="background-color:#86B381">
        <div class="container text-center pt-5">
            <h3 class="display-6 py-3 text-white animated slideInDown">프로젝트 후원완료</h3>
        </div>
    </div>
	
	<div class="container-xxl mt-5 py-5">
   		<div class="container col-12 col-md-11">
   			<div class="mb-3">
   				
   			</div>
   			<div class="row justify-content-center">
   				<div class="col-12 col-lg-7">
   					<div class="d-flex justify-content-center">
   						<div class="col-3 mb-4">
   							<img alt="어머니 지구의 날 아이콘  제작자: Freepik - Flaticon" src="resources/img/icon/free-icon-mother-earth-day-9879134.png" width="100%">
   						</div>
   					</div>
   					<div class="text-center mb-5" style="line-height:2rem">
   						세상을 향한 따뜻한 손길 감사합니다.<br><br>
   						<c:if test="${projectInfo.kind eq 'donate' }">
   							후원하신 금액은 펀딩 종료 시 기획자에게 전달됩니다.
   						</c:if>
   						<c:if test="${projectInfo.kind eq 'fund' }">
   							후원하신 금액은 펀딩 성공 시 기획자에게 전달되며,<br>
   							펀딩 실패 시 후원자분께 다시 돌아갑니다.
   						</c:if>
   					</div>
   					<div class="text-center mt-5 mb-3">
   						<strong>[ 후원정보 ]</strong>
   					</div>
   					<div class="bg-light rounded p-4 mb-4 animated fadeIn" data-wow-delay="0.1s">                        
                        <div class="row g-3">
                            <div class="col-12">
                            	<div class="bg-white rounded">
                                    <p class="p-3" style="line-height:2rem">
                                    	<b><i class="far fa-file-powerpoint me-2 text-primary"></i>${projectInfo.title }</b><br>
                                    	&nbsp;${projectInfo.curr_amount_df }원
                                    	 | 
                                    	달성률 : ${projectInfo.progress }%
                                    	 | 
                                    	<c:if test="${projectInfo.deadline == 0 }">
                                    		오늘 마감
                                    	</c:if>
                                    	<c:if test="${projectInfo.deadline != 0 }">
	                                    	${projectInfo.progress }일 남음<br>
                                    	</c:if>                                    	
                                    </p>
                                </div>
                                <div class="bg-white rounded">
                                    <p class="p-3" style="line-height:2rem">
                                    	<b><i class="fas fa-gift me-2 text-primary"></i>리워드 : ${rewardInfo.r_name }</b><br>
                                    	&nbsp;${rewardInfo.r_content }<br>
                                    	&nbsp;리워드 금액 : ${rewardInfo.r_price }원<br> 
                                    	&nbsp;추가 후원금액 : ${add_donation }원<br>
                                    	&nbsp;<b>최종 후원금액</span>&nbsp;&nbsp;:&nbsp;&nbsp;${rewardInfo.r_price + add_donation}원</b><br> 
                                    </p>
                                </div>
                                <div class="bg-white rounded">
                                    <p class="p-3" style="line-height:2rem">
                                    	<b><i class="fas fa-truck me-2 text-primary"></i></i>배송지</b><br>
                                    	&nbsp;수령인 이름 : ${addressInfo.receiver_name }<br>
                                    	&nbsp;수령인 연락처 : ${addressInfo.receiver_phone }<br> 
                                    	&nbsp;우편번호 : ${addressInfo.postcode }<br> 
                                    	&nbsp;기본주소 : ${addressInfo.address1 }<br> 
                                    	&nbsp;상세주소 : ${addressInfo.address2 }<br> 
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="text-center">
						<a href="userDonationHistory.usr">나의 후원내역</a>
						<span style="font-size:0.9rem; color:#AAA">&nbsp;|&nbsp;</span>
						<c:if test="${projectInfo.kind eq 'donate' }">
							<a href="userOngoingDonateProjectList.pj">둘러보기</a>
						</c:if>
						<c:if test="${projectInfo.kind eq 'fund' }">
							<a href="userOngoingFundProjectList.pj">둘러보기</a>
						</c:if>						
						<span style="font-size:0.9rem; color:#AAA">&nbsp;|&nbsp;</span>
						<a href="userMain.usr">메인으로</a>
					</div>
                    
   				</div>
   				
   			</div>
   		</div>
   	</div>
   	
</body>
</html>