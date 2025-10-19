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
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public T removeFirst() {
        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }

    /**Node : Implementing the node of a Deque
     * Has  :
     * */
    private class Node {

        private T value;
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
         * @param previous the previous node
         * @param latter the latter node
         */
        public void set(Node previous, Node latter) {
            this.previous = previous;
            this.latter = latter;
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
