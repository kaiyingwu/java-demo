package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Proxy;

/**
 * redis配置类
 *
 * @author zhouwen
 * @date 2019/10/27 7:58 下午
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private Integer timeout;

    @Value("${spring.redis.max-idle}")
    private Integer maxIdle;

    @Value("${spring.redis.min-idle}")
    private Integer minIdle;

    @Value("${spring.redis.max-wait}")
    private Integer maxWait;

    @Value("${spring.redis.max-active}")
    private Integer maxActive;

    @Bean
    public JedisPool getPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxTotal(maxActive);
        config.setMaxWaitMillis(maxWait);
        return new JedisPool(config, host, port, timeout, password);
    }

    @Bean
    public JedisCommands getClient() {
        return (JedisCommands) Proxy.newProxyInstance(JedisCommands.class.getClassLoader(),
                new Class[] {JedisCommands.class},
                new JedisCommandsHandler(getPool()));
    }

}
