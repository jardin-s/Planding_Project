package action.project;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.project.InsertProjectService;
import vo.ActionForward;
import vo.PlannerBean;
import vo.ProjectBean;
import vo.RewardBean;

//프로젝트 기획자 입력, 프로젝트 내용 입력 후 임시저장
public class InsertFundProjectTempAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
        
		//프로젝트 Id 0으로 초기화
		//int project_id = 0;
        
		//이미 이미지가 temp폴더에 저장된 상태이므로 기부프로젝트와 달리 이미지저장을 제외한 프로젝트 저장, 기획자 저장, 리워드 저장만 이루어짐
		
        String[] r_names = request.getParameterValues("r_name");
		String[] r_contents = request.getParameterValues("r_content");
		String[] r_prices = request.getParameterValues("r_price");
		String[] r_price = new String[r_prices.length];
		
		for(int i=0;i<r_prices.length;i++) {
			r_price[i]=r_prices[i].replace(",", "");
		}
		
		//파라미터값들을 session에 저장 (이전단계 > 다음단계 클릭 시 세팅)
		HttpSession session = request.getSession();
		session.setAttribute("r_name", r_names);
		session.setAttribute("r_contents", r_contents);
		session.setAttribute("r_price", r_price);
		
		//파라미터값 확인
		System.out.println("[InsertFundProjectTempAction] 파라미터 값 확인");
		System.out.println("r_names 배열 = "+Arrays.toString(r_names));
		System.out.println("r_contents 배열 = "+Arrays.toString(r_contents));
		System.out.println("r_price 배열 = "+Arrays.toString(r_price));
		
		
		//파라미터값들로 RewardBean객체들을 생성 ----------------------------------------------------------
		ArrayList<RewardBean> rewardList = new ArrayList<>();
		for(int i=0; i<r_names.length; i++) {
			
			if(r_names[i].trim().equals("")) break;//만약 더이상 리워드배열의 칸이 null이면 반복문 끝
			
			rewardList.add(new RewardBean(r_names[i],
										  r_contents[i],
										  Integer.parseInt(r_price[i])
										  )
						  );		
		}
		//세션에 리워드 리스트를 저장하고
		session.setAttribute("rewardList", rewardList);
					
		//미리보기 페이지로 이동
		request.setAttribute("showPage", "project/insertProjectTempView.jsp");
		forward = new ActionForward("userTemplate.jsp", false);
			
		
        return forward;
    }
    
}
