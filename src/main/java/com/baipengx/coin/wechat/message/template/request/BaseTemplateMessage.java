package com.baipengx.coin.wechat.message.template.request;

import java.io.Serializable;
/**
 * {
"touser":"OPENID",
"template_id":"ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY",
"url":"http://weixin.qq.com/download",
"topcolor":"#FF0000",
"data":{
"User": {
"value":"黄先生",
"color":"#173177"
},
"Date":{
"value":"06月07日 19时24分",
"color":"#173177"
},
"CardNumber":{
"value":"0426",
"color":"#173177"
},
"Type":{
"value":"消费",
"color":"#173177"
},
"Money":{
"value":"人民币260.00元",
"color":"#173177"
},
"DeadTime":{
"value":"06月07日19时24分",
"color":"#173177"
},
"Left":{
"value":"6504.09",
"color":"#173177"
}
}
}
 * @author siwei
 *
 */
public class BaseTemplateMessage implements Serializable{
	protected String touser;
	protected String template_id;
	protected String url;
	protected String topcolor="#FF0000";
	
	protected Object data;
	
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTopcolor() {
		return topcolor;
	}
	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
