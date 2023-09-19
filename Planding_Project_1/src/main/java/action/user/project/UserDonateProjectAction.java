package action.user.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import vo.ActionForward;

public class UserDonateProjectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		/* <후원하기> 
		 * 1. 기부프로젝트 - 기본리워드로 후원 (1000원 + 추가후원금액), 배송지 없음
		 * 2. 펀딩프로젝트 - 기본리워드로 후원 (1000원 + 추가후원금액), 배송지 없음
		 * 3. 펀딩프로젝트 - 리워드 선택 (리워드 금액 + 추가후원금액), 배송지 있음
		 */
		
		return forward;
	}

}
