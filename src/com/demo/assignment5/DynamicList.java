package com.demo.assignment5;

import java.util.Iterator;

public class DynamicList<E extends Comparable<E>> implements List<E> {
    private Node<E> head;
    private Node<E> tail;
    private Node<E> cursor;
    private int length;

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public boolean empty() {
        return this.length() == 0;
    }

    @Override
    public boolean offEnd() {
        return this.cursor == null;
    }

    @Override
    public void toFront() {
        this.cursor = this.head;
    }

    @Override
    public void advance() {
        if(this.cursor != null)
            this.cursor = this.cursor.next;
    }

    @Override
    public E get() {
        if (this.cursor == null)
            throw new NoItemException();

        return this.cursor.data;
    }

    @Override
    public void find(E element) {
        while(this.cursor != null) {
            if (this.cursor.data.compareTo(element) == 0)
                break;
            this.cursor = this.cursor.next;
        }
    }

    @Override
    public void add(E item){
        Node<E> node = new Node<>(item);

        if(this.empty()) {                     // 1. head == cur == null
            this.head = node;
            this.tail = node;
        } else if (this.head == this.cursor) { // 2. head == cur != null
            node.next = this.head;
            this.head = node;
        } else if(this.offEnd()) {              // 3. head != null, cur == null
            this.tail.next = node;
            this.tail = node;
        } else {                                // 4. head != cur != null
            Node prev = head;
            while (prev.next != this.cursor) {
                prev = prev.next;
            }
            node.next = prev.next;
            prev.next = node;
        }

        this.cursor = node;
        this.length++;
    }

    @Override
    public E remove() {
        if(this.offEnd()) {              // 1. and 3. head == cur == null and head != cur == null
            throw new NoItemException();
        }

        E result = this.cursor.data;

        if(this.head == this.cursor) {    // 2. head == cur != null
            this.head = this.head.next;
            this.cursor = this.head;
        } else {                          // 4. head != cur != null
            Node<E> prev = this.head;
            while(prev.next != this.cursor){
                prev = prev.next;
            }
            prev.next = this.cursor.next;
            this.cursor = prev.next;
        }

        this.length--;
        return result;
    }

    /**
     * the front of the linkedlist
     * @return Node
     */
    public Node<E> getFront() {
        return head;
    }

    /**
     *
     * @return Iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new DynamicListIterator<>(this);
    }
}