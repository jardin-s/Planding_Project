package action.qna;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import vo.ActionForward;

public class QnaImageFileDownAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		String filename = request.getParameter("q_image");
		
		String savePath = "images/qna";
		ServletContext context = request.getServletContext();
		String sDownloadPath = context.getRealPath(savePath);
		
		String fullFilePath = sDownloadPath + File.separator + filename;
		
		//풀path로 파일객체 생성하고 FileInputStream 생성
		File file = new File(fullFilePath);
		FileInputStream fis = new FileInputStream(file);
		
		//유형확인
		//String sMimeType = 
		//System.out.println("sMimeType(MIME 타입) = "+sMimeType);

		
		return forward;
	}

}
