package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.DonationBean;
import vo.NoticeBean;
import vo.RewardBean;

public class RewardDAO {
	
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
	private RewardDAO() {}
	
	
	
	private static RewardDAO rewardDAO; //static메서드인 getInstance에서 쓸 수 있게 static (단 외부에서 직접 접근 불가능하도록 private)
	
	public static RewardDAO getInstance() {
		if(rewardDAO == null) {//DogDAO객체가 없으면
			rewardDAO = new RewardDAO();//객체 생성
		}
		
		return rewardDAO;//기존 DogDAO객체의 주소 리턴
	}
	
	/******************************************************************/
	
	/**
	 * 1. "DB연결(Service)" -> 2. SQL 실행 -> 3. 결과처리 -> 4. DB연결해제(Service)
	 */
	//DB연결 : 매개값으로 받은 Connection객체의 주소값을 필드con에 셋팅
	public void setConnection(Connection con){
		this.con = con;
	}
	
	/** 리워드 테이블에 리워드 insert */
	public int insertReward(RewardBean reward) {
		int insertRewardCount = 0;
		
		String sql = "insert into reward_tbl(reward_id, r_name, r_content, r_price) values(?,?,?,?)";
		   	
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, reward.getReward_id());
			pstmt.setString(2, reward.getR_name());
			pstmt.setString(3, reward.getR_content());
			pstmt.setInt(4, reward.getR_price());
			
			insertRewardCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[RewardDAO] insertReward() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return insertRewardCount;
	}

	/** 리워드ID로 특정 리워드정보를 얻어냄 */
	public RewardBean selectReward(String reward_id) {
		RewardBean rewardInfo = null;
		
		String sql = "select * from reward_tbl where reward_id=?";
		   	
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, reward_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				rewardInfo = new RewardBean(
											rs.getString("reward_id"),
											rs.getString("r_name"),
											rs.getString("r_content"),
											rs.getInt("r_price")
											);
			}
			
			
		} catch(Exception e) {
			System.out.println("[RewardDAO] selectReward() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return rewardInfo;
	}

	/** 프로젝트ID와 리워드ID로 프로젝트-리워드 매핑 */
	public int insertProjectReward(int project_id, String reward_id) {
		int insertProjectRewardCount = 0;
		
		String sql = "insert into project_reward_tbl values(?,?)";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, project_id);
			pstmt.setString(2, reward_id);
			
			insertProjectRewardCount = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("[RewardDAO] insertProjectReward() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			//close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return insertProjectRewardCount;
	}
	//리워드 아이디로 도네이션 정보 얻어오기
	public ArrayList<DonationBean> selectDonation(int project_id) {
		DonationBean donationInfo = null;
		ArrayList<DonationBean> donationList=new ArrayList<>();
		String sql = "select donate_id,project_id,member_id,reward_id,r_price,add_donation,address_id,DATE_FORMAT(donatedate, %Y-%m-%d) as donatedate "
				+ " from donation_tbl where project_id=?";
		   	
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, project_id);
			
			rs = pstmt.executeQuery();
			//토탈프라이스를 바로 계산해서 넘길 수 있도록 객체 생성함
			if(rs.next()) {
				do{
					donationInfo = new DonationBean(
											rs.getInt("donate_id"),
											rs.getInt("project_id"),
											rs.getString("member_id"),
											rs.getString("reward_id"),
											rs.getInt("r_price"),
											rs.getInt("add_donation"),
											rs.getInt("r_price")+rs.getInt("add_donation"),
											rs.getString("address_id"),
											rs.getString("donatedate")
											);
					donationList.add(donationInfo);
					}while(rs.next());
			}
			
			
		} catch(Exception e) {
			System.out.println("[RewardDAO] selectDonation(project_id) 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return donationList;
	}
	
	public ArrayList<DonationBean> selectDonation(String reward_id) {
		DonationBean donationInfo = null;
		ArrayList<DonationBean> donationList=new ArrayList<>();
		String sql = "select donate_id,project_id,member_id,reward_id,r_price,add_donation,address_id,DATE_FORMAT(donatedate, %Y-%m-%d) as donatedate "
				+ " from donation_tbl where reward_id=?";
		   	
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, reward_id);
			
			rs = pstmt.executeQuery();
			//토탈프라이스를 바로 계산해서 넘길 수 있도록 객체 생성함
			if(rs.next()) {
				do{
					donationInfo = new DonationBean(
											rs.getInt("donate_id"),
											rs.getInt("project_id"),
											rs.getString("member_id"),
											rs.getString("reward_id"),
											rs.getInt("r_price"),
											rs.getInt("add_donation"),
											rs.getInt("r_price")+rs.getInt("add_donation"),
											rs.getString("address_id"),
											rs.getString("donatedate")
											);
					donationList.add(donationInfo);
					}while(rs.next());
			}
			
			
		} catch(Exception e) {
			System.out.println("[RewardDAO] selectDonation(reward_id) 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return donationList;
	}
	
	public int getDonationCount(String reward_id) {
		int donationCount = 0;
		
		String sql = "select count(*) from donation_tbl where reward_id = ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, reward_id);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				donationCount = rs.getInt(1);
			}
			
		} catch(Exception e) {
			System.out.println("[RewardDAO] getDonationCount() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return donationCount;
	}

	public ArrayList<DonationBean> getDonation_addrList(ArrayList<DonationBean> donationList) {
		
		
		String sql = "select receiver_name, receiver_phone, postcode, address1, address2 from address_tbl where address_id=? and member_id=?";   	
		try {
			for(int i=0;i<donationList.size();i++) {//주소정보를 바로 DonationBean 객체에 저장
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, donationList.get(i).getAddress_id());
				pstmt.setString(2, donationList.get(i).getMember_id());
				
				rs = pstmt.executeQuery();
				//바로 donation객체에 주소정보 추가함
				if(rs.next()) {
						donationList.get(i).setReceiver_name(rs.getString("receiver_name"));
						donationList.get(i).setReceiver_phone(rs.getString("receiver_phone"));
						donationList.get(i).setPostcode(rs.getInt("postcode"));
						donationList.get(i).setAddress1(rs.getString("address1"));
						donationList.get(i).setAddress2(rs.getString("address2"));
				}
			}
			
			
		} catch(Exception e) {
			System.out.println("[RewardDAO] getDonation_addrList() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return donationList;
	}

	public ArrayList<DonationBean> getDonationList_page(String reward_id, int page, int limit) {
		ArrayList<DonationBean> donationList = null;
		DonationBean donationInfo = null;
		
		int startrow = (page-1)*10;
		//1페이지 조회 -> 후원목록의 제일 윗 건은 sql에서 row index 0부터
		//2페이지 조회 -> 후원목록의 제일 윗 건은 sql에서 row index 10부터
		//3페이지 조회 -> 후원목록의 제일 윗 건은 sql에서 row index 20부터
		String sql = "select donate_id,project_id,member_id,reward_id,r_price,add_donation,address_id,DATE_FORMAT(donatedate, %Y-%m-%d) as donatedate "
				+ " from donation_tbl where reward_id=? order by donate_id desc limit ?, ?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, reward_id);
			pstmt.setInt(2, startrow);//startrow번째행부터 limit개만 가져옴
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				donationList = new ArrayList<>();
				do {
					donationInfo = new DonationBean(
							rs.getInt("donate_id"),
							rs.getInt("project_id"),
							rs.getString("member_id"),
							rs.getString("reward_id"),
							rs.getInt("r_price"),
							rs.getInt("add_donation"),
							rs.getInt("r_price")+rs.getInt("add_donation"),
							rs.getString("address_id"),
							rs.getString("donatedate")
							);
							donationList.add(donationInfo);
					
				}while(rs.next());
			}
			
		} catch(Exception e) {
			System.out.println("[RewardDAO] getDonationList_page() 에러 : "+e);//예외객체종류 + 예외메시지
		} finally {
			close(pstmt); //JdbcUtil.생략가능
			close(rs); //JdbcUtil.생략가능
			//connection 객체에 대한 해제는 DogListService에서 이루어짐
		}
		
		return donationList;
	}

	

	
}