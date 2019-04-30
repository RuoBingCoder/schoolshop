package com.shop.o2o.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.SafeEncoder;

/**
 * @author : 石建雷
 * @date :2019/4/29
 */

public class JedisUtil {
    /**
     * 操作key的方法
     */
    public Keys KEYS;
    /**
     * 对string的操作
     */
    public Strings STRINGS;
    /**
     * redis连接池对象
     */
    public JedisPool jedisPool;

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    /**
     * 设置Redis连接池
     *
     * @param jedisPoolWriper
     */
    public void setJedisPool(JedisPoolWriper jedisPoolWriper) {
        this.jedisPool = jedisPoolWriper.getJedisPool();
    }

    /**
     * 从jedis连接池中获取jedis对象
     *
     * @return
     */
    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public class Keys {


        /**
         * 判断key是否存在
         *
         * @param  key
         * @return boolean
         */
        public boolean exists(String key) {
            // ShardedJedis sjedis = getShardedJedis();
            Jedis sjedis = getJedis();
            boolean exis = sjedis.exists(key);
            sjedis.close();
            return exis;
        }
    }

    public class Strings {

        /**
         * 添加记录,如果记录已存在将覆盖原有的value
         *
         * @param  key
         * @param  value
         * @return 状态码
         */

        public String set(String key, String value) {
            return set(SafeEncoder.encode(key), SafeEncoder.encode(value));
        }

        /**
         * 添加记录,如果记录已存在将覆盖原有的value
         *
         * @param  key
         * @param  value
         * @return 状态码
         */
        public String set(String key, byte[] value) {
            return set(SafeEncoder.encode(key), value);
        }

        /**
         * 添加记录,如果记录已存在将覆盖原有的value
         *
         * @param key
         * @param  value
         * @return 状态码
         */
        public String set(byte[] key, byte[] value) {
            Jedis jedis = getJedis();
            String status = jedis.set(key, value);
            jedis.close();
            return status;
        }

        /**
         * 根据key获取记录
         *
         * @param  key
         * @return 值
         */
        public String get(String key) {
            // ShardedJedis sjedis = getShardedJedis();
            Jedis sjedis = getJedis();
            String value = sjedis.get(key);
            sjedis.close();
            return value;
        }
    }
}

/**
 * public class Lists {
 * <p>
 * public Lists(String s) {
 * <p>
 * }
 * }
 * <p>
 * public class Sets {
 * public Sets(String s) {
 * }
 * }
 * <p>
 * public class Hash {
 * public Hash(String s) {
 * }
 * }
 */

