package vo;

public class PageInfo {//페이지네이션
	
	private int page; //조회할 페이지 넘버
	private int maxPage; //최대 페이지 수 (전체 글 개수 / 한 페이지당 조회할 글 개수)
	
	private int startPage; //페이지네이션 첫번째 넘버
	private int endPage; //페이지네이션 마지막 넘버
	
	private int listCount; //전체 글 개수
	
	public PageInfo() {}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getMaxPage() {
		return maxPage;
	}
	
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	
	public int getStartPage() {
		return startPage;
	}
	
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	
	public int getEndPage() {
		return endPage;
	}
	
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	public int getListCount() {
		return listCount;
	}
	
	public void setListCount(int listCount) {
		this.listCount = listCount;
	}
}
