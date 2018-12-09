package com.taotao.portal.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.common.pojo.TaotaoResult;
import com.common.utils.HttpClientUtil;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;
	@Value("${SSO_TOKEN_URL}")
	private String SSO_TOKEN_URL;
	@Value("${SSO_BASE_URL}")
	public String SSO_LOGIN_URL;
	
	@Override
	public TbUser getUserByToken(String token) {
		try {
			String json = HttpClientUtil.doGet(SSO_BASE_URL+SSO_TOKEN_URL+token);
			TaotaoResult tbUser = TaotaoResult.formatToPojo(json, TbUser.class);
			if(tbUser.getStatus() == 200) {
				TbUser user = (TbUser) tbUser.getData();
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
