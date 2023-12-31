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

//정렬 요청
function changeOrder() {
	
	let selectedValue = document.getElementById("selectOrder").value;
	
	if(selectedValue != 'default'){
		document.forder.submit();
	}
	
}

//검색어 유효성 검사 및 검색요청
function searchMemberList() {
	
	let search_id = document.getElementById("member_id").value;
	
	if(search_id == ''){
		alert('검색어를 입력하세요.');
		return false;
	}
	
	document.fsearch.submit();
	
}

//편집을 취소버튼으로 변경하고 삭제버튼 보이기
function switchEditCancel(){
	
	//편집 버튼 클릭 시 -> 편집버튼을 취소버튼으로 변경하고, 선택삭제버튼 보이기
	switchBtn();
	
	//form-check클래스 div태그 안에 체크박스 태그 삽입
	const checkElements = document.getElementsByClassName('remove-th');
	for(let i=0; i<checkElements.length; i++){
		
		if(checkElements[i].classList.contains('d-none')){//숨김 상태면 보이기로 전환
			checkElements[i].classList.remove('d-none');
		}else{//보이기 상태면 숨김으로 전환
			checkElements[i].classList.add('d-none');
		}
	}
	
}

//편집을 취소로 변경. 삭제버튼 활성화
function switchBtn(){
	
	//편집을 취소로 변경
	//취소를 편집으로 변경
	const editBtn = document.getElementById("editBtn");
	const editBtnText = editBtn.innerText;
	
	if(editBtnText == "회원편집"){
		editBtn.innerText = "취소";
	}else if(editBtnText == "취소"){
		editBtn.innerText = "회원편집";
	}
	
	//삭제버튼 없으면 활성화
	//삭제버튼 있으면 비활성화
	const deleteBtn = document.getElementById("deleteBtn");
	if(deleteBtn.classList.contains('d-none')){
		deleteBtn.classList.remove('d-none');
	}else{
		deleteBtn.classList.add('d-none');
	}
	
}

//전체선택
function checkAll(theForm){
	
	if(theForm.remove.length == undefined){//폼의 remove(체크박스)배열의 길이가 정의되어 있지 않다면 == 항목이 1개만 있다면
		
		theForm.remove.checked = theForm.allCheck.checked; //전체선택 체크하면, 모든 항목이 체크됨
	
	}else{//항목이 2개 이상 있다면 -> 배열로 생성(같은이름(remove)의 checkbox)
	
		for(var i=0; i<theForm.remove.length; i++){
			theForm.remove[i].checked = theForm.allCheck.checked; //remove배열의 각 값 checked
		}
	}
	
}

function selectDelete(){
	
	const checkElements = document.getElementsByClassName('form-check-input');
	
	let isCheckboxChecked = false;
	for(let i=0; i<checkElements.length; i++){
		
		//하나라도 체크된 것이 있으면 체크여부 true로 변경하고 반복문 끝
		if(checkElements[i].checked == true){
			isCheckboxChecked = true;
			break;
		}		
	}
	
	if(!isCheckboxChecked){//체크된 것이 없으면
		return alert('선택된 항목이 없어 삭제할 수 없습니다.');
	
	}else{//체크된 것이 있으면
		
		if(confirm('회원을 삭제하면 회원 아이디를 제외한 모든 개인정보가 삭제됩니다. 정말로 삭제하시겠습니까?')){
			document.dlt.submit();
		}else{
			alert('회원 삭제를 취소합니다.');
			return false;
		}		
	}
	
	
}

</script>

