package com.zhaolei.material.admin.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author ZHAOLEI
 */
@Service
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

    public Jedis getJedis() {
        JedisPoolConfig config = new JedisPoolConfig();
        // 最大连接数
        config.setMaxTotal(maxTotal);
        // 最大连接空闲数
        config.setMaxIdle(maxIdle);
        JedisPool jedisPool = new JedisPool(config, host, port);
        try{

            return jedisPool.getResource();
        }catch(Exception e){
            e.printStackTrace();
            return null;
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