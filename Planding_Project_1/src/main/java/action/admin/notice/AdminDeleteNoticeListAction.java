package action.admin.notice;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.admin.notice.DeleteNoticeListService;
import vo.ActionForward;

public class AdminDeleteNoticeListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		//파라미터값
		String[] removeNoticeList = request.getParameterValues("remove");
		
		System.out.println("[AdminDeleteNoticeListAction] 파라미터값 = "+Arrays.toString(removeNoticeList));
		
		
		HttpSession session = request.getSession();
		String a_id= (String) session.getAttribute("a_id");
		
		if(a_id == null) {//관리자 로그인이 풀린 상태라면
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('해당 서비스는 로그인이 필요합니다.');");
			out.println("adminLoginForm.adm;");
			out.println("</script>");
		}else {
			
			//공지글 id 리스트 생성
			ArrayList<Integer> noticeIdList = new ArrayList<>();
			for(int i=0; i<removeNoticeList.length; i++) {
				noticeIdList.add(Integer.parseInt(removeNoticeList[i]));
			}
				
			
			//notice_id로 글 삭제
			DeleteNoticeListService deleteNoticeListService = new DeleteNoticeListService();
			boolean isDeleteNoticeListSuccess = deleteNoticeListService.deleteNoticeList(noticeIdList);
			
			
			if(!isDeleteNoticeListSuccess) {//글 삭제 실패
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('문의글 삭제가 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");			
			}else {
				
				forward = new ActionForward("adminNoticeList.adm", true);
				
			}
		}
				
		return forward;
	}

}
