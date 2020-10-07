package com.wm.shirodemo.shiro.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * 自定义redis缓存实现
 * 实现ApplicationContextAware接口,setApplicationContext方法在spring容器初始化完成后执行,可获取初始化完成后的spring容器
 * @param <K>
 * @param <V>
 */
@Slf4j
@Component
public class RedisCache<K,V> implements Cache<K,V>, ApplicationContextAware {

    private static ApplicationContext context = null;

//    缓存名称--将整个shiro权限以hash模型来存储
    private String cacheName;

    public RedisCache() {
    }

    public RedisCache(String cacheName) {
        this.cacheName = cacheName;
    }

    @Override
    public V get(K k) throws CacheException {
        log.info("redis get k:" + k);
        return (V)getRedisTemplate().opsForHash().get(this.cacheName, k.toString());
    }

    @Override
    public V put(K k, V v) throws CacheException {
        log.info("redis put k:" + k);
        log.info("redis put v:" + v);
        getRedisTemplate().opsForHash().put(this.cacheName, k.toString(), v);
        return null;
    }

    @Override
    public V remove(K k) throws CacheException {
        return (V) getRedisTemplate().opsForHash().delete(this.cacheName, k.toString());
    }

    @Override
    public void clear() throws CacheException {
        getRedisTemplate().delete(this.cacheName);
    }

    @Override
    public int size() {
        return getRedisTemplate().opsForHash().size(this.cacheName).intValue();
    }

    @Override
    public Set<K> keys() {
        return getRedisTemplate().opsForHash().keys(this.cacheName);
    }

    @Override
    public Collection<V> values() {
        return getRedisTemplate().opsForHash().values(this.cacheName);
    }

    /**
     * 设置key的序列化及HashKey的序列化
     * @return
     */
    private RedisTemplate getRedisTemplate(){
        RedisTemplate redisTemplate = (RedisTemplate) getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 获取spring容器中的bean
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName){
        return context.getBean(beanName);
    }

}
