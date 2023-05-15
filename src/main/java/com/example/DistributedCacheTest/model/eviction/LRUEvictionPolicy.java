package com.example.DistributedCacheTest.model.eviction;

import com.example.DistributedCacheTest.model.DataNode;
import com.example.DistributedCacheTest.model.DoubleLinkedDataList;

import java.util.HashMap;
import java.util.Map;

public class LRUEvictionPolicy<K> implements EvictionPolicy<K>{
    private DoubleLinkedDataList<K> keyList = new DoubleLinkedDataList<>();
    private Map<K , DataNode<K>> indexing = new HashMap<>();

    @Override
    public void updateAccessedKey(K key) {
        DataNode<K> newNode  = new DataNode<>(key);
        if (indexing.containsKey(key)) {
            newNode  = keyList.remove(indexing.get(key));
        }
        newNode = keyList.insertNodeAtTail(newNode);
        indexing.put(key, newNode);
    }

    @Override
    public K evictKey() {
        DataNode<K> lruNode = keyList.getHead();
        System.out.println("evicting value :"+lruNode.getValue());
        return keyList.remove(lruNode).getValue();
    }

    @Override
    public void displayAccessSequence() {
        DataNode<K> temp = keyList.getHead();
        System.out.println();
        while(temp.getNext()!=null){
            System.out.print(temp.getValue()+" ");
            temp = temp.getNext();
        }
        System.out.println();
    }


}
