package com.baipengx.coin.wechat.message.template.request;

import lombok.Data;

/**
 * {{first.DATA}}
活动名称：{{keyword1.DATA}}
活动时间：{{keyword2.DATA}}
活动地点：{{keyword3.DATA}}
{{remark.DATA}}
 * @author siwei
 *
 */
@Data
public class ActivityNotifyData {
	private static String COLOR = "#173177";
	
	private DataDetail first;
	private DataDetail keyword1;
	private DataDetail keyword2;
	private DataDetail keyword3;
	private DataDetail remark;
	
	
	public ActivityNotifyData(String ticket) {
		this.first = new DataDetail(String.format("您好,您已经成功购买%s门票,请您准时参加", ticket), COLOR);
		this.keyword1 = new DataDetail("区块链数字资产峰会(Token Economy Summit)", COLOR);
		this.keyword2 = new DataDetail("2017-9-17  早上8:00", COLOR);
		this.keyword3 = new DataDetail("上海市中心酒店 二层大宴会三厅", COLOR);
		this.remark = new DataDetail("现场请出示二维码。请点击查看", COLOR);
	}
}
