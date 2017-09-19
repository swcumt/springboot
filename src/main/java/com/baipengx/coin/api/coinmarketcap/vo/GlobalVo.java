package com.baipengx.coin.api.coinmarketcap.vo;

import java.math.BigDecimal;

import com.baipengx.coin.model.GlobalInfo;

import lombok.Data;
/**
 * {
    "total_market_cap_usd": 101908425443,
    "total_24h_volume_usd": 3568841259,
    "bitcoin_percentage_of_market_cap": 45.48,
    "active_currencies": 745,
    "active_assets": 121,
    "active_markets": 4046,
    "total_market_cap_btc": 36011394,
    "total_24h_volume_btc": 1261122
}
 * @author siwei
 *
 */
@Data
public class GlobalVo {
	private BigDecimal total_market_cap_usd;
	private BigDecimal total_24h_volume_usd;
	private BigDecimal total_market_cap_cny;
	private BigDecimal total_24h_volume_cny;
	private BigDecimal total_market_cap_btc;
	private BigDecimal total_24h_volume_btc;
	private BigDecimal bitcoin_percentage_of_market_cap;
	private Integer active_currencies;
	private Integer active_assets;
	private Integer active_markets;
	
	public GlobalInfo toModel(){
		GlobalInfo gi = new GlobalInfo();
		gi.setActiveAssets(this.active_assets);
		gi.setActiveCurrencies(this.active_currencies);
		gi.setActiveMarkets(this.active_markets);
		gi.setBitcoinPercentageOfMarketCap(this.bitcoin_percentage_of_market_cap);
		gi.setTotal24hVolumeBtc(this.total_24h_volume_btc);
		gi.setTotal24hVolumeCny(this.total_24h_volume_cny);
		gi.setTotal24hVolumeUsd(this.total_24h_volume_usd);
		gi.setTotalMarketCapBtc(this.total_market_cap_btc);
		gi.setTotalMarketCapCny(this.total_market_cap_cny);
		gi.setTotalMarketCapUsd(this.total_market_cap_usd);

		return gi;
	}
}
