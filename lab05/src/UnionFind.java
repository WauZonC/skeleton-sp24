import java.util.ArrayList;
import java.util.List;

public class UnionFind {
    List<Integer> list;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            list.addLast(-1);
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        return - parent(find(v));
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return list.get(v);
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v < 0 || v >= list.size()) {
            throw new IllegalArgumentException();
        }
        if (parent(v) < 0) {
            return v;
        } else {
            int root = find(parent(v));
            list.set(v, root);
            return root;
        }
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        int newSize = sizeOf(p2) + sizeOf(p1);
        if (sizeOf(p1) > sizeOf(p2)) {
            list.set(p2, p1);
            list.set(p1, -newSize);
        } else {
            list.set(p1, p2);
            list.set(p2, -newSize);
        }
    }

}
