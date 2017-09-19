package com.baipengx.coin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baipengx.coin.dao.OrderSuffixPoolMapper;
import com.baipengx.coin.model.OrderSuffixPool;
import com.baipengx.coin.model.OrderSuffixPoolCriteria;
import com.baipengx.coin.service.OrderSuffixPoolService;
import com.baipengx.common.BaseLogger;


@Service
public class OrderSuffixPoolServiceImpl extends BaseLogger implements OrderSuffixPoolService{

	@Autowired
	private OrderSuffixPoolMapper poolMapper;
	
	@Override
	@Transactional
	public synchronized String updateSuffix() {
		OrderSuffixPool pool = get();
		int placeNum = pool.getPlaceNum();
		Long suffix = pool.getOrderSuffix();
		pool.setOrderSuffix(suffix + 1);
		char[] suffixChar = suffix.toString().toCharArray();
		if(suffixChar.length > placeNum){
			pool.setPlaceNum(++placeNum);
		}
		char[] orderSuffix = new char[placeNum];
		int j = suffixChar.length - 1;
		
		for(int i = placeNum -1; i >= 0; i--){
			if(j < 0){
				orderSuffix[i] = '0';
			}else{
				orderSuffix[i] = suffixChar[j--];
			}
		}
		poolMapper.updateByPrimaryKey(pool);
		return new String(orderSuffix);
	}

	@Override
	public OrderSuffixPool get() {
		List<OrderSuffixPool> list = poolMapper.selectByExample(new OrderSuffixPoolCriteria());
		return list.get(0);
	}

}
