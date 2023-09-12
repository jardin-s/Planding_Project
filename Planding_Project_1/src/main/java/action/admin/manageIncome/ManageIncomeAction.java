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

public class ManageIncomeAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		
		DateUtil dateUtil = new DateUtil();
		
		if(year == null && month == null) {//클릭하여 검색하지 않는 경우 (연도, 달 파라미터값 없음. 현재달 세팅. 매출관리 초기화면)
			
			//오늘날짜로 세팅한 DateUtil객체 생성
			dateUtil = new DateUtil();
			dateUtil.setYear(String.valueOf(Calendar.YEAR));
			dateUtil.setMonth(String.valueOf(Calendar.MONTH));
			dateUtil.setDate(String.valueOf(Calendar.DATE));
			
		}else {//클릭하여 검색하는 경우 (연도와 달 값이 파라미터값으로 넘어옴)
			
			dateUtil = new DateUtil();
			dateUtil.setYear(year);//검색연도
			dateUtil.setMonth(month);//검색월
			dateUtil.setDate(String.valueOf(Calendar.DATE));//일은 오늘날짜의 일로 세팅
			
		}
		
		//뷰페이지에 세팅할 날짜의 정보
		Map<String, Object> today_info = dateUtil.today_info(dateUtil);
		request.setAttribute("today_info", today_info);
		
		//SQL문에서 쓸 첫날, 마지막날
		String db_startDate = String.valueOf(today_info.get("db_startDate"));
		String db_endDate = String.valueOf(today_info.get("db_endDate"));
		
		//실제 매출 정보를 담아 출력
		ManageIncomeService manageIncomeService = new ManageIncomeService();
		List<DateUtil> dateList = ManageIncomeService.selectList(db_startDate, db_endDate);
		request.setAttribute("dateList", dateList);
		
		return forward;
	}

}
