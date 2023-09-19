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

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">
function findAddr(){
	//카카오 지도 발생 -> 주소 입력 후 [검색] -> 찾는 주소 [선택] -> 우편번호와 주소 세팅
	//postcode 객체 생성하여 바로 open
	new daum.Postcode({
		oncomplete : function(data) {//[선택] 시 입력값 세팅 (검색결과 중 선택한 주소)
			console.log(data);
			//alert(data);//테스트를 위해 값이 뜨도록 함
			
			document.getElementById("postcode").value = data.zonecode; //우편번호를 가져와 postcode에 넣음
			
			let roadAddr = data.roadAddress;//도로명 주소
			let jibunAddr = data.jibunAddress;//지번 주소
			
			if(roadAddr != ''){//도로명 주소가 있으면 도로명 주소를 등록 ('': 자바스크립트 널)
				document.getElementById("address1").value = roadAddr;	
			}else if(jibunAddr != ''){//도로명 주소가 없고 지번 주소만 있으면 지번주소를 등록
				document.getElementById("address1").value = jibunAddr;
			}
			
			document.getElementById("address2").focus();//상세주소 입력창에 커서를 깜빡거리게 함
			
		}
	}).open();
}
</script>

<body>
	
	<div>
		<jsp:include page="../userHeader.jsp" />
	</div>
	
	<!-- Page Header Start -->
    <div class="container-fluid py-4 mb-5 wow fadeIn" data-wow-delay="0.1s" style="background-color:#86B381">
        <div class="container text-center pt-5">
            <h3 class="display-6 py-3 text-white animated slideInDown">프로젝트 후원하기</h3>
        </div>
    </div>
	
	<div class="container-xxl mt-5 py-5">
   		
   		<!-- 전체 후원 컨테이너 시작 -->
   		<div class="container col-12 col-md-11">
   			<div class="mb-3">
   				<i class="fas fa-heart mx-2 text-danger"></i><strong>후원할 프로젝트 정보</strong>
   			</div>
   			<div class="row justify-content-center">
   				
   				<!-- 프로젝트 및 후원금액 정보 -->
   				<div class="col-12 col-lg-7">
   					<div class="bg-light rounded p-4 mb-4 animated fadeIn" data-wow-delay="0.1s">                        
                        <div class="row g-3">
                            <div class="col-12">
                            	<div class="bg-white rounded">
                                    <p class="p-3" style="line-height:2rem">
                                    	<b><i class="far fa-file-powerpoint me-2 text-primary"></i>결식아동에게 밥 한 끼를 선물해주세요.</b><br>
                                    	&nbsp;1565400원 | 달성률 : 87% | 6일 남음<br>
                                    </p>
                                </div>
                                <div class="bg-white rounded">
                                    <p class="p-3" style="line-height:2rem">
                                    	<b><i class="fas fa-gift me-2 text-primary"></i>후원금액</b><br>
                                    	&nbsp;기본 금액 : 1000원<br> 
                                    	&nbsp;추가 후원금액 : 10000원<br> 
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    
   				</div>
   				
   				<!-- 최종 후원확인 -->
   				<div class="col-12 col-lg-5">
   					<div class="bg-light rounded p-4 animated fadeIn" data-wow-delay="0.1s">                        
                        <div class="row g-3">
                            <div class="col-12">
                            	<div class="bg-white rounded">
                                    <p class="p-3" style="line-height:2.2rem">
                                    	<b><span class="text-danger me-3">최종 후원금액</span> 15000원</b><br>
                                    </p>
                                </div>
                                <div class="bg-white rounded p-3">
                                    <div class="form-check">
	                                    <input type="checkbox" name="agree" value="agree" class="form-check-input border-1" id="agree">
	                                    <label class="form-check-label" for="agree">개인정보 제 3자 제공 동의</label>
	                                    <a href="#" class="pt-2 ms-2" data-bs-toggle="modal" data-bs-target="#agreeModal">내용보기</a>
									</div>
									<div class="form-check">
	                                    <input type="checkbox" name="confirm" value="confirm" class="form-check-input border-1" id="confirm">
	                                    <label class="form-check-label" for="confirm">후원 유의사항 확인</label>
	                                    <a href="#" class="pt-2 ms-2" data-bs-toggle="modal" data-bs-target="#confirmModal">내용보기</a>
									</div>	
									
									<div class="d-grid gap-2 col-10 mx-auto mt-5 mb-3">
										<button class="btn btn-primary py-3" type="button" onclick="checkForm();"><b>후원하기</b></button>
									</div>						
                                </div>
                            </div>
                        </div>
                    </div>
   				</div>
   				
   				
   				
   			</div>
   		</div>
   		<!-- 전체 후원 컨테이너 끝 -->
   		
   	</div>
   	
   	<div>
		<jsp:include page="../userFooter.jsp" />
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
    	
	<!-- Agree Modal -->
	<div class="modal fade" id="agreeModal" tabindex="-1" aria-labelledby="agreeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel">개인정보 제3자 제공 동의</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body" style="font-size:0.8em">
					회원의 개인정보는 당사의 개인정보 취급방침에 따라 안전하게 보호됩니다. 
					'회사'는 이용자들의 개인정보를 개인정보 취급방침의 '제 2조 수집하는 개인정보의 항목, 수집방법 및 이용목적'에서 고지한 범위 내에서 사용하며,  
					이용자의 사전 동의 없이는 동의 범위를 초과하여 이용하거나 원칙적으로 이용자의 개인정보를 외부에 공개하지 않습니다.<br><br>
					
					<table class="table">
						<tr>
							<td class="col-4">제공받는 자</td>
							<td>후원 프로젝트의 기획자</td>
						</tr>
						<tr>
							<td class="col-4">제공 목적</td>
							<td>리워드 전달 및 배송과 관련된 상담 및 민원처리</td>
						</tr>
						<tr>
							<td class="col-4">제공 정보</td>
							<td>수취인 성명, 휴대전화번호, 배송 주소</td>
						</tr>
						<tr>
							<td class="col-4">보유 및 이용 기간</td>
							<td>재화 또는 서비스의 제공이 완료된 즉시 파기</td>
						</tr>
					</table>
					<p class="px-2">
					* 동의 거부권 등에 대한 고지<br>
					개인정보 제공은 서비스 제공을 위한 필수요소입니다. 개인정보 제공을 거부하실 수 있으나, 이 경우 서비스 이용이 제한될 수 있습니다.
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
					<button type="button" class="btn btn-primary" id="agreeBtn">동의하기</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- Confirm Modal -->
	<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel">후원 유의사항 확인</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body" style="font-size:0.8em">
					<ul style="line-height:1.5em">
						<li class="mb-2"><span class="fw-bold">후원은 구매가 아닌 창의적인 계획에 자금을 지원하는 일입니다.</span><br>
							프로젝트가 실현될 수 있도록 제작비를 후원하는 과정이므로, 기존의 상품 또는 용역을 거래의 대상으로 하는 매매와는 차이가 있습니다. 따라서 전자거래법상 청약철회 등의 규정이 적용되지 않습니다.
						</li>
						<li class="mb-2"><span class="fw-bold">프로젝트는 계획과 달리 진행될 수 있습니다.</span><br>
							진행과정에서 계획이 지연, 변경되거나 무산될 수도 있습니다. 본 프로젝트를 완수할 책임과 권리는 기획자에게 있습니다.
						</li>
						<li class="mb-2"><span class="fw-bold">프로젝트가 마감하기 전까지 결제를 취소할 수 있습니다.</span><br>
							프로젝트 종료일까지 언제든지 결제를 취소할 수 있습니다. 단, 프로젝트가 시작되면 환불 문제 등은 기획자에게 직접 문의하여 해결할 수 있습니다.
						</li>
					</ul>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
					<button type="button" class="btn btn-primary" id="confirmBtn">유의사항을 확인했습니다.</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>