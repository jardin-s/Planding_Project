package action.project;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.project.ProjectPageViewService;
import vo.*;

public class manageProjectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession();
		
		//세션에 저장된 유저아이디, get방식으로 얻어온 프로젝트 아이디
		String member_id=(String)session.getAttribute("u_id");
        int project_id = Integer.parseInt(request.getParameter("project_id"));
		
        
		
        ProjectPageViewService projectPageViewService = new ProjectPageViewService();
        //페이지 상단에 띄울 기획자명과 프로젝트명 가져옴 생략해도 무방할듯
        ProjectBean pj = projectPageViewService.getProjectInfo(project_id);
        String pj_title = pj.getTitle();
        PlannerBean planner = projectPageViewService.getPlannerInfo(project_id, member_id);
        String planner_name = planner.getPlanner_name();
      //세션에 기획자명과 프로젝트명 저장
	    session.setAttribute("pj_title", pj_title);
	    session.setAttribute("planner_name", planner_name);
        
        //프로젝트 아이디로 리워드 아이디 리스트를 가져옴
        ArrayList<String> reward_id_list = projectPageViewService.getRewardIdList(project_id);
        ArrayList<RewardBean> rewardList = new ArrayList<>();
        //빈 리워드빈객체리스트를 생성, 리워드 아이디 리스트를 통해 가져온 리워드빈 객체를 리워드빈객체리스트에 추가함
        for(int i=0;i<reward_id_list.size();i++) {
        	rewardList.add(projectPageViewService.getRewardInfo(reward_id_list.get(i)));
        }
	    //세션에 리워드리스트 저장
	    session.setAttribute("rewardList", rewardList);
        
	    
	    
	    
	    
	    //프로젝트 아이디로 도네이션 테이블에서 리스트 얻어옴
	    ArrayList<DonationBean> donationList = projectPageViewService.getDonationList(project_id);
	    
	    //리워드별 카운트를 저장할 배열을 리워드 리스트의 크기로 초기화
	    int[] byRewardCount = new int[rewardList.size()];
	    //리워드별 총액을 넣을 배열을 리워드리스트 크기로 초기화
	    int[] byRewardTotalPrice=new int[rewardList.size()];
	    
	    
	    //각 리워드 아이디별로 카운트 계산
	    for (int i = 0; i < rewardList.size(); i++) {
	        RewardBean reward = rewardList.get(i);
	        String rewardId = reward.getReward_id();
	        int rewardCount = 0;
	        int rewardTotalPrice=0;
	        //기부 리스트를 돌리면서 현재 리워드 아이디와 일치하는 카운트 계산 + 리워드별 총액 계산
	        for (int j = 0; j < donationList.size(); j++) {
	            DonationBean donation = donationList.get(j);
	            if (rewardId.equals(donation.getReward_id())) {
	                rewardCount++;
	            }
	            rewardTotalPrice+=donation.getTotalDonation();
	            
	        }
	        byRewardTotalPrice[i]=rewardTotalPrice; //배열에 리워드별 총액 저장 
	        byRewardCount[i] = rewardCount; //배열에 리워드별 카운트 저장
	    }

	    //세션에 리워드별 총액과 카운트 저장
	    session.setAttribute("byRewardCount", byRewardCount);
	    session.setAttribute("byRewardTotalPrice", byRewardTotalPrice);
	    
	    
	    
	    
	    
	    //세션에 리워드리스트 저장
	    session.setAttribute("donationList", donationList);
        
        
        
        
		
		//파라미터값 확인
		System.out.println("[manageProjecttAction] 파라미터 값 확인");
		System.out.println("project_id = "+project_id);
		System.out.println("rewardList = "+rewardList.toString());
		System.out.println("donationList = "+donationList.toString());

		
		

		
					
		//미리보기 페이지로 이동
		request.setAttribute("showPage", "project/insertProjectTempView.jsp");
		forward = new ActionForward("userTemplate.jsp", false);
			
		
        return forward;
    }
    
}
