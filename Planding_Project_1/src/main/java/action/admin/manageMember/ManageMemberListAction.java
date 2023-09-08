package action.admin.manageMember;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.admin.manageMember.ManageMemberListService;
import svc.qna.QnaListService;
import vo.ActionForward;
import vo.MemberBean;
import vo.PageInfo;
import vo.QnaBean;

public class ManageMemberListAction implements Action {

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
		
		
		/* < 회원 조회하는 경우 >
		 * 1. 아무 조건도 없이 조회하는 경우 (기본값 '최근 가입한 회원순')
		 * 2. 정렬기준으로 조회하는 경우 (선택한 정렬기준 selectOrder로 넘어온 파라미터값으로 정렬)
		 * 3. 검색하여 조회하는 경우 (입력한 id값으로 검색하여 정렬)
		 */
		
		//1. member 테이블에서 글을 가져옴 
		ManageMemberListService manageMemberListService = new ManageMemberListService();
				
		String order = request.getParameter("selectOrder");//정렬기준 선택하여 조회할 경우
		String search_id = request.getParameter("member_id");//아이디로 검색하여 조회할 경우
		
		int listCount = 0;
		ArrayList<MemberBean> memberList = null;
		if(order == null && search_id == null) {//정렬기준X,검색X (그냥 처음 조회)
			//member_tbl에서 관리자가 아닌 회원 수를 얻어옴
			listCount = manageMemberListService.getMemberCount();
			
			//회원목록을 얻어옴 (기본값 : 최근 가입순)
			memberList = manageMemberListService.getMemberList(page, limit);
		}else if(order != null && search_id == null) {//정렬기준 선택. 검색X
			//member_tbl에서 관리자가 아닌 회원 수를 얻어옴
			listCount = manageMemberListService.getMemberCount();
			
			//정렬기준에 따른 회원목록을 얻어옴
			memberList = manageMemberListService.getOrderMemberList(order, page, limit);
		}else if(order == null && search_id != null) {//정렬기준X. 검색O
			//검색기준에 따른 회원 수를 얻어옴
			listCount = manageMemberListService.getSearchMemberCount(search_id);
			
			//검색기준에 따른 회원목록을 얻어옴
			memberList = manageMemberListService.getSearchMemberList(page, limit, search_id);
		}
		
		System.out.println("[AdminManageMemberListAction] member_tbl 총 회원 수 = "+listCount);
		
		request.setAttribute("memberList", memberList);
		
		
		//2. 정렬기준에 출력할 해쉬맵저장
		HashMap<String, String> orderMap = new HashMap<>();
		orderMap.put("member_id desc", "역가나다순");
		orderMap.put("member_id asc", "가나다순");
		orderMap.put("joindate asc", "오래된 가입순");
		orderMap.put("joindate desc", "최근 가입순");
		orderMap.put("none", "-- 정렬기준 --");
		request.setAttribute("orderMap", orderMap);
		
		if(order != null) {
			request.setAttribute("orderKeyword", order);
		}
		
		
		//3. 페이지네이션 설정
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
		
		
		request.setAttribute("showAdmin", "admin/manageMember/manageMemberList.jsp");
		forward = new ActionForward("adminTemplate.jsp", false);
		
		return forward;
	}

}
