package com.baipengx.coin.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.baipengx.coin.model.IcoItemsWithBLOBs;

import lombok.Data;

@Data
public class IcoItemsPageVo {
	private Long id;

	private String icon;

	private String status;

	private String beginDate;

	private String name;

	private String title;

	private Long starNum;

	private Boolean isStared;

	private String tempStatus;
	
	private String remark;
	
	private Long rank;

	private Boolean isHot;
	
	public IcoItemsPageVo(IcoItemsWithBLOBs item) {
		Date now = new Date();
		this.id = item.getId();
		this.icon = item.getIcon();
		this.status = item.getBeginTime() == null ? "待定"
				: item.getBeginTime().before(now) && item.getEndTime().after(now) ? "进行中"
						: item.getEndTime().before(now) ? "已结束"
								: (item.getBeginTime().getTime() - now.getTime()) / (24 * 60 * 60 * 1000) == 0
										? (item.getBeginTime().getTime() - now.getTime()) / (60 * 60 * 1000) == 0
												? (item.getBeginTime().getTime() - now.getTime()) / (60 * 1000) + "分"
												: (item.getBeginTime().getTime() - now.getTime()) / (60 * 60 * 1000)
														+ "小时"
										: (item.getBeginTime().getTime() - now.getTime()) / (24 * 60 * 60 * 1000)
												+ "天后";
		this.beginDate = item.getBeginTime() == null? "" : new SimpleDateFormat("MM月dd日").format(item.getBeginTime());
		this.name = item.getName();
		this.title = item.getTitle();
		this.starNum = item.getStarNum();
		this.tempStatus = item.getTempStatus();
		this.remark = item.getRemark();
		this.rank = item.getRank();
		this.isHot = item.getIsHot();
	}
}
