package com.baipengx.coin.config.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.baipengx.common.BaseLogger;

@Configuration
public class FreeMarkerConfig extends BaseLogger{
	@Autowired
	private freemarker.template.Configuration configuration;
	
	
	@PostConstruct
	public void setVariables(){
		configuration.setSharedVariable("getPercent",  new GetPercentMethod());
		configuration.setSharedVariable("countDown", new CountDownMethod());
	}
}
