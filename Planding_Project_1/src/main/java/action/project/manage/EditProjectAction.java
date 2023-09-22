package action.project.manage;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import svc.project.EditProjectService;
import svc.project.ProjectPageViewService;
import vo.ActionForward;
import vo.PlannerBean;
import vo.ProjectBean;
import vo.RewardBean;

/** 미완 */
public class EditProjectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession();
		int project_id = Integer.parseInt(request.getParameter("project_id"));
		request.setAttribute("project_id", project_id);//일단 올려두기
		ProjectPageViewService projectPageViewService = new ProjectPageViewService();
		
		//프로젝트 정보를 얻어옴
		ProjectBean projectInfo=projectPageViewService.getProjectInfo(project_id);
		String[] contentImgs =  projectInfo.getImage().split(";");
		ArrayList<String> imgArr = new ArrayList<>();
		
		//새로받은 이미지들과 비교할 배열에 기존 콘텐트이미지이름 삽입
		for(int i = 0 ; i < contentImgs.length ; i++) {
			imgArr.add(contentImgs[i]);
			System.out.println("imgArr.get("+i+") : " +imgArr.get(i));
		}
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
       
        ArrayList<Integer> modifyedContentImgNum = new ArrayList<>();
        ArrayList<String> modifyedContentImgName = new ArrayList<>();
        int contentImgCount = 0; // contentImg 파일의 번호를 초기화
        while (filenames.hasMoreElements()) {
            
        	//input[type="file"] 태그의 name
        	String paramName = (String) filenames.nextElement();
        	
        	//만약 파일태그 name이 contentImg로 시작한다면
        	if (paramName.startsWith("contentImg" + contentImgCount)) {
        	       // 해당 name으로 저장된 실제파일이름을 얻어와
        	       String fileSystemName = multi.getFilesystemName(paramName);
        	        
        	       // contentImgSysName에 append (파일 구분을 위해 ;을 붙임)
        	       if (fileSystemName != null) {
        	    	   //modifyedContentImgName에 
        	    	   modifyedContentImgName.add(fileSystemName);
//        	            contentImgSysName.append(fileSystemName + ";");
        	            //수정된 이미지 번호를 얻어옴
        	            modifyedContentImgNum.add(contentImgCount);
        	       }
        	       contentImgCount++;
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
        
        //modifyedContentImgName.get(modifyedContentImgNum.get(i))==imgArr.get(modifyedContentImgNum.get(i))
        //따라서 기존 이미지 리스트인 imgArr에 삽입하여 목록을 업데이트 할 수 있음
        for(int i = 0 ; i < modifyedContentImgNum.size() ; i++ ) {
        	int num = modifyedContentImgNum.get(i);
        	//둘의 값이 다르다면 기존 파일명 ArrayList(imgArr)에 새로받은값 삽입
        	if(!(modifyedContentImgName.get(num).equals(imgArr.get(num)))){
        		imgArr.remove(num);
        		imgArr.set(num, modifyedContentImgName.get(num));
        	}
        }
        //;으로 이어진 프로젝트 내용 이미지들 이름을 하나의 String객체로 만듦
        for(int i = 0 ; i < imgArr.size() ; i++) {
        	contentImgSysName.append(imgArr.get(i)+";");
        }
        contentImgSysNames = contentImgSysName.toString();
        //--------------------------------------------------------------파일 업로드 끝
        //--------------------------------------------------------------파일명 변경(project_id+"_"+FILENAME.jpg), 불필요한 파일삭제는 블록처리함
     // 폴더 내의 파일 검사 및 변경/삭제
        File folder = new File(sUploadPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName();

                    // 파일 이름이 project_id+"_"로 시작하지 않는 경우 변경
                    if (!fileName.startsWith(project_id + "_")) {
                        String newFileName = project_id + "_" + fileName;

                        // 파일 이름 변경
                        if (file.renameTo(new File(folder, newFileName))) {
                            System.out.println("파일 이름 변경: " + fileName + " -> " + newFileName);
                        } else {
                            System.out.println("파일 이름 변경 실패: " + fileName);
                        }
                    }
//                    String thumbnailSysNameTemp = "";
//                    if(!thumbnailSysName.startsWith(project_id + "_")) {
//                    	thumbnailSysNameTemp=project_id+"_"+thumbnailSysName;
//                    }
//                    // 썸네일 및 imgArr의 값과 대조하여 필요없는 파일 삭제
//                    if (!fileName.equals(thumbnailSysNameTemp) && !imgArr.contains(fileName)) {
//                        if (file.delete()) {
//                            System.out.println("불필요한 파일 삭제: " + fileName);
//                        } else {
//                            System.out.println("파일 삭제 실패: " + fileName);
//                        }
//                    }
                }
            }
        }
        
        //-------------------------------------------------------------------
        //[손정원] 위의 소스를 수정함
        System.out.println("[EditProjectAction] hidden으로 넘어온 kind값 = "+multi.getParameter("kind"));
        
        String startdate_str = multi.getParameter("startdate");
        String enddate_str = multi.getParameter("enddate");
        
        projectInfo = new ProjectBean(multi.getParameter("kind"),
			        				  multi.getParameter("title"),
			        				  multi.getParameter("summary"),
			        				  thumbnailSysName,
			        				  multi.getParameter("content"),
			        				  contentImgSysNames,
			        				  startdate_str,
			        				  enddate_str,
			        				  Integer.parseInt(multi.getParameter("goal_amount").replace(",", "")),
			        				  0);
        //달성률 세팅 (미리보기 페이지는 항상 달성률 75%로 고정)
