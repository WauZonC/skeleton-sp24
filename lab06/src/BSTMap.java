import java.util.*;

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
            return;
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
        Set<K> set = new TreeSet<>();
        for (K key : this) {
            set.add(key);
        }
        return set;
    }

    @Override
    public V remove(K key) {
        Node find = findNode(key);
        if (find != null) {
            V value = find.value();
            root = remove(root, key);
            size--;
            return value;
        } else {
            return null;
        }
    }

    private Node remove(Node node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = node.key().compareTo(key);
        if (cmp < 0) {
            node.setRight(remove(node.right(), key));
        } else if (cmp > 0) {
            node.setLeft(remove(node.left(), key));
        } else {
            if (node.left() == null) {
                return node.right();
            } else if (node.right() == null) {
                return node.left();
            } else {
                Node remove = findMin(node.right());
                node.modify(remove.key(), remove.value());
                node.setRight(remove(node.right(), remove.key()));
            }
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left() != null) {
            node = node.left();
        }
        return node;
    }

    @Override
    public Iterator<K> iterator() {
        return toList(root).iterator();
    }

    private List<K> toList(Node n) {
        if (n == null) {
            return new ArrayList<>();
        }
        List<K> list;
        if (n.left() != null) {
            list = toList(n.left());
        } else {
            list = new ArrayList<>();
        }
        list.add(n.key());
        if (n.right() != null) {
            list.addAll(toList(n.right()));
        }
        return list;
    }

    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }

        public void modify(K k, V v) {
            this.key = k;
            this.value = v;
        }
        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
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
            return (left == null ? 0 : 1) + (right == null ? 0 : 1);
        }
    }
}
