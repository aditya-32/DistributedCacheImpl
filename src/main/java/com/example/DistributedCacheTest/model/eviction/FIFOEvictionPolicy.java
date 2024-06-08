package com.example.DistributedCacheTest.model.eviction;

import com.example.DistributedCacheTest.model.DataNode;
import com.example.DistributedCacheTest.model.DoubleLinkedDataList;

public class FIFOEvictionPolicy<K> implements EvictionPolicy<K> {

    DoubleLinkedDataList<K> keyList = new DoubleLinkedDataList<>();

    @Override
    public void updateAccessedKey(K key) {
        keyList.insertNodeAtTail(new DataNode<>(key));
    }

    @Override
    public K evictKey() {
        DataNode<K> fifoNode = keyList.getHead();
        return keyList.remove(fifoNode).getValue();
    }

    @Override
    public void displayAccessSequence() {
        DataNode<K> temp = keyList.getHead();
        while(temp.getNext() != null) {
            System.out.print(temp.getValue()+" ");
            temp = temp.getNext();
        }
        System.out.println();
    }
}
