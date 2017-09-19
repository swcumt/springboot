package com.baipengx.coin.api.coinmarketcap;

import java.io.File;

import javax.annotation.PostConstruct;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.baipengx.common.BaseLogger;
import com.baipengx.util.HttpUtil;

@Component
public class CoinMarketCapSpider extends BaseLogger{
	private static final String IMAGE_URL = "https://files.coinmarketcap.com/static/img/coins/64x64/ID.png";
	private static final String DETAIL_URL = "http://coinmarketcap.com/currencies/ID/";
	@Value("${coins.icon.dir}")
	private String iconDir;
	
	private  static final String SUFFIX = ".png";
	public String getIconBase64(String id){
		String filePath = iconDir + id + SUFFIX;
		File icon = new File(filePath);
		
		if(icon.exists()){
			return filePath;
		}
		
		String url = IMAGE_URL.replace("ID", id);
		try {
			HttpUtil.executeGetISAsBase64(HttpUtil.defaultClient(), new HttpGet(url), 1, new HttpClientContext(), filePath);
			return filePath;
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	public String getMaxSupply(String id){
		String url = DETAIL_URL.replace("ID", id);
		String html = null;
		try {
			html = HttpUtil.executeGet(HttpUtil.defaultClient(), new HttpGet(url), 1, new HttpClientContext());
			Document doc = Jsoup.parse(html);
			Elements maxEles = doc.getElementsContainingOwnText("Max Supply");
			if(maxEles == null || maxEles.isEmpty())
				return null;
			Element goalEle = maxEles.get(0).parent().siblingElements().get(0);
			String value = goalEle.ownText();
			
			return value;
			//return Long.valueOf(value.replaceAll("\\D", ""));
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	@PostConstruct
	public void init(){
		File file = new File(iconDir);
		if(!file.exists()){
			file.mkdirs();
		}
	}
}
