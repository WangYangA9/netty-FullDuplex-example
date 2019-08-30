package lru;

/**
 * Created by wangyang on 2019-08-29
 */
/**
 * KV 存储抽象
 */
public interface Storage<K,V> {
    /**
     * 根据提供的 key 来访问数据
     * @param key 数据 Key
     * @return 数据值
     */
    V get(K key);
}