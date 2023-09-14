package action.project;

import java.io.File;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import vo.ActionForward;

public class InsertProjectContentsAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = null;

        // 파일 업로드 관련 설정
        String uploadPath = "images/temp"; // 파일을 저장할 경로
        
        ServletContext context = request.getServletContext();
        String sUploadPath = context.getRealPath(uploadPath);
        
        File dir = new File(sUploadPath);
		if(!dir.exists()){
			dir.mkdirs();//upload 폴더가 존재하지 않으면 해당 위치에 생성
		}
		
        // 파일 업로드 처리
        MultipartRequest multi = new MultipartRequest(
            request,
            sUploadPath,
            10 * 1024 * 1024, // 최대 업로드 파일 크기 (10MB)
            "UTF-8",
            new DefaultFileRenamePolicy() // 동일한 파일명 처리 정책
        );
        
        
        
        // 업로드된 파일의 경로 가져오기
        Enumeration<String> filenames = multi.getFileNames();
        StringBuffer contentImgSysName=new StringBuffer();
        String contentImgSysNames="";
        String thumbnailSysName="";
        while (filenames.hasMoreElements()) {
            
        	String paramName = (String) filenames.nextElement();
        	 if (paramName.startsWith("contentImg")) {
        	        String fileSystemName = multi.getFilesystemName(paramName);

        	        if (fileSystemName != null) {
        	            contentImgSysName.append(fileSystemName+";");
        	        }
        	    }
            if(paramName.equals("thumbnail")) {
            	thumbnailSysName=multi.getFilesystemName(paramName);
            }
            
            
        	
            String fileName = multi.getOriginalFileName(paramName);
            String fileSystemName = multi.getFilesystemName(paramName);
            
            System.out.println("파일 경로: " + sUploadPath);
            System.out.println("파라미터 이름: " + paramName);
            System.out.println("원본 파일 이름: " + fileName);
            System.out.println("시스템 파일 이름: " + fileSystemName);
            System.out.println(contentImgSysNames);
        }
        
        contentImgSysNames=contentImgSysName.toString();
        
        HttpSession session = request.getSession();
        session.setAttribute("title", multi.getParameter("title"));
        session.setAttribute("summary", multi.getParameter("summary"));
        session.setAttribute("contentImgSysNames", contentImgSysNames); // 컬럼에 올릴 저장용이자 서버폴더에 올릴 실제 파일명 나열
        session.setAttribute("content", multi.getParameter("content"));
        session.setAttribute("thumbnail", thumbnailSysName);
        session.setAttribute("startdate", multi.getParameter("startdate"));
        session.setAttribute("enddate", multi.getParameter("enddate"));
        session.setAttribute("goal_amount", multi.getParameter("goal_amount"));
        
        
        
        
        request.setAttribute("showPage", "project/insertProjectReward.jsp");
        forward = new ActionForward("userTemplate.jsp", false);

        return forward;
    }

}
