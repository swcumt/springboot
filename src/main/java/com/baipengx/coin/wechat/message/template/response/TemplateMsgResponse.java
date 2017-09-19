package com.baipengx.coin.wechat.message.template.response;
/**
 * {
"errcode":0,
"errmsg":"ok",
"msgid":200228332
}
 * @author siwei
 *
 */
public class TemplateMsgResponse {
	private Integer errcode;
	private String errmsg;
	private String msgid;
	public Integer getErrcode() {
		return errcode;
	}
	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
}
