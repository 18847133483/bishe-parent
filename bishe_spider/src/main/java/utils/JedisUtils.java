package utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {
    private static JedisPool jedisPool = null;
    private static final String host = "127.0.0.1";
    private static final int port = 6379;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxIdle(20);
        jedisPool = new JedisPool(jedisPoolConfig, host, port);

    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
}
