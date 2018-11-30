package com.taotao.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.common.pojo.TaotaoResult;
import com.common.utils.ExceptionUtil;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.RedisService;

import sun.tools.jar.resources.jar;
/**
 * 对外发布的清空redis缓存的服务
 * @author xw
 *
 */
@Service
public class RedisServiceImpl implements RedisService{

	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String REDIS_KEY;
	
	@Override
	public TaotaoResult sysnContent(long contentCid) {
		try {
			jedisClient.hdel(REDIS_KEY, contentCid+"");
			System.out.println("---");
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}

}
