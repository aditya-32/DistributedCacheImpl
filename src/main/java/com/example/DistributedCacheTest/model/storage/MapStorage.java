package com.example.DistributedCacheTest.model.storage;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class MapStorage<K,V> implements IStorage<K,V> {


    private Map<K, V> map = new HashMap<>();
    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        V result = map.put(key, value);
        return  result;
    }

    @Override
    public int getSize() {
        return map.size();
    }

    @Override
    public V removeKey(K key) {
        return map.remove(key);
    }
}
