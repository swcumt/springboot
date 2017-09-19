package com.baipengx.coin.wechat.menu;

import java.io.Serializable;

public class Button implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3369503507103521404L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
