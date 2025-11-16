public class RedBlackTree<T extends Comparable<T>> {
    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack to decide the node's color
         * @param item to fill the node's content
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack to decide the
         * @param item to fill the node's content
         * @param left the left node of present node
         * @param right the right node of present node
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }

        private void flip() {
            isBlack = !isBlack;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node the node of the tree expected to be flipped
     */
    void flipColors(RBTreeNode<T> node) {
        node.flip();
        node.left.flip();
        node.right.flip();
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node the previous node that expected to be rotated
     * @return the present node that used to be at the left
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        RBTreeNode<T> presentNode = node.left;
        node.left = presentNode.right;
        presentNode.right = node;
        swap(node, presentNode);
        return presentNode;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node the previous node that expected to be rotated
     * @return the present node that used to be at the right
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        RBTreeNode<T> presentNode = node.right;
        node.right = presentNode.left;
        presentNode.left = node;
        swap(node, presentNode);
        return presentNode;
    }

    private void swap(RBTreeNode<T> node1, RBTreeNode<T> node2) {
        boolean temporaryColor = node2.isBlack;
        node2.isBlack = node1.isBlack;
        node1.isBlack = temporaryColor;
    }
    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node expected to be checked
     * @return whether the node is red
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item the content that will be inserted
     */
    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }

    /**
     * Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node the previous node
     * @param item the content waited to be inserted
     * @return the present node
     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // Handle normal binary search tree insertion.
        if (node == null) {
            node = new RBTreeNode<>(false, item);
        } else if (node.item.compareTo(item) > 0) {
            node.left = insert(node.left, item);
        } else {
            node.right = insert(node.right, item);
        }
        // Rotate left operation
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }
        // Rotate right operation
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        // Color flip
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        return node;
    }

}
