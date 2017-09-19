package com.baipengx.coin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baipengx.coin.beans.EmaySMSService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoinMarketsApplicationTests {
	@Autowired
	private EmaySMSService emayService;
	@Test
	public void contextLoads() throws Exception {
		emayService.sendSingleSMS("【GoToken】您关注的项目:(ITEM_NAME),将在一天后BEGIN_TIME开启ICO,请提前做好准备.更多ICO一手信息,请持续关注<GoToken>.", "15313961195", "20170623");
	}

}
