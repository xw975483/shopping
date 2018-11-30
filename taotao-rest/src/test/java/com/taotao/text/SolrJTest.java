package com.taotao.text;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {
	
	@Test
	public void addDocument() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://180.76.50.167:8080/solr");
		//创建一个对象文档
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "001");
		document.addField("item_title", "手机");
		document.addField("item_price", 123123);
		//文档写入索引库
		solrServer.add(document);
		solrServer.commit();
	}
	
	@Test
	public void deleteTest() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://180.76.50.167:8080/solr");
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}
}
