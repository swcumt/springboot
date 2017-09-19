package com.baipengx.coin.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baipengx.coin.dao.WechatPayOrderMapper;
import com.baipengx.coin.model.WechatPayOrder;
import com.baipengx.coin.model.WechatPayOrderCriteria;
import com.baipengx.coin.service.ActivityUserService;
import com.baipengx.coin.service.OrderSuffixPoolService;
import com.baipengx.coin.service.WechatPayOrderService;
import com.baipengx.common.BaseLogger;

@Service
public class WechatPayOrderServiceImpl extends BaseLogger implements WechatPayOrderService {
	private static final DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

	private static final String PREFIX = "BPX";

	@Autowired
	private OrderSuffixPoolService poolService;

	@Autowired
	private WechatPayOrderMapper orderMapper;

	@Autowired
	private ActivityUserService auserService;

	@Override
	public String getOrderNo(String type) {
		Date now = new Date();

		return PREFIX + type + df.format(now) + poolService.updateSuffix();
	}

	@Override
	@Transactional
	public void createOrder(String orderNo, Integer fee, String prepayId, String type, Long userid) {
		WechatPayOrder payOrder = new WechatPayOrder();
		payOrder.setCreateDateTime(new Date());
		payOrder.setFee(fee);
		payOrder.setIsPayed(false);
		payOrder.setIsReported(false);
		payOrder.setPrepayId(prepayId);
		payOrder.setOrderNo(orderNo);
		payOrder.setOrderType(type);
		payOrder.setUserId(userid);
		orderMapper.insertSelective(payOrder);
	}

	@Override
	public WechatPayOrder getByOrderNo(String orderNo) {
		WechatPayOrderCriteria example = new WechatPayOrderCriteria();
		example.createCriteria().andOrderNoEqualTo(orderNo);
		List<WechatPayOrder> list = orderMapper.selectByExample(example);
		if (list == null || list.size() == 0)
			return null;
		return list.get(0);
	}

	@Override
	@Transactional
	public void updateReport(WechatPayOrder order, String url) {
		order.setReportTime(new Date());
		order.setIsPayed(true);
		order.setIsReported(true);
		orderMapper.updateByPrimaryKeySelective(order);
		auserService.updatePayed(order.getId(), url);
	}

	@Override
	@Transactional
	public void updatePayed(String orderNo, String url) {
		WechatPayOrder order = getByOrderNo(orderNo);
		order.setUpdateDateTime(new Date());
		order.setIsPayed(true);
		orderMapper.updateByPrimaryKeySelective(order);

		auserService.updatePayed(order.getUserId(), url);
	}

	@Override
	public WechatPayOrder findByActivityUser(Long userid) {
		WechatPayOrderCriteria example = new WechatPayOrderCriteria();
		example.createCriteria().andUserIdEqualTo(userid).andIsPayedEqualTo(true).andOrderTypeEqualTo("ACT");

		List<WechatPayOrder> list = orderMapper.selectByExample(example);

		return list != null && list.size() > 0 ? list.get(0) : null;
	}

}
