package com.baipengx.coin.service;

import com.baipengx.coin.model.WechatUserInfo;

/**
 * 
 * @author siwei
 *
 */
public interface WechatUserInfoService {
	/**
	 * 保存  微信用户信息
	 * @param wu
	 * @return 
	 */
	com.baipengx.coin.model.WechatUserInfo save(com.baipengx.coin.model.WechatUserInfo wu);

	/**
	 * 是否需要获取 用户信息
	 * @param openid
	 * @return
	 */
	boolean needGetWechatUserInfo(String openid);
	
	/**
	 * 根据openId 获取用户
	 * @param openId
	 * @return
	 */
	com.baipengx.coin.model.WechatUserInfo queryByOpenid(String openId);
	
	/**
	 * 
	 * @param uuid
	 * @return
	 */
	WechatUserInfo queryByUUID(String uuid);
	
	/**
	 * 取消订阅时 更改状态
	 * @param openId
	 */
	void update2UnSubscribe(String openId);
	
	
	void reminded(Long id);
}
