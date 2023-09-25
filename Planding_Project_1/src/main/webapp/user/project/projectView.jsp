<%@page import="java.text.DecimalFormat"%>
<%@page import="vo.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<%

/* 
ProjectBean projectInfo = (ProjectBean)request.getAttribute("projectInfo");
int curr_amount = projectInfo.getCurr_amount();
int goal_amount = projectInfo.getGoal_amount();

//현재모금액, 목표모금액 천단위 구분쉼표 넣기
DecimalFormat df = new DecimalFormat("###,###");
String curr_amount_str = df.format(curr_amount);
String goal_amount_str = df.format(goal_amount);
*/

%>

<script>
//후원하기 폼 유효성 체크
function checkDonate() {
	
	//숫자 정규식 (직접입력 시)
	const regNum = /^[0-9]+$/;
	
	
	//리워드 선택여부 체크 (무조건 하나는 선택)
	if(document.f.reward_id.value == ''){
		alert('리워드를 선택해주세요.');
		document.f.reward_id.focus();
		return false;
	}else{
		if(document.f.add_donation.value != ''){
			
			//추가후원금 숫자입력 검사
			if(!regNum.test(document.f.add_donation.value)){
				alert('추가후원금은 숫자만 입력가능합니다.');
				document.f.add_donation.select();
				return false;
			}
		}
	}
	
	document.f.submit();
}


//그외 처리해야 할 내용
//projectList 요청 시, 페이지네이션으로 한 페이지당 n개씩 출력하고
//projectView 요청 시, 해당 페이지의 page값이 넘어가게 하고, sql에서 오늘날짜-종료일로 날짜계산해서 setDeadline()하기
//프로젝트 후원하기 -> 후원 폼 보기 -> 최종 후원완료

</script>

