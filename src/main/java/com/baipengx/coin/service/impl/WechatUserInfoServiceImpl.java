package com.baipengx.coin.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baipengx.coin.dao.WechatUserInfoMapper;
import com.baipengx.coin.model.WechatUserInfo;
import com.baipengx.coin.model.WechatUserInfoCriteria;
import com.baipengx.coin.service.WechatUserInfoService;
import com.baipengx.common.BaseLogger;

@Service
public class WechatUserInfoServiceImpl extends BaseLogger implements WechatUserInfoService {

	@Autowired
	private WechatUserInfoMapper wechatUserMapper;

	@Override
	@Transactional
	public com.baipengx.coin.model.WechatUserInfo save(WechatUserInfo wechat) {
		com.baipengx.coin.model.WechatUserInfo wu = queryByOpenid(wechat.getOpenid());

		if (wu == null) {
			// 新增记录
			wechat.setCreateDateTime(new Date());
			String uuid = UUID.randomUUID().toString();
			wechat.setUuid(uuid);
			wechatUserMapper.insertSelective(wechat);
			wu = queryByUUID(uuid);
		}
		else if (wechat.getIsSubscribed()) {
			// 更新记录
			wu.setIsSubscribed(true);
			wu.setSubscribeTime(wechat.getSubscribeTime());
			wu.setUpdateDateTime(new Date());
			wechatUserMapper.updateByPrimaryKeySelective(wu);
		}
		else {
			// pass
			log.warn("未关注用户再次 登录, user info is {}", wechat);
		}

		return wu;
	}

	@Override
	public boolean needGetWechatUserInfo(String openid) {
		com.baipengx.coin.model.WechatUserInfo wu = queryByOpenid(openid);

		if (wu == null)
			return true;
		return !wu.getIsSubscribed();
	}

	@Override
	public com.baipengx.coin.model.WechatUserInfo queryByOpenid(String openId) {
		WechatUserInfoCriteria example = new WechatUserInfoCriteria();
		example.createCriteria().andOpenidEqualTo(openId);
		List<com.baipengx.coin.model.WechatUserInfo> list = wechatUserMapper.selectByExample(example);

		if (list == null || list.size() == 0)
			return null;

		com.baipengx.coin.model.WechatUserInfo wu = list.get(0);
		return wu;
	}

	@Override
	public WechatUserInfo queryByUUID(String uuid) {
		WechatUserInfoCriteria example = new WechatUserInfoCriteria();
		example.createCriteria().andUuidEqualTo(uuid);
		List<com.baipengx.coin.model.WechatUserInfo> list = wechatUserMapper.selectByExample(example);

		if (list == null || list.size() == 0)
			return null;

		com.baipengx.coin.model.WechatUserInfo wu = list.get(0);
		return wu;
	}

	@Override
	@Transactional
	public void update2UnSubscribe(String openId) {
		WechatUserInfoCriteria example = new WechatUserInfoCriteria();
		example.createCriteria().andOpenidEqualTo(openId).andIsSubscribedEqualTo(true);
		
		WechatUserInfo wui = new WechatUserInfo();
		wui.setIsSubscribed(false);
		wui.setUpdateDateTime(new Date());
		
		wechatUserMapper.updateByExampleSelective(wui, example);
	}

	@Override
	@Transactional
	public void reminded(Long id) {
		WechatUserInfo wui = new WechatUserInfo();
		wui.setIsReminded(true);
		wui.setId(id);
		wui.setUpdateDateTime(new Date());
		
		wechatUserMapper.updateByPrimaryKeySelective(wui);
	}

	
}
