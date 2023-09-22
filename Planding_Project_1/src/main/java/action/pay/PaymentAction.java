package action.pay;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity.HeadersBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import action.Action;
import svc.pay.PaymentService;
import vo.ActionForward;
import vo.PayDTO;

public class PaymentAction implements Action {
	//토스결제 성공 시, (PPT)에 스프링 Json 썼다고 넣어둘 것
	
	/* 필드 : 결제위해 추가 */
	//RestTemplate를 이용하여 두 컴퓨터 시스템(영남-토스)이 인터넷을 통해 정보를 안전하게 교환할 수 있음
	//Spring에서 지원하는 객체로 간편하게 Rest방식 API를 호출할 수 있는 Spring 내장 클래스
	//Rest방식 API? 두 컴퓨터 시스템(영남-토스)이 인터넷을 통해 정보를 안전하게 교환하기 위해 사용하는 인터페이스
	private final RestTemplate restTemplate = new RestTemplate();//스프링
	
	//ObjectMapper를 이용하면 JSON을 JAVA객체로 변환할 수 있고(역직렬화), 반대로 JAVA객체를 JSON객체로 serialization(직렬화)할 수 있음
	private final ObjectMapper objectMapper = new ObjectMapper();//json
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		//결과를 웹브라우저에 출력위해
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//session에 공유한 속성 얻기
		HttpSession session = request.getSession();
		//내가 필요에 의해 가져옴(SQL 설계 - 테이블에 필요)
		
		String u_id = (String)session.getAttribute("u_id");
		String u_name = (String)session.getAttribute("u_name");
		String u_email = (String)session.getAttribute("u_email");
		
		//ArrayList<Menu> menuList = (ArrayList<Menu>) session.getAttribute("menuList"); 필요없음
		
	//순서-6. 결제 승인 ------------------------------------------------------------------------
		/* 결제 승인 API를 호출해서 마지막 단계를 완료
		 * 먼저, API 인증을 위해 아래와 같이 인증 헤더값을 만들기
		 * Http Basic 인증 방식은 클라이언트에서 Base64로 인코딩된 사용자 ID, 비밀번호 쌍을 인증 정보 값으로 사용
		 * 사용자 ID와 비밀번호 쌍은 사용자ID:비밀번호 콜론으로 구분
		 * Base64로 인코딩한 정보는 쉽게 디코딩이 가능해서 Basic인증은 반드시 HTTPS/TLS와 함께 사용해야 함
		 * 토스페이먼트 API는 시크릿키를 사용자ID로 사용하고, 비밀번호는 사용하지 않음
		 * (=>실제 결제 안되는 테스트용이므로)
		 */
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Basic "+Base64.getEncoder().encodeToString(("test_sk_5OWRapdA8dYWYyqgalY3o1zEqZKL"+":").getBytes("UTF-8")));
					//인증			Basic방식		사용자ID를 Basic64로 인코딩(UTF-8로 bytes인코딩된 값을 다시 Base64로)

		//데이터를 JSON형식으로 게시하기 위해 APPLICATION_JSON 미디어 유형을 사용하여 헤더의 Content-Type으로 추가 후 요청에 추가함
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		
	//순서-7. '영남서버'에서 '토스페이먼츠'에 RestTemplate을 사용하여 JSON데이터를 담아 POST방식으로 요청함  ------------------------------------------------------------------------
		/* 현재 결제창에서 결제 요청이 성공하면 결제성공 페이지로 이동한다.(successUrl)--------------
		 * 이때의 요청 ↓
		 * https://{ORIGIN}/success?paymentKey={PAYMENT_KEY}&orderId={ORDER_ID}&amount={AMOUNT}
		 * 즉, 성공url로 넘어올때 paymentKey, orderId, amount를 파라미터값으로 해서 넘어옴
		 */
		
		//요청객체
		String paymentKey = request.getParameter("paymentKey");//결제번호
		String orderId = request.getParameter("orderId");//주문번호(서비스에서 정한 주문 고유번호(결제창에서 Math.random으로 생성함))
		String amount = request.getParameter("AMOUNT");//총결제가격
		
		Map<String, String> payloadMap = new HashMap<>();
		payloadMap.put("orderId", orderId);
		payloadMap.put("amount", amount);
		
