package action.admin.manageProject.fund;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.admin.manageProject.ManageStatusProjectListService;
import svc.admin.manageProject.fund.AuthFundProjectListService;
import vo.ActionForward;
import vo.PageInfo;
import vo.ProjectBean;

public class OngoingFundProjectListAction implements Action {

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
		 * 1. "아무 조건도 없이" 조회하는 경우 (기본값 '최근 등록순')
		 * 2. 정렬기준으로 조회하는 경우 (선택한 정렬기준 selectOrder로 넘어온 파라미터값으로 정렬)
		 * 3. 검색하여 조회하는 경우 (입력한 title값으로 검색하여 정렬)
		 */
		
		//[순서-1] project 테이블에서 글을 가져옴 
		ManageStatusProjectListService manageStatusProjectListService = new ManageStatusProjectListService();
		
		String selectOrder = request.getParameter("selectOrder");//정렬조회시
		String searchTitle = request.getParameter("searchTitle");//검색조회시
		
		int listCount = 0;
		ArrayList<ProjectBean> projectList = null;
		
		if(selectOrder != null) {//정렬기준으로 조회
			
			if(!selectOrder.equalsIgnoreCase("default")) {//혹시모를 default 선택 방지
				//project_tbl에서 기부 프로젝트 수를 얻어옴
				listCount = manageStatusProjectListService.getProjectCount("fund", "ongoing");
				System.out.println("[OngoingFundProjectListAction] project_tbl 정렬한 진행 펀딩 프로젝트 수 = "+listCount);
				
				//기부 프로젝트-기획자 목록을 얻어옴 (기본값 : 진행중, 최신순)
				projectList = manageStatusProjectListService.getOrderProjectList("fund", "ongoing", selectOrder, page, limit);
				
				request.setAttribute("orderKeyword", selectOrder);
			}			
			
		}else if(searchTitle != null) {//검색 조회
			
			//project_tbl에서 기부 프로젝트 수를 얻어옴
			listCount = manageStatusProjectListService.getSearchProjectCount("fund", "ongoing", searchTitle);
			System.out.println("[OngoingFundProjectListAction] project_tbl 검색조건에 따른 진행 펀딩 프로젝트 수 = "+listCount);
			
			//기부 프로젝트-기획자 목록을 얻어옴 (기본값 : 진행중, 최신순)
			projectList = manageStatusProjectListService.getSearchProjectList("fund", "ongoing", searchTitle, page, limit);
			
			request.setAttribute("searchKeyword", searchTitle);
			
		}else {//아무 조건 없이 조회
			//project_tbl에서 기부 프로젝트 수를 얻어옴
			listCount = manageStatusProjectListService.getProjectCount("fund", "ongoing");
			System.out.println("[OngoingFundProjectListAction] project_tbl 진행 펀딩 프로젝트 수 = "+listCount);
			
			//기부 프로젝트-기획자 목록을 얻어옴 (기본값 : 진행중, 최신순)
			projectList = manageStatusProjectListService.getProjectList("fund", "ongoing", page, limit);
		}
		
		
		//얻어온 프로젝트 목록을 request 속성으로 저장
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
		
		
		request.setAttribute("showAdmin", "admin/manageProject/fundList/ongoingFundProjectList.jsp");
		forward = new ActionForward("adminTemplate.jsp", false);
		
		return forward;
	}

}
