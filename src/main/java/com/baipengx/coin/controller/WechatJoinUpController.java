package com.baipengx.coin.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baipengx.coin.config.wechat.WechatConfig;
import com.baipengx.coin.wechat.service.WechatService;
import com.baipengx.util.DigestUtil;


/**
 * 微信接入
 * @author siwei
 *
 */
@Controller
public class WechatJoinUpController extends BaseController {

	@Autowired
	private WechatService wechatService;
	
	@Autowired
	private WechatConfig config;
	/**
	 * wechat 接入验证 GET请求
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @param response
	 */
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public void joinup(@RequestParam(required = true) String signature, @RequestParam(required = true) String timestamp,
			@RequestParam(required = true) String nonce, @RequestParam(required = true) String echostr,
			HttpServletResponse response) {
//		logger.debug("echostr is {}", echostr);
		if (isSign(signature, timestamp, nonce)) {
			write(response, echostr);
		}
		else {
			log.warn("{} is not correct", signature);
		}
	}

	/**
	 * 验证 请求是否 是 wechat 服务器 发送来的
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	private boolean isSign(String signature, String timestamp, String nonce) {
//		logger.debug("signature is {}", signature);
//		logger.debug("timestamp is {}", timestamp);
//		logger.debug("nonce is {}", nonce);
		String[] arr = new String[] { timestamp, nonce, config.getToken() };

		Arrays.sort(arr);

		String sortedStr = "";

		for (String s : arr) {
			sortedStr += s;
		}

//		logger.debug("sortedStr is {}", sortedStr);
		String mysign = DigestUtil.SHA1(sortedStr);
//		logger.debug("mysign is {}", mysign);
		return mysign.equals(signature);
	}

	/**
	 * 接收微信传入的数据 并做出响应
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public void receive(@RequestParam(required = true) String signature,
			@RequestParam(required = true) String timestamp, @RequestParam(required = true) String nonce,
			HttpServletRequest request, HttpServletResponse response) {
		if(isSign(signature, timestamp, nonce)){
			String resp = wechatService.processRequest(request);
			
			if(!StringUtils.isEmpty(resp))
				write(response, resp);
		}else{
			log.warn("signature is wrong, signature is {}", signature);
		}
	}
}
