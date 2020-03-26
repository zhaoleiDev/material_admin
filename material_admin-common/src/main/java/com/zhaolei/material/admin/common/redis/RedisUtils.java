package com.zhaolei.material.admin.common.redis;

import com.zhaolei.material.admin.common.tools.ApplicationContextUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 根据需求添加对应的jedis方法
 * @author ZHAOLEI
 */
public class RedisUtils {

    private static JedisPool jedisPool;

    static{
        jedisPool = ((RedisCli) ApplicationContextUtils.getContext().getBean("redisCli")).getJedisPool();
    }

    private static Jedis getJedis(){
        try{
            return jedisPool.getResource();
        }catch(Exception e){
            throw new RedisRuntimeException("获取jedis失败",e);
        }

    }
    public static String get(String key){
        return getJedis().get(key);
    }

    public static String set(String key,String value){
        return getJedis().set(key,value);
    }


    public static String set(byte[] key,byte[] value){
        return getJedis().set(key,value);
    }
    public static String set(String key,String value,Integer ex){
        return getJedis().setex(key,ex,value);
    }



}
