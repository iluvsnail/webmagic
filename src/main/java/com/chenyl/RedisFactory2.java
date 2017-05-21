package com.chenyl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * redis
 *
 * @author guqiong
 */
public class RedisFactory2 {
    private static final JedisPoolConfig config = new JedisPoolConfig();
    private static JedisPool pool = null;
    private static void init() {
        config.setMaxIdle(200);
        config.setMaxTotal(5120);
        config.setMaxWaitMillis(5000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        String ip = "in.gfire.cn";
        int port = 6379;
        pool = new JedisPool(config, ip, port, 60000);
    }
    public static synchronized Jedis getRedisInstance() {
        Jedis jedis = null;
        if (pool == null) {
            init();
        }
        boolean borrowOrOprSuccess = true;
        try {
            jedis = pool.getResource();
        } catch (JedisConnectionException e) {
            e.printStackTrace();
            borrowOrOprSuccess = false;
            if (jedis != null) {
                jedis.close();
                ;
            }
            e.printStackTrace();
        } finally {
            if (borrowOrOprSuccess) {
                //pool.returnResource(jedis);
            }
        }
        return jedis;
    }
    private static JedisPool getPool() {
        return pool;
    }
    public static void revokeRedis(Jedis jedis) {
        pool.returnResource(jedis);
    }
}