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
</head>

<script type="text/javascript">
//검색어 유효성 검사 및 검색요청
function searchNoticeList() {
	
	let search_title = document.getElementById("n_title").value;
	
	if(search_title == ''){
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
	
	if(editBtnText == "편집"){
		editBtn.innerText = "취소";
	}else if(editBtnText == "취소"){
		editBtn.innerText = "편집";
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
		
		if(confirm('정말로 해당 공지글들을 삭제하시겠습니까?')){
			document.dlt.submit();
		}else{
			alert('공지글 삭제를 취소합니다.');
			return false;
		}		
	}
	
	
}
</script>

<body>
	
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid py-5 mb-5 wow fadeIn" data-wow-delay="0.1s" style="background-color:#86B381">
        <div class="container text-center py-5">
            <h3 class="display-6 text-white mb-4 animated slideInDown">공지사항</h3>
        </div>
    </div>
    <!-- Page Header End -->
    
    <c:if test="${pageInfo.listCount == 0 }">
    	<div class="container-xxl py-5">
    		<div class="container col-10 col-md-6 col-lg-4">
    			<div class="col-12 mb-5">
    				<p class="text-center">작성된 공지글이 없습니다.</p>
    			</div>
    			<div class="col-12 text-center mx-auto mb-5">
					<button class="btn btn-outline-primary py-1" type="button" onclick="location.href='adminInsertNoticeForm.adm'">공지글 작성</button>
				</div>
    		</div>
    	</div>
    </c:if>
    
    <c:if test="${pageInfo.listCount != 0 }">
    	<c:set var="n_index" value="${pageInfo.listCount - (pageInfo.page-1)*10 }" />
	    
	    <%-- Search Tab Start --%>
	    <div class="container-fluid pt-4 pb-3">
	    	<div class="container col-lg-8 px-0">
	    		<div class="d-flex justify-content-end">
					<form action="searchNoticeList.adm" method="post" name="fsearch">
		    			<div class="btn btn-outline-light py-1 px-2 me-1">
			    			<input type="text" name="n_title" id="n_title" value="${searchKeyword }" class="border-0 me-2" placeholder="제목으로 검색">
			    			<a href="javascript:searchNoticeList();"><i class="fas fa-search"></i></a>
		    			</div>
		    		</form>
	    		</div>
	    	</div>
	    </div>
	    <%-- Search Tab End --%>
	
	    <%-- Table Start --%>
	    <form action="DeleteNoticeList.adm" method="post" name="dlt">
	    <div class="container-fluid pt-0 pb-2">
	        <div class="container col-lg-8">
	            <div class="row justify-content-center">
					<table class="table table-hover">
						<thead>
							<tr>
								<th scope="col" class="remove-th d-none text-center"><input class="form-check-input" type="checkbox" name="allCheck" onclick="checkAll(this.form);"></th>
								<th scope="col" class="col-1 text-center">#</th>
								<th scope="col" class="col-7">제목</th>
								<th scope="col" class="col-2 text-center">날짜</th>
								<th scope="col" class="col-2 text-center">조회수</th>
							</tr>
						</thead>
						<tbody class="table-group-divider">
							<c:forEach var="notice" items="${noticeList}">
								<tr>
									<th class="remove-th d-none">
										<input class="form-check-input" type="checkbox" name="remove" value="${notice.notice_id}">
									</th>
									<th scope="row" class="text-center">${n_index}</th>
									<td><a href="adminNoticeView.adm?notice_id=${notice.notice_id}&page=${pageInfo.page}">${notice.n_title }</a></td>
									<td class="text-center">${notice.writetime }</td>
									<td class="text-center">${notice.viewcount }</td>
								</tr>
								<c:set var="n_index" value="${n_index -1 }"/>
							</c:forEach>
						</tbody>
					</table>
				</div>
	        </div>		
	    </div>
	    <%-- Table End --%>
	    
	    <%-- Write Button --%>
	    <div class="container-fluid mt-0 pt-0 pb-5">
	    	<div class="container col-lg-8 px-0">
	    		<div class="d-flex justify-content-end">
	    			<button class="btn btn-outline-primary float-right py-1" type="button" onclick="location.href='adminInsertNoticeForm.adm'">공지글 작성</button>
	    		</div>
	    	</div>
	   	</div>
	    
	    <%-- Delete Button --%>
	    <div class="container-fluid mt-0 pt-0 pb-5">
	    	<div class="container col-lg-8 px-0">
	    		<div class="d-flex justify-content-end">
	    			<button class="btn btn-outline-primary float-right me-2 py-1 d-none" type="submit" id="deleteBtn" onclick="selectDelete(); return false;">선택삭제</button>
	    			<button class="btn btn-outline-primary float-right py-1" type="button" id="editBtn" onclick="switchEditCancel();">편집</button>	    			
	    		</div>
	    	</div>
	   	</div>
	   	</form>
	    	    
	    <%-- Pagination Start --%>
	    <div class="container-fluid mt-0 pt-0 pb-5">
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
								<a class="page-link" href="searchNoticeList.adm?page=${pageInfo.page -1 }&n_title=${searchKeyword}" aria-label="Previous">
									<span aria-hidden="true">&laquo;</span>
								</a>	
							</c:if>								
						</li>
						
						<c:forEach var="pNum" begin="${pageInfo.startPage }" end="${pageInfo.endPage}" step="1">
							<c:if test="${pNum eq pageInfo.page }">
								<li class="page-item active" aria-current="page"><a class="page-link">${pNum}</a></li>
							</c:if>
							<c:if test="${pNum ne pageInfo.page }">
								<li class="page-item"><a class="page-link" href="searchNoticeList.adm?page=${pNum}&n_title=${searchKeyword}">${pNum}</a></li>
							</c:if>
						</c:forEach>
						
						<li class="page-item">
							<c:if test="${pageInfo.page >= pageInfo.maxPage }">
								<a class="page-link" aria-label="Next">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</c:if>
							<c:if test="${pageInfo.page < pageInfo.maxPage }">
								<a class="page-link" href="searchNoticeList.adm?page=${pageInfo.page +1 }&n_title=${searchKeyword}" aria-label="Next">
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