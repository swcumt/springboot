package com.baipengx.coin.wechat.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.baipengx.coin.controller.BaseController;
import com.baipengx.coin.service.WechatUserInfoService;
import com.baipengx.coin.wechat.message.resp.TextMessage;
import com.baipengx.coin.wechat.util.MessageUtil;

/**
 * 
 * @author siwei
 *
 */
@Component
public class WechatService extends BaseController {

	@Autowired
	private WechatUserInfoService userService;

	@Autowired
	private TokenService tokenService;

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return xml
	 */
	public String processRequest(HttpServletRequest request) {
		// xml格式的消息数据
		String respXml = null;
		// 默认返回的文本消息内容
		String respContent = "";
		try {
			// 调用parseXml方法解析请求消息
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号
			String fromUserName = requestMap.get("FromUserName");
			// 开发者微信号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(System.currentTimeMillis());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				respContent = "";
				String content = requestMap.get("Content");
				if (!StringUtils.isEmpty(content) && "彩蛋".equals(content.trim())) {
					respContent = "最新下款成功率排行榜：\n"
							+ "1、<a href =\"https://m.daxiaodai.com/hd/active/html/wifi_tab.html?source=gc-llcs-channel5\">大小贷</a>\n"
							+ "2、<a href =\"https://api.daishangqian.com/vue/?channel=58eaf2e471b3#/active/promotion\">贷上钱</a>\n"
							+ "3、<a href =\"http://channel.xianjinxia.com/act/light-loan-xjx?invite_code=MjYxNTczNw==&user_from=577\">极速现金侠</a>\n"
							+ "4、<a href =\"https://activity.jiandanjiekuan.com/html/register_getNewUser.html?channelCode=1b73e64b00e042cdac036489a9f5faf9\">简单借款</a>\n"
							+ "5、<a href =\"https://onecard.9fbank.com/wkCubeNew/#/register?proId=dtj9181c9c0179c4e3bae3d9cecf336d002\">玖富万卡 </a>\n";
				}
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "";
			}
			// 语音消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "";
			}
			// 视频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				respContent = "";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 关注
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "hi，各位看官。你终于来了！！！\n" + "网贷、银行卡、QQ三大指数，一网打尽。\n" + "知己知彼，下款无忧！\n"
							+ "查询戳下方：<a href=\"http://wx.daikuantoutiao.com/wechat/home\">百信分</a>\n\n" +

							"有什么问题，直接后台留言哟！";
				}
				// 取消关注
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
					userService.update2UnSubscribe(fromUserName);
				}
				// 扫描带参数二维码
				else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
					// TODO 处理扫描带参数二维码事件
				}
				// 上报地理位置
				else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
					// TODO 处理上报地理位置事件
				}
				// 自定义菜单
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// TODO 处理菜单点击事件
					if ("advice".equals(requestMap.get("EventKey"))) {
						respContent = "老哥们，有什么建议或者疑问直接在后台留言哈，我们这边会很快给你们反馈的！说不定还有神秘口子等着你哟！";
					}
				}
			}
			// 设置文本消息的内容
			textMessage.setContent(respContent);

			// 将文本消息对象转换成xml
			if (!StringUtils.isEmpty(respContent))
				respXml = MessageUtil.messageToXml(textMessage);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}
}
