package com.mall.sso.redis;

/**
 * @description: redis 相关操作接口
 * 接口的意义是配合单机版和集群版两种实现，由spring选择负责注入
 * @author: Jingzeng Wang
 * @date: created in 2017/10/23 8:32
 * @modified By:
 */
public interface JedisClient {

    /**
     * 获得key对应的value值
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 设置key-value
     *
     * @param key
     * @param value
     * @return 设置的value
     */
    String set(String key, String value);

    /**
     * 获得redis hash数据类型的值
     *
     * @param hkey
     * @param key  hash键
     * @return
     */
    String hget(String hkey, String key);

    /**
     * 设置hash类型数据
     *
     * @param hkey
     * @param key
     * @param value
     * @return
     */
    long hset(String hkey, String key, String value);

    /**
     * 自增
     *
     * @param key
     * @return
     */
    long incr(String key);

    /**
     * 设置过期时间
     *
     * @param key
     * @param second
     * @return
     */
    long expire(String key, int second);

    /**
     * 查看过期时间
     *
     * @param key
     * @return
     */
    long ttl(String key);

    /**
     * 删除key-value键值对
     *
     * @param key
     * @return
     */
    long del(String key);

    /**
     * 删除hash类型的数据
     *
     * @param hkey
     * @param key
     * @return
     */
    long hdel(String hkey, String key);

}
