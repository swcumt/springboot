package com.baipengx.coin.wechat.menu;

import java.util.Set;

public class ComplexButton extends Button{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6737104619531377871L;

	private Set<Button> subButton;

	public Set<Button> getSubButton() {
		return subButton;
	}

	public void setSubButton(Set<Button> subButton) {
		this.subButton = subButton;
	}
}
