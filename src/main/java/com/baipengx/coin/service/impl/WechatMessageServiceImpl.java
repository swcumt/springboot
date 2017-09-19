package com.baipengx.coin.service.impl;


import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baipengx.coin.dao.WechatMessageMapper;
import com.baipengx.coin.model.WechatMessage;
import com.baipengx.coin.model.WechatMessageCriteria;
import com.baipengx.coin.service.WechatMessageService;
import com.baipengx.common.BaseLogger;


@Service
public class WechatMessageServiceImpl extends BaseLogger implements WechatMessageService {
	public static final String NOT_SEND_STATUS = "Not Send";
	//Not Send：未发送，Success:发送成功，Fail：发送失败，unsubscribe：取消订阅
	public static final String  SUCCESS_SEND_STATUS = "Success";
	
	public static final String FAIL_SEND_STATUS = "Fail";
	
	public static final String UNSUB_SEND_STATUS = "unsubscribe";
	@Autowired
	private WechatMessageMapper msgMapper;
	@Override
	@Transactional
	public void saveMessage(WechatMessage msg) {
		msg.setCreateDateTime(new Date());
		msg.setStatus(NOT_SEND_STATUS);
		
		msgMapper.insertSelective(msg);
	}
	@Override
	public List<WechatMessage> query2send() {
		WechatMessageCriteria example = new WechatMessageCriteria();
		example.createCriteria().andStatusEqualTo(NOT_SEND_STATUS);
		return msgMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(0, 500));
	}
	
	@Override
	@Transactional
	public void updateWechatMessage(WechatMessage msg) {
		msgMapper.updateByPrimaryKeySelective(msg);
	}
}
