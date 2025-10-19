import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    private int length;
    private final Node sentinel;

    public LinkedListDeque61B() {
        sentinel = new Node(null);
        sentinel.set(sentinel, sentinel);

        length = 0;
    }

    @Override
    public void addFirst(T x) {

        Node node = new Node(x);
        Node preHead = sentinel.latter;
        preHead.setPrevious(node);
        sentinel.setLatter(node);
        node.set(sentinel, preHead);

        length++;

    }

    @Override
    public void addLast(T x) {

        Node node = new Node(x);
        Node preLast = sentinel.previous;
        preLast.setLatter(node);
        sentinel.setPrevious(node);
        node.set(preLast, sentinel);

        length++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node p = sentinel.getLatter();
        while (p != sentinel) {
            returnList.addLast(p.value());

            p = p.getLatter();
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        Node dummyHead = sentinel.getLatter().getLatter();
        Node node = sentinel.getLatter();

        dummyHead.setPrevious(sentinel);
        sentinel.setLatter(dummyHead);

        length--;
        return node.value();
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        Node dummyHead = sentinel.getPrevious().getPrevious();
        Node node = sentinel.getPrevious();

        dummyHead.setLatter(sentinel);
        sentinel.setPrevious(dummyHead);

        length--;
        return node.value();
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) {
            return null;
        }
        Node p = sentinel.getLatter();
        int cnt = 0;
        while (cnt != index) {
            p = p.getLatter();
            cnt++;
        }
        return p.value();
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size()) {
            return null;
        }
        return getRecursiveHelper(sentinel.getLatter(), index);
    }

    private T getRecursiveHelper(Node node, int index) {
        return (index == 0) ? node.value() : getRecursiveHelper(node.getLatter(), index - 1);
    }

    /**Node : Implementing the node of a Deque
     * Has  :
     * */
    private class Node {

        private final T value;
        private Node previous;
        private Node latter;

        public Node(T x) {
            value = x;
            previous = null;
            latter = null;
        }

        /**An encapsulated method for getting the value of a node.
         * @return the value the node contains.
         */
        public T value() {
            return this.value;
        }

        /**
         * @param prev the previous node
         * @param lat the latter node
         */
        public void set(Node prev, Node lat) {
            this.previous = prev;
            this.latter = lat;
        }

        /**
         * @param previous the previous node
         */
        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        /**
         * @param latter the latter node
         */
        public void setLatter(Node latter) {
            this.latter = latter;
        }

        /**
         * @return the previous node of the node;
         */
        public Node getPrevious() {
            return previous;
        }

        /**
         * @return the latter node of the node
         */
        public Node getLatter() {
            return latter;
        }
    }
}
