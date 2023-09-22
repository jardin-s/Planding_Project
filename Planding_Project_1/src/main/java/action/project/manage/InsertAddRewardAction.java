package action.project.manage;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.project.InsertProjectService;
import svc.project.ProjectPageViewService;
import vo.ActionForward;
import vo.RewardBean;

public class InsertAddRewardAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		int project_id = Integer.parseInt(request.getParameter("project_id"));
        String[] r_names = request.getParameterValues("r_name");
		String[] r_contents = request.getParameterValues("r_content");
		String[] r_prices = request.getParameterValues("r_price");
		int[] r_price = new int[r_prices.length];
		
		for(int i=0;i<r_prices.length;i++) {
			r_prices[i]=r_prices[i].replace(",", "");
			try {
				r_price[i] = Integer.parseInt(r_prices[i]);
			}catch(Exception e) {
				System.out.println("[InsertAddRewardAction] error : ");
				e.printStackTrace();
			}
		}
		
		//기존 리워드 id의 끝번호를 가져옴 
		ProjectPageViewService projectPageViewService = new ProjectPageViewService();
		ArrayList<String>  reward_id_list = projectPageViewService.getRewardIdList(project_id);
		
		//미리 세팅해둔 리워드 갯수를 불러와 마지막 인덱스에 있는 값을 얻음
		String last_reward_id = reward_id_list.get(reward_id_list.size()-1);
		//리워드아이디 형식으로 라스트 리워드 아이디의 숫자만 추출
		String rewardNameSet = "pj"+project_id+"rwd";
		last_reward_id = last_reward_id.replace(rewardNameSet, "");
		
		int last_reward_id_num = Integer.parseInt(last_reward_id);
		
		//파라미터값 확인
		System.out.println("[InsertAddRewardAction] 파라미터 값 확인");
		System.out.println("r_names 배열 = "+Arrays.toString(r_names));
		System.out.println("r_contents 배열 = "+Arrays.toString(r_contents));
		System.out.println("r_price 배열 = "+Arrays.toString(r_price));
		
		
		//파라미터값들로 RewardBean객체들을 생성 ----------------------------------------------------------
		ArrayList<RewardBean> rewardList = new ArrayList<>();
		for(int i=0; i<r_names.length; i++) {//last_reward_id_num+1값을 통해 리워드아이디가 기존과 중복되지 않도록 설정
			
			if(r_names[i].trim().equals("")) { break;}//만약 더이상 리워드배열의 칸이 null이면 반복문 끝
			
			else{rewardList.add(new RewardBean(
										  		"pj"+project_id+"rwd"+(i+last_reward_id_num+1),
										  		r_names[i],
										  		r_contents[i],
										  		r_price[i]
										  	)
						    );		
			
				System.out.println("중간 rewardList.size() :" + rewardList.size());
			
			}
			
		}
		System.out.println("최종 rewardList.size() :" + rewardList.size());//추가할 리워드 갯수 확인
		
			
		
		InsertProjectService insertProjectService = new InsertProjectService();

        //리워드 테이블에 리워드 insert
        boolean isInsertRewardListSuccess = insertProjectService.insertRewardList(rewardList);
        if(!isInsertRewardListSuccess) {
        	System.out.println("insertRewardList error");
        	response.setContentType("text/html; charset=UTF-8");
            
        	PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('리워드 추가가 실패했습니다.');");
            out.println("history.back();");
            out.println("</script>");
        }
        else{
        
	        // 프로젝트-리워드 매핑 insert
	        boolean isMapProjectRewardList = insertProjectService.mapProjectRewardList(project_id, rewardList);
	        
	        if(!isMapProjectRewardList) {
	        	System.out.println("mapProjectRewardList error");
	        	response.setContentType("text/html; charset=UTF-8");
                
            	PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('리워드 추가가 실패했습니다.');");
                out.println("history.back();");
                out.println("</script>");
	        }else {
	        	forward = new ActionForward("editProjectRewardList.pj?project_id="+project_id, true);//반드시 디스패치 (request를 공유)
	        }
        
        }
    		
		//내가 등록한 프로젝트 목록 보기 요청
		forward = new ActionForward("editProjectRewardList.pj?project_id="+project_id, true);//반드시 디스패치 (request를 공유)
			
		return forward;
	}

}
