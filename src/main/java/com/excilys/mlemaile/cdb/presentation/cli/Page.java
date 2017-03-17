package com.excilys.mlemaile.cdb.presentation.cli;

public class Page<T> {
	public static int number_per_page = 50;
	private int pageNumber;
	
	public Page(int page){
		setPageNumber(page);
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		if(pageNumber>0){
			this.pageNumber = pageNumber;
		}else{
			throw new IllegalArgumentException("A page number must be positive");
		}
	}
}
