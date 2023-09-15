package action.project;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.project.*;
import vo.*;

public class SubmitProjectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
//		int project_id=1;
//		int reward_id=1;
		
		//파라미터 값을 얻어옴
		int project_id = Integer.parseInt(request.getParameter("project_id"));
		int reward_id = Integer.parseInt(request.getParameter("reward_id"));
		String status = request.getParameter("editStatus");//제출하기를 눌렀다면, editStatus="unauthorize"값으로 넘어옴
		
		System.out.println("[SubmitProjectAction] 파라미터값");
		System.out.println("project_id = "+project_id);
		System.out.println("reward_id = "+reward_id);
		System.out.println("editStatus = "+status);
		
		//★★세션에 저장된 아이디가 없으면 뒤로가기 처리한 부분 삭제
		//이유? 프로젝트 내용 다 작성했는데 그동안 세션이 만료돼서 뒤로가기 처리되어봤자
		//다시 제출하기 눌러도 어차피 뒤로가기밖에 안되고, 내용 벗어나서 로그인 하고 다시 처음부터 작성해야됨
		//처음 프로젝트 등록하기 눌렀을 때만 세션에 아이디 있는지 확인하고, 그 다음부터 member_id가 필요하다면 request로 넘기도록 수정함
		
		//프로젝트 정보를 얻어옴
		ProjectPageViewService ProjectPageViewService = new ProjectPageViewService();
		ProjectBean projectInfo = ProjectPageViewService.getProjectInfo(project_id);
		
		/*------ 임시 폴더에 저장된 이미지들을 실제 폴더로 이동 (이때, 실제폴더 : project_No_해당프로젝트ID) -------*/
		
		//[순서-1] ;을 구분자로 image를 나누고 myFileNames에 하나씩 저장
		ArrayList<String> myFileNames = new ArrayList<>();
		for(int i=0;i<projectInfo.getImage().split(";").length;i++) {
			myFileNames.add(projectInfo.getImage().split(";")[i]);
		}
		myFileNames.add(projectInfo.getThumbnail());
		
		//[순서-2] 이미지 저장 경로 처리
		String sourceImgFolder = "images/temp"; // 임시 폴더 경로
        String targetImgFolder = "images/project_No_"+project_id; //이동할 목표 폴더 경로
        
        ServletContext context = request.getServletContext();
        String sourceFolderPath = context.getRealPath(sourceImgFolder);
        String targetFolderPath = context.getRealPath(targetImgFolder);
        
        try {
            File sourceFolder = new File(sourceFolderPath);//임시폴더
            File targetFolder = new File(targetFolderPath);//이동할 폴더

            if (!sourceFolder.exists()) {
                // 임시 폴더가 존재하지 않으면 처리 중단
                throw new IOException("임시 폴더가 존재하지 않습니다.");
            }

            if (!targetFolder.exists()) {
                targetFolder.mkdirs(); // 이동할 폴더가 없으면 생성
            }

            //[순서-3] 임시 폴더의 파일 목록을 가져와서 이동
            File[] files = sourceFolder.listFiles();//해당 폴더에 위치한 파일들로 File타입 배열 반환
            if (files != null) {//임시 폴더에 저장된 파일이 있으면
            		
            	for (File file : files) {
            		
            		if(file.isFile()) {//폴더 등이 아닌 실제 파일인지 확인
            			
            			String fileName = file.getName();//해당 파일의 이름을 가져옴
            			
            			boolean shouldMove = false;//이동할지 여부
                    	
            			//myFileNames : project의 각 이미지들 이름이 있음
            			for (String myFileName : myFileNames) {
                            if (fileName.equals(myFileName)) {//임시폴더에 저장된 파일이름이 프로젝트 이미지와 같다면
                                shouldMove = true;//이동여부 true
                                break;
                            }
                        }
                    	
            			if (shouldMove) {
                            //파일을 지정한 폴더로 이동 (파일이름 : 해당프로젝트ID_파일명)
                            File targetFile = new File(targetFolder, project_id+"_"+fileName);
                            Files.move(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        }
                    }
                }
            }

            //[순서-4] 폴더 이동 작업 후 임시 폴더 삭제
            sourceFolder.delete();
            
            
            /*------------ 프로젝트ID와 리워드ID로 프로젝트-리워드 매핑 ------------*/
            ProjectRewardMappingService projectRewardMappingService = new ProjectRewardMappingService();
            boolean isMapProjectRewardSuccess = projectRewardMappingService.mapProjectReward(project_id, reward_id);
            
            if(!isMapProjectRewardSuccess) {
            	System.out.println(" submitProjectAction error : project_reward_tbl_Service ");
            	
            }else {
            	ProjectStatusUpdateService projectStatusUpdateService=new ProjectStatusUpdateService();
            	boolean updateSuccess = projectStatusUpdateService.projectStatusUpdateService(project_id,status);
            	
            	if(!updateSuccess) {
	            	System.out.println(" submitProjectAction error : projectStatusUpdateService ");
            	}else {
            		
            		
            		forward = new ActionForward("userUploadProjectList.usr",true);//반드시 디스패치 (request를 공유)
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
			
		
		return forward;
	}

}