package com.baipengx.coin.wechat.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baipengx.coin.wechat.menu.Button;
import com.baipengx.coin.wechat.menu.Menu;
import com.baipengx.coin.wechat.menu.ViewButton;
import com.baipengx.coin.wechat.vo.Token;
import com.google.common.collect.Lists;

import net.sf.json.JSONObject;

/**
 * 自定义菜单工具类
 * @author siwei
 */
public class MenuUtil {
	private static Logger log = LoggerFactory.getLogger(MenuUtil.class);

	// 菜单创建（POST）
	public final static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 菜单查询（GET）
	public final static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	// 菜单删除（GET）
	public final static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	/**
	 * 创建菜单
	 * 
	 * @param menu 菜单实例
	 * @param accessToken 凭证
	 * @return true成功 false失败
	 */
	public static boolean createMenu(Menu menu, String accessToken) {
		boolean result = false;
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 发起POST请求创建菜单
		JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST", jsonMenu);
		
		if (null != jsonObject) {
			int errorCode = jsonObject.getInt("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
			} else {
				result = false;
				log.error("创建菜单失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}

		return result;
	}

	/**
	 * 查询菜单
	 * 
	 * @param accessToken 凭证
	 * @return
	 */
	public static String getMenu(String accessToken) {
		String result = null;
		String requestUrl = menu_get_url.replace("ACCESS_TOKEN", accessToken);
		// 发起GET请求查询菜单
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			result = jsonObject.toString();
		}
		return result;
	}

	/**
	 * 删除菜单
	 * 
	 * @param accessToken 凭证
	 * @return true成功 false失败
	 */
	public static boolean deleteMenu(String accessToken) {
		boolean result = false;
		String requestUrl = menu_delete_url.replace("ACCESS_TOKEN", accessToken);
		// 发起GET请求删除菜单
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			int errorCode = jsonObject.getInt("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
			} else {
				result = false;
				log.error("删除菜单失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		Token token = CommonUtil.getToken("wx8cf58e2a0ed2d8ff", "aa9b357aa4c0a17463148e9f9180b2d5");
		Menu menu = new Menu();
		ViewButton vb = new ViewButton();
		vb.setName("百信分");
		vb.setType("view");
		vb.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ "wx8cf58e2a0ed2d8ff" +"&redirect_uri=http://wx.daikuantoutiao.com/wechat/oauthed&response_type=code&scope=snsapi_userinfo&state=2017#wechat_redirect");
		List<Button> set = Lists.newArrayList(vb);
		ViewButton loanBt = new ViewButton();
		loanBt.setName("贷款超市");
		loanBt.setType("view");
		loanBt.setUrl("http://wx.daikuantoutiao.com/loan/");
		set.add(loanBt);
		ViewButton hongbaoBt = new ViewButton();
		hongbaoBt.setName("问卷红包");
		hongbaoBt.setType("view");
		hongbaoBt.setUrl("https://sojump.com/jq/14258294.aspx");
		set.add(hongbaoBt);
//		ClickButton clickBt = new ClickButton();
//		clickBt.setKey("advice");
//		clickBt.setName("提建议");
//		clickBt.setType("click");
//		set.add(clickBt);
		
		menu.setButton(set);
		createMenu(menu, token.getAccessToken());
		
		System.out.println(getMenu(token.getAccessToken()));
	}
}