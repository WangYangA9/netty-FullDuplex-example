package lru;

/**
 * Created by wangyang on 2019-08-29
 */

public class LruNode<K, V> {
    private K key;
    private V value;
    private LruNode<K, V> prev;
    private LruNode<K, V> next;

    public LruNode(K key, V value, LruNode<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
        this.prev = null;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public LruNode<K, V> getPrev() {
        return prev;
    }

    public void setPrev(LruNode<K, V> prev) {
        this.prev = prev;
    }

    public LruNode<K, V> getNext() {
        return next;
    }

    public void setNext(LruNode<K, V> next) {
        this.next = next;
    }
}
