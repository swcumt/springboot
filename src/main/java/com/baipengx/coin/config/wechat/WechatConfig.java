package com.baipengx.coin.config.wechat;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "wechat")
public class WechatConfig {
	private String appid;
	private String appsecret;
	private String token;
	private String encodingaeskey;
	private Pay pay;
	
	
	@Data
	public static class Pay{
		private String key;
		private String mchid;
	}
}
