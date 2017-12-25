package com.baipengx.coin.wechat.message.template.request;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BusinessProcessData {
	private static final DateFormat df = new SimpleDateFormat("yyyy年M月d日 HH:mm");
	private DataDetail first;

	private DataDetail keyword1;

	private DataDetail keyword2;

	private DataDetail keyword3;

	private DataDetail keyword4;

	private DataDetail remark;

	public BusinessProcessData() {
//		this.first = new DataDetail("你好，淘宝指数提醒如下：", "#173177");
//		this.keyword2 = new DataDetail("淘宝指数", "#173177");
//		this.keyword3 = new DataDetail("淘宝指数查询完成", "#173177");
//		this.remark = new DataDetail("您的淘宝指数已完成查询，点击查看具体指数，感谢您的使用！", "#173177");
	}

	public DataDetail getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = new DataDetail(first, "#173177");
	}

	public DataDetail getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(String keyword1) {
		this.keyword1 = new DataDetail(keyword1, "#173177");
	}

	public void setKeyword2(String keyword2) {
		this.keyword2 = new DataDetail(keyword2, "#173177");
	}

	public void setKeyword3(String keyword3) {
		this.keyword3 = new DataDetail(keyword3, "#173177");
	}

	public void setKeyword4(java.util.Date now) {
		this.keyword4 = new DataDetail(df.format(now), "#173177");
	}

	public void setRemark(String remark) {
		this.remark = new DataDetail(remark, "#173177");
	}

	public DataDetail getKeyword2() {
		return keyword2;
	}

	public DataDetail getKeyword3() {
		return keyword3;
	}

	public DataDetail getKeyword4() {
		return keyword4;
	}

	public DataDetail getRemark() {
		return remark;
	}
}
