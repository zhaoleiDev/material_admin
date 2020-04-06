package com.zhaolei.material.admin.common.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author ZHAOLEI
 */
@Component
public class RedisCli {

    /**
     * 服务器地址
     */
    @Value("${ali.redis.host}")
    private  String host;
    /**
     * 服务器端口
     */
    @Value("${ali.redis.port}")
    private  int port;
    /**
     * 最大连接数
     */
    @Value("${ali.redis.maxTotal}")
    private int maxTotal;
    /**
     * 最大连接空闲数
     */
    @Value("${ali.redis.maxIdle}")
    private int maxIdle;

    /**
     * 最大阻塞时间，超出时间后抛出异常
     */
    @Value("${ali.redis.maxWaitMillis}")
    private int maxWaitMillis;

    /**
     * 当引入redis实例时检查其是否有效
     */
    @Value("${ali.redis.testOnBorrow}")
    private boolean testOnBorrow;

    /**
     * //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
     //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
     * config.setMaxTotal(50);
     *
     * //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
     * config.setMaxIdle(5);
     *
     * //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；单位毫秒
     * //小于零:阻塞不确定的时间,  默认-1
     * config.setMaxWaitMillis(1000*100);
     *
     * //在borrow(引入)一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
     * config.setTestOnBorrow(true);
     *
     * //return 一个jedis实例给pool时，是否检查连接可用性（ping()）
     * config.setTestOnReturn(true);
     *
     * @return
     */

    public JedisPool getJedisPool(){
        try{
            JedisPoolConfig config = new JedisPoolConfig();
            // 最大连接数
            config.setMaxTotal(maxTotal);
            // 最大连接空闲数
            config.setMaxIdle(maxIdle);
            //最大等待时间
            config.setMaxWaitMillis(maxWaitMillis);
            /*引入redis实例时检查其是否可用
            若开启,每一次获取redis实例时会先ping一下redis,才进行命令的发送，并且出现了有get命令直接返回PONG
            config.setTestOnBorrow(testOnBorrow);*/
            return new JedisPool(config, host, port);
        }catch(Exception e){
            throw new RedisRuntimeException("获取jedispool失败",e);
        }
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }
}