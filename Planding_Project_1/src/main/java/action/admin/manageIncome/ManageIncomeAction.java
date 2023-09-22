package action.admin.manageIncome;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.admin.manageIncome.ManageIncomeService;
import util.DateUtil;
import vo.ActionForward;
import vo.AdminIncomeBean;

public class ManageIncomeAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		/* ******************************************************************************
		 * 
		 * 1. 매출 달력 만들기
		 * 
		 ********************************************************************************/
		
		//현재 2023.9 -> 뷰페이지에서 이전해 클릭 -> 2022.9 -> month값으로 search_month-1을 넘김(=8) 
		String year = request.getParameter("year");//2022년
		String month = request.getParameter("month");//8일때 (실제 9월)
		
		System.out.println("[ManageIncomeAction] 파라미터값 확인");
		System.out.println("year = "+year);
		System.out.println("month = "+month);
		
		DateUtil dateUtil = new DateUtil();
		
		if(year == null && month == null) {//클릭하여 검색하지 않는 경우 (연도, 달 파라미터값 없음. 현재달 세팅. 매출관리 초기화면)
			
			Calendar today = Calendar.getInstance();
			
			//오늘날짜로 세팅한 DateUtil객체 생성
			dateUtil = new DateUtil();
			dateUtil.setYear(String.valueOf(today.get(Calendar.YEAR)));
			dateUtil.setMonth(String.valueOf(today.get(Calendar.MONTH)));
			dateUtil.setDate(String.valueOf(today.get(Calendar.DATE)));
			
			System.out.println("[ManageIncomeAction] 클릭없이 오늘날짜로 세팅");
			System.out.println("오늘날짜 연도 = "+dateUtil.getYear());
			System.out.println("오늘날짜 월-1 = "+dateUtil.getMonth());
			System.out.println("오늘날짜 일 = "+dateUtil.getDate());
			
		}else {//클릭하여 검색하는 경우 (연도와 달 값이 파라미터값으로 넘어옴)
			
			dateUtil = new DateUtil();
			dateUtil.setYear(year);//검색연도 (2022)
			dateUtil.setMonth(month);//검색월 (8)(실제9)
			dateUtil.setDate("1");//일은 1일로 세팅 (오늘이 31일일때, 이전달 클릭 시 30일까지 밖에 없으면 31일이 세팅될 수 없음)
			
			System.out.println("[ManageIncomeAction] 클릭한 날짜로 세팅");
			System.out.println("검색날짜 연도 = "+year);//2022
			System.out.println("검색날짜 월 = "+month);//8(실제9)
			System.out.println("검색날짜 일 = 1");
		}
		
		//뷰페이지에 세팅할 날짜의 정보
		Map<String, Object> today_info = dateUtil.today_info(dateUtil);//검색날짜로 뷰페이지에 세팅할 선택날짜 세팅
		request.setAttribute("today_info", today_info);
		
		//SQL문에서 쓸 첫날, 마지막날
		String db_startDate = String.valueOf(today_info.get("db_startDate"));
		String db_endDate = String.valueOf(today_info.get("db_endDate"));
		
		//실제 매출 정보를 얻어옴
		ManageIncomeService manageIncomeService = new ManageIncomeService();
		ArrayList<AdminIncomeBean> incomeList = manageIncomeService.getDateRangeList(db_startDate, db_endDate);
		
		
		/*-- 얻어온 매출정보를 달력의 각 칸에 삽입 ------------------------------------*/
		
		//하루에 얼마만큼의 수익이 발생할지 모르기 때문에 2차원배열이 아니라, ArrayList를 관리하는 배열 생성
		ArrayList<AdminIncomeBean>[] adminIncomeArr = new ArrayList[32];
		
		//얻어온 데이터를 배열에 저장
		if(!incomeList.isEmpty()) {//만약 매출데이터가 있으면
			
			for(int i=0; i<incomeList.size(); i++) {
				
				//수익기록 중 날짜만 가져옴 (예 : incomeList.get(i)의 수익날짜가 2023-9-13 일 때 13만)
				String incomeDate = incomeList.get(i).getIncomedate();
				//날짜(연월일) 중 일만 가져와서 숫자로 변환 
				int date = Integer.parseInt(incomeDate.substring(incomeDate.length()-2));
				
				
				if(i>0) {//총 매출데이터가 1개가 넘으면
					
					//i번째와 i-1번째 기록의 날짜를 비교
					String beforeIncomeDate = incomeList.get(i-1).getIncomedate();
					int beforeDate = Integer.parseInt(beforeIncomeDate.substring(beforeIncomeDate.length()-2));
					
					//만약 현재기록이 이전수익기록과 같은날 발생한 수익이면
					if(date == beforeDate) {
						//이미 해당 칸의 ArrayList객체가 생성되어 있는 상태이므로 바로 add
						adminIncomeArr[date].add(incomeList.get(i));
					}else {//다른날 발생한 수익이라면
						
						//date번째 배열에 넣을 ArrayList객체를 생성한 다음
						adminIncomeArr[date] = new ArrayList<AdminIncomeBean>();
						//현재 수익기록을 list에 저장
						adminIncomeArr[date].add(incomeList.get(i));
					}
					
				}else {//총 매출데이터가 1개만 있다면
					//ArrayList객체 생성 후 add
					adminIncomeArr[date] = new ArrayList<AdminIncomeBean>();
					adminIncomeArr[date].add(incomeList.get(i));
				}
				
				System.out.println("얻어온 수익기록데이터의 날짜 : "+date);
				
				//해당 날짜의 배열 칸에 수익기록을 저장 (기존 소스에서 : 13일이면 index13에 저장)
				//adminIncomeArr[date] = incomeList.get(i);
			}
		}
		
		
		
		//실질적인 달력데이터 리스트에 데이터 삽입 시작
		List<DateUtil> dateList = new ArrayList<>();
		DateUtil calendarData = null;
		//start=이번달 첫째날 요일, startDay = 이번달 첫째날, endDay = 이번달 마지막날
		int start = Integer.parseInt(String.valueOf(today_info.get("start")));
		int startDay = Integer.parseInt(String.valueOf(today_info.get("startDay")));
		int endDay = Integer.parseInt(String.valueOf(today_info.get("endDay")));
		
		//start : 이번달 첫째날 요일(일요일1~토요일7)
		//첫째날 요일 전까지 빈데이터의 DateUtil값으로 list 자리를 채움
		for(int i=1; i<start; i++) {
			calendarData = new DateUtil(null, null, null, null, null);
			dateList.add(calendarData);
			
			//dateList의 값 calendarData의 모든 필드가 null값으로 되어있음.
		}
		
		//이번달 첫째날부터 마지막날까지
		for(int i=startDay; i<=endDay; i++) {
			ArrayList<AdminIncomeBean> dayIncomeList = new ArrayList<>();
			dayIncomeList = adminIncomeArr[i];
			//13일이면 -> dayIncomeList는 13일 수익기록목록이 대입됨 
			//만약 13일만 데이터가 있으면, dayIncomeList는 adminIncomeArr[13]일 때를 제외하고 모두 null이 대입됨
			
			System.out.println("[ManageIncomeAction] "+i+"일의 수익리스트 값 확인 : "+dayIncomeList);
			
			//만약 특정날짜가 오늘에 해당할 경우, 생성할 DateUtil객체의 value에 "today"라는 값을 넣어줌
			//"today"는 지정날짜가 올해, 이번달일 경우, 오늘날짜의 '일'이 대입되어 있음
			if(i == Integer.parseInt(String.valueOf(today_info.get("today")))) {
				calendarData = new DateUtil(dateUtil.getYear(), dateUtil.getMonth(), String.valueOf(i), "today", dayIncomeList);
				
			}else{//오늘이 아니면 value에 "normal_date"
				calendarData = new DateUtil(dateUtil.getYear(), dateUtil.getMonth(), String.valueOf(i), "normal_date", dayIncomeList);
			}
			
			//수익데이터가 담긴 날짜객체를 리스트에 추가
			dateList.add(calendarData);
		}
		
		//달력의 빈 곳은 빈데이터로 삽입
		int index = 7 - dateList.size()%7; 
		//dateList의 사이즈는 해당 달의 총 일수와 같음
		//총 일수%7 = 31일이라면, 31%7 = 3 -> 한 주에서 7-3=4만큼 비어있음
		
		if(dateList.size()%7 != 0) {
			for(int i=0; i<index; i++) {
				calendarData = new DateUtil(null,null,null,null,null);
				dateList.add(calendarData);
				
				//dateList의 값 calendarData의 모든 필드가 null값으로 되어있음.
			}
		}
		
		//수익데이터가 담긴 날짜객체로 이루어진 날짜리스트를 request에 저장
		request.setAttribute("dateList", dateList);
		
		
		/* ******************************************************************************
		 * 
		 * 2. 매출 리스트 만들기
		 * 
		 ********************************************************************************/
		//최근순으로 정렬된 모든 수익리스트를 얻어옴
		ArrayList<AdminIncomeBean> allIncomeList = manageIncomeService.getAllIncomeList();
		System.out.println("[ManageIncomeAction]");
		System.out.println("allIncomeList = "+allIncomeList);
		
		//request에 저장
		request.setAttribute("allIncomeList", allIncomeList);
		
		
		
		request.setAttribute("showAdmin", "admin/manageIncome/manageIncome.jsp");
		forward = new ActionForward("adminTemplate.jsp", false);
		
		return forward;
	}

}
