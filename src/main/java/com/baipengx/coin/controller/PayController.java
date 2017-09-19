package com.baipengx.coin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baipengx.coin.config.wechat.WechatConfig;
import com.baipengx.coin.controller.views.ActivityUserController;
import com.baipengx.coin.model.ActivityUser;
import com.baipengx.coin.model.WechatPayOrder;
import com.baipengx.coin.service.ActivityUserService;
import com.baipengx.coin.service.WechatPayOrderService;
import com.baipengx.coin.vo.ResponseVo;
import com.baipengx.coin.wechat.util.MessageUtil;
import com.baipengx.coin.wechat.util.WechatPayUtil;
import com.google.common.collect.Maps;

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
	private WechatPayOrderService orderService;
	@Autowired
	private WechatPayUtil payUtil;
	@Autowired
	private WechatConfig config;
	
	@Autowired
	private ActivityUserService auserService;
	
	private static final Map<String, Integer> PRICE_MAP = Maps.newHashMap();
	private static final  Map<String, String> TICKET_NAME = Maps.newHashMap();
	static{
		//'vip package','vip','normal'
		TICKET_NAME.put("vip package", "V_I_P套餐票");
		TICKET_NAME.put("vip", "V_I_P门票");
		TICKET_NAME.put("normal", "普通门票");

		PRICE_MAP.put("normal", 999 * 100);
		PRICE_MAP.put("vip", 2199 * 100);
		PRICE_MAP.put("vip package", 2999 * 100);
	}
	
	
	
	@RequestMapping(value="/topay/{type}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo toPay(HttpServletRequest request, @PathVariable String type, @RequestBody ActivityUser activityUser){
		Integer fee = PRICE_MAP.get(activityUser.getType());
		if(fee == null){
			throw new RuntimeException("门票类型不对,非法调用");
		}
		activityUser.setFee(fee);
		activityUser.setWechatId(getCurrentAuthed(request).getId());
		activityUser.setAdId((Integer)getSession(request, ActivityUserController.AD_KEY));
		auserService.createNew(activityUser);
		String reportUrl = getRootPath(request) + "/pay/report";
		String orderNo = orderService.getOrderNo(type);
		Map<String, String> map = payUtil.createOrder("GoToken", String.format("GoToken区块链数字资产峰会%s", TICKET_NAME.get(activityUser.getType())), getCurrentAuthed(request).getOpenid(), request.getHeader("X-Real-IP"), orderNo, fee, reportUrl);
		Map<String, String> h5Map = Maps.newHashMap();
		h5Map.put("appId", config.getAppid());
		h5Map.put("timeStamp", "" + System.currentTimeMillis());
		h5Map.put("nonceStr", map.get("nonce_str"));
		h5Map.put("package", PACKAGE.replace("PREPAY_ID", map.get("prepay_id")));
		h5Map.put("signType", "MD5");
		h5Map.put("paySign", payUtil.getSign(h5Map));
		h5Map.put("orderNo", orderNo);
		orderService.createOrder(orderNo, fee, map.get("prepay_id"),type, activityUser.getId());
		
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
            	String orderNo = map.get("out_trade_no");
            	WechatPayOrder order = orderService.getByOrderNo(orderNo);
            	if(order == null){
            		log.error("{} 未找到该订单", orderNo);
            	}else if(order.getIsReported()){
            		log.debug("{} 已经  reproted", orderNo);
            	}else{
            		order.setBankType(map.get("bank_type"));
            		order.setTimeEnd(map.get("time_end"));
            		order.setTradeType(map.get("trade_type"));
            		order.setTransactionId(map.get("transaction_id"));
            		orderService.updateReport(order, getRootPath(request) + "/activity/myticket/" + orderNo);
            	}

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
	
	
	@RequestMapping(value = "/payed/{orderNo}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo payed(@PathVariable String orderNo, HttpServletRequest request){
		WechatPayOrder order = orderService.getByOrderNo(orderNo);
		orderService.updatePayed(orderNo, getRootPath(request) + "/activity/myticket/" + orderNo);
		return vo;
	}
}
