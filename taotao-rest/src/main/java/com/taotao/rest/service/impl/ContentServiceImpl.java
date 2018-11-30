package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.common.utils.JsonUtils;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String REDIS_KEY;
	
	//根据广告位id从数据库查询该位置所有的广告
	public List<TbContent> geContents(Long contentCid) {
		//在redis缓存里查询
		try {
			String string = jedisClient.hget(REDIS_KEY, contentCid+"");
			if(!StringUtils.isBlank(string)){
				List<TbContent> list = JsonUtils.jsonToList(string, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentCid);
		List<TbContent> list = contentMapper.selectByExample(example);
		
		//把从数据库中取出的数据存到缓存中
		try {
			String jsonlist = JsonUtils.objectToJson(list);
			Long long1 = jedisClient.hset(REDIS_KEY, contentCid+"", jsonlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
