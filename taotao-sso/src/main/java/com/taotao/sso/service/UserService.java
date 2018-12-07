package com.taotao.sso.service;

import com.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserService {
	public TaotaoResult checkData(String content,Integer type); 
	public TaotaoResult createUser(TbUser user);
	public TaotaoResult userLogin(String username,String password);
	
	public TaotaoResult getUserByToken(String token);
	public TaotaoResult userLogout(String token);
}
