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

//프로젝트 승인하기
function authorizeProject(project_id){
	if(confirm('이 프로젝트를 승인하시겠습니까?')){
		location.href='authorizeProject.mngp?project_id='+project_id;
	}else{
		alert('승인 취소되었습니다.');
	}
}

//프로젝트 승인거절
function unauthorizeProject(project_id, kind){
	if(confirm('이 프로젝트의 승인을 거절하시겠습니까?')){
		location.href='unauthorizeProject.mngp?project_id='+project_id+'&kind='+kind;
	}else{
		alert('승인거절이 취소되었습니다.');
	}
}

//프로젝트 진행 취소
function cancelProject(project_id, p_status, kind){
	if(confirm('이 프로젝트의 진행을 취소하시겠습니까?')){
		location.href='cancelProjectForm.mngp?project_id='+project_id+'&p_status='+p_status+'&kind='+kind;
	}else{
		alert('프로젝트를 계속 진행합니다.');
	}
}

</script>

<body>
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid py-4 mb-5 wow fadeIn" data-wow-delay="0.1s" style="background-color:#86B381">
        <div class="container text-center py-5">
            <h3 class="display-6 py-3 text-white animated slideInDown">프로젝트 상세</h3>
        </div>
    </div>
    <!-- Page Header End -->
    
    
    <div class="container-xxl mb-5 py-5">
   		<div class="container col-10 col-md-8">
   			<div class="col-12 mb-5">
   				<h4 class="text-center mb-5">${projectInfo.title }</h4>
   				<table class="table table-bordered mb-5">
			    	<tr>
			    		<th colspan="2" class="text-center">
			    			<img src="images/project_No_${projectInfo.project_id }/${projectInfo.project_id }_${projectInfo.thumbnail }" style="width:80%">
			    		</th>
			    	</tr>
			    	<tr>
			    		<th class="text-center col-4">프로젝트 ID</th>
			    		<td>${projectInfo.project_id }</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">프로젝트 제목</th>
			    		<td>${projectInfo.title }</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">프로젝트 구분</th>
			    		<td>
			    			<c:if test="${projectInfo.kind eq 'donate'}">기부</c:if>
			    			<c:if test="${projectInfo.kind eq 'fund'}">펀딩</c:if>
			    		</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">프로젝트 상태</th>
			    		<td>
			    			<c:if test="${projectInfo.p_status eq 'unauthorized'}">미승인</c:if>
			    			<c:if test="${projectInfo.p_status eq 'ready'}">공개예정</c:if>
			    			<c:if test="${projectInfo.p_status eq 'ongoing'}">진행중</c:if>
			    			<c:if test="${projectInfo.p_status eq 'done'}">종료</c:if>
			    			<c:if test="${projectInfo.p_status eq 'success'}">성공</c:if>
			    		</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">등록일자</th>
			    		<td>${projectInfo.regdate}</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">시작일</th>
			    		<td>${projectInfo.startdate}</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">종료일</th>
			    		<td>${projectInfo.enddate}</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">목표 모금액</th>
			    		<td>${projectInfo.goal_amount}원</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">현재 모금액</th>
			    		<td>${projectInfo.curr_amount}원</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">달성률</th>
			    		<td>${projectInfo.progress}%</td>
			    	</tr>
			    	<tr>
			    		<th colspan="2" class="text-center">요약글</th>
			    	</tr>
			    	<tr>
			    		<td colspan="2" class="text-center">${projectInfo.summary}</td>
			    	</tr>
			    	<tr>
			    		<th colspan="2" class="text-center">내용</th>
			    	</tr>
			    	<tr>
			    		<td colspan="2" class="text-center">${projectInfo.content}</td>
			    	</tr>
			    	<tr>
			    		<th colspan="2" class="text-center">첨부 이미지</th>
			    	</tr>
			    	<tr>
			    		<td colspan="2" class="text-center">
			    			<c:forTokens var="image" items="${projectInfo.image }" delims=";">
								<img src="images/project_No_${projectInfo.project_id }/${projectInfo.project_id }_${image}" style="width:60%; margin-bottom: 2rem">
							</c:forTokens>
			    		</td>
			    	</tr>
			    </table>
			    
			    <h4 class="text-center mb-4">프로젝트 기획자</h4>
   				<table class="table table-bordered mb-5">
			    	<tr class="text-center">
			    		<th>기획자 ID</th>
			    		<td>${plannerInfo.member_id}</td>
			    		<th>기획자 이름</th>
			    		<td>${plannerInfo.planner_name}</td>
			    	</tr>
			    	<tr class="text-center">
			    		<th>계좌 은행</th>
			    		<td>
			    			<c:choose>
			    				<c:when test="${plannerInfo.bank eq 'shinhan' }">신한은행</c:when>
			    				<c:when test="${plannerInfo.bank eq 'kb' }">국민은행</c:when>
			    				<c:when test="${plannerInfo.bank eq 'woori' }">우리은행</c:when>
			    				<c:when test="${plannerInfo.bank eq 'hana' }">하나은행</c:when>
			    				<c:when test="${plannerInfo.bank eq 'ibk' }">기업은행</c:when>
			    				<c:when test="${plannerInfo.bank eq 'nh' }">농협은행</c:when>
			    				<c:when test="${plannerInfo.bank eq 'gn' }">경남은행</c:when>
			    				<c:when test="${plannerInfo.bank eq 'kwangju' }">광주은행</c:when>
			    				<c:when test="${plannerInfo.bank eq 'daegu' }">대구은행</c:when>
			    				<c:when test="${plannerInfo.bank eq 'busan' }">부산은행</c:when>
			    				<c:when test="${plannerInfo.bank eq 'kdb' }">산업은행</c:when>
			    				<c:when test="${plannerInfo.bank eq 'suhyub' }">수협은행</c:when>
			    				<c:when test="${plannerInfo.bank eq 'jb' }">전북은행</c:when>
			    				<c:when test="${plannerInfo.bank eq 'jeju' }">제주은행</c:when>
			    				<c:when test="${plannerInfo.bank eq 'keb' }">케이뱅크</c:when>
			    				<c:when test="${plannerInfo.bank eq 'kakaobank' }">카카오뱅크</c:when>
			    				<c:when test="${plannerInfo.bank eq 'shinhyup' }">신협은행</c:when>
			    				<c:when test="${plannerInfo.bank eq 'upost' }">우체국은행</c:when>
			    				<c:otherwise>${plannerInfo.bank }</c:otherwise>
			    			</c:choose>
			    		</td>
			    		<th>계좌번호</th>
			    		<td>${plannerInfo.account_num }</td>
			    	</tr>
			    	<tr class="text-center">
			    		<th colspan="4">소개글</th>
			    	</tr>
			    	<tr class="text-center">
			    		<td colspan="4">${plannerInfo.introduce }</td>
			    	</tr>
			    </table>
			    
			    <h4 class="text-center mb-4">리워드 정보</h4>
   				<table class="table table-bordered mb-5">
			    	<tr class="text-center">
			    		<th>리워드 ID</th>
			    		<th>리워드 이름</th>
			    		<th>리워드 내용</th>
			    		<th>리워드 가격</th>
			    	</tr>
			    	<c:forEach var="reward" items="${rewardList}">
			    		<tr class="text-center">
				    		<td>${reward.reward_id }</td>
				    		<td>${reward.r_name }</td>
				    		<td>${reward.r_content }</td>
				    		<td>${reward.r_price}원</td>
				    	</tr>
			    	</c:forEach>
			    </table>
			    
			   
			    <div class="col-12 text-center mx-auto">
			    	<c:if test="${projectInfo.p_status eq 'unauthorized'}">
	    				<button class="btn btn-outline-primary py-1" type="button" id="authBtn" onclick="authorizeProject(${projectInfo.project_id});">프로젝트 승인</button>
	    				<button class="btn btn-outline-primary py-1" type="button" id="unauthBtn" onclick="unauthorizeProject(${projectInfo.project_id}, '${projectInfo.kind }');">승인 거절</button>
	    			</c:if>
			    	<c:if test="${projectInfo.p_status eq 'ready' or projectInfo.p_status eq 'ongoing'}">
	    				<button class="btn btn-outline-primary py-1" type="button" id="cancelBtn" onclick="cancelProject(${projectInfo.project_id}, '${projectInfo.p_status }','${projectInfo.kind }');">프로젝트 취소</button>
	    			</c:if>
	    			<c:if test="${projectInfo.p_status eq 'done'}">
	    				<button class="btn btn-outline-primary py-1" type="button" id="refundBtn" onclick="location.href='refundAllDonation.mngp?project_id=${project.project_id}';">모금액 환불진행</button>
	    			</c:if>
	    			<c:if test="${projectInfo.p_status eq 'success' && adminIncome == 0}">
	    				<button class="btn btn-outline-primary py-1" type="button" id="sendBtn" onclick="location.href='sendTotalAmount.mngp?project_id=${project.project_id}'">송금</button>
	    			</c:if>
	    			<br><br>
			    	
			    	<button class="btn btn-light" onclick="history.back();">프로젝트 목록</button>
			    			    	
			    </div>
   			</div>
   		</div>
   	</div>
    
	    
</body>
</html>