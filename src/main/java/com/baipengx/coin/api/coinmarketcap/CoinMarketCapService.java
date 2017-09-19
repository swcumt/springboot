package com.baipengx.coin.api.coinmarketcap;

import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.springframework.stereotype.Component;

import com.baipengx.coin.api.coinmarketcap.vo.CoinVo;
import com.baipengx.coin.api.coinmarketcap.vo.GlobalVo;
import com.baipengx.common.BaseLogger;
import com.baipengx.util.HttpUtil;
import com.baipengx.util.JsonUtil;
import com.google.common.reflect.TypeToken;

@Component
public class CoinMarketCapService extends BaseLogger{
	private static final String COIN_URL = "https://api.coinmarketcap.com/v1/ticker/?convert=CURRENCY";
	private static final String GLOBAL_URL = "https://api.coinmarketcap.com/v1/global/?convert=CURRENCY";
	
	
	public List<CoinVo> getCoinList(String currency){
		String url = COIN_URL.replace("CURRENCY", currency);
		try {
			String result = HttpUtil.executeGet(HttpUtil.defaultClient(), new HttpGet(url), 1, new HttpClientContext());
			List<CoinVo> list = JsonUtil.json2Obj(result, new TypeToken<List<CoinVo>>() {
			}.getType());
			return list;
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	public GlobalVo getGlobalInfo(String currency){
		String url = GLOBAL_URL.replace("CURRENCY", currency);
		try {
			String result = HttpUtil.executeGet(HttpUtil.defaultClient(), new HttpGet(url), 1, new HttpClientContext());
			return JsonUtil.json2Obj(result, GlobalVo.class);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
}
