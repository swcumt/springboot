package com.baipengx.coin.config.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.baipengx.coin.config.wechat.WechatConfig;
import com.baipengx.coin.controller.BaseController;

/**
 * 微信 未授权 跳转授权页
 * @author siwei
 *
 */
@Component
public class OAuth2Interceptor extends BaseController implements HandlerInterceptor {
	@Autowired
	private WechatConfig config;

	/**
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (isAuthed(request)) {
			return true;
		}
		else {
			response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + config.getAppid()
					+ "&redirect_uri=" + getRootPath(request)
					+ "/ico/wechat/oauthed"+URLEncoder.encode("?request-uri=" + request.getRequestURL().toString() + (StringUtils.isEmpty(request.getQueryString())? "" : "?" + request.getQueryString()), "utf-8") +"&response_type=code&scope=snsapi_userinfo&state=2017#wechat_redirect");
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
