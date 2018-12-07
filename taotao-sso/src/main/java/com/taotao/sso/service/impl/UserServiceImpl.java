package com.taotao.sso.service.impl;



import java.util.Date;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.util.DigestUtils;



import com.common.pojo.TaotaoResult;
import com.common.utils.JsonUtils;
import com.taotao.mapper.TbUserMapper;

import com.taotao.pojo.TbUser;

import com.taotao.pojo.TbUserExample;

import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.UserService;

/**
 * 进行数据校验
 * @author xw
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper tbUserMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${USER_SESSION_TOKEN}")
	private String USER_SESSION_TOKEN; 

	@Value("${SSO_SESSION_EXPIRE}")
	private Integer SSO_SESSION_EXPIRE;

	public TaotaoResult checkData(String content, Integer type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		//对数据进行校验：1、2、3分别代表username、phone、email
		if(type==1){
			criteria.andUsernameEqualTo(content);
		}else if (2 == type) {
			criteria.andPhoneEqualTo(content);
		}else {
			criteria.andEmailEqualTo(content);
		}
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			return TaotaoResult.ok(true);
		}

		return TaotaoResult.ok(false);
	}

	//注册的接口
	public TaotaoResult createUser(TbUser user) {
		user.setUpdated(new Date());
		user.setCreated(new Date());
		//spring自带的md5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		tbUserMapper.insert(user);
		return TaotaoResult.ok();
	}



	//登录操作
	public TaotaoResult userLogin(String username, String password) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if (list == null||list.size()==0 ) {
			return TaotaoResult.build(500, "用户名或密码错误");
		}else {
			
		}
		TbUser tbUser = list.get(0); 
		if( !DigestUtils.md5DigestAsHex(password.getBytes()).equals(tbUser.getPassword()) ) {
			return TaotaoResult.build(500, "用户名或密码错误");
		}
		String token = UUID.randomUUID().toString();
		tbUser.setPassword(null);
		jedisClient.set(USER_SESSION_TOKEN+":"+token,JsonUtils.objectToJson(tbUser));
		jedisClient.expire(USER_SESSION_TOKEN+":"+token, SSO_SESSION_EXPIRE);
		
		return TaotaoResult.ok(token);

	}



	public TaotaoResult getUserByToken(String token) {
		String json = jedisClient.get(USER_SESSION_TOKEN+":"+token);
		if (!StringUtils.isBlank(json)) {
			TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
			jedisClient.expire(USER_SESSION_TOKEN+":"+token, SSO_SESSION_EXPIRE);
			return TaotaoResult.ok(tbUser);
		}else {
			return TaotaoResult.build(500, "此session已过期，请重新登录");
		}
	}

	//安全退出的功能
	public TaotaoResult userLogout(String token) {
		String json = jedisClient.get(USER_SESSION_TOKEN+":"+token);
		if(!StringUtils.isBlank(json)){
			jedisClient.del(USER_SESSION_TOKEN+":"+token);
			return TaotaoResult.ok();
		}else{
			return TaotaoResult.build(500, "session不存在，请重新登录");
		}
	}

   

}