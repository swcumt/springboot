package com.baipengx.coin.controller;

import com.baipengx.coin.config.wechat.WechatConfig;
import com.baipengx.coin.vo.ResponseVo;
import com.baipengx.coin.wechat.util.MessageUtil;
import com.baipengx.coin.wechat.util.WechatPayUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author siwei
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/pay")
public class PayController extends BaseController {
	private static final String PACKAGE = "prepay_id=PREPAY_ID";
	@Autowired
	private WechatPayUtil payUtil;
	@Autowired
	private WechatConfig config;
	

	@RequestMapping(value="/topay/{type}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo toPay(HttpServletRequest request){
		Integer fee = 1000;
		String reportUrl = getRootPath(request) + "/pay/report";
		String orderNo = "xxxxxxxxxx";
		String openId = "xxasdwglkorto";
		Map<String, String> map = payUtil.createOrder("xxxxxxxxxxxxxxxx", "", openId, request.getHeader("X-Real-IP"), orderNo, fee, reportUrl);
		Map<String, String> h5Map = Maps.newHashMap();
		h5Map.put("appId", config.getAppid());
		h5Map.put("timeStamp", "" + System.currentTimeMillis());
		h5Map.put("nonceStr", map.get("nonce_str"));
		h5Map.put("package", PACKAGE.replace("PREPAY_ID", map.get("prepay_id")));
		h5Map.put("signType", "MD5");
		h5Map.put("paySign", payUtil.getSign(h5Map));
		h5Map.put("orderNo", orderNo);

		vo.setData(h5Map);
		
		return vo;
	}
	

	/**
	 * 接收微信支付 报告
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/report", method=RequestMethod.POST)
	public void report(HttpServletRequest request, HttpServletResponse response){
        // 1、读取传入信息并转换为map
        Map<String, String> map = Maps.newHashMap();
		try {
			map = MessageUtil.parseXml(request);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
        log.debug("pay report map is {}", map);
        // 2、克隆传入的信息并进行验签
        Map<String, String> signMap = (Map<String, String>) ((HashMap<String, String>) map).clone();
        signMap.remove("sign");
        String sign = payUtil.getSign(signMap);
        if (!sign.equals(map.get("sign"))) {
            log.error( "微信支付回调函数：验签错误 , request sign is {}, my sign is {}", map.get("sign"), sign);
            return;
        }
        // 信息处理
        String result_code = map.get("result_code");
        try {

            if ("SUCCESS".equals(result_code)) {
                //由于微信后台会同时回调多次，所以需要做防止重复提交操作的判断
                //此处放防止重复提交操作

            } else if ("FAIL".equals(result_code)) {
            	log.debug("{} reported fail", map.get("out_trade_no"));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return;
        }
        try {
           //进行业务逻辑操作

        } catch (Exception e) {
            e.printStackTrace();
            log.error("回调用户中心错误", e);
        }


        // 返回信息，防止微信重复发送报文
        String result = "<xml>"
                + "<return_code><![CDATA[SUCCESS]]></return_code>"
                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml>";
       write(response, result);
	}
}
