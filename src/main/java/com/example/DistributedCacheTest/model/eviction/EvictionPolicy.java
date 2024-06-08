package com.example.DistributedCacheTest.model.eviction;

public interface EvictionPolicy<K> {
    void updateAccessedKey(K key);
    K evictKey();
    void displayAccessSequence();
}
