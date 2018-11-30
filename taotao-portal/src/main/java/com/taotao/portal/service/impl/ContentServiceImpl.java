package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.common.pojo.TaotaoResult;
import com.common.utils.HttpClientUtil;
import com.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_INDEX_AD_URL}")
	private String REST_INDEX_AD_URL;
	@Override
	public String getContentList() {
		//调用服务层的服务
		try {
			String result = HttpClientUtil.doGet(REST_BASE_URL+REST_INDEX_AD_URL);
			TaotaoResult taotaoResult = TaotaoResult.formatToList(result, TbContent.class);
			List<TbContent> listtb = (List<TbContent>) taotaoResult.getData();
			List<Map> resultList = new ArrayList<>();
			for (TbContent tb : listtb) {
				Map map = new HashMap<>();
				map.put("src", tb.getPic());
				map.put("height", 240);
				map.put("width", 670);
				map.put("srcB", tb.getPic2());
				map.put("widthB", 550);
				map.put("heightB", 240);
				map.put("href", tb.getUrl());
				map.put("alt", tb.getSubTitle());
				resultList.add(map);
			}
			return JsonUtils.objectToJson(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
