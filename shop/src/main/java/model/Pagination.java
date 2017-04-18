package model;

public class Pagination {
	//页码
	private String pageIndex = "1";
	//共几页
	private String pageSize;
	//每页条数
	private String pageCount = "10";
	//查询用 从几到几
	private String pageFrom = "1";
	private String pageTo = "10";
	//总数
	private String total;
	//分页开关 可不用
	private String pageYn;
	
	private int limitFrom = 0;
	private int limitCount = 10;
	
	public void setPagination(String pageIndex, String total){
		if(pageIndex != null && !"".equals(pageIndex)){
			this.pageIndex = pageIndex;
		}else{
			pageIndex = "1";
			this.pageIndex = pageIndex;
		}
		this.total = total;
		int pi = Integer.parseInt(pageIndex);
		int tt = Integer.parseInt(total);
		int pc =  Integer.parseInt(pageCount);
		this.pageSize = (tt%pc==0?tt/pc:(tt/pc+1))  + "";
		this.pageFrom = ((pi-1)*pc+1)+"";
		this.pageTo = ((pi-1)*pc+pc)+"";
		this.limitFrom =Integer.parseInt(this.pageFrom)-1;
		this.limitCount =Integer.parseInt(this.pageCount);
	}
	
	public int getLimitFrom() {
		return limitFrom;
	}

	public void setLimitFrom(int limitFrom) {
		this.limitFrom = limitFrom;
	}

	public int getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(int limitCount) {
		this.limitCount = limitCount;
	}

	public String getPageYn() {
		return pageYn;
	}


	public void setPageYn(String pageYn) {
		this.pageYn = pageYn;
	}


	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getPageCount() {
		return pageCount;
	}
	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}
	public String getPageFrom() {
		return pageFrom;
	}
	public void setPageFrom(String pageFrom) {
		this.pageFrom = pageFrom;
	}
	public String getPageTo() {
		return pageTo;
	}
	public void setPageTo(String pageTo) {
		this.pageTo = pageTo;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
}
