package action.admin.notice;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import svc.admin.notice.AdminInsertNoticeService;
import svc.admin.notice.AdminModifyNoticeService;
import vo.ActionForward;
import vo.NoticeBean;

public class AdminModifyNoticeAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		//이미지 파일을 업로드할 위치
		ServletContext context = request.getServletContext();
		String uploadPath = context.getRealPath("images/notice");
		System.out.println("서버상의 실제 경로(절대경로) = "+uploadPath);
		
		//업로드할 절대경로로 파일객체를 얻음
		File dir = new File(uploadPath);
		if (!dir.exists()) {//해당 경로(upload)가 존재하지 않으면
			dir.mkdirs();//해당 위치(경로)에 upload 폴더를 만들어줌
		}
		
		//한번에 올릴 수 있는 최대 파일용량 10MB 제한
		int size = 10 * 1024 * 1024;
			
		MultipartRequest multi = new MultipartRequest(request, uploadPath, size, "UTF-8", new DefaultFileRenamePolicy());
													//request객체, 업로드위치, 최대용량, 파일명인코딩, 파일명 중복방지
		
		int notice_id = Integer.parseInt(multi.getParameter("notice_id"));
		
		String n_title = multi.getParameter("n_title");
		String n_content = multi.getParameter("n_content");
		String importance = multi.getParameter("importance");
		
		//다시 해당 글 보기로 돌아가야 하므로 page파라미터값 받아와서 다시 넘겨줘야 함
		int page = Integer.parseInt(multi.getParameter("page"));
		
		String n_image = multi.getFilesystemName("n_image");
		String origFile = multi.getParameter("origFile");//기존이미지가 삭제되었으면, origFile이 null
		//n_image도 null이고 origFile도 null일 경우, 해당 글의 이미지가 없어짐
		
			
		System.out.println("[AdminModifyNoticeAction] 파라미터값 이미지파일 업로드 성공");
		System.out.println("notice_id = "+notice_id);
		System.out.println("n_title = "+n_title);
		System.out.println("n_content = "+n_content);
		System.out.println("n_image = "+n_image);
		System.out.println("importance = "+importance);
				
		NoticeBean notice = new NoticeBean();
		notice.setNotice_id(notice_id);
		notice.setN_title(n_title);
		notice.setN_content(n_content);
		notice.setN_image(n_image);
		notice.setImportance(importance);
		
		
		AdminModifyNoticeService adminModifyNoticeService = new AdminModifyNoticeService();		
		boolean isModifyNoticeSuccess = adminModifyNoticeService.modifyNotice(notice, origFile);
		
		if(!isModifyNoticeSuccess) {//글 수정 실패 시
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('공지글 수정이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {//글 등록 성공 시
			
			//다시 해당 글 요청
			forward = new ActionForward("adminNoticeView.adm?notice_id="+notice_id+"&page="+page, true);
		}
		
		return forward;
	}

}
