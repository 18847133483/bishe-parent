package utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author itheima
 * @Title: JedisUtils
 * @ProjectName gossip_spider_parent
 * @Description: jedis连接池
 * @date 2019/1/814:37
 */
public class JedisUtils {

    private static JedisPool jedisPool = null;
    private static  final  String host = "127.0.0.1";
    private static final  int port = 6379;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxIdle(20);
        jedisPool = new JedisPool(jedisPoolConfig,host, port);

    }


    /**
     * 获取jedis连接对象，用完要关闭close
     * @return
     */
    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
}