<body>

	<div class="container-fluid pt-5 pb-2">
		<div class="container col-lg-8">
			<div class="row g-3 justify-content-center">
				<!-- 제목 -->
				<div class="col-12 text-center">
					<c:if test="${projectInfo.kind eq 'donate' }">기부 프로젝트</c:if>
					<c:if test="${projectInfo.kind eq 'fund' }">펀딩 프로젝트</c:if>
				</div>
				<div class="col-12 text-center">
					<h1 class="display-6">${projectInfo.title }</h1>
				</div>
				
				<!-- 상품 헤더 -->
				<!-- 좌측 썸네일 이미지 -->
				<div class="col-md-6 p-3 text-center">
					<img src="images/project_No_${projectInfo.project_id }/${projectInfo.thumbnail }" style="width:100%">
				</div>
				
				<!-- 우측 프로젝트 정보 -->
				<div class="col-md-6 pt-4 px-4">
					<table class="table">
						<thead>
							<tr class="text-start">
								<th class="py-2">
									<span class="fw-normal">&nbsp;현재 모금액</span><br>
									&nbsp;<span style="font-size:2rem; font-weight:normal">${projectInfo.curr_amount_df }</span>&nbsp;원
								</th>					
							</tr>
							<tr class="text-start">
								<th class="py-2">
									<span class="fw-normal">&nbsp;달성률</span>
									<div class="progress col-10 mt-2" role="progressbar" aria-label="Basic example" aria-valuenow="${projectInfo.progress }" aria-valuemin="0" aria-valuemax="100" style="height:20px">
										<div class="progress-bar" style="width: ${projectInfo.progress }%">${projectInfo.progress }%</div>
									</div>
								</th>					
							</tr>
							<tr class="text-start">
								<th class="py-2">
									<c:choose>
										<c:when test="${projectInfo.p_status eq 'ongoing' }">
											<c:if test="${projectInfo.deadline == 0 }">
												&nbsp;<span style="font-size:1.2rem; font-weight:normal">오늘 자정 마감</span>&nbsp;
											</c:if>
											<c:if test="${projectInfo.deadline != 0 }">
												<span class="fw-normal">&nbsp;남은 시간</span><br>
												&nbsp;<span style="font-size:2rem; font-weight:normal">${projectInfo.deadline }</span>&nbsp;일
											</c:if>
										</c:when>
										<c:when test="${projectInfo.p_status eq 'done' }">
											<span class="fw-normal" style="font-size:0.8rem">&nbsp;종료되었습니다.</span><br>
										</c:when>
										<c:when test="${projectInfo.p_status eq 'success' }">
											<span class="fw-normal" style="font-size:0.8rem">&nbsp;여러분들의 소중한 후원으로 프로젝트가 성공적으로 마감했습니다.</span><br>
										</c:when>
										<%-- 미승인 또는 공개예정의 경우 --%>
										<c:otherwise>
											<span class="fw-normal">&nbsp;공개일까지</span><br>
											&nbsp;<span style="font-size:2rem; font-weight:normal">${projectInfo.deadline }</span>&nbsp;일
										</c:otherwise>
									</c:choose>
								</th>					
							</tr>
							<tr class="text-start">
								<th class="py-3">
									&nbsp;목표 금액 <span class="fw-normal ms-2">${projectInfo.goal_amount_df}원</span><br>
									&nbsp;후원 기간 <span class="fw-normal ms-2">${projectInfo.startdate } ~ ${projectInfo.enddate }</span>
								</th>					
							</tr>
						</thead>
						<tbody>
							<tr>
								<!-- 진행중인 프로젝트만 후원하기 표시 -->
								<c:if test="${projectInfo.p_status eq 'ongoing' }">
									<form action="userDonateProjectForm.pj" method="post" name="f">
										<input type="hidden" name="project_id" value="${projectInfo.project_id }">
										<input type="hidden" name="planner_id" value="${plannerInfo.member_id }">
										
										<td class="pt-3">
											<div class="row">
												<div class="col-8 my-auto">
													<%-- 기부 프로젝트는 기본리워드가 선택된 상태 --%>
													<c:if test="${projectInfo.kind eq 'donate' }">
														<select class="form-select form-select-lg mb-3" name="reward_id">
															<option value="default" selected>1000원 (최소후원금)</option>
														</select>
													</c:if>
													
													<!-- 펀딩 프로젝트는 리워드목록에서 선택 -->
													<c:if test="${projectInfo.kind eq 'fund' }">
														<select class="form-select form-select-lg mb-3" name="reward_id">
															<option selected>리워드 선택</option>
															<optgroup label="1,000원">
																<option value="default">리워드 없이 후원하기</option>
															</optgroup>
															<c:forEach var="reward" items="${rewardList }">
																<optgroup label="${reward.r_price}원">
																	<option value="${reward.reward_id }">${reward.r_name }</option>
																</optgroup>
															</c:forEach>
														</select>
													</c:if>
												</div>
												<div class="col-4">
													<button type="submit" class="btn btn-primary btn-lg px-3 d-block d-md-none d-xl-block" onclick="checkDonate(); return false;">
														<i class="fas fa-donate py-1 d-block d-xxl-none"></i><span class="d-none d-xxl-block">후원하기</span>
													</button>
													<button type="submit" class="btn btn-primary py-2 px-4 d-none d-md-block d-xl-none" onclick="checkDonate(); return false;">
														<i class="fas fa-donate"></i>
													</button>
												</div>
											</div>
											<input class="form-control" type="text" name="add_donation" id="add_donation" placeholder="추가 후원금 (선택)">	
										</td>
									</form>
								</c:if>
								
								<!-- status가 진행중이 아니라면 -->
								<c:if test="${projectInfo.p_status ne 'ongoing' }">
									<td class="py-3">
										<button type="button" class="btn btn-light" style="width:100%" disabled>
											<c:choose>
												<%-- 공개예정 --%>
												<c:when test="${projectInfo.p_status eq 'unauthorized'}">
													프로젝트 검토 중
												</c:when>
												<c:when test="${projectInfo.p_status eq 'ready'}">
													${projectInfo.deadline}일 이후부터 후원가능
												</c:when>
												<%-- 종료 또는 성공 --%>
												<c:otherwise>
													종료된 프로젝트입니다.
												</c:otherwise>
											</c:choose>
										</button>	
									</td>
								</c:if>
								
								
							</tr>						
						</tbody>
					</table>
				</div>
				<!-- 상품 헤더 끝 -->
				
				<!-- 상품 바디 -->
				<!-- 기획자 정보 -->
				<div class="col-12">
					<table class="table">
						<thead>
							<tr>
								<th colspan="2">기획자 정보</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>기획자명</td>
								<td>${plannerInfo.planner_name }</td>
							</tr>
							<tr>
								<td>소개글</td>
								<td>${plannerInfo.introduce }</td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<!-- 프로젝트 세부사항 -->
				<div class="col-12">
					<table class="table">
						<thead>
							<tr>
								<th colspan="3">프로젝트 요약</th>
							</tr>
							<tr>
								<td colspan="3">${projectInfo.summary }<br><br></td>
							</tr>
							<tr>
								<th colspan="3">프로젝트 상세</th>
							</tr>
							<tr class="text-center">
								<th>
									<c:forTokens var="image" items="${projectInfo.image }" delims=";">
										<img src="images/project_No_${projectInfo.project_id }/${image}" style="width:60%; margin-bottom: 2rem">
									</c:forTokens>
								</th>
							</tr>
						</thead>
						<tbody>
							<tr class="text-center">
								<td style="padding:50px">
								${projectInfo.content }
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<!-- 리워드 세부사항 (펀딩 프로젝트만 보임) -->
				<c:if test="${projectInfo.kind eq 'fund' }">
					<div class="col-12">
						<table class="table">
							<thead>
								<tr>
									<th>리워드 이름</th>
									<th>세부내용</th>
									<th>금액</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="reward" items="${rewardList }">
									<tr>
										<td class="col-4">${reward.r_name}</td>
										<td class="col-auto">${reward.r_content }</td>
										<td class="col-2">${reward.r_price }원</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
				
								
				<div class="col-12 text-center">
					<%-- 진행중인 프로젝트 목록에서 클릭해서 들어왔다면 --%>
					<c:if test="${projectInfo.p_status eq 'ongoing' }">
						<%-- 기부 프로젝트 --%>
						<c:if test="${projectInfo.kind eq 'donate' && orderKeyword ne null}">
							<button class="btn btn-light" onclick="location.href='userOngoingDonateProjectList.pj?page=${page}&selectOrder=${orderKeyword }'">목록보기</button>
						</c:if>
						<c:if test="${projectInfo.kind eq 'donate' && searchKeyword ne null}">
							<button class="btn btn-light" onclick="location.href='userOngoingDonateProjectList.pj?page=${page}&searchTitler=${searchKeyword }'">목록보기</button>
						</c:if>
						<c:if test="${projectInfo.kind eq 'donate' && searchKeyword eq null && orderKeyword eq null}">
							<button class="btn btn-light" onclick="location.href='userOngoingDonateProjectList.pj?page=${page}'">목록보기</button>
						</c:if>
						<%-- 펀딩 프로젝트 --%>
						<c:if test="${projectInfo.kind eq 'fund' && orderKeyword ne null}">
							<button class="btn btn-light" onclick="location.href='userOngoingFundProjectList.pj?page=${page}&selectOrder=${orderKeyword }'">목록보기</button>
						</c:if>
						<c:if test="${projectInfo.kind eq 'fund' && searchKeyword ne null}">
							<button class="btn btn-light" onclick="location.href='userOngoingFundProjectList.pj?page=${page}&searchTitler=${searchKeyword }'">목록보기</button>
						</c:if>
						<c:if test="${projectInfo.kind eq 'fund' && searchKeyword eq null && orderKeyword eq null}">
							<button class="btn btn-light" onclick="location.href='userOngoingFundProjectList.pj?page=${page}'">목록보기</button>
						</c:if>
					</c:if>
					<%-- 공개예정 프로젝트 목록에서 클릭해서 들어왔다면 --%>
					<c:if test="${projectInfo.p_status eq 'ready' }">
						<%-- 기부 프로젝트 --%>
						<c:if test="${projectInfo.kind eq 'donate' && orderKeyword ne null}">
							<button class="btn btn-light" onclick="location.href='userReadyDonateProjectList.pj?page=${page}&selectOrder=${orderKeyword }'">목록보기</button>
						</c:if>
						<c:if test="${projectInfo.kind eq 'donate' && searchKeyword ne null}">
							<button class="btn btn-light" onclick="location.href='userReadyDonateProjectList.pj?page=${page}&searchTitler=${searchKeyword }'">목록보기</button>
						</c:if>
						<c:if test="${projectInfo.kind eq 'donate' && searchKeyword eq null && orderKeyword eq null}">
							<button class="btn btn-light" onclick="location.href='userReadyDonateProjectList.pj?page=${page}'">목록보기</button>
						</c:if>
						<%-- 펀딩 프로젝트 --%>
						<c:if test="${projectInfo.kind eq 'fund' && orderKeyword ne null}">
							<button class="btn btn-light" onclick="location.href='userReadyFundProjectList.pj?page=${page}&selectOrder=${orderKeyword }'">목록보기</button>
						</c:if>
						<c:if test="${projectInfo.kind eq 'fund' && searchKeyword ne null}">
							<button class="btn btn-light" onclick="location.href='userReadyFundProjectList.pj?page=${page}&searchTitler=${searchKeyword }'">목록보기</button>
						</c:if>
						<c:if test="${projectInfo.kind eq 'fund' && searchKeyword eq null && orderKeyword eq null}">
							<button class="btn btn-light" onclick="location.href='userReadyFundProjectList.pj?page=${page}'">목록보기</button>
						</c:if>
					</c:if>
					<%-- 종료된 프로젝트 목록에서 클릭해서 들어왔다면 --%>
					<c:if test="${projectInfo.p_status eq 'done' || projectInfo.p_status eq 'success'}">
						<%-- 기부 프로젝트 --%>
						<c:if test="${projectInfo.kind eq 'donate' && orderKeyword ne null}">
							<button class="btn btn-light" onclick="location.href='userDoneDonateProjectList.pj?page=${page}&selectOrder=${orderKeyword }'">목록보기</button>
						</c:if>
						<c:if test="${projectInfo.kind eq 'donate' && searchKeyword ne null}">
							<button class="btn btn-light" onclick="location.href='userDoneDonateProjectList.pj?page=${page}&searchTitler=${searchKeyword }'">목록보기</button>
						</c:if>
						<c:if test="${projectInfo.kind eq 'donate' && searchKeyword eq null && orderKeyword eq null}">
							<button class="btn btn-light" onclick="location.href='userDoneDonateProjectList.pj?page=${page}'">목록보기</button>
						</c:if>
						<%-- 펀딩 프로젝트 --%>
						<c:if test="${projectInfo.kind eq 'fund' && orderKeyword ne null}">
							<button class="btn btn-light" onclick="location.href='userDoneFundProjectList.pj?page=${page}&selectOrder=${orderKeyword }'">목록보기</button>
						</c:if>
						<c:if test="${projectInfo.kind eq 'fund' && searchKeyword ne null}">
							<button class="btn btn-light" onclick="location.href='userDoneFundProjectList.pj?page=${page}&searchTitler=${searchKeyword }'">목록보기</button>
						</c:if>
						<c:if test="${projectInfo.kind eq 'fund' && searchKeyword eq null && orderKeyword eq null}">
							<button class="btn btn-light" onclick="location.href='userDoneFundProjectList.pj?page=${page}'">목록보기</button>
						</c:if>
					</c:if>
										
				</div>
			</div>
		</div>
	</div>
	

</body>

</html>