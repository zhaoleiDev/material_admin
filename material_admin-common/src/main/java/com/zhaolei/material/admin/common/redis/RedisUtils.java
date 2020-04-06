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
    private static Jedis getJedis(){
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis;
        } catch (Exception e) {
            throw new RedisRuntimeException("获取jedis失败", e);
        }
    }

    public static String get(String key){
        //使用 try with resource 编译后会自动调用close方法
        try(Jedis jedis = jedisPool.getResource() ) {
            return jedis.get(key);
        } catch (Exception e) {
            throw new RedisRuntimeException("redis操作失败", e);
        }
    }

    public static String set(String key,String value){
        try(Jedis jedis = jedisPool.getResource() ) {
            return jedis.set(key,value);
        } catch (Exception e) {
            throw new RedisRuntimeException("redis操作set失败", e);
        }
    }


    public static String set(byte[] key,byte[] value){
        try(Jedis jedis = jedisPool.getResource() ) {
            return jedis.set(key,value);
        } catch (Exception e) {
            throw new RedisRuntimeException("redis操作set失败", e);
        }
    }
    public static String setRandomEx(String key, String value, Integer ex){
        int min = 50;
        int max = 100;
        Random random = new Random();
        //防止缓存雪崩，在存活时间后加一个随机值
        ex = ex + random.nextInt(min)%(max-min+1)+min;
        try(Jedis jedis = jedisPool.getResource() ) {
            return jedis.setex(key,ex,value);
        } catch (Exception e) {
            throw new RedisRuntimeException("redis操作setRandomEx失败", e);
        }
    }
    public static String setex(String key, Integer ex,String value){
        try(Jedis jedis = jedisPool.getResource() ) {
            return jedis.setex(key,ex,value);
        } catch (Exception e) {
            throw new RedisRuntimeException("redis操作setex失败", e);
        }
    }

    public static void  del(String key){
        try(Jedis jedis = jedisPool.getResource() ) {
            jedis.del(key);
        } catch (Exception e) {
            throw new RedisRuntimeException("redis操作del失败", e);
        }
    }



}
