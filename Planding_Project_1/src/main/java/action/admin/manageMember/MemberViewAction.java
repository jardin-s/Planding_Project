package action.admin.manageMember;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.admin.manageMember.MemberViewService;
import vo.ActionForward;
import vo.AddressBean;
import vo.DonationBean;
import vo.MemberBean;
import vo.QnaBean;
import vo.RewardBean;

public class MemberViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		String member_id = request.getParameter("member_id");
		
		
		/*- 필요한 정보들을 가져옴 ---------------------------------------------------------*/
		
		MemberViewService memberViewService = new MemberViewService();
		//1. 회원 정보를 가져옴		
		MemberBean memberInfo = memberViewService.getMemberInfo(member_id);
		request.setAttribute("memberInfo", memberInfo);
		
		//2. 회원 배송지 정보를 가져옴
		ArrayList<AddressBean> addressList = memberViewService.getMemberAddressList(member_id);
		request.setAttribute("addressList", addressList);
		
		//3. 회원 후원기록을 가져옴
		ArrayList<DonationBean> donationList = memberViewService.getMemberDonationList(member_id);
		request.setAttribute("donationList", donationList);
				
		//4. 회원이 작성한 문의글 목록을 가져옴
		ArrayList<QnaBean> qnaList = memberViewService.getMemberQnaList(member_id);
		request.setAttribute("qnaList", qnaList);
		
		request.setAttribute("showAdmin", "admin/manageMember/manageUserView.jsp");
		forward = new ActionForward("adminTemplate.jsp", false);
		
		return forward;
	}

}
