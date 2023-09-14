package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


import util.SHA256;
import vo.AddressBean;
import vo.DonationBean;
import vo.MemberBean;
import vo.MemberPwChangeBean;
import vo.PlannerBean;
import vo.ProjectBean;

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

	//1. 프로젝트 ID로 프로젝트 정보를 얻어옴
	public ProjectBean selectProject(int p_id) {
		ProjectBean projectInfo = null;
		
		String sql = "select * from project_tbl where project_id=?";
		   	
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, p_id);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				projectInfo = new ProjectBean(
						rs.getInt("project_id"),
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
			}
			
			
		} catch(Exception e) {
			System.out.println("[ProjectDAO] getProjectInfo() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return projectInfo;
	}
	public int insertProject(ProjectBean pj) {
		int insertProjectCount = 0;
		String sql ="insert into project_tbl(kind,title,summary,thumbnail,content,image,startdate,enddate,goal_amount,curr_amount,status,likes)";
		sql+=" values(?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pj.getKind());
			pstmt.setString(2, pj.getTitle());
			pstmt.setString(3, pj.getSummary());
			pstmt.setString(4, pj.getThumbnail());
			pstmt.setString(5, pj.getContent());
			pstmt.setString(6, pj.getImage());
			pstmt.setString(7, pj.getStartdate());
			pstmt.setString(8, pj.getEnddate());
			pstmt.setInt(9, pj.getGoal_amount());
			pstmt.setInt(10, 0);
			pstmt.setString(11, "temp");
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

	public int getProjectID(ProjectBean pj) {
		int project_id=0;;
		String sql = "select project_id from project_tbl where thumbnail=?";
		   	
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pj.getThumbnail());
			rs=pstmt.executeQuery();
			if (rs.next()) {
	            project_id = rs.getInt(1);
	        }
			System.out.println(project_id);
		} catch(Exception e) {
			System.out.println("[ProjectDAO] getProjectID() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return project_id;
	}

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
			pstmt.setString(6, planner.getAccount());

			
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
	//특정 회원의 후원기록을 가져오기
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
					donation.setReward_id(rs.getInt("reward_id"));
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

		public ProjectBean donatePageView(int project_id) {
			// TODO 자동 생성된 메소드 스텁
			return null;
		}

		public PlannerBean selectPlanner(int project_id, String member_id) {
			PlannerBean planner = null;
			
			String sql = "select * from project_planner_tbl where project_id=? and member_id=?";
			try {
				
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, project_id);
				pstmt.setString(2, member_id);
				rs = pstmt.executeQuery();			
				if(rs.next()) {
					planner = new PlannerBean(
							rs.getInt("project_id"),
							rs.getString("member_id"),
							  rs.getString("planner_name"),
							  rs.getString("introduce"),
							  rs.getString("bank"),
							  rs.getString("account")
							  );
				}
				
				
			} catch(Exception e) {
				System.out.println("[ProjectDAO] selectPlanner() 에러 : "+e);//예외객체종류 + 예외메시지
			} finally {
				close(pstmt); //JdbcUtil.생략가능
				close(rs); //JdbcUtil.생략가능
				//connection 객체에 대한 해제는 DogListService에서 이루어짐
			}
			
			return planner;
		}

		public int projectStatusUpdateService(int project_id, String string) {
			String sql="UPDATE project_tbl SET status = ? WHERE project_id = ?;";
			int updateStatusCount=0;
			try {
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, string);
				pstmt.setInt(2, project_id);


				
				updateStatusCount = pstmt.executeUpdate();
				
			} catch(Exception e) {
				System.out.println("[ProjectDAO] projectStatusUpdateService() 에러 : "+e);//예외객체종류 + 예외메시지
			} finally {
				close(pstmt); //JdbcUtil.생략가능
				//close(rs); //JdbcUtil.생략가능
				//connection 객체에 대한 해제는 DogListService에서 이루어짐
			}
			
			return updateStatusCount;
		}
	}