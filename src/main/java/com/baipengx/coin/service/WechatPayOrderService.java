package com.baipengx.coin.service;

import com.baipengx.coin.model.WechatPayOrder;

public interface WechatPayOrderService {
	public String getOrderNo(String type);

	/**
	 * 
	 * @param orderNo
	 * @param fee
	 * @param string
	 * @param type
	 */
	public void createOrder(String orderNo, Integer fee, String prepayId, String type, Long userid);
	
	WechatPayOrder getByOrderNo(String orderNo);

	public void updateReport(WechatPayOrder order, String url);

	public void updatePayed(String orderNo, String url);

	public WechatPayOrder findByActivityUser(Long userid);
}
