package com.wm.shirodemo.shiro.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;


/**
 * 自定义shiro缓存管理器
 */
@Slf4j
public class RedisCacheManager implements CacheManager {

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new RedisCache<K,V>(s);
    }

}
