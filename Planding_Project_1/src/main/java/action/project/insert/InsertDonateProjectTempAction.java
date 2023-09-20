package action.project.insert;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import svc.project.InsertProjectService;
import svc.project.ProjectPageViewService;
import vo.ActionForward;
import vo.PlannerBean;
import vo.ProjectBean;
import vo.RewardBean;

//프로젝트 기획자 입력, 프로젝트 내용 입력 후 임시저장
public class InsertDonateProjectTempAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
        
		        
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
        
        //--------------------------------------------------------------임시폴더에 파일 업로드 끝
        
        //[손정원] 위의 소스를 수정함
        System.out.println("[InsertDonateProjectTempAction] hidden으로 넘어온 kind값 = "+multi.getParameter("kind"));
        
        String startdate_str = multi.getParameter("startdate");
        String enddate_str = multi.getParameter("enddate");
        
        ProjectBean projectInfo = new ProjectBean(multi.getParameter("kind"),
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
        //projectInfo.setProgressFormatWithCurrGoal(0, Integer.parseInt(multi.getParameter("goal_amount").replace(",", "")));
        
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
        
        
        //세션에 project이름으로 ProjectBean객체 저장
        HttpSession session = request.getSession();
        session.setAttribute("projectInfo", projectInfo);
		
        //★★★사실 미리보기 출력을 위해서는 정보만 있으면 되기 때문에, 미리 프로젝트와 기획자를 저장할 필요가 없지만, 수정하는 데 너무 많은 시간이 소요되므로 이대로 사용하겠음
		//수정 안하니까 최종 submit 때 너무 오류가 많이 발생해서 그냥 최종 submit때 DB저장하는 걸로 수정하겠음
		
					
		//기본 리워드(기부용) 정보를 얻어와 세션에 저장 ("default", "donate", 1000)
		ProjectPageViewService projectPageViewService = new ProjectPageViewService();
		RewardBean rewardInfo = projectPageViewService.getRewardInfo("default");
		session.setAttribute("rewardInfo", rewardInfo);
		
		//미리보기 페이지로 포워딩
		request.setAttribute("showPage", "project/insert/insertProjectTempView.jsp");
		forward = new ActionForward("userTemplate.jsp", false);		
						
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
