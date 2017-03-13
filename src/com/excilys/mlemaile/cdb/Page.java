package com.excilys.mlemaile.cdb;

import java.util.List;

public class Page<T> {
	public static int number_per_page = 50;
	private List<T> list;
	private int pageNumber;
	
	public Page(int page){
		setPageNumber(page);
	}
	
	public void displayPage(){
		for(T t : list){
			System.out.println(t.toString());
		}
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

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
