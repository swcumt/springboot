package com.baipengx.coin.wechat.oauth2;
/**
 * 
 * @author siwei
 *
 */

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baipengx.coin.config.wechat.WechatConfig;
import com.baipengx.common.BaseLogger;
import com.baipengx.util.HttpUtil;
import com.baipengx.util.JsonUtil;

@Component
public class WechatOAuth2Service extends BaseLogger {
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

	private static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";
	
	@Autowired
	private WechatConfig config;

	/**
	 * {"access_token":"RRMqRf82kc2Ftnqq3QzOf5TuUbVOjmI8DH4lgAG5rDwgXTLSBkufyKZGvyGPCh2C08LkemifywTmNgiuEwhX8th3O-TSTLxBiBTHoYZO9Zk","expires_in":7200,"refresh_token":"VTbeyMkr09GnRyl1anATNos37gBuo_3sbshycQCqOWs53s2sQTvqNwRCcSct-_MrHDeLEP_F0SMvcyxO6aXqrTtxqaohejIZJUkyuAj_e8Q","openid":"oVs4b0ldUXKK37zXXI98pn-a6B0o","scope":"snsapi_userinfo"}

	 * 获取Access token
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public AccessToken getToken(final String code) throws Exception {
		String url = ACCESS_TOKEN_URL + "?appid=" + config.getAppid() + "&secret=" + config.getAppsecret()
				+ "&code=" + code + "&grant_type=authorization_code";

		String resp = HttpUtil.executeGet(HttpUtil.defaultClient(), new HttpGet(url), 1, new HttpClientContext());
		log.debug("access_token is {}", resp);
		return JsonUtil.json2Obj(resp, AccessToken.class);
	}

	/**
	 * 获取用户信息
	 * @param accessToken
	 * @param openid
	 * @return
	 * @throws Exception 
	 */
	public WechatUserInfo getUserInfo(final String accessToken, final String openid) throws Exception {
		String url = USER_INFO_URL + "?access_token=" + accessToken + "&openid=" + openid + "&lang=zh_CN";
		String resp = HttpUtil.executeGet(HttpUtil.defaultClient(), new HttpGet(url), 1, new HttpClientContext());
		log.debug("userinfo is {}", resp);
		return JsonUtil.json2Obj(resp, WechatUserInfo.class);
	}
}
