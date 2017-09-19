package com.baipengx.coin.wechat.message.req;

/**
 * 图片消息
 * 
 * @author siwei
 */
public class ImageMessage extends BaseMessage {
	// 图片链接
	private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
}
