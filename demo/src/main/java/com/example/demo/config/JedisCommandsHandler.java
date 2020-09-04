package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 采用动态代理来进行redis操作，因为要使用jedisPool
 *
 * @author zhouwen
 * @date 2019/10/27 10:25 下午
 */
@Slf4j
public class JedisCommandsHandler implements InvocationHandler {

    private JedisPool jedisPool;

    public JedisCommandsHandler(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        String name = method.getName();
        Jedis jedis = jedisPool.getResource();
        try {
            Method targetMethod;
            if (args == null) {
                targetMethod = Jedis.class.getMethod(name);
            } else {
                targetMethod = Jedis.class.getMethod(name, getClass(args));
            }
            return targetMethod.invoke(jedis, args);
        } catch (Exception e) {
            log.error("redis invoke {} method error.", name, e);
        } finally {
            jedis.close();
        }
        return null;
    }

    public static Class<?>[] getClass(Object[] args) {
        Class<?>[] res = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            Class<?> clazz = args[i].getClass();
            if (clazz.equals(Long.class)) {
                res[i] = Long.TYPE;
            } else if (clazz.equals(Double.class)) {
                res[i] = Double.TYPE;
            } else if (clazz.equals(Integer.class)) {
                res[i] = Integer.TYPE;
            } else if (clazz.equals(HashMap.class)) {
                res[i] = Map.class;
            } else {
                res[i] = args[i].getClass();
            }
        }
        return res;
    }
}
