package action.user.project;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.project.UserDonateProjectFormService;
import vo.ActionForward;
import vo.AddressBean;
import vo.ProjectBean;
import vo.RewardBean;

public class UserDonateProjectFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		//비회원일 시 후원하기 불가능 = 로그인 후 가능합니다.
		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		String a_id = (String) session.getAttribute("a_id");
		
		if(a_id != null) {//관리자 아이디로 후원 시도 시
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('관리자 계정으로 후원할 수 없습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else if(u_id == null) {
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 후 후원가능합니다.');");
			out.println("location.href='userLoginForm.usr';");
			out.println("</script>");
		
		
		}else if(u_id != null) {//로그인 시에만 후원하기 폼으로 넘어감
			
			//후원하기 클릭 시 => 프로젝트 ID, 리워드 ID, 추가후원금액 
			int project_id = Integer.parseInt(request.getParameter("project_id"));
			String reward_id = request.getParameter("reward_id");
			int add_donation = 0;
			
			if(!(request.getParameter("add_donation").equals("") || request.getParameter("add_donation") == null)) {
				add_donation = Integer.parseInt(request.getParameter("add_donation"));
			}
			
			//프로젝트 정보 불러오기
			UserDonateProjectFormService userDonateProjectFormService = new UserDonateProjectFormService();
			ProjectBean projectInfo = userDonateProjectFormService.getProjectInfo(project_id);
			
			if(projectInfo == null) {//프로젝트 정보 얻어오기 실패
				response.setContentType("text/html; charset=UTF-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('프로젝트 정보를 불러오는 데 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}else {//프로젝트 정보 얻어오기 성공
				
				//리워드 정보 불러오기
	 			RewardBean rewardInfo = userDonateProjectFormService.getRewardInfo(reward_id);
	 			
	 			if(rewardInfo == null) {//리워드 정보 얻어오기 실패
					response.setContentType("text/html; charset=UTF-8");
					
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('리워드 정보를 불러오는 데 실패했습니다.');");
					out.println("history.back();");
					out.println("</script>");
				}else {//리워드 정보 얻어오기 성공
					
					if(projectInfo.getKind().equalsIgnoreCase("donate")) {//후원 프로젝트의 경우
						
						request.setAttribute("projectInfo", projectInfo);
						request.setAttribute("rewardInfo", rewardInfo);
						request.setAttribute("add_donation", add_donation);
						request.setAttribute("member_id", u_id);//주문 시 필요
						
						//기본리워드로 세팅된 후원하기 폼으로 이동 (배송지 입력칸 없음)
						request.setAttribute("showPage", "user/project/userDonateProject_DonateForm.jsp");
						forward = new ActionForward("userTemplate.jsp", false);
						
					}else if(projectInfo.getKind().equalsIgnoreCase("fund")) {//펀딩 프로젝트의 경우
						
						if(reward_id.equalsIgnoreCase("default")) {//만약 기본리워드만 선택했다면
							
							request.setAttribute("projectInfo", projectInfo);
							request.setAttribute("rewardInfo", rewardInfo);
							request.setAttribute("add_donation", add_donation);
							request.setAttribute("member_id", u_id);//주문 시 필요
							
							//기본리워드로 세팅된 후원하기 폼으로 이동 (배송지 입력칸 없음)
							request.setAttribute("showPage", "user/project/userDonateProject_DonateForm.jsp");
							forward = new ActionForward("userTemplate.jsp", false);
							
						}else {//리워드 목록 중 선택했다면
							//회원의 기본 주소지 가져오기
							AddressBean addressInfo = userDonateProjectFormService.getBasicAddress(u_id);
							
							//request속성값으로 저장
							request.setAttribute("projectInfo", projectInfo);
							request.setAttribute("rewardInfo", rewardInfo);
							request.setAttribute("addressInfo", addressInfo);
							request.setAttribute("add_donation", add_donation);
							request.setAttribute("member_id", u_id);//주문 시 필요
							
							//선택리워드로 세팅된 후원하기 폼으로 이동 (배송지 입력칸 있음)
							request.setAttribute("showPage", "user/project/userDonateProject_FundForm.jsp");
							forward = new ActionForward("userTemplate.jsp", false);
						}
						
					}
					
					
				}
				
			}		
					
		}
		
		return forward;
	}

}
