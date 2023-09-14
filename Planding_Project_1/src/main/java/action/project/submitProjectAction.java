package action.project;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.project.*;
import vo.*;

public class submitProjectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession();
//		int project_id=1;
//		int reward_id=1;
		int project_id=Integer.parseInt(request.getParameter("project_id"));//안되는중
		int reward_id=Integer.parseInt(request.getParameter("reward_id"));
		String member_id=(String)session.getAttribute("u_id");
		if(member_id==null) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('잘못된 요청입니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			String sourceImgFolder = "images/temp"; // 임시 폴더 경로
	        String targetImgFolder = "images/project_No_"+project_id; // 이동할 목표 폴더 경로
	        ServletContext context = request.getServletContext();
	        String sourceFolderPath = context.getRealPath(sourceImgFolder);
	        String targetFolderPath = context.getRealPath(targetImgFolder);
	        // 폴더 이동 처리
	        try {
	            File sourceFolder = new File(sourceFolderPath);
	            File targetFolder = new File(targetFolderPath);

	            if (!sourceFolder.exists()) {
	                // 임시 폴더가 존재하지 않으면 처리 중단
	                throw new IOException("임시 폴더가 존재하지 않습니다.");
	            }

	            if (!targetFolder.exists()) {
	                targetFolder.mkdirs(); // 이동할 폴더가 없으면 생성
	            }

	            // 임시 폴더의 파일 목록을 가져와서 이동
	            File[] files = sourceFolder.listFiles();
	            if (files != null) {
	                for (File file : files) {
	                    if (file.isFile()) {
	                        // 파일을 이동합니다.
	                        File targetFile = new File(targetFolder, file.getName());
	                        Files.move(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	                    }
	                }
	            }

	            // 임시 폴더가 비어있으면 삭제
	            if (sourceFolder.list().length == 0) {
	                sourceFolder.delete();
	            }
	            
	            Project_reward_tbl_Service project_reward_tbl_Service=new Project_reward_tbl_Service();
	            boolean commitSuccess = project_reward_tbl_Service.project_reward_tbl_Service(project_id, reward_id);
	           boolean updateSuccess=false;
	            if(!commitSuccess) {
	            	System.out.println(" submitProjectAction error : project_reward_tbl_Service ");
	            	
	            }else {
	            	ProjectStatusUpdateService projectStatusUpdateService=new ProjectStatusUpdateService();
	            	updateSuccess=projectStatusUpdateService.projectStatusUpdateService(project_id,"unauthorized");
	            	
	            	if(!updateSuccess) {
		            	System.out.println(" submitProjectAction error : projectStatusUpdateService ");
	            	}else {
	            		request.setAttribute("showPage", "user/myPage/userUploadProjectList.jsp");//어느 폼 보기인지 showPage이름 속성으로 저장
	            		forward = new ActionForward("userTemplate.jsp",false);//반드시 디스패치 (request를 공유)
	            	}
	            }
	            
	        } catch (IOException e) {
	            System.out.println("이미지폴더 이동 error : ");
	        	e.printStackTrace();

	            response.setContentType("text/html; charset=UTF-8");
	            PrintWriter out = response.getWriter();
	            out.println("<script>");
	            out.println("alert('폴더 이동에 실패하였습니다.');");
	            out.println("history.back();");
	            out.println("</script>");
	        }
		}
		
		
		
		
	

		
		
		return forward;
	}

}