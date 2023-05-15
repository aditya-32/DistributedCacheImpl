package com.example.DistributedCacheTest.model;

import org.springframework.stereotype.Component;

import java.util.Objects;

public class DoubleLinkedDataList<V> {
    private DataNode<V> head;
    private DataNode<V> tail;

    public DoubleLinkedDataList(){
        this.head = new DataNode<>(null);
        this.tail = new DataNode<>(null);
        head.setNext(tail);
        tail.setPrev(head);
    }

    public DataNode<V> insertNodeAtTail(DataNode<V> node) {
        DataNode<V> tailPrev = tail.getPrev();
        tailPrev.setNext(node);
        node.setPrev(tailPrev);
        node.setNext(tail);
        tail.setPrev(node);
        return tail.getPrev();
    }

    public DataNode<V> insertNodeAtHead(DataNode<V> node) {
        DataNode<V>  headNext = head.getNext();
        headNext.setPrev(node);
        node.setNext(headNext);
        head.setNext(node);
        node.setPrev(head);
        return head.getNext();
    }
    public DataNode<V> remove(DataNode<V> node){
        if(Objects.isNull(node)){
            return node;
        }
        node.getNext().setPrev(node.getPrev());
        node.getPrev().setNext(node.getNext());
        node.setNext(null);
        node.setPrev(null);
        return node;
    }
    public DataNode<V> getHead(){
        return head.getNext();
    }

    public DataNode<V> getTail(){
        return tail.getPrev();
    }

}
