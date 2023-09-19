package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.DonationBean;
import vo.PlannerBean;
import vo.ProjectBean;
import vo.ProjectPlannerBean;
import vo.RewardBean;

public class ProjectDAO {
	
	//필드 : 전역변수 - 전체 메서드에서 사용가능하도록 선언
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	
	
	/** 싱글톤 패턴 : DogDAO 객체를 단 1개만 생성 **************************
	 * 이유? 외부 클래스에서 '처음 생성된 UserDAO객체를 공유해어 사용하도록' 하기 위함
	 * - 생성자를 private으로 하여 외부에서 객체 생성을 막음
	 * - getInstance()메서드로 객체를 하나만 생성
	 */
	
	//1. 생성자는 무조건 private : 외부에서 객체 생성 불가능
	private ProjectDAO() {}
	
	
	
	private static ProjectDAO projectDAO; //static메서드인 getInstance에서 쓸 수 있게 static (단 외부에서 직접 접근 불가능하도록 private)
	
	public static ProjectDAO getInstance() {
		if(projectDAO == null) {//DogDAO객체가 없으면
			projectDAO = new ProjectDAO();//객체 생성
		}
		
		return projectDAO;//기존 DogDAO객체의 주소 리턴
	}
	
	/******************************************************************/
	
	/**
	 * 1. "DB연결(Service)" -> 2. SQL 실행 -> 3. 결과처리 -> 4. DB연결해제(Service)
	 */
	//DB연결 : 매개값으로 받은 Connection객체의 주소값을 필드con에 셋팅
	public void setConnection(Connection con){
		this.con = con;
	}

