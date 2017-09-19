package com.baipengx.coin.service;

import java.util.List;

import com.baipengx.coin.model.WechatMessage;


public interface WechatMessageService {

	/**
	 * 保存 微信消息
	 * @param msg
	 */
	void saveMessage(WechatMessage msg);

	/**
	 * 查询需要推送的消息
	 * @return
	 */
	List<WechatMessage> query2send();
	
	
	/**
	 * 
	 * @param msg
	 */
	void updateWechatMessage(WechatMessage msg);
}
