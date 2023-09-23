package action.project.manage;

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
import svc.project.EditProjectService;
import vo.ActionForward;
import vo.PlannerBean;
import vo.ProjectBean;

/** 미완 */
public class EditProjectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;

		int project_id = Integer.parseInt(request.getParameter("project_id"));
		
		/* <파일 업로드> ------------------------------------------------------*/
        
        // 파일 업로드 관련 설정
		String uploadPath = "images/project_No_"+project_id; //파일저장 경로 (temp 임시폴더에 먼저 저장)
        
		ServletContext context = request.getServletContext();
		String sUploadPath = context.getRealPath(uploadPath);
        
		File dir = new File(sUploadPath);
		if(!dir.exists()){
			dir.mkdirs();//upload 폴더가 존재하지 않으면 해당 위치에 생성
		}
		
        // 파일 업로드 처리
        MultipartRequest multi = new MultipartRequest(
        											 request,
        											 sUploadPath,//파일업로드 경로
        											 10 * 1024 * 1024, // 최대 업로드 파일 크기 (10MB)
        											 "UTF-8",//파일명 인코딩
        											 new DefaultFileRenamePolicy() // 동일한 파일명 처리 정책
        											 );
        
        //★★업로드된 파일의 경로 가져오기
        Enumeration<String> filenames = multi.getFileNames();
        
        //파일이 몇개까지 첨부될지 모르므로, 하나의 String객체만 생성하기 위해 StringBuffer 사용
        StringBuffer contentImgSysName = new StringBuffer();
        
        String contentImgSysNames = ""; //프로젝트 내용 이미지
        String thumbnailSysName = ""; //프로젝트 썸네일 이미지
       
        while (filenames.hasMoreElements()) {
            
        	//input[type="file"] 태그의 name
        	String paramName = (String) filenames.nextElement();
        	
        	//만약 파일태그 name이 contentImg로 시작한다면
        	if(paramName.startsWith("contentImg")) {
        		
        		//해당 name으로 저장된 실제파일이름을 얻어와
        		String fileSystemName = multi.getFilesystemName(paramName);
        		
        		//contentImgSysName에 append (파일 구분을 위해 ;을 붙임)
        		if(fileSystemName != null) {
        			contentImgSysName.append(fileSystemName+";");
        		}
    		}
        	
        	//만약 파일태그 name이 thumbnail이라면 (썸네일 이미지는 1개만 등록)
            if(paramName.equals("thumbnail")) {
            	
            	//해당 name으로 저장된 실제파일이름을 얻어와 thumbnailSysName에 대입
        		thumbnailSysName = multi.getFilesystemName(paramName);
            }
            
            //파일이름 및 파일경로 확인
            String fileName = multi.getOriginalFileName(paramName);
            String fileSystemName = multi.getFilesystemName(paramName);
            
            System.out.println("파일 경로: " + sUploadPath);
            System.out.println("파라미터 이름: " + paramName);
            System.out.println("원본 파일 이름: " + fileName);
            System.out.println("시스템 파일 이름: " + fileSystemName);
            System.out.println(contentImgSysNames);
        }//while문 끝
        
        //;으로 이어진 프로젝트 내용 이미지들 이름을 하나의 String객체로 만듦
        contentImgSysNames = contentImgSysName.toString();
        
        //기존 이미지 중 삭제되지 않은 이미지 목록
		String[] originImages = multi.getParameterValues("originImages");
		StringBuffer origImgBuffer = new StringBuffer();
		for(int i=0; i<originImages.length; i++) {
			String origImg = originImages[i];
			origImgBuffer.append(origImg+";");
		}
		String origImgNames = origImgBuffer.toString();
		
		//수정될 프로젝트의 전체 이미지 목록
		String image = origImgNames + contentImgSysNames;
        
		String member_id = multi.getParameter("member_id");
		String planner_name = multi.getParameter("planner_name");
		String introduce = multi.getParameter("introduce");
		String bank="";
		if(request.getParameter("bank") != null) {
			bank= multi.getParameter("bank");
			}else {
				bank= multi.getParameter("otherBankName");
			}
		String account_num = multi.getParameter("account_num");
		
		String title = multi.getParameter("title");
		String summary = multi.getParameter("summary");
		String content = multi.getParameter("content");
		String startdate = multi.getParameter("startdate");
		String enddate = multi.getParameter("enddate");
		int goal_amount = Integer.parseInt(multi.getParameter("goal_amount").replace(",", ""));
		
		
		//파라미터값 확인
		System.out.println("[EditProjectAction] 파라미터 값 확인");
		System.out.println("member_id : "+ member_id);
		System.out.println("project_id : " + project_id);
		System.out.println("planner_name : " + planner_name);
		System.out.println("introduce : " + introduce);
		System.out.println("bank : " + bank);
		System.out.println("account_num : " + account_num);
		System.out.println("title : " + title);
		System.out.println("summary : " + summary);
		System.out.println("content : " + content);
		System.out.println("startdate : " + startdate);
		System.out.println("enddate : " + enddate);
		System.out.println("goal_amount : " + goal_amount);
		
		//프로젝트 객체 생성
		ProjectBean projectInfo = new ProjectBean(project_id, title, summary, thumbnailSysName, content, image, startdate, enddate, goal_amount);
					
		//플래너 객체 생성
		PlannerBean plannerInfo = new PlannerBean(project_id, member_id, planner_name, introduce, bank, account_num);
		
		EditProjectService editProjectService = new EditProjectService();
		
		boolean isEditProjectPlannerSuccess = editProjectService.editProjectPlanner(projectInfo, plannerInfo);
		
		System.out.println("plannerInfo.getPlanner_name()"+plannerInfo.getPlanner_name());
		
				
		if(!isEditProjectPlannerSuccess) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('프로젝트 수정에 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			//세션에 저장했던 프로젝트 아이디 삭제
			//session.removeAttribute("project_id");
			
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('프로젝트 수정에 성공하였습니다.');");
			out.println("window.location.href = 'userUploadProjectList.usr'");
			out.println("</script>");
		}
				
        return forward;
	}

}
