package com.example.DistributedCacheTest;

import com.example.DistributedCacheTest.enums.EvictionPolicyEnum;
import com.example.DistributedCacheTest.model.eviction.EvictionPolicy;
import com.example.DistributedCacheTest.model.eviction.FIFOEvictionPolicy;
import com.example.DistributedCacheTest.model.eviction.LRUEvictionPolicy;
import com.example.DistributedCacheTest.model.storage.IStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CacheManager<K, V> {
    private IStorage<K, V> storage;
    private Long cacheSize = 10L;
    private EvictionPolicy<K> evictionPolicy;

    private static final Semaphore lock = new Semaphore(1, true);

    public CacheManager(){

    }

    public CacheManager(EvictionPolicyEnum evictionPolicy, IStorage<K,V> storage){
        this.storage = storage;
        this.evictionPolicy = getEvictionPolicy(evictionPolicy);
    }

    public CacheManager(EvictionPolicyEnum evictionPolicy, IStorage<K,V> storage, Long cacheSize){
        this.storage = storage;
        this.evictionPolicy = getEvictionPolicy(evictionPolicy);
        this.cacheSize = cacheSize;
    }

    private EvictionPolicy<K> getEvictionPolicy(EvictionPolicyEnum evictionPolicyEnum) {
        switch (evictionPolicyEnum) {
            case LRU :
                return new LRUEvictionPolicy<>();
            case FIFO :
                return new FIFOEvictionPolicy<>();
            default :
                log.error("Eviction policy supported");
        }
        return null;
    }

    public V get(K key){
        try {
            if (lock.tryAcquire(5, TimeUnit.SECONDS))
                evictionPolicy.updateAccessedKey(key);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.release();
        }
        return storage.get(key);
    }
    public V put(K key, V value){
        V result = null;
        try {
            if(lock.tryAcquire(5, TimeUnit.SECONDS)){
                evictionPolicy.updateAccessedKey(key);
                evictionPolicy.displayAccessSequence();
                if (storage.getSize() > cacheSize) {
                    K evictKey = evictionPolicy.evictKey();
                    storage.removeKey(evictKey);
                }
                result = storage.put(key, value);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.release();
        }
        return result;
    }
    public Long getCacheSize(){
        return this.cacheSize;
    }
}
