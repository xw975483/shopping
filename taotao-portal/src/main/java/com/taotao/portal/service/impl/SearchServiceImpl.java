package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.common.pojo.TaotaoResult;
import com.common.utils.HttpClientUtil;
import com.common.utils.JsonUtils;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
/**
 * 调用taotao-search服务来搜索
 * @author hw
 *
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_URL}")
	private String SEARCH_URL;
	
	public SearchResult getSearchResult(String queryString, int page) {
		Map<String , String > param = new HashMap<>();
		param.put("q", queryString);
		param.put("page", page+"");
		String json = HttpClientUtil.doGet(SEARCH_URL, param);
		System.out.println(json);
		try {
			//json字符串转对象
			TaotaoResult formatToPojo = TaotaoResult.formatToPojo(json, SearchResult.class);
			if(formatToPojo.getStatus() == 200) {
				SearchResult searchResult = (SearchResult)formatToPojo.getData();
				return searchResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
