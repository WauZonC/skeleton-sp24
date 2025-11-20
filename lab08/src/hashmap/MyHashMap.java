package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private final int initialCapacity;
    private int capacity;
    private final double loadFactor;
    private int N;

    /** Constructors */
    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        N = 0;
        this.initialCapacity = initialCapacity;
        capacity = initialCapacity;
        this.loadFactor = loadFactor;
        initialBucket();
    }

    private void initialBucket() {
        buckets = new Collection[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a data structure to be a hash table bucket
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * Override this method to use different data structures as
     * the underlying bucket type
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        private final K key;
        private V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /**
     * Returns the valid index of a key
     * @param key the key to be hashed
     * @return valid index
     */
    private int index(K key) {
        return Math.floorMod(key.hashCode(), capacity);
    }

    private Node getNode(K key) {
        for (Node node : buckets[index(key)]) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        Node node = getNode(key);
        if (node == null) {
            buckets[index(key)].add(new Node(key, value));
            N++;
            if ((double) N / capacity >= loadFactor) {
                resize();
            }
        } else {
            node.value = value;
        }
    }

    /**
     * Resize the buckets when the N / M greater than loadFactor
     */
    private void resize() {
        Collection<Node>[] oldBuckets = this.buckets;
        this.capacity = capacity * 2;
        initialBucket();
        for (Collection<Node> bucket : oldBuckets) {
            for (Node node : bucket) {
                this.buckets[index(node.key)].add(node);
            }
        }
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node == null ? null : node.value;
    }

    @Override
    public boolean containsKey(K key) {
        return getNode(key) != null;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public void clear() {
        capacity = initialCapacity;
        initialBucket();
        N = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> returnSet = new HashSet<>();
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                returnSet.add(node.key);
            }
        }
        return returnSet;
    }

    @Override
    public V remove(K key) {
        Node node = getNode(key);
        if (node == null) {
            return null;
        } else {
            buckets[index(key)].remove(node);
            N--;
            return node.value;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

}
