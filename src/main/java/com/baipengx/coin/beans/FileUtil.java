package com.baipengx.coin.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.baipengx.common.BaseLogger;

@Component
public class FileUtil extends BaseLogger {
	@Value("${gotoken.ico.iconpath}")
	private String icoIconDir;

	@PostConstruct
	public void init() {
		File icoIcon = new File(icoIconDir);
		if (!icoIcon.exists()) {
			icoIcon.mkdirs();
		}
	}

	public String saveImgFromBase64(String imgStr) {
		if(StringUtils.isEmpty(imgStr)){
			return null;
		}
		imgStr = imgStr.replaceFirst("data:[a-zA-Z/]*;base64,", "");
		Decoder decoder = Base64.getDecoder();
		try {
			// Base64解码
			byte[] b = decoder.decode(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".png";
			String imgFilePath = icoIconDir + fileName;// 新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return fileName;
		}
		catch (Exception e) {
			log.error("base64 转图片出错: {}", e);
			return null;
		}
	}
}
