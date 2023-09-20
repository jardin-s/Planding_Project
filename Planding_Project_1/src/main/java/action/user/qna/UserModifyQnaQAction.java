package action.user.qna;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import svc.user.qna.ModifyQnaQService;
import vo.ActionForward;
import vo.QnaBean;

public class UserModifyQnaQAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		//이미지 파일을 업로드할 위치
		ServletContext context = request.getServletContext();
		String uploadPath = context.getRealPath("images/qna");
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
		
		String page = multi.getParameter("page");

		int qna_id = Integer.parseInt(multi.getParameter("qna_id"));
		String q_writer = multi.getParameter("q_writer");
		String q_title = multi.getParameter("q_title");
		String q_content = multi.getParameter("q_content");
		String q_private = multi.getParameter("q_private");
		
		//새로 추가하면 null아님
		String q_image = multi.getFilesystemName("q_image");
		
		System.out.println("[UserModifyQnaQAction] 수정 파라미터 값 확인");
		System.out.println("qna_id = "+qna_id);
		System.out.println("q_writer = "+q_writer);
		System.out.println("q_title = "+q_title);
		System.out.println("q_content = "+q_content);
		System.out.println("q_private = "+q_private);
		System.out.println("q_image = "+q_image);
		
		QnaBean qna = new QnaBean();
		qna.setQna_id(qna_id);
		qna.setQ_writer(q_writer);
		qna.setQ_title(q_title);
		qna.setQ_content(q_content);
		qna.setQ_image(q_image);
		qna.setQ_private(q_private);
		
		ModifyQnaQService modifyQnaQService = new ModifyQnaQService();
		boolean isQnaQModifySuccess = modifyQnaQService.updateQuestion(qna);
		
		if(!isQnaQModifySuccess) {
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('문의글 수정이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			
			forward = new ActionForward("userQnaView.usr?page="+page+"&qna_id="+qna_id+"&q_private="+q_private+"&q_writer="+q_writer, true);			
			
		}
		
		return forward;
		
	}

}
