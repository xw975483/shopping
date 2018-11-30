package com.taotao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.ItemPojo;
import com.taotao.search.pojo.SearchResult;

@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	private SolrServer solrServer;
	
	public SearchResult search(SolrQuery query) throws Exception {
		SearchResult searchResult = new SearchResult();
		QueryResponse queryResponse = solrServer.query(query);
		//取高亮显示
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		
		SolrDocumentList list = queryResponse.getResults();
		searchResult.setRecordCount(list.getNumFound());
		List<ItemPojo> itemlist = new ArrayList<>();
		for (SolrDocument solrDocument : list) {
			ItemPojo itemPojo = new ItemPojo();
			itemPojo.setId((String)solrDocument.get("id"));
			//高亮
			List<String> list2 = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if (list2 != null && list.size() >0) {
				title = list2.get(0);
			}else{
				title=(String)solrDocument.get("item_title");
			}
			itemPojo.setTitle(title);
			itemPojo.setImage((String)solrDocument.get("item_image"));
			itemPojo.setPrice((long)solrDocument.get("item_price"));
			itemPojo.setSell_point((String)solrDocument.get("item_sell_point"));
			itemPojo.setCategory_name((String)solrDocument.get("item_category_name"));
			itemlist.add(itemPojo);
		}
		searchResult.setItemList(itemlist);
		return searchResult;
	}

}
