package action.user.project;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.user.project.UserOngoingFundProjectListService;
import vo.ActionForward;
import vo.PageInfo;
import vo.ProjectPlannerBean;

public class UserOngoingFundProjectListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		//처음 요청할 경우 조회하는 페이지넘버 기본값 1
		int page = 1;
		int limit = 15;//한 페이지 당 프로젝트 15개 조회
		
		//페이지 넘버 클릭하여 조회하는 경우
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		
		/* < 프로젝트 조회하는 경우 >
		 * 1. 아무 조건도 없이 조회하는 경우 (기본값 '최근 런칭순')
		 * 2. 정렬기준으로 조회하는 경우 (선택한 정렬기준 selectOrder로 넘어온 파라미터값으로 정렬)
		 * 3. 검색하여 조회하는 경우 (입력한 id값으로 검색하여 정렬)
		 */
		
		//[순서-1] project_planner 뷰에서 프로젝트-기획자 목록을 가져옴 
		UserOngoingFundProjectListService userFundProjectListService = new UserOngoingFundProjectListService();
		
		String selectOrder = request.getParameter("selectOrder");//정렬조회시
		String searchTitle = request.getParameter("searchTitle");//검색조회시
		
		int listCount = 0;
		ArrayList<ProjectPlannerBean> projectPlannerList = null;
		if(selectOrder != null) {//정렬기준으로 조회
			
			if(!selectOrder.equalsIgnoreCase("default")) {//혹시모를 default 선택 방지
				//project_tbl에서 기부 프로젝트 수를 얻어옴
				listCount = userFundProjectListService.getFundProjectOngoingCount();
				System.out.println("[UserOngoingFundProjectListAction] project_tbl 진행중인 기부 프로젝트 수 = "+listCount);
				
				//기부 프로젝트-기획자 목록을 얻어옴 (기본값 : 진행중, 최신순)
				projectPlannerList = userFundProjectListService.getOrderFundProjectPlannerOngoingList(selectOrder, page, limit);
				
				request.setAttribute("orderKeyword", selectOrder);
			}			
			
		}else if(searchTitle != null) {//검색 조회
			
			//project_tbl에서 기부 프로젝트 수를 얻어옴
			listCount = userFundProjectListService.getSearchFundProjectOngoingCount(searchTitle);
			System.out.println("[UserOngoingFundProjectListAction] project_tbl 검색조건에 따른 진행중인 기부 프로젝트 수 = "+listCount);
			
			//기부 프로젝트-기획자 목록을 얻어옴 (기본값 : 진행중, 최신순)
			projectPlannerList = userFundProjectListService.getSearchFundProjectPlannerOngoingList(searchTitle, page, limit);
			
			request.setAttribute("searchKeyword", searchTitle);
			
		}else {//아무 조건 없이 조회
			//project_tbl에서 기부 프로젝트 수를 얻어옴
			listCount = userFundProjectListService.getFundProjectOngoingCount();
			System.out.println("[UserOngoingFundProjectListAction] project_tbl 진행중인 기부 프로젝트 수 = "+listCount);
			
			//기부 프로젝트-기획자 목록을 얻어옴 (기본값 : 진행중, 최신순)
			projectPlannerList = userFundProjectListService.getFundProjectPlannerOngoingList(page, limit);
		}
		
		//request에 프로젝트-기획자 리스트 속성값 저장
		request.setAttribute("projectPlannerList", projectPlannerList);	

		
		
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
		
		
		request.setAttribute("showPage", "user/project/userOngoingFundProjectList.jsp");
		forward = new ActionForward("userTemplate.jsp", false);
		
		return forward;
	}

}
