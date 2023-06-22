package org.example;

import java.util.HashMap;
import java.util.Map;

class LRUCache {

  private int size;
  private Map<Integer, CacheElement> map;
  private CacheElement head;
  private CacheElement tail;


  class CacheElement {
    Integer key;
    Integer value;
    private CacheElement previous;
    private CacheElement next;

    public CacheElement(Integer key, Integer value) {
      this.key = key;
      this.value = value;
    }

  }


  public LRUCache(int capacity) {
    this.size = capacity;
    map = new HashMap<>(size);
  }

  public void printAll() {
    while(head.next!=null) {
      System.out.println(head.key);
      System.out.println(head.value);
      System.out.println("____");
      head=head.next;
    }
  }

  public int get(int key) {
    if (map.containsKey(key)) {
      CacheElement cacheElement = map.get(key);
      moveToFront(cacheElement);
      return key;
    }
    return -1;
  }

  public void put(int key, int value) {
    CacheElement cacheElement = map.get(key);
    if (cacheElement != null) {
      cacheElement.value = value;
      moveToFront(cacheElement);
    } else {
      CacheElement newCacheElement = new CacheElement(key, value);
      addToFront(newCacheElement);
      map.put(key, newCacheElement);
      if (map.size() > size) {
        map.remove(tail.key);
        removeLast();
      }
    }
  }

  private void addToFront(CacheElement cacheElement) {
    if(head==null) {
      head=cacheElement;
      tail=cacheElement;
    }
    else {
      cacheElement.next = head;
      head.previous=cacheElement;
      head=cacheElement;
    }

  }

  private void moveToFront(CacheElement cacheElement) {
    if (cacheElement == head) {
      return;
    }
    if (cacheElement == tail) {
      tail = tail.previous;
      tail.next = null;
    } else {
      cacheElement.previous.next = cacheElement.next;
      cacheElement.next.previous = cacheElement.previous;
    }
    cacheElement.next = head;
    cacheElement.previous = null;
    head.previous = cacheElement;
    head = cacheElement;
  }

  private void removeLast() {
    if (tail == null) {
      return;
    }
    if (head == tail) {
      head = null;
      tail = null;
    } else {
      tail = tail.previous;
      tail.next = null;
    }
  }


}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */