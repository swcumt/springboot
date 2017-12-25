package com.baipengx.coin.jedis.impl;

import com.baipengx.coin.jedis.JedisDao;
import com.baipengx.common.BaseLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;


@Repository("stringJedis")
public class JedisDaoImpl extends BaseLogger implements JedisDao<String, String> {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(final String key, final String value, final Long expire) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        valueOps.set(key, value);
        if (expire != null)
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();

        return valueOps.get(key);
    }

}
