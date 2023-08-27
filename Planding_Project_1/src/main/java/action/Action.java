/**
 * Action클래스들의 규격을 정의한 인터페이스
 */

package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.ActionForward;

//인터페이스에 각 Action 클래스들의 규격을 정의
public interface Action {
	//미완성된 추상 메서드 : 구현한 자식클래스에서 반드시 재정의해야 함
	
	//이동할 url + 포워딩방식
	ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception;//호출한 쪽에서 예외 처리  
	
}