	/** 프로젝트 ID로 프로젝트 정보를 얻어옴 */
	public ProjectBean selectProjectTime(int project_id) {
		ProjectBean projectInfo = null;
		
		String sql = "select project_id, kind, title,"
				  + " summary, thumbnail, content, image,"
				  + " DATE_FORMAT(startdate,'%Y.%m.%d %H:%i') as startdate,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d %H:%i') as enddate,"
				  + " goal_amount, curr_amount, status, likes,"
				  + " DATE_FORMAT(regdate,'%Y.%m.%d %H:%i') as regdate"
				  + " from project_tbl"
				  + " where project_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, project_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				projectInfo = new ProjectBean(rs.getInt("project_id"),
											  rs.getString("kind"),
											  rs.getString("title"),
											  rs.getString("summary"),
											  rs.getString("thumbnail"),
											  rs.getString("content"),
											  rs.getString("image"),
											  rs.getString("startdate"),
											  rs.getString("enddate"),
											  rs.getInt("goal_amount"),
											  rs.getInt("curr_amount"),
											  rs.getString("status"),
											  rs.getInt("likes"),
											  rs.getString("regdate")
											  );
				//현재모금액과 목표모금액으로 달성률 세팅
				projectInfo.setProgressFormatWithCurrGoal(rs.getInt("curr_amount"), rs.getInt("goal_amount"));
			}
			
			System.out.println("[ProjectDAO] selectProject() - rs값이 있는지 확인(title) : "+rs.getString("title"));
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectProject() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectInfo;
	}
	
	/** 프로젝트 ID로 프로젝트 정보를 얻어옴 (날짜 연월일만)*/
	public ProjectBean selectProjectDate(int project_id) {
		ProjectBean projectInfo = null;
		
		String sql = "select project_id, kind, title,"
				+ " summary, thumbnail, content, image,"
				+ " DATE_FORMAT(startdate,'%Y.%m.%d') as startdate,"
				+ " DATE_FORMAT(enddate,'%Y.%m.%d') as enddate,"
				+ " goal_amount, curr_amount, status, likes,"
				+ " DATE_FORMAT(regdate,'%Y.%m.%d') as regdate"
				+ " from project_tbl"
				+ " where project_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, project_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				projectInfo = new ProjectBean(rs.getInt("project_id"),
												rs.getString("kind"),
												rs.getString("title"),
												rs.getString("summary"),
												rs.getString("thumbnail"),
												rs.getString("content"),
												rs.getString("image"),
												rs.getString("startdate"),
												rs.getString("enddate"),
												rs.getInt("goal_amount"),
												rs.getInt("curr_amount"),
												rs.getString("status"),
												rs.getInt("likes"),
												rs.getString("regdate")
												);
				//현재모금액과 목표모금액으로 달성률 세팅
				projectInfo.setProgressFormatWithCurrGoal(rs.getInt("curr_amount"), rs.getInt("goal_amount"));
				
				//현재모금액, 목표모금액 천단위 구분자 세팅
				projectInfo.setCurr_amount_df_exc(rs.getInt("curr_amount"));
				projectInfo.setGoal_amount_df_exc(rs.getInt("goal_amount"));
				
				//남은 일수 세팅
				projectInfo.setDeadline_exc(rs.getString("enddate"));
				
			}
			
			System.out.println("[ProjectDAO] selectProject() - rs값이 있는지 확인(title) : "+rs.getString("title"));
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectProject() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectInfo;
	}

	/** 특정 회원의 후원기록을 가져오기 */
	public ArrayList<DonationBean> selectDonationList(String u_id) {
		ArrayList<DonationBean> donationList = null;
		
		String sql = "select donation_id, project_id, member_id, reward_id,"
				  + " r_price, add_donation, address_id,"
				  + " DATE_FORMAT(donatedate,'%Y.%m.%d') as donatedate"
				  + " from donation_tbl"
				  + " where member_id=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				donationList = new ArrayList<>();
				
				DonationBean donation = new DonationBean();
				donation.setDonation_id(rs.getInt("donation_id"));
				donation.setProject_id(rs.getInt("project_id"));
				donation.setMember_id(rs.getString("member_id"));
				donation.setReward_id(rs.getString("reward_id"));
				donation.setR_price(rs.getInt("r_price"));
				donation.setAdd_donation(rs.getInt("add_donation"));
				donation.setAddress_id(rs.getString("address_id"));
				donation.setDonatedate(rs.getString("donatedate"));
				
				donationList.add(donation);
			}
			
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectProject() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return donationList;
	}
	
	/** 프로젝트 임시 등록하기 - status가 temp. 최종적으로 등록하면 unauthorize로 변경 */
	public int insertProject(ProjectBean project) {
		int insertProjectCount = 0;
		
		String sql = "insert into project_tbl(kind,title,summary,thumbnail,content,image,startdate,enddate,goal_amount,curr_amount,status,likes)";
			   sql+= " values(?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			//project_id 자동생성
			pstmt.setString(1, project.getKind());
			pstmt.setString(2, project.getTitle());
			pstmt.setString(3, project.getSummary());
			pstmt.setString(4, project.getThumbnail());
			pstmt.setString(5, project.getContent());
			pstmt.setString(6, project.getImage());
			pstmt.setString(7, project.getStartdate());
			pstmt.setString(8, project.getEnddate());
			pstmt.setInt(9, project.getGoal_amount());
			pstmt.setInt(10, 0);
			pstmt.setString(11, "unauthorized");//미승인 상태
			pstmt.setInt(12, 0);
			
			insertProjectCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] insertProject() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return insertProjectCount;
	}

	/** 프로젝트 제목과 썸네일로 프로젝트 ID를 얻어오기 */
	public int getProjectID(String title, String thumbnail) {
		int project_id = 0;
		
		String sql = "select project_id from project_tbl where title=? and thumbnail=?";
		   	
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, thumbnail);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
	            project_id = rs.getInt(1);
	        }
			System.out.println("[ProjectDAO] getProjectID() 매개값 title = "+title);
			System.out.println("[ProjectDAO] getProjectID() 매개값 thumbnail = "+thumbnail);
			System.out.println("[ProjectDAO] getProjectID() 얻어온 ID값 확인 = "+project_id);
		
		} catch(Exception e) {
			System.out.println("[ProjectDAO] getProjectID() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return project_id;
	}

	/** 프로젝트 기획자 등록하기 */
	public int insertPlanner(PlannerBean planner) {
		int insertPlannerCount = 0;
		
		String sql ="insert into project_planner_tbl values(?,?,?,?,?,?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, planner.getProject_id());
			pstmt.setString(2, planner.getMember_id());
			pstmt.setString(3, planner.getPlanner_name());
			pstmt.setString(4, planner.getIntroduce());
			pstmt.setString(5, planner.getBank());
			pstmt.setString(6, planner.getAccount_num());

			insertPlannerCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] insertPlanner() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return insertPlannerCount;
	}
	
	
	/** 프로젝트ID로 기획자 정보 얻어오기 */
	public PlannerBean selectPlanner(int project_id) {
		
		PlannerBean plannerInfo = null;
		
		String sql = "select project_id, member_id, planner_name, introduce,"
				  + " introduce, bank, account_num"
				  + " from project_planner_tbl"
				  + " where project_id = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, project_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				plannerInfo = new PlannerBean(project_id,
											  rs.getString("member_id"),
											  rs.getString("planner_name"),
											  rs.getString("introduce"),
											  rs.getString("bank"),
											  rs.getString("account_num")
											  );
				
			}
			
	
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectPlanner() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return plannerInfo;
	}

	/** 프로젝트ID로 리워드ID 리스트 얻어오기 (기본리워드 포함) */
	public ArrayList<String> selectAllRewardIdList(int project_id) {
		ArrayList<String> rewardIdList = null;
		
		String sql = "select reward_id"
				  + " from project_reward_tbl"
				  + " where project_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, project_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				rewardIdList = new ArrayList<>();
				
				do {
					
					rewardIdList.add(rs.getString("reward_id"));
					
				}while(rs.next());
			}
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectAllRewardIdList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return rewardIdList;
	}
	
	
	/** 프로젝트ID로 리워드ID 리스트 얻어오기 (기본리워드 제외) */
	public ArrayList<String> selectRewardIdList(int project_id) {
		ArrayList<String> rewardIdList = null;
		
		String sql = "select reward_id"
				  + " from project_reward_tbl"
				  + " where project_id = ?"
				  + " and reward_id != 'default'";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, project_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				rewardIdList = new ArrayList<>();
				
				do {
					
					rewardIdList.add(rs.getString("reward_id"));
					
				}while(rs.next());
			}
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectRewardIdList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return rewardIdList;
	}
	
	/** 특정 프로젝트의 후원기록 리스트 가져오기 */
	public ArrayList<DonationBean> selectProjectDonationList(int project_id) {
		ArrayList<DonationBean> donationList = null;
		
		String sql = "select donation_id, project_id, member_id, reward_id,"
				  + " r_price, add_donation, address_id, donatedate"
				  + " from donation_tbl"
				  + " where project_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, project_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				donationList = new ArrayList<>();
				
				do {
					
					DonationBean donation = new DonationBean();
					donation.setDonation_id(rs.getInt("donation_id"));
					donation.setProject_id(project_id);
					donation.setMember_id(rs.getString("member_id"));
					donation.setReward_id(rs.getString("reward_id"));
					donation.setR_price(rs.getInt("r_price"));
					donation.setAdd_donation(rs.getInt("add_donation"));
					donation.setTotalDonation(rs.getInt("r_price") + rs.getInt("add_donation"));
					donation.setAddress_id(rs.getString("address_id"));
					donation.setDonatedate(rs.getString("donatedate"));
					
					donationList.add(donation);					
					
				}while(rs.next());
			}
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectProjectDonationList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return donationList;
	}
	
	/** status 입력값 조정으로 관리자가 요구하는 상태로 변경가능 */
	public int updateProjectStatus(int project_id, String status) {
		int updateProjectStatusCount = 0;
		
		String sql = "update project_tbl"
				  + " set status = ?"
				  + " where project_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setInt(2, project_id);
			
			updateProjectStatusCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] updateProjectStatus() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return updateProjectStatusCount;
	}

	/** 프로젝트 종류별(기부/펀딩) 전체 진행중인 프로젝트 개수를 얻어옴  */
	public int selectProjectKindOngoingCount(String kind) {
		int projectKindCount = 0;
		
		String sql = "select count(*) from project_tbl where kind = ? and status = 'ongoing'";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				projectKindCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectProjectKindOngoingCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectKindCount;
	}
	
	/** 검색조건에 따른 진행중인 프로젝트(기부/펀딩) 목록 개수를 얻어옴 */
	public int selectSearchProjectKindOngoingCount(String kind, String title) {
		int projectKindCount = 0;
		
		String sql = "select count(*)"
				  + " from project_tbl"
				  + " where status = 'ongoing'"
				  + " and kind = ? and title regexp ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, title);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				projectKindCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectSearchProjectKindOngoingCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectKindCount;
	}

	
	/** 공개예정인 기부/펀딩 프로젝트 수 얻어오기 */
	public int selectProjectKindReadyCount(String kind) {
		int projectKindCount = 0;
		
		String sql = "select count(*) "
				  + " from project_tbl"
				  + " where status = 'ready' and kind = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				projectKindCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectProjectKindReadyCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectKindCount;
	}
	
	/** 검색어에 따른 공개예정인 기부/펀딩 프로젝트 수 얻어오기 */
	public int selectSearchProjectKindReadyCount(String kind, String title) {
		int projectKindCount = 0;
		
		String sql = "select count(*) "
				+ " from project_tbl"
				+ " where status = 'ready'"
				+ " and kind = ? and title regexp ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, title);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				projectKindCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectSearchProjectKindReadyCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectKindCount;
	}
	
	/** 종료된(성공) 기부/펀딩 프로젝트 수 얻어오기 */
	public int selectProjectKindDoneCount(String kind) {
		int projectKindCount = 0;
		
		String sql = "select count(*) "
				+ " from project_tbl"
				+ " where (status = 'done' or status = 'success')"
				+ " and kind = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				projectKindCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectProjectKindDoneCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectKindCount;
	}
	
	/** 검색어에 따른 (성공)종료된 기부/펀딩 프로젝트 수 얻어오기 */
	public int selectSearchProjectKindDoneCount(String kind, String title) {
		int projectKindCount = 0;
		
		String sql = "select count(*) "
				+ " from project_tbl"
				+ " where (status = 'done' or status = 'success')"
				+ " and kind = ? and title regexp ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, title);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				projectKindCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectSearchProjectKindDoneCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectKindCount;
	}
	
	/** 원하는 페이지의 원하는 개수만큼 진행중인 프로젝트-기획자 리스트를 얻어옴 (최근 런칭 순) */
	public ArrayList<ProjectPlannerBean> selectProjectPlannerOngoingList(String kind, int page, int limit) {
		ArrayList<ProjectPlannerBean> projectPlannerList = null;
		
		int startrow = (page-1)*limit;
		
		String sql = "select project_id, kind, title, summary,"
				  + " thumbnail, content, image,"
				  + " DATE_FORMAT(startdate,'%Y.%m.%d') as startdate_F,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as enddate,"
				  + " goal_amount, curr_amount,"
				  + " status, likes,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as regdate,"
				  + " planner_name"
				  + " from project_planner_view"
				  + " where kind = ? and status='ongoing'"
				  + " order by startdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectPlannerList = new ArrayList<>();
				
				do {
					
					ProjectPlannerBean projectPlanner = new ProjectPlannerBean(rs.getInt("project_id"),
																			   rs.getString("kind"),
																			   rs.getString("title"),
																			   rs.getString("summary"),
																			   rs.getString("thumbnail"),
																			   rs.getString("content"),
																			   rs.getString("image"),
																			   rs.getString("startdate_F"),
																			   rs.getString("enddate"),
																			   rs.getInt("goal_amount"),
																			   rs.getInt("curr_amount"),
																			   rs.getString("status"),
																			   rs.getInt("likes"),
																			   rs.getString("regdate"),
																			   rs.getString("planner_name")
																			   );
					//달성률 세팅 (progress)
					projectPlanner.setProgressFormatWithCurrGoal(rs.getInt("curr_amount"), rs.getInt("goal_amount"));
					
					//남은일수 세팅 (0이면 오늘마감 표시)
					projectPlanner.setDeadline_exc(rs.getString("enddate"));
					
					//현재모금액, 목표모금액 천단위 구분자 세팅
					projectPlanner.setCurr_amount_df_exc(rs.getInt("curr_amount"));
					projectPlanner.setGoal_amount_df_exc(rs.getInt("goal_amount"));
					
					//리스트에 프로젝트-기획자 객체 추가
					projectPlannerList.add(projectPlanner);
					
				}while(rs.next());
				
			}
			
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectProjectPlannerOngoingList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectPlannerList;
	}

	/** (오래된공개순 정렬) 원하는 페이지의 원하는 개수만큼 진행중인 프로젝트-기획자 리스트를 얻어옴 */
	public ArrayList<ProjectPlannerBean> selectOldProjectPlannerOngoingList(String kind, int page, int limit) {
		ArrayList<ProjectPlannerBean> projectPlannerList = null;
		
		int startrow = (page-1)*limit;
		
		String sql = "select project_id, kind, title, summary,"
				  + " thumbnail, content, image,"
				  + " DATE_FORMAT(startdate,'%Y.%m.%d') as startdate_F,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as enddate,"
				  + " goal_amount, curr_amount,"
				  + " status, likes,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as regdate,"
				  + " planner_name"
				  + " from project_planner_view"
				  + " where kind = ? and status='ongoing'"
				  + " order by startdate asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectPlannerList = new ArrayList<>();
				
				do {
					
					ProjectPlannerBean projectPlanner = new ProjectPlannerBean(rs.getInt("project_id"),
																			   rs.getString("kind"),
																			   rs.getString("title"),
																			   rs.getString("summary"),
																			   rs.getString("thumbnail"),
																			   rs.getString("content"),
																			   rs.getString("image"),
																			   rs.getString("startdate_F"),
																			   rs.getString("enddate"),
																			   rs.getInt("goal_amount"),
																			   rs.getInt("curr_amount"),
																			   rs.getString("status"),
																			   rs.getInt("likes"),
																			   rs.getString("regdate"),
																			   rs.getString("planner_name")
																			   );
					//달성률 세팅 (progress)
					projectPlanner.setProgressFormatWithCurrGoal(rs.getInt("curr_amount"), rs.getInt("goal_amount"));
					
					//남은일수 세팅 (0이면 오늘마감 표시)
					projectPlanner.setDeadline_exc(rs.getString("enddate"));
					
					//현재모금액, 목표모금액 천단위 구분자 세팅
					projectPlanner.setCurr_amount_df_exc(rs.getInt("curr_amount"));
					projectPlanner.setGoal_amount_df_exc(rs.getInt("goal_amount"));
					
					//리스트에 프로젝트-기획자 객체 추가
					projectPlannerList.add(projectPlanner);
					
				}while(rs.next());
				
			}
			
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectOldProjectPlannerOngoingList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectPlannerList;
	}
	
	/** (마감임박순 정렬) 원하는 페이지의 원하는 개수만큼 진행중인 프로젝트-기획자 리스트를 얻어옴 */
	public ArrayList<ProjectPlannerBean> selectDeadlineProjectPlannerOngoingList(String kind, int page, int limit) {
		ArrayList<ProjectPlannerBean> projectPlannerList = null;
		
		int startrow = (page-1)*limit;
		
		String sql = "select project_id, kind, title, summary,"
				  + " thumbnail, content, image,"
				  + " DATE_FORMAT(startdate,'%Y.%m.%d') as startdate,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as enddate_F,"
				  + " goal_amount, curr_amount,"
				  + " status, likes,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as regdate,"
				  + " planner_name"
				  + " from project_planner_view"
				  + " where kind = ? and status='ongoing'"
				  + " order by enddate asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectPlannerList = new ArrayList<>();
				
				do {
					
					ProjectPlannerBean projectPlanner = new ProjectPlannerBean(rs.getInt("project_id"),
																				rs.getString("kind"),
																				rs.getString("title"),
																				rs.getString("summary"),
																				rs.getString("thumbnail"),
																				rs.getString("content"),
																				rs.getString("image"),
																				rs.getString("startdate"),
																				rs.getString("enddate_F"),
																				rs.getInt("goal_amount"),
																				rs.getInt("curr_amount"),
																				rs.getString("status"),
																				rs.getInt("likes"),
																				rs.getString("regdate"),
																				rs.getString("planner_name")
																				);
					//달성률 세팅 (progress)
					projectPlanner.setProgressFormatWithCurrGoal(rs.getInt("curr_amount"), rs.getInt("goal_amount"));
					
					//남은일수 세팅 (0이면 오늘마감 표시)
					projectPlanner.setDeadline_exc(rs.getString("enddate_F"));
					
					//현재모금액, 목표모금액 천단위 구분자 세팅
					projectPlanner.setCurr_amount_df_exc(rs.getInt("curr_amount"));
					projectPlanner.setGoal_amount_df_exc(rs.getInt("goal_amount"));
					
					//리스트에 프로젝트-기획자 객체 추가
					projectPlannerList.add(projectPlanner);
					
				}while(rs.next());
				
			}
			
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectDeadlineProjectPlannerOngoingList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectPlannerList;
	}
	
	/** (높은관심순 정렬) 원하는 페이지의 원하는 개수만큼 진행중인 프로젝트-기획자 리스트를 얻어옴 */
	public ArrayList<ProjectPlannerBean> selectPopularProjectPlannerOngoingList(String kind, int page, int limit) {
		ArrayList<ProjectPlannerBean> projectPlannerList = null;
		
		int startrow = (page-1)*limit;
		
		String sql = "select project_id, kind, title, summary,"
				  + " thumbnail, content, image,"
				  + " DATE_FORMAT(startdate,'%Y.%m.%d') as startdate,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as enddate,"
				  + " goal_amount, curr_amount,"
				  + " status, likes,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as regdate,"
				  + " planner_name"
				  + " from project_planner_view"
				  + " where kind = ? and status='ongoing'"
				  + " order by likes desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectPlannerList = new ArrayList<>();
				
				do {
					
					ProjectPlannerBean projectPlanner = new ProjectPlannerBean(rs.getInt("project_id"),
																				rs.getString("kind"),
																				rs.getString("title"),
																				rs.getString("summary"),
																				rs.getString("thumbnail"),
																				rs.getString("content"),
																				rs.getString("image"),
																				rs.getString("startdate"),
																				rs.getString("enddate"),
																				rs.getInt("goal_amount"),
																				rs.getInt("curr_amount"),
																				rs.getString("status"),
																				rs.getInt("likes"),
																				rs.getString("regdate"),
																				rs.getString("planner_name")
																				);
					//달성률 세팅 (progress)
					projectPlanner.setProgressFormatWithCurrGoal(rs.getInt("curr_amount"), rs.getInt("goal_amount"));
					
					//남은일수 세팅 (0이면 오늘마감 표시)
					projectPlanner.setDeadline_exc(rs.getString("enddate"));
					
					//현재모금액, 목표모금액 천단위 구분자 세팅
					projectPlanner.setCurr_amount_df_exc(rs.getInt("curr_amount"));
					projectPlanner.setGoal_amount_df_exc(rs.getInt("goal_amount"));
					
					//리스트에 프로젝트-기획자 객체 추가
					projectPlannerList.add(projectPlanner);
					
				}while(rs.next());
				
			}
			
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectPopularProjectPlannerOngoingList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectPlannerList;
	}
	
	
	
	
	/** (검색어에 따른) 원하는 페이지의 원하는 개수만큼 진행중인 프로젝트-기획자 리스트를 얻어옴 */
	public ArrayList<ProjectPlannerBean> selectSearchProjectPlannerOngoingList(String kind, String title, int page, int limit) {
		ArrayList<ProjectPlannerBean> projectPlannerList = null;
		
		int startrow = (page-1)*limit;
		
		String sql = "select project_id, kind, title, summary,"
				  + " thumbnail, content, image,"
				  + " DATE_FORMAT(startdate,'%Y.%m.%d') as startdate_F,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as enddate,"
				  + " goal_amount, curr_amount,"
				  + " status, likes,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as regdate,"
				  + " planner_name"
				  + " from project_planner_view"
				  + " where status='ongoing'"
				  + " and kind = ? and title regexp ?"
				  + " order by startdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, title);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, limit);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectPlannerList = new ArrayList<>();
				
				do {
					
					ProjectPlannerBean projectPlanner = new ProjectPlannerBean(rs.getInt("project_id"),
																				rs.getString("kind"),
																				rs.getString("title"),
																				rs.getString("summary"),
																				rs.getString("thumbnail"),
																				rs.getString("content"),
																				rs.getString("image"),
																				rs.getString("startdate_F"),
																				rs.getString("enddate"),
																				rs.getInt("goal_amount"),
																				rs.getInt("curr_amount"),
																				rs.getString("status"),
																				rs.getInt("likes"),
																				rs.getString("regdate"),
																				rs.getString("planner_name")
																				);
					//달성률 세팅 (progress)
					projectPlanner.setProgressFormatWithCurrGoal(rs.getInt("curr_amount"), rs.getInt("goal_amount"));
					
					//남은일수 세팅 (0이면 오늘마감 표시)
					projectPlanner.setDeadline_exc(rs.getString("enddate"));
					
					//현재모금액, 목표모금액 천단위 구분자 세팅
					projectPlanner.setCurr_amount_df_exc(rs.getInt("curr_amount"));
					projectPlanner.setGoal_amount_df_exc(rs.getInt("goal_amount"));
					
					//리스트에 프로젝트-기획자 객체 추가
					projectPlannerList.add(projectPlanner);
					
				}while(rs.next());
				
			}
			
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectSearchProjectPlannerOngoingList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectPlannerList;
	}
	
	/** 원하는 페이지의 원하는 개수만큼 공개예정인 프로젝트-기획자 리스트를 얻어옴 (최근 런칭 순) */
	public ArrayList<ProjectPlannerBean> selectProjectPlannerReadyList(String kind, int page, int limit) {
		ArrayList<ProjectPlannerBean> projectPlannerList = null;
		
		int startrow = (page-1)*limit;
		
		String sql = "select project_id, kind, title, summary,"
				  + " thumbnail, content, image,"
				  + " DATE_FORMAT(startdate,'%Y.%m.%d') as startdate_F,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as enddate,"
				  + " goal_amount, curr_amount,"
				  + " status, likes,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as regdate,"
				  + " planner_name"
				  + " from project_planner_view"
				  + " where kind = ? and status='ready'"
				  + " order by startdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectPlannerList = new ArrayList<>();
				
				do {
					
					ProjectPlannerBean projectPlanner = new ProjectPlannerBean(rs.getInt("project_id"),
																				rs.getString("kind"),
																				rs.getString("title"),
																				rs.getString("summary"),
																				rs.getString("thumbnail"),
																				rs.getString("content"),
																				rs.getString("image"),
																				rs.getString("startdate_F"),
																				rs.getString("enddate"),
																				rs.getInt("goal_amount"),
																				rs.getInt("curr_amount"),
																				rs.getString("status"),
																				rs.getInt("likes"),
																				rs.getString("regdate"),
																				rs.getString("planner_name")
																				);
					//달성률 세팅 (progress)
					projectPlanner.setProgressFormatWithCurrGoal(rs.getInt("curr_amount"), rs.getInt("goal_amount"));
					
					//남은일수 세팅 (공개일까지 남은 일수)
					projectPlanner.setDeadline_start_exc(rs.getString("startdate_F"));
					
					//현재모금액, 목표모금액 천단위 구분자 세팅
					projectPlanner.setCurr_amount_df_exc(rs.getInt("curr_amount"));
					projectPlanner.setGoal_amount_df_exc(rs.getInt("goal_amount"));
					
					//리스트에 프로젝트-기획자 객체 추가
					projectPlannerList.add(projectPlanner);
					
				}while(rs.next());
				
			}
			
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectProjectPlannerReadyList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectPlannerList;
	}
	
	/** (오래된공개순 정렬) 원하는 페이지의 원하는 개수만큼 공개예정 프로젝트-기획자 리스트를 얻어옴 */
	public ArrayList<ProjectPlannerBean> selectOldProjectPlannerReadyList(String kind, int page, int limit) {
		ArrayList<ProjectPlannerBean> projectPlannerList = null;
		
		int startrow = (page-1)*limit;
		
		String sql = "select project_id, kind, title, summary,"
				  + " thumbnail, content, image,"
				  + " DATE_FORMAT(startdate,'%Y.%m.%d') as startdate_F,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as enddate,"
				  + " goal_amount, curr_amount,"
				  + " status, likes,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as regdate,"
				  + " planner_name"
				  + " from project_planner_view"
				  + " where kind = ? and status='ready'"
				  + " order by startdate asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectPlannerList = new ArrayList<>();
				
				do {
					
					ProjectPlannerBean projectPlanner = new ProjectPlannerBean(rs.getInt("project_id"),
																				rs.getString("kind"),
																				rs.getString("title"),
																				rs.getString("summary"),
																				rs.getString("thumbnail"),
																				rs.getString("content"),
																				rs.getString("image"),
																				rs.getString("startdate_F"),
																				rs.getString("enddate"),
																				rs.getInt("goal_amount"),
																				rs.getInt("curr_amount"),
																				rs.getString("status"),
																				rs.getInt("likes"),
																				rs.getString("regdate"),
																				rs.getString("planner_name")
																				);
					//달성률 세팅 (progress)
					projectPlanner.setProgressFormatWithCurrGoal(rs.getInt("curr_amount"), rs.getInt("goal_amount"));
					
					//남은일수 세팅 (공개일까지 남은 일수)
					projectPlanner.setDeadline_start_exc(rs.getString("startdate_F"));
					
					//현재모금액, 목표모금액 천단위 구분자 세팅
					projectPlanner.setCurr_amount_df_exc(rs.getInt("curr_amount"));
					projectPlanner.setGoal_amount_df_exc(rs.getInt("goal_amount"));
					
					//리스트에 프로젝트-기획자 객체 추가
					projectPlannerList.add(projectPlanner);
					
				}while(rs.next());
				
			}
			
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectOldProjectPlannerReadyList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectPlannerList;
	}
	
	/** (높은관심순 정렬) 원하는 페이지의 원하는 개수만큼 공개예정 프로젝트-기획자 리스트를 얻어옴 */
	public ArrayList<ProjectPlannerBean> selectPopularProjectPlannerReadyList(String kind, int page, int limit) {
		ArrayList<ProjectPlannerBean> projectPlannerList = null;
		
		int startrow = (page-1)*limit;
		
		String sql = "select project_id, kind, title, summary,"
				  + " thumbnail, content, image,"
				  + " DATE_FORMAT(startdate,'%Y.%m.%d') as startdate_F,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as enddate,"
				  + " goal_amount, curr_amount,"
				  + " status, likes,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as regdate,"
				  + " planner_name"
				  + " from project_planner_view"
				  + " where kind = ? and status='ready'"
				  + " order by likes desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectPlannerList = new ArrayList<>();
				
				do {
					
					ProjectPlannerBean projectPlanner = new ProjectPlannerBean(rs.getInt("project_id"),
																				rs.getString("kind"),
																				rs.getString("title"),
																				rs.getString("summary"),
																				rs.getString("thumbnail"),
																				rs.getString("content"),
																				rs.getString("image"),
																				rs.getString("startdate_F"),
																				rs.getString("enddate"),
																				rs.getInt("goal_amount"),
																				rs.getInt("curr_amount"),
																				rs.getString("status"),
																				rs.getInt("likes"),
																				rs.getString("regdate"),
																				rs.getString("planner_name")
																				);
					//달성률 세팅 (progress)
					projectPlanner.setProgressFormatWithCurrGoal(rs.getInt("curr_amount"), rs.getInt("goal_amount"));
					
					//남은일수 세팅 (공개일까지 남은 일수)
					projectPlanner.setDeadline_start_exc(rs.getString("startdate_F"));
					
					//현재모금액, 목표모금액 천단위 구분자 세팅
					projectPlanner.setCurr_amount_df_exc(rs.getInt("curr_amount"));
					projectPlanner.setGoal_amount_df_exc(rs.getInt("goal_amount"));
					
					//리스트에 프로젝트-기획자 객체 추가
					projectPlannerList.add(projectPlanner);
					
				}while(rs.next());
				
			}
			
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectPopularProjectPlannerReadyList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectPlannerList;
	}
	
	
	/** (검색어에 따른) 원하는 페이지의 원하는 개수만큼 공개예정 프로젝트-기획자 리스트를 얻어옴 */
	public ArrayList<ProjectPlannerBean> selectSearchProjectPlannerReadyList(String kind, String title, int page, int limit) {
		ArrayList<ProjectPlannerBean> projectPlannerList = null;
		
		int startrow = (page-1)*limit;
		
		String sql = "select project_id, kind, title, summary,"
				  + " thumbnail, content, image,"
				  + " DATE_FORMAT(startdate,'%Y.%m.%d') as startdate_F,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as enddate,"
				  + " goal_amount, curr_amount,"
				  + " status, likes,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as regdate,"
				  + " planner_name"
				  + " from project_planner_view"
				  + " where status='ready'"
				  + " and kind = ? and title regexp ?"
				  + " order by startdate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, title);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, limit);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectPlannerList = new ArrayList<>();
				
				do {
					
					ProjectPlannerBean projectPlanner = new ProjectPlannerBean(rs.getInt("project_id"),
																				rs.getString("kind"),
																				rs.getString("title"),
																				rs.getString("summary"),
																				rs.getString("thumbnail"),
																				rs.getString("content"),
																				rs.getString("image"),
																				rs.getString("startdate_F"),
																				rs.getString("enddate"),
																				rs.getInt("goal_amount"),
																				rs.getInt("curr_amount"),
																				rs.getString("status"),
																				rs.getInt("likes"),
																				rs.getString("regdate"),
																				rs.getString("planner_name")
																				);
					//달성률 세팅 (progress)
					projectPlanner.setProgressFormatWithCurrGoal(rs.getInt("curr_amount"), rs.getInt("goal_amount"));
					
					//남은일수 세팅 (공개일까지 남은 일수)
					projectPlanner.setDeadline_start_exc(rs.getString("startdate_F"));
					
					//현재모금액, 목표모금액 천단위 구분자 세팅
					projectPlanner.setCurr_amount_df_exc(rs.getInt("curr_amount"));
					projectPlanner.setGoal_amount_df_exc(rs.getInt("goal_amount"));
					
					//리스트에 프로젝트-기획자 객체 추가
					projectPlannerList.add(projectPlanner);
					
				}while(rs.next());
				
			}
			
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectSearchProjectPlannerReadyList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectPlannerList;
	}
	
	/** 원하는 페이지의 원하는 개수만큼 성공/종료된 프로젝트-기획자 리스트를 얻어옴 (최근 종료 순) */
	public ArrayList<ProjectPlannerBean> selectProjectPlannerDoneList(String kind, int page, int limit) {
		ArrayList<ProjectPlannerBean> projectPlannerList = null;
		
		int startrow = (page-1)*limit;
		
		String sql = "select project_id, kind, title, summary,"
				  + " thumbnail, content, image,"
				  + " DATE_FORMAT(startdate,'%Y.%m.%d') as startdate,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as enddate_F,"
				  + " goal_amount, curr_amount,"
				  + " status, likes,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as regdate,"
				  + " planner_name"
				  + " from project_planner_view"
				  + " where kind = ? and (status='done' or status='success')"
				  + " order by enddate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectPlannerList = new ArrayList<>();
				
				do {
					
					ProjectPlannerBean projectPlanner = new ProjectPlannerBean(rs.getInt("project_id"),
																				rs.getString("kind"),
																				rs.getString("title"),
																				rs.getString("summary"),
																				rs.getString("thumbnail"),
																				rs.getString("content"),
																				rs.getString("image"),
																				rs.getString("startdate"),
																				rs.getString("enddate_F"),
																				rs.getInt("goal_amount"),
																				rs.getInt("curr_amount"),
																				rs.getString("status"),
																				rs.getInt("likes"),
																				rs.getString("regdate"),
																				rs.getString("planner_name")
																				);
					//달성률 세팅 (progress)
					projectPlanner.setProgressFormatWithCurrGoal(rs.getInt("curr_amount"), rs.getInt("goal_amount"));
					
					//남은일수 세팅 (0이면 오늘마감 표시)
					projectPlanner.setDeadline_exc(rs.getString("enddate_F"));
					
					//현재모금액, 목표모금액 천단위 구분자 세팅
					projectPlanner.setCurr_amount_df_exc(rs.getInt("curr_amount"));
					projectPlanner.setGoal_amount_df_exc(rs.getInt("goal_amount"));
					
					//리스트에 프로젝트-기획자 객체 추가
					projectPlannerList.add(projectPlanner);
					
				}while(rs.next());
				
			}
			
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectProjectPlannerDoneList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectPlannerList;
	}
	
	/** (오래된마감순 정렬) 원하는 페이지의 원하는 개수만큼 성공/종료된 프로젝트-기획자 리스트를 얻어옴 */
	public ArrayList<ProjectPlannerBean> selectOldProjectPlannerDoneList(String kind, int page, int limit) {
		ArrayList<ProjectPlannerBean> projectPlannerList = null;
		
		int startrow = (page-1)*limit;
		
		String sql = "select project_id, kind, title, summary,"
				  + " thumbnail, content, image,"
				  + " DATE_FORMAT(startdate,'%Y.%m.%d') as startdate_F,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as enddate_F,"
				  + " goal_amount, curr_amount,"
				  + " status, likes,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as regdate,"
				  + " planner_name"
				  + " from project_planner_view"
				  + " where kind = ? and (status='done' or status='success')"
				  + " order by enddate asc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectPlannerList = new ArrayList<>();
				
				do {
					
					ProjectPlannerBean projectPlanner = new ProjectPlannerBean(rs.getInt("project_id"),
																				rs.getString("kind"),
																				rs.getString("title"),
																				rs.getString("summary"),
																				rs.getString("thumbnail"),
																				rs.getString("content"),
																				rs.getString("image"),
																				rs.getString("startdate_F"),
																				rs.getString("enddate_F"),
																				rs.getInt("goal_amount"),
																				rs.getInt("curr_amount"),
																				rs.getString("status"),
																				rs.getInt("likes"),
																				rs.getString("regdate"),
																				rs.getString("planner_name")
																				);
					//달성률 세팅 (progress)
					projectPlanner.setProgressFormatWithCurrGoal(rs.getInt("curr_amount"), rs.getInt("goal_amount"));
					
					//남은일수 세팅 (0이면 오늘마감 표시)
					projectPlanner.setDeadline_exc(rs.getString("enddate_F"));
					
					//현재모금액, 목표모금액 천단위 구분자 세팅
					projectPlanner.setCurr_amount_df_exc(rs.getInt("curr_amount"));
					projectPlanner.setGoal_amount_df_exc(rs.getInt("goal_amount"));
					
					//리스트에 프로젝트-기획자 객체 추가
					projectPlannerList.add(projectPlanner);
					
				}while(rs.next());
				
			}
			
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectOldProjectPlannerDoneList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectPlannerList;
	}
	
	/** (높은관심순 정렬) 원하는 페이지의 원하는 개수만큼 성공/종료된 프로젝트-기획자 리스트를 얻어옴 */
	public ArrayList<ProjectPlannerBean> selectPopularProjectPlannerDoneList(String kind, int page, int limit) {
		ArrayList<ProjectPlannerBean> projectPlannerList = null;
		
		int startrow = (page-1)*limit;
		
		String sql = "select project_id, kind, title, summary,"
				  + " thumbnail, content, image,"
				  + " DATE_FORMAT(startdate,'%Y.%m.%d') as startdate_F,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as enddate,"
				  + " goal_amount, curr_amount,"
				  + " status, likes,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as regdate,"
				  + " planner_name"
				  + " from project_planner_view"
				  + " where kind = ? and (status='done' or status='success')"
				  + " order by likes desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, limit);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectPlannerList = new ArrayList<>();
				
				do {
					
					ProjectPlannerBean projectPlanner = new ProjectPlannerBean(rs.getInt("project_id"),
																				rs.getString("kind"),
																				rs.getString("title"),
																				rs.getString("summary"),
																				rs.getString("thumbnail"),
																				rs.getString("content"),
																				rs.getString("image"),
																				rs.getString("startdate_F"),
																				rs.getString("enddate"),
																				rs.getInt("goal_amount"),
																				rs.getInt("curr_amount"),
																				rs.getString("status"),
																				rs.getInt("likes"),
																				rs.getString("regdate"),
																				rs.getString("planner_name")
																				);
					//달성률 세팅 (progress)
					projectPlanner.setProgressFormatWithCurrGoal(rs.getInt("curr_amount"), rs.getInt("goal_amount"));
					
					//남은일수 세팅 (0이면 오늘마감 표시)
					projectPlanner.setDeadline_exc(rs.getString("enddate"));
					
					//현재모금액, 목표모금액 천단위 구분자 세팅
					projectPlanner.setCurr_amount_df_exc(rs.getInt("curr_amount"));
					projectPlanner.setGoal_amount_df_exc(rs.getInt("goal_amount"));
					
					//리스트에 프로젝트-기획자 객체 추가
					projectPlannerList.add(projectPlanner);
					
				}while(rs.next());
				
			}
			
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectPopularProjectPlannerDoneList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectPlannerList;
	}
	
	
	/** (검색어에 따른) 원하는 페이지의 원하는 개수만큼 성공/종료된 프로젝트-기획자 리스트를 얻어옴 */
	public ArrayList<ProjectPlannerBean> selectSearchProjectPlannerDoneList(String kind, String title, int page, int limit) {
		ArrayList<ProjectPlannerBean> projectPlannerList = null;
		
		int startrow = (page-1)*limit;
		
		String sql = "select project_id, kind, title, summary,"
				  + " thumbnail, content, image,"
				  + " DATE_FORMAT(startdate,'%Y.%m.%d') as startdate,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as enddate_F,"
				  + " goal_amount, curr_amount,"
				  + " status, likes,"
				  + " DATE_FORMAT(enddate,'%Y.%m.%d') as regdate,"
				  + " planner_name"
				  + " from project_planner_view"
				  + " where (status='done' or status='success')"
				  + " and kind = ? and title regexp ?"
				  + " order by enddate desc"
				  + " limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, title);
			pstmt.setInt(3, startrow);
			pstmt.setInt(4, limit);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				projectPlannerList = new ArrayList<>();
				
				do {
					
					ProjectPlannerBean projectPlanner = new ProjectPlannerBean(rs.getInt("project_id"),
																				rs.getString("kind"),
																				rs.getString("title"),
																				rs.getString("summary"),
																				rs.getString("thumbnail"),
																				rs.getString("content"),
																				rs.getString("image"),
																				rs.getString("startdate"),
																				rs.getString("enddate_F"),
																				rs.getInt("goal_amount"),
																				rs.getInt("curr_amount"),
																				rs.getString("status"),
																				rs.getInt("likes"),
																				rs.getString("regdate"),
																				rs.getString("planner_name")
																				);
					//달성률 세팅 (progress)
					projectPlanner.setProgressFormatWithCurrGoal(rs.getInt("curr_amount"), rs.getInt("goal_amount"));
					
					//남은일수 세팅 (0이면 오늘마감 표시)
					projectPlanner.setDeadline_exc(rs.getString("enddate_F"));
					
					//현재모금액, 목표모금액 천단위 구분자 세팅
					projectPlanner.setCurr_amount_df_exc(rs.getInt("curr_amount"));
					projectPlanner.setGoal_amount_df_exc(rs.getInt("goal_amount"));
					
					//리스트에 프로젝트-기획자 객체 추가
					projectPlannerList.add(projectPlanner);
					
				}while(rs.next());
				
			}
			
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] selectSearchProjectPlannerDoneList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectPlannerList;
	}

	/** 회원의 계좌 잔액을 업데이트 (빼기) */
	public int updateUserMinusMoney(String member_id, int money) {
		
		int updateUserMoneyCount = 0;
		
		String sql = "update member_tbl"
				+ " set money = money - ?"
				+ " where member_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, money);
			pstmt.setString(2, member_id);
			
			updateUserMoneyCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[UserDAO] updateUserMinusMoney() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return updateUserMoneyCount;
	}
	
	/** 후원된 금액만큼 프로젝트 현재모금액 추가 */
	public int updateProjectCurrAmount(int project_id, int d_money) {
		int updateProjectCurrAmountCount = 0;
		
		String sql = "update project_tbl"
				  + " set curr_amount = curr_amount + ?"
				  + " where project_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, d_money);
			pstmt.setInt(2, project_id);
			
			updateProjectCurrAmountCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] updateProjectCurrAmount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		
		return updateProjectCurrAmountCount;
	}

	
	/** 후원기록 테이블에 후원기록 insert (주소제외) */
	public int insertDonationNotAddr(DonationBean donation) {
		int insertDonationCount = 0;
		
		String sql = "insert into donation_tbl(project_id, member_id, reward_id, r_price, add_donation) values(?,?,?,?,?)";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, donation.getProject_id());
			pstmt.setString(2, donation.getMember_id());
			pstmt.setString(3, donation.getReward_id());
			pstmt.setInt(4, donation.getR_price());
			pstmt.setInt(5, donation.getAdd_donation());
			//후원기록ID 자동1씩 증가
			//주소ID null 허용
			//donatedate 현재서버시간으로 자동세팅
			
			insertDonationCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] insertDonationNotAddr() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		
		return insertDonationCount;
	}
	
	
	/** 후원기록 테이블에 후원기록 insert (주소 포함) */
	public int insertDonationAddr(DonationBean donation) {
		int insertDonationCount = 0;
		
		String sql = "insert into donation_tbl(project_id, member_id, reward_id, r_price, add_donation, address_id) values(?,?,?,?,?,?)";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, donation.getProject_id());
			pstmt.setString(2, donation.getMember_id());
			pstmt.setString(3, donation.getReward_id());
			pstmt.setInt(4, donation.getR_price());
			pstmt.setInt(5, donation.getAdd_donation());
			pstmt.setString(6, donation.getAddress_id());
			//후원기록ID 자동1씩 증가
			//donatedate 현재서버시간으로 자동세팅
			
			insertDonationCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] insertDonationAddr() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		
		return insertDonationCount;
	}

	
}