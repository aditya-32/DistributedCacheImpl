package com.example.DistributedCacheTest.model.eviction;

import javafx.util.Pair;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class LFUEvictionPolicy<K> implements EvictionPolicy<K> {
    PriorityQueue<Pair<K, Integer>> countHeap = new PriorityQueue<>(Comparator.comparingInt(Pair::getValue));
    Map<K, Pair<K, Integer>> indexing = new HashMap<>();
    @Override
    public void updateAccessedKey(K key) {
        Pair<K, Integer> nodePair = indexing.get(key);
        countHeap.remove(nodePair);
        Pair<K, Integer> newNodePAir = new Pair<>(key, nodePair.getValue()+1);
        indexing.put(key, newNodePAir);
        countHeap.add(newNodePAir);
    }

    @Override
    public K evictKey() {
        if (!countHeap.isEmpty()) {
            Pair<K, Integer> leastFreqUsedValue = countHeap.poll();
            System.out.println("Removed Element "+ leastFreqUsedValue);
            return leastFreqUsedValue.getKey();
        }
        return null;
    }

    @Override
    public void displayAccessSequence() {

    }
}
