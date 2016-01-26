package com.eudemon.taurus.app.entity;

import java.util.ArrayList;
import java.util.List;

public class PagedEntity<T> {
	private int rowCount = 0;
	private int pageNo = 1;
	private int pageSize = 10;
	private int pageCount = 1;
	private List<T> entities = new ArrayList<T>();

	public PagedEntity(int pg, int ps, int count, List<T> list){
		this.pageNo = pg;
		this.pageSize = ps;
		this.rowCount = count;
		this.entities = list;
	}
	
	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		pageCount = rowCount % pageSize == 0 ? rowCount / pageSize : rowCount / pageSize + 1;
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public List<T> getEntities() {
		return entities;
	}

	public void setEntities(List<T> entities) {
		this.entities = entities;
	}
}
