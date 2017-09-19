package com.baipengx.coin.wechat.vo;

import java.util.List;

import lombok.Data;

/**
 * {
   "subscribe": 1, 
   "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M", 
   "nickname": "Band", 
   "sex": 1, 
   "language": "zh_CN", 
   "city": "广州", 
   "province": "广东", 
   "country": "中国", 
   "headimgurl":  "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4
eMsv84eavHiaiceqxibJxCfHe/0",
  "subscribe_time": 1382694957,
  "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
  "remark": "",
  "groupid": 0,
  "tagid_list":[128,2]
}
 * @author siwei
 *
 */
@Data
public class WechatApiUser {
	private Integer subscribe;
	private String openid;
	private String nickname;
	private Integer sex;
	private String language;
	private String city;
	private String province;
	private String country;
	private String headimgurl;
	private Long subscribe_time;
	private String unionid;
	private String remark;
	private Integer groupid;
	private List<Integer> tagid_list;
	public WechatApiUser() {
	}
	
	public com.baipengx.coin.model.WechatUserInfo toModel(){
		com.baipengx.coin.model.WechatUserInfo wu = new com.baipengx.coin.model.WechatUserInfo();
		wu.setCity(this.city);
		wu.setCountry(this.country);
		wu.setHeadimg(this.headimgurl);
		wu.setIsSubscribed(this.subscribe == 1);
		wu.setNickName(this.nickname);
		wu.setUnionId(this.unionid);
		wu.setNickName(this.nickname);
		wu.setOpenid(this.openid);
		wu.setProvince(this.province);
		wu.setSex("1".equals(this.sex)? "M":"F");
		return wu;
	}
}
