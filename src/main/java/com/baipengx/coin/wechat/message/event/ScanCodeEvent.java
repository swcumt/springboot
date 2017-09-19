package com.baipengx.coin.wechat.message.event;

/**
 * 扫描带参数二维码事件
 * 
 * @author siwei
 */
public class ScanCodeEvent extends BaseEvent {
	// 事件KEY值
	private String EventKey;
	// 用于换取二维码图片
	private String Ticket;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		Ticket = ticket;
	}
}
