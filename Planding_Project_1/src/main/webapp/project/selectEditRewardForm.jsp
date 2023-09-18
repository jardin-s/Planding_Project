<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>PlanDing - Fund for Our Plannet</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<!-- Favicon -->
<link href="../resources/img/favicon.ico" rel="icon">

<!-- Google Web Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Jost:wght@500;600;700&family=Open+Sans:wght@400;500&display=swap"
	rel="stylesheet">

<!-- Icon Font Stylesheet -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
	rel="stylesheet">

<!-- Libraries Stylesheet -->
<link href="../resources/lib/animate/animate.min.css" rel="stylesheet">
<link href="../resources/lib/owlcarousel/assets/owl.carousel.min.css"
	rel="stylesheet">
<link href="../resources/lib/lightbox/css/lightbox.min.css"
	rel="stylesheet">

<!-- Customized Bootstrap Stylesheet -->
<link href="../resources/css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="../resources/css/style.css" rel="stylesheet">

<script>

//금액 천단위 구분쉼표
function updateFormattedAmount(input) {
    // 입력된 금액을 가져옵니다.
    var amount = input.value.replace(/\D/g, ''); // 숫자 이외의 문자 제거

    // 3자리마다 쉼표를 추가하여 포맷합니다.
    var formattedAmount = amount.replace(/\B(?=(\d{3})+(?!\d))/g, ',');

    // 포맷된 금액을 입력란에 설정합니다.
    input.value = formattedAmount;
}

//리워드 추가 함수
function addReward() {
    var rewardFields = document.querySelectorAll('[id^=rewardField]');
    for (var i = 0; i < rewardFields.length; i++) {
        if (rewardFields[i].style.display === 'none') {
            rewardFields[i].style.display = 'block';

            // 추가한 리워드 필드의 input 요소들에 required 속성 추가
            var inputs = rewardFields[i].querySelectorAll('input[type="text"], textarea');
            for (var j = 0; j < inputs.length; j++) {
                inputs[j].setAttribute('required', 'required');
            }

            return; // 이미 숨겨진 필드를 보이게 한 후 종료
        }
    }
}

//리워드 삭제 함수
function deleteReward(button) {
    var rewardFieldContainer = button.parentNode;
    rewardFieldContainer.style.display = 'none';

    // 필드의 값을 초기화
    var inputs = rewardFieldContainer.querySelectorAll('input[type="text"], textarea');
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].value = '';
    }

    // 필드의 required 속성 제거
    var requiredInputs = rewardFieldContainer.querySelectorAll('input[required], textarea[required]');
    for (var i = 0; i < requiredInputs.length; i++) {
        requiredInputs[i].removeAttribute('required');
    }
}

</script>
</head>
<body>

    	<!-- Table Start -->
	    <div class="container-fluid pt-0 pb-2">
	        <div class="container col-lg-8">
	            <div class="row justify-content-center" style="height:400px; overflow-y:auto">
					<table class="table table-hover">
						<thead class="sticky-header">
							<tr class="text-center">
								<th scope="col" class="col-1">#</th>
								<th scope="col" class="col-3">리워드명</th>
								<th scope="col" class="col-auto">리워드설명</th>
								<th scope="col" class="col-2">리워드금액</th>
							</tr>
						</thead>
						<tbody class="table-group-divider">
<%-- 							<c:forEach var="i" begin="0" end="${rewardList.size()-1 }" step="1" varStatus="count"> --%>
							 <tr class="text-center" onclick="editProjectRewardForm.pj?reward_id=${rewardList[i].reward_id }&project_id=${project_id}" data-bs-toggle="tooltip" data-bs-placement="top" title="클릭하여 리워드 수정 및 삭제">
						        <td>
						            ${rewardList[i].reward_id }
						        </td>
						        <td>
						            ${rewardList[i].r_name }
						        </td>
						        <td>
						            ${rewardList[i].r_content }
						        </td>
						        <td>
						            ${rewardList[i].r_price }
						        </td>
						     </tr>
<%-- 							</c:forEach> --%>
						</tbody>
						<tfoot class="sticky-footer">
							<tr class="text-center">
							
								<th scope="col" class="col-1" colspan="4">
						<button class="btn btn-outline-primary py-1" type="button" id="answerBtn" 
						onclick="insertAddRewardForm.pj?project_id=${pj.project_id}&reward_Count=${rewardList.size()}">리워드 추가</button>
								</th>
							</tr>
						</tfoot>
					</table>
				</div>
	        </div>		
	    </div>
	    <%-- Table End --%>
	


	<!-- JavaScript Libraries -->
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="../resources/lib/wow/wow.min.js"></script>
	<script src="../resources/lib/easing/easing.min.js"></script>
	<script src="../resources/lib/waypoints/waypoints.min.js"></script>
	<script src="../resources/lib/owlcarousel/owl.carousel.min.js"></script>
	<script src="../resources/lib/counterup/counterup.min.js"></script>
	<script src="../resources/lib/parallax/parallax.min.js"></script>
	<script src="../resources/lib/isotope/isotope.pkgd.min.js"></script>
	<script src="../resources/lib/lightbox/js/lightbox.min.js"></script>

	<!-- Template Javascript -->
	<script src="../resources/js/main.js"></script>
</body>
</html>