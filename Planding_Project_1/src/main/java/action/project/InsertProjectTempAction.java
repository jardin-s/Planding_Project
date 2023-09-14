package action.project;

import java.io.File;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import svc.project.GetProjectIDService;
import svc.project.InsertPlannerSevice;
import svc.project.InsertProjectService;
import vo.ActionForward;
import vo.PlannerBean;
import vo.ProjectBean;

public class InsertProjectTempAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = null;
        int project_id=0;
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
//이전페이지로 돌아갈 경우 세션으로 값을 받아와 폼에 올리므로 세션 필요        

        //프로젝트+플래너 저장 파트
		String kind=(String)session.getAttribute("kind");
		String title=multi.getParameter("title");
		String summary=multi.getParameter("summary");
		String content=multi.getParameter("content");
		String startdate=multi.getParameter("startdate");
		String enddate=multi.getParameter("enddate");
		int goal_amount=Integer.parseInt(multi.getParameter("goal_amount").replace(",", ""));
		
		ProjectBean pj = new ProjectBean();
		pj.setKind(kind);
		pj.setTitle(title);
		pj.setSummary(summary);
		pj.setThumbnail(thumbnailSysName);
		pj.setContent(content);
		pj.setImage(contentImgSysNames);
		pj.setStartdate(startdate);
		pj.setEnddate(enddate);
		pj.setGoal_amount(goal_amount);
		
		InsertProjectService insertProjectService=new InsertProjectService();
		boolean isInsertProjectSuccess = insertProjectService.insertProjectService(pj);
		
		
		if(!isInsertProjectSuccess) { //실패
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('프로젝트 저장에 실패하였습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else { //성공-플래너 등록
			GetProjectIDService getProjectIDService=new GetProjectIDService();
			project_id=getProjectIDService.getProjectID(pj);
			if(project_id==0) {
				response.setContentType("text/html; charset=UTF-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('프로젝트 저장에 실패하였습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}else{
				session.setAttribute("project_id", project_id );
				pj.setProject_id(project_id);//project_id를 얻어와서 projectBean에 넣어둠
				
				String member_id=(String)session.getAttribute("u_id");
				String planner_name=(String)session.getAttribute("planner_name");
				String introduce=(String)session.getAttribute("introduce");
				String bank=(String)session.getAttribute("bank");
				String account=(String)session.getAttribute("account");
	
				
				PlannerBean planner = new PlannerBean();
				planner.setProject_id(project_id);
				planner.setMember_id(member_id);
				planner.setPlanner_name(planner_name);
				planner.setIntroduce(introduce);
				planner.setBank(bank);
				planner.setAccount(account);
				
				InsertPlannerSevice insertPlannerSevice=new InsertPlannerSevice();
				boolean isInsertPlannerSuccess=insertPlannerSevice.insertPlanner(planner);
				
				if(!isInsertPlannerSuccess) {
					response.setContentType("text/html; charset=UTF-8");
					
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('프로젝트 등록에 실패하였습니다.');");
					out.println("history.back();");
					out.println("</script>");
				}else {
					
					response.setContentType("text/html; charset=UTF-8");
					
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('저장되었습니다.');");
					out.println("</script>");
				}
			}
			
			
		}
        
        
        request.setAttribute("showPage", "project/insertProjectSuccess.jsp?project_id="+project_id);
        forward = new ActionForward("userTemplate.jsp", false);

        return forward;
    }

}
