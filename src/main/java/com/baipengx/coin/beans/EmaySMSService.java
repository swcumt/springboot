package com.baipengx.coin.beans;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baipengx.coin.config.gotoken.SMSConfig;
import com.baipengx.common.BaseLogger;
import com.baipengx.util.AES;
import com.baipengx.util.GZIPUtils;
import com.baipengx.util.HttpUtil;
import com.baipengx.util.JsonUtil;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;

@Component
public class EmaySMSService extends BaseLogger{
	@Autowired
	private SMSConfig config;
	
	/**
	 * 
	 * @param content
	 * @param phone
	 * @param customerId
	 * @return
	 * @throws Exception
	 */
	public String sendSingleSMS(String content, String phone, String customerId) throws Exception{
		Map<String, Object> params = Maps.newHashMap();
		params.put("mobile", phone);
		params.put("content", content);
		params.put("customSmsId", customerId);
		params.put("requestTime", System.currentTimeMillis());
		params.put("requestValidPeriod", 30);
		
		String res = sendRequest(JsonUtil.toJson(params), "/inter/sendSingleSMS");
		
		Map<String, String> resMap = JsonUtil.json2Obj(res, new TypeToken<Map<String, String>>() {
		}.getType());
		
		return resMap.get("smsId");
	}
	
	
	/**
	 * 
	 * @param contentJson
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	public String  sendRequest(String contentJson, String uri) throws Exception{
		HttpPost post = new HttpPost(config.getHost() + uri);
		post.setHeader("appId", config.getAppid());
		post.setHeader("gzip", "on");
		
		byte[] body = contentJson.getBytes("UTF-8");
		body = GZIPUtils.compress(body);
		body = AES.encrypt(body, config.getSecretKey().getBytes(), config.getAlgorithm());
		post.setEntity(new ByteArrayEntity(body));
		HttpResponse response = HttpUtil.defaultClient().execute(post);
		if(!(200 == response.getStatusLine().getStatusCode())){
			throw new Exception(String.format("%s get response status is not 200", config.getHost() + uri));
		}
		String result = response.getFirstHeader("result").getValue();
		if(!"SUCCESS".equals(result)){
			throw new EmayException(result, String.format("%s get response Header result is ", config.getHost() + uri, result));
		}
		
		InputStream is = response.getEntity().getContent();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		int len = 0;
		byte[] b = new byte[1024];
		while((len = is.read(b ))!=-1){
			os.write(b, 0, len);
		}
		byte[] resByte = os.toByteArray();
		resByte = AES.decrypt(resByte, config.getSecretKey().getBytes(), config.getAlgorithm());
		resByte = GZIPUtils.decompress(resByte);
		
		return new String(resByte, "UTF-8");
	}
	
	
	public static class EmayException extends RuntimeException{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1123311612087759069L;
		
		private String errCode;
		
		public EmayException() {
			super();
		}
		
		public EmayException(String errCode, String msg){
			super(msg);
			this.setErrCode(errCode);
		}

		public String getErrCode() {
			return errCode;
		}

		public void setErrCode(String errCode) {
			this.errCode = errCode;
		}
	}
}
