package com.baipengx.coin.wechat.vo;

import lombok.Data;

/**
 * {
"errcode":0,
"errmsg":"ok",
"ticket":"bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA",
"expires_in":7200
}
 * @author siwei
 *
 */
@Data
public class Ticket {
	
	private Integer errcode;
	private String errmsg;
	private String ticket;
	private Integer expires_in;
}
