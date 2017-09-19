package com.baipengx.coin.wechat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.baipengx.coin.config.wechat.WechatConfig;
import com.baipengx.coin.jedis.JedisDao;
import com.baipengx.coin.wechat.util.CommonUtil;
import com.baipengx.coin.wechat.vo.Token;
import com.baipengx.common.BaseLogger;

@Component
public class TokenService extends BaseLogger {
	private static final String TOKEN_KEY = "gotoken.wechat.token";
	@Autowired
	private WechatConfig config;
	@Autowired
	@Qualifier("stringJedis")
	private JedisDao<String, String> jedisDao;
	
	public String getToken(){
		String token = jedisDao.get(TOKEN_KEY);
		if(token != null)
			return token;
		
	  Token t =	CommonUtil.getToken(config.getAppid(), config.getAppsecret());
	  
	  jedisDao.save(TOKEN_KEY, t.getAccessToken(), t.getExpiresIn()-300l);
	  
	  return t.getAccessToken();
	}
}
