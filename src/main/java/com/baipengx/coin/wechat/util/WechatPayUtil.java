package com.baipengx.coin.wechat.util;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baipengx.coin.config.wechat.WechatConfig;
import com.baipengx.common.BaseLogger;
import com.baipengx.util.DigestUtil;
import com.baipengx.util.HttpUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Component
public class WechatPayUtil extends BaseLogger{
	private static final String PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	@Autowired
	private WechatConfig wechatConfig;
	private static String CREATE_ORDER_XML = "" + "<xml>" + "<appid>APPID</appid>" + // 公众号ID
			"<device_info>WEB</device_info>" + // 设备信息
			"<detail><![CDATA[DETAIL]]></detail>" + // 商品详情
			"<body>BODY</body>" + // 商品描述
			"<mch_id>MERCHANT</mch_id>" + // 微信给的商户ID
			"<nonce_str>NONCE_STR</nonce_str>" + // 32位随机字符串,不改
			"<notify_url><![CDATA[REPORT_URL]]></notify_url>" + // 信息通知页面
			"<openid>OPENID</openid>" + // 支付的用户ID
			"<fee_type>CNY</fee_type>" + // 支付货币，不改
			"<spbill_create_ip>IP</spbill_create_ip>" + // 用户IP
			"<time_start>START</time_start>" + // 订单开始时间
			"<time_expire>STOP</time_expire>" + // 订单结束时间
			"<goods_tag>WXG</goods_tag>" + // 商品标记，不改
			"<limit_pay>no_credit</limit_pay>" + // 支付范围，默认不支持信用卡支付，不改
			"<out_trade_no>ORDER_NO</out_trade_no>" + // 商城生成的订单号
			"<total_fee>TOTAL_FEE</total_fee>" + // 总金额
			"<trade_type>JSAPI</trade_type>" + // 交易类型，JSAPI，NATIVE，APP，WAP
			"<sign>SIGN</sign>" + // 加密字符串
			"</xml>";

	private static DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");

	/**
	 * 
	 * @param detail
	 * @param desc
	 * @param openid
	 * @param ip
	 * @param orderNo
	 * @param fee
	 * @return
	 */
	public  Map<String, String> createOrder(String detail, String desc, String openid, String ip, String orderNo,
			Integer fee, String reportUrl) {
		String xml = CREATE_ORDER_XML.replace("APPID", wechatConfig.getAppid());
		xml = xml.replace("DETAIL", detail);
		xml = xml.replace("BODY", desc);
		xml = xml.replace("MERCHANT", wechatConfig.getPay().getMchid());
		String nonceStr = RandomStringUtils.random(32, true, true);
		xml = xml.replace("NONCE_STR", nonceStr);
		xml = xml.replace("REPORT_URL", reportUrl);
		xml = xml.replace("OPENID", openid);
		xml = xml.replace("IP", ip);
		String start = df.format(new Date());
		String end = df.format(new Date().getTime() + 7 * 24 * 60 * 60 * 1000);
		xml = xml.replace("START", start);
		xml = xml.replace("STOP", end);
		xml = xml.replace("ORDER_NO", orderNo);
		xml = xml.replace("TOTAL_FEE", fee.toString());
		try {
			Map<String, String> params = xml2Map(xml);
			params.remove("sign");
			log.debug("生成验签 map is {}", params);
			xml = xml.replace("SIGN", getSign(params));
			log.debug("统一下单接口 xml is :{}", xml);

			HttpPost httpPost = new HttpPost(PAY_URL);
			httpPost.setEntity(new StringEntity(xml, "utf-8"));
			String result = HttpUtil.executePOST(HttpUtil.defaultClient(), httpPost, 1, new HttpClientContext());
			log.debug("resut is {}", result);
			Map<String, String> resultMap = xml2Map(result);
			if ("SUCCESS".equals(resultMap.get("return_code")) && "SUCCESS".equals(resultMap.get("result_code"))) {
				return resultMap;
			}
		}
		catch (DocumentException e) {
			log.error(e.getMessage(), e);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	static Map<String, String> xml2Map(String xml) throws DocumentException, UnsupportedEncodingException {
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(new ByteArrayInputStream(xml.getBytes("utf-8")));
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		Map<String, String> map = Maps.newHashMap();
		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());
		return map;
	}

	/**
	 * 生成签名
	 * @param params
	 * @return
	 */
	public String getSign(Map<String, String> params) {
		StringBuilder signBuilder = new StringBuilder();
		List<String> kl = Lists.newArrayList(params.keySet());
		Collections.sort(kl);
		boolean isBegin = true;
		for (String key : kl) {
			if (isBegin) {
				isBegin = false;
				signBuilder.append(key).append("=").append(params.get(key));
			}
			else {
				signBuilder.append("&").append(key).append("=").append(params.get(key));
			}
		}
		signBuilder.append("&key=" + wechatConfig.getPay().getKey());

		return DigestUtil.MD5(signBuilder.toString()).toUpperCase();
	}

	public static void main(String[] args) {
		System.out.println(RandomStringUtils.random(32, true, true));
	}

}