		/* ObjectMapper 클래스를 이용하여 
		 * JAVA객체(위에서 만든 payload요청객체)를 JSON으로 직렬화하기 위해 writeValue() 이용
		 */
		HttpEntity<String> requests = new HttpEntity<>(objectMapper.writeValueAsString(payloadMap), headers);//요청객체, 인증방식
		
		
		//RestTemplate 사용하여 POST 방식으로 요청
		//(최종적으로 요청할 URL(내가 파라미터값으로 받은 paymentKey도 같이 달아서), 요청객체, JsonNode.class)
		ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity("https://js.tosspayments.com/v1/payment"+paymentKey, requests, JsonNode.class);
		
		
		//------------------------------------------------------------------------- 여기까지 토스에 결제요청하는 것 끝
		
		//결제 성공/실패여부 체크하고 처리 (OK = 200)
		if(responseEntity.getStatusCode() != HttpStatus.OK) {//요청 처리 실패
			JsonNode failNode = responseEntity.getBody(); 
			System.out.println("[PaymentAction] 최종결제 실패 = " + failNode);
			
			//※ 토스페이먼츠 jsp 예시샘플에서 fail.jsp 참조하여 코드 작성해도 됨 
			out.println("<script>");
			out.println("alert('결제를 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
			
		}else {//요청이 성공적으로 처리됨
			JsonNode successNode = responseEntity.getBody();
			System.out.println("[PaymentAction] 최종결제 성공 = " + successNode);
			
			//선생님 필요에 의해 작성
			//결제 성공 시 order_table 가장 마지막에 주문한(latestOrder)의 order_status를 'pay'로 최종수정
			
			
			//pay_table에 결제성공 정보를 insert
			PayDTO pay = new PayDTO();//기본값으로 채워진 pay객체
			
			//이때, asText()로 JsonNode값을 String으로 변경
			pay.setOrderId(successNode.get("orderId").asText());//위에서 콘솔에 출력된 successNode의 이름을 보면서 하면됨 
			pay.setmId(successNode.get("mId").asText());
			pay.setPaymentKey(paymentKey);
			//pay.setOrder_num()
			
			pay.setMember_id(u_id);//회원 ID
			pay.setEmail(u_email);//회원 이메일
			
			pay.setOrderName(successNode.get("orderName").asText());//주문명
			pay.setPay_method(successNode.get("method").asText());//결제수단
			
			String easypay = null;
			try {
				easypay = successNode.get("easyPay").asText();
				
				//easyPay는 배열로 넘어오므로, 그 중 provider만 가져와 easyPay로 세팅
				if(easypay != null) pay.setEasyPay(successNode.get("easyPay").get("provider").asText());
				
			} catch(Exception e) {
				System.out.println("easyPay를 선택하지 않았습니다.");
			}
			
			String card = null;
			try {
				card = successNode.get("card").asText();
				
				//easyPay는 배열로 넘어오므로, 그 중 provider만 가져와 easyPay로 세팅
				if(card != null) pay.setPay_method(successNode.get("card").get("company").asText());
				/* 결제 은행 */
				
			} catch(Exception e) {
				System.out.println("card를 선택하지 않았습니다.");
			}
			
			pay.setPay_status(successNode.get("status").asText());//[결제상황]
			pay.setPay_date(successNode.get("approvedAt").asText());//결제날짜
			
			pay.setTotalAmount(successNode.get("totalAmount").asInt());//총 결제금액
			
			System.out.println("pay = "+pay);
			
			//정보가 담긴 객체로 DB에 insert
			PaymentService paymentService = new PaymentService();
			boolean isInsertPaySuccess = paymentService.insertPay(pay);
			
			if(!isInsertPaySuccess) {//DB insert 실패
				out.println("<script>");
				out.println("alert('결제를 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}else {//DB insert 성공
				//반드시 session안의 장바구니 제거
				//session.removeAttribute("cartList");
				
				//선생님 - 사용자가 최종 인쇄할 '주문표'에 출력할 값
				/*
				session.setAttribute("u_id", u_id);
				session.setAttribute("order_num", order_num);
				session.setAttribute("orderName", successNode.get("orderName").asText());
				session.setAttribute("totalAmount", successNode.get("totalAmount").asInt());
				
				out.println("<script>");
				out.println("alert('결제를 실패했습니다.');");
				out.println("location.href='successOrderPayment.kiosk';");
				out.println("</script>");
				*/
			}
			
		}
		return forward;
	}

}
