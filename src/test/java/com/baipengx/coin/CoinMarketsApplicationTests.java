package com.baipengx.coin;

import com.baipengx.coin.wechat.message.template.request.BaseTemplateMessage;
import com.baipengx.coin.wechat.message.template.request.BusinessProcessData;
import com.baipengx.coin.wechat.message.template.request.TestMessage;
import com.baipengx.coin.wechat.service.SendTemplateMessageUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoinMarketsApplicationTests {
//	@Autowired
//	private EmaySMSService emayService;
//	@Test
//	public void contextLoads() throws Exception {
//		emayService.sendSingleSMS("【GoToken】您关注的项目:(ITEM_NAME),将在一天后BEGIN_TIME开启ICO,请提前做好准备.更多ICO一手信息,请持续关注<GoToken>.", "15313961195", "20170623");
//	}

    @Resource
    private SendTemplateMessageUtil util;

    @Test
    public void test(){
        BaseTemplateMessage testMsg = new TestMessage();
        testMsg.setTouser("oDCZNuCvcumftLeHB4qyJ08PbmAU");
        testMsg.setUrl("http://d497a0f2.ngrok.io");
        BusinessProcessData data = new BusinessProcessData();
        data.setFirst("hello");
        data.setKeyword1("eat");
        data.setKeyword2("lunch");
        data.setKeyword3("egg");
        data.setKeyword4(new Date());
        data.setRemark("模版消息发送成功了");
        testMsg.setData(data);
        util.sendMsg(testMsg);
    }
}
