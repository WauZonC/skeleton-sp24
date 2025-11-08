import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private Node root;
    private int size;

    public BSTMap() {
        root = null;
        size = 0;
    }

    private Node findNode(K key) {
        Node n = root;
        while (n != null) {
            if (n.key().compareTo(key) == 0) {
                return n;
            } else if (n.key().compareTo(key) > 0) {
                n = n.left();
            } else {
                n = n.right();
            }
        }
        return null;
    }

    private Node insert(Node present, Node insert) {
        if (present == null) {
            return insert;
        } else if (present.key().compareTo(insert.key()) < 0) {
            present.setRight(insert(present.right(), insert));
        } else {
            present.setLeft(insert(present.left(), insert));
        }
        return present;
    }

    @Override
    public void put(K key, V value) {
        Node find = findNode((K) key);
        if (find != null) {
            find.modifyValue(value);
        } else {
            size++;
        }
        Node insert = new Node(key, value);
        root = insert(root, insert);
    }

    @Override
    public V get(K key) {
        Node returnNode = findNode(key);
        if (returnNode != null) {
            return returnNode.value();
        } else {
            return null;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return findNode(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    private class Node {
        private final K key;
        private V value;
        private Node left;
        private Node right;
        private int number;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            number = 0;
        }

        public void setLeft(Node left) {
            this.left = left;
            this.number += 1;
        }

        public void setRight(Node right) {
            this.right = right;
            this.number += 1;
        }

        public Node left() {
            return left;
        }

        public Node right() {
            return right;
        }

        public V value() {
            return value;
        }

        public K key() {
            return key;
        }

        public void modifyValue(V newValue) {
            value = newValue;
        }

        public int linkedNumber() {
            return number;
        }
    }
}
