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

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public BigDecimal getPrice_usd() {
		return price_usd;
	}

	public void setPrice_usd(BigDecimal price_usd) {
		this.price_usd = price_usd;
	}

	public BigDecimal getPrice_btc() {
		return price_btc;
	}

	public void setPrice_btc(BigDecimal price_btc) {
		this.price_btc = price_btc;
	}

	public BigDecimal getPrice_cny() {
		return price_cny;
	}

	public void setPrice_cny(BigDecimal price_cny) {
		this.price_cny = price_cny;
	}

	public BigDecimal getVolume_usd_24h() {
		return volume_usd_24h;
	}

	public void setVolume_usd_24h(BigDecimal volume_usd_24h) {
		this.volume_usd_24h = volume_usd_24h;
	}

	public BigDecimal getVolume_cny_24h() {
		return volume_cny_24h;
	}

	public void setVolume_cny_24h(BigDecimal volume_cny_24h) {
		this.volume_cny_24h = volume_cny_24h;
	}

	public BigDecimal getVolume_btc_24h() {
		return volume_btc_24h;
	}

	public void setVolume_btc_24h(BigDecimal volume_btc_24h) {
		this.volume_btc_24h = volume_btc_24h;
	}

	public BigDecimal getMarket_cap_usd() {
		return market_cap_usd;
	}

	public void setMarket_cap_usd(BigDecimal market_cap_usd) {
		this.market_cap_usd = market_cap_usd;
	}

	public BigDecimal getMarket_cap_cny() {
		return market_cap_cny;
	}

	public void setMarket_cap_cny(BigDecimal market_cap_cny) {
		this.market_cap_cny = market_cap_cny;
	}

	public BigDecimal getMarket_cap_btc() {
		return market_cap_btc;
	}

	public void setMarket_cap_btc(BigDecimal market_cap_btc) {
		this.market_cap_btc = market_cap_btc;
	}

	public BigDecimal getAvailable_supply() {
		return available_supply;
	}

	public void setAvailable_supply(BigDecimal available_supply) {
		this.available_supply = available_supply;
	}

	public BigDecimal getTotal_supply() {
		return total_supply;
	}

	public void setTotal_supply(BigDecimal total_supply) {
		this.total_supply = total_supply;
	}

	public BigDecimal getPercent_change_1h() {
		return percent_change_1h;
	}

	public void setPercent_change_1h(BigDecimal percent_change_1h) {
		this.percent_change_1h = percent_change_1h;
	}

	public BigDecimal getPercent_change_7d() {
		return percent_change_7d;
	}

	public void setPercent_change_7d(BigDecimal percent_change_7d) {
		this.percent_change_7d = percent_change_7d;
	}

	public BigDecimal getPercent_change_24h() {
		return percent_change_24h;
	}

	public void setPercent_change_24h(BigDecimal percent_change_24h) {
		this.percent_change_24h = percent_change_24h;
	}

	public Long getLast_updated() {
		return last_updated;
	}

	public void setLast_updated(Long last_updated) {
		this.last_updated = last_updated;
	}
}
