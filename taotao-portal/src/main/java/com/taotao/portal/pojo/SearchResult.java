package com.taotao.portal.pojo;

import java.util.List;

public class SearchResult {
	private List<ItemPojo> itemList;
	private long recordCount;
	private long pageCount;
	private long page;
	public List<ItemPojo> getItemList() {
		return itemList;
	}
	public void setItemList(List<ItemPojo> itemList) {
		this.itemList = itemList;
	}
	public long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	public long getPageCount() {
		return pageCount;
	}
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	public long getPage() {
		return page;
	}
	public void setPage(long page) {
		this.page = page;
	}
	
	
}
