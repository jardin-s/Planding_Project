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

function confirmDelete(member_id){
	
	if(!confirm('회원을 정말 삭제할까요? 확인을 누르시면 취소할 수 없습니다.')){
		alert('회원 삭제를 취소합니다.');
		return false;
	}else{
		location.href='deleteMember.mngm?member_id='+member_id;
	}
	
}

</script>

<body>
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid py-4 mb-5 wow fadeIn" data-wow-delay="0.1s" style="background-color:#86B381">
        <div class="container text-center pt-5">
            <h3 class="display-6 py-3 text-white animated slideInDown">회원상세</h3>
        </div>
    </div>
    <!-- Page Header End -->
    
    
    <div class="container-xxl mb-5 py-5">
   		<div class="container col-12 col-md-8 col-lg-6">
   			<div class="col-12 mb-5">
   				<h4 class="text-center mb-4">${memberInfo.member_id }의 상세정보</h4>
   				<table class="table table-bordered mb-5">
			    	<tr>
			    		<th class="text-center">아이디</th>
			    		<td>${memberInfo.member_id }</td>
			    		<th class="text-center">이름</th>
			    		<td>
			    			<c:if test="${memberInfo.name eq 'delete'}">-</c:if>
			    			<c:if test="${memberInfo.name ne 'delete'}">${memberInfo.name }</c:if>
			    		</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">이메일</th>
			    		<td>
			    			<c:if test="${memberInfo.email eq 'delete'}">-</c:if>
			    			<c:if test="${memberInfo.email ne 'delete'}">${memberInfo.email }</c:if>
			    		</td>
			    		<th class="text-center">전화번호</th>
			    		<td>
			    			<c:if test="${memberInfo.phone eq 'delete'}">-</c:if>
			    			<c:if test="${memberInfo.phone ne 'delete'}">${memberInfo.phone }</c:if>
			    		</td>
			    	</tr>
			    	<tr>
			    		<th class="text-center">가입일</th>
			    		<td>${memberInfo.joindate }</td>
			    		<th class="text-center">계좌잔액</th>
			    		<td>${memberInfo.money }원</td>
			    	</tr>
			    	<c:if test="${memberInfo.deletedate ne null}">
			    		<tr>
				    		<th class="text-center" colspan="2">탈퇴일</th>
				    		<td class="text-center" colspan="2">${memberInfo.deletedate }</td>
				    	</tr>
			    	</c:if>
			    </table>
			    
			    <h4 class="text-center mb-4">등록된 배송지</h4>
			    <c:if test="${memberInfo.deletedate ne null }">
			   		<div class="text-center my-3">※리워드 배송을 위해 탈퇴 후 6개월 뒤 삭제됩니다.</div>
			    </c:if>
   				<table class="table table-bordered mb-5">
			    	<tr class="text-center">
			    		<th>우편번호</th>
			    		<th>주소</th>
			    		<th>상세주소</th>
			    		<th>수령인</th>
			    		<th>전화번호</th>
			    	</tr>
			    	<c:if test="${addressList eq null }">
			    		<tr class="text-center">
			    			<td colspan="5">등록된 주소가 없습니다.</td>
			    		</tr>
			    	</c:if>
			    	<c:if test="${addressList ne null }">
			    		<c:forEach var="address" items="${addressList }">
				    		<tr class="text-center">
					    		<td>${address.postcode }</td>
					    		<td>${address.address1}</td>
					    		<td>${address.address2}</td>
					    		<td>${address.receiver_name}</td>
					    		<td>${address.receiver_phone}</td>
					    	</tr>
				    	</c:forEach>
			    	</c:if>			    	
			    </table>
			    
			    <h4 class="text-center mb-4">후원기록</h4>
   				<table class="table table-bordered mb-5">
			    	<tr class="text-center">
			    		<th>프로젝트 ID</th>
			    		<th>리워드 ID</th>
			    		<th>총 후원금액</th>
			    		<th>후원일자</th>
			    	</tr>
			    	<c:if test="${donationList eq null }">
			    		<tr class="text-center">
			    			<td colspan="4">후원기록이 없습니다.</td>
			    		</tr>
			    	</c:if>
			    	<c:if test="${donationList ne null }">
				    	<c:forEach var="donation" items="${donationList }">
				    		<tr class="text-center">
					    		<td>${donation.project_id}</td>
					    		<td>${donation.reward_id }</td>
					    		<td>${donation.totalDonation}원</td>
					    		<td>${donation.donatedate}</td>
					    	</tr>
				    	</c:forEach>
			    	</c:if>
			    </table>
			    
			    <h4 class="text-center mb-4">작성한 문의글</h4>
   				<table class="table table-bordered">
			    	<tr class="text-center">
			    		<th>문의글 ID</th>
			    		<th>문의글 제목</th>
			    		<th>답변등록 여부</th>
			    		<th>작성일자</th>
			    	</tr>
			    	<c:if test="${qnaList eq null }">
			    		<tr class="text-center">
			    			<td colspan="4">작성한 문의글이 없습니다.</td>
			    		</tr>
			    	</c:if>
			    	<c:if test="${qnaList ne null }">
			    		<c:forEach var="qna" items="${qnaList }">
				    		<tr class="text-center">
					    		<td>${qna.qna_id}</td>
					    		<td>${qna.q_title }</td>
					    		<td>
					    			<c:if test="${qna.a_writer ne null }">답변완료</c:if>
					    			<c:if test="${qna.a_writer eq null }">
					    				<button class="btn btn-outline-primary py-0" type="button" id="answerBtn" onclick="location.href='adminInsertQnaAForm.adm?qna_id=${qna.qna_id}&page=1'">답변하기</button>
					    			</c:if>
					    		</td>
					    		<td>${qna.q_time}</td>
					    	</tr>
				    	</c:forEach>
			    	</c:if>
			    </table>
			    
			    <div class="col-12 text-center mt-5 mx-auto">
			    	<button class="btn btn-light" type="button" onclick="location.href='manageMemberList.mngm'">회원 목록</button>
			    	<c:if test="${memberInfo.delete_status eq 'N' }">
			    		<button class="btn btn-light" type="button" onclick="confirmDelete('${memberInfo.member_id}');">회원 삭제</button>
			    	</c:if>			    				    	
			    </div>
   			</div>
   		</div>
   	</div>
    
	    
	    
    
</body>
</html>