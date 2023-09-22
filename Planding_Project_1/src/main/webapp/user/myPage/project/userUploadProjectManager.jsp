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
    <link href="../../resources/lib/animate/animate.min.css" rel="stylesheet">
    <link href="../../resources/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="../../resources/lib/lightbox/css/lightbox.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="../../resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="../../resources/css/style.css" rel="stylesheet">
    
    <!-- Custom Stylesheet -->
    <link href="../../resources/css/customStyle.css" rel="stylesheet">
    
    <style>
    .nav-pills > .nav-item > .active {
		background-color: #E8F5E9 !important; 
		color: #348E38 !important;
	}
    </style>
    <script type="text/javascript">

    function redirectToPage(url) {
        // 지정한 페이지로 리다이렉션
        window.location.href = url;
    }
    $(document).ready(function () {
        // 테이블의 스크롤 이벤트 처리
        $(".table-group-divider").scroll(function () {
            // 헤더 고정
            var scroll = $(".table-group-divider").scrollTop();
            $(".sticky-header").css("transform", "translate(0," + scroll + "px)");

            // 바닥글 고정
            var footerScroll = -scroll; // 바닥글은 위로 스크롤되어야 하므로 부호를 반대로 변경
            $(".sticky-footer").css("transform", "translate(0," + footerScroll + "px)");
        });
    });
    $(document).ready(function() {
        // 테이블 행 내의 모든 td 요소에 대해 툴팁을 즉시 표시하도록 설정
        $('tr[data-toggle="tooltip"] td').tooltip({
          trigger: 'manual',
        });

        // 테이블 행을 클릭할 때 툴팁을 표시하기 위한 이벤트 처리
        $('tr[data-toggle="tooltip"]').click(function() {
          $(this).find('td').tooltip('show');
        });
      });
    
 // 필터링 함수 리워드별로
    function filterTable() {
        var input, filter, table, tr, td, i, txtValue;
        input = document.getElementById("filterSelect");
        filter = input.value.toUpperCase();
        table = document.getElementById("donation_tbl");
        tr = table.querySelectorAll("tr");

        for (i = 1; i < tr.length; i++) {
            td = tr[i].querySelectorAll("td")[1]; // r_id 컬럼을 필터링할 경우 [1]로 변경
            if (td) {
                txtValue = td.textContent || td.innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1 || filter === "") {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        }
    }
 // 테이블 정렬 함수
       function sortTable(columnIndex) {
    const table = document.getElementById("donation_tbl");
    const tbody = table.querySelector("tbody");
    const rows = Array.from(tbody.querySelectorAll("tr"));
    const headers = table.querySelectorAll("th");
    const clickedHeader = headers[columnIndex];

    // 클릭된 열에 아이콘 추가 및 정렬 방향 결정
    if (clickedHeader.classList.contains("asc")) {
        clickedHeader.classList.remove("asc");
        clickedHeader.classList.add("desc");
        rows.sort((a, b) => {
            const cellA = a.querySelectorAll("td")[columnIndex].textContent;
            const cellB = b.querySelectorAll("td")[columnIndex].textContent;
            return cellB.localeCompare(cellA); // 내림차순 정렬
        });
    } else {
        clickedHeader.classList.remove("desc");
        clickedHeader.classList.add("asc");
        rows.sort((a, b) => {
            const cellA = a.querySelectorAll("td")[columnIndex].textContent;
            const cellB = b.querySelectorAll("td")[columnIndex].textContent;
            return cellA.localeCompare(cellB); // 오름차순 정렬
        });
    }

    // 정렬된 행을 다시 테이블에 추가
    for (const row of rows) {
        tbody.appendChild(row);
    }

    // 다른 열의 아이콘 초기화
    for (const header of headers) {
        if (header !== clickedHeader) {
            header.classList.remove("asc", "desc");
        }
    }
}

        // 초기화 함수: 페이지 로딩 후 테이블을 정렬할 기본 열을 지정할 수 있습니다.
        function initializeTable() {
            // 초기 정렬 기준
        	sortTable(0);
        }

        // 페이지 로딩 후 초기화 함수를 호출합니다.
        window.onload = initializeTable;
    </script>
    <style type="text/css">
    .sticky-header {
    position: sticky;
    top: 0;
    background-color: #fff; /* 헤더 배경색 지정 */
    z-index: 1; /* 다른 요소 위에 표시되도록 설정 */
	}
	
	.sticky-footer {
	    position: sticky;
	    bottom: 0;
	    background-color: #fff; /* 바닥글 배경색 지정 */
	    z-index: 1; /* 다른 요소 위에 표시되도록 설정 */
	}
	th {
            cursor: pointer;
        }
	 .asc::after {
        content: "\25B2"; /* Unicode up arrow character */
    }

    .desc::after {
        content: "\25BC"; /* Unicode down arrow character */
    }
    </style>
</head>
<body>
	
	<!-- Main Section -->
	<!-- Page Header Start -->
    <div class="container-fluid page-header pt-5 mb-5 wow fadeIn" data-wow-delay="0.1s">
        <div class="container text-center pt-5">
            <h3 class="display-6 text-white animated slideInDown">${sessionScope.u_name}님의 페이지</h3>
            <div class="row justify-content-center">
	            <ul class="col-12 col-lg-8 nav nav-pills justify-content-center mt-4 mb-0">
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userMyPage.usr">내 정보관리</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userDonationHistory.usr">내 후원내역</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link text-white" href="userMyQnaList.usr">내 문의글</a></li>
					<li class="col-6 col-md-3 nav-item"><a class="nav-link active fw-bold" aria-current="page" href="#">등록 프로젝트</a></li>
	            </ul>
            </div>
        </div>
    </div>
    <!-- Page Header End -->



    	<div class="container-xxl py-5">
    		<div class="container col-lg-8 mb-5">
   				<h3 class="text-center">${pj_title} 프로젝트<br>리워드 관리 페이지</h3>
    		</div>
    	</div>

	

    	
    	<!-- Table Start -->
	    <div class="container-fluid pt-0 pb-2">
	        <div class="container col-lg-8">
	            <div class="table-responsive" style="height:400px; overflow-y:auto">
					<table class="table table-hover">
						<thead class="sticky-header">
							<tr class="text-center" style="padding: 50px; white-space: nowrap;">
								<th scope="col" class="col-1" >#</th>
								<th scope="col" class="col-2">리워드명</th>
								<th scope="col" class="col-3" colspan="2">리워드설명</th>
								<th scope="col" class="col-2">신청자수</th>
								<th scope="col" class="col-2">최소금액</th>
								<th scope="col" class="col-2">총액</th>
							</tr>
						</thead>
						<tbody class="table-group-divider">
							<c:forEach var="i" begin="0" end="${rewardList.size() }" step="1" varStatus="count">
							 <tr class="text-center" onclick="window.location.href='byRewardDonationList.pj?reward_id=${rewardList[i].reward_id }'" data-bs-toggle="tooltip" data-bs-placement="top" title="클릭하여 리워드 후원목록 보기">
						        <td>
						            ${rewardList[i].reward_id }
						        </td>
						        <td>
						            ${rewardList[i].r_name }
						        </td>
						        <td style="text-overflow: ellipsis;" colspan="2">
						            ${rewardList[i].r_content }
						        </td>
						        <td>
						            ${byRewardCount[i]}
						        </td>
						        <td>
						            ${rewardList[i].r_price }
						        </td>
						        <td>
						            ${byRewardTotalPrice[i]}
						        </td>
						     </tr>
							</c:forEach>
						</tbody>
						<tfoot class="sticky-footer">
							<tr class="text-center">
							
								<th scope="col" class="col-1" colspan="2">
						<button class="btn btn-outline-primary py-1" type="button" id="answerBtn" 
						onclick="window.location.href='editProjectRewardList.pj?project_id=${project_id}'">리워드 수정</button>	
								</th>
								<th scope="col" class="col-1" colspan="2">
						<button class="btn btn-outline-primary py-1" type="button" id="answerBtn" 
						onclick="window.location.href='userProjectDonationListALL.pj?project_id=${project_id}'">전체 후원목록보기</button>
								</th>

								<th>총${totalCount}명</th>
								<th>전체총액</th>
								<th>${totalPrice}원</th>
							</tr>
						</tfoot>
					</table>
				</div>
	        </div>		
	    </div>
	    <%-- Table End --%>

	
	   
</body>
</html>