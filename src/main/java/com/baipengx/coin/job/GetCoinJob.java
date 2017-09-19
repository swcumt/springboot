package com.baipengx.coin.job;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baipengx.coin.api.coinmarketcap.CoinMarketCapService;
import com.baipengx.coin.api.coinmarketcap.CoinMarketCapSpider;
import com.baipengx.coin.api.coinmarketcap.vo.CoinVo;
import com.baipengx.coin.api.coinmarketcap.vo.GlobalVo;
import com.baipengx.coin.model.Coin;
import com.baipengx.coin.model.GlobalInfo;
import com.baipengx.coin.service.CoinPushService;
import com.baipengx.coin.service.CoinService;
import com.baipengx.coin.service.GlobalInfoService;
import com.baipengx.coin.wechat.message.template.request.CoinPriceChangeData;
import com.baipengx.common.BaseLogger;
import com.baipengx.util.JsonUtil;

@Component
public class GetCoinJob extends BaseLogger {
	private static final String CNY = "CNY";

	private static final String BTC = "BTC";

	@Autowired
	private CoinService coinService;

	@Autowired
	private CoinMarketCapSpider spider;

	@Autowired
	private CoinMarketCapService capService;

	@Autowired
	private GlobalInfoService globalService;
	
	@Autowired
	private CoinPushService pushService;

	@Scheduled(fixedDelay = 5 * 60 * 1000 )
	public void exec() {
		log.warn("get coin job start");
		List<CoinVo> coinCnyList = capService.getCoinList(CNY);
		List<CoinVo> coinBtcList = capService.getCoinList(BTC);

		// 封装GI
		GlobalVo globalCny = capService.getGlobalInfo(CNY);
		GlobalVo globalBtc = capService.getGlobalInfo(BTC);
		for (CoinVo cv : coinCnyList) {
			for (CoinVo bv : coinBtcList) {
				if (cv.getId().equals(bv.getId())) {
					cv.setMarket_cap_btc(bv.getMarket_cap_btc());
					cv.setPrice_btc(bv.getPrice_btc());
					cv.setVolume_btc_24h(bv.getVolume_btc_24h());
					coinBtcList.remove(bv);
					break;
				}
			}
			Coin coin = cv.toModel();
			if (!coinService.hasImageAndMax(cv.getId())) {
				// 抓取图标和 max supply
				coin.setIcon(spider.getIconBase64(cv.getId()));
				coin.setMaxSupply(spider.getMaxSupply(cv.getId()));
			}
			coinService.save(coin);
/*			if ((coin.getRank() < 6 && coin.getPercentChange24h().abs().compareTo(new BigDecimal(5)) > 0)
					|| (coin.getRank() > 5 && coin.getRank() < 21
							&& coin.getPercentChange24h().abs().compareTo(new BigDecimal(20)) > 0)) {
	
				CoinPriceChangeData data = new CoinPriceChangeData();
				data.setFirst(coin.getSymbol(), coin.getPriceCny(), coin.getPriceUsd(), coin.getPercentChange24h());
				data.setKeyword1(coin.getSymbol());
				data.setKeyword2(coin.getLastUpdated());
				
				pushService.createPush(coin.getId(), JsonUtil.toJson(data));
			}*/
		}

		GlobalInfo gi = globalCny.toModel();
		gi.setTotal24hVolumeBtc(globalBtc.getTotal_24h_volume_btc());
		gi.setTotalMarketCapBtc(globalBtc.getTotal_market_cap_btc());

		globalService.save(gi);
		log.warn("get coin job end");
	}
}
