package com.baipengx.coin.config.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfig extends WebMvcConfigurerAdapter {
	@Value("${coins.icon.dir}")
	private String filepath;
	@Value("${gotoken.ico.iconpath}")
	private String icoIconDir;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/img/icon/**").addResourceLocations("file:" + filepath);
		registry.addResourceHandler("/ico/icon/**").addResourceLocations("file:" + icoIconDir);
	}
}