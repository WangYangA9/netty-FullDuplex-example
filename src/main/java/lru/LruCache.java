package lru;

/**
 * Created by wangyang on 2019-08-29
 */
/**
 * LRU 缓存。你需要继承这个抽象类来实现 LRU 缓存。
 * @param <K> 数据 Key
 * @param <V> 数据值
 */
public abstract class LruCache<K, V> implements Storage<K,V>{
    // 缓存容量
    protected final int capacity;
    // 低速存储，所有的数据都可以从这里读到
    protected final Storage<K,V> lowSpeedStorage;

    public LruCache(int capacity, Storage<K,V> lowSpeedStorage) {
        this.capacity = capacity;
        this.lowSpeedStorage = lowSpeedStorage;
    }
}