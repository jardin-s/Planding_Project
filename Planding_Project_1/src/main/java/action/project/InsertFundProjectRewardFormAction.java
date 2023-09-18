package action.project;

import java.io.File;
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
import vo.ActionForward;
import vo.ProjectBean;

//프로젝트 기획자 입력, 프로젝트 내용 입력 후 임시저장
public class InsertFundProjectRewardFormAction implements Action {

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
        
        //--------------------------------------------------------------------------------------------- 임시 폴더에 파일 저장 끝
        
        /* 파라미터값(입력한 프로젝트 내용들)으로 생성한 ProjectBean객체를 세션에 저장 -------------------------------------------------------------------*/
        System.out.println("[InsertFundProjectRewardFormAction] hidden으로 넘어온 kind값 = "+multi.getParameter("kind"));
        
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
        //달성률 세팅 (미리보기는 항상 75%로 고정)
        //projectInfo.setProgressFormatWithCurrGoal(0, Integer.parseInt(multi.getParameter("goal_amount").replace(",", "")));
       
        //남은일수 세팅
        LocalDate startdate_date = LocalDate.parse(startdate_str);//날짜로 변환
        LocalDate enddate_date = LocalDate.parse(enddate_str);
        				
        long deadline = ChronoUnit.DAYS.between(startdate_date, enddate_date);//두 날짜 사이 일수차이를 구함
        projectInfo.setDeadline((int)deadline);
        
        
        //세션에 저장
        HttpSession session = request.getSession();
        session.setAttribute("projectInfo", projectInfo);
        
        //리워드 입력 폼으로 이동
  		request.setAttribute("showPage", "project/insertProjectRewardForm.jsp");
  		forward=new ActionForward("userTemplate.jsp", false);
        
		return forward;
    }
    
}
