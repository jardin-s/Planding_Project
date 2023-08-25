/**
 * 포워딩 정보를 저장
 * - 컨트롤러에서 각 요청을 받아서 처리한 후, 다시 컨트롤러에 옴
 *   최종적으로 뷰 페이지(.jsp)로 포워딩 처리 시
 *   '이동할 뷰 페이지의 ur'과 '포워딩 방식(디스패치,리다이렉트)'을
 *   편리하게 다루기 위해 클래스로 설계
 *   (request에 값이 들어있어 그대로 넘겨줘야 할 때 디스패치로 포워딩)
 */

package vo;

public class ActionForward {
	
	private String path = null; //*.usr/adm/kiosk(요청), 이동할 뷰 페이지의 url(예:.jsp 뷰페이지)
	
	//디스패치=false(기본값),리다이렉트=true
	private boolean isRedirect = false; //false면 디스패치로 포워딩, true면 리다이렉트로 포워딩
	//기본값이 디스패치? request객체를 공유하여 그대로 넘겨줘야 하므로

	
	
	//★★매개변수가 없는 생성자 - 반드시 자바빈에 존재해야 함
	public ActionForward() {}
	
	//매개변수가 있는 생성자 (편의를 위해 삽입)
	public ActionForward(String path, boolean isRedirect) {
		super();
		this.path = path;
		this.isRedirect = isRedirect;
	}

	
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
	
	
	
}
