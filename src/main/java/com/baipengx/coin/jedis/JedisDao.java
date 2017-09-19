package com.baipengx.coin.jedis;

public interface JedisDao <K,V>{
	void save(K key, V value, Long expire);
	
	V get(K key);
}
