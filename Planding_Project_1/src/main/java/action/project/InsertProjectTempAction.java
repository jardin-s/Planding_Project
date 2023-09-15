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
import svc.project.InsertPlannerSevice;
import svc.project.InsertProjectService;
import vo.ActionForward;
import vo.PlannerBean;
import vo.ProjectBean;

//프로젝트 기획자 입력, 프로젝트 내용 입력 후 임시저장
public class InsertProjectTempAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
        
		//프로젝트 Id 0으로 초기화
		int project_id = 0;
        
        /* <파일 업로드> ------------------------------------------------------*/
        
        // 파일 업로드 관련 설정
		String uploadPath = "images/temp"; //파일저장 경로 (temp 임시폴더에 먼저 저장)
        
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
        
        
        
        /* 파라미터값(입력한 프로젝트 내용들) 세션에 저장 -------------------------------------------------------------------*/
        //이전페이지로 돌아갈 경우 세션으로 값을 받아와 폼에 올리므로 세션 필요
        
        HttpSession session = request.getSession();
        
        session.setAttribute("title", multi.getParameter("title"));
        session.setAttribute("summary", multi.getParameter("summary"));
        session.setAttribute("contentImgSysNames", contentImgSysNames); //컬럼에 올릴 저장용이자 서버폴더에 올릴 실제 파일명 나열
        session.setAttribute("content", multi.getParameter("content"));
        session.setAttribute("thumbnail", thumbnailSysName);
        session.setAttribute("startdate", multi.getParameter("startdate"));
        session.setAttribute("enddate", multi.getParameter("enddate"));
        session.setAttribute("goal_amount", multi.getParameter("goal_amount"));
        
        /* 프로젝트 기획자 저장을 위해 파라미터로 넘긴 값 member_id */
        String member_id = multi.getParameter("member_id");

        /* 프로젝트 및 기획자 저장 -------------------------------------------------------------------*/
		//[순서-1] project_tbl에 프로젝트 저장
        //session에서 kind값 + 파라미터값들을 가져와
        String kind = (String)session.getAttribute("kind");
		String title = multi.getParameter("title");
		String summary = multi.getParameter("summary");
		String content = multi.getParameter("content");
		String startdate = multi.getParameter("startdate");
		String enddate = multi.getParameter("enddate");
		int goal_amount = Integer.parseInt(multi.getParameter("goal_amount").replace(",", ""));
		
		//ProjectBean객체 생성
		ProjectBean project = new ProjectBean();
		project.setKind(kind);
		project.setTitle(title);
		project.setSummary(summary);
		project.setThumbnail(thumbnailSysName);
		project.setContent(content);
		project.setImage(contentImgSysNames);
		project.setStartdate(startdate);
		project.setEnddate(enddate);
		project.setGoal_amount(goal_amount);
		
		//프로젝트 테이블에 프로젝트 저장
		InsertProjectService insertProjectService = new InsertProjectService();
		boolean isInsertProjectSuccess = insertProjectService.insertProjectService(project);
		
		
		if(!isInsertProjectSuccess) {//프로젝트 insert 실패 시
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('프로젝트 저장에 실패하였습니다.');");
			out.println("history.back();");
			out.println("</script>");
			
		} else {//프로젝트 insert 성공 시
			
			//프로젝트 ID를 가지고 옴
			InsertPlannerSevice insertPlannerSevice = new InsertPlannerSevice();
			project_id = insertPlannerSevice.getProjectID(project);
			
			
			if(project_id==0) {//프로젝트 id 조회 실패 (1부터 자동 1증가이므로)
				response.setContentType("text/html; charset=UTF-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('프로젝트 저장에 실패하였습니다.');");
				out.println("history.back();");
				out.println("</script>");
			
			}else{//프로젝트 id 조회 성공
				
				//해당 project_id를 session에 저장
				session.setAttribute("project_id", project_id );
				project.setProject_id(project_id);//projectBean에 세팅
				
				//이전 페이지에서 입력해 세션에 저장해둔 프로젝트 기획자 입력값을 가지고 옴(위에서 multi로 가지고 옴)
				//String member_id = request.getParameter("member_id");//hidden타입 태그에 담긴 값
				System.out.println("[InsertProjectTempAction] 파라미터로 넘어온 member_id = "+member_id);
				
				String planner_name = (String)session.getAttribute("planner_name");
				String introduce = (String)session.getAttribute("introduce");
				String bank = (String)session.getAttribute("bank");
				String account = (String)session.getAttribute("account");
				
				//Planner객체 생성 후 입력한 값으로 세팅
				PlannerBean planner = new PlannerBean();
				planner.setProject_id(project_id);//프로젝트 insert 후 위에서 얻어온 project_id
				planner.setMember_id(member_id);
				planner.setPlanner_name(planner_name);
				planner.setIntroduce(introduce);
				planner.setBank(bank);
				planner.setAccount(account);
				
				//프로젝트 기획자 insert
				boolean isInsertPlannerSuccess = insertPlannerSevice.insertPlanner(planner);
				
				if(!isInsertPlannerSuccess) {//기획자 insert 실패 시
					response.setContentType("text/html; charset=UTF-8");
					
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('프로젝트 등록에 실패하였습니다.');");
					out.println("history.back();");
					out.println("</script>");
					
				}else {//기획자 insert 성공 시
					
					if(kind.equalsIgnoreCase("donate")) {//기부 프로젝트라면
						response.setContentType("text/html; charset=UTF-8");
						
						PrintWriter out = response.getWriter();
						out.println("<script>");
						out.println("alert('저장되었습니다.');");//저장 알림창 띄우기
						out.println("location.href='donateTempPageView.pj'");//기부프로젝트 미리보기 페이지로 이동
						out.println("</script>");
					}else if(kind.equalsIgnoreCase("fund")){//펀딩 프로젝트라면
						response.setContentType("text/html; charset=UTF-8");
						
						PrintWriter out = response.getWriter();
						out.println("<script>");
						out.println("alert('저장되었습니다.');");//저장 알림창 띄우기
						out.println("location.href='fundTempPageView.pj'");//펀딩프로젝트 미리보기 페이지로 이동
						out.println("</script>");
					}
					
				}//프로젝트 기획자 insert 성공여부
			
			}//프로젝트 id 조회 성공여부
						
		}//프로젝트 insert 성공여부

        return forward;
    }
    
}
