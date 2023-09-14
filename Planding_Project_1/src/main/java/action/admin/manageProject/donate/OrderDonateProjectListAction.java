package action.admin.manageProject.donate;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.admin.manageMember.ManageMemberListService;
import svc.admin.manageProject.donate.ManageDonateProjectListService;
import svc.admin.manageProject.donate.UnauthDonateProjectListService;
import vo.ActionForward;
import vo.MemberBean;
import vo.PageInfo;
import vo.ProjectBean;

public class OrderDonateProjectListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		//처음 요청할 경우 조회하는 페이지넘버 기본값 1
		int page = 1;
		int limit = 20;//한 페이지 당 최대 회원 20명
		
		//페이지 넘버 클릭하여 조회하는 경우
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		
		/* < 프로젝트 조회하는 경우 >
		 * 1. 아무 조건도 없이 조회하는 경우 (기본값 '최근 등록순')
		 * 2. "정렬기준"으로 조회하는 경우 (선택한 정렬기준 selectOrder로 넘어온 파라미터값으로 정렬)
		 * 3. 검색하여 조회하는 경우 (입력한 title값으로 검색하여 정렬)
		 */
		
		//[순서-1] project 테이블에서 글을 가져옴 
		ManageDonateProjectListService manageDonateProjectListService = new ManageDonateProjectListService();
				
		String order = request.getParameter("selectOrder");//정렬기준 선택하여 조회할 경우
		//선택한 기준으로 목록에 세팅되도록
		request.setAttribute("orderKeyword", order);
		
		//project_tbl에서 기부프로젝트 수를 얻어옴
		int listCount = manageDonateProjectListService.getDonateCount();
		System.out.println("[OrderDonateProjectListAction] project_tbl 총 기부프로젝트 수 = "+listCount);
		
		//정렬기준에 따른 기부프로젝트 목록을 얻어옴
		ArrayList<ProjectBean> projectList = manageDonateProjectListService.getOrderDonateList(order, page, limit);
		request.setAttribute("projectList", projectList);
		
		
		//[순서-2] 페이지네이션 설정
		int maxPage = (int) ((double)listCount/limit + 0.95); //최대 페이지 수
		//(0.95를 더해 올림 -> 나눗셈 결과가 0 또는 1이 아니면 무조건 올림효과 발생)
		
		int startPage = (( (int) ((double)page/10 + 0.9)) -1 )*10 +1; //페이지네이션 중 첫 번째 수
		//(예) 2페이지라면 2/10 = 0.2			16페이지라면
		//0.2+0.9 = 1.1 -> (int)1.1 = 1		1.6+0.9 = 2.5 -> (int)2.5 = 2
		//1-1 = 0							2-1 = 1
		//0*10+1 = 1						1*10+1 = 11
		
		int endPage = startPage+10 -1; //페이지네이션 중 마지막 수 10 = 1+10-1
		
		if(endPage > maxPage) {//만약 페이지네이션 마지막수가 최대페이지수보다 크다면
			endPage = maxPage;//페이지네이션 마지막수를 최대페이지수로 표시
		}
		
		//페이지네이션 정보를 PageInfo객체에 담아 전달
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPage(page);
		pageInfo.setListCount(listCount);
		pageInfo.setMaxPage(maxPage);
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);
		
		//페이지네이션 정보와 해당 페이지 글목록을 request속성으로 저장
		request.setAttribute("pageInfo", pageInfo);
		
		
		request.setAttribute("showAdmin", "admin/manageProject/donateList/orderDonateProjectList.jsp");
		forward = new ActionForward("adminTemplate.jsp", false);
		
		return forward;
	}

}
