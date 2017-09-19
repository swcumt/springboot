package com.baipengx.coin.wechat.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baipengx.coin.wechat.vo.Ticket;
import com.baipengx.coin.wechat.vo.Token;
import com.baipengx.coin.wechat.vo.WechatApiUser;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 通用工具类
 * 
 * @author siwei
 */
public class CommonUtil {
	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

	private static String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	// 凭证获取（GET）
	public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// 获取用户基本信息
	public final static String user_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	/**
	 * 发送https请求
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		}
		catch (ConnectException ce) {
			log.error("连接超时：{}", ce);
		}
		catch (Exception e) {
			log.error("https请求异常：{}", e);
		}
		return jsonObject;
	}

	/**
	 * 获取接口访问凭证
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static Token getToken(String appid, String appsecret) {
		Token token = null;
		String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		// 发起GET请求获取凭证
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				token = new Token();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
			}
			catch (JSONException e) {
				token = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return token;
	}

	/**
	 * 
	 * @param accessToken
	 * @return
	 */
	public static Ticket getTicket(String accessToken){
		String url = TICKET_URL .replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = httpsRequest(url, "GET", null);
		Ticket ticket = null;
		if (null != jsonObject) {
			try {
				ticket = new Ticket();
				ticket.setExpires_in(jsonObject.getInt("expires_in"));
				ticket.setTicket(jsonObject.getString("ticket"));
			}
			catch (JSONException e) {
				ticket = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return ticket;
	}
	/**
	 * 获取用户基本信息
	 * @param accessToken
	 * @param openid
	 * @return
	 * 
	 * { "subscribe": 1, "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M", "nickname": "Band", "sex": 1, "language": "zh_CN",
	 * "city": "广州", "province": "广东", "country": "中国", "headimgurl":
	 * "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4
	 * eMsv84eavHiaiceqxibJxCfHe/0", "subscribe_time": 1382694957, "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL" "remark":
	 * "", "groupid": 0, "tagid_list":[128,2] }
	 */
	public static WechatApiUser getUser(String accessToken, String openid) {
		WechatApiUser wau = null;
		String requestUrl = user_url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				wau = new WechatApiUser();
				wau.setSubscribe(jsonObject.getInt("subscribe"));
				if (wau.getSubscribe() == 1) {
					wau.setCity(jsonObject.getString("city"));
					wau.setOpenid(jsonObject.getString("openid"));
					wau.setNickname(jsonObject.getString("nickname"));
					wau.setSex(jsonObject.getInt("sex"));
					wau.setLanguage(jsonObject.getString("language"));
					wau.setProvince(jsonObject.getString("province"));
					wau.setCountry(jsonObject.getString("country"));
					wau.setHeadimgurl(jsonObject.getString("headimgurl"));
					wau.setSubscribe_time(jsonObject.getLong("subscribe_time"));
//					wau.setUnionid(jsonObject.getString("unionid"));  //只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
					wau.setRemark(jsonObject.getString("remark"));
					wau.setGroupid(jsonObject.getInt("groupid"));
				}
			}
			catch (JSONException e) {
				wau = null;
				log.error(e.getMessage(), e);
				// 获取token失败
				log.error("获取微信用户信息失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return wau;
	}

	/**
	 * URL编码（utf-8）
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据内容类型判断文件扩展名
	 * 
	 * @param contentType 内容类型
	 * @return
	 */
	public static String getFileExt(String contentType) {
		String fileExt = "";
		if ("image/jpeg".equals(contentType))
			fileExt = ".jpg";
		else if ("audio/mpeg".equals(contentType))
			fileExt = ".mp3";
		else if ("audio/amr".equals(contentType))
			fileExt = ".amr";
		else if ("video/mp4".equals(contentType))
			fileExt = ".mp4";
		else if ("video/mpeg4".equals(contentType))
			fileExt = ".mp4";
		return fileExt;
	}
}