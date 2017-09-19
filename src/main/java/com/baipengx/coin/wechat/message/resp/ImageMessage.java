package com.baipengx.coin.wechat.message.resp;

/**
 * 图片消息
 * 
 * @author siwei
 */
public class ImageMessage extends BaseMessage {
	// 图片
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}
}
