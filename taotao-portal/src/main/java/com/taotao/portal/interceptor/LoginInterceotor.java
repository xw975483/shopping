package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import com.taotao.portal.service.impl.UserServiceImpl;

public class LoginInterceotor implements HandlerInterceptor {

	@Autowired
	private UserServiceImpl userService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 在handler执行前处理，由返回值来决定handler是否执行，返回true得时候执行
		String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//返回所需要得user信息，下面进行判断
		TbUser tbUser = userService.getUserByToken(cookieValue);
		if(tbUser == null) {
			//说明redis里面没有token对应得用户，也就是用户没有登录，需要跳转到登录页面
			response.sendRedirect(userService.SSO_BASE_URL+userService.SSO_LOGIN_URL+request.getRequestURL());
			return false;
		}
		//得到了用户信息就返回true
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
