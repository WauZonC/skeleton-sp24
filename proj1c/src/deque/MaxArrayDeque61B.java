package deque;

import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {

    private Comparator<T> comparator;
    /**@param c a comparator for MaxArrayDeque so that it has order
     * */
    public MaxArrayDeque61B(Comparator<T> c) {
    }
}
