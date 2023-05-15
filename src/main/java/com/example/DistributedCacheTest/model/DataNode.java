package com.example.DistributedCacheTest.model;


import lombok.Data;

@Data
public class DataNode<V> {

    private V value;
    private DataNode<V> prev;
    private DataNode<V> next;
    public DataNode(V value){
        this.value = value;
        this.prev = null;
        this.next = null;
    }
}
