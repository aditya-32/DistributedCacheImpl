package com.example.DistributedCacheTest.model.eviction;

import java.util.Collections;

public interface EvictionPolicy<K> {
    void updateAccessedKey(K key);
    K evictKey();

    void displayAccessSequence();
}
