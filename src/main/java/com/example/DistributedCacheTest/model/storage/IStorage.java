package com.example.DistributedCacheTest.model.storage;

public interface IStorage<K,V>  {

    V get(K key);

    V put(K key, V value );

    int getSize();

    V removeKey(K key);
}
