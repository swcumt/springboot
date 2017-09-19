package com.baipengx.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Json 转换工具
 * @author siwei
 *
 */
public class JsonUtil {
	private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:MM:ss").create();

	public static <T> T json2Obj(String json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}

	public static <T> T json2Obj(String json, Type type) {
		return gson.fromJson(json, type);
	}

	public static String toJson(Object o) {
		if (o == null)
			return null;
		return gson.toJson(o);
	}
}
