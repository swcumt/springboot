package com.baipengx.coin.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器配置
 * @author siwei
 *
 */
@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
	@Autowired
	private OAuth2Interceptor oauth2Interceptor;
	@Autowired
	private TicketInterceptor ticketInterceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(oauth2Interceptor)
				.addPathPatterns("/ico/wechat/**")
				.addPathPatterns("/pay/**")
				.addPathPatterns("/activity/**")
				.addPathPatterns("/home")
				.addPathPatterns("/test/**")
				.excludePathPatterns("/ico/wechat/oauthed", "/ico/wechat/common/**")
				.excludePathPatterns("/activity/1")
				.excludePathPatterns("/activity/en/1")
				.excludePathPatterns("/activity/myticket/**")
				.excludePathPatterns("/pay/report");
		registry.addInterceptor(ticketInterceptor).addPathPatterns("/activity/ticket/**");
	}
}
