package com.example.domain;

/**
 * 
 * @author ashibe
 *
 */
public class Page {
	
	
	/**今のページ
	 * 
	 */
	private Integer nowPage;
	
	/**
	 * 前のページ
	 */
	private Integer prevPage;
	
	/**
	 * 次のページ
	 */
	private Integer nextPage;

	@Override
	public String toString() {
		return "Page [nowPage=" + nowPage + ", prevPage=" + prevPage + ", nextPage=" + nextPage + ", getNowPage()="
				+ getNowPage() + ", getPrevPage()=" + getPrevPage() + ", getNextPage()=" + getNextPage()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	public Integer getNowPage() {
		return nowPage;
	}

	public void setNowPage(Integer nowPage) {
		this.nowPage = nowPage;
	}

	public Integer getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(Integer prevPage) {
		this.prevPage = prevPage;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}

}