//        projectInfo.setProgressFormatWithCurrGoal(0, Integer.parseInt(multi.getParameter("goal_amount").replace(",", "")));
        
        //남은일수 세팅 (미리보기에서는 오늘-종료일이 아니라 시작일-종료일로 계산해둠)
        LocalDate startdate_date = LocalDate.parse(startdate_str);//날짜로 변환
        LocalDate enddate_date = LocalDate.parse(enddate_str);
        				
        long deadline = ChronoUnit.DAYS.between(startdate_date, enddate_date);//두 날짜 사이 일수차이를 구함
        projectInfo.setDeadline((int)deadline);
        
        System.out.println("[InsertDonateProjectTempAction] 생성된 ProjectBean객체");
        System.out.println("kind = "+projectInfo.getKind());
        System.out.println("title = "+projectInfo.getTitle());
        System.out.println("summary = "+projectInfo.getSummary());
        System.out.println("thumbnail = "+projectInfo.getThumbnail());
        System.out.println("content = "+projectInfo.getContent());
        System.out.println("image = "+projectInfo.getImage());
        System.out.println("startdate = "+projectInfo.getStartdate());
        System.out.println("enddate = "+projectInfo.getEnddate());
        System.out.println("deadline = "+projectInfo.getDeadline());
        System.out.println("goal_amount = "+projectInfo.getGoal_amount());
        
        

        request.setAttribute("projectInfo", projectInfo);
		
		PlannerBean planner = new PlannerBean();
		planner.setPlanner_name(multi.getParameter("planner_name"));
		planner.setIntroduce(multi.getParameter("introduce"));
        //기타 은행 케이스 처리
		if(multi.getParameter("otherBankName") != null) {
			planner.setBank(multi.getParameter("bank"));
		}else {
			planner.setBank(multi.getParameter("otherBankName"));
		}
		planner.setAccount_num(multi.getParameter("account"));
		planner.setMember_id((String)session.getAttribute("u_id"));
		
		
		/*****************************객체 세팅이 완료, sql업데이트*********************************/
		EditProjectService editProjectService = new EditProjectService();
		boolean editProject=editProjectService.editProject(projectInfo);
		boolean editPlanner=editProjectService.editPlanner(planner);
		System.out.println("planner.getPlanner_name()"+planner.getPlanner_name());
		
		
		
		if(editProject && editPlanner) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('프로젝트 수정에 성공하였습니다.');");
			out.println("window.location.href = 'userUploadProjectList.usr';");
			out.println("</script>");

		
		}else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('프로젝트 수정에 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}		
		
						
		/* response.setContentType("text/html; charset=UTF-8");
		 * 
		 * PrintWriter out = response.getWriter(); out.println("<script>");
		 * out.println("alert('저장되었습니다.');");//저장 알림창 띄우기
		 * out.println("location.href='donateTempPageView.pj'");//기부프로젝트 미리보기 페이지로 이동
		 * out.println("</script>");
		 */

        return forward;
    }
    
}
