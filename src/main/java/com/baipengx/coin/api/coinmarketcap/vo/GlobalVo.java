package com.baipengx.coin.api.coinmarketcap.vo;

import java.math.BigDecimal;
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

    public BigDecimal getTotal_market_cap_usd() {
        return total_market_cap_usd;
    }

    public void setTotal_market_cap_usd(BigDecimal total_market_cap_usd) {
        this.total_market_cap_usd = total_market_cap_usd;
    }

    public BigDecimal getTotal_24h_volume_usd() {
        return total_24h_volume_usd;
    }

    public void setTotal_24h_volume_usd(BigDecimal total_24h_volume_usd) {
        this.total_24h_volume_usd = total_24h_volume_usd;
    }

    public BigDecimal getTotal_market_cap_cny() {
        return total_market_cap_cny;
    }

    public void setTotal_market_cap_cny(BigDecimal total_market_cap_cny) {
        this.total_market_cap_cny = total_market_cap_cny;
    }

    public BigDecimal getTotal_24h_volume_cny() {
        return total_24h_volume_cny;
    }

    public void setTotal_24h_volume_cny(BigDecimal total_24h_volume_cny) {
        this.total_24h_volume_cny = total_24h_volume_cny;
    }

    public BigDecimal getTotal_market_cap_btc() {
        return total_market_cap_btc;
    }

    public void setTotal_market_cap_btc(BigDecimal total_market_cap_btc) {
        this.total_market_cap_btc = total_market_cap_btc;
    }

    public BigDecimal getTotal_24h_volume_btc() {
        return total_24h_volume_btc;
    }

    public void setTotal_24h_volume_btc(BigDecimal total_24h_volume_btc) {
        this.total_24h_volume_btc = total_24h_volume_btc;
    }

    public BigDecimal getBitcoin_percentage_of_market_cap() {
        return bitcoin_percentage_of_market_cap;
    }

    public void setBitcoin_percentage_of_market_cap(BigDecimal bitcoin_percentage_of_market_cap) {
        this.bitcoin_percentage_of_market_cap = bitcoin_percentage_of_market_cap;
    }

    public Integer getActive_currencies() {
        return active_currencies;
    }

    public void setActive_currencies(Integer active_currencies) {
        this.active_currencies = active_currencies;
    }

    public Integer getActive_assets() {
        return active_assets;
    }

    public void setActive_assets(Integer active_assets) {
        this.active_assets = active_assets;
    }

    public Integer getActive_markets() {
        return active_markets;
    }

    public void setActive_markets(Integer active_markets) {
        this.active_markets = active_markets;
    }
}