<body>
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid pt-4 mb-5 wow fadeIn" data-wow-delay="0.1s" style="background-color:#86B381">
        <div class="container text-center pt-5">
            <h3 class="display-6 pb-3 text-white animated slideInDown">회원관리</h3>
            <div class="row justify-content-center">
	            <ul class="col-12 col-lg-8 nav nav-pills justify-content-center mt-4 mb-0">
					<li class="col-4 nav-item"><a class="nav-link text-white" href="manageMemberList.mngm">전체 회원</a></li>
					<li class="col-4 nav-item"><a class="nav-link active fw-bold" aria-current="page" href="#">미탈퇴 회원</a></li>
					<li class="col-4 nav-item"><a class="nav-link text-white" href="deletedMemberList.mngm">탈퇴 회원</a></li>
	            </ul>
            </div>
        </div>
    </div>
    <!-- Page Header End -->
    
    
    
    
	    
    <%-- Search Tab Start --%>
    <div class="container-fluid mb-3 pt-4 pb-3">
    	<div class="container col-lg-8 px-0">
    		<div class="row">
				
				<%-- Order --%>
				<div class="col-4 col-md-3">
					<div class="d-flex justify-content-start">
						<form action="orderUndeletedMemberList.mngm" method="post" name="forder">
			    			<select class="form-select py-1" name="selectOrder" id="selectOrder" aria-label="selectOrder" onchange="changeOrder()">
								<option value="default" selected>-- 정렬조건 --</option>
								<option value="new">최근 가입순</option>
								<option value="old">오래된 가입순</option>
								<option value="az">가나다순</option>
								<option value="za">역가나다순</option>
							</select>
						</form>
	    			</div>
	    		</div>
	    		
	    		<%-- Search --%>
	    		<div class="col auto">
		    		<div class="d-flex justify-content-end">
		    			<form action="searchUndeletedMemberList.mngm" method="post" name="fsearch">
			    			<div class="btn btn-outline-light py-1 px-2 me-1">
				    			<input type="text" name="member_id" value="${search_id}" id="member_id" class="border-0" placeholder="아이디로 검색">
				    			<a href="javascript:searchMemberList();"><i class="fas fa-search"></i></a>
			    			</div>
		    			</form>
	    			</div>
    			</div>
   			</div>
    	</div>
    </div>
    <%-- Search Tab End --%>
	
	<c:if test="${pageInfo.listCount == 0 }">
    	<div class="container-xxl mb-5 py-5" style="height:30vh">
    		<div class="container col-10 col-md-6 col-lg-4">
    			<div class="col-12 mb-5">
    				<c:if test="${search_id ne null }">
    					<p class="text-center">${search_id }이(가) 포함된 회원 아이디가 없습니다.</p>
    				</c:if>
    				<c:if test="${search_id eq null }">
    					<p class="text-center">가입한 회원이 없습니다.</p>
    				</c:if>
    			</div>
    		</div>
    	</div>
    </c:if>
	
	<c:if test="${pageInfo.listCount != 0 }">
    	<c:set var="m_index" value="${(pageInfo.page-1)*10 +1}" />
	
	    <%-- Table Start --%>
	    <form action="deleteMember.mngm" method="post" name="dlt">
	    <div class="container-fluid pt-0 pb-2">
	        <div class="container col-lg-8">
	            <div class="row justify-content-center">
					<table class="table table-hover">
						<thead>
							<tr class="text-center">
								<th scope="col" class="remove-th col-1 d-none"><input class="form-check-input" type="checkbox" name="allCheck" onclick="checkAll(this.form);"></th>
								<th scope="col" class="col-1">#</th>
								<th scope="col" class="col-auto">아이디</th>
								<th scope="col" class="col-3">가입일자</th>
								<th scope="col" class="col-3">탈퇴여부</th>
							</tr>
						</thead>
						<tbody class="table-group-divider">						
							<c:forEach var="member" items="${memberList}">
								<tr class="text-center">
									<th class="remove-th d-none">
										<input class="form-check-input" type="checkbox" name="remove" value="${member.member_id}">									
									</th>
									<th scope="row">${m_index}</th>
									<td><a href="memberView.mngm?member_id=${member.member_id}&page=${pageInfo.page}">${member.member_id }</a></td>
									<td>${member.joindate }</td>
									<td>${member.delete_status }</td>
								</tr>
								<c:set var="m_index" value="${m_index +1 }"/>
							</c:forEach>
						</tbody>
					</table>
					<hr>
				</div>
	        </div>		
	    </div>
	    <%-- Table End --%>
	    
	    <%-- Delete Button --%>
	    <div class="container-fluid mt-0 pt-0 pb-5">
	    	<div class="container col-lg-8 px-0">
	    		<div class="d-flex justify-content-end">
	    			<button class="btn btn-outline-primary float-right me-2 py-1 d-none" type="submit" id="deleteBtn" onclick="selectDelete(); return false;">선택추방</button>
	    			<button class="btn btn-outline-primary float-right py-1" type="button" id="editBtn" onclick="switchEditCancel();">회원편집</button>	    			
	    		</div>
	    	</div>
	   	</div>
	   	</form>
	    
	    	    
	    <%-- Pagination Start --%>
	    <div class="container-fluid mb-5 mt-0 pt-0 pb-5">
	    	<div class="container col-lg-4">
		    	<div class="row">
					<ul class="pagination justify-content-center">
						<li class="page-item">
							<c:if test="${pageInfo.page <= 1}">
								<a class="page-link" aria-label="Previous">
									<span aria-hidden="true">&laquo;</span>
								</a>	
							</c:if>
							<c:if test="${pageInfo.page > 1}">
								<a class="page-link" href="searchUndeletedMemberList.mngm?page=${pageInfo.page -1 }&member_id=${search_id}" aria-label="Previous">
									<span aria-hidden="true">&laquo;</span>
								</a>	
							</c:if>								
						</li>
						
						<c:forEach var="pNum" begin="${pageInfo.startPage }" end="${pageInfo.endPage}" step="1">
							<c:if test="${pNum eq pageInfo.page }">
								<li class="page-item active" aria-current="page"><a class="page-link">${pNum}</a></li>
							</c:if>
							<c:if test="${pNum ne pageInfo.page }">
								<li class="page-item"><a class="page-link" href="searchUndeletedMemberList.mngm?page=${pNum}&member_id=${search_id}">${pNum}</a></li>
							</c:if>
						</c:forEach>
						
						<li class="page-item">
							<c:if test="${pageInfo.page >= pageInfo.maxPage }">
								<a class="page-link" aria-label="Next">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</c:if>
							<c:if test="${pageInfo.page < pageInfo.maxPage }">
								<a class="page-link" href="searchUndeletedMemberList.mngm?page=${pageInfo.page +1 }&member_id=${search_id}" aria-label="Next">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</c:if>							
						</li>
					</ul>
		        </div>
	        </div>
	    </div>
	    <%-- Pagination Start --%>       
    
    </c:if>
    
</body>
</html>