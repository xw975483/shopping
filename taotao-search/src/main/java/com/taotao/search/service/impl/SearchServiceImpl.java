package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService{

	@Autowired
	private SearchDao searchDao;
	
	public SearchResult search(String queryString, int page, int rows) throws Exception {
		//查询对象
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(queryString);
		solrQuery.setStart((page-1) * rows);
		solrQuery.setRows(rows);
		//默认搜索域
		solrQuery.set("df", "item_keywords");
		//高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
		solrQuery.setHighlightSimplePost("</em>");
		
		SearchResult search = searchDao.search(solrQuery);
		//总页数
		long recordCount = search.getRecordCount();
		long pageCount = recordCount/rows;
		if(recordCount % rows > 0){
			pageCount++;
		}
		search.setPageCount(pageCount);
		search.setPage(page);
		
		return search;
	}

}
