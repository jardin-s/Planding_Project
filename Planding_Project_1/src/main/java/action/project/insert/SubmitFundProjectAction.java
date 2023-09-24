package action.project.insert;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.project.*;
import vo.*;

public class SubmitFundProjectAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
				
		//★★세션에 저장된 아이디가 없으면 뒤로가기 처리한 부분 삭제
		//이유? 프로젝트 내용 다 작성했는데 그동안 세션이 만료돼서 뒤로가기 처리되어봤자
		//다시 제출하기 눌러도 어차피 뒤로가기밖에 안되고, 내용 벗어나서 로그인 하고 다시 처음부터 작성해야됨
		//처음 프로젝트 등록하기 눌렀을 때만 세션에 아이디 있는지 확인하고, 그 다음부터 member_id가 필요하다면 request로 넘기도록 수정함
		
		/* 1. 프로젝트 정보로 프로젝트 insert하고 프로젝트id를 얻어옴
		 * 2. 이미지를 임시폴더에서 해당 프로젝트이미지로 이동 후 임시폴더 삭제
		 * 3. 프로젝트 기획자 insert
		 * 4. 프로젝트 리워드 insert
		 * 5. 프로젝트-리워드 매핑 insert
		 * 6. 포워딩 처리 
		 */
		
		
		//프로젝트 정보를 얻어옴
		HttpSession session = request.getSession();
		ProjectBean projectInfo = (ProjectBean) session.getAttribute("projectInfo");
		
		//(1) 프로젝트를 insert -------------------------------------------------------------------------------------
		InsertProjectService insertProjectService = new InsertProjectService();
		boolean isInsertProjectSuccess = insertProjectService.insertProject(projectInfo);
		
		if(!isInsertProjectSuccess) {//프로젝트 insert 실패
			response.setContentType("text/html; charset=UTF-8");
            
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('프로젝트 저장에 실패했습니다.');");
            out.println("history.back();");
            out.println("</script>");
            
		}else {//프로젝트 insert 성공
			
			//등록한 프로젝트 ID를 얻어옴
			int project_id = insertProjectService.getProjectID(projectInfo.getTitle(), projectInfo.getThumbnail());
			
			
			//(2) 임시 폴더에 저장된 이미지들을 실제 폴더로 이동 (이때, 실제폴더 : project_No_해당프로젝트ID) -------------------------
			
			//[순서-1] ;을 구분자로 image를 나누고 myFileNames에 하나씩 저장
			ArrayList<String> myFileNames = new ArrayList<>();
			for(int i=0;i<projectInfo.getImage().split(";").length;i++) {
				myFileNames.add(projectInfo.getImage().split(";")[i]);
			}
			myFileNames.add(projectInfo.getThumbnail());
			//(+) 썸네일 이미지도 이동을 해야하므로 썸네일 이미지를 가져옴
			String thumbnail = projectInfo.getThumbnail();
			
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
	            			
	            			if(fileName.equals(thumbnail)) {//임시폴더에 저장된 파일이름이 썸네일 이미지와 같다면
	            				shouldMove = true;//이동여부 true
	            			}
	                    	
	            			if (shouldMove) {
	                            //파일을 지정한 폴더로 이동 (파일이름 : 해당프로젝트ID_파일명)
	                            File targetFile = new File(targetFolder, fileName);
	                            Files.move(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	                        }
	                    }
	                }
	            }

	            //[순서-4] 폴더 이동 작업 후 임시 폴더 삭제
	            sourceFolder.delete();
	        
	        } catch (IOException e) {
	            System.out.println("[SubmitFundProjectAction] 이미지 폴더 이동 시 에러 발생 : "+e);
	        	e.printStackTrace();

	            response.setContentType("text/html; charset=UTF-8");
	            
	            PrintWriter out = response.getWriter();
	            out.println("<script>");
	            out.println("alert('폴더 이동에 실패하였습니다.');");
	            out.println("history.back();");
	            out.println("</script>");
	        }
	        
	        
	        //(3) 프로젝트 기획자 insert
	        PlannerBean plannerInfo = (PlannerBean) session.getAttribute("plannerInfo");
	        plannerInfo.setProject_id(project_id);//등록한 프로젝트ID를 세팅
	        
	        boolean isInsertPlannerSuccess = insertProjectService.insertPlanner(plannerInfo);
	        
	        
	        //(4) 프로젝트 리워드 insert
	        ArrayList<RewardBean> rewardList = (ArrayList<RewardBean>) session.getAttribute("rewardList");
	        
	        for(int i=0; i<rewardList.size(); i++) {
	        	RewardBean reward = rewardList.get(i);
	        	reward.setReward_id("pj"+project_id+"rwd"+(i+1));
	        	
	        	System.out.println("[SubmitFundProjectAction] 리워드 Id 세팅 : "+reward.getReward_id());
	        }
	        
	        boolean isInsertRewardListSuccess = insertProjectService.insertRewardList(rewardList);
	        
	        
	        //(5) 프로젝트-리워드 매핑 insert
	        boolean isMapProjectRewardList = insertProjectService.mapProjectRewardList(project_id, rewardList);
	        
	        
	        
	        if(!(isInsertPlannerSuccess && isInsertRewardListSuccess && isMapProjectRewardList)) {//기획자 insert, 리워드 insert, 프로젝트-리워드 매핑 실패시
	        	response.setContentType("text/html; charset=UTF-8");
                
            	PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('프로젝트 등록이 실패했습니다.');");
                out.println("history.back();");
                out.println("</script>");
                
	        }else {//기획자 insert, 리워드 insert, 프로젝트-리워드 매핑 성공시
	        	
	        	//미리보기를 위해 세션에 담아뒀던 정보들을 삭제
        		session = request.getSession();
        		session.removeAttribute("plannerInfo");
        		session.removeAttribute("projectInfo");
        		session.removeAttribute("otherBankName");
        		session.removeAttribute("r_name");
        		session.removeAttribute("r_contents");
        		session.removeAttribute("r_price");
        		session.removeAttribute("rewardInfo");
        		session.removeAttribute("rewardList");
        		
        		//내가 등록한 프로젝트 목록 보기 요청
        		forward = new ActionForward("userUploadProjectList.usr", true);//반드시 디스패치 (request를 공유)
	        	
	        }//기획자 insert & 프로젝트-리워드 매핑 성공여부
	        	        
		}//프로젝트 insert 성공여부
			
		return forward;
	}

}