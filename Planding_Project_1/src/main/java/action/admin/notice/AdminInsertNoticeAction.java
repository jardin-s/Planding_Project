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
import vo.ActionForward;
import vo.NoticeBean;


public class AdminInsertNoticeAction implements Action {

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
		
		String member_id = multi.getParameter("member_id");
		
		String n_title = multi.getParameter("n_title");
		String n_content = multi.getParameter("n_content");
		String importance = multi.getParameter("importance");
		
		String n_image = multi.getFilesystemName("n_image");
			
		System.out.println("[AdminInsertNoticeAction] 파라미터값 이미지파일 업로드 성공");
		System.out.println("member_id = "+member_id);
		System.out.println("n_title = "+n_title);
		System.out.println("n_content = "+n_content);
		System.out.println("n_image = "+n_image);
		System.out.println("importance = "+importance);
		
		
		NoticeBean notice = new NoticeBean();
		notice.setMember_id(member_id);
		notice.setN_title(n_title);
		notice.setN_content(n_content);
		notice.setN_image(n_image);
		notice.setImportance(importance);
		
		AdminInsertNoticeService adminInsertNoticeService = new AdminInsertNoticeService();
		boolean isWriteSuccess = adminInsertNoticeService.insertNotice(notice);
		
		if(!isWriteSuccess) {//글 등록 실패 시
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('공지글 등록이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {//글 등록 성공 시
						
			forward = new ActionForward("adminNoticeList.adm", true);
		}
		
		return forward;
	}

}
