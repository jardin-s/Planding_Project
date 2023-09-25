package action.project.manage;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.project.ProjectPageViewService;
import vo.ActionForward;
import vo.DonationBean;
import vo.PlannerBean;
import vo.ProjectBean;
import vo.RewardBean;

public class ManageProjectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		
		//세션에 저장된 유저아이디, get방식으로 얻어온 프로젝트 아이디
        int project_id = Integer.parseInt(request.getParameter("project_id"));
		
        
		ProjectPageViewService projectPageViewService = new ProjectPageViewService();
        
		//페이지 상단에 띄울 기획자명과 프로젝트명 가져옴 생략해도 무방할듯
        ProjectBean projectInfo = projectPageViewService.getProjectInfo(project_id);
        request.setAttribute("projectInfo", projectInfo);
        
        
        PlannerBean planner = projectPageViewService.getPlannerInfo(project_id);
        String planner_name = planner.getPlanner_name();
        request.setAttribute("planner_name", planner_name);
        
        //세션에 기획자명과 프로젝트명 저장
	    /*
        session.setAttribute("project_id", project_id);
	    session.setAttribute("pj_title", pj_title);
	    session.setAttribute("planner_name", planner_name);
	    */
        
	    /* ★★★제발 세션에 저장하지 말것!!!!
	     * 각 프로젝트마다 프로젝트 관리 페이지가 다른데, 그때마다 세션의 값을 덮어씌우고 만약 프로젝트 관리 페이지 다르게 띄웠다가 원하는 정보대로 안 뜰수도 있음. request값으로 저장하고 로딩하는게 나음 */
	    
        /* 리워드 수정은 진행중인 프로젝트에서 수정 불가하도록 막음
         * 원래 후원자가 선택한 리워드는 수정 못하도록 막았어야 됐는데 그렇게 안 되어있어서
         * 시간없으니까 일단 임의로 상태별로 뷰페이지에서 버튼 안뜨도록 막겠음 */
        
        
        
        
        //프로젝트 아이디로 리워드 아이디 리스트를 가져옴
        ArrayList<String> reward_id_list = projectPageViewService.getAllRewardIdList(project_id);
        ArrayList<RewardBean> rewardList = new ArrayList<>();
        
        //빈 리워드빈객체리스트를 생성, 리워드 아이디 리스트를 통해 가져온 리워드빈 객체를 리워드빈객체리스트에 추가함
        for(int i=0;i<reward_id_list.size();i++) {
        	rewardList.add(projectPageViewService.getRewardInfo(reward_id_list.get(i)));
        }
        
	    //세션에 리워드리스트 저장
        int rewardListSize = rewardList.size();
        request.setAttribute("rewardList", rewardList);
        request.setAttribute("rewardListSize", rewardListSize);
        
	    //프로젝트 아이디로 도네이션 테이블에서 리스트 얻어옴
	    ArrayList<DonationBean> donationList = projectPageViewService.getDonationList(project_id);
	    
	    if(donationList != null) {//후원기록이 있을 때
		    
	    	//리워드별 카운트를 저장할 배열을 리워드 리스트의 크기로 초기화
		    int[] byRewardCountArr = new int[rewardList.size()];
		    //리워드별 총액을 넣을 배열을 리워드리스트 크기로 초기화
		    int[] byRewardTotalPriceArr = new int[rewardList.size()];
		    
		    System.out.println("[ManageProjectAction] rewardList.size() = "+rewardList.size());
		    System.out.println("[ManageProjectAction] donationList.size() = "+donationList.size());
		    
		    
		    int totalPrice=0;
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
		                rewardCount++;	//리워드별 후원 수 합계
		                
		                rewardTotalPrice+=donation.getTotalDonation();	//리워드별 후원금합계
		            }		            
		            
		            totalPrice+=donation.getTotalDonation(); //총 후원금합계
		        }
		        byRewardTotalPriceArr[i] = rewardTotalPrice; //배열에 리워드별 총액 저장 
		        byRewardCountArr[i] = rewardCount; //배열에 리워드별 카운트 저장
		    }
	
		    //리워드별 총액과 카운트 저장
		    request.setAttribute("byRewardCountArr", byRewardCountArr);
		    request.setAttribute("byRewardTotalPriceArr", byRewardTotalPriceArr);
		    request.setAttribute("totalCount", donationList.size());//전체 후원 수
		    request.setAttribute("totalPrice", totalPrice);//전체 후원금액
	    
	    }else {//후원기록이 없다면
	    	
	    	//리워드별 카운트를 저장할 배열을 리워드 리스트의 크기로 초기화
		    int[] byRewardCountArr = new int[rewardList.size()];
		    //리워드별 총액을 넣을 배열을 리워드리스트 크기로 초기화
		    int[] byRewardTotalPriceArr = new int[rewardList.size()];
		    //각 리워드 아이디별로 카운트 계산
		    
		    int totalCount=0;
		    int totalPrice=0;
		    
		    for (int i = 0; i < rewardList.size(); i++) {
		        RewardBean reward = rewardList.get(i);
		        String rewardId = reward.getReward_id();
		        int rewardCount = 0;
		        int rewardTotalPrice=0;
		        //기부 리스트를 돌리면서 현재 리워드 아이디와 일치하는 카운트 계산 + 리워드별 총액 계산

		        byRewardTotalPriceArr[i] = rewardTotalPrice; //배열에 리워드별 총액 저장 
		        byRewardCountArr[i] = rewardCount; //배열에 리워드별 카운트 저장
		    }

		    //리워드별 총액과 카운트 저장
		    request.setAttribute("byRewardCountArr", byRewardCountArr);
		    request.setAttribute("byRewardTotalPriceArr", byRewardTotalPriceArr);
		    request.setAttribute("totalCount", totalCount);//전체 후원 수
		    request.setAttribute("totalPrice", totalPrice);//전체 후원금액
	    }
	    
	    //후원리스트 저장
	    request.setAttribute("donationList", donationList);
        
		//파라미터값 확인
		System.out.println("[ManageProjectAction]");
		System.out.println("project_id = "+project_id);
		System.out.println("rewardList = "+rewardList.toString());
		System.out.println("donationList = "+donationList.toString());
		
		
		//프로젝트 관리 페이지로 포워딩
		request.setAttribute("showPage", "user/myPage/project/userUploadProjectManager.jsp");
		forward = new ActionForward("userTemplate.jsp", false);
				
		return forward;
	}

}
