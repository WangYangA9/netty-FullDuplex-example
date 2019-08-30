package lru;

import java.util.Map;

/**
 * Created by wangyang on 2019-08-29
 * highSpeedStorage为查询用的高速Key-value存储
 * 同时，将key-value维护成一个双向链表，head为新鲜的数据，tail处为老一些的数据
 * 在容量满时，一次删除 size/10+1个元素，避免重复触发删除操作
 */
public class GeekLru<K, V> extends LruCache<K, V> {
    private Map<K,LruNode<K, V>> highSpeedStorage;
    private LruNode<K, V> head;
    private LruNode<K, V> tail;
    private int deleteLength;

    public GeekLru(int capacity, Storage<K,V> lowSpeedStorage) {
        super(capacity, lowSpeedStorage);
        deleteLength = capacity / 10 + 1;
        head = null;
        tail = null;
    }

    @Override
    public V get(K key) {
        LruNode<K, V> res = highSpeedStorage.get(key);
        if(res != null){
            removeInLinkedList(res);
            res.setNext(head);
            head = res;
            return res.getValue();
        } else {
            //未命中缓存，从低速存储中查询，如果查到了，调用put放入高速缓存
            V result = lowSpeedStorage.get(key);
            if(result != null){
                put(key, result);
            }
            return result;
        }
    }

    public void put(K key, V value) {
        LruNode<K, V> node = highSpeedStorage.get(key);
        if(node != null) {
            //如果node已经存在 在双向链表中先删除该node
            removeInLinkedList(node);
        } else {
            checkSize();//增加一个全新的key之前，缓存是否超过容量
            node = new LruNode<>(key, value, head);
        }
        highSpeedStorage.put(key, node);
        head = node;
        //TODO 更新低速存储
    }

    private void removeInLinkedList(LruNode<K, V> node) {
        if(node != null){
            if (node.getPrev() != null) {
                node.getPrev().setNext(node.getNext());
            } else {
                head = node.getNext();
            }
            if (node.getNext() != null) {
                node.getNext().setPrev(node.getPrev());
            } else {
                tail = node.getPrev();
            }
            node.setNext(null);
            node.setPrev(null);
        }
    }

    private int checkSize() {
        int len = 0;
        if(highSpeedStorage.size() >= capacity) {
            len = deleteFromTail(deleteLength);
        }
        return len;
    }

    private int deleteFromTail(int length) {
        LruNode<K, V> temp = tail;
        int len = 0;
        while(temp != null && len++ < length){
            highSpeedStorage.remove(temp.getKey());
            temp = tail.getPrev();
            if(temp != null) {
                temp.setNext(null);
            }
        }
        return len;
    }

}
