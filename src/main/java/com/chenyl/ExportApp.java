package com.chenyl;

import redis.clients.jedis.Jedis;

/**
 * Hello world!
 *
 */
public class ExportApp
{
    public static void main( String[] args )
    {
        Jedis jedis=RedisFactory.getRedisInstance();
        Jedis shardJedis = RedisFactory2.getRedisInstance();
        jedis.hscan("poi",0).getResult().forEach(e->{
            System.out.println(e.getValue());
        });
        shardJedis.close();
        jedis.close();
        System.out.println("finish");
    }

}
