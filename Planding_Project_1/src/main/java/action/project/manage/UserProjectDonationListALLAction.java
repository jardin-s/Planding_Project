package action.project.manage;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.project.ProjectPageViewService;
import vo.ActionForward;
import vo.DonationBean;
import vo.ProjectBean;

public class UserProjectDonationListALLAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;

		//프로젝트 아이디
		int project_id = Integer.parseInt(request.getParameter("project_id"));
		
		ProjectPageViewService projectPageViewService = new ProjectPageViewService();
		//페이지 상단에 띄울 기획자명과 프로젝트명 가져옴 생략해도 무방할듯
		ProjectBean pj = projectPageViewService.getProjectInfo(project_id);
		String pj_title = pj.getTitle();
        
		//request 프로젝트명 저장
		request.setAttribute("pj_title", pj_title);
        
		//프로젝트 아이디로 리워드 아이디 리스트를 가져옴
		ArrayList<String> reward_id_list = projectPageViewService.getRewardIdList(project_id);
		request.setAttribute("reward_id_list", reward_id_list);
        
		System.out.println("rewardList.reward_id : "+reward_id_list.toString());
	    
	    
		//프로젝트 아이디로 도네이션 테이블에서 리스트 얻어옴
		ArrayList<DonationBean> donationList = projectPageViewService.getDonationList(project_id);
	    
	    //세션에 후원리스트 저장
	    request.setAttribute("donationList", donationList);
		
		//파라미터값 확인
		System.out.println("[UserProjectDonationListALLAction] 파라미터 값 확인");
		System.out.println("project_id = "+project_id);
		System.out.println("donationList = "+donationList.toString());
					
		
		request.setAttribute("showPage", "user/myPage/project/userProjectDonationListALL.jsp");
		forward = new ActionForward("userTemplate.jsp", false);
			
		
        return forward;
	}

}
