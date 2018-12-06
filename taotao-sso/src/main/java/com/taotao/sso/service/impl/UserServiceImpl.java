package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
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

	@Override
	public TaotaoResult userLogin(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
