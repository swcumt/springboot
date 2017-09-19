package com.baipengx.coin.api.coinmarketcap.vo;
/**
 * 
 * @author siwei
 *	{
    "id": "litecoin",
    "name": "Litecoin",
    "symbol": "LTC",
    "rank": "6",
    "price_usd": "30.4799",
    "price_btc": "0.0107343",
    "24h_volume_usd": "280267000.0",
    "market_cap_usd": "1568093544.0",
    "available_supply": "51446807.0",
    "total_supply": "51446807.0",
    "percent_change_1h": "-0.24",
    "percent_change_24h": "-0.91",
    "percent_change_7d": "28.13",
    "last_updated": "1496816041",
    "price_cny": "207.097692223",
    "24h_volume_cny": "1904292629.12",
    "market_cap_cny": "10654515079.0"
}
 */

import java.math.BigDecimal;

import com.baipengx.coin.model.Coin;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CoinVo {
	private String id;
	private String name;
	private String symbol;
	private Integer rank;
	private BigDecimal price_usd;
	private BigDecimal price_btc;
	private BigDecimal price_cny;
	@SerializedName("24h_volume_usd")
	private BigDecimal volume_usd_24h;
	@SerializedName("24h_volume_cny")
	private BigDecimal volume_cny_24h;
	@SerializedName("24h_volume_btc")
	private BigDecimal volume_btc_24h;
	
	private BigDecimal market_cap_usd;
	private BigDecimal market_cap_cny;
	private BigDecimal market_cap_btc;
	
	private BigDecimal available_supply;
	
	private BigDecimal total_supply;
	
	private BigDecimal percent_change_1h;
	private BigDecimal percent_change_7d;
	private BigDecimal percent_change_24h;
	private Long last_updated;
	
	public Coin toModel(){
		Coin coin = new Coin();
		coin.setId(this.id);
		coin.setAvailableSupply(this.available_supply);
		coin.setLastUpdated(this.last_updated);
		coin.setMacketCapBtc(this.market_cap_btc);
		coin.setMacketCapCny(this.market_cap_cny);
		coin.setMacketCapUsd(this.market_cap_usd);
		coin.setName(this.name);
		coin.setPercentChange1h(this.percent_change_1h);
		coin.setPercentChange24h(this.percent_change_24h);
		coin.setPercentChange7d(this.percent_change_7d);
		coin.setPriceBtc(this.price_btc);
		coin.setPriceCny(this.price_cny);
		coin.setPriceUsd(this.price_usd);
		coin.setRank(this.rank);
		coin.setSymbol(this.symbol);
		coin.setTotalSupply(this.total_supply);
		coin.setVolumeBtc24h(this.volume_btc_24h);
		coin.setVolumeCny24h(this.volume_cny_24h);
		coin.setVolumeUsd24h(this.volume_usd_24h);
		return coin;
	}
}
