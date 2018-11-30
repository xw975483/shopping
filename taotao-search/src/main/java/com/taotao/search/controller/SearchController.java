package com.taotao.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.pojo.TaotaoResult;
import com.common.utils.ExceptionUtil;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;

/**
 * 
 * @author xw
 *
 */
@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value="/query",method=RequestMethod.GET)
	@ResponseBody
	public TaotaoResult search(@RequestParam("p") String queryString,
							@RequestParam(defaultValue="1") int page,
							@RequestParam(defaultValue="30")  int rows){
		if (StringUtils.isBlank(queryString)) {
			return TaotaoResult.build(400, "搜索条件不能为空");
		}
		
		SearchResult searchResult = null;
		try {
			//解决乱码
			queryString = new String(queryString.getBytes("iso8859-1"),"utf-8");
			searchResult = searchService.search(queryString, page, rows);
			
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(400, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok(searchResult);
	}
}
