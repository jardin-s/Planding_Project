package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {//날짜 계산하는 클래스 (매출관리에서 달력생성을 위함)
	
	//1. 필드
	private String year = "";
	private String month = "";
	private String date = "";
	private String value = "";
	
	//DB의 sql문에서 사용
	private String db_startDate = "";
	private String db_endDate = "";
	
	//스케줄링에 사용
	private String schedule = "";
	private String schedule_datail = "";
	
	//2. 생성자
	//2-1. 스케줄 사용
	public DateUtil(String year, String month, String date, String value, String schedule, String schedule_datail) {
		super();
		this.year = year;
		this.month = month;
		this.date = date;
		this.value = value;
		this.schedule = schedule;
		this.schedule_datail = schedule_datail;
	}
	
	//2-2. 달력만 사용
	public DateUtil(String year, String month, String date, String value) {
		if((month != null && month !="") && (date != null && date != "")) {
			this.year = year;
			this.month = month;
			this.date = date;
			this.value = value;
		}
	}
	
	//2-3. 기본생성자
	public DateUtil() {}
	
	
	
	//3. 메서드
	
	//3-1. getter & setter
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	public String getDb_startDate() {
		return db_startDate;
	}

	public void setDb_startDate(String db_startDate) {
		this.db_startDate = db_startDate;
	}

	public String getDb_endDate() {
		return db_endDate;
	}

	public void setDb_endDate(String db_endDate) {
		this.db_endDate = db_endDate;
	}

	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getSchedule_datail() {
		return schedule_datail;
	}
	public void setSchedule_datail(String schedule_datail) {
		this.schedule_datail = schedule_datail;
	}
	
	//3-2. 날짜에 관련된 달력정보를 가지는 메서드 (달력네비게이션에서 이전달/다음달 넘기는 <>에 사용 )-------------------------------------------
	public Map<String, Object> today_info(DateUtil dateUtil){
		
		//int값 외에도 db_startdate, db_enddate는 String이므로 모두 value에 담기 위해 Object타입으로 설정
		Map<String, Object> today_data = new HashMap<String, Object>();//HashMap : 순서 상관없이 쌍으로 관리
		
		//Calendar 객체를 얻어옴
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(dateUtil.getYear()), Integer.parseInt(dateUtil.getMonth()), Integer.parseInt(dateUtil.getDate()));
		//매개변수의 year, month, day로 날짜 세팅
		
		//DB에서 사용
		int startDay = cal.getMinimum(java.util.Calendar.DATE);//이번달 날짜 중 첫째날(minimum)
		int endDay = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);//이번달의 마지막날(maximum)
		int start = cal.get(java.util.Calendar.DAY_OF_WEEK);//현재요일 (일요일 1 ~ 토요일 7)
		
		//오늘 날짜에 해당하는 연도와 월 등의 정보를 얻기위해 호출
		Calendar todayCal = Calendar.getInstance();
		SimpleDateFormat ysdf = new SimpleDateFormat("yyyy");//2023년 -> 2023
		SimpleDateFormat msdf = new SimpleDateFormat("M");//9월 -> 9
		
		int today_year = Integer.parseInt(ysdf.format(todayCal.getTime()));//오늘날짜 중 연도 yyyy형태로 가져와 숫자로 변환
		int today_month = Integer.parseInt(msdf.format(todayCal.getTime()));//오늘날짜 중 월 M형태로 가져와 숫자로 변환
		
		int search_year = Integer.parseInt(ysdf.format(dateUtil.getYear()));//매개변수(검색날짜) 중 연도 yyyy 숫자변환
		int search_month = Integer.parseInt(msdf.format(dateUtil.getMonth()));//매개변수(검색날짜) 중 월 M 숫자변환
		
		int today = -1;
		if(today_year == search_year && today_month == search_month) {//만약 검색날짜가 올해, 이번달이라면
			SimpleDateFormat dsdf = new SimpleDateFormat("dd");//일수 dd형태(01~31)
			today = Integer.parseInt(dsdf.format(todayCal.getTime()));//today는 오늘 날짜의 day로 변경
		}
		
		//시스템적으로 월은 0~11이므로 -1하여 세팅 (예: 검색날짜가 5월이면, 시스템적 월은 5-1=4)
		search_month = search_month -1;
		
		//검색날짜의 이전년도/이전달 & 다음년도/다음달 정보가 담긴 Map객체
		Map<String, Integer> before_after_calendar = before_after_calendar(search_year, search_month);
		
		//검색날짜의 달
		System.out.println("search_momth : "+search_month);
		
		today_data.put("start", start);//현재요일
		today_data.put("startDay", startDay);//이번달 첫째날
		today_data.put("endDay", endDay);//이번달 마지막날
		today_data.put("today", today);//검색날짜가 이번달이면 오늘날짜, 아니면 -1
		today_data.put("search_year", search_year);//검색날짜의 연도
		today_data.put("search_month", search_month);//검색날짜의 달
		today_data.put("before_year", before_after_calendar.get("before_year"));//이전년도
		today_data.put("before_month", before_after_calendar.get("before_month"));//이전달
		today_data.put("after_year", before_after_calendar.get("after_year"));//다음년도
		today_data.put("after_month", before_after_calendar.get("after_month"));//다음달
		
		//sql문 조건 설정을 위한 시작날짜와 끝날짜 값 설정 (SQL에선 달이 1~12이므로 다시 1을 더해줌)
		this.db_startDate = String.valueOf(search_year) + "-"+ String.valueOf(search_month+1)+"-"+String.valueOf(startDay);
		this.db_endDate = String.valueOf(search_year) + "-"+ String.valueOf(search_month+1)+"-"+String.valueOf(endDay);
		
		today_data.put("db_startDate", db_startDate);
		today_data.put("db_endDate", db_endDate);
		
		//현재날짜 & 검색날짜 & 검색날짜의 이전/다음날짜 정보가 담긴 Map객체 리턴
		return today_data;
		
	}
	
	
	//3-3. 이전달 다음달 & 전년도 다음년도를 얻어오는 메서드 (달력에서 버튼을 눌러 이전달, 다음달로 넘어가는 것)
	private Map<String, Integer> before_after_calendar(int search_year, int search_month){
		Map<String, Integer> before_after_data = new HashMap<String, Integer>();
		
		int before_year = search_year;		//검색날짜의 연도 == 이전년도 (일단 같은 연도에서 검색)
		int before_month = search_month-1;  //검색날짜의 월-1 == 이전달
		int after_year = search_year;		//검색날짜의 연도 == 다음년도 (일단 같은 연도에서 검색)
		int after_month = search_month+1;	//검색날짜의 월+1 == 다음달
		
		/*
		  실제 시스템적으로 세팅되는 달 시스템 : 1월(0) ~ 12월(1)
		  이전달 클릭하여 검색을 하다가, 검색한 달이 1월일 경우, 시스템 달 == 0
		  따라서 검색달의 이전달은 0-1 = -1 -> 1월에서 이전달은 12월이므로 이전달이 음수면 해당 달은 12월, 즉 시스템달 == 11
		  
		  다음달 클릭하여 검색을 하다가, 검색한 달이 12월일 경우, 시스템 달 == 11
		  따라서 검색달의 다음달은 11+1 = 12 -> 12월에서 다음달은 1월이므로, 다음달이 11보다 크면 해당 달은 1월, 즉 시스템달 == 0
		*/
		if(before_month <0) {//만약 before_month가 음수라면 (검색한 달이 1월(시스템달 0))
			before_month = 11; //그 달은 시스템달 11 (실제 12월)
			before_year = search_year -1; //전년도는 검색연도-1 (2021.12 <- 2022.1)
		}
		
		if(after_month >11) {//만약 after_month가 11보다 크다면 (검색한 달이 12월(시스템달 11)
			after_month = 0; //그 달은 시스템달 0 (실제 1월)
			after_year = search_year -1; //다음년도는 검색연도+1 (2022.12 -> 2023.1)
		}
		
		//이전년도, 이전달 & 다음년도, 다음달 정보를 저장한 Map객체를 리턴
		before_after_data.put("before_year", before_year);
		before_after_data.put("before_month", before_month);
		before_after_data.put("after_year", after_year);
		before_after_data.put("after_month", after_month);
		
		
		return before_after_data;
	}

	
	//3-4. toString 메서드 재정의
	@Override
	public String toString() {
		return "DateUtil [year=" + year + ", month=" + month + ", date=" + date + ", value=" + value + "]";
	}
	

}
