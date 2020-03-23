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

    public JedisPool getJedisPool(){
        try{
            JedisPoolConfig config = new JedisPoolConfig();
            // 最大连接数
            config.setMaxTotal(maxTotal);
            // 最大连接空闲数
            config.setMaxIdle(maxIdle);
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