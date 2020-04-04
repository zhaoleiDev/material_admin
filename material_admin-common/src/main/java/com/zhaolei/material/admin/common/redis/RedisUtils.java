package com.zhaolei.material.admin.common.redis;

import com.zhaolei.material.admin.common.tools.ApplicationContextUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Random;

/**
 * 根据需求添加对应的jedis方法
 * @author ZHAOLEI
 */
public class RedisUtils {

    private static JedisPool jedisPool;

    static{
        jedisPool = ((RedisCli) ApplicationContextUtils.getContext().getBean("redisCli")).getJedisPool();
    }
//======================================需要释放资源===========================================
    private static Jedis getJedis(){
        Jedis jedis = null;
        try{
            jedis= jedisPool.getResource();
            return jedis;
        }catch(Exception e) {
            throw new RedisRuntimeException("获取jedis失败", e);
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
    public static String setRandomEx(String key, String value, Integer ex){
        int min = 50;
        int max = 100;
        Random random = new Random();
        //防止缓存雪崩，在存活时间后加一个随机值
        ex = ex + random.nextInt(min)%(max-min+1)+min;
        return getJedis().setex(key,ex,value);
    }
    public static String setex(String key, Integer ex,String value){
        return getJedis().setex(key,ex,value);
    }

    public static void  del(String key){
        getJedis().del(key);
    }



}
