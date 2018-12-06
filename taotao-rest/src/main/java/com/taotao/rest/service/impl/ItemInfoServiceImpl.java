package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.common.pojo.TaotaoResult;
import com.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemInfoService;


@Service
public class ItemInfoServiceImpl implements ItemInfoService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TbItemDescMapper itemdesc;
	@Autowired
	private TbItemParamItemMapper itemParam;
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;
	
	public TaotaoResult getItemInfo(long itemId) {
		//从缓存中取
		String json = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":base");
		if(!StringUtils.isBlank(json)){
			TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
			return TaotaoResult.ok(item);
		}
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		try {
			jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":base", JsonUtils.objectToJson(item));
			//设置过期时间
			jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":base", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(item);
	}

	//取商品描述
	public TaotaoResult getItemDesc(long itemId) {
		//从缓存中取
		String json = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":desc");
		if(!StringUtils.isBlank(json)){
			TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
			return TaotaoResult.ok(tbItemDesc);
		}		
		TbItemDesc tbItemDesc = itemdesc.selectByPrimaryKey(itemId);
		try {
			jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":desc", JsonUtils.objectToJson(tbItemDesc));
			//设置过期时间
			jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":desc", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(tbItemDesc);
	}

	//取商品规格参数
	public TaotaoResult getItemParams(long itemId) {
		String json = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":param");
		if(!StringUtils.isBlank(json)){
			TbItemParamItem itemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
			return TaotaoResult.ok(itemParamItem);
		}	
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemParam.selectByExampleWithBLOBs(example);
		if(list!= null && list.size()>0){
			TbItemParamItem itemParamItem = list.get(0);
		    try {
			jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":param", JsonUtils.objectToJson(itemParamItem));
			//设置过期时间
			jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":param", REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		    return TaotaoResult.ok(itemParamItem);
		}
		
		return TaotaoResult.build(400, "无此商品");
		
	}

}
