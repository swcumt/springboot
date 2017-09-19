package com.baipengx.coin.vo;

import lombok.Data;

@Data
public class ResponseVo {
	private Integer errcode = 0;
	private String errmsg;
	private Object data;
}
