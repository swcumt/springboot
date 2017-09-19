package com.baipengx.coin.wechat.message.template.request;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CoinPriceChangeData {
	private static final DateFormat df = new SimpleDateFormat("yyyy年M月d日 HH:mm:ss");
	private static final String FIRST_CONTENT = "您好，NAME目前价格为￥PRICE_CNY（$PRICE_USD ） ,涨幅已达CHNAGE%";

	private static String COLOR = "#173177";
	
	private DataDetail first;

	private DataDetail keyword1;

	private DataDetail keyword2;

	private DataDetail remark;

	public CoinPriceChangeData() {
		this.remark = new DataDetail("更多货币价格点击这里查看", COLOR);	
	}
	
	public DataDetail getFirst() {
		return first;
	}

	public void setFirst(String name, BigDecimal priceCny, BigDecimal priceUsd, BigDecimal change) {
		this.first = new DataDetail(FIRST_CONTENT.replace("NAME", name).replace("PRICE_CNY", priceCny.setScale(2, BigDecimal.ROUND_HALF_UP).toString()).replace("PRICE_USD", priceUsd.setScale(2, BigDecimal.ROUND_HALF_UP).toString()).replace("CHNAGE", change.setScale(2, BigDecimal.ROUND_HALF_UP).toString()), COLOR);
	}

	public DataDetail getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(String symbol) {
		this.keyword1 = new DataDetail(symbol, COLOR);
	}

	public DataDetail getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(long timestamp) {
		this.keyword2 = new DataDetail(df.format(new Date(timestamp * 1000)), COLOR);
	}

	public DataDetail getRemark() {
		return remark;
	}

	public void setRemark(DataDetail remark) {
		this.remark = remark;
	}
	
	
}
