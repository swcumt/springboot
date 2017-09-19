package com.baipengx.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESDecodeUtils {
	public static void main(String[] args) {

		String content = "g25";
		String pwd = "8182838485";
		String addPwd = "123456";

		// 加密
		System.out.println("加密前content：" + content);
		byte[] enAccount = encrypt(content, addPwd);
		byte[] enPwd = encrypt(pwd, addPwd);
		String encryptResultStr = parseByte2HexStr(enAccount);
		String parseByte2HexStr2 = parseByte2HexStr(enPwd);
		System.out.println("加密后content：" + encryptResultStr);

		// 解密 ——账号/身份证号
		byte[] accountFrom = AESDecodeUtils.parseHexStr2Byte(encryptResultStr);
		byte[] deAccount = AESDecodeUtils.decrypt(accountFrom, addPwd);
		String userAccount = new String(deAccount);
		System.out.println("解密后content：" + userAccount);
		// 解密——密码
		byte[] pwdFrom = AESDecodeUtils.parseHexStr2Byte(parseByte2HexStr2);
		byte[] deUserPwd = AESDecodeUtils.decrypt(pwdFrom, addPwd);
		String userPwd = new String(deUserPwd);
		// System.out.println(userPwd);
	}

	/**
	 * AES加密
	 * 
	 * @param content
	 *            要加密的内容
	 * @param password
	 *            加密使用的密钥
	 * @return 加密后的字节数组
	 */
	public static byte[] encrypt(String content, String password) {
		SecureRandom random = null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}

		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			// kgen.init(128, new SecureRandom(password.getBytes()));
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将二进制转换成16进制 加密
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String password) {
		SecureRandom random = null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			// kgen.init(128, new SecureRandom(password.getBytes()));
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
}