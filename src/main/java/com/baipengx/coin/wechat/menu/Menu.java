package com.baipengx.coin.wechat.menu;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author siwei
 *
 */
public class Menu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6905113404398565017L;

	
	List<Button> button;


	public List<Button> getButton() {
		return button;
	}


	public void setButton(List<Button> button) {
		this.button = button;
	}

	
}
